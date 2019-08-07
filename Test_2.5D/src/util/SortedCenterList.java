package util;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SortedCenterList<E> extends ArrayList<E> {

	private boolean addingToFront = true;
	
	@Override
	public boolean add(E element) {
		
		if(addingToFront) {
			super.add(0, element);
			addingToFront = false;
		}
		else {
			super.add(element);
			addingToFront = true;
		}

		return true;
	}
	
	public void addNormal(E element) { //adds to end of list
		super.add(element);
	}
}
