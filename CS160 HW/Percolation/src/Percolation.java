/**
 * Name:Trish Dao Le
 * username: DLe
 */
public class Percolation {
	private int n;
	private int[][] grid;
	private WeightedQuickUnionUF wuf;
	private QuickUnionUF qu;
	private static final int OPEN = 1;

	public Percolation(int N) { // create N-by-N grid, with all sites blocked
		n = N;
		grid = new int[N][N];	// my 2D array
		wuf = new WeightedQuickUnionUF((N * N) + 2); // private helper UF class
		qu = new QuickUnionUF((N * N) + 2);
	}

	public void open(int i, int j) { // open site (row i, column j) if it is not
										// already
		int row = i;
		int col = j;
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IndexOutOfBoundsException("row index " + i
					+ " must be between 0 and " + (n - 1));
		grid[row][col] = OPEN;
		if (row == 0) {								//connect to the virtual top site
			wuf.union(n * n, to1D(row, col));
		}

		if (row == n - 1) {							//connect to the virtual bottom site
			wuf.union((n * n) + 1, to1D(row, col));
		}

		if (row > 0 && grid[row - 1][col] == OPEN) {	// check to make its not out of bound && if its the row is open
			wuf.union(to1D(row, col), to1D(row - 1, col)); // is so we union to its neighbors in the middle  
		}

		if (row < n - 1 && grid[row + 1][col] == OPEN) {	//check to make sure its not out of bound
			wuf.union(to1D(row, col), to1D(row + 1, col));
		}

		if (col > 0 && grid[row][col - 1] == OPEN) {
			wuf.union(to1D(row, col), to1D(row, col - 1));
		}

		if (col < n - 1 && grid[row][col + 1] == OPEN) {
			wuf.union(to1D(row, col), to1D(row, col + 1));
		}
	}

	public boolean isOpen(int i, int j) {	// is site (row i, column j) open?
		int row = i; 
		int col = j;
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IndexOutOfBoundsException(row +"is out of bound" + col );
		return grid[row][col] == OPEN;		//site at i j is open if its value is open 
	}

	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		int row = i;
		int col = j;
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IndexOutOfBoundsException("row index " + i
					+ " must be between 0 and " + (n - 1));
		return wuf.connected(n * n,to1D(row, col));		//return true if site is connected fake top and the first row
		
			
		}
		
	

	public boolean percolates() {					// does the system percolate?
		return wuf.connected(n * n, (n * n) + 1);	// we check if the virtual top is connected to the virtual
	} 												// bottom

	private int to1D(int i, int j) { // my converter method from 2D to 1D array
										
		return (i * n) + j;
	}


	public static void main(String[] args) { // do unit testing of this class
		
	}
}
