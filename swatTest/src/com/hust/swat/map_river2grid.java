package com.hust.swat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class map_river2grid {

	// Format of outout: tcw
	// 57 (total # of river cells in MODFLOW)
	// 36.2 591.25 299.04 350.12 (list of thickness of lengths of river segments of
	// grid cell)
	// 1 4 (grid#) (# of river segments contributing to this grid)
	// rivr2_ID rivr12_ID rivr31_ID rivr37_ID (list of river id #s which are in this
	// grid cell)
	// 36.2 591.25 299.04 350.12 (list of lengths of river segment in this grid
	// cell)

	public static void main(String[] args) throws IOException { // Inputs
		String directory = args[0];
		String mfRiverFile = args[1];
		int num_RiverGrids = Integer.parseInt(args[2]);
		System.out.println("Creating map_river2grid.txt...");
		writeResults(directory, mfRiverFile, num_RiverGrids);
		System.out.println("Done");
	}

	public static String columnRow(String column_row, boolean columnTrue) { // Convers the strin Col - row into
		String[] array = column_row.substring(1, column_row.length() - 1).split("-");
		String column = array[0].trim();
		String row = array[1].trim();
		String returnValue = row;
		if (columnTrue) {
			returnValue = column;
		}
		return returnValue;
	}

	public static String[][] readRiverGridFile(String fileLocation) throws IOException {
		// Open a reader for the results file
		String path = fileLocation + "/rivergrid.csv";
		FileReader file_to_read = new FileReader(path);
		BufferedReader reader = new BufferedReader(file_to_read);
		// Read out some of the contents of the input file
		ArrayList<String> data = new ArrayList<String>();
		String currentLine = "";
		while ((currentLine = reader.readLine()) != null) {
			data.add(currentLine);
		}
		reader.close();
		file_to_read.close();
		// Populate data
		String[][] resultArray = new String[data.size() - 1][4];
		int index_col_row = 0;
		int index_riv_id = 0;
		int index_sub_id = 0;
		int index_riv_len = 0;
		for (int i = 0; i < data.size(); i++) {
			// Loop rows of data
			String[] currentRow = data.get(i).split(",");
			if (i == 0) {
				for (int j = 0; j < currentRow.length; j++) {
					currentRow[j] = currentRow[j].replace('"', '%');
					currentRow[j] = currentRow[j].replaceAll("%", "");
					if (currentRow[j].equalsIgnoreCase("Col_Row")) {
						index_col_row = j;
					} else if (currentRow[j].equalsIgnoreCase("riv_id")) {
						index_riv_id = j;
					} else if (currentRow[j].equalsIgnoreCase("Subbasin")) {
						index_sub_id = j;
					} else if (currentRow[j].equalsIgnoreCase("rgrid_len")) {
						index_riv_len = j;
					}
				}
				// Check for missing information
				if (index_col_row == 0) {
					throw new IOException("Error encountered, there is no attribute column 'Col_Row' in rivergrid.csv");
				}
				if (index_riv_id == 0) {
					throw new IOException("Error encountered, there is no attribute column 'riv_id' in rivergrid.csv");
				}
				if (index_sub_id == 0) {
					throw new IOException(
							"Error encountered, there is no attribute column 'Subbasin' in rivergrid.csv");
				}
				if (index_riv_len == 0) {
					throw new IOException(
							"Error encountered, there is no attribute column 'rgrid_len' in rivergrid.csv");
				}
			} else {
				// Keep desired information
				resultArray[i - 1][0] = currentRow[index_riv_id];
				// riverID#
				resultArray[i - 1][1] = currentRow[index_riv_len];
				// lengthOfRiver in grid#
				resultArray[i - 1][2] = columnRow(currentRow[index_col_row], true);
				// grid column#
				resultArray[i - 1][3] = columnRow(currentRow[index_col_row], false);
				// grid row#
			}
		}
		return resultArray;
	}

	/**
	 * * read the MODLFOW river package file to get the order/info. about the river
	 * grid cells so the right * SWAT river information is passed to the correct
	 * MODFLOW river grid cell * @param filePath the full file path to the river
	 * package file (ex. C:/tempFolder/myRivepackage.txt) * @param num_RiverGrids
	 * the total number of MODFLOW river grids in the model * @return selected
	 * contents of the river package as a String[][] array * @throws IOException
	 */
	public static int[][] readMODFLOWrivFile(String filePath, int num_RiverGrids) throws IOException {
		// intialize scanner (reader)
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		// Read in header information information
		boolean readNextLine = true;
		while (readNextLine) {
			String currentLine = scanner.nextLine();
			try {
				if (currentLine.substring(0, 1).equals("#")) {
					readNextLine = true;
				} else {
					currentLine = scanner.nextLine();
					readNextLine = false;
				}
			} catch (IndexOutOfBoundsException e) {
				currentLine = scanner.nextLine();
				readNextLine = false;
			}
		}
		// Populate data
		int[][] resultArray = new int[num_RiverGrids][3];
		for (int i = 1; i <= num_RiverGrids; i++) {// Loop for number of river cells
			String[] columns = scanner.nextLine().split("\\s+");
			// Keep desired information
			resultArray[i - 1][0] = i; // river grid #
			resultArray[i - 1][1] = Integer.parseInt(columns[2]); // grid row#
			resultArray[i - 1][2] = Integer.parseInt(columns[3]); // grid column#
		}
		return resultArray;
	}

	/** * * @param fileLocation * @param subbasinMax * @throws IOException */
	public static void writeResults(String fileLocation, String mfRiverFile, int num_RiverGrids) throws IOException {
		// Read River/grid Attribute table file
		String[][] riverGridData = readRiverGridFile(fileLocation);
		// Read MODFLOW .riv file
		int[][] mfRiverData = readMODFLOWrivFile(fileLocation + "/" + mfRiverFile, num_RiverGrids);
		// Delete the old map_rive2grid.txt if it exists
		String path = fileLocation + "/map_river2grid.txt";
		File file = new File(path);
		boolean fileTF = file.isFile();
		if (fileTF) {
			boolean fileDeleteTF = file.delete();
			if (!fileDeleteTF) {
				System.out.println("The file (" + path + ") could not be deleted");
			}
		}
		// Format for which the text file will be written
		Formatter riverFile = new Formatter(new FileWriter(file, true));
		riverFile.format("%1$12s%n", num_RiverGrids);
		// Write the total number of MODFLOW grids for dimensioning purposes
		for (int j = 1; j <= num_RiverGrids; j++) { // iterating over river grid number
			System.out.println(j);
			int mfRow = mfRiverData[j - 1][1];
			int mfColumn = mfRiverData[j - 1][2];
			// Find all positions containing current grid number
			ArrayList<Integer> index_for_river_Num_for_Current_grid_Num = new ArrayList<Integer>(); // arraylist
																									// containing all
																									// river within
																									// current grid
																									// number
			for (int i = 0; i < riverGridData.length; i++) {
				int row = (int) Double.parseDouble(riverGridData[i][3]);
				int column = (int) Double.parseDouble(riverGridData[i][2]);
				if (mfRow == row && mfColumn == column) {
					// Gives me an array containing all positions of rivers which contribute to
					// current grid
					index_for_river_Num_for_Current_grid_Num.add(i);
				}
			}
			riverFile.format("%1$12s%2$12s%n", j, index_for_river_Num_for_Current_grid_Num.size());
			// format the grid number as first column and the number of rivers contributing
			// to this as the second column
			// Print ID of rivers contributing to the current grid
			for (int i = 0; i < index_for_river_Num_for_Current_grid_Num.size(); i++) {
				int riverIndex = index_for_river_Num_for_Current_grid_Num.get(i);
				int riverNumber = (int) Double.parseDouble(riverGridData[riverIndex][0]);
				riverFile.format("%1$12s", riverNumber);
			}
			riverFile.format("%n", "");
			// Print length of river within current grid
			for (int i = 0; i < index_for_river_Num_for_Current_grid_Num.size(); i++) {
				int riverIndex = index_for_river_Num_for_Current_grid_Num.get(i);
				double riverLength = Double.parseDouble(riverGridData[riverIndex][1]);
				riverFile.format("%1$12.5f", riverLength);
			}
			riverFile.format("%n", "");
			index_for_river_Num_for_Current_grid_Num.clear();
		}
		riverFile.close();// tcw
	}
}
