package com.calculater.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.calculater.assessment.exceptions.NegativeNotAllowedException;

public class StringCalculator {

	// final static String numberStringPat = "(\\d+[\\n,]?)+";
	final static String numberStringPat = "(([\\-]?)\\d+[\\n,]?)+";
	final static String delimiterNumberStringPat = "//\\[(.+)\\](\\n)((.+\\d+)+)";
	final static String negativeErrorMessage = "Negative number not allowed";

	public int add(String numberString) throws Exception {
		if (numberString.matches(numberStringPat)) {
			// System.out.println("Case 1 without delimiter");
			return calculatingNumbersInString(numberString, null, false);

		} else if (numberString.matches(delimiterNumberStringPat)) {
			// System.out.println("Case 2 with single multiple delimiter");
			return singleMultipleDelimiteredCalculation(numberString);
		}

		return 0;
	}

	public static int singleMultipleDelimiteredCalculation(String numberString) throws NegativeNotAllowedException {
		int actualStringLength = numberString.length();

		String delimitersString;
		String numbers;
		int delimiterBeginIndex = 2;
		int delimiterEndIndex = slashNLocation(numberString);

		int numbersBeginIndex = delimiterEndIndex + 1;
		int numbersEndIndex = actualStringLength;

		delimitersString = numberString.substring(delimiterBeginIndex, delimiterEndIndex);
		numbers = numberString.substring(numbersBeginIndex, numbersEndIndex);

		// System.out.println("delimitersString "+ delimitersString);
		// System.out.println("numbers "+ numbers);

		// System.out.println(slashNLocation(numberString));
		List<String> delimiters = getAllDelimiters(delimitersString);

		return calculatingNumbersInString(numbers, delimiters, true);

	}

	public static List<String> getAllDelimiters(String delimitersString) {
		// building location map
		Map<Integer, Integer> bracketLocationMap = new HashMap<Integer, Integer>();

		int openBracketLoc = 0;
		int closeBracketLoc = 0;

		for (int i = 0; i < delimitersString.length(); i++) {
			if (delimitersString.charAt(i) == '[') {
				openBracketLoc = i;
			}
			if (delimitersString.charAt(i) == ']') {
				closeBracketLoc = i;
			}
			if (openBracketLoc >= 0 && closeBracketLoc > 0) {
				bracketLocationMap.put(openBracketLoc, closeBracketLoc);

				openBracketLoc = 0;
				closeBracketLoc = 0;
			}
		}
		// System.out.println(bracketLocationMap);
		// delimiter retrieval from bracket
		List<String> delimitersList = new ArrayList<String>();

		for (Entry<Integer, Integer> entry : bracketLocationMap.entrySet()) {
			String delimiter = delimitersString.substring(entry.getKey() + 1, entry.getValue());
			delimitersList.add(delimiter);
			// System.out.println("Each Delimiter "+ delimiter );
		}

		return delimitersList;
	}

	public static int calculatingNumbersInString(String numbersString, List<String> delimiters, Boolean isDelimitered)
			throws NegativeNotAllowedException {
		int totalSum = 0;
		String numberStr = "";
		String delimStr = "";

		List<String> delimList = new ArrayList<String>();
		for (int i = 0; i < numbersString.length(); i++) {

			if (Character.isDigit(numbersString.charAt(i))) {
				if (i > 0) {
					if (numbersString.charAt(i - 1) == '-') {
						throw new NegativeNotAllowedException(negativeErrorMessage);
					}
				}
				numberStr += numbersString.charAt(i);
				if (!delimStr.isEmpty()) {
					if (!checkDelimIsValid(delimiters, delimStr) && isDelimitered == true) {
						return 0;
					}
					delimList.add(delimStr);
				}
				delimStr = "";
			}

			else {
				// System.out.println("Each number = " + numberStr);
				if (!numberStr.isEmpty()) {
					int number = Integer.valueOf(numberStr);
					if (number <= 1000) {
						totalSum += number;
					}
				}
				numberStr = "";
				delimStr += numbersString.charAt(i);
			}
			if (i == (numbersString.length() - 1)) {
				// System.out.println("Each number = " + numberStr);
				if (!numberStr.isEmpty()) {
					int number = Integer.valueOf(numberStr);
					if (number <= 1000) {
						totalSum += number;
					}
				}
			}
		}

		// System.out.println("Total sum "+totalSum);
		// System.out.println("Delim List "+delimList);
		return totalSum;
	}

	public static boolean checkDelimIsValid(List<String> delimeters, String delimToCheck) {
		if (delimeters != null) {
			for (String delim : delimeters) {
				if (delim.equals(delimToCheck)) {
					return true;
				}
			}
		}
		return false;
	}

	public static int slashNLocation(String numberString) {
		for (int i = 0; i < numberString.length(); i++) {
			if (numberString.charAt(i) == '\n') {
				return i;
			}
		}

		return -1;
	}

//	public static void main(String args[]) throws Exception {
//		StringCalculator calculaterApp = new StringCalculator();
//		String numberWithoutDelimiter = "10,5\n8,";
//
//		System.out.println("Total Sum Case 1 = " + calculaterApp.add(numberWithoutDelimiter));
//
//		String numberString = "//[##][*]\n1##2##4*6";
//		System.out.println("Total Sum Case 2= " + calculaterApp.add(numberString));
//
//	}

}
