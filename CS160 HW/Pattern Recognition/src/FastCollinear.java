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

public class FastCollinear {
	private int size;						// number of elements passed in as a paramter to the constructor
	private Point[] copy;			// copy of the array to maintain the original
	private int count;
	
	//makes a defensive copy of the array of points
    public FastCollinear(Point [] points)        
    {    	
    	// obtain the size so it can be returned in numberOfPoints
    	size = points.length;
    	
    	// copy the original array and begin with it sorted
    	copy = points.clone(); 	
    	Arrays.sort(copy);
    }
    
    //returns the number of total points in the array
    public int numberOfPoints()                        
    {
    	return size;
    }
  
    //returns the number of segments of length minLength or more
    public int numberOfSegments(int minLength)         
    {  	    
    	// count is updated in segments() as elements are added to the stack
    	return count;
    }
    
    public Iterable<PointSequence> segments (int minLength) 
    {
    	Point[] sortedPoints = copy.clone(); // maintain original order when iterating through the array 
    	Stack<PointSequence> stack = new Stack<PointSequence>();	// initialize a stack object to be returned
    	
    	// temporary array to store collinear points;
    	// number of collinear points is unknown, so it is necessary to overestimate the size of the array
    	Point[] temp = new Point[size];			
    	
    	for (int i = 0; i < size; i++)
    	{
    		// sort all points in relation to the slope they make with Point p (dynamic comparator)
    		Point p = copy[i];
    		Arrays.sort(sortedPoints, p.SLOPE_ORDER);

    		// countSlope keeps track of the number of collinear points in the array.
    		// It is initialized to 2 because it must take into account Point p and the last 
    		// collinear point in the array.
    		int countSlope = 2;
    		
    		// Store collinear points beginning at index 1 because the first element in the array is always 
    		// Point p.
    		int index = 1;	
    		
    		for (int j = 1; j < size; j++)
    		{
    			if (p.slopeTo(sortedPoints[j]) == p.slopeTo(sortedPoints[j-1]))
    			{
    				temp[0] = p;
    				temp[index] = sortedPoints[j-1];
    				temp[index+1] = sortedPoints[j];
			
    				countSlope++; 
    				index++;
    			} 
    		} // end for
    			
    		// If there are enough collinear points to make a segment of at least minlength, add these points
    		// first to a PointSequence object and then to a stack.
    		if (countSlope >= minLength)
    		{
    			Point[] temp2 = new Point[countSlope];
			
    			// add elements to temp2 so that it becomes the correct size
   				for (int k = 0; k < countSlope; k++)
   				{
   						temp2[k] = temp[k];
   				}
    				
    			// create a PointSequence object with at least minLength collinear Points
    			PointSequence ptseq = new PointSequence(temp2);
		
    			// push theses values onto a Stack
    			// check for duplicates
    			if (ptseq.isCollinear())
    			{
    				stack.push(ptseq);
    				// count stores the number of segments
       				count++;
    			}		
			
    		} // if
    	} // end first for
    	
    	return stack;
    }
    
    //draws all maximal length segments of length 4 or more
    public static void main(String[] args)             
    {
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
    	   	
    	FastCollinear fast = new FastCollinear(pts);
    	
    	Iterable<PointSequence> iterablePtSeq = fast.segments(4);
    	
    	StdOut.println("number of points: " + fast.numberOfPoints());
    	StdOut.println("number of segments: " + fast.numberOfSegments(4));

    	// code to assist in drawing points
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        StdDraw.show(0);
        
        // iterate through the values in the stack to draw them and print out their values
        for (PointSequence point: iterablePtSeq)
        {
        	point.draw();
        	StdOut.println(point.toString());
        }

        StdDraw.show(0);
    }
}