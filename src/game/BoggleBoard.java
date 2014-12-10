package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoggleBoard {
	private static final int BOARD_SIZE = 4;
	
	private Dictionary dictionary; //an English language dictionary
	private String[][] letters; //boggle board data structure 
	private String[][] dice;
	
	public BoggleBoard(){
		dictionary = new Dictionary();
		letters = new String[BOARD_SIZE][BOARD_SIZE];
		dice = new String[BOARD_SIZE*BOARD_SIZE][6];
		
		//load board from file
		File file = new File("board.txt");
		Scanner reader;
		try{
			reader = new Scanner(file);
			int row = 0;
			while(reader.hasNext()){
				String line = reader.nextLine();
				for (int i = 0; i < line.length(); i++){
					letters[row][i] = line.substring(i, i+1);
				}
				row++;
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
//		//load boggle dice from frequency chart
//		File file = new File("frequency_dice.txt");
//		Scanner reader;
//		try{
//			reader = new Scanner(file);
//			int row = 0;
//			int face = 0;
//			while(reader.hasNext()){
//				String die = reader.nextLine();
//				String[] letters = die.split(", ");
//				for (String letter : letters){
//					dice[row][face] = letter;
//					face++;
//				}
//				row++;
//				face = 0;
//			}
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		
//		int row = 0, col = 0;
//		//randomly assemble board by "tossing" each die and seeing which face it lands on
//		for (String[] die : dice){
//			int face = (int)(Math.random()*6);
//			letters[row][col] = die[face];
//			if (col % BOARD_SIZE == BOARD_SIZE-1){
//				row++;
//				col = 0;
//			}
//			else 
//				col++;
//		}
	}
	
	public boolean search(String word){
		for (int r = 0; r < letters.length; r++){
			for (int c = 0; c < letters[r].length; c++){
				if (letters[r][c].equalsIgnoreCase(word.substring(0, 1))){ //found first letter of word
					String path = "" + word.substring(0,1); 
					List<int[]> visitedPositions = new ArrayList<int[]>(); 
					int[] pos = {r, c};
					visitedPositions.add(pos);
					if (searchHelper(word, path, r, c, visitedPositions))
						return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean searchHelper(String word, String path, int lastRow, int lastCol, List<int[]> visitedPositions){
		if (path.equals(word))
			return true;
		
		int startRow = lastRow - 1;
		if (startRow < 0) 
			startRow = 0;
		int startCol = lastCol - 1;
		if (startCol < 0)
			startCol = 0; 
		
		for (int r = startRow; r <= lastRow+1 && r < letters.length; r++){
			for (int c = startCol; c <= lastCol+1 && c < letters[r].length; c++){
				if (hasBeenVisited(r, c, visitedPositions)) //can't use the same tile twice in a word
					continue;
					
				if (letters[r][c].equalsIgnoreCase(word.substring(path.length(), path.length()+1))){
					String newPath = path + word.substring(path.length(), path.length()+1);
					int[] pos = {r, c};
					visitedPositions.add(pos);
					if (searchHelper(word, newPath, r, c, visitedPositions)){
						return dictionary.search(word);
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean hasBeenVisited(int row, int col, List<int[]> visitedPositions){
		for (int[] pos : visitedPositions){
			if (row == pos[0] && col == pos[1])
				return true;
		}
		
		return false;
	}
	
	public void display(){
		for (int r = 0; r < letters.length; r++){
			for (int c = 0; c < letters[r].length; c++){
				System.out.print(letters[r][c] + "    ");
			}
			System.out.println();
		}
	}
}
