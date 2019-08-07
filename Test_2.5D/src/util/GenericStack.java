package util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A generic-type implementation of a Stack, using an internal
 * Linked-List.
 * 
 * @author William DeStaffan
 * @param <E> A generic-type
 */
public class GenericStack<E> {

	/** The internal Linked-List */
	private LinkedList<E> list;
	
	/**
	 * Constructs a LinkedStack.
	 */
	public GenericStack() {
		list = new LinkedList<E>();
	}
	
	/**
	 * Adds an element to the top of the stack. If the stack is
	 * full, an IllegalArgumentException is thrown.
	 * 
	 * @param element The element to push to the top of the stack.
	 * @throws IllegalArgumentException If the stack is full.
	 */
	public void push(E element) {
		list.addFirst(element);
	}

	/**
	 * Removes and returns the top-most element of the stack.
	 * If the stack is empty, an IllegalArgumentException is thrown.
	 * 
	 * @throws IllegalArgumentException If the stack is empty.
	 * @return The element 'popped' from the stack.
	 */
	public E pop() {
		try {
			return list.removeFirst();
		}
		catch(NoSuchElementException e) {
			throw new IllegalArgumentException("Stack empty!");
		}
	}
	
	/**
	 * Returns a boolean corresponding to whether the stack is empty 
	 * or not.
	 *
	 * @return True if the stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the current size of the stack;
	 * 
	 * @return The size of the stack.
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * Returns the top element of the stack without removing it
	 * 
	 * @throws IllegalArgumentException If the stack is empty.
	 * @return The top element of the stack
	 */
	public E peek() {
		if(isEmpty())
			throw new IllegalArgumentException();
		return list.get(0);
	}
}
