
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

/*************************************************************************
 * name: Trish
 * partner: Lyle
 * username: dle
 *************************************************************************/
public class Point implements Comparable<Point> {
	

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    final String name;
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point point1, Point point2) {
            // Calculate the slope of the point <this> points to with the <point1>
            double slope1 = slopeTo(point1);
            
            // Calculate the slope of the point <this> point to with the <point2>
            double slope2 = slopeTo(point2);
            
            if      (slope1 < slope2) return -1;
            else if (slope1 > slope2) return  1;
            else return 0;
        }
    }
    
    // create the point (x, y)
    public Point(String name) {
        /* DO NOT MODIFY */
    	this.name = new String(name);
        String[] tokens = name.split(" ");
        x = tokens[0].length();
        y = tokens[1].length();
    }
    
    public int hashCode() {
    	 int hash = 17;
    	    hash = 31 * hash + this.x;
    	    hash = 31 * hash + this.y;
    	    return hash;
       
   }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
    public String getName() {
    	return name;
    }
    public boolean equals(Object other){
    	if(this == other) return true;
    	if(other == null) return false;
    	 Point that = (Point) other;
    	 if (other.getClass() != this.getClass()) return false;
    	 return this.x == that.x && this.y == that.y;
    	
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if(that.x == this.x){
            if(that.y == this.y){
            
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if(that.y == this.y){
           
            return 0;
        }
        return new Double ((that.y - this.y)).doubleValue()/new Double (that.x - this.x).doubleValue();
//       
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        
    	if(this.y > that.y || (this.y == that.y && this.x > that.x)) return 1;
        if(this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;       
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    public static void main(String[] args) {
//    	Point p=null;
//    	 In in = new In(args[0]);
//         
//         while (in.hasNextLine()) {
//         	String line = in.readLine();
//     		String[] tokens = line.split(",");
//     		String name = tokens[0] + " " + tokens[1];
//     		 p = new Point(name);
//     		map.put(p.hashCode(), name);
//     	}
//         for(String s : map.values()) {
//         	s.toString();
        	 
         }
    	}
    
 
