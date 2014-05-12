package com.kuxhausen.geneticalgos.tsp;

import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgo {
	
	public static final int POP_SIZE = 400;
	public static final int NUM_ELITE = 4;
	
	public Chromosome run(long timeAllocated, Chromosome initialS, CityList cl, double mutateRate, double crossoverRate){
		long stopTime = System.nanoTime()+timeAllocated;
		
		double initialFitness = initialS.getFitness();
		
		ArrayList<Chromosome> population = new ArrayList<Chromosome>(POP_SIZE);
		
		for(int i = 0; i< POP_SIZE; i++){
			population.add(new Chromosome(initialS.route, cl));
		}
		
		//sort the population by fitness before beginning to evolve it
		Collections.sort(population);
		
		while(System.nanoTime()<stopTime){
			
			//Perform selection
			ArrayList<Chromosome> selected = rouletteSelection(population, POP_SIZE-NUM_ELITE);
			
			//Reproduce (Crossover)
			ArrayList<Chromosome> children = selected;
			
			//Mutate the children
			for(Chromosome c : children){
				if(Math.random()>mutateRate){
					c.doublePointChrossover();
				}
			}
			
			
			/*
			 * Add children to population, replacing all but the elite
			 */
			for(int i = 0; i< children.size(); i++){
				population.set(i+NUM_ELITE,children.get(i)); 
			}
			
			//resort the population at the end of each generation 
			Collections.sort(population);
		}
		
		return population.get(0);
	}
	
	/**
	 * 
	 * @param parrent Chromosome
	 * @return arrayList with duplicates of those selected from parent
	 */
	private ArrayList<Chromosome> rouletteSelection(ArrayList<Chromosome> parent, int numToSelect){
		double fitnessSum = 0;
		for(Chromosome c : parent){
			fitnessSum += c.getFitness();
		}
		
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>(numToSelect);
		//loop to fill the selected list
		for(int i = 0; i< selected.size(); i++){
			//the roulette wheel is the size of all combined chromosome fitnesses

			//scan through the parent chromosomes summing fitnesses until the random roulette wheel spot is reached
			double rouletteSpot = Math.random()*fitnessSum;
			double scannedSoFar = 0;
			for(int j = 0; scannedSoFar<rouletteSpot; j++){
				scannedSoFar+=parent.get(j).getFitness();
				if(scannedSoFar>=rouletteSpot){
					//this one selected on the roulette wheel, add to selected
					selected.set(i, parent.get(j));
				}
					
			}
		}
		return selected;
	}
	
	private Chromosome[] rankSelection(){
		//TOOD implement
		
		return null;
	}
	
}
