import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * name:Trish Le 
 * user name: dle 
 * TA: Sam
 * 
 * @author daoxle
 *
 */
public class Board {
	private int[][] blocks;
	private final int size;

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.size = blocks.length;
		this.blocks = copyArray(blocks);

	}

	// board size N
	public int size() {
		return blocks.length;

	}

	// number of blocks out of place
	public int hamming() {
		int countOfOutPlace = 0;
		int numOrder = 1;
		for (int row = 0; row < size(); row++) // search thru the 2D array and
			// check to the order of the
			// number
		{
			for (int col = 0; col < size(); col++) {
				if (blocks[row][col] != numOrder) // if its not correct then
					// increment numOrder by 1
				{
					countOfOutPlace++;

				}
				numOrder++;
			}
			// and we return the count of out of place blocks
		}
		return countOfOutPlace - 1; // subtract 1 for the empty slot

	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int sum = 0;
		int rowGoal, colGoal; 
		int vaule = 0;
		for (int row = 0; row < size(); row++) {
			for (int col = 0; col < size(); col++) {
				vaule = blocks[row][col];
				if (vaule == 0)
					continue; // if the iterator hit the empty space which is 0
				// we just continue
				rowGoal = (vaule - 1) / size();
				colGoal = (vaule - 1) - size()*rowGoal;
				int rowDistance = Math.abs(rowGoal - row); 
				// absolute vaule is
				// need is that the
				// distance is
				// always
				int colDistance = Math.abs(colGoal - col); // positive when we
				// subtract the
				// where we want the block to with where it is at currently

				sum += rowDistance + colDistance;

			}

		}

		return sum;

	}

	// is this board the goal board?
	public boolean isGoal() {
		if (blocks[size - 1][size - 1] != 0) // if the empty slot is not at the
			// end of the array then its not
			// the goal board
			return false;
		int numOrder = 1;
		for (int row = 0; row < size(); row++) {
			for (int col = 0; col < size(); col++) {
				if (row == size()-1 && col == size()-1)
				{
					if (blocks[row][col] == 0)
					{
						continue; 
					}

				}

				if (blocks[row][col] != numOrder) 

					// if the blocks are not in
					// order then it is not the
					// goal board
				{
					return false;
				}
				numOrder++;
			}
		}
		return true;

	}

	// is the board solvable?
	public boolean isSolvable() {

		int i = 0;
		int j = 0;
		int counter = 0;
		int rowZero = 0;
		for (int row = 0; row < size(); row++) {
			for (int col = 0; col < size(); col++) {
				i = blocks[row][col];
				if (i == 0) {
					rowZero = row; // to keep track of the row that zero is in
				}

				for (int row2 = row; row2 < size(); row2++) // to check its
					// neighbors
				{
					int col2;
					if (row2 == row) {	// if we are in the same row then we are making a comparison with the next
						col2 = col + 1;	// block
					} else
						col2 = 0;		// else meaning that we are comparing it in a different row and thus col would be at 
					for (; col2 < size(); col2++) {	// at zero 

						j = blocks[row2][col2];
						if (i == 0 || j == 0) { // if i and j is pointing at the zero then we continue
							continue;			// and iterate until we find a non zero spot to increment the counter
						}

						if (i > j && i != 0) {
							counter++; // to keep track of inversions
						}

					}// end of 4th for loop

				}// end of 3rd for loop

			}// end of 2nd for loop
		}// end if 1st for loop

		if (counter % 2 == 1 && size() % 2 == 1) // if the inversions is odd on
			// a odd number grid
		{ // it is false
			return false;
		} else if ((counter + rowZero) % 2 == 0 && size() % 2 == 0)
			// if the inversion is even plus row zero is in
			// on an even number grid return false
		{

			return false;
		}

		return true; // everything else would be true

	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null) // check for null
			return false;
		if (y.getClass() != this.getClass()) // if object is not in same class
			// then return false
			return false;

		Board that = (Board) y; // cast is guaranteed to succeed
		if (this.blocks.length != that.blocks.length) // if the objects
			// block are not the
			// same length
			// return false
			return false;

		for (int row = 0; row < size(); row++)
			// one step further to check that all the blocks are equal
			if (!Arrays.equals(this.blocks[row], that.blocks[row]))
				return false;

		return true; // and then return true for other cases.
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		Queue<Board> q = new LinkedList<Board>();
		int row = 0;
		int col = 0;
		boolean search = false; // boolean to break out of the for loops when we
		// find the zero

		// find the empty slot to start making the exchanges
		for (row = 0; row < size(); row++) {

			for (col = 0; col < size(); col++) {
				if (blocks[row][col] == 0) {
					search = true;
					break;
				}// end of if

			}// end of 1st while loop

			if (search ==true) {
				break; // break out of the search once we find the zero
			}
		}// end of second while loop

		// exchange the empty slot with the top block
		if (row > 0) {
			Board neighbor = exchange(row, col, row - 1, col);
			q.add(neighbor);
		}
		// exchange the empty slot with the left block
		if (col > 0) { 
			Board neighbor = exchange(row, col, row, col - 1);
			q.add(neighbor);
		}
		// exchange the empty slot with the bottom block
		if (row < size() - 1) {
			Board neighbor = exchange(row, col, row + 1, col);
			q.add(neighbor);

		}
		// exchange the empty slot with the right block
		if (col < size() - 1) {
			Board neighbor = exchange(row, col, row, col + 1);
			q.add(neighbor);
		}

		return q;

	}

	// private method that will make the exchanges of block
	private Board exchange(int oldRow, int oldCol, int newRow, int newCol) {
		int[][] copy = copyArray(blocks);
		copy[oldRow][oldCol] = blocks[newRow][newCol]; // where we make our swap
		copy[newRow][newCol] = 0; // and save where the zero is after the swap
		Board neighbor = new Board(copy); // save the new board
		return neighbor;

	}

	// private method that copy the original array so that when we exchange is
	// does not override
	// the boards but save the original as well so we have as reference
	private int[][] copyArray(int[][] original) {
		int N = original.length;
		int[][] copy = new int[N][N];
		for (int row = 0; row < N; row++) {
			assert original[row].length == N;
			for (int col = 0; col < N; col++)

				copy[row][col] = original[row][col];
		}
		return copy;
	}

	// string representation of the board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(size() + "\n");
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < size(); j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// unit testing
	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board b = new Board(blocks);


		StdOut.println(b);
		StdOut.println();

		StdOut.println("Number of out of place block is " + b.hamming());
		StdOut.println("The sum of distance of out of place blocks is "
				+ b.manhattan());
		StdOut.println("Is solvable: " + b.isSolvable());
		StdOut.println("Is goal: " + b.isGoal());

		Board b2 = new Board(blocks);
		StdOut.println("Is equal: " + b.equals(b2));

		for (Board i : b.neighbors()) {
			StdOut.println(i);
		}

	}

}