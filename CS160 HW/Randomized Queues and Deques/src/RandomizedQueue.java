import java.util.Iterator;
import java.util.Random;



public class RandomizedQueue<Item> implements Iterable<Item> {
	private int N;         // number of elements on queue
	private Node first;    // beginning of queue
	private Node last;     // end of queue
	private Random randomGen; // a random number generator 

	private class Node {
		Item item;
		Node next;
	}

	public RandomizedQueue()
	{	
		// construct an empty randomized queue
		first = new Node();
		first.next = null;
		first.item = null;
		first = last;
		randomGen = new Random();}



	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return N;     
	}

	public void enqueue(Item item)  { 	// code provided in the book 
		Node node = new Node();
		node.item = item;
		if (isEmpty()) { first = node;     last = node; }
		else           { last.next = node; last = node; }
		N++;
	}

	public Item dequeue() {
		// delete and return a random item
		if (isEmpty()) throw new java.util.NoSuchElementException();
		int random = randomGen.nextInt()%N; 
		
		Item item = null; 				
		if(random == 0){
			
			item = first.item;
			first = first.next;
			N--;
			
			if (isEmpty()) last = null;  
		} else if (random == N-1){
			
			item = last.item;

			for(Node x = first; x!=null; x=x.next){		// for loop for iterating through a 
														//queue given in the book
				x.next = null; 
				N--;
			}
		}
		else{
			Node pointer = first;
			for(int i = 0; i < random; pointer=pointer.next,i++);
			item = pointer.next.item; 
			pointer.next = pointer.next.next; 
			
		}	N--;
			return item; 	

	}
	public Item sample()   
	// return (but do not delete) a random item
	{	if (isEmpty())
		throw new java.util.NoSuchElementException(); 
		int random = randomGen.nextInt()%N; 

		Node pointer = first;
		for(int i=0; i < random; pointer=pointer.next,i++){
		Item item = pointer.next.item;}
		return pointer.item; 
	
	}


	public Iterator<Item> iterator() {
		return new ListIterator();						// Iterator code is provided in the book
	}
	private class ListIterator implements Iterator<Item>{
		private Node current = first;
		public boolean hasNext(){
			return current != null;
		}

		public Item next(){
			Item item = current.item;
			current = current.next;
			return item;
		}


		public void remove() {
			throw new java.lang.UnsupportedOperationException(); 

		}


	}
	public static void main(String[] args) {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		
			q.enqueue("Hello");
			q.enqueue("My");
			q.enqueue("Name");
			q.enqueue("Trish");
			StdOut.println(q.size());
			for(String s :q){
				StdOut.println(s);}
			StdOut.println("The item removed is " + q.dequeue()); 
			StdOut.println(q.size());
			for(String s :q){
				StdOut.println(s);}
			StdOut.println("The Item Peek is " + q.sample());
			
			for(String s :q){
				StdOut.println(s);}
	}

}
