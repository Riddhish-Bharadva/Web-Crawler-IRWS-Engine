import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateCosine
{
	private Map<String,Double> WeightPerQuery = new HashMap<>(); // This will contain weightage of each page as per query. This is Q.W value.
	private Map<String,Double> Weight = new HashMap<>(); // This will contain final weight of all documents per query.

	public void Cosine()
	{
		CalculateValue1(); // Calling method to calculate upper value of Cosine.
		CalculateValue2(); // Calling method to calculate lower value of Cosine.
	}

	private void CalculateValue1() // This method calculates upper value to calculate cosine.
	{
		double Weight; // This will temporarily contain values of page weight.
		List<Double> PageValue; // This will temporarily contain list of all values for words frequency in all pages.
		List<Double> Value; // This will temporarily contain list of all values for words frequency in all pages.
		int j=0; // Initializing j to 0 as we need to start fetching data from query 1.
//		System.out.println(TF_IDF.TF_IDF_WP);
//		System.out.println(TF_IDF.TF_IDF_UQ);
		for(int i=0;i<MainClass.URL_All.size();i++) // This loop runs for all web pages detected.
		{
			Weight=0.0; // Initializing page weight to 0 before calculating weight of next page.
			for(String word:TF_IDF.TF_IDF_WP.keySet()) // This loop runs for all words in all web pages.
			{
				PageValue = TF_IDF.TF_IDF_WP.get(word); // assigning values of list into value.
				if(TF_IDF.TF_IDF_UQ.containsKey(word))
				{
					Value = TF_IDF.TF_IDF_UQ.get(word);
					Weight = Weight+(PageValue.get(i)*Value.get(j)); // Multiplying values and adding to Weight.
//					System.out.println("Word : "+word+" "+PageValue.get(i)+" "+Value.get(j));
				}
			}
//			System.out.println("W : "+Weight);
			Weight = Math.sqrt(Weight); // Taking square root of total value.
			Weight = Double.parseDouble(String.format("%.3f",Weight)); // Restricting decimal places to 2.
			WeightPerQuery.put(MainClass.URL_All.get(i)+j,Weight); // Adding new values to HM.
			if(j == 0)
			{
				j++; // Setting to get j to 1 in next for run. This will result in fetching data for Query 2.
				i--; // I want both query must be calculated for each page.
			}
			else
			{
				j=0; // Setting to get j again to 0 in next for run. This will result in fetching data for Query 1.
			}
		}
//		System.out.println(WeightPerQuery);
	}

	private void CalculateValue2() // This method calculates lower value of Cosine.
	{
//		System.out.println(WeightPerQuery);
//		System.out.println(TF_IDF.PageLength);
//		System.out.println(TF_IDF.UQ_Length);
		for(int i=0;i<TF_IDF.PageLength.size();i++) // This loop will run for all pages.
		{
			for(int j=0;j<TF_IDF.UQ_Length.size();j++) // This loop will run for all queries.
			{
				double weight = 0.0;
				String UpperValue = MainClass.URL_All.get(i)+j; // Creating unique keys for HM.
//				System.out.println("WeightPerQuery.get(UpperValue)"+WeightPerQuery.get(UpperValue));
//				System.out.println("TF_IDF.PageLength.get(MainClass.URL_All.get(i)"+TF_IDF.PageLength.get(MainClass.URL_All.get(i)));
//				System.out.println("TF_IDF.UQ_Length.get(j)"+TF_IDF.UQ_Length.get(j));
				weight = WeightPerQuery.get(UpperValue)/(TF_IDF.PageLength.get(MainClass.URL_All.get(i))*TF_IDF.UQ_Length.get(j)); // Calculating weight.
				weight = Double.parseDouble(String.format("%.3f", weight)); // Restricting decimal places to 2.
				Weight.put(UpperValue,weight); // Adding values in HM.
//				System.out.println(UpperValue+"\t"+weight);
			}
		}
		System.out.println("=============================================================");
		System.out.println("Below are Cosine values of each document against each user query as per Vector Space Model.");
		System.out.println();
		System.out.println("Document\tQuery1\tQuery2");
		for(int i=0;i<TF_IDF.PageLength.size();i++) // This loop runs for all web pages.
		{
			System.out.print(MainClass.URL_All.get(i)+"\t");
			for(int j=0;j<TF_IDF.UQ_Length.size();j++) // This loop will run for all queries.
			{
				String Document = MainClass.URL_All.get(i)+j; // Creating unique keys for HM.
				System.out.print(Weight.get(Document)+"\t");
			}
			System.out.println();
		}
		System.out.println("=============================================================");
		System.out.println("Hence, as per Vector Space Model and above values of each webpage against both the queries, below are the list of most relevant document to least relevant document to user query.");
		System.out.println("Please note: In case all the values of any one of the above query or both the queries are 0.0, below list of documents is just the list of available webpages and all of them does not have results of your query.");
		RelevantDocument();
	}

	private void RelevantDocument() // This function gives most relevant to least relevant documents to queries.
	{
		ArrayList<Double> WeightageQ1 = new ArrayList<>(); // This will contain weights of documents in descending order for Q1.
		ArrayList<Double> WeightageQ2 = new ArrayList<>(); // This will contain weights of documents in descending order for Q2.
		ArrayList<String> Q1 = new ArrayList<>(); // This will contain names of documents in descending order for Q1.
		ArrayList<String> Q2 = new ArrayList<>(); // This will contain names of documents in descending order for Q1.
		for(String Link:Weight.keySet()) // This loop will run for all the documents.
		{
			if(Integer.parseInt(Link.charAt(Link.length()-1)+"") == 0) // This will check if weightage is for Q1 or Q2 by last character of document. If last char is 0, weightage is for query 1.
			{
				WeightageQ1.add(Weight.get(Link)); // Adding value to new list.
			}
			else if(Integer.parseInt(Link.charAt(Link.length()-1)+"") == 1) // This will check if weightage is for Q1 or Q2 by last character of document. If last char is 1, weightage is for query 2.
			{
				WeightageQ2.add(Weight.get(Link)); // Adding value to new list.
			}
		}
		Collections.sort(WeightageQ1,Collections.reverseOrder()); // Reversing values in descending order.
		Collections.sort(WeightageQ2,Collections.reverseOrder()); // Reversing values in descending order.
		for(int i=0;i<WeightageQ1.size();i++) // This loop will run for all web links.
		{
			for(String Key:Weight.keySet()) // This will run for all values in HM of weightages.
			{
				if(Weight.get(Key) == WeightageQ1.get(i) && !Q1.contains(Key)) // This will compare all values and if found and if list of Q1 does not already contain that element, do as below.
				{
					Q1.add(Key.substring(0,Key.length()-1)); // Adding Web page name excluding last character in new list of string.
					break;
				}
			}
			for(String Key:Weight.keySet()) // This will run for all values in HM of weightages.
			{
				if(Weight.get(Key) == WeightageQ2.get(i) && !Q2.contains(Key)) // This will compare all values and if found and if list of Q1 does not already contain that element, do as below.
				{
					Q2.add(Key.substring(0,Key.length()-1)); // Adding web page name excluding last character in new list of string.
					break;
				}
			}
		}
		System.out.println("Query 1 : "+Q1);
		System.out.println("Query 2 : "+Q2);
	}
}