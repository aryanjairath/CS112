package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

		TNode z = new TNode();
		z.setLocation(0);
		TNode y = new TNode();
		z.setLocation(0);
		
		trainZero=new TNode();
		trainZero.setLocation(0);
		trainZero.setDown(z);
		z.setDown(y);

		TNode head3 = trainZero.getDown().getDown();
		TNode head2 = trainZero.getDown();
		TNode head1 = trainZero;

		int bus=0;
		int train = 0;
		for(int i = 1; i < locations.length+1; i++){
			TNode nf = new TNode();
			nf.setLocation(i);
			head3.setNext(nf);
			while(head3.getNext()!=null){
				head3=head3.getNext();

			}
			TNode nf2 = new TNode();
			nf2.setLocation(i);
			if(bus<busStops.length && i==busStops[bus]){
				head2.setNext(nf2);
				while(head2.getNext()!=null){
					head2=head2.getNext();
					
			}
			head2.setDown(head3);
			bus++;

				TNode nf3 = new TNode();
				nf3.setLocation(i);
				if(train<trainStations.length && i==trainStations[train]){
					head1.setNext(nf3);
					while(head1.getNext()!=null){
						head1=head1.getNext();					
				}
					head1.setDown(head2);
					train++;
				}
			}
			
		}
				
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode copy = trainZero;
		while(copy.getNext()!=null){
			if(station==copy.getNext().getLocation()){
				copy.getNext().setDown(null);
				TNode first = copy;
				first.setNext(copy.getNext().getNext());
			}
				if(copy.getNext()!=null)
					copy=copy.getNext();
		}
		return;
		
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
		TNode bus = trainZero.getDown();
		TNode trav2 = trainZero.getDown().getDown();
		TNode add = new TNode();
		boolean val = false;

		add.setLocation(busStop);
		while(trav2.getNext()!= null && trav2.getLocation()!=busStop){
			trav2 = trav2.getNext();
			if(trav2.getLocation()==busStop)
				val=true;
		}
		if(!val)
			return;
			
		while(bus.getNext()!=null&& bus.getNext().getLocation()<busStop){
			if(bus.getNext()!=null)
				bus=bus.getNext();	
		}
		TNode temp = new TNode();
		if(bus.getNext()!=null){
			temp = bus.getNext();
			bus.setNext(add);
			add.setNext(temp);
			add.setDown(trav2);
		}else{
			bus.setNext(add);;
			add.setDown(trav2);		
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> optimal = new ArrayList<>();
		TNode copy = trainZero;
		optimal.add(copy);
		while(copy.getNext()!=null && copy.getNext().getLocation()<=destination){
			optimal.add(copy.getNext());
			copy=copy.getNext();
		}
		if(copy.getLocation()==destination){
			optimal.add(copy.getDown());
			optimal.add(copy.getDown().getDown());
			return optimal;
		}
		if(copy.getDown().getLocation()<=destination){
			copy = copy.getDown();
			optimal.add(copy);
			while(copy.getNext()!=null && copy.getNext().getLocation()<=destination){
				optimal.add(copy.getNext());
				copy=copy.getNext();
		}
	}
		if(copy.getLocation()==destination){
			optimal.add(copy.getDown());
			return optimal;
	}
		if(copy.getDown().getLocation()<=destination){
			copy = copy.getDown();
			optimal.add(copy);
			while( copy.getNext()!=null && copy.getNext().getLocation()<=destination){
				optimal.add(copy.getNext());
				copy=copy.getNext();
			}
		}
		
		return optimal;
		
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

		TNode deep=new TNode();
		deep.setLocation(0);

		TNode x = new TNode();
		x.setLocation(0);
		TNode y = new TNode();
		y.setLocation(0);
		deep.setDown(x);
		x.setDown(y);
		
		TNode head1 = trainZero.getNext();
		while(head1.getNext()!=null){
			TNode nf = new TNode();
			nf.setLocation(head1.getLocation());
			head1.setNext(nf);
			while(head1.getNext()!=null){
				head1=head1.getNext();

			}
			deep.setNext(head1);
			if(head1.getDown()!=null)
				nf.setLocation(head1.getDown().getLocation());
				head1.setDown(head1.getDown());
		}
		/*copy = trainZero.getDown();
		while(copy.getNext()!=null){
			copy = trainZero.getDown();
			deep.setNext(copy.getNext());
			copy = copy.getNext();
			if(copy.getDown()!=null)
				deep.setDown(copy.getDown());
		}
		while(copy.getDown().getDown()!=null){
			copy = trainZero.getDown().getDown();
			deep.setNext(copy.getNext());
			copy = copy.getNext();
			if(copy.getDown()!=null)
				deep.setDown(copy.getDown());
		}*/
			

	    return deep;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		TNode busHead = trainZero.getDown();
		TNode walkHead = trainZero.getDown().getDown();
        TNode scooterHead = new TNode();
        scooterHead.setLocation(0);
		scooterHead.setDown(trainZero.getDown().getDown());
        busHead.setDown(scooterHead);
		int scoot = 0;
        while(scoot!=scooterStops.length){
            TNode nf = new TNode();
			nf.setLocation(scooterStops[scoot]);
            scoot++;
			scooterHead.setNext(nf);
			while(scooterHead.getNext()!=null){
				scooterHead=scooterHead.getNext();

			}
            
        }
        TNode scootCopy =trainZero.getDown().getDown();
        while(walkHead.getNext()!=null){
            if(scootCopy.getLocation()==walkHead.getLocation()){
                scootCopy.setDown(walkHead);
                if(scootCopy.getNext()!=null){
                    scootCopy=scootCopy.getNext();
                }
            }
            walkHead = walkHead.getNext();
        }
        
        TNode scootCopy2 =trainZero.getDown().getDown();
        while(busHead.getNext()!=null){
            while(busHead.getLocation()!=scootCopy2.getLocation()){
                if(scootCopy2.getNext()!=null)    
                    scootCopy2=scootCopy2.getNext();
                
            }
            busHead.setDown(scootCopy2);
            busHead = busHead.getNext();
        }
		/*while(walkHead.getNext()!=null){
                TNode nf = new TNode()
				temp1 = new TNode();
				temp1.setLocation(copy[i]);
				if(busHead.getNext()!=null && busHead.getLocation()==temp1.getLocation()){
					busHead=busHead.getNext();
					busHead.setDown(temp1);
					System.out.println(busHead.getDown().getLocation());
				}

				if(temp1.getNext()!=null){
					temp1.setNext(new TNode());
					temp1=temp1.getNext();

				}
			if(i!=scooterStops.length)		
				i++;
			else
             break;
        }*/
			

		//}
		
		/*while(busHead.getNext()!=null){
			busHead.setDown(temp1);
			busHead=busHead.getNext();
			if(temp1.getNext()!=null)
				temp1=temp1.getNext();
		}*/

	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
