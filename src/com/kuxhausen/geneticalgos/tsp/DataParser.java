package com.kuxhausen.geneticalgos.tsp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class DataParser {
	public static CityList parse(String filename) throws FileNotFoundException{
		Scanner in = new Scanner(new FileReader(filename));
		
		in.nextLine();
		in.nextLine();
		in.nextLine();
		in.nextLine();
		in.next();
		in.next();
		int numCities = in.nextInt();
		in.nextLine();
		in.nextLine();
		in.nextLine();
		
		double[] x = new double[numCities];
		double[] y = new double[numCities];
		
		for(int i = 0; i<numCities; i++){
			in.next();
			x[i] = in.nextDouble();
			y[i] = in.nextDouble();
		}
		
		in.close();
		
		
		CityList cl = new CityList();
		cl.numCities = numCities;
		cl.cityX = x;
		cl.cityY = y;
		return cl;
	}
}
