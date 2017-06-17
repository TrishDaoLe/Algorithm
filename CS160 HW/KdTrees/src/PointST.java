public class PointST<Value> {
	// key Point2D,
	// value is the value passes by the client
	private RedBlackBST<Point2D, Value> rbtree;

	private int size;

	// construct an empty symbol table of points
	public PointST() {
		rbtree = new RedBlackBST<Point2D, Value>();
		size = 0;
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// number of points in the ST
	public int size() {
		return size;
	}

	// add the point p to the ST or if it already exists, update
	public void insert(Point2D p, Value v) {
		rbtree.put(p, v);
		size++;
	}

	// returns value mapped to by p
	public Value get(Point2D p) {
		return rbtree.get(p);
	}

	// does the ST contain the point p?
	public boolean contains(Point2D p) {
		return rbtree.contains(p);

	}

	// draw points to standard draw
	public void draw() {

		for (Point2D pt : rbtree.keys()) {
			pt.draw();
		}

	}

	// all points in the ST that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {

		// Create Iterable for output
		Queue<Point2D> queue = new Queue<Point2D>();

		// Through all Point2D, enqueue if inside given rectangle
		for (Point2D pt : rbtree.keys()) {
			if (rect.contains(pt))
				queue.enqueue(pt);
		}

		return queue;
	}

	// a nearest neighbor in the ST to p; null if set is empty
	public Point2D nearest(Point2D p) {

		Point2D nearest = null;
		for (Point2D pt : rbtree.keys()) {

			// If found closer Point2D, remember it
			if (!p.equals(pt)
					&& (nearest == null || p.distanceSquaredTo(pt) < p
							.distanceSquaredTo(nearest)))
				nearest = pt;

		}
		return nearest;
	}

	// unit testing of the methods
	public static void main(String[] args) {
		PointST<Character> st = new PointST<Character>();
		Point2D p1 = new Point2D(0.2, 0.3);
		Point2D p2 = new Point2D(0.8, 0.8);
		Point2D p3 = new Point2D(0.5, 0.9);
		st.insert(p1, 'a');
		st.insert(p2, 'b');
		st.insert(p3, 'c');

		RectHV rect = new RectHV(0.1, 0.1, 0.7, 0.7);

		StdOut.println("size() " + st.size());
		StdOut.println("isEmpty(): " + st.isEmpty());
		StdOut.println("range(): " + st.range(rect));
		StdOut.println("nearest(): " + st.nearest(p3));
		StdOut.println("contains(): " + st.contains(p2));
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);
		StdDraw.filledSquare(10 / 2.0, 10 / 2.0, 10 / 2.0);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		
		st.draw();
		StdDraw.show(0);

	}
}
