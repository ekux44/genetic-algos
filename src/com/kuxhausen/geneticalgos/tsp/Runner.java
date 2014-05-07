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
		
			for(int i = 0; i< 10; i++){
				Chromosome c = new Chromosome(cList.numCities);
				System.out.println("rand fitness "+cList.getFitness(c) +" for length "+cList.numCities);
			}
		
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
