public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty()) {		// while user input is not empty we enqueue the input
			q.enqueue(StdIn.readString());
		}	
		int max = Integer.parseInt(args[0]);	// assignment to convert the string to a single integer 
												// so we can dequeue randomly without have repeats 
		for(int i = 0; i < max;i++){
			StdOut.println(q.dequeue());
			
		}
			
		StdOut.println("\n Have a great day!"); // an extra token when the user execute their code
	}

}
