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
		
			Chromosome c = new Chromosome(cList);
			System.out.println("rand fitness "+c.getFitness() +" for length "+cList.numCities);
			
			/*
			HillClimber fool = new HillClimber(false);
			Chromosome foolishBest = fool.climb(3000000000l, c, 5000 * cList.numCities, .94, 20, 1.05);
			System.out.println("foolish HC fitness "+foolishBest.getFitness() +" for length "+cList.numCities);
			
			HillClimber simA = new HillClimber(true);
			Chromosome simuAnnealBest = simA.climb(3000000000l, c, 5000 * cList.numCities, .94, 20, 1.05);
			System.out.println("SA fitness "+simuAnnealBest.getFitness() +" for length "+cList.numCities);
			*/
			GeneticAlgo ga = new GeneticAlgo();
			Chromosome gaBest = ga.run(3000000000l, cList, Selection.Rank, Mutation.TripplePointShuffle, .05, Crossover.Order1, .8);
			System.out.println("GA fitness "+gaBest.getFitness() +" for length "+cList.numCities);
		
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
