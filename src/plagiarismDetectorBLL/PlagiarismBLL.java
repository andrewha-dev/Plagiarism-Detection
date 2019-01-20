/**
 * 
 */
package plagiarismDetectorBLL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import exceptions.InvalidArgumentsException;
import classes.Tuple;
/**
 * @author Andrew
 *
 */
public class PlagiarismBLL
{
	//BLL stands for Business Logic Layer. Hence Plagiarism business logic layer
	private HashMap<String, HashSet<String>> synonymMap;
	//Only set to public for unit testing
	public Map<Tuple, Integer> firstFileTuplesMap = new LinkedHashMap<Tuple, Integer>();
	public Set<Tuple> secondFileTuplesSet = new HashSet<Tuple>();
	public boolean isValid;
	//Setting up local variables
	private int sizeOfTuple;
	public PlagiarismBLL()
	{
		synonymMap = new HashMap<String, HashSet<String>>();
	}
	
	//@params fileName1 - Name of first file to parse
	//				fileName2 - Name of second file to parse
	//				_sizeOfTuple - Size of tuples/amount of words per tuple
	//Purpose of this method: Will get the words from each file given, 
	//Afterwards sets up a linkedhash map of the first file's tuples
	//Sets up a set of the second files tuples
	public void setUpTuples(String fileName1, String fileName2, int _sizeOfTuple)
	{
		try {
		sizeOfTuple = _sizeOfTuple;
		List<String> fileOneWords = getWords(fileName1);
		List<String> fileTwoWords = getWords(fileName2);
		//Start Validation
		//Checking to make sure there aren't any empty words, bug fix for empty files
		if (fileOneWords.size() == 1)
			if (fileOneWords.get(0).equals(""))
					throw new InvalidArgumentsException(fileName1 + " is empty");
		
		if (fileTwoWords.size() == 1)
			if (fileTwoWords.get(0).equals(""))
					throw new InvalidArgumentsException(fileName2 + " is empty");
		
			//Validation to check if Tuples > then words in the files		
			if ((fileOneWords.size() < sizeOfTuple && fileTwoWords.size() < sizeOfTuple) || sizeOfTuple <= 0)
			{
					throw new InvalidArgumentsException("Invalid Tuple Length Specified, was given: " + sizeOfTuple);	

			}
			else
			{
				//Passes validation, lets create the tuples
				isValid = true;
					//if either file 1 or file 2 doesnt have enough for the tuple size, we set it to the tuple to be the file with smallest words
					if (fileOneWords.size() < sizeOfTuple || fileTwoWords.size() < sizeOfTuple)
					{
						if (fileOneWords.size() > fileTwoWords.size())
							sizeOfTuple = fileTwoWords.size();
						else
							sizeOfTuple = fileOneWords.size();
						
						System.out.println("Warning: One of the files contains less words then the tuple size specified.");
						System.out.println("Setting new Tuple Size to " + sizeOfTuple + " (setting tuple to the length of smallest amount words in file)");

					}//End Validation					
					
					//TUPLE CREATION STARTS HERE					
					//Setting up first tuple. 
					for(int i = 0; i <= fileOneWords.size() - sizeOfTuple; i++)
					{
						ArrayList<String> tupleWords = new ArrayList<String>(0);						
						//Looping for every N to get the tuples						
						for(int j = i; j < i + sizeOfTuple; j++)
						{
							tupleWords.add(fileOneWords.get(j));							
						}
						Tuple tuple = new Tuple(sizeOfTuple, tupleWords);
						//If this key is already existing then just increment the value by one, so we dont have a duplicate entry
						firstFileTuplesMap.put(tuple, firstFileTuplesMap.getOrDefault(tuple, 0) + 1);
					}
					
					//Setting up second tuple, THIS SHOULD PROBABLY BE IN ANOTHER FUNCTION
					for(int i = 0; i <= fileTwoWords.size() - sizeOfTuple; i++)
					{
						ArrayList<String> tupleWordsSecond = new ArrayList<String>(0);						
						for(int j = i; j < i + sizeOfTuple; j++)
						{
								tupleWordsSecond.add(fileTwoWords.get(j));
						}
						
						Tuple tuple = new Tuple(sizeOfTuple, tupleWordsSecond);
						secondFileTuplesSet.add(tuple);					
					}
				
			}
		}
		
		catch(InvalidArgumentsException e)
		{
			System.out.println(e.getMessage());
			isValid = false;
		}		
	}
	//How the algorithm works:
	//Using the fileTupleMap, will search through each one of the tuples, first looking for an exact match comparing it to the second
	//If there isn't an exact match, then start swapping the synonyms in and out and compare for a match
	public double calculateMatch()
	{
		int tuplesMatched = 0;
		int tuplesTotal = 0;
		//Checking to see if exact phrase matches
		for(Tuple firstFileTuple: firstFileTuplesMap.keySet())
		{
			
			if (secondFileTuplesSet.contains(firstFileTuple))
			{
				tuplesMatched += firstFileTuplesMap.get(firstFileTuple);
			}
			else
			{
				ArrayList<String> firstFileTupleWords = firstFileTuple.getWords();
				//Checking each individual tuple to see if it contains the synonym
				for(Tuple secondFileTuple : secondFileTuplesSet)
				{
					ArrayList<String> secondFileTupleWords = secondFileTuple.getWords();
					int i = 0;
					while (i < firstFileTupleWords.size())
					{
						String firstWord = firstFileTupleWords.get(i);
						String secondWord = secondFileTupleWords.get(i);
						
						if (!firstWord.equals(secondWord) && (!synonymMap.containsKey(firstWord) 
								||!synonymMap.containsKey(secondWord) || !synonymMap.get(firstWord).contains(secondWord)))
						{
							break;
						}
						i++;
					}
					
					if (i == firstFileTupleWords.size()) {
						tuplesMatched += firstFileTuplesMap.get(firstFileTuple);
						break;
					}							
						
				}
			}
			tuplesTotal += firstFileTuplesMap.get(firstFileTuple);
		}
		System.out.println("Tuples Matched: " + tuplesMatched);
		System.out.println("Total Tuples: " + tuplesTotal);
		double res = (double) tuplesMatched / tuplesTotal * 100;
		if (Double.isNaN(res))
			return 0;
		else
		return (double) tuplesMatched / tuplesTotal * 100;
	}
	
	//Removes all everything but letters and whitespaces from the files
	public List<String> cleanFileAndReturnWords(String text)
	{
		String onlyLettersAndSpace = text.replace("[^a-zA-Z ]", "");
		onlyLettersAndSpace = onlyLettersAndSpace.toLowerCase();
		
		return (List<String>) Arrays.asList(onlyLettersAndSpace.split("\\s+"));
	}
	
	//Retrieves all the words in the specified file
	public List<String> getWords(String fileName)
	{
		List<String> words;
		try {
			words = new ArrayList<String>();
			StringBuilder sb = new StringBuilder("");
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String fileSentence = "";
			
			while((fileSentence = reader.readLine()) != null)
			{
				sb.append(fileSentence  + " ");
			}
			words = (List<String>) cleanFileAndReturnWords(sb.toString());
			reader.close();
			if (words.size() <= 0)
			{
				throw new IOException("is empty");
			}
		}
		catch(IOException e)
		{
			System.out.println("Error reading " + fileName + " : " + e.getMessage());
			return new ArrayList<String>();
		}
		
		return words;

	}
	
	public void generateSynonymsMap(String fileName)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String lineOfSynonyms;
			while ((lineOfSynonyms = reader.readLine()) != null)
			{
				lineOfSynonyms = lineOfSynonyms.toLowerCase();
				String[] synonymsArray = lineOfSynonyms.split(" ");
				//Algorithm
				//1.) First take each element in the array and make each one a key for
				//2.) Create set out of the the elements
				//3.) Add to HashMap using each key, and the set as the value
				List<String> synonymsArrayList =  (List<String>) Arrays.asList(synonymsArray);
				HashSet<String> synonymsSet = new HashSet<String>();
				synonymsSet.addAll(synonymsArrayList);
				
				for(String key: synonymsArrayList)
				{
					 synonymMap.put(key, synonymsSet);
				}					
				
			}			
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error reading synonym file: " + e.getMessage());
		}
	}
	
	

}
