package com.kuxhausen.geneticalgos.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneticAlgo {
	
	public static final int POP_SIZE = 400;
	public static final int NUM_ELITE = 4;
	
	public Chromosome run(long timeAllocated, CityList cl, Selection s, Mutation m, double mutateRate, Crossover c, double crossoverRate){
		long stopTime = System.nanoTime()+timeAllocated;
		
		ArrayList<Chromosome> population = new ArrayList<Chromosome>(POP_SIZE);
		
		for(int i = 0; i< POP_SIZE; i++){
			population.add(new Chromosome(cl));
		}
		
		//sort the population by fitness before beginning to evolve it
		Collections.sort(population);
		
		while(System.nanoTime()<stopTime){
			
			//Perform selection
			ArrayList<Chromosome> selected = null;
			if(s == Selection.Roulette){
				selected = rouletteSelection(population, POP_SIZE-NUM_ELITE);
			} else if(s == Selection.Rank){
				selected = rankSelection(population, POP_SIZE-NUM_ELITE);
			}
			
			//Reproduce (Crossover)
			ArrayList<Chromosome> children = new ArrayList<Chromosome>(selected.size());
			for(int i = 0; i<selected.size(); i+=2){
				
				if(Math.random()<=crossoverRate){
					if(c == Crossover.PMX){
						children.add(this.pmxCrossover(selected.get(i), selected.get(i+1)));
						children.add(this.pmxCrossover(selected.get(i+1), selected.get(i)));
					} else if(c == Crossover.Order1){
						children.add(this.order1Crossover(selected.get(i), selected.get(i+1)));
						children.add(this.order1Crossover(selected.get(i+1), selected.get(i)));
					}
					
				} else{
					children.add(selected.get(i));
					children.add(selected.get(i+1));
				}
			}
			
			//Mutate the children
			for(Chromosome child : children){
				if(Math.random()<=mutateRate){
					if(m == Mutation.DoublePointShuffle){
						child.doublePointShuffle();
					} else if(m == Mutation.TripplePointShuffle){
						child.tripplePointShuffle();
					}
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
			
			//print out generational best for debugging/tuning purposes
			System.out.println(population.get(0).getFitness());
		}
		
		return population.get(0);
	}
	
	/**
	 * @param parent sorted most fit to least fit
	 * @param numToSelect
	 * @return selected Chromosomes, unsorted and with duplicates
	 */
	private ArrayList<Chromosome> rouletteSelection(ArrayList<Chromosome> parent, int numToSelect){
		double fitnessSum = 0;
		for(Chromosome c : parent){
			fitnessSum += c.getFitness();
		}
		
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>(numToSelect);
		//loop to fill the selected list
		for(int i = 0; i< numToSelect; i++){
			//the roulette wheel is the size of all combined chromosome fitnesses

			//scan through the parent chromosomes summing fitnesses until the random roulette wheel spot is reached
			double rouletteSpot = Math.random()*fitnessSum;
			double scannedSoFar = 0;
			for(int j = 0; scannedSoFar<rouletteSpot; j++){
				scannedSoFar+=parent.get(j).getFitness();
				if(scannedSoFar>=rouletteSpot){
					//this one selected on the roulette wheel, add to selected
					selected.add(parent.get(j));
				}
					
			}
		}
		return selected;
	}
	
	/**
	 * @param parent sorted most fit to least fit
	 * @param numToSelect
	 * @return selected Chromosomes, unsorted and with duplicates
	 */
	private ArrayList<Chromosome> rankSelection(ArrayList<Chromosome> parent, int numToSelect){
		double totalRank = parent.size()*(parent.size()+1)/2.0;
		
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>(numToSelect);
		//loop to fill the selected list
		for(int i = 0; i< numToSelect; i++){
			//the roulette wheel is the size of all combined rank
			
			//scan through the parent chromosomes summing rank until the random ranked roulette wheel spot is reached
			double rouletteSpot = Math.random()*totalRank;
			double scannedSoFar = 0;
			for(int j = 0; scannedSoFar<rouletteSpot; j++){
				scannedSoFar+=parent.size()-j;
				if(scannedSoFar>=rouletteSpot){
					//this one selected on the ranked roulette wheel, add to selected
					selected.add(parent.get(j));
				}
			}
		}
		return selected;
	}
	
	
	public Chromosome pmxCrossover(Chromosome p1, Chromosome p2){
		Chromosome child = new Chromosome(p1.route, p1.getCityList());
		
		//randomly generate pmx crossover start point
		int pmxStart = (int) (Math.random()*(child.route.length-2));
		
		//use a 3-wide pmx crossover region to generate a mapping for swapping cities in the child
		for(int i = 0; i< 3; i++){
			int p1MappedValue = child.route[pmxStart+i];
			int p2MappedValue = p2.route[pmxStart+i];
			
			//search for p2Value in child then swap with with p1Value
			int p2ValInChildLoc = 0;
			for(int j = 0; j< child.route.length; j++){
				if(child.route[j] == p2MappedValue){
					p2ValInChildLoc = j;
				}
			}
			
			child.route[p2ValInChildLoc] = p1MappedValue;
			child.route[pmxStart+i] = p2MappedValue;
		}
		child.invalidateCache();
		
		//System.out.println(p1.getFitness() + " " + child.getFitness());
		return child;
	}
	
	
	public Chromosome order1Crossover(Chromosome p1, Chromosome p2){
		Chromosome child = new Chromosome(p1.route, p1.getCityList());
		
		//randomly generate basis crossover start point
		int basisStart = (int) (Math.random()*(child.route.length/2));
		
		//use a half-length section of p1 as basis, 
		int[] basis = new int[child.route.length/2];
		
		//insert the basis at the beginning of child, 
		for(int i = 0; i< basis.length; i++){
			basis[i] = p1.route[basisStart+i];
			child.route[i] = basis[i];
		}
		
		//now sort the basis for efficient search 
		Arrays.sort(basis);
		
		//then insert the remaining cities as based on their ordering in p2
		int childIndex = basis.length;
		for(int j = 0; j< p2.route.length; j++){
			if(Arrays.binarySearch(basis, p2.route[j])<0){
				child.route[childIndex] = p2.route[j];
				childIndex++;
			}
		}
		
		child.invalidateCache();
		
		//System.out.println(p1.getFitness() + " " + child.getFitness());
		return child;
	}
	
}
