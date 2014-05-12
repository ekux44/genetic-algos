package com.kuxhausen.geneticalgos.tsp;

import java.util.Random;

public class Chromosome implements Comparable<Chromosome>{

	/**
	 * a list of city numbers in the order they occur in the route, 0 indexed
	 */
	int[] route;
	
	private CityList cl;
	private double fitnessCache;
	private boolean fitnessCacheValidity;
	
	/*
	 * initializes the route to a random ordering
	 */
	public Chromosome(int length, CityList cList){
		route = new int[length];
		cl = cList;
		
		// Initially populate with known city ordering
		for(int i = 0; i< length; i++)
			route[i] = i;
		
		// Fisher–Yates shuffle to randomize the route
		Random rnd = new Random();
	    for (int i = route.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = route[index];
	      route[index] = route[i];
	      route[i] = a;
	    }
	}
	
	/**
	 * initializes the route with prior route
	 * @param priorRoute
	 */
	public Chromosome(int[] priorRoute, CityList cList){
		route = priorRoute.clone();
		cl = cList;
	}
	
	public Chromosome doublePointChrossover(){
		Chromosome child = new Chromosome(route, cl);
		int a = (int)(Math.random() * child.route.length);
		int b = (int)(Math.random() * child.route.length);
		
		int swap = child.route[a];
		child.route[a] = child.route[b];
		child.route[b] = swap;
		return child;
	}
	
	public double getFitness(){
		if(!fitnessCacheValidity){
			fitnessCache = calculateFitness();
		}
		return fitnessCache;
	}
	
	public void invalidateCache(){
		fitnessCacheValidity = false;
	}
	
	/**
	 * @param Chromosome whose fitness is to be calculated
	 * @return fitness based on total distanced in Chromosome's routes
	 */
	private double calculateFitness(){
		double distanceTraveled = 0;
		for(int i = 1; i< cl.numCities; i++){
			int a = route[i-1];
			int b = route[i];
			distanceTraveled+= Math.sqrt(Math.pow(cl.cityX[b]-cl.cityX[a], 2)+Math.pow(cl.cityY[b]-cl.cityY[a], 2));
		}
		return distanceTraveled;
	}

	@Override
	public int compareTo(Chromosome o) {
		return ((Double)this.getFitness()).compareTo((Double)o.getFitness());
	}
}
