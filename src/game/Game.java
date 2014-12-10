package game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
	private BoggleBoard board;
	private int score;
	private ArrayList<String> foundWords;
	private int timeLeft = 60;
	
	public Game(){
		board = new BoggleBoard();
		score = 0;
		foundWords = new ArrayList<String>();
	}
	
	public void run(){
		System.out.println("Welcome to Boggle Java Edition!");
		System.out.println("Enter a word you see on the board or enter \"QUIT\" in all caps to quit");
		System.out.println("You will have exactly 60 seconds to find as many words as you can! Type in 'TIME?' in all caps to see how much time you have remaining");
		System.out.println();
		board.display();
		Scanner reader = new Scanner(System.in);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				System.out.println("Time up! Your final score is " + score);
				System.out.println("Words found: " + foundWords.size());
				for (String word : foundWords){
					System.out.println(word);
				}
				System.exit(0);
			}
		}, 60*1000);
		
		Timer counter = new Timer();
		counter.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				timeLeft--;
			}
		}, 1000, 1000);

		while(true){
			String input = reader.nextLine();
			if (input.equals("QUIT")){
				System.out.println("Goodbye! Your final score is: " + score);
				System.out.println("Words found: " + foundWords.size());
				for (String word : foundWords){
					System.out.println(word);
				}
				System.exit(0);
			}
			
			if (input.equals("TIME?")){
				System.out.println("Remaining Time: " + timeLeft);
			}
			
			if (input.length() < 3){
				System.out.println("WORD MUST BE THREE LETTERS OR LONGER!");
				continue;
			}
			
			if (alreadyFound(input)){
				System.out.println("You already found that word! Nice try");
				continue;
			}
			
			if (board.search(input)){
				score += getScore(input);
				foundWords.add(input);
			}
			System.out.println("Your score is : " + score);
		}
	}
	
	private int getScore(String word){
		if (word.length() < 7){
			return word.length() - 2;
		} 
		else if (word.length() == 7)
			return 6;
		else if (word.length() == 8)
			return 10;
		else
			return 15;
	}
	
	private boolean alreadyFound(String word){
		for (String w : foundWords){
			if (w.equalsIgnoreCase(word)){
				return true;
			}
		}
		
		return false;
	}
}
