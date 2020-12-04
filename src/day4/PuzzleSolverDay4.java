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
	List<List<String>> passportComponents = new ArrayList<>();
	
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
		for (List<String> aPasswordsComponents : passportComponents) {
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
		final List<String> keys = new ArrayList<>();
		for (String component : components) {
			final String key = component.split(":")[0];
			keys.add(key);
		}
		passportComponents.add(keys);
	}
	
	/** Checks the validity of the passports */
	private boolean checkValidity(final List<String> components) {
		for (String requiredField : requiredFields) {
			if(!components.contains(requiredField)){
	            return false;
	        }
		}
		return true;
	}
}
