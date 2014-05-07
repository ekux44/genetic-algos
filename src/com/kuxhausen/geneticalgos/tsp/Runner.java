package com.kuxhausen.geneticalgos.tsp;

import java.io.FileNotFoundException;

public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<1 || args[0] == null){
			System.out.println("Error: Missing Filename");
			return;
		}
		
		String filename = "data/"+args[0];
		System.out.println("Loading "+filename);
		
		try {
			CityList cList = DataParser.parse(filename);
		
			
			for(int i = 0 ; i< cList.numCities; i++){
				System.out.println(i+" "+cList.cityX[i]+" "+cList.cityY[i]);
			}
		
		
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
