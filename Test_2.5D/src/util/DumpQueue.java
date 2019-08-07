package util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A generic-type implementation of a Queue, using an
 * internal LinkedList for element storage.
 * 
 * @author William DeStaffan
 * @param <E> A generic-type.
 */
public class DumpQueue<E> {

	/** The internal LinkedList */
	private LinkedList<E> list;
	
	/**
	 * Instantiates LinkedQueue
	 */
	
	public DumpQueue() {
		list = new LinkedList<E>();
	}
	
	/**
	 * Returns true if the LinkedQueue contains an element, false otherwise.
	 * 
	 * @param element The element to check if the list contains.
	 * @return True if the list contains 'element', false otherwise.
	 */
	public boolean contains(E element) {
		for(int idx = 0; idx < size(); ++idx) {
			if(list.get(idx).equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds an element to the back of the queue: If there is no room in the queue,
	 * an IllegalArgumentException will be thrown
	 * 
	 * @param element The element to add to the back of the queue.
	 * @throws IllegalArgumentException If the queue is at capacity.
	 */
	public void enqueue(E element) {
		try {
			list.addFirst(element);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Queue at capacity!");
		}
	}

	/**
	 * Removes and returns the element at the front of the queue.
	 * 
	 * @return The foremost element in the queue.
	 * @throws NoSuchElementException If the queue is empty.
	 */
	public E dequeue() {
		try {
			return list.removeLast();
		}
		catch(IndexOutOfBoundsException e) {
			 throw new NoSuchElementException("Queue is empty!");
		}
	}

	/**
	 * Returns true if the Queue is empty, false if it is not.
	 * 
	 * @return True if the queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return The number of elements in the queue.
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Dumps the queue to a String.
	 * 
	 * @return The dumped queue.
	 */
	public String dump() {
		
		String str = "";
	
		while(!isEmpty()) {
			str += dequeue().toString() + "\n";
		}
		
		if(str.isEmpty())
			return null;
		return str;
	}
}