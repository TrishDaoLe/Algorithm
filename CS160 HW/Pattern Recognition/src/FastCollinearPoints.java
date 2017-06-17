/*************************************************************************
 * Name: Courtney Pharr and Trish Le
 * Login: cpharr and dle
 *
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class FastCollinearPoints {
	Stack<PointSequence> stack;
	private int size; // size of the
	private Point[] sortedPoints; // copy of the array to maintain the original
	private int count;

	// makes a defensive copy of the array of points
	public FastCollinearPoints(Point[] points) {
		size = points.length;

		sortedPoints = points.clone();
		Arrays.sort(sortedPoints);
	}

	// returns the number of total points in the array
	public int numberOfPoints() {
		return size;
	}

	// returns the number of segments of length minLength or more
	public int numberOfSegments(int minLength) {

		return count;
	}

	public Iterable<PointSequence> segments(int minLength) {
		Point[] copy = sortedPoints.clone();
		stack = new Stack<PointSequence>();
		// have a for loop that iterates thru an unsorted array
		//sort it by its slope order according to p, which changes for each run thru
		// have another for loop that checks for the collinear points
//
//		for (int i = 0; i < size; i++) { // go through each possible p
//			
//			Point p = copy[i];
//			
//			Arrays.sort(sortedPoints, p.SLOPE_ORDER);
//			
//			ArrayList<Point> collinearPoints = new ArrayList<Point>(size);
//			
//			for (int k = 0; k < size - 1; k++) {
//				
//				if (i == k) {
//					
//					continue;
//
//				}
//				if (collinearPoints.isEmpty()) {
//					
//					collinearPoints.add(sortedPoints[k]);
//					
//				} else if (p.slopeTo(sortedPoints[k - 1]) == p.slopeTo(sortedPoints[k])) {
//					
//					collinearPoints.add(sortedPoints[k]);
//					
//				} else if (collinearPoints.size() > 2) {
//					
//					collinearPoints.add(sortedPoints[i]);
//				}
//			}	
//			// then we add all of the collinear points to an array of size min length
//			// the return the stack of all of our pts sequence
//
//		}
		return stack;

	}

	// draws all maximal length segments of length 4 or more
	public static void main(String[] args) {
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

		FastCollinearPoints fast = new FastCollinearPoints(pts);

		Iterable<PointSequence> iterablePtSeq = fast.segments(4);

		StdOut.println("number of points: " + fast.numberOfPoints());
		StdOut.println("number of segments: "
				+ fast.numberOfSegments(4));

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

	}
}