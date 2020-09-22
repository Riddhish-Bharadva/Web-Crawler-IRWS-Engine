import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class RemoveStopWords
{
	// Declaration of required variables starts.
	public static Map<String,Integer> CommonWords = new HashMap<>();
	private String StopwordsFilePath;
	// Declaration of required variables ends.

	public void IdentifyStopWords(String Option)
	{
		// Declaration of required variables starts.
		Set<String> Keys = TokeniseData.LOHM.get(0).keySet();
		// Declaration of required variables ends.

		if(Option.equals("0")) // In case there are more than 1 web pages found earlier, Option will be 0.
		{
			for(String EachKey:Keys) // Taking all the keys one by one to compare with other HashMaps of other pages.
			{
				int Found = 1;
				for(int i=1; i<TokeniseData.LOHM.size(); i++) // Running loop to compare data with all HashMaps of all pages.
				{
					if(TokeniseData.LOHM.get(i).containsKey(EachKey)) // If Key is found, do as below.
					{
						Found = Found + 1; // Increment check variable by 1.
					}
				}
				if(Found == TokeniseData.LOHM.size()) // If check variable is not equal to 1, do as below.
				{
					CommonWords.put(EachKey,0); // Put in collection of common words.
				}
			}
			System.out.println("Identified stopwords after scanning all the web pages are : "+CommonWords.keySet());
			for(int iterate=0;iterate<1;)
			{
				System.out.println("=============================================================");
				System.out.println("Select any one from the below options : ");
				System.out.println("Enter 1 if you want to continue removing stopwords identified in above list.");
				System.out.println("Enter 2 if you want to upload your own stopwords list using flat file.");
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				Option = sc.nextLine();
				if(Option.equals("1") || Option.equals("2"))
				{
					iterate++;
				}
			}
			ProceedWithOption(Integer.parseInt(Option)); // This will proceed further with decision of using either found stopwords or to take new list of stopwords from user.
		}
		else // In case there is only 1 web page, proceed with Option 2 in decision of using stopwords.
		{
			ProceedWithOption(2); // Pass 2 in function stopwords decision to take new list of stopwords from user.
		}
	}

	private void ProceedWithOption(int Option) // This function will check decision of which stopwords to use and proceed accordingly.
	{
		if(Option == 1) // If option is 1, proceed with found stopwords.
		{
			System.out.println();
			System.out.println("=============================================================");
			System.out.println("You choose option to continue with identified Stopwords.");
			System.out.println("=============================================================");
		}
		else if(Option == 2) // If option is 2, proceed with new stopwords list given by user.
		{
			System.out.println();
			System.out.println("=============================================================");
			System.out.println("Please enter stopwords file location with filename and file extension (only files with extension .txt are accepted) to upload stopwords list and proceed further.");
			System.out.println("Eg.: D:\\IRWS\\RepeatAssignment\\StopWords.txt");
			@SuppressWarnings("resource")
			Scanner sc1 = new Scanner(System.in);
			StopwordsFilePath = sc1.nextLine();
			TokeniseData TD = new TokeniseData(StopwordsFilePath); // Do not remove this line else StopwordsFilePath will not be sent.
			TD.TokeniseDocument(2); // Passing Request Id 2 to tokenize words in stopwords list.
			System.out.println("=============================================================");
			System.out.println("Stopwords identified are : "+CommonWords.keySet());
			System.out.println("=============================================================");
		}
		else // If no option match, do not proceed.
		{
			System.out.println("Give appropriate option and try again.");
		}
		RemoveWords(1); // Call function RemoveWords() to remove stopwords from all web pages.
	}

	public void RemoveWords(int Option) // This method is responsible to remove all stopwords from HashMap of all tokens we created previously.
	{
		System.out.println("Below are tokens after removing stop words from list.");
		if(Option == 1) // If option is 1, remove stopwords from HM list of web pages.
		{
			for(int i=0;i<MainClass.URL_All.size();i++) // This loop runs for all pages.
			{
				Map<String,Integer> Temp = new HashMap<>();
				for(String Key:TokeniseData.LOHM.get(i).keySet()) // This loop runs for all tokens in each page.
				{
					if(CommonWords.containsKey(Key)) // This condition checks if Key is in list of stopwords.
					{
						Temp.put(Key,0); // If found, set for removal from HashMap.
					}
					else if(Key.length() == 1) // Adjusting for any unnecessary trimmed words or any unnecessary words.
					{
						Temp.put(Key, 0); // If true, set for removal from HashMap.
					}
				}
				for(String Remove:Temp.keySet()) // Looping till there are values in Temp HashMap.
				{
					TokeniseData.LOHM.get(i).remove(Remove); // Removing all values.
				}
			}
			System.out.println("=============================================================");
			for(int j=0;j<TokeniseData.LOHM.size();j++) // This loop is to print contents of all HashMaps.
			{
				System.out.println("Tokens for " + MainClass.URL_All.get(j) + " with their occurances are : " +TokeniseData.LOHM.get(j));
				System.out.println("=============================================================");
			}
		}
		else if(Option == 2) // If option is 2, remove stopwords from user queries.
		{
			int query = 1;
			for(int i=0;i<2;i++) // This loop will run for 2 time as we are taking 2 queries as input.
			{
				Map<String,Integer> TempKey = new HashMap<>();
				Map<String,Integer> queryKey = null;
				if(query == 1) // For query 1.
				{
					queryKey = TokeniseData.QHM1;
				}
				else if(query == 2) // For query 2.
				{
					queryKey = TokeniseData.QHM2;
				}
				for(String KeyWords:queryKey.keySet()) // This loop runs for all tokens in each page.
				{
					if(CommonWords.containsKey(KeyWords)) // This condition checks if Key is in list of stopwords.
					{
						TempKey.put(KeyWords,0); // Adding in temp HM for removal.
					}
					else if(KeyWords.length() == 1) // Adjusting for any unnecessary trimmed words or any unnecessary words.
					{
						TempKey.put(KeyWords,0); // Adding in temp HM for removal.
					}
				}
				if(query == 1) // If query 1.
				{
					for(String Temp:TempKey.keySet()) // This loop runs for all keys in Q1.
					{
		 				TokeniseData.QHM1.remove(Temp); // Removing data from Q1.
					}
					query = 2; // Assigning query = 2 to run loop for next query.
				}
				else if(query == 2) // For query 2.
				{
					for(String Temp:TempKey.keySet()) // This loop runs for all keys in Q2.
					{
		 				TokeniseData.QHM2.remove(Temp); // Removing data from Q2.
					}
					query = 1; // Assigning query = 1 to run loop for 1st query on next run.
				}
			}
			System.out.println("=============================================================");
			System.out.println("Tokens for Query1 with their occurances are : " +TokeniseData.QHM1);
			System.out.println("=============================================================");
			System.out.println("Tokens for Query2 with their occurances are : " +TokeniseData.QHM2);
			System.out.println("=============================================================");
		}
	}
}