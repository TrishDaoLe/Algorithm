/*************************************************************************
 * Name: Courtney Pharr and Trish Le
 * Login: cpharr and dle
 *
 * Compilation:  javac Point.java
 *
 * Description: A faster version of BruteCollinearPoints that removes all
 * duplicate collinear points and has a running time of N^2lgN.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;

public class FastCollinearPoint {
	private int size; // number of elements passed in as a paramter to the
	// constructor
	private Point[] copy; // copy of the array to maintain the original
	private int count;

	// makes a defensive copy of the array of points
	public FastCollinearPoint(Point[] points) {
		// obtain the size so it can be returned in numberOfPoints
		size = points.length;

		// copy the original array and begin with it sorted
		copy = points.clone();
		Arrays.sort(copy);
	}

	// returns the number of total points in the array
	public int numberOfPoints() {
		return size;
	}

	// returns the number of segments of length minLength or more
	public int numberOfSegments(int minLength) {
		// count is updated in segments() as elements are added to the stack
		return count;
	}

	public Iterable<PointSequence> segments(int minLength) {

		Point[] sortedPoints = copy.clone(); // maintain original order when
		// iterating through the array
		Stack<PointSequence> stack = new Stack<PointSequence>(); // initialize a
		
		for (int i = 0; i < size; i++) {
			// sort all points in relation to the slope they make with Point p
			// (dynamic comparator)
			Point p = copy[i];
			Arrays.sort(sortedPoints, p.SLOPE_ORDER);

			int k = 1;
			for (int m = 1; m < size; m++) {
				if (p.compareTo(sortedPoints[m]) == 0) {
					++k; // number of same points
				} else
					break; // when there are no more same points we stop
			}

			double s = p.slopeTo(sortedPoints[k]); // calculate the slope from p
			// to the non duplicate

			int j;
			for (j = 1; j < size; j++) {
				double s2 = p.slopeTo(sortedPoints[j]);
				if (s != s2) {
					break;
				}
			}

			int segmentLength = j - k + 1;
			if (minLength > segmentLength)
				break;

			Point[] segments = new Point[segmentLength];
			segments[0] = sortedPoints[0];
			for (int l = k; l < j; l++) {
				segments[l - k + 1] = sortedPoints[l];

			}

			PointSequence ptseq = new PointSequence(segments);
			stack.push(ptseq);

		} // end first for

		count = stack.size();

		return stack;
	}

	// draws all maximal length segments of length 4 or more
	public static void main(String[] args) {
		// Stopwatch stopwatch = new Stopwatch();

		// read in the input
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] pts = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			pts[i] = p;
		}

		FastCollinearPoint fast = new FastCollinearPoint(pts);

		Iterable<PointSequence> iterablePtSeq = fast.segments(4);

		StdOut.println("number of points: " + fast.numberOfPoints());
		StdOut.println("number of segments: " + fast.numberOfSegments(4));

		// code to assist in drawing points
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.005);
		StdDraw.show(0);

		// iterate through the values in the stack to draw them and print out
		// their values
		for (PointSequence point : iterablePtSeq) {
			point.draw();
			StdOut.println(point.toString());
		}

		StdDraw.show(0);

		// StdOut.println("Time: " + stopwatch.elapsedTime() + " seconds");
	}
}