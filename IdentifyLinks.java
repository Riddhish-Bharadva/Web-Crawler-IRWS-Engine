import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifyLinks
{
// Declaration of global variable starts.
	private String FilePath;
	PageRank PR = new PageRank(); // Object of class PageRank.
// Declaration of global variable ends.

	public void CheckPage(String FilePath, String FileName) // This will initiate the crawling from 1st Web page.
	{
		FilePath = FilePath + FileName;
		String DataInFile = null;
		File file;
		FileReader FR;
		BufferedReader BR=null;
		List<String> url = new ArrayList<>(); // This list will contain all newly found urls after scanning web pages.
		
		this.FilePath = FilePath; // This line passes link of 1st web page from user to our global variable.
		try // In case file is not found in given path, exception should be handled.
		{
			file = new File(this.FilePath);
			FR = new FileReader(file);
			BR = new BufferedReader(FR);
		}
		catch(Exception e) // In case file is not found in given path, exception will be handled here.
		{
			e.printStackTrace(); // Printing stack trace identify exact line containing exception or error.
		}
		try
		{
			System.out.println("Found below links from this page :");
			while((DataInFile = BR.readLine())!=null) // This line indicates to run loop while all the lines in given web page is scanned.
			{
				url = FindURL(DataInFile); // Calling function line by line to check if URL is present or not.
				if(url != null) // If URL found in above call, run below code.
				{
					for(int i=0;i<url.size();i++)
					{
						String link = url.get(i).toLowerCase(); // Converting all links to lower case if there are any in upper case.
						System.out.println(url.get(i)); // Display found link in above function call.
						PR.IdentifyInLinks(link, FileName);
						PR.IdentifyOutLinks(FileName, link);
						if(!MainClass.URL_All.contains(link)) // Check if found link is already present in our list.
						{
							MainClass.URL_All.add(link); // If not present, add link in list.
						}
					}
				}
			}
			System.out.println("Adding new links (if any) in the list to crawl further.");
		}
		catch(Exception e) // Catching exception occurred while reading data from file.
		{
			e.printStackTrace(); // Printing stack trace identify exact line containing exception or error.
		}
	}

	private List<String> FindURL(String line) // This function is used to find URLs from passed line.
	{
        if(line.contains("href")) // This if condition filters out lines having href.
        {
    		List<Integer> HrefPositions = new ArrayList<>(); // Array used to store positions of hrefs.
    		Matcher StringMatcher = Pattern.compile("href").matcher(line); // Match substring "href" in given String line.
    		while(StringMatcher.find()) // For all occurrances of href, find position in given line.
    		{
    			HrefPositions.add(StringMatcher.start()); // Adding all positions of href in list.
    		}
        	List<String> url = new ArrayList<>();
        	List<Integer> hrefs = HrefPositions; // Passing given line to identify number of hrefs in same. Can be single or multiple.
        	for (int i=0; i<hrefs.size(); i++) // Run the loop for all occurrances of hrefs.
        	{
        		int index = hrefs.get(i); // Get hrefs one by one from list.
        		StringBuilder sb = new StringBuilder(); // Used to store link temporarily.
        		while(line.charAt(index) != '\"') // This while loop identifies start of " in href tag. Eg.: href=".
        		{
        			index++; // Incrementing index.
        		}
        		index++; // This is incremented to go on to next index of found " in href="www.somelink.com".
        		while(line.charAt(index) != '\"') // This while loop identifies end of " in href. Eg.: href="www.somelink.com".
        		{
        			sb.append(line.charAt(index)); // Appending all the date in between two " of href.
        			index++; // incrementing index untill we find """ for second time.
        		}
        		url.add(sb.toString().trim()); // adding newly found url in list.
        	}
        	return url; // Returning all found url.
        }
        else // If given line does not contain any href, return null.
        {
        	return null; // If no urls are found, return null.
        }
    }
}