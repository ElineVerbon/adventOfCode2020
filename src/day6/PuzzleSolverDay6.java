package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PuzzleSolverDay6 {

	Map<Integer, List<String>> groups = new HashMap<>(); 
	
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
	    int nPeople = 0;
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day6/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	    		if (line.equals("")) {
	    			if (groups.get(nPeople) == null) {
	    				List<String> firstGroup = new ArrayList<>();
	    				firstGroup.add(currentGroup);
	    				groups.put(nPeople, firstGroup);
	    			} else {
	    				List<String> existingGroups = groups.get(nPeople);
	    				existingGroups.add(currentGroup);
	    				groups.put(nPeople, existingGroups);
	    			}
	    			currentGroup = "";
	    			nPeople = 0;
	    		} else {
	    			currentGroup += line;
	    			nPeople += 1;
	    		}
	        }
	    	if (groups.get(nPeople) == null) {
				List<String> firstGroup = new ArrayList<>();
				firstGroup.add(line);
				groups.put(nPeople, firstGroup);
			} else {
				List<String> existingGroups = groups.get(nPeople);
				existingGroups.add(currentGroup);
				groups.put(nPeople, existingGroups);
			}
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** Counts the chars per group */
	private int countChars() {
		int totalCharsPerGroup = 0;
		for (Map.Entry<Integer, List<String>> entry : groups.entrySet()) {
		    Integer nPeople = entry.getKey();
		    List<String> groupStrings = entry.getValue();
		    for (String group : groupStrings) {
		    	totalCharsPerGroup += checkAnswers(nPeople, group);
		    }
		}
		return totalCharsPerGroup;
	}
	
	private int checkAnswers(final int number, final String group) {
		Set<Character> distinctChars = group.chars() .mapToObj(c -> (char) c).collect(Collectors.toCollection(TreeSet::new));
		int nCharsEveryone = 0;
		for (Character aChar : distinctChars) {
			if(number == group.chars().filter(ch -> ch == aChar).count()) {
				nCharsEveryone += 1;
			}
		}
		return nCharsEveryone;
	}
}
