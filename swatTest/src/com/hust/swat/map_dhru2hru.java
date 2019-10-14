package com.hust.swat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class map_dhru2hru {

	// Format of output:
	// 390 (total number of disaggregated HRUs to be read in)
	// 1 4 1 (aggregated hru#) (# of dhrus contributing to this hru) (subbasinID)
	// dhru1_ID dhru12_ID dhru31_ID dhru37_ID (list of dhru id #s contributing to
	// hru#)
	// 0.36 0.25 0.04 0.35 (list of %areas of dhru contributing to hru#, adds to 1)
	public static void main(String[] args) throws IOException {
		// Inputs
		String directory = args[0];
		int num_HRUs = Integer.parseInt(args[1]);
		int num_DHRUs = Integer.parseInt(args[2]);
		System.out.println("Creating map_dhru2hru.txt...");
		readFile(directory, num_DHRUs, num_HRUs);
		System.out.println("Done");
	}

	public static void readFile(String directory, int num_DHRUs, int num_HRUs) throws IOException {
		// read the file
		String path = directory + "/FullDHRU.csv";
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		// Read header
		String[] header = scanner.nextLine().split(",");
		// Get indices for the desired columns
		int index_hru_id = 0;
		int index_hru_area = 0;
		int index_sub_id = 0;
		int index_dhru_id = 0;
		int index_dhru_area = 0;
		for (int i = 0; i < header.length; i++) {
			header[i] = header[i].replace('"', '%');
			header[i] = header[i].replaceAll("%", "");
			if (header[i].equalsIgnoreCase("hru_id")) {
				index_hru_id = i;
			} else if (header[i].equalsIgnoreCase("Shape_Area")) {
				index_hru_area = i;
			} else if (header[i].equalsIgnoreCase("SUBBASIN")) {
				index_sub_id = i;
			} else if (header[i].equalsIgnoreCase("dhru_id")) {
				index_dhru_id = i;
			} else if (header[i].equalsIgnoreCase("dhru_area")) {
				index_dhru_area = i;
			}
		}
		// Check for missing information
		if (index_hru_id == 0) {
			throw new IOException("Error encountered, there is no attribute column 'hru_id' in FullDHRU_intersect.csv");
		}
		if (index_hru_area == 0) {
			throw new IOException(
					"Error encountered, there is no attribute column 'Shape_Area' in FullDHRU_intersect.csv");
		}
		if (index_sub_id == 0) {
			throw new IOException(
					"Error encountered, there is no attribute column 'SUBBASIN' in FullDHRU_intersect.csv");
		}
		if (index_dhru_id == 0) {
			throw new IOException(
					"Error encountered, there is no attribute column 'dhru_id' in FullDHRU_intersect.csv");
		}
		if (index_dhru_area == 0) {
			throw new IOException(
					"Error encountered, there is no attribute column 'dhru_area' in FullDHRU_intersect.csv");
		}
		// Read and parse now reduced text file (should have 1 line per dhru)
		ArrayList<String> HRU_Num = new ArrayList<String>(); // HRU Num
		ArrayList<Double> percent_Area_HRU = new ArrayList<Double>(); // area DHRU inside given HRU / total area HRU
		ArrayList<String> dhru_Num = new ArrayList<String>(); // DHRU number
		ArrayList<Integer> subbasin_id = new ArrayList<Integer>(); // subbasin id number containing the current river ID
																	// number
		while (scanner.hasNextLine()) {
			// Split the read data
			String[] data_Splitted = scanner.nextLine().split(",");
			// Grab values from text file
			double total_area_of_HRU = Double.parseDouble(data_Splitted[index_hru_area]);
			double area_of_Dhru_within_HRU = Double.parseDouble(data_Splitted[index_dhru_area]);
			// Calculate percent area of dhru within HRU
			double percent_area_of_HRU_Double = area_of_Dhru_within_HRU / total_area_of_HRU;
			// Save values from text file
			HRU_Num.add(data_Splitted[index_hru_id]);
			percent_Area_HRU.add(percent_area_of_HRU_Double);
			dhru_Num.add(data_Splitted[index_dhru_id]);
			subbasin_id.add(Integer.parseInt(data_Splitted[index_sub_id]));
		}
		scanner.close();
		write_map_grid2hru(directory, HRU_Num, percent_Area_HRU, dhru_Num, subbasin_id, num_DHRUs, num_HRUs);
	}

	public static void write_map_grid2hru(String directory, ArrayList<String> HRU_Num,
			ArrayList<Double> percent_Area_hru, ArrayList<String> dhru_Num, ArrayList<Integer> subbasin_id,
			int num_DHRUs, int num_HRUs) throws IOException {
		String path = directory + "/map_dhru2hru.txt";
		File file = new File(path);
		boolean fileTF = file.isFile();
		if (fileTF) {
			boolean fileDeleteTF = file.delete();
			if (!fileDeleteTF) {
				System.out.println("The file (" + path + ") could not be deleted");
			}
		}
		ArrayList<ArrayList<String>> hruIndexList = new ArrayList<ArrayList<String>>();
		int maxSize = 0;
		for (int j = 1; j <= num_HRUs; j++) { // iterating over hru number
			System.out.println(j);
			// Find all positions containing current hru number
			ArrayList<String> DHRUindex_for_currentHRU = new ArrayList<String>();
			// arraylist containing all dhrus within current HRU
			for (int i = 0; i < HRU_Num.size(); i++) {
				if (j == (int) Double.parseDouble(HRU_Num.get(i))) { // Gives an array containing all the indexes of
																		// dhrus which contribute to current HRU
					DHRUindex_for_currentHRU.add(String.valueOf(i));
				}
			}
			if (DHRUindex_for_currentHRU.size() > maxSize)
				maxSize = DHRUindex_for_currentHRU.size();
			hruIndexList.add(DHRUindex_for_currentHRU);
		} // Format for which the text file will be written
		Formatter outputFile = new Formatter(new FileWriter(file, true));
		// Write out the total number of disaggregated HRUs contained in this file
		outputFile.format("%1$12s%2$12s%n", num_HRUs, maxSize);
		for (int j = 1; j <= num_HRUs; j++) { // iterating over hru number
			ArrayList<String> currentHRU_DHRUindexList = hruIndexList.get(j - 1);
			// format the HRU number as first column and the number of dhrus contributing to
			// this HRU as the second column
			int index = Integer.parseInt(currentHRU_DHRUindexList.get(0));
			int sub_id = subbasin_id.get(index);
			outputFile.format("%1$12s%2$12s%3$12s%n", j, currentHRU_DHRUindexList.size(), sub_id);
			// Print the dhru ids for the grids contributing to the current dhru
			for (int i = 0; i < currentHRU_DHRUindexList.size(); i++) {
				int gridIndex = Integer.parseInt(currentHRU_DHRUindexList.get(i));
				int rowNumber = (int) Double.parseDouble(dhru_Num.get(gridIndex));
				outputFile.format("%1$12s", rowNumber);
			}
			outputFile.format("%n", "");
			// Print Percent areas of each grid contributing to the current dhru
			for (int i = 0; i < currentHRU_DHRUindexList.size(); i++) {
				int gridIndex = Integer.parseInt(currentHRU_DHRUindexList.get(i));
				outputFile.format("%1$12.5f", percent_Area_hru.get(gridIndex));
			}
			outputFile.format("%n", "");
		}
		outputFile.close();// tcw
	}

}
