package com.hust.swat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class map_dhru2grid {
	public static void main(String[] args) throws IOException { // Inputs
		String directory = args[0];
		int num_Grids = Integer.parseInt(args[1]);
		int num_DHRUs = Integer.parseInt(args[2]);
		System.out.println("Creating map_dhru2grid.txt...");
		readFile(directory, num_Grids, num_DHRUs);
		System.out.println("Done");
	}

	/**
	 * * Reads the attribute table file MOD_HRU.csv containing the grid IDs, HRU
	 * IDs, their respective areas and their area of overlap * @param directory the
	 * path to the input file (ex. C:/temp) * @param numGrids the total number of
	 * grid cells (active and inactive) in the MODFLOW model * @param num_DHRUs the
	 * total number of disaggregated HRUs in the SWAT model * @throws IOException
	 */
	public static void readFile(String directory, int numGrids, int num_DHRUs) throws IOException {
		// read the file
		String path = directory + "/DHRU_grid.csv";
		File file = new File(path);
		Scanner scanner = new Scanner(file);

		// Read header
		String[] header = scanner.nextLine().split(",");

		// Get indices for the desired columns
		int index_grid_id = 0;
		int index_grid_area = 0;
		int index_dhru_id = 0;
		int index_overlap_area = 0;
		for (int i = 0; i < header.length; i++) {
			header[i] = header[i].replace('"', '%');
			header[i] = header[i].replaceAll("%", "");
			if (header[i].equalsIgnoreCase("grid_id")) {
				index_grid_id = i;
			} else if (header[i].equalsIgnoreCase("grid_area")) {
				index_grid_area = i;
			} else if (header[i].equalsIgnoreCase("dhru_id")) {
				index_dhru_id = i;
			} else if (header[i].equalsIgnoreCase("overlap_a")) {
				index_overlap_area = i;
			}
		}
		// Check for missing information
		if (index_grid_id == 0) {
			throw new IOException("Error encountered, there is no attribute column 'grid_id' in DHRU_grid.csv");
		}
		if (index_grid_area == 0) {
			throw new IOException("Error encountered, there is no attribute column 'grid_area' in DHRU_grid.csv");
		}
		if (index_dhru_id == 0) {
			throw new IOException("Error encountered, there is no attribute column 'dhru_id' in DHRU_grid.csv");
		}
		if (index_overlap_area == 0) {
			throw new IOException("Error encountered, there is no attribute column 'overlap_a' in DHRU_grid.csv");
		}
		// Read and parse text file
		ArrayList<String> DHRU_Num = new ArrayList<String>(); // DHRU Num
		ArrayList<Double> percent_Area_GRID = new ArrayList<Double>(); // area DHRU inside given grid / total area grid
		ArrayList<String> grid_Num = new ArrayList<String>(); // Grid number
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// Split the read data
			String[] data_Splitted = line.split(",");
			// Grab values from text file
			double total_area_of_GRID = Double.parseDouble(data_Splitted[index_grid_area]);
			double area_of_HRU_within_GRID = Double.parseDouble(data_Splitted[index_overlap_area]);
			// Calculate percent area of HRU within grid
			double percent_area_of_GRID_Double = area_of_HRU_within_GRID / total_area_of_GRID;

			// Save values from text file
			DHRU_Num.add(data_Splitted[index_dhru_id]);
			percent_Area_GRID.add(percent_area_of_GRID_Double);
			grid_Num.add(data_Splitted[index_grid_id]);
		}
		scanner.close();
		write_modified_HRU_MOD(directory, DHRU_Num, percent_Area_GRID, grid_Num, numGrids, num_DHRUs);
	}

	/**
	 * * Takes the list of grid/hru ids and the percent area of HRUs contributing to
	 * a given grid and creates the SM linkage file * map_hru2grid.txt * @param
	 * directory the path to the output file (ex. C:/temp) * @param DHRU_Num list of
	 * HRU ID numbers * @param percent_Area_GRID list of percent grid within the
	 * above HRU ID * @param grid_Num list of grid ID numbers * @param numGrids the
	 * total number of grid cells (active and inactive) in the MODFLOW model
	 * * @param num_DHRUs the total number of disaggregated HRUs in the SWAT model
	 * * @throws IOException
	 */
	public static void write_modified_HRU_MOD(String directory, ArrayList<String> DHRU_Num,
			ArrayList<Double> percent_Area_GRID, ArrayList<String> grid_Num, int numGrids, int num_DHRUs)
			throws IOException {
		String path = directory + "/map_dhru2grid.txt";
		File file = new File(path);
		boolean fileTF = file.isFile();
		if (fileTF) {
			boolean fileDeleteTF = file.delete();
			if (!fileDeleteTF) {
				System.out.println("The file (" + path + ") could not be deleted");
			}
		}
		ArrayList<ArrayList<String>> gridIndexList = new ArrayList<ArrayList<String>>();
		int maxSize = 0;
		for (int j = 1; j <= numGrids; j++) { // iterating over grid number
			System.out.println(j);
			// Find all positions containing current Grid number
			ArrayList<String> DHRUindex_for_currentGrid = new ArrayList<String>();
			// arraylist containing all HRUs within current Grid Num"
			for (int i = 0; i < grid_Num.size(); i++) {
				if (j == (int) Double.parseDouble(grid_Num.get(i))) {
					/*
					 * Gives me an array containing all positions of HRUs which contribute to
					 * current grid
					 */
					DHRUindex_for_currentGrid.add(String.valueOf(i));
				}
			}
			if (DHRUindex_for_currentGrid.size() > maxSize)
				maxSize = DHRUindex_for_currentGrid.size();
			gridIndexList.add(DHRUindex_for_currentGrid);
		}
		// Format for which the text file will be written
		Formatter HRU_MOD = new Formatter(new FileWriter(file, true));
		/*
		 * Write out the total number of MODFLOW grid cells which intersect with the
		 * SWAT hrus and what the maximum number of SWAT hrus is that intersect this
		 */
		HRU_MOD.format("%1$12s%2$12s%n", numGrids, maxSize);
		for (int j = 1; j <= numGrids; j++) { // iterating over grid number
			ArrayList<String> currentGrid_DHRUindexList = gridIndexList.get(j - 1);
			/*
			 * format the grid number as first column and the number of HRUs contributing to
			 * this grid as the second column
			 */
			HRU_MOD.format("%1$12s%2$12s%n", j, currentGrid_DHRUindexList.size());
			// Print ID of HRUs contributing to the current grid
			for (int i = 0; i < currentGrid_DHRUindexList.size(); i++) {
				int hruIndex = Integer.parseInt(currentGrid_DHRUindexList.get(i));
				int hruNumber = (int) Double.parseDouble(DHRU_Num.get(hruIndex));
				HRU_MOD.format("%1$12s", hruNumber);
			}
			HRU_MOD.format("%n", "");
			// Print Percent areas of each HRU contributing to the current grid
			for (int i = 0; i < currentGrid_DHRUindexList.size(); i++) {
				int hruIndex = Integer.parseInt(currentGrid_DHRUindexList.get(i));
				HRU_MOD.format("%1$12.5f", percent_Area_GRID.get(hruIndex));
			}
			HRU_MOD.format("%n", "");
		}
		gridIndexList.clear();
		HRU_MOD.close();
	}
	// Format of output
	// (total number of grids to be read in) (the maximum number of dhrus that
	// contribute to a single grid)
	// 390 59
    //	(grid#) (# of dhrus contributing to this grid)
	// 1 4 
	// hru2_ID hru12_ID hru31_ID hru37_ID (list of dhru id #s contributing to grid#)
	// 0.36 0.25 0.04 0.35 (list of %areas of dhru contributing to grid#, adds to 1)
}
