public class KdTreeST<Value> {
	private Node root;
	private int size;

	private class Node {
		private Point2D p; // the point
		private Value value; // the symbol table maps the point to this value
		private RectHV rect; // the axis-aligned rectangle corresponding to this
		// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D pt, Value val) {
			this.p = pt;
			this.value = val;
		}
		
		public Node(Point2D pt, Value val, RectHV rect) {
			this.p = pt;
			this.value = val;
			this.rect = rect;
		}

		private void setLeft(Node leftC) {
			lb = leftC;
		}

		private void setRight(Node rightC) {
			rt = rightC;
		}
	}

	// construct an empty symbol table of points
	public KdTreeST() {
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
		Node node = null;
		RectHV rect = null;
		size++;
		if (root == null) {
			rect = new RectHV(0, 0, 1, 	1);
			node = new Node(p, v, rect);
			root = node;
		} else {
			node = new Node(p, v);
			searchInsert(node, root, true);
			// the bounding box is define by the node parent's line

		}

	}

	private void searchInsert(Node toInsert, Node node, boolean compareX) {

		
		
		// Compare X / Y
		if (compareX) {
			if (toInsert.p.x() < node.p.x()) {
				// Go left
				if (node.lb != null) {
					searchInsert(toInsert, node.lb, !compareX);
				} else {
					bindNode(node, toInsert, true, compareX);

				}
			} else {
				// Go right
				if (node.rt != null) {
					searchInsert(toInsert, node.rt, !compareX);
				} else {
					bindNode(node, toInsert, false, compareX);
				}
			}
		} else {
			if (toInsert.p.y() < node.p.y()) {
				// Go left
				if (node.lb != null) {
					searchInsert(toInsert, node.lb, !compareX);
				} else {
					bindNode(node, toInsert, true, compareX);
				}
			} else {
				// Go right
				if (node.rt != null) {
					searchInsert(toInsert, node.rt, !compareX);
				} else {
					bindNode(node, toInsert, false, compareX);
				}
			}
		}

	}

	private RectHV createBoundary(Node node, Node parentNode, boolean xDominant) {
		
		RectHV parentBox = parentNode.rect;
		
		// Invert value (should be parent's when inverted)
		xDominant = !xDominant;
		
		
		double w = parentBox.width();
		double h = parentBox.height();
		
		RectHV newBox = null;
		
		// Child node needs a shift if it's to the right or below parent node
		boolean shouldShift = false;
		if (!isToTheLeft(node, parentNode) || !isAbove(node, parentNode)) {
			shouldShift = true;
		}
		
		
		
		// Vertical line case (for parent)
		if (xDominant) {
			w -= parentNode.p.x();
			
			// Calculates new minimum whether or not it shifts
			double minX = parentNode.rect.xmin();
			if (shouldShift) minX += parentNode.p.x();
			
			newBox = new RectHV(minX, parentNode.rect.ymin(), parentNode.rect.xmax() + w, parentNode.rect.ymax());
		} else {
			
			// Calculates new minimum whether or not it shifts		
			double maxY = parentNode.rect.ymax();
			if (shouldShift) maxY -= parentNode.p.y();
						
			// Horizontal line case (for parent)
			h -= parentNode.p.y();
			newBox = new RectHV(parentNode.rect.xmin(), parentNode.rect.ymin() - parentNode.p.y(), parentNode.rect.xmax(), maxY);
		}
		
		return newBox;
		
	}
	/**
	 * Checks whether or not the first Node given is to the left (smaller x value)
	 * the second given Node
	 * @param n1
	 * 		the first Node.
	 * @param n2
	 * 		the second Node.
	 * 
	 * @return
	 * 		true if the first Node is to the left of the second Node, false otherwise.
	 */	
	private boolean isToTheLeft(Node n1, Node n2) {
		return (n1.p.x() < n2.p.x());
	}
	
	/**
	 * Checks whether or not the first Node given is above (smaller y value)
	 * the second given Node
	 * @param n1
	 * 		the first Node.
	 * @param n2
	 * 		the second Node.
	 * 
	 * @return
	 * 		true if the first Node is above the second Node, false otherwise.
	 */	
	private boolean isAbove(Node n1, Node n2) {
		return (n1.p.y() < n2.p.y());
	}
	private void bindNode(Node node, Node child, boolean leftChild, boolean xDominant) {
		if (leftChild) {
			node.setLeft(child);
		} else {
			node.setRight(child);
		}
		node.rect = createBoundary(child, node, xDominant);
	}

	// returns value mapped to by p
	public Value get(Point2D p) {
		return null;
	}

	// does the ST contain the point p?
	public boolean contains(Point2D p) {
		return false;
	}

	// draw points to standard draw
	public void draw() {
	}

	// all points in the ST that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		return null;
	}

	// a nearest neighbor in the ST to p; null if set is empty
	public Point2D nearest(Point2D p) {
		return p;
	}

	// unit testing of the methods (not graded)
	public static void main(String[] args) {
	
		Point2D p1 = new Point2D(0.1, 0.1);
		Point2D p2 = new Point2D(0.2, 0.2);
		Point2D p3 = new Point2D(0.3, 0.3);
		Point2D p4 = new Point2D(0.4, 0.4);
		KdTreeST<Character> tree = new KdTreeST<Character>();
		tree.insert(p1, 'a');
		tree.insert(p2, 'b');
		tree.insert(p3, 'c');
		tree.insert(p4, 'd');
		
	
	}
}
