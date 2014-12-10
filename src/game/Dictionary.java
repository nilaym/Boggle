package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
	private Map<String, ArrayList<String>> dict;
	
	public Dictionary(){
		dict = new HashMap<String, ArrayList<String>>();
		
		File file = new File("dictionary.txt");
		try{
			Scanner reader = new Scanner(file);
			ArrayList<String> words = new ArrayList<String>();
			String currentLetter = "a";
	
			while (reader.hasNext()){
				String word = reader.nextLine();
				String startingLetter = word.substring(0, 1); //take the beginning letter of the word
				if (!startingLetter.equalsIgnoreCase(currentLetter)){ //if the next word in the dictionary starts with a different letter than the last one
					dict.put(currentLetter, words); //add the entire current letter list to dict
					currentLetter = startingLetter;
					words = new ArrayList<String>(); //reset the current letter list 
					words.add(word); 
				}else{
					words.add(word);
				}
				
			}
		} catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//binary search method
	public boolean search(String word){
		String startingLetter = word.substring(0,1).toLowerCase();
		ArrayList<String> words = dict.get(startingLetter);
		word = word.toLowerCase();
		
		int first = 0;
		int last = words.size()-1;
		int middle = (first+last)/2;
		
		while(first <= last){
			middle = (first+last)/2;
			
			if (words.get(middle).equals(word)){
				return true;
			} 
			
			if (words.get(middle).compareTo(word) < 0){
				first = middle + 1;
			}else if (words.get(middle).compareTo(word) > 0){
				last = middle - 1;
			}
		}
		
		return false;
	}
}
