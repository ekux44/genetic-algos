package com.kuxhausen.geneticalgos.tsp;

import java.io.FileNotFoundException;
import java.util.Arrays;

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
			//System.out.println("rand fitness "+c.getFitness() +" for length "+cList.numCities);
			
			
			Result[] results = new Result[5];
			if(cf.st == SolverType.HC){
				HillClimber fool = new HillClimber(false);
				
				for(int i = 0; i<results.length; i++)
					results[i] = fool.climb(cf, c, 5000 * cList.numCities, .94, 20, 1.05);
			} else if(cf.st == SolverType.SA){
				HillClimber simA = new HillClimber(true);
				for(int i = 0; i<results.length; i++)
					results[i] = simA.climb(cf, c, 5000 * cList.numCities, .94, 20, 1.05);
			} else if(cf.st == SolverType.GA){
				GeneticAlgo ga = new GeneticAlgo();
				for(int i = 0; i<results.length; i++)
					results[i] = ga.run(cf, cList, .05, .8);
			}
			
			Arrays.sort(results);
			System.out.println("Best Tour Length " + results[0].chrome.getFitness());
			double fitSum = 0;
			for(Result r : results)
				fitSum+=r.chrome.getFitness();
			
			System.out.println("Average Best Tour Length " + (fitSum/results.length));
			
			double genSum = 0;
			for(Result r : results)
				genSum+=r.numGenerations;
			
			System.out.println("Average Num Generations/Perturbations " + (genSum/results.length));
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
