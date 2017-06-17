/**
 * Name:Trish Dao Le
 * username: DLe
 */
public class PercolationStats {
	private int exper;
	private Percolation perc;
	private double[] results;

	/**
	 * perform T independent computational experiments on an N-by-N grid
	 * 
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException();	// throw an exception if out of bound
		}
		int open = 0;

		exper = T;
		results = new double[exper]; // we declare and initialize a different
										// array that will store
		for (int n = 0; n < exper; n++) { // each T results and we can compare
											// to compute each T
			perc = new Percolation(N);
			open = 0; // keeping track of open sites
			while (!perc.percolates()) {
				int i = StdRandom.uniform(0, N);
				int j = StdRandom.uniform(0, N);
				if (!perc.isOpen(i, j)) { // the site randomly produced is open
											// we open it
					perc.open(i, j);
					open++;
				}
			}
			double fraction = (double) open / (N * N); // this give us the
														// fraction of site open										
			results[n] = fraction;						// over site not open
		}

	}

	/**
	 * // sample mean of percolation threshold
	 */
	public double mean() {					// it compute the mean from out results array that store all of the 
											// T experiments
		return StdStats.mean(results);

	}

	/**
	 * // sample standard deviation of percolation // threshold
	 * 
	 * @return
	 */
	public double stddev() {

		if (results.length <= 1)
			return Double.NaN; // when T equal to 1 the stddev is undefined we
								// return a primitive type
		return StdStats.stddev(results);
	}

	/**
	 * // low end of 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceLow() { // math provided on the HW page

		return mean() - ((1.96 * stddev()) / Math.sqrt(exper));

	}

	/**
	 * // high end of 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceHigh() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(exper));
	}
	//Unit testing 
	public static void main(String[] args) {
		
		Stopwatch stopwatch = new Stopwatch();
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
	
		PercolationStats ps = new PercolationStats(N, T);

		StdOut.println("mean                    = " + ps.mean());
		StdOut.println("stddev                  = " + ps.stddev());
		StdOut.println("Low confidence          = " + ps.confidenceLow());
		StdOut.println("High confidence         = " + ps.confidenceHigh());
	
		StdOut.println("Time in seconds         = " + stopwatch.elapsedTime());
		
	}
}
