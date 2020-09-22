import java.util.Scanner;

public class UserQuery
{
	// Declaration of required variables starts.
	public static String userQuery1 = null; // This variable will contain query from user.
	public static String userQuery2 = null; // This variable will contain query from user.
	// Declaration of required variables ends.

	public void AcceptQuery() // This function is used to control user input after scanning web pages.
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String userInput; // This will contain user's input of selection from 2 options.
		for(int i=0;i<1;) // This loop will exit when user inputs 2.
		{
			System.out.println("=============================================================");
			System.out.println("Enter 1 to perform query search in scanned web pages.");
			System.out.println("Enter 2 to exit.");
			userInput = sc.nextLine();
			if(userInput.equals("1")) // In case user wish to perform query, user will enter 1.
			{
				@SuppressWarnings("resource")
				Scanner sc1 = new Scanner(System.in);
				System.out.println("Please enter query 1 to perform search operation.");
				userQuery1 = sc1.nextLine();
				System.out.println("Please enter query 2 to perform search operation.");
				userQuery2 = sc1.nextLine();
				TokeniseData TD = new TokeniseData(""); // Calling tokenise data method in TokeniseData class to start user query tokenising. Passing "" in class as query is to be fetched from user and not any path.
				TD.TokeniseDocument(3); // Passing 3 to run logic of User Query.
				RemoveStopWords RS = new RemoveStopWords(); // Object of class RemoveStopWords.
				RS.RemoveWords(2); // Calling function to remove stopwords.
//				System.out.println("User Query Tokens : "+TokeniseData.QHM.keySet());
				StemWords SW = new StemWords(); // Creating object of stemming engine.
				SW.StartStemmer(2); // Calling start method of stemming engine.
				TF_IDF TI = new TF_IDF(); // Creating object of TF_IDF class.
				TI.Calculate_TF_IDF(2); // Calling Start method of TF_IDF and passing 2 in parameter to indicate this needs to be run for query.
				CalculateCosine CC = new CalculateCosine(); // Creating object of CalculateCosine class.
				CC.Cosine(); // Calling start method to Calculate Cosine value which is also weightage.
			}
			else if(userInput.equals("2")) // In case user wish to exit, they will enter 2.
			{
				i++; // Increament loop constant to exit.
			}
		}
	}
}