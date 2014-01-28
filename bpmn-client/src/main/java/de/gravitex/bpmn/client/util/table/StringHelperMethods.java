package de.gravitex.bpmn.client.util.table;

public class StringHelperMethods {

	public static String firstToUpper(String s) {
		if (isBlank(s)) {
			return null;
		}
		String tmpLeading = String.valueOf(s.charAt(0)).toUpperCase();
		if (s.length() == 1) {
			return tmpLeading;
		}
		return tmpLeading + s.substring(1, s.length());
	}
	
	public static boolean isBlank(String s) {
		return s == null || s.length() == 0;
	}
}
