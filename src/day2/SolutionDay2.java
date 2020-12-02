package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionDay2 {
	List<String> input = new ArrayList<>();
	String tempString;
	String password;
	String rule;
	char letterOfRule;
	String numbersOfRule;
	int startRangeRule;
	int endRangeRule;
	int validPasswords = 0;
	int validPasswordsSecondInterpretation = 0;
	
	public static void main(String[] args) {
		SolutionDay2 solution = new SolutionDay2();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		convertInputToList();
		for (String line : input) {
			tempString = line;
			parseInputLine();
			if (checkValidityFirstInterpretation()) {
				validPasswords += 1;
			}
			if (checkValiditySecondInterpretation()) {
				validPasswordsSecondInterpretation += 1;
			}
		}
		System.out.println("Number correct first interpretation: " + validPasswords);
		System.out.println("Number correct second interpretation: " + validPasswordsSecondInterpretation);
	}
	
	/** Converts to input to a list of integers */
	private void convertInputToList() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day2/inputDay2.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(line);
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** Parse a line to the password and the components of the rule */
	private void parseInputLine() {
		password = tempString.split(": ")[1];
		tempString = tempString.split(": ")[0];
		letterOfRule = tempString.charAt(tempString.length() - 1);
		tempString = tempString.split(" ")[0];
		startRangeRule = Integer.parseInt(tempString.split("-")[0]);
		endRangeRule = Integer.parseInt(tempString.split("-")[1]);
	}
	
	/** Check validity of a password */
	private boolean checkValidityFirstInterpretation() {
		long count = password.chars().filter(ch -> ch == letterOfRule).count();
		return (count >= startRangeRule && count <= endRangeRule);
	}
	
	/** Check validity of a password according to second interpretation*/
	private boolean checkValiditySecondInterpretation() {
		char firstPosition = password.charAt(startRangeRule - 1);
		char secondPosition = password.charAt(endRangeRule - 1);
		return firstPosition == letterOfRule ^ secondPosition == letterOfRule;
	}
}
