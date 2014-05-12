package com.kuxhausen.geneticalgos.tsp;

public class Result implements Comparable<Result>{

	Chromosome chrome;
	int numGenerations;

	public Result(Chromosome c, int nGen){
		chrome = c;
		numGenerations = nGen;
	}

	@Override
	public int compareTo(Result o) {
		return this.chrome.compareTo(o.chrome);
	}
}
