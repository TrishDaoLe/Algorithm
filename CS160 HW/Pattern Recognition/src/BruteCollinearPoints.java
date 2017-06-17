import java.util.Arrays;
import java.util.Stack;

public class BruteCollinearPoints {
	private PointSequence point;
	int size;

	public BruteCollinearPoints(Point[] points) {// makes a defensive copy of the array of points
		Arrays.sort(points);
		size = points.length;  
		int count =0; 
		double slopeIJ;
		double slopeJK;
		double slopeKL;
		Stack<PointSequence> stack = new Stack<PointSequence>();
		
		int [] temp ;
		 

		for (int i = 0; i < size; i++) {
				 
			for (int j = i + 1; j < size; j++) {
				slopeIJ = points[i].slopeTo(points[j]);

				for (int k = j + 1; k < size; k++) {
					slopeJK = points[j].slopeTo(points[k]);

					for (int l = k + 1; l < size; l++) {
						slopeKL = points[k].slopeTo(points[l]);
						 if(slopeJK == slopeKL && slopeJK == slopeIJ && slopeKL == slopeIJ){
							 temp = new int [4];
							 temp[0] = i;
							 temp [1] = j;
							 temp[2] = k;
							 temp[3] =l; 
							 
							// PointSequence point = new PointSequence(temp);
								stack.push(point);
								count++;
						 }
					}	
				}
			}
		}
	} 

	public int numberOfPoints() {
		return size;
	}

	public int numberOfSegments() {
		return point.numberOfPoints()/4;
	}
	// returns an iterable of segments of length 4
	public Iterable<PointSequence> segments() {
		return null;
	} 

	public static void main(String[] args) {
		// Rescale the coordinate system.
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		// Read points from the input file.
		In in = new In(args[0]);
		int pointsCount = in.readInt();
		Point [] pts = new Point[pointsCount]; 
		Point[] points = new Point[pointsCount];
		for (int i = 0; i < pointsCount; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
			points[i].draw();
		}
	} // draws all 4 point segments in file
}
