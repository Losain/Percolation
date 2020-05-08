package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Nathan Clawson
 * @author natec
 *
 */
public class PercolationStats
{
	//create a field that is an array for calculating the mean.
	double[] meanArry;
	double numExperiments;
	/**perform T number of independent experiments on an N-by-N grid.
	 * 
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T)
	{
		int i = 0; // create a local variable for tracking purposes. 
		//throw an exception if the value of N or T is <= 0.
		if(N <= 0 || T <= 0) throw new IllegalArgumentException("N size of grid must be bigger than 0 and T number of experiments must be greater than 0.");
		meanArry = new double[T];
		numExperiments = T;
		//Do the thing T number of times. 
		while (i < T)
		{
			//create a new percolation object using the N that's been passed in for the construction. 
			Percolation perc = new Percolation(N);
			
			//while perc is not percolating 
			 while (perc.percolates() == false)
			{
				//generate two random numbers and open that space.
				int one = StdRandom.uniform(N);
				int two = StdRandom.uniform(N);
				perc.open(one, two);
			}
			meanArry[i] = Double.parseDouble(perc.numberOfOpenSites()) / (N*N); //supply the average 
			i++;
		}
	}
	
	/**
	 * sample mean of the percolation threshold.
	 * @return
	 */
	public double mean()
	{
		return StdStats.mean(meanArry);
	}
	
	/**
	 * sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev()
	{
		return StdStats.stddev(meanArry);
	}
	
	/**
	 * low endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceLow()
	{
		double results = StdStats.mean(meanArry) - ((1.96 * StdStats.stddev(meanArry)) / Math.sqrt(numExperiments));
		return results;
	}
	
	/**
	 * high endpoint of 95% confidence interval
	 * @return
	 */
	public double confidenceHigh()
	{
		double results = StdStats.mean(meanArry) + ((1.96 * StdStats.stddev(meanArry)) / Math.sqrt(numExperiments));
		return results;
	}
	
	
	public static void main(String[] args)
	{
		PercolationStats ps2 = new PercolationStats(200, 100);
		System.out.println("Mean: "+ ps2.mean());
		System.out.println("Standard Deviation: " + ps2.stddev());
		System.out.println("Conf. Low: " + ps2.confidenceLow());
		System.out.println("Conf. High: " + ps2.confidenceHigh());
		System.out.println();
		
		PercolationStats ps3 = new PercolationStats(200, 100);
		System.out.println("Mean: "+ps3.mean());
		System.out.println("Standard Deviation: " + ps3.stddev());
		System.out.println("Conf. Low: " + ps3.confidenceLow());
		System.out.println("Conf. High: " +ps3.confidenceHigh());
		System.out.println();
		
		PercolationStats ps1 = new PercolationStats(2, 100000);
		System.out.println("Mean: "+ps1.mean());
		System.out.println("Standard Deviation: " + ps1.stddev());
		System.out.println("Conf. Low: " + ps1.confidenceLow());
		System.out.println("Conf. High: " +ps1.confidenceHigh());
	}
}
