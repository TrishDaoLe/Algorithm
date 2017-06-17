import java.util.Comparator;

/**
 * name:Trish Le 
 * user name: dle
 *  TA: Sam
 * 
 * @author daoxle
 *
 */

public class Solver {
	private SearchNode result;
//private class for search node
	private class SearchNode {
		private final Board board;
		private int moves;
		private SearchNode previous;

		public SearchNode(Board b) {
			board = b;
			previous = null;
			moves = 0;

		}
	}
	// private class that compare the searchNode board to each other 
	private class Compare implements Comparator<SearchNode> {
		public int compare(SearchNode search1, SearchNode search2) {
			int SV1 = search1.board.manhattan() + search1.moves; 
			int SV2 = search2.board.manhattan() + search2.moves;

			if (SV1 < SV2) {
				return -1;

			}
			if (SV1 > SV2) {
				return 1;
			} 
			else
				return 0;

		}

	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		// if the first board is the goal than return that node
		if (initial.isGoal()) {
			result = new SearchNode(initial);
			return;
		}// if the board is not solvable that return null;
		if (!initial.isSolvable()) {
			result = null;
			return;
		}
		// else make result equal to the private solve method that will solve it
		result = solve(initial);

	}

	private SearchNode solve(Board initial) 
	{	// Initializing my minPQ
		MinPQ<SearchNode> minpq = new MinPQ<SearchNode>(new Compare());
		
		minpq.insert(new SearchNode(initial)); // insert the initial node the root
												

		SearchNode current = minpq.delMin();	// delete the min and set it to our current node
		while (!current.board.isGoal())
		{ // while the current board is not our goal board then do the iterate thru its neighbors
			
			for (Board neighbor : current.board.neighbors())
				// for each board neighbor of the current node
			{ // add the negihbors to the minpq
				// del the min and set that to be the current node
				if (current.previous == null || !neighbor.equals(current.previous.board)){
					// if the neighbor board is not the one we already visited than proceed
					// to add it to the minPQ
					SearchNode sn = new SearchNode(neighbor); 
					// this searchnode is to wrap the board neighbor into a search
					// so we can easily add it to the queue
					sn.previous = current; 			 
					sn.moves = sn.previous.moves + 1; 
					minpq.insert(sn); // and insert the neighbors search node into the queue
				}

			}
			current = minpq.delMin(); // pop the min number of moves and set that as our current

			
		}
		return current;
	}

	// min number of moves to solve initial board
	public int moves() {
		if (result != null)
			return result.moves;
		return -1; // if there's no moves

	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		if (result == null) return null; // return if there is no solution
			
		Stack<Board> s = new Stack<Board>();
		for (SearchNode n = result; n != null; n = n.previous)
			s.push(n.board); // push the n board onto the stack
		return s;

	}

	// unit testing
	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		Solver solver = new Solver(initial);
		
		StdOut.println("Minimum number of moves = " + solver.moves());
		for (Board board : solver.solution())
			StdOut.println(board);
		StdOut.println("Time in seconds         = " + stopwatch.elapsedTime());
	}

}
