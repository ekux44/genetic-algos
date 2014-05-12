package com.kuxhausen.geneticalgos.tsp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ConfigParser {

	public static Config parse(String filename) throws FileNotFoundException{
		Scanner in = new Scanner(new FileReader(filename));
		Config result = new Config();
		result.runTime = in.nextLong();
		in.nextLine();
		result.st = SolverType.valueOf(in.nextLine());
		result.m = Mutation.valueOf(in.nextLine());
		result.s = Selection.valueOf(in.nextLine());
		result.c = Crossover.valueOf(in.nextLine());
		return result;
	}

	public static class Config{
		/** in seconds **/
		long runTime;
		SolverType st;
		Mutation m;
		Selection s;
		Crossover c;
	}
}
