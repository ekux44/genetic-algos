package com.kuxhausen.geneticalgos.tsp;

import com.kuxhausen.geneticalgos.tsp.ConfigParser.Config;

public class HillClimber {
	
	private boolean mSimulatedAnnealing;
	public HillClimber(boolean isSimulatedAnnealingMode){
		mSimulatedAnnealing = isSimulatedAnnealingMode;
	}
	public Result climb(Config cf, Chromosome initialS, double initialTemp, double alpha, double initalNumInnerLoops, double beta){
		long stopTime = System.nanoTime()+1000000000*cf.runTime;
		Chromosome s = initialS;
		double temp = initialTemp;
		double numInnerLoops = initalNumInnerLoops;
		double cachedFitness = s.getFitness();
		int numPermutations = 0;
		
		while(System.nanoTime()<stopTime){
			for(int i = 0; i<numInnerLoops; i++){
				numPermutations++;
				
				Chromosome newS = null;
				if(cf.m == Mutation.TWO_CITY_SHUFFLE){
						s.doublePointShuffle();
				} else if(cf.m == Mutation.THREE_CITY_SHUFFLE){
					s.tripplePointShuffle();
				}
				
				double newFitness = newS.getFitness();
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
		
		return new Result(s, numPermutations);
	}
}
