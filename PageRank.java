import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PageRank
{
	private static Map<String,List<String>> InLinks = new HashMap<>(); // This HashMap will store all inlinks to any webpage.
	private static Map<String,List<String>> OutLinks = new HashMap<>(); // This HashMap will store all outlinks from any webpage.
	private static Map<String,Double> PR_LI = new HashMap<>(); // This HashMap will store page rank of any webpage calculated in last iteration.
	private static Map<String,Double> PR_CI = new HashMap<>(); // This HashMap will store page rank of any webpage calculated in current iteration.
	private static int iteration = 20; // This variable controls number of iterations. We will run this function for maximum 20 iterations as per requirement.

	public void CalculatePageRank(String FilePath) // This function is responsible to calculate page rank.
	{
		double initialPageRank = 100.0; // This is initialPageRank given in Assignment. This can be changed or taken from user input.
		double dampingFactor = 0.75; // This is dampingFactor given in Assignment. This can be changed or taken from user input.
		double InitialFactor = 1-dampingFactor; // This is initial part in our page rank formula. Calculated once can be used every time in all iterations.
		String FileName = FilePath+"IRWS_Part3.txt"; // Appending File name to file path and storing in file name.
		String Page = null;

		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(FileName));
			BW.append("#############################################################\n");
			BW.append("=============================================================\n");
			BW.append("IRWS Assignment Part 3.\n");
			BW.append("=============================================================\n");
			BW.append("#############################################################\n");
			BW.append("\n\n=============================================================\n");
			for(int i=0;i<1;) // This loop will run unless a correct input is given by user.
			{
				BW.append("Enter page name from below list to initialize it with weightage 100 for 1st iteration.\n");
				BW.append(MainClass.URL_All+"\n");
				System.out.println("Enter page name from below list to initialize it with weightage 100 for 1st iteration.");
				System.out.println(MainClass.URL_All);
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				Page = sc.nextLine();
				System.out.println("=============================================================");
				BW.append("=============================================================\n");
				for(String link:InLinks.keySet())
				{
					System.out.println("InLinks for "+link+" are : "+InLinks.get(link));
					BW.append("InLinks for "+link+" are : "+InLinks.get(link)+"\n");
					System.out.println("OutLinks for "+link+" are : "+OutLinks.get(link));
					BW.append("OutLinks for "+link+" are : "+OutLinks.get(link)+"\n");
				}
				Page = Page.toString(); // Converting given text into string.
				Page = Page.toLowerCase(); // Converting given text into lowercase.
				BW.append("=============================================================\n");
				BW.append("Your input is : "+Page+". Hence, initializing weightage of 100 to same for 1st iteration.\n");
//				System.out.println(Page);
				if(MainClass.URL_All.contains(Page)) // If given page is found in list, do as below.
				{
					i++; // Break loop by incrementing value.
				}
				else // If not found, do as below.
				{
					BW.append("Please try again with correct page name.\n");
					System.out.println("Please try again with correct page name.");
				}
			}
			for(int i=0; i<MainClass.URL_All.size(); i++) // This for loop will initialize pagerank of all pages for 1st iteration to 0.
			{
//				System.out.println(MainClass.URL_All.get(i));
				if(MainClass.URL_All.get(i).equals(Page)) // If page is found, do as below.
				{
					PR_LI.put(MainClass.URL_All.get(i),initialPageRank); // Adding 1st page to HashMap and assigning weightage to 1st page.
				}
				else // If page is not 1st page, do as below.
				{
					PR_LI.put(MainClass.URL_All.get(i),0.0); // Adding page to HashMap and assigning initial weightage.
				}
			}
			BW.append("=============================================================\n");
			BW.append("Initial page weights are : "+PR_LI+"\n");
			BW.append("=============================================================\n");
			BW.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("=============================================================");
		System.out.println("Initial page weights are : "+PR_LI);
		System.out.println("=============================================================");
		for(int i=0; i<iteration; i++) // This loop will run for number of defined iterations.
		{
			PR_CI.clear(); // Clearing HashMap of Current Iteration to store new values of weightage in current loop run.
			for(int j=0; j<MainClass.URL_All.size(); j++) // This loop will run for all web pages in our list.
			{
				double value = 0.0; // Initializing value to 0. This will be used to calculate new weightage in current iteration.
				double InLinkPR = 0.0; // This is 2nd part of page rank to be calculated in current iteration.
				int OutLinksCount = 0; // This will store number of outlinks in given page.
//				System.out.println("Page Name : "+MainClass.URL_All.get(j));
				List<String> InLinksNames = InLinks.get(MainClass.URL_All.get(j)); // This list will store list of inlinks for given webpages.
//				System.out.println("Iteration : "+(i+1)+" InLinkNames : "+InLinksNames);
				if(InLinksNames != null) // In case there are InLinks to page, below loop will run.
				{
					for(int k=0; k<InLinksNames.size(); k++) // This loop will run for all inlinks of given webpage.
					{
						OutLinksCount = OutLinks.get(InLinksNames.get(k)).size(); // This will fetch count of outlinks for given web page.
	//					System.out.println("Iteration : "+(i+1)+" OutLinkCount : "+OutLinksCount);
						InLinkPR = InLinkPR + (PR_LI.get(InLinksNames.get(k))/OutLinksCount); // Calculating 2nd part of page rank formula.
	//					System.out.println("InLinkPR : "+InLinkPR);
					}
				}
				else // If there are no inlinks, assigning Page Rank 0.
				{
					InLinkPR = 0.0;
				}
				value = InitialFactor + (dampingFactor*InLinkPR); // Calculating page rank as per formula.
				value = Double.parseDouble(String.format("%.3f",value)); // Restricting values to 3 digits after decimal.
//				System.out.println("Value : "+value);
				PR_CI.put(MainClass.URL_All.get(j), value); // Adding values of current iteration page rank to HashMap.
//				System.out.println("PR_CI : "+PR_CI);
			}
			System.out.println("Iteration : "+(i+1)+" page weights are : "+PR_CI);		
			System.out.println("=============================================================");
			try
			{
				BufferedWriter BW = new BufferedWriter(new FileWriter(FileName,true));
				BW.append("Iteration : "+(i+1)+" page weights are : "+PR_CI+"\n");
				BW.append("=============================================================\n");
				BW.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			PR_LI.clear(); // Deleting all valus of last iteration page ranks of all pages from HashMap.
			for(int j=0; j<MainClass.URL_All.size(); j++) // This loop runs for all pages in list.
			{
				PR_LI.put(MainClass.URL_All.get(j),PR_CI.get(MainClass.URL_All.get(j))); // Adding values of page rank in current iteration to last iteration HashMap. This is to use current iteration's values as last iteration valus in next iteration.
			}
//			System.out.println("Updated page rank values for last iteration : "+PR_LI);
		}
		DefinePageRank(FileName); // Calling function to define page rank. Passing FileName which also contains file path to write into same.
	}

	public void IdentifyInLinks(String ForPage, String FromPage) // This function is responsible to identify all InLinks to all web pages.
	{
		List<String> Links = null; // Initializing list to null.
		if(InLinks.containsKey(ForPage)) // If web page already have any entry in InLink HashMap, do as below.
		{
			Links = InLinks.get(ForPage); // Assigning already existing list to new list.
			if(!Links.contains(FromPage)) // If FromPage is not in list of InLink of ForPage, do as below.
			{
				Links.add(FromPage); // Add FromPage in list.
			}
		}
		else if(!InLinks.containsKey(ForPage)) // If web page do not have any entry in InLink HashMap, do as below.
		{
			Links = new ArrayList<>(); // Create new list of Links.
			Links.add(FromPage); // Add FromPage in list.
		}
		InLinks.put(ForPage,Links); // Finally in both the above scenarios put values for given ForPage in HashMap.
//		System.out.println("InLinks are : "+InLinks);
	}

	public void IdentifyOutLinks(String FromPage, String ToPage) // This function is responsible to identify all OutLinks to all web pages.
	{
		List<String> Links = null; // Initializing list to null.
		if(OutLinks.containsKey(FromPage)) // If web page already have any entry in OutLink HashMap, do as below.
		{
			Links = OutLinks.get(FromPage); // Assigning already existing list to new list.
			if(!Links.contains(ToPage)) // If ToPage is not in list of OutLink of FromPage, do as below.
			{
				Links.add(ToPage); // Add ToPage in list.
			}
		}
		else if(!OutLinks.containsKey(FromPage)) // If web page do not have any entry in OutLink HashMap, do as below.
		{
			Links = new ArrayList<>(); // Create new list of Links.
			Links.add(ToPage); // Add ToPage in list.
		}
		OutLinks.put(FromPage,Links); // Finally in both the above scenarios put values for given ForPage in HashMap.
//		System.out.println("OutLinks are : "+OutLinks);
	}

	private void DefinePageRank(String FileName) // This function is responsible to define page rank.
	{
		ArrayList<Double> Weightage = new ArrayList<>(); // This will contain weights of web pages in descending order.
		ArrayList<String> WebPageName = new ArrayList<>(); // This will contain names of web pages in descending order as per rank.
		for(String Link:PR_LI.keySet()) // This loop will run for all the documents.
		{
			Weightage.add(PR_LI.get(Link)); // Adding weight of web page to list.
		}
		Collections.sort(Weightage,Collections.reverseOrder()); // Reversing values in descending order.
		for(int i=0;i<Weightage.size();i++) // This loop will run for all web links.
		{
			for(String Key:PR_LI.keySet()) // This will run for all values in HM of weightage.
			{
				if(PR_LI.get(Key) == Weightage.get(i) && !WebPageName.contains(Key)) // This will compare all values and if found and if list of WebPageName does not already contain that Key, do as below.
				{
					WebPageName.add(Key); // Adding Web page name in list of WebPageName.
					break;
				}
			}
		}
		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(FileName,true));
			BW.append("\n=============================================================\n");
			BW.append("Below are the identified page names with their corresponding ranks at iteration : "+iteration+"\n");
			BW.append("=============================================================\n");
			BW.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		for(int i=0;i<WebPageName.size();i++) // This loop is written to display page ranks of all web pages.
		{
			System.out.println("Page Rank of "+WebPageName.get(i)+" is : "+(i+1));
			try
			{
				BufferedWriter BW = new BufferedWriter(new FileWriter(FileName,true));
				String rank = WebPageName.get(i)+" : "+(i+1);
				BW.append(rank+"\n");
				BW.append("=============================================================\n");
				BW.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(FileName,true));
 			BW.append("\n\n#############################################################\n");
			BW.append("=============================================================\n");
			BW.append("End of Part 3.\n");
			BW.append("=============================================================\n");
			BW.append("#############################################################\n");
			BW.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("=============================================================");
	}
}