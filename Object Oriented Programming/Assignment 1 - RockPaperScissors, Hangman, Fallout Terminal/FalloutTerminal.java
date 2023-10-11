package com.bham.pij.assignments.rps;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author iankenny
 */

import java.util.Random;
import java.util.Scanner;

public class FalloutTerminal {

	/**
	 * This method creates a single line of the terminal display.
	 * @param startAddress The first address of the line.
	 * @param wordProbability If less than or equal to 0.6 the line should contain one word.
	 * @return The line for display.
	 */
	public String buildDisplayLine(int startAddress, float wordProbability) {

		final String gibberish = "!@#$%^&*()_-=+[];,./|{}:<>?";
		String gibString = "";
		String gibStringChance = "";
		Random random = new Random();
		int i;
		int Length = 30;
		int LengthChance = 22;
		char[] Gib = new char[Length];
		char[] GibChance = new char[LengthChance];
		for(i = 0; i<=Length-1; i++)
			Gib[i] = gibberish.charAt(random.nextInt(gibberish.length()));
		for(i = 0; i< Gib.length; i++)
			gibString += Gib[i];
		for(i = 0; i<=LengthChance-1; i++)
			GibChance[i] = gibberish.charAt(random.nextInt(gibberish.length()));
		for(i = 0; i< GibChance.length; i++)
			gibStringChance += GibChance[i];

		gibStringChance += getRandomWord().toUpperCase();

			String returningString = "";
		if(wordProbability<=0.6) returningString = "0x" + Integer.toHexString(startAddress).toUpperCase() + "\t" + gibStringChance + "\t" + "0x" + Integer.toHexString(startAddress + 240).toUpperCase() + "\t" + gibString;
		else  returningString = "0x" + Integer.toHexString(startAddress).toUpperCase() + "\t" + gibString + "\t" + "0x" + Integer.toHexString(startAddress + 240).toUpperCase() + "\t" + gibString;
		addSelectedWord(getRandomWord());
		return returningString;
	}

/*
 ***************************************************************************************	
 */

	private static final int START_ADDRESS = 0x9380;
	private static final int DISPLAY_HEIGHT = 20;
	private static final int NUM_CHARACTERS_PER_ROW = 60;
	private static final float WORD_PROB_1 = 0.6f;
	private static final int WORD_LENGTH = 8;
	private static final int NUM_GUESSES_ALLOWED = 4;
	private static final int BYTE_SIZE = 8;
	private static Random rand;
	private static final String[] words = {
			"flourish",
			"appendix",
			"separate",
			"unlawful",
			"platform",
			"shoulder",
			"marriage",
			"attitude",
			"reliable",
			"contempt",
			"prestige",
			"evaluate",
			"division",
			"birthday",
			"orthodox",
			"appetite",
			"perceive",
			"pleasant",
			"surprise",
			"elephant",
			"incident",
			"medieval",
			"absolute",
			"dominate",
			"designer",
			"misplace",
			"possible",
			"graduate",
			"solution",
			"governor"
	};
	
	private static String password;

	private static ArrayList<String> selectedWords = new ArrayList<String>();
	
	private void run() {
		
		System.out.println(buildDisplay());
		
		Scanner in = new Scanner(System.in);
		
		int guessCount = NUM_GUESSES_ALLOWED;
		
		boolean done = false;
		
		do {
			System.out.println("> Password required.");
			System.out.println("> Attempts remaining = " + guessCount);
			System.out.println(">");
			String guess = in.nextLine();
			
			if (guess.equalsIgnoreCase(getPassword())) {
				System.out.println("> Access granted.");
				done = true;
			}
			
			else {
				--guessCount;
				System.out.println("> Access denied.");
				System.out.println("> Likeness = " + getCorrectCount(guess, password));
			}	
		} while (guessCount > 0 && !done);
		
		if (guessCount == 0) {
			System.out.println("> Initiating lockout");
		}
		
		in.close();
	}
	
	private int getCorrectCount(String guess, String password) {
		
		int count = 0;
		
		for (int i = 0; i < guess.length(); i++) {			
			if (guess.charAt(i) == password.charAt(i)) {
				count++;
			}
		}
		
		return count;
	}
		
	private String getRandomWord() {
		return words[rand.nextInt(words.length)];
	}
	
	private String buildDisplay() {
		
		String ret = "";
		
		int address = START_ADDRESS;
						
		for (int i = 0; i < DISPLAY_HEIGHT; i++) {
											
			float rf = rand.nextFloat();
			
			String line = buildDisplayLine(address, rf);

			ret += line + "\n";
			
			address += BYTE_SIZE * NUM_CHARACTERS_PER_ROW;
		}
		
		setRandomPassword();
		
		return ret;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pw) {
		password = pw;
	}

	private void addSelectedWord(String word) {
		selectedWords.add(word);
	}
	
	public void setRandomPassword() {
		password = selectedWords.get(rand.nextInt(selectedWords.size())).toLowerCase();
	}

	public FalloutTerminal() {
		rand = new Random(System.currentTimeMillis());	
	}
	
	public static void main(String[] args) {
		new FalloutTerminal().run();
	}
}
