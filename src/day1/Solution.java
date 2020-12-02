package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class Solution {
	
	List<Integer> input = new ArrayList<>();
	int firstNumber = 0;
	int secondNumber = 0;
	
	
	public static void main(String[] args) {
		Solution solution = new Solution();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		convertInputToList();
		findTwoNumbers();
		System.out.println("Number 1: " + firstNumber);
		System.out.println("Number 1: " + secondNumber);
		System.out.println("Solution: " + firstNumber * secondNumber);
	}
	
	/** Converts to input to a list of integers */
	private void convertInputToList() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day1/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(Integer.parseInt(line));
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** loop through input twice and find two number that add up to 2020. */
	private void findTwoNumbers() {
		IntStream.range(0, input.size()).forEach(n -> {
			IntStream.range(0, input.size()).forEach(x -> {
				if(n != x) {
					int option1 = input.get(n);
					int option2 = input.get(x);
					if (option1 + option2 == 2020) {
						firstNumber = option1;
						secondNumber = option2;
					}
				}
			});
		});
	}
}