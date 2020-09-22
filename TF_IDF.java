import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TF_IDF
{
	private Map<String, Integer> Ni = new HashMap<>(); // This HM will contain count of documents that contains particular word.
	private Map<String, Integer> Ni_UQ = new HashMap<>(); // This HM will contain count of documents that contains particular word.
	private Map<String, Double> IDF = new HashMap<>(); // This HM will contain IDF of each word.
	private Map<String, Double> IDF_UQ = new HashMap<>(); // This will contain IDF of user query.
	private List<Integer> Max_Frequency = new ArrayList<>(); // This HM will contain maximum frequency of particular word in each document.
	private List<Integer> Max_Frequency_UQ = new ArrayList<>(); // This HM will contain maximum frequency of particular word in each query.
	public static Map<String,Double> PageLength = new HashMap<>(); // This list will have page length of all pages.
	public static List<Double> UQ_Length = new ArrayList<>(); // This list will have page length of all pages.
	public static Map<String,List<Double>> TF_IDF_WP = new HashMap<>(); // This HashMap will store occurance of all words in each document.
	public static Map<String,List<Double>> TF_IDF_UQ = new HashMap<>(); // This HashMap will store occurance of all words in each user query.

	public void Calculate_TF_IDF(int Option) // This is method that will be called from MainClass().
	{
		GetWordsCount(Option); // This function calculates words count of each word in All_Words_HM in all the web pages.
		Calculate_Max_Frequency(Option); // This function calculates max frequency from each word in document.
		Calculate_IDF(Option); // This function calculates IDF for each word.
		TF_IDF_Calculate(Option); // This function calculates TF_IDF for each word in web pages or in user query.
		System.out.println("=============================================================");
		System.out.println("TF.IDF calculation completed. Below lengths are calculated as per TF.IDF in VSM method.");
		System.out.println("=============================================================");
		Calculate_Weight(Option); // This function calculates the page weights.
	}

	private void GetWordsCount(int Option) // This function is used to calculate word count of each word in all the web pages.
	{
		if(Option == 1) // Calculation for web pages.
		{
			for(String Word:StemWords.All_Words_HM.keySet()) // This loop runs for all words in our main list of words.
			{
				List<Double> ContainsList = new ArrayList<>(); // This list will be created new for each words in our list.
				int total_Frequency = 0;
				for(int j=0;j<StemWords.LOHMSW.size();j++) // This loop runs for each web page.
				{
					if(StemWords.LOHMSW.get(j).containsKey(Word)) // If word is found in HashMap of webpage, do as below.
					{
						ContainsList.add(Double.parseDouble(StemWords.LOHMSW.get(j).get(Word)+"")); // Save count of occurance in the particular web page in list.
					}
					else // If not found, do as below.
					{
						ContainsList.add(0.0); // Add 0.
					}
				}
				for(int j=0; j<ContainsList.size(); j++) // This loop will run for all documents.
				{
					if(ContainsList.get(j)>0) // If word is found in any document, do as below.
					{
						total_Frequency = total_Frequency + 1; // Increment counter by 1.
					}
				}
				TF_IDF_WP.put(Word, ContainsList); // Saving whole List in HashMap of Words.
				Ni.put(Word,total_Frequency); // Saving frequency of each words in HashMap.
			}
			System.out.println("=============================================================");
			System.out.println("Ni of identified Tokens are : "+Ni);
		}
		else if(Option == 2)
		{
			TF_IDF_UQ.clear(); // Clearing to delete data of previous queries.
			Ni_UQ.clear(); // Clearing to delete data of previous queries.
			for(String Word:StemWords.All_Words_UserQuery.keySet()) // This loop runs for all words in our main list of words.
			{
				List<Double> ContainsList = new ArrayList<>(); // This list will be created new for each words in our list.
				int total_Frequency = 0;
				for(int j=0;j<StemWords.LOUQ.size();j++) // This loop runs for each query.
				{
					if(StemWords.LOUQ.get(j).containsKey(Word)) // If word is found in HashMap of webpage, do as below.
					{
						ContainsList.add(Double.parseDouble(StemWords.LOUQ.get(j).get(Word)+"")); // Save count of occurance in the particular web page in list.
					}
					else // If not found, do as below.
					{
						ContainsList.add(0.0); // Add 0.
					}
				}
				for(int j=0; j<ContainsList.size(); j++) // This loop will run for all query.
				{
					if(ContainsList.get(j)>0) // If word is found in any query, do as below.
					{
						total_Frequency = total_Frequency + 1; // Increment counter by 1.
					}
				}
				TF_IDF_UQ.put(Word, ContainsList); // Saving whole List in HashMap of Words.
				Ni_UQ.put(Word,total_Frequency); // Saving frequency of each words in HashMap.
			}
			System.out.println("=============================================================");
			System.out.println("Ni of identified Tokens are : "+Ni_UQ);
		}
	}

	private void Calculate_Max_Frequency(int Option) // This function calculates max frequency from each word in document.
	{
		if(Option == 1) // Calculation for web pages.
		{
			System.out.println("=============================================================");
			for(int i=0;i<StemWords.LOHMSW.size();i++) // This loop runs for all stemmed words in list.
			{
				int max_freq = 0; // Initializing max_frequency to 0 each time new stemmed word list if fetched for new page.
				for(String EachWord:StemWords.LOHMSW.get(i).keySet()) // For each word in list of current page.
				{
					if(StemWords.LOHMSW.get(i).get(EachWord)>max_freq) // In case frequency of current word is large than value in max_freq, do as below.
					{
						max_freq = StemWords.LOHMSW.get(i).get(EachWord); // Assign value of word to max_freq.
					}
				}
				Max_Frequency.add(max_freq); // Adding max_frequency of all pages in List.
				System.out.println("Max Frequency for "+MainClass.URL_All.get(i)+" is : "+max_freq);
			}
			System.out.println("=============================================================");
		}
		else if(Option == 2) // Calculation for user query.
		{
			Max_Frequency_UQ.clear(); // Clearing to delete data of previous queries.
			System.out.println("=============================================================");
			for(int i=0;i<StemWords.LOUQ.size();i++) // This loop runs for all stemmed words in list.
			{
				int MF = 0;
				for(String Key:StemWords.LOUQ.get(i).keySet()) // This loop runs for all stemmed words in query.
				{
					if(MF<StemWords.LOUQ.get(i).get(Key)) // if value of frequency is less than frequency value of word, do as below.
					{
						MF=StemWords.LOUQ.get(i).get(Key); // Re-assign frequency value.
					}
				}
				Max_Frequency_UQ.add(MF); // Adding max_frequency of all pages in List.
				System.out.println("Max Frequency for Query "+(i+1)+" is : "+MF);
			}
			System.out.println("=============================================================");
		}
	}

	private void Calculate_IDF(int Option) // This function calculates IDF of each word.
	{
		if(Option == 1) // Calculation for web pages.
		{
			for(String Key:Ni.keySet()) // For all words in our main words list.
			{
				double N = (double)MainClass.URL_All.size()/(double)Ni.get(Key);
				double logN = Double.parseDouble(String.format("%.3f",(Math.log(N)/Math.log(10)))); // Calculating log(N/Ni) to the base 10.
				IDF.put(Key,logN); // Adding log value to HM of IDF.
//				System.out.println("Word : "+Key+" Log is : "+logN);
			}
			System.out.println("IDF of all terms in webpages are : "+IDF);
			System.out.println("=============================================================");
		}
		else if(Option == 2) // Calculation for user query.
		{
			IDF_UQ.clear(); // Clearing to delete data of previous queries.
			for(String Key:Ni_UQ.keySet()) // For all words in our main words list.
			{
				double N = (double)2/(double)Ni_UQ.get(Key);
				double logN = Double.parseDouble(String.format("%.3f",(Math.log(N)/Math.log(10)))); // Calculating log(N/Ni) to the base 10.
				IDF_UQ.put(Key,logN); // Adding log value to HM of IDF.
//				System.out.println("Word : "+Key+" Log is : "+logN);
			}
			System.out.println("IDF of all terms in queries are : "+IDF_UQ);
			System.out.println("=============================================================");
		}
	}

	private void TF_IDF_Calculate(int Option) // This function will calculate TF.IDF.
	{
		System.out.println("Below are TF.IDF values of each term against each document.");
		System.out.println("=============================================================");
		if(Option == 1) // Calculation for web pages.
		{
			System.out.println("Word\t"+MainClass.URL_All+"\n");
			for(String word:TF_IDF_WP.keySet()) // This loop runs for all words in HM.
			{
				List<Double> frequency = TF_IDF_WP.get(word); // List of frequency for all words count will be copied in this list.
				List<Double> TF_IDF_List = new ArrayList<>(); // This list will be added with new values of TF.IDF.
//				System.out.println("Word is : "+word+" frequency is : "+frequency+" Max Frequency of page is : "+Max_Frequency+" IDF is : "+IDF.get(word));
				for(int i=0;i<frequency.size();i++) // This loop will run run for all values in list.
				{
					double value = (frequency.get(i)/Max_Frequency.get(i))*IDF.get(word); // Calculation for TF.IDF.
					TF_IDF_List.add(Double.parseDouble(String.format("%.3f",value))); // This is format to restrict values to 2 decimal places.
				}
				TF_IDF_WP.put(word,TF_IDF_List); // Values of words in global HM will be changed here.
				System.out.println(word+"\t\t"+TF_IDF_WP.get(word));
			}
		}
		else if(Option == 2) // Calculation for user query.
		{
			System.out.println("Word\t[Query 1, Query 2]\n");
			for(String word:TF_IDF_UQ.keySet()) // This loop runs for all words in HM.
			{
				List<Double> frequency = TF_IDF_UQ.get(word); // List of frequency for all words count will be copied in this list.
				List<Double> TF_IDF_List = new ArrayList<>(); // This list will be added with new values of TF.IDF.
//				System.out.println("Word is : "+word+" frequency is : "+frequency+" Max Frequency of page is : "+Max_Frequency_UQ+" IDF is : "+IDF_UQ.get(word));
				for(int i=0;i<frequency.size();i++) // This loop will run run for all values in list.
				{
					double value = (frequency.get(i)/Max_Frequency_UQ.get(i))*IDF_UQ.get(word); // Calculation for TF.IDF.
					TF_IDF_List.add(Double.parseDouble(String.format("%.3f",value))); // This is format to restrict values to 2 decimal places.
				}
				TF_IDF_UQ.put(word,TF_IDF_List); // Values of words in global HM will be changed here.
				System.out.println(word+"\t\t"+TF_IDF_UQ.get(word));
			}
		}
	}

	private void Calculate_Weight(int Option) // This function is used to calculate page weight.
	{
		double Page_Weight; // This will temporarily contain values of page weight.
		double Query_Weight; // This will temporarily contain values of query weight.
		List<Double> Value; // This will temporarily contain list of all values for words frequency in all pages.
		if(Option == 1) // Calculation for web pages.
		{
			for(int i=0;i<MainClass.URL_All.size();i++) // This loop runs for all web pages detected.
			{
				Page_Weight=0.0; // Initializing page weight to 0 before calculating weight of next page.
				for(String word:TF_IDF_WP.keySet()) // This loop runs for all words in web page.
				{
					Value = TF_IDF_WP.get(word); // assigning values of list into value.
					Page_Weight = Page_Weight+(Value.get(i)*Value.get(i)); // Squaring value and adding to Page_Weight.
				}
//				System.out.println("PW : "+Page_Weight);
				Page_Weight = Math.sqrt(Page_Weight); // Taking square root of total value.
				Page_Weight = Double.parseDouble(String.format("%.3f",Page_Weight)); // Restricting decimal places to 2.
				PageLength.put(MainClass.URL_All.get(i),Page_Weight); // Adding new values to list PageLength.
			}
			System.out.println("Page Length : "+PageLength);
		}
		else if(Option == 2) // Calculation for user query.
		{
			UQ_Length.clear(); // Clearing to delete data of previous queries.
			for(int i=0;i<2;i++) // This loop runs for all web pages detected.
			{
				Query_Weight=0.0; // Initializing page weight to 0 before calculating weight of next page.
				for(String word:TF_IDF_UQ.keySet()) // This loop runs for all words in web page.
				{
					Value = TF_IDF_UQ.get(word); // assigning values of list into value.
					Query_Weight = Query_Weight+(Value.get(i)*Value.get(i)); // Squaring value and adding to Page_Weight.
				}
//				System.out.println("QW : "+Query_Weight);
				Query_Weight = Math.sqrt(Query_Weight); // Taking square root of total value.
				Query_Weight = Double.parseDouble(String.format("%.3f",Query_Weight)); // Restricting decimal places to 2.
				UQ_Length.add(Query_Weight); // Adding new values to list PageLength.
			}
			System.out.println("Query Length : "+UQ_Length);
		}
	}
}