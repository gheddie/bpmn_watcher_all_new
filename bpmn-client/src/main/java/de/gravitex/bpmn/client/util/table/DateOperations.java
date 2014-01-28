package de.gravitex.bpmn.client.util.table;

import org.apache.log4j.Logger;

public class DateOperations {

	private static Logger logger = Logger.getLogger(DateOperations.class);

	public static boolean isDateCorrectMsSqlSpecific(int tmpDay, int tmpMonth, int tmpYear) {
		if ((tmpYear < 1753) || (tmpYear > 9999))
			return false;
		return isDateCorrect(tmpDay, tmpMonth, tmpYear);
	}

	private static boolean isDateCorrect(int day, int month, int year) {
		logger.trace("checke : [d:" + day + "|m:" + month + "|y:" + year + "]");

		if (!((day > 0) && (month > 0) && (year > 0)))
			return false;

		int tmpDaysInMonth = daysInMonth(month, isLeapYear(year));
		logger.trace("Tage für Monat " + month + "/" + year + " berechnet : " + tmpDaysInMonth);

		if (day > tmpDaysInMonth)
			return false;

		if (!((month > 0) && (month <= 12)))
			return false;

		logger.trace("----------------> OK");
		return true;
	}

	private static int daysInMonth(int month, boolean leapYear) {
		
		if (!((month>0) && (month<=12)))
			return -1;
		
		if (month == 2)
			if (leapYear)
				return 29;
			else
				return 28;
		
		switch(month) {
			case 1:
			case 3:			
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
		}
		return -1;
	}

	private static boolean isLeapYear(int year) {
		if (year == 0) {
			logger.warn("Es gibt kein Jahr 0!");
			return false;
		} else {
			if (year % 4 == 0) {
				if (year % 100 == 0) {
					if (year % 400 == 0) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
