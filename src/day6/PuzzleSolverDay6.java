package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PuzzleSolverDay6 {

	public static void main(String[] args) {
		PuzzleSolverDay6 solution = new PuzzleSolverDay6();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		String currentGroup = "";
		int totalCharsPerGroup = 0;
	    int nPeople = 0;
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day6/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	    		if (line.equals("")) {
	    			totalCharsPerGroup += checkAnswers(nPeople, currentGroup);
	    			currentGroup = "";
	    			nPeople = 0;
	    		} else {
	    			currentGroup += line;
	    			nPeople += 1;
	    		}
	        }
	    	totalCharsPerGroup += checkAnswers(nPeople, currentGroup);
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		System.out.println("Number of chars: " + totalCharsPerGroup);
	}
	
	private int checkAnswers(final int number, final String group) {
		Set<Character> distinctChars = group.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(TreeSet::new));
		int nCharsEveryone = 0;
		for (Character aChar : distinctChars) {
			if(number == group.chars().filter(ch -> ch == aChar).count()) {
				nCharsEveryone += 1;
			}
		}
		return nCharsEveryone;
	}
}
