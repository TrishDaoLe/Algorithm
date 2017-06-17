import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first; // link to least recently added node
	private Node last; // link to the most recently added node
	private int queueSize; // number of items on the queue

	private class Node { // helper class
		Item item;
		Node next;
	}

	public Deque() {
		first = null;
		last = null;		//contrust a empty deque

	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return queueSize;
	}

	public void addFirst(Item item) {
		if (null == item)
			throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;

		if (last == null) {
			last = first;
		} else
			first.next = oldfirst;

		queueSize++;
	}

	public void addLast(Item item) {
		if (null == item)
			throw new java.lang.NullPointerException();

		Node oldLast = last; // code provided in the book
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) {
			first = last;
		} else
			oldLast.next = last;

		queueSize++;

	}

	public Item removeFirst() {
		// removing item from the beginning of the queue

		if (isEmpty())
			throw new java.util.NoSuchElementException();

		Item item = first.item;

		first = first.next;
		queueSize--;

		if (isEmpty()) {
			last = null;
		}
		return item;

	}

	public Item removeLast() {
		// removing the last item from the queue
		if (isEmpty())
			throw new java.util.NoSuchElementException();

		Item item = last.item;

		Node x = first;
		Node previous = null;

		while (x.next != null) {
			previous = x;
			x = x.next;

		}
		last = previous;
		if(last != null){		// a special case when queueSize is 1
			last.next = null;
		}else first = null; 
		

		queueSize--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();

		}
	}

	public static void main(String[] args) {

		Deque<String> q = new Deque<String>();
		q.addFirst("Hello");
		q.addLast("Name");
		q.addLast("Is");
		q.addLast("Trish");
		StdOut.println(q.size());
		StdOut.println(q.removeFirst());
		StdOut.println(q.size());
		StdOut.println(q.removeLast());
		StdOut.println(q.size());

	}

}
