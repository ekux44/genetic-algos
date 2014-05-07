package com.kuxhausen.geneticalgos.tsp;

public class CityList {

	public int numCities;
	public double[] cityX;
	public double[] cityY;

	/**
	 * @param Chromosome whose fitness is to be calculated
	 * @return fitness based on total distanced in Chromosome's routes
	 */
	public double getFitness(Chromosome c){
		double distanceTraveled = 0;
		for(int i = 1; i< numCities; i++){
			int a = c.route[i-1];
			int b = c.route[i];
			distanceTraveled+= Math.sqrt(Math.pow(cityX[b]-cityX[a], 2)+Math.pow(cityY[b]-cityY[a], 2));
		}
		return distanceTraveled;
	}
}
