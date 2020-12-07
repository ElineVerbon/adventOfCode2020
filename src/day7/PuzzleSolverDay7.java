package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PuzzleSolverDay7 {

	List<String> input = new ArrayList<>();
	Map<String, List<String>> rules = new HashMap<>();
	
	public static void main(String[] args) {
		PuzzleSolverDay7 solution = new PuzzleSolverDay7();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInput();
		convertInputToMap();
		findABag("shinygold");
	}
	
	/** Converts to input to a list of integers */
	private void parseInput() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day7/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(line);
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** 
	 * Converts input to map 
	 * Example line: posh brown bags contain 5 dim coral bags, 1 plaid blue bag, 2 faded bronze bags, 2 light black bags.
	 */
	private void convertInputToMap() {
		for (String line : input) {
			if (line.contains("no other bag")) {
				continue;
			}
			final List<String> containedBags = new ArrayList<>();
			final String[] words = line.split(" ");
			final String key = words[0] + words[1];
			if (line.contains("no other bag")) {
				rules.put(key,  containedBags);
				continue;
			}
			final int nBagsContained = (int) line.chars().filter(ch -> ch == ',').count() + 1;
			IntStream.range(0, nBagsContained).forEach(
				n -> {
					final String containedBagType = words[words.length - 3 - 4 * n] + words[words.length - 2 - 4 * n];
					final int amount = Integer.parseInt(words[words.length - 4 - 4 * n]);
					IntStream.range(0, amount).forEach(
						b -> {
							containedBags.add(containedBagType);
				        }
				    );
		        }
		    );
			rules.put(key,  containedBags);
		}
	}
	
	List<String> bagsToBeSearched = new ArrayList<>();
	List<String> bagsFound = new ArrayList<>();
	
	private void findABag(final String bagType) {
		//add all bag types containing the given bag type
		findBagTypes(bagType);
		
		//search for bags containing the bag types found until no more
		while(bagsToBeSearched.size() > 0 ) {
			findBagTypes(bagsToBeSearched.get(0));
		}
		
		System.out.println(bagsFound.size());
	}
	
	private void findBagTypes(final String bagType) {
		for (Map.Entry<String,List<String>> entry : rules.entrySet()) {
			if (entry.getValue().contains(bagType)) {
				if (!bagsFound.contains(entry.getKey())) {
					bagsToBeSearched.add(entry.getKey());
					bagsFound.add(entry.getKey());
				}
			}
		}
		bagsToBeSearched.remove(bagType);
	}
}
