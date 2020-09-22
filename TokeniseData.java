import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokeniseData
{
	// Declaration of required variables starts.
	private String FilePath;
	private File file;
	private FileReader FR;
	private BufferedReader BR;
	private int RequestId; // This will contain request id to perform operations further.
	public static List<Map<String,Integer>> LOHM = new ArrayList<>(); // Storing HashMap of each page in List.
	public static Map<String,Integer> SWLHM = new HashMap<>(); // This HashMap will contain list of stopwords uploaded by user.
	public static Map<String,Integer> QHM1 = new HashMap<>(); // This HashMap will contain list of words in user query 1.
	public static Map<String,Integer> QHM2 = new HashMap<>(); // This HashMap will contain list of words in user query 2.
	private int query = 1;
	// Declaration of required variables ends.

	public TokeniseData(String FilePath) // This is a constructor used to initialize required global variable.
	{
		this.FilePath = FilePath; // This is FilePath being called from MainClass. This contains String of location where all the files to be scanned are stored.
	}

	public void TokeniseDocument(int RequestId) // This is process initiator method. RequestId 1 is for tokenising web pages. 2 is for tokenising Stopwords List if uploaded. 3 is for tokenising user query.
	{
		this.RequestId = RequestId;
		try
		{
			if(RequestId == 1) // Tokenising Web Pages.
			{
				for(int i=0;i<MainClass.URL_All.size();i++) // Loop runs for total number of URL's we have in our list.
				{
					Map<String, Integer> HM = new HashMap<>(); // This HashMap will contain all the words along with word count in that page for single link from out list of links and will be created new for each page.
					file = new File(FilePath+MainClass.URL_All.get(i));
					FR = new FileReader(file);
					BR = new BufferedReader(FR);
					StartTokenizing(HM); // Calling function to start tokenizing process.
					LOHM.add(HM); // Adding HashMap of single page into List of HashMaps.
				}
				for(int j=0;j<LOHM.size();j++) // This loop is to print contents of all HashMaps.
				{
					System.out.println("Tokens for " + MainClass.URL_All.get(j) + " with their occurances are : " +LOHM.get(j));
					System.out.println("=============================================================");
				}
			}
			else if(RequestId == 2) // Tokenising stopwords list if uploaded by user.
			{
				file = new File(FilePath);
				FR = new FileReader(file);
				BR = new BufferedReader(FR);
				StartTokenizing(SWLHM); // Triggering method to start tokenizing.
				RemoveStopWords.CommonWords = SWLHM; // Triggering method to remove stopwords from list of tokens for each web pages.
			}
			else if(RequestId == 3) // Tokenising Query.
			{
				System.out.println("=============================================================");
				QHM1.clear(); // Clearing HM to delete data of previous run.
				QHM2.clear(); // Clearing HM to delete data of previous run.
				StartTokenizing(QHM1); // This function is used to start tokenising data of query.
				StartTokenizing(QHM2); // This function is used to start tokenising data of query.
			}
			else // In case anything else is passed in error, display below images.
			{
				System.out.println("No logic exist to perform an operation for request id passed.");
			}
		}
		catch(Exception e) // Catching File not found exception here.
		{
			e.printStackTrace(); // Printing stack trace identify exact line containing exception or error.
		}
	}

	private void StartTokenizing(Map<String,Integer> HM) // This function is called to start tokenizing passed data.
	{
		String EachLine,EachLineWithoutTags; // Each line will contain HTML tags along with other words and EachLineWithoutTags will contain only words.
		try
		{
			if(RequestId == 1 || RequestId == 2)
			{
				while((EachLine = BR.readLine())!=null) // While document have line to read, loop will be executed.
				{
					EachLineWithoutTags = RemoveTags(EachLine); // Calling function to remove html tags and return line with words after removal of HTML tags.
					if(EachLineWithoutTags!=null && EachLineWithoutTags!="" && EachLineWithoutTags!=" " && EachLineWithoutTags.length()!=0) // If Line contains words and not blanks or null, below code will be executed.
					{
						String[] Words = EachLineWithoutTags.split(" "); // Splitting line into words by spaces.
						for(int j=0; j<Words.length;j++) // For total number of words in line, run below code.
						{
							Words[j].trim(); // Removing blanks from words.
							Words[j]=Words[j].toLowerCase(); // Handling upper case and lower case here.
							Words[j] = RemoveSpecialCharacters(Words[j], HM); // Calling function to remove all special characters it contains(if any) before or after word.
							if(Words[j].length() != 0) // After performing all above operations, if words is not empty, run below.
							{
								AddToHashMap(Words[j], HM); // If everything is fine, call function to add word into HashMap.
							}
						}
					}
				}
			}
			else if(RequestId == 3) // User input needs to be Tokenise.
			{
				if(UserQuery.userQuery1 != null && UserQuery.userQuery2 != null) // If user query is not null.
				{
					String[] Words = null;
					if(query == 1)
					{
						Words = UserQuery.userQuery1.split(" "); // Splitting line into words by spaces.
						query = 2;
					}
					else if(query == 2)
					{
						Words = UserQuery.userQuery2.split(" "); // Splitting line into words by spaces.
						query = 1;
					}
					for(int j=0; j<Words.length;j++) // For total number of words in line, run below code.
					{
						Words[j].trim(); // Removing blanks from words.
						Words[j]=Words[j].toLowerCase(); // Handling upper case and lower case here.
						Words[j] = RemoveSpecialCharacters(Words[j], HM); // Calling function to remove all special characters it contains(if any) before or after word.
						if(Words[j].length() != 0) // After performing all above operations, if words is not empty, run below.
						{
							AddToHashMap(Words[j], HM); // If everything is fine, call function to add word into HashMap.
						}
					}
				}
			}
		}
		catch(Exception e) // Catching any exception occurred while performing all above operations.
		{
			e.printStackTrace(); // Printing stack trace identify exact line containing exception or error.
		}
	}

	private String RemoveTags(String EachLine) // This function is used to remove HTML tags from passed line.
	{
		StringBuilder sb = new StringBuilder(); // This will be used to store String in character by character form.
		String EachLineWithoutTags; // This will store string from StringBuilder.
		if(EachLine.contains("<")) // In case given line contain "<" which is start of HTML tags, run below code.
		{
			for(int i=0;i<EachLine.length();i++) // Loop runs for each character in given line.
			{
				while(EachLine.charAt(i) != '<') // This finds "<" in given line.
				{
					sb.append(EachLine.charAt(i)); // This appends characters until "<" is found.
					i++; // This increments until "<" is found.
				}
				while(EachLine.charAt(i) != '>') // Run loop until ">" is found.
				{
					i++; // Increment character count until ">" is found.
				}
				if(EachLine.charAt(i) == '>' && i == EachLine.length()-2) // If HTML tag is closed and this is 2nd last character of line, do as below.
				{
					i++; // Increment counter by 1.
				}
			}
			EachLineWithoutTags = sb.toString(); // Converting appended StringBuilder to String and assigning to EachLineWithoutTags variable.
			return EachLineWithoutTags; // Return new string after removal of HTML tags.
		}
		else // In case given line does not contain "<" which is start of HTML tags, run below code.
		{
			return EachLine; // returning original String Line without any changes.
		}
	}

	private String RemoveSpecialCharacters(String Word, Map<String, Integer> HM)
	{
	// Declaration of required variables starts.
		// Below is list of all 32 characters on keyboard that can be replaced in String.
		char[] SpecialCharacterList = {'`','~','!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','}','[',']','\\','|',':',';','"','\'',',','.','/','?','<','>'};
		char[] IntegerValue = {'0','1','2','3','4','5','6','7','8','9'};
		StringBuilder sb = new StringBuilder(); // This is used to build String of characters after removing special characters.
		int found=0; // Check variable.
	// Declaration of required variables ends.
		
		for(int i=0; i<Word.length();i++) // Loop runs for length of whole Word.
		{
			found=0; // Initializing check variable.
			for(int j=0;j<SpecialCharacterList.length;j++) // Loop runs for all characters in SpecialCharacterList.
			{
				if((Word.charAt(i) == SpecialCharacterList[j]) && (i != Word.length()-2)) // If found on comparing and if special character location is not equal to 2 less than length of word length, do as below.
				{
					sb.append(" "); // In case any special character is present, replace it with blank space.
					found = 1; // Set check variable to 1.
					break; // Break For loop of checking special character from list.
				}
				else if((Word.charAt(i) == SpecialCharacterList[j]) && (i == Word.length()-2)) // If found on comparing and if special character location is 2 less than length of word length, do as below.
				{
					i++; // Increment if above is true so that words like "shouldn't" will perfectly be detected as "shouldnt".
				}
			}
			for(int j=0;j<IntegerValue.length;j++) // Loop runs for all characters in NumbersList.
			{
				if((Word.charAt(i) == IntegerValue[j])) // If found on comparing, do as below.
				{
					found = 2; // Set check variable to 2.
					break; // Break For loop of checking special character from list.
				}
			}
			if(found == 0) // If check variable is 0 i.e. if no special variable is found at position checked, do as below.
			{
				sb.append(Word.charAt(i)); // append character to sb.
			}
			else if(found == 2) // In case found == 2, that means this word contains numbers and we don't need it.
			{
				break; // Breaking loop.
			}
		}
		if(found != 2) // In case there are no numbers in word, do as below.
		{
			Word = sb.toString(); // Convert toString from list of characters.
			Word = IdentifyCorrectWord(Word, HM); // Now, identifying if string is correct variable or not.
		}
		else // In case there are numbers in word, do as below.
		{
			Word = ""; // Need to return blank as we do not need words with numerics.
		}
		return Word; // Return final word.
	}

	private String IdentifyCorrectWord(String Word, Map<String, Integer> HM)
	{
	// Declaration of required variables starts.
		// Below is list of all 26 alphabets in lower case on keyboard that cannot be replaced in String.
		char[] AlphabetList = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	// Declaration of required variables ends.

		String[] WordList = Word.split(" ");
		if(WordList.length>1)
		{
			for(int i=1;i<WordList.length;i++) // Run loop for number of words received in WordList[]-1. I am running it 1 less than actual count because 1 word at position 0 of list I am sending to main TokeniseData() method to add into HM from there. Words can be 1 or more than 1 in list and hence running it for words other than 1st word.
 			{
				WordList[i] = WordList[i].trim(); // Removing blank spaces from each words.
				StringBuilder sb = new StringBuilder(); // This is used to build string of correct words.
				for(int j=0; j<WordList[i].length();j++) // Loop runs for length of whole word from String array WordList. I am doing i+1 as I will return 1st word of my WordList and create HashMap of same in my main method.
				{
					for(int k=0;k<AlphabetList.length;k++) // Loop runs for all characters in SpecialCharacterList.
					{
						if(WordList[i].charAt(j) == AlphabetList[k]) // If found on comparing, do below.
						{
							sb.append(WordList[i].charAt(j)); // If its an alphabet, add to string builder.
						}
					}
				}
				Word = sb.toString(); // I will get correct word if there are words separated by blank space.
				// Example : Word "Power-House" will be converted in previous method(i.e. in RemoveSpecialCharacters()) to "Power House".
				// Power will be sent back to main method and House will be sent for hashing from here itself.
				if(Word!=null && Word!="" && Word!=" " && Word.length()!=0)
				{
					AddToHashMap(Word, HM); // Sent for hashing.
				}
			}
			return WordList[0].trim(); // Returning 1st word from new list.
		}
		else
		{
			return Word.trim(); // Return final word.
		}
	}

	private void AddToHashMap(String Word, Map<String, Integer> HM) // This function adds words to HM.
	{
		Word.trim(); // Final trimming if word contains any unnecessary blank space.
		if(Word!=null && Word!="" && Word!=" " && Word.length() != 0) // If word does not contain null or blanks or spaces, below code will be executed.
		{
			if(HM.containsKey(Word)) // If word is already in our HashMap, run below.
			{
				HM.put(Word,HM.get(Word)+1); // Increment count by 1.
			}
			else // If not already present in our HashMap, run below.
			{
				HM.put(Word,1); // Add new word in HashMap.
			}
		}
	}
}