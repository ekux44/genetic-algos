package com.kuxhausen.geneticalgos.tsp;

import java.util.Random;

public class Chromosome {

	/**
	 * a list of city numbers in the order they occur in the route, 0 indexed
	 */
	int[] route;
	
	/*
	 * initializes the route to a random ordering
	 */
	public Chromosome(int length){
		route = new int[length];
		
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
}
