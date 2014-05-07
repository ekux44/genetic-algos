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
		
			Chromosome c = new Chromosome(cList.numCities);
			System.out.println("rand fitness "+cList.getFitness(c) +" for length "+cList.numCities);
			
			
			HillClimber fool = new HillClimber(false);
			Chromosome foolishBest = fool.climb(1000000000l, c, cList, 50000, .95, 20, 1.05);
			System.out.println("foolish HC fitness "+cList.getFitness(foolishBest) +" for length "+cList.numCities);
			
			HillClimber simA = new HillClimber(true);
			Chromosome simuAnnealBest = simA.climb(1000000000l, c, cList, 50000, .95, 20, 1.05);
			System.out.println("SA fitness "+cList.getFitness(simuAnnealBest) +" for length "+cList.numCities);
		
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
