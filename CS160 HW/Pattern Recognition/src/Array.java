public class Array {
	private int arr[];
	
	int size;
	public Array(int n){
		arr = new int [n];
		size = 0; 
	}
	public void display(){
		
		for(int j = 0; j<size;j++){
			StdOut.println(arr[j] + " " );
		}
		StdOut.println(" ");
	}
	public void insert(int num){
		arr[size] = num;
		size++;
	}

	public void removeDuplicates() {
		int i = 0;
		int j = 1; // use as location that store the next non-dup
		int k = 0; // store the duplicate
		int dup = 0;

			while (k < (size - 1)) {
				while (arr[k + 1] <= arr[i]) {
					k++;
					dup++; // keep track of the duplicates
				}
				arr[j] = arr[k + 1];
				k = k + 1;
				i = j;
				j = i + 1;
			}
			size = size - dup; // new length of the array
		}
	

	// implement by calling this private method
	// example arrayname.removeDuplicates()
	public static void main(String[] args) {
		
		 int n = 10; 
		 Array arr;
		 arr = new Array(n);
		 arr.insert(1);
		 arr.insert(1);
		 arr.insert(2);
		 arr.insert(2);
		 arr.insert(3);
		 arr.display();
		 
		arr.removeDuplicates();
		arr.display();
		
	}
}