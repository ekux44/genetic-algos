package com.kuxhausen.geneticalgos.tsp;

public class HillClimber {
	
	private boolean mSimulatedAnnealing;
	public HillClimber(boolean isSimulatedAnnealingMode){
		mSimulatedAnnealing = isSimulatedAnnealingMode;
	}
	public Chromosome climb(long timeAllocated, Chromosome initialS, CityList cl,  double initialTemp, double alpha, double initalNumInnerLoops, double beta){
		long stopTime = System.nanoTime()+timeAllocated;
		Chromosome s = initialS;
		double temp = initialTemp;
		double numInnerLoops = initalNumInnerLoops;
		double cachedFitness = cl.getFitness(s);
		
		while(System.nanoTime()<stopTime){
			for(int i = 0; i<numInnerLoops; i++){
				Chromosome newS = s.doublePointChrossover();
				double newFitness = cl.getFitness(newS);
				if(!mSimulatedAnnealing
						|| newFitness<cachedFitness
						|| Math.random()<Math.pow(Math.E, (cachedFitness - newFitness)/temp)){
					s = newS;
					cachedFitness = newFitness;
				}
			}
			temp*=alpha;
			numInnerLoops*= beta;
		}
		
		return s;
	}
}
