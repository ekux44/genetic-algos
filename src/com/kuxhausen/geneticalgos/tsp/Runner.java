package com.kuxhausen.geneticalgos.tsp;

import java.io.FileNotFoundException;

import com.kuxhausen.geneticalgos.tsp.ConfigParser.Config;

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
			
			Config cf = ConfigParser.parse("data/"+"config.txt");
			
			Chromosome c = new Chromosome(cList);
			System.out.println("rand fitness "+c.getFitness() +" for length "+cList.numCities);
			
			
			if(cf.st == SolverType.HC){
				HillClimber fool = new HillClimber(false);
				Chromosome foolishBest = fool.climb(cf, c, 5000 * cList.numCities, .94, 20, 1.05);
				System.out.println("foolish HC fitness "+foolishBest.getFitness() +" for length "+cList.numCities);
			} else if(cf.st == SolverType.SA){
				HillClimber simA = new HillClimber(true);
				Chromosome simuAnnealBest = simA.climb(cf, c, 5000 * cList.numCities, .94, 20, 1.05);
				System.out.println("SA fitness "+simuAnnealBest.getFitness() +" for length "+cList.numCities);
			} else if(cf.st == SolverType.GA){
				GeneticAlgo ga = new GeneticAlgo();
				Chromosome gaBest = ga.run(cf, cList, .05, .8);
				System.out.println("GA fitness "+gaBest.getFitness() +" for length "+cList.numCities);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
