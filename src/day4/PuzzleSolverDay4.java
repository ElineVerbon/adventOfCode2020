package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PuzzleSolverDay4 {
	final List<String> requiredFields = Arrays.asList(new String[]{"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"});
	final List<String> optionalFields = Arrays.asList(new String[]{"cid"});
	
	List<String> passports = new ArrayList<>();
	List<Map<String, String>> passportComponents = new ArrayList<>();
	
	int nValid = 0;
	
	public static void main(String[] args) {
		PuzzleSolverDay4 solution = new PuzzleSolverDay4();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInputToPassports();
		for (String passport : passports) {
			getComponentsPerPassport(passport);
		}
		for (Map<String, String> aPasswordsComponents : passportComponents) {
			if (checkValidity(aPasswordsComponents)) {
				nValid += 1;
			}
		}

		System.out.println("Total number of passports: " + passports.size());
		System.out.println("Number of valid passports: " + nValid);
	}
	
	/** Parses input */
	private void parseInputToPassports() {
	    String currentPassport = "";
	    boolean newPassport = true;
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day4/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	    		if (line.equals("")) {
	    			passports.add(currentPassport);
	    			currentPassport = "";
	    			newPassport = true;
	    		} else {
	    			if (newPassport) {
	    				newPassport = false;
	    			} else {
	    				currentPassport += " ";
	    			}
	    			currentPassport += line;
	    		}
	        }
	    	if (!newPassport) {
	    		passports.add(currentPassport);
	    	}
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** Gets the components from the passports */
	private void getComponentsPerPassport(final String passport) {
		final String[] components = passport.split(" ");
		final Map<String, String> mapOfComponents = new HashMap<>();
		for (String component : components) {
			final String key = component.split(":")[0];
			final String value = component.split(":")[1];
			mapOfComponents.put(key, value);
		}
		passportComponents.add(mapOfComponents);
	}
	
	/** Checks the validity of a passport */
	private boolean checkValidity(final Map<String, String> components) {
		for (String requiredField : requiredFields) {
			if(!components.keySet().contains(requiredField)){
	            return false;
	        }
		}
		
		if(!checkBirthYear(components.get("byr"))) {
			return false;
		}
		
		if(!checkIssueYear(components.get("iyr"))) {
			return false;
		}
		
		if(!checkExpirationYear(components.get("eyr"))) {
			return false;
		}
		
		if(!checkHeight(components.get("hgt"))) {
			return false;
		}
		
		if(!checkHairColor(components.get("hcl"))) {
			return false;
		}
		
		if(!checkEyeColor(components.get("ecl"))) {
			return false;
		}
		
		if(!checkPassportID(components.get("pid"))) {
			return false;
		}
		
		return true;
	}
	
	/** Check byr */
	private boolean checkBirthYear(final String year) {
		return checkInt(year, 1920, 2002);
	}
	
	/** Check iyr */
	private boolean checkIssueYear(final String year) {
		return checkInt(year, 2010, 2020);
	}
	
	/** Check eyr */
	private boolean checkExpirationYear(final String year) {
		return checkInt(year, 2020, 2030);
	}
	
	/** Check int */
	private boolean checkInt(final String anInt, final int minValue, final int maxValue) {
		try {
			int y = Integer.parseInt(anInt);
			return (y >= minValue && y <= maxValue);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/** Check height */
	private boolean checkHeight(final String height) {
		final String unit = height.substring(height.length()-2, height.length());
		if (unit.equals("in")) {
			return checkInt(height.substring(0, height.length()-2), 59, 76);
		} else if (unit.equals("cm")) {
			return checkInt(height.substring(0, height.length()-2), 150, 193);
		}
		return false;	
	}
	
	/** Check hair color */
	private boolean checkHairColor(final String hairColor) {
		if(hairColor.charAt(0) != '#') {
			return false;
		}
		String rest = hairColor.substring(1);
		if (rest.length() != 6) {
			return false;
		}
		
		List<Character> possibleChars = Arrays.asList(new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'});
		
		for (int i = 0; i < rest.length(); i++){
		    char c = rest.charAt(i);        
		    if (!possibleChars.contains(c)) {
		    	return false;
		    }
		}
		
		return true;
	}
	
	/** Check eye color */
	private boolean checkEyeColor(final String eyeColor) {
		List<String> possibleEyeColors = Arrays.asList(new String[]{"amb", "blu", "brn", "gry", "grn", "hzl", "oth"});
		return possibleEyeColors.contains(eyeColor);
	}
	
	/** Check passport id */
	private boolean checkPassportID(final String passportID) {
		if (passportID.length() != 9) {
			return false;
		}
		try {
			Integer.parseInt(passportID);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
