import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClass
{
// Declaration of required variables starts.
	public static List<String> URL_All = new ArrayList<String>();
// Declaration of required variables ends.

	public static void main(String[] args) throws IOException
	{
		// Required declaration starts.
		int CrawlingLength = 0; // I am assigning LoopLength to 0.
		// This does not matter right now as I am going to change this length in runtime later on.
		// Required declaration ends.

		System.out.println("#############################################################");
		System.out.println("=============================================================");
		System.out.println("Start of Part 1.");
		System.out.println("=============================================================");
		System.out.println("#############################################################");
		System.out.println("\nPlease enter file path without page name and page extension :");
		System.out.println("Eg.: D:\\IRWS\\RepeatAssignment\\");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String FilePath = sc.nextLine(); // Accepting file path.
		if(FilePath.charAt(FilePath.length()-1) != '\\') // This will add "\\" at the end of path in case it does not have it already.
		{
			FilePath = FilePath+'\\'; // Adding "\\" here.
		}
		System.out.println("Enter page name and extension to start crawling :");
		System.out.println("Eg.: FirstPage.html");
		String FileName = sc.nextLine(); // Accepting 1st file name.
		String FirstFilePath = FilePath + FileName; // Creating path to start crawling.
		System.out.println("Given file path is : "+FirstFilePath);
		System.out.println("=============================================================");
		System.out.println("Crawling for new links in web page : "+FileName);
		MainClass.URL_All.add(FileName); // Adding 1st file name in list so that the same page is not scanned again.
		IdentifyLinks IL = new IdentifyLinks(); // Object for Class to identify all links to crawl with.
		IL.CheckPage(FilePath,FileName); // Passing 1st page to function responsible to start crawling.
		CrawlingLength = MainClass.URL_All.size(); // Changing length of for loop below dynamically.
		//This will change dynamically as engine will crawl through pages.
		System.out.println("=============================================================");
		for(int i=1;i<CrawlingLength;i++) // This loop will keep running till new pages are added in list.
		{
			System.out.println("Crawling for new links in web page : "+MainClass.URL_All.get(i));
			IL.CheckPage(FilePath,MainClass.URL_All.get(i)); // Passing next page from list to function responsible to start crawling.
			CrawlingLength = MainClass.URL_All.size(); // Changing length of crawling again to change for loop life.
			System.out.println("=============================================================");
		}
		System.out.println("\n");
		System.out.println("#############################################################");
		System.out.println("=============================================================");
		System.out.println("End of Part 1.");
		System.out.println("=============================================================");
		System.out.println("Start of Part 2.");
		System.out.println("=============================================================");
		System.out.println("#############################################################");
		System.out.println("\n");
		System.out.println("=============================================================");
		System.out.println("Creating tokens of all identified web pages.");
		System.out.println("=============================================================");
		TokeniseData TD = new TokeniseData(FilePath); // Creating object to initialize required global variable in TokeniseData class using a constructor.
		TD.TokeniseDocument(1); // Calling the TokeniseDocument class to initiate process of tokenising words, removing html tags, converting to lower case and removing special characters.
		System.out.println();
		System.out.println("=============================================================");
		System.out.println("Identifying and removing stop words from all web pages.");
		System.out.println("It is mentioned to create a stop word list made up of the words that appear in every document,");
		System.out.println("=============================================================");
		RemoveStopWords RSW = new RemoveStopWords(); // Creating object and passing option 0 as we have more than 1 page to find stopwords.
		if(URL_All.size() == 1)
		{
			System.out.println("Since there is only 1 web page found, we cannot compare it with any other page for stopwords list.");
			RSW.IdentifyStopWords("2"); // Calling method to identify stopwords and proceed further.
		}
		else
		{
			RSW.IdentifyStopWords("0"); // Calling method to identify stopwords and proceed further.
		}
		System.out.println("\n");
		System.out.println("=============================================================");
		System.out.println("Stemming words using Porter Stemmer Algorithm referring to tartarus.org.");
		System.out.println("=============================================================");
		StemWords SW = new StemWords(); // Creating object of StemWords class.
		SW.StartStemmer(1); // Passing 1 here to indicate stemming needs to be done for words found in all documents.
		System.out.println("\n");
		System.out.println("=============================================================");
		System.out.println("Calculating TF-IDF for each words in each document.");
		TF_IDF TFIDF = new TF_IDF(); // Creating object of class TD_IDF.
		TFIDF.Calculate_TF_IDF(1); // Calling the trigger method to calculate TF-IDF.
		UserQuery UQ = new UserQuery(); // Creating object of class UserQuery to fetch queries from user.
		UQ.AcceptQuery(); // Calling trigger method.
		System.out.println("\n");
		System.out.println("#############################################################");
		System.out.println("=============================================================");
		System.out.println("End of Part 2.");
		System.out.println("=============================================================");
		System.out.println("Start of Part 3.");
		System.out.println("=============================================================");
		System.out.println("#############################################################");
		System.out.println("\n");
		System.out.println("=============================================================");
		System.out.println("Calculating page rank for identified web pages.");
		System.out.println("=============================================================");
		PageRank PR = new PageRank();
		PR.CalculatePageRank(FilePath);
		System.out.println();
		System.out.println("#############################################################");
		System.out.println("=============================================================");
		System.out.println("End of Part 3. Output file has been created for Part 3 at : "+FilePath+"IRWS_Part3.txt");
		System.out.println("=============================================================");
		System.out.println("#############################################################");
	}
}