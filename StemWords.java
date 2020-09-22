import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StemWords
{
	// Declaration of required variables starts.
	public static List<Map<String,Integer>> LOHMSW = new ArrayList<>(); // Storing HashMap of stemmed words for each web page in List.
	public static List<Map<String,Integer>> LOUQ = new ArrayList<>(); // Storing HashMap of user query in list.
	public static Map<String,Integer> All_Words_HM = new HashMap<>(); // This HashMap contains all words found in all documents after stemming them.
	public static Map<String,Integer> All_Words_UserQuery = new HashMap<>(); // This HashMap contains all words found in all documents after stemming them.
	public static Map<String,Integer> UserQuery1 = new HashMap<>(); // This HM will contain all words of user query after stemming.
	public static Map<String,Integer> UserQuery2 = new HashMap<>(); // This HM will contain all words of user query after stemming.
	// Declaration of required variables ends.

	public void StartStemmer(int Option)
	{
		Stemmer St = new Stemmer(); // Creating object of Stemmer class.
		String NewWordAfterStemming;
		if(Option == 1) // Stem words from HM of web pages.
		{
			for(int i=0; i<TokeniseData.LOHM.size();i++) // This loop length is equal to number of HashMaps of tokens created for all pages.
			{
				Map<String,Integer> HM = new HashMap<>();
				for(String Keys:TokeniseData.LOHM.get(i).keySet()) // This loop length is equal to number of words in each HashMap.
				{
					Keys = Keys.trim(); // Trimming words before passing into Porter Stemmer engine.
//					System.out.println("Word is : "+Keys + " Value is : "+TokeniseData.LOHM.get(i).get(Keys));
					for(int j=0;j<Keys.length();j++) // This loop length is equal to length of each Keys in HashMap.
					{
						St.add(Keys.charAt(j)); // This will send character by character value of each word to stemmer class.
					}
					St.stem(); // This triggers stemming of word function.
					NewWordAfterStemming = St.toString(); // Fetching stemmed word from engine.
					NewWordAfterStemming = NewWordAfterStemming.trim(); // Trimming to remove blank spaces from word.
//					System.out.println("New word after stemming is : "+NewWordAfterStemming);
					if(HM.containsKey(NewWordAfterStemming)) // Checking if stemmed word was previously found in document?
					{
						HM.put(NewWordAfterStemming, HM.get(NewWordAfterStemming)+TokeniseData.LOHM.get(i).get(Keys)); // If present, it will increment value by 1.
					}
					else // If not found, do as below.
					{
						HM.put(NewWordAfterStemming, TokeniseData.LOHM.get(i).get(Keys)); // First fetching value of Key from original HM and then add new entry for stemmed word in LOHMSW.
					}
					if(All_Words_HM.containsKey(NewWordAfterStemming)) // Check if word is previously entered in all words list?
					{
						All_Words_HM.put(NewWordAfterStemming, All_Words_HM.get(NewWordAfterStemming)+1); // If found, increment value by 1.
					}
					else // If not found, do as below.
					{
						All_Words_HM.put(NewWordAfterStemming, TokeniseData.LOHM.get(i).get(Keys)); // Add new entry in for stemmed word.
					}
				}
				LOHMSW.add(HM); // Finally add HashMap of each document in list.
			}
			System.out.println("Below are words of each web page along with their occurance count after stemming.");
			System.out.println("=============================================================");
			for(int i=0; i<LOHMSW.size();i++) // This loop length is equal to number of HashMaps of tokens created for all pages.
			{
				System.out.println("Stemmed words for " + MainClass.URL_All.get(i) + " are : " +LOHMSW.get(i));
				System.out.println("=============================================================");
			}
		}
		else if(Option == 2) // Stem words of user queries.
		{
			UserQuery1.clear(); // Clearing all data of previous run if any.
			UserQuery2.clear(); // Clearing all data of previous run if any.
			LOUQ.clear(); // Clearing all data of previous run if any.
			All_Words_UserQuery.clear(); // Clearing all data of previous run if any.
			for(String Keys:TokeniseData.QHM1.keySet()) // This loop runs for query 2.
			{
				for(int i=0;i<Keys.length();i++) // This loop runs till whole word is passed.
				{
					St.add(Keys.charAt(i)); // Passing words to stemmer engine character by character.
				}
				St.stem(); // Calling stemming engine start function.
				NewWordAfterStemming = St.toString(); // Fetching stemmed data.
				if(UserQuery1.containsKey(NewWordAfterStemming)) // Checking if stemmed word was previously found in document?
				{
					UserQuery1.put(NewWordAfterStemming, UserQuery1.get(NewWordAfterStemming)+TokeniseData.QHM1.get(Keys)); // If present, it will increment value by other word's count.
				}
				else // If not found, do as below.
				{
					UserQuery1.put(NewWordAfterStemming, TokeniseData.QHM1.get(Keys)); // First fetching value of Key from original HM and then add new entry for stemmed word in UserQuery.
				}
				if(All_Words_UserQuery.containsKey(NewWordAfterStemming)) // Checking if stemmed word was previously found in document?
				{
					All_Words_UserQuery.put(NewWordAfterStemming, All_Words_UserQuery.get(NewWordAfterStemming)+TokeniseData.QHM1.get(Keys)); // If present, it will increment value by other word's count.
				}
				else // If not found, do as below.
				{
					All_Words_UserQuery.put(NewWordAfterStemming, TokeniseData.QHM1.get(Keys)); // First fetching value of Key from original HM and then add new entry for stemmed word in UserQuery.
				}
			}
			for(String Keys:TokeniseData.QHM2.keySet()) // This loop runs for query 2.
			{
				for(int i=0;i<Keys.length();i++) // This loop runs till whole word is passed.
				{
					St.add(Keys.charAt(i)); // Passing words to stemmer engine character by character.
				}
				St.stem(); // Calling stemming engine start function.
				NewWordAfterStemming = St.toString(); // Fetching stemmed data.
				if(UserQuery2.containsKey(NewWordAfterStemming)) // Checking if stemmed word was previously found in document?
				{
					UserQuery2.put(NewWordAfterStemming, UserQuery2.get(NewWordAfterStemming)+TokeniseData.QHM2.get(Keys)); // If present, it will increment value by other word's count.
				}
				else // If not found, do as below.
				{
					UserQuery2.put(NewWordAfterStemming, TokeniseData.QHM2.get(Keys)); // First fetching value of Key from original HM and then add new entry for stemmed word in UserQuery.
				}
				if(All_Words_UserQuery.containsKey(NewWordAfterStemming)) // Checking if stemmed word was previously found in document?
				{
					All_Words_UserQuery.put(NewWordAfterStemming, All_Words_UserQuery.get(NewWordAfterStemming)+TokeniseData.QHM2.get(Keys)); // If present, it will increment value by other word's count.
				}
				else // If not found, do as below.
				{
					All_Words_UserQuery.put(NewWordAfterStemming, TokeniseData.QHM2.get(Keys)); // First fetching value of Key from original HM and then add new entry for stemmed word in UserQuery.
				}
			}
			LOUQ.add(UserQuery1); // Adding UserQuery1 to HM.
			LOUQ.add(UserQuery2); // Adding UserQuery2 to HM.
			System.out.println("Stemmed words of user query 1 are : "+UserQuery1);
			System.out.println("Stemmed words of user query 2 are : "+UserQuery2);
			System.out.println();
		}
	}
}