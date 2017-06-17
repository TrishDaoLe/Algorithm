

public class Palindrome {
	public static void main(String[] args) {
		
		
		Deque<Character> q = new Deque<Character>(); // create the queue
		while(!StdIn.isEmpty()){
			q.addLast(StdIn.readChar() );		//while the input is not empty we addlast the 
												//input
		}
		
		boolean palindromeResult = true; //a boolean to say if the plaindrome is true or false
		if (q.size()%2 != 0){				// we break from the loop and return true or fasle
			StdOut.println("false");    // a special case for when queueSize is 1 we just return false
			System.exit(0); 			// and stop the while loop from executing 
		}
		
		
		while (!q.isEmpty()) {			// if the q is not just 1 item we continue to our main code
			
			Character frontItem = q.removeFirst(); // as we remove First and Last item we store them in
			Character tailItem = q.removeLast();	// a variable in order to compare later
			  switch (tailItem){
			  	case 'A': tailItem = 'T';			// and also change the Last item to its complemented base
			  		break;
			  	case 'T': tailItem = 'A';
			  		break;
			  	case 'C': tailItem = 'G';
			  		break;
			  	case 'G':tailItem = 'C';
			  		break; 
			  }
				
			if(!frontItem.equals(tailItem)){	
				StdOut.println("False");
				palindromeResult = false; 
				break; 
			}	
			
		}		
		if( palindromeResult){
					StdOut.println("true"); 
		} 

		
	}

}
