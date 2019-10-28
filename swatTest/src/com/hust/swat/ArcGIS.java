package com.hust.swat;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * * Last Updated: 28-February-2014 * @author Tyler Wible * @since 31-July-2013
 */
public class ArcGIS {
	public static void main(String[] args) throws IOException {
		// Ask user for inputs
		String directory = fetchDirectory();
		int num_Grids = fetchGrids();
		int num_HRUs = fetchHRUs();
		int num_DHRUs = fetchDHRUs();
		String mfRiverFile = fetchMFriverFile();
		int num_RiverGrids = fetchRiverGrids();
		// Set inputs
		String[] inputs = new String[3];
        inputs[0] = directory;
		// //Create grid2dhru file
		inputs[1] = String.valueOf(num_Grids);
		inputs[2] = String.valueOf(num_DHRUs);
		map_grid2dhru.main(inputs);
		// Create dhru2grid file
		inputs[1] = String.valueOf(num_Grids);
		inputs[2] = String.valueOf(num_DHRUs);
		map_dhru2grid.main(inputs);
		// Create dhru2hru file
		inputs[1] = String.valueOf(num_HRUs);
		inputs[2] = String.valueOf(num_DHRUs);
		map_dhru2hru.main(inputs);
		// Create river2grid file
		inputs[1] = mfRiverFile;
		inputs[2] = String.valueOf(num_RiverGrids);
		map_river2grid.main(inputs);
	}

	static String fetchDirectory() {
		String fileString = JOptionPane.showInputDialog(
				"Please enter the file path to the directory where the input files are located. Ex: 'C:/myFolder/myOtherFolder'",
				"Enter file directory");
		return fileString;
	}

	static int fetchGrids() {
		String gridString = JOptionPane.showInputDialog(
				"Please enter the total number (including inactive cells) of MODFLOW grid cells in the MODFLOW model",
				"Enter Number of Grid Cells");
		int gridInt = Integer.parseInt(gridString);
		return gridInt;
	}

	static int fetchHRUs() {
		String hruString = JOptionPane.showInputDialog(
				"Please enter the total number of SWAT Hydrologic Response Units (HRUs) in the SWAT model",
				"Enter Number of HRUs");
		int hruInt = Integer.parseInt(hruString);
		return hruInt;
	}

	static int fetchDHRUs() {
		String dhruString = JOptionPane.showInputDialog(
				"Please enter the total number of Disaggregated-Hydrologic Response Units (DHRUs) in the SWAT/MODFLOW/UZFRT3D model",
				"Enter Number of DHRUs");
		int dhruInt = Integer.parseInt(dhruString);
		return dhruInt;
	}

	static String fetchMFriverFile() {
		String fileString = JOptionPane.showInputDialog(
				"Please enter the name of the river pakage file in the MODFLOW model (ex: 'myMODFLOWFile.riv')",
				"Enter MODFLOW river file name");
		return fileString;
	}

	static int fetchRiverGrids() {
		String dhruString = JOptionPane.showInputDialog(
				"Please enter the total number of MODFLOW river grid cells in the MODFLOW model",
				"Enter Number of river grid cells");
		int dhruInt = Integer.parseInt(dhruString);
		return dhruInt;
	}
}
