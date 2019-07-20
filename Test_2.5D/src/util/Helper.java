package util;

/**
 * Some useful methods.
 * 
 * @author J2579
 */
public class Helper {

	/**
	 * Converts a Boolean value to an int value: True - 1, False - 0
	 * 
	 * @param b The boolean to convert.
	 * @return The integer value corresponding to the boolean.
	 */
	public static int boolToInt(boolean b) {
		return b ? 1 : 0;
	}
	
	/**
	 * Converts a boolean array to an int array
	 * 
	 * @param b The boolean array to convert
	 * @return The integer array to return
	 */
	public static int[] boolToInt(boolean[] b) {
		if(b == null) 
			return null;
		
		int[] returnArr = new int[b.length];
		
		for(int idx = 0; idx < b.length; ++idx) {
			returnArr[idx] = Helper.boolToInt(b[idx]); 
		}
		
		return returnArr;
	}
	
	/**
	 * Converts an array to a string
	 */
	public static String arrToString(Object[] arr) {
		if(arr == null) 
			return null;
		
		String s = "{";
		
		for(int idx = 0; idx < arr.length; ++idx) {
			
			s += arr[idx];
			
			if(idx == arr.length - 1) 
				 s += "}"; 
			else 
				 s += ",";
		}	
		
		return s;
	}
	
}
