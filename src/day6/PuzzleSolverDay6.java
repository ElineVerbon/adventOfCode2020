package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PuzzleSolverDay6 {

	List<String> groups = new ArrayList<>();
	
	public static void main(String[] args) {
		PuzzleSolverDay6 solution = new PuzzleSolverDay6();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInputPerGroup();
		System.out.println("Number of chars: " + countChars());
		System.out.println("Total number of groups: " + groups.size());
	}
	
	/** Parses input */
	private void parseInputPerGroup() {
	    String currentGroup = "";
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day6/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	    		if (line.equals("")) {
	    			groups.add(currentGroup);
	    			currentGroup = "";
	    		} else {
	    			currentGroup += line;
	    		}
	        }
	    	groups.add(currentGroup);
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** Counts the chars per question */
	private int countChars() {
		int totalCharsPerGroup = 0;
		for (String group : groups) {
			Set<Character> uniqueChars = group.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(TreeSet::new));
			totalCharsPerGroup += uniqueChars.size();
		}
		return totalCharsPerGroup;
	}
}
