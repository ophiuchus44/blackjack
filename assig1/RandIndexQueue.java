import java.util.*;

public class RandIndexQueue<T> implements Shufflable, Indexable<T>, MyQ<T> {

	private T [] arr;

	private int logicalSize = 0;

	private int front = 0;
	private int end = 0;

	private int moves=0;

	public RandIndexQueue(int objectSize) {
		arr = (T[]) new Object[objectSize];
	}	

//// from myQ

	public boolean offer(T item) {		
		//System.out.println("Pre-offer: " + toString());

	
		if (logicalSize<arr.length){

			// 
			arr[end] = item;
			
			end = (end+1) % arr.length;
			
			logicalSize++; 	

			//System.out.println("***logicalSize = " + logicalSize);

			}	
		
		else {

//			for (int i =0; i<arr.length; i++){
//				System.out.print(" " + arr[i] + " ");
//					}

			//System.out.println("*****RESIZING ARRAY*****");

			T[] tmp = (T[]) new Object[2*arr.length];

		

			for (int a=0; a<logicalSize; a++){
				int tmpInt = (a + front) % arr.length;

				//System.out.println("tmpInt = " + tmpInt);
				tmp[a] = arr[tmpInt];

				//System.out.println("tmpArray = " + tmp[a]);

			} 

			arr = tmp;

			end = logicalSize;
			front = 0;
			arr[end] = item;
			end = (end+1) % arr.length;
			logicalSize ++;

		}


//		for (int b =0; b<arr.length; b++){
//			System.out.print(" " + arr[b] + " ");
//					}

		//System.out.println("end index # = " + end);
		//arr[moves] = item;
		//moves ++; 
		moves ++;
		//logicalSize++; 	
	//	System.out.println("Post-offer: " + toString());
		return true;
	}


// Remove and return the logical front item in the MyQ.  If the MyQ
	// is empty, return null

	public T poll() {
	//	System.out.println("Pre-poll: " + toString());
		//System.out.println("front1 = " + front);
		if(isEmpty()){
			return null;
		}

		T tmp = arr[front];

		arr[front] = null;

		logicalSize--;

		front = (front + 1) % arr.length;
		//System.out.println("front2 = " + front);
	
		//for (int i =0; i<arr.length; i++){
		//	System.out.println("array contents" + arr[i]);
		//}
	//	System.out.println("Post-poll: " + toString());
		return tmp;

		//return null;

	}


	public T peek() {
		return null;

	}

	public boolean isFull() {

		return false;
	}

	// Return true if the MyQ is empty, and false otherwise

	public boolean isEmpty() {

		boolean isEmpty;

		if(logicalSize==0){
			isEmpty = true;

		}
		else{
			isEmpty = false;
		} 

		return isEmpty;
	}



	public int size() {

		// return count 

		//System.out.println("logicalSize = " + logicalSize);

		return logicalSize;
	}

	// Reset the MyQ to empty status by reinitializing the variables
	// appropriately
	public void clear() {

		//arr = new Object();
		arr = (T[]) new Object[2];

		logicalSize = 0;

		front = 0;
		end = 0;

		moves=0;
	
	}

	public int getMoves() {
		return moves;
	}

	
	public void setMoves(int moves){
		this.moves = moves;
		//logicalSize logimoves;
	}


/// from shuffle

	// Reorganize the items in the object in a pseudo-random way.  The exact
	// way is up to you but it should utilize a Random object (see Random in 
	// the Java API).  Note that this should not change the size of the
	// collection.

	public void shuffle() {
		
//    	Random r = new Random();
		//System.out.println("logicalSize = " + logicalSize);

    	Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<logicalSize; i++) {

		    int randomPosition = (rgen.nextInt(logicalSize) + front) % arr.length;

		  //  System.out.println("randomPosition = " + randomPosition);
		    T temp = arr[(i+front) % arr.length] ;
		    arr[(i+front) % arr.length] = arr[randomPosition];
		    arr[randomPosition] = temp;
		}

		//return array;
		
	}

// from indexable

	// Get and return the value located at logical location i in the implementing
	// collection, where location 0 is the logical beginning of the collection.
	// If the collection has fewer than (i+1) items, throw an IndexOutOfBoundsException 

	public T get(int i) {

		T tmp = arr[(front+ i) % arr.length];

		return tmp;

	}

	// Assign item to logical location i in the implementing collection, where location
	// 0 is the logical beginning of the collection.  If the collection has fewer than
	// (i+1) items, throw an IndexOutOfBoundsException

	public void set(int i, T item) {
		
		arr[(front+ i) % arr.length] = item;
		//for (int a= front; a<logicalSize; a++){
//
//			if (arr[a].equals(i))
//			item = arr[i];
//		}

	}


	public String toString()
	{
		StringBuilder theData = new StringBuilder();
			for (int b =0; b<logicalSize; b++) {
				theData.append(arr[(b+front) %arr.length] + " ");
			}

		String finalData = "contents: " + theData.toString();

	//	System.out.println("logicalSize = " + logicalSize);

		return finalData;
	}







}