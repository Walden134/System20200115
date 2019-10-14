package com.hust.swat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class map_grid2dhru {
	// Format of outout: tcw
	// 390 62 (total number of dHRUs to be read in) (the maximum number of grids
	// that contribute to a single dhru)
	// 1 4 (hru#) (# of grids contributing to this dhru)
	// grid2_row_ID grid12_row_ID grid31_row_ID grud37_row_ID (list of grid row id
	// #s contributing to dhru#)
	// grid2_col_ID grid12_col_ID grid31_col_ID grud37_col_ID (list of grid column
	// id #s contributing to dhru#)
	// 0.36 0.25 0.04 0.35 (list of %areas of grid contributing to dhru#, adds to 1)

	public static void main(String[] args) throws IOException {
		// Inputs
		String directory = args[0];
		int num_Grids = Integer.parseInt(args[1]);
		int num_DHRUs = Integer.parseInt(args[2]);
		System.out.println("Creating map_grid2dhru.txt...");
		readFile(directory, num_Grids, num_DHRUs);
		System.out.println("Done");
	}

	public static void readFile(String directory, int numGrids, int numDHRUs) throws IOException {
		// read the file
		String path = directory + "/DHRU_grid.csv";
		File file = new File(path);
		Scanner scanner = new Scanner(file);

		// Read header
		String[] header = scanner.nextLine().split(",");
		// Get indices for the desired columns
		int index_col_row = 0;
		int index_grid_id = 0;
		int index_dhru_id = 0;
		int index_dhru_area = 0;
		int index_overlap_area = 0;
		for (int i = 0; i < header.length; i++) {
			header[i] = header[i].replace('"', '%');
			header[i] = header[i].replaceAll("%", "");
			if (header[i].equalsIgnoreCase("Col_Row")) {
				index_col_row = i;
			} else if (header[i].equalsIgnoreCase("grid_id")) {
				index_grid_id = i;
			} else if (header[i].equalsIgnoreCase("dhru_id")) {
				index_dhru_id = i;
			} else if (header[i].equalsIgnoreCase("dhru_area")) {
				index_dhru_area = i;
			} else if (header[i].equalsIgnoreCase("overlap_a")) {
				index_overlap_area = i;
			}
		}
		// Check for missing information

		if (index_col_row == 0) {
			throw new IOException("Error encountered, there is no attribute column 'Col_Row' in DHRU_grid.csv");
		}
		if (index_grid_id == 0) {
			throw new IOException("Error encountered, there is no attribute column 'grid_id' in DHRU_grid.csv");
		}
		if (index_dhru_id == 0) {
			throw new IOException("Error encountered, there is no attribute column 'dhru_id' in DHRU_grid.csv");
		}
		if (index_dhru_area == 0) {
			throw new IOException("Error encountered, there is no attribute column 'dhru_area' in DHRU_grid.csv");
		}
		if (index_overlap_area == 0) {
			throw new IOException("Error encountered, there is no attribute column 'overlap_a' in DHRU_grid.csv");
		}
		// Read and parse text file
		ArrayList<String> DHRU_Num = new ArrayList<String>(); // HRU Num
		ArrayList<Double> percent_Area_HRU = new ArrayList<Double>(); // area grid inside given HRU / total area HRU
		ArrayList<String> grid_Num = new ArrayList<String>(); // Grid number
		ArrayList<String> grid_row = new ArrayList<String>(); // Grid row id
		ArrayList<String> grid_col = new ArrayList<String>(); // Grid column id
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// Split the read data
			String[] data_Splitted = line.split(",");
			// Grab values from text file
			double total_area_of_DHRU = Double.parseDouble(data_Splitted[index_dhru_area]);
			double area_of_GRID_within_DHRU = Double.parseDouble(data_Splitted[index_overlap_area]);
			// Calculate percent area of grid within HRU
			double percent_area_of_DHRU_Double = area_of_GRID_within_DHRU / total_area_of_DHRU;
			// Save values from text file
			DHRU_Num.add(data_Splitted[index_dhru_id]);
			percent_Area_HRU.add(percent_area_of_DHRU_Double);
			grid_Num.add(data_Splitted[index_grid_id]);
			grid_row.add(columnRow(data_Splitted[index_col_row], false)); // grid row#
			grid_col.add(columnRow(data_Splitted[index_col_row], true)); // grid column#
		}
		scanner.close();
		write_map_grid2dhru(directory, DHRU_Num, percent_Area_HRU, grid_Num, grid_row, grid_col, numGrids, numDHRUs);
	}

	public static String columnRow(String column_row, boolean columnTrue) {
		// Convers the strin Col - row into
		String[] array = column_row.substring(1, column_row.length() - 1).split("-");
		String column = array[0].trim();
		String row = array[1].trim();
		String returnValue = row;
		if (columnTrue) {
			returnValue = column;
		}
		return returnValue;
	}

	public static void write_map_grid2dhru(String directory, ArrayList<String> DHRU_Num,
			ArrayList<Double> percent_Area_HRU, ArrayList<String> grid_Num, ArrayList<String> grid_row,
			ArrayList<String> grid_col, int numGrids, int numDHRUs) throws IOException {
		String path = directory + "/map_grid2dhru.txt";
		File file = new File(path);
		boolean fileTF = file.isFile();
		if (fileTF) {
			boolean fileDeleteTF = file.delete();
			if (!fileDeleteTF) {
				System.out.println("The file (" + path + ") could not be deleted");
			}
		}
		ArrayList<ArrayList<String>> dhruIndexList = new ArrayList<ArrayList<String>>();
		int maxSize = 0;
		for (int j = 1; j <= numDHRUs; j++) { // iterating over dhru number
			System.out.println(j);
			// Find all positions containing current Grid number
			// arraylist containing all grids within current HRU
			ArrayList<String> gridIndex_for_currentDHRU = new ArrayList<String>();
			for (int i = 0; i < DHRU_Num.size(); i++) {
				if (j == (int) Double.parseDouble(DHRU_Num.get(i))) {
					// Gives an array containing all the indexes of grids which contribute to
					// current HRU
					gridIndex_for_currentDHRU.add(String.valueOf(i));
				}
			}
			if (gridIndex_for_currentDHRU.size() > maxSize)
				maxSize = gridIndex_for_currentDHRU.size();
			dhruIndexList.add(gridIndex_for_currentDHRU);
		}

		// Format for which the text file will be written
		Formatter MOD_HRU = new Formatter(new FileWriter(file, true));
		// Write out the total number of dissaggregated hrus which intersect with the
		// MODFLOW grid cells and the maximum number of MODFLOW grid cells that
		// intersect a single dhru
		MOD_HRU.format("%1$12s%2$12s%n", numDHRUs, maxSize);
		for (int j = 1; j <= numDHRUs; j++) { // iterating over dhru number
			ArrayList<String> currentDHRU_gridIndexList = dhruIndexList.get(j - 1);
			// format the dHRU number as first column and the number of grids contributing
			// to this HRU as the second column
			MOD_HRU.format("%1$12s%2$12s%n", j, currentDHRU_gridIndexList.size());
			// Print the row ids for the grids contributing to the current dhru
			for (int i = 0; i < currentDHRU_gridIndexList.size(); i++) {
				int gridIndex = Integer.parseInt(currentDHRU_gridIndexList.get(i));
				int rowNumber = (int) Double.parseDouble(grid_row.get(gridIndex));
				MOD_HRU.format("%1$12s", rowNumber);
			}
			MOD_HRU.format("%n", "");
			// Print the column ids for the grids contributing to the current dhru
			for (int i = 0; i < currentDHRU_gridIndexList.size(); i++) {
				int gridIndex = Integer.parseInt(currentDHRU_gridIndexList.get(i));
				int columnNumber = (int) Double.parseDouble(grid_col.get(gridIndex));
				MOD_HRU.format("%1$12s", columnNumber);
			}
			MOD_HRU.format("%n", "");
			// Print Percent areas of each grid contributing to the current dhru
			for (int i = 0; i < currentDHRU_gridIndexList.size(); i++) {
				int gridIndex = Integer.parseInt(currentDHRU_gridIndexList.get(i));
				MOD_HRU.format("%1$12.5f", percent_Area_HRU.get(gridIndex));
			}
			MOD_HRU.format("%n", "");
		}
		MOD_HRU.close();// tcw
	}
}
