package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class Solution {
	
	List<Integer> input = new ArrayList<>();
	int firstNumberSetOf2 = 0;
	int secondNumberSetOf2 = 0;
	int firstNumberSetOf3 = 0;
	int secondNumberSetOf3 = 0;
	int thirdNumberSetOf3 = 0;
	
	
	public static void main(String[] args) {
		Solution solution = new Solution();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		convertInputToList();
		findTwoNumbers();
		System.out.println("Solution two numbers");
		System.out.println("Number 1: " + firstNumberSetOf2);
		System.out.println("Number 2: " + secondNumberSetOf2);
		System.out.println("Solution: " + firstNumberSetOf2 * secondNumberSetOf2);
		
		findThreeNumbers();
		System.out.println("Solution two numbers");
		System.out.println("Number 1: " + firstNumberSetOf3);
		System.out.println("Number 2: " + secondNumberSetOf3);
		System.out.println("Number 3: " + thirdNumberSetOf3);
		System.out.println("Solution: " + firstNumberSetOf3 * secondNumberSetOf3 * thirdNumberSetOf3);
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
						firstNumberSetOf2 = option1;
						secondNumberSetOf2 = option2;
					}
				}
			});
		});
	}
	
	/** loop through input and find three numbers that add up to 2020. */
	private void findThreeNumbers() {
		IntStream.range(0, input.size()).forEach(x -> {
			IntStream.range(0, input.size()).forEach(y -> {
				IntStream.range(0, input.size()).forEach(z -> {
					if(x != y && x != z && y != z) {
						int option1 = input.get(x);
						int option2 = input.get(y);
						int option3 = input.get(z);
						if (option1 + option2 + option3 == 2020) {
							firstNumberSetOf3 = option1;
							secondNumberSetOf3 = option2;
							thirdNumberSetOf3 = option3;
						}
					}
				});
			});
		});
	}
}