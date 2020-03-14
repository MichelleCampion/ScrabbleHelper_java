import java.util.*;
import java.io.*;

public class ScrabbleHelper
{
	public static void main(String [] args) throws Exception
	{

		FileIO reader = new FileIO();
        Scanner scan = new Scanner(System.in);

		//array of strings from dictionary file
		// user must add file location to use the dictionary.txt file
        String [] dictionary = reader.load("C:....//dictionary.txt");    //Reading the File as a String array


		//player's game letters
		int n = Integer.parseInt(scan.nextLine());
		char [] playerLetters = new char [n];		//{'s', 'd', 'r', 'e', 'l', 'e', 't', 'w'};

		for(int i=0; i<n; i++)
		{
			playerLetters[i] = scan.next().charAt(0);
		}
		System.out.println();

		PriorityQueue <ScrabbleWord> PQ = new PriorityQueue <ScrabbleWord> ();


		for(int i=0; i<dictionary.length; i++)
		{
			//checks each word in the dictionary string array to see if the player has the letters for each word
			if(isScrabbleWord(dictionary[i], playerLetters))
			{
				//if the player can make a word it is added to the priority queue based on word score
				ScrabbleWord word = new ScrabbleWord(dictionary[i]);
				PQ.add(word);
			}
		}

		//output 10 suggestions based on highest possible scores
		for(int j=0; j<10; j++)
		{
			ScrabbleWord wordFromQueue = new ScrabbleWord();
			wordFromQueue = PQ.poll();
			System.out.println(wordFromQueue.score + "  " + wordFromQueue.word);
			//System.out.println(wordFromQueue.score);
			System.out.println();
		}

	}

	//method checks if the player has the letters to make a certain word
	public static boolean isScrabbleWord(String text, char [] array)
	{
		String thisWord = text.substring(0, text.length()-1);

		boolean letterFound;

		char [] copyOfPlayerLetters = new char[array.length];

		//making a copy of player letters
		for(int i=0; i<array.length; i++)
		{
			copyOfPlayerLetters[i] = array[i];
		}


		//checking each character in the word to see if player has letters
		for(int j=0; j<text.length()-1; j++)
		{

			char letterToCheck = text.charAt(j);
			letterFound = false;

			for(int k=0; k<array.length; k++)
			{
				if(copyOfPlayerLetters[k] == letterToCheck)
				{
					copyOfPlayerLetters[k] = '+';
					letterFound = true;
					break;	//break out of inner loop once letter is found
				}
			}

			if(letterFound == false)
			{
				return false;
			}
		}

		return true;
	}
}

class ScrabbleWord implements Comparable < ScrabbleWord >
{
	String word;
	int score;
	String temp;

	char [] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};

		int [] letterScores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0};

	public ScrabbleWord()
	{

	}

	public ScrabbleWord(String s)
	{
		word = s;
		score = getScore(word);

	}

	public int getScore(String text)
	{
		int sum = 0;

		for(int i=0; i<text.length()-1; i++)
		{
			char letter = text.charAt(i);
			int index = indexOfLetter(letter);
			sum = sum + letterScores[index];
		}

		return sum;
	}

	public int indexOfLetter(char c)
	{
		for(int i=0; i<letters.length; i++)
		{
			if(letters[i]==c)
				return i;
		}
		return 0;
	}

	public int compareTo(ScrabbleWord object)
	{
		if(score < object.score)
			return 1;
		else if(score > object.score)
			return -1;
		else
			return 0;

	}
}

class FileIO{

  public String[] load(String file) {
    File aFile = new File(file);
    StringBuffer contents = new StringBuffer();
    BufferedReader input = null;
    try {
      input = new BufferedReader( new FileReader(aFile) );
      String line = null;
      int i = 0;
      while (( line = input.readLine()) != null){
        contents.append(line);
        i++;
        contents.append(System.getProperty("line.separator"));
      }
    }
    catch (FileNotFoundException ex) {
      System.out.println("Can't find the file - are you sure the file is in this location: "+file);
      ex.printStackTrace();
    }
    catch (IOException ex){
      System.out.println("Input output exception while processing file");
      ex.printStackTrace();
    }
    finally {
      try {
        if (input!= null) {
          input.close();
        }
      }
      catch (IOException ex) {
        System.out.println("Input output exception while processing file");
        ex.printStackTrace();
      }
    }
    String[] array = contents.toString().split("\n");
    for(String s: array){
        s.trim();
    }
    return array;
  }


  public void save(String file, String[] array) throws FileNotFoundException, IOException {

    File aFile = new File(file);
    Writer output = null;
    try {
      output = new BufferedWriter( new FileWriter(aFile) );
      for(int i=0;i<array.length;i++){
        output.write( array[i] );
        output.write(System.getProperty("line.separator"));
      }
    }
    finally {
      if (output != null) output.close();
    }
  }
}
