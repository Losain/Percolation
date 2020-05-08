package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	
	/**
	 * Nathan Clawson CSIS 2420 - 403 A01 Percolation assignment
	 * @author natec
	 *
	 */
	private static boolean[][] percArray;//create a 2d boolean array 
	private int size;//keep track of the size that was entered to create the array.
	private int ufSize = size*2+2; //double the size for the weight UF object. add 2 for the virtual top and the virtual bottom. 
	private int numOfOpenSites = 0;
	WeightedQuickUnionUF uf;

	/**
	 * Constructor, takes and int and creates an n by n grid with all sites blocked.
	 * Then sets the size field to N for later use. 
	 * @param N
	 */
	public Percolation(int N)
	{
		if(N <= 0) throw new IllegalArgumentException("2D Array must be larger than 0.");
		
		percArray = new boolean[N][N];
		size = N;
		ufSize = size*size+2;
		uf = new WeightedQuickUnionUF(ufSize);//weighted UF object. Not sure if I should be calling the methods statically or not?
		//note that virtual top is at n-2 and virtual bottom is at n-1. Is this even right?
		
		//need to create union calls to the whole top of the grid and the whole bottom of the grid. 
		//that is 0 - n-1 and for the 2d array. 
		for (int i = 0 ; i < N; i++)
		{
		uf.union(twoDto1d(0, i), ufSize-2);
		}
		
		//need to connect the bottom row to the virtual bottom.
		for (int i = 0 ; i < N; i++)
		{
		uf.union(twoDto1d(N-1 , i), ufSize-1);
		}
		
	}

	/**
	 * open site (row i column j) if it is not open already. Checks sites in 4 basic directions (up, down, left, right) to see if
	 * those sites are open, if this is the case it unions to those sites as well. 
	 * @param i
	 * @param j
	 */
	public void open(int i, int j)
	{
		//validate coordinates given.
		validateCoord(i, j);
		//check to see if the spot is available to open, and open it. 
		if(!isOpen(i,j)) 
			{
			percArray[i][j] = true;
			numOfOpenSites++;
			}
		//union queries to check all sites around the newly open site. These also check to see if the sight is open and if true unions to the triggering site.. 
		if (i > 0) if(isOpen((i-1) , j)) uf.union(twoDto1d(i, j), twoDto1d(i-1, j));
		if (i < percArray.length -1) if(isOpen((i+1) , j)) uf.union(twoDto1d(i, j), twoDto1d(i+1, j));
		if (j > 0) if(isOpen(i , (j-1))) uf.union(twoDto1d(i, j), twoDto1d(i, j-1));
		if (j < percArray.length -1) if(isOpen(i , (j+1))) uf.union(twoDto1d(i, j), twoDto1d(i, j+1));
	}
	

	/**
	 * is site (row i, col j) open?
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j)
	{
		//validate the coordinates given, note this may no longer be needed if validation is done in other methods. 
		validateCoord(i, j);
		
		//return the boolean result in the array. I THINK i can rewrite this to be return (percArray[i][j]); 
		//which would return the false or true values stored there.
		if(percArray[i][j]==false)return false;
		return true;
	}

	/**
	 * is site (row i, col j) full? 
	 * A site is full when it's connected to the virtual top. Note that this is where the backwash is going to be represented
	 * Instructions suggest not to be too worried about the backwash.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j)
	{
		//here we need to say that everything connected to the virtual top is full. 
		//validate coordinates
		validateCoord(i,j);
		//if location provided is connected to virtual top it returns true, else returns false. 
		if(uf.connected(twoDto1d(i, j), ufSize - 2) && isOpen(i, j)) return true;
		else return false;
	}

	/**
	 * does the system percolate?
	 * 
	 * @return
	 */
	public boolean percolates()
	{
		if(uf.connected(ufSize-2, ufSize-1))
		{
			return true;
		}
		else 
			{
			return false;
			}
	}
	
	/**
	 * this method takes a 2 dimensional indicies and returns a 1 dimension number. 
	 * @param i
	 * @param j
	 * @return
	 */
	private int twoDto1d(int i, int j)
	{
		int result = (i * size) + j;
		return result;
	}
	
	/**
	 * Validates coordinates entered. If they go off the sides of the grid then it throws an exception.
	 * @param i 
	 * @param j
	 */
	private void validateCoord(int i, int j)
	{
		if(i < 0 || i >= percArray.length && j < 0 || j >= percArray.length) throw new IndexOutOfBoundsException("row index " + Integer.toString(i) 
		+ " must be between 0 and " + Integer.toString(percArray.length-1));
	}

	public String numberOfOpenSites()
	{
		//can't have a for loop, those are too slow. simplest answer is to have a field variable that is incremented when a new site is opened. 
		return Integer.toString(numOfOpenSites);
	}

}