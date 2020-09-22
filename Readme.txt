###############################################################################################################

Important Information before running this Program:
1) All the "FileName.java" files must be copied in same directory(folder).
2) All the webpages must be in the same directory(folder).

###############################################################################################################

Please Note: All the assignment parts are linked by MainClass.java. Hence, there is no requirement of running all files seperately. Only run MainClass.java as per below instructions to run this assignment.

###############################################################################################################

Steps to run this program:

1) Open command prompt and change working directory to where these files are stored.
   Eg.: cd C:\Users\Administrator\Downloads\IRWS

2) Type "javac MainClass.java" in command prompt to compile the program.
   Now, you will be able to see a new set of .class files will be generated in same directory.
   In case you are facing issue like javac is not recognised as internal or external command, then there is no java path set in your system environment variables.
   Alternative to this is to set path at run time. After going to directory where all .java files are stored, type in command prompt:
   set path="Your Java JDK Bin folder path" & then re-run command in point 2.
   Eg.: set path="C:\Program Files\Java\jdk1.8.0_261\bin"

3) Type "java MainClass" in command prompt to run the program.

4) Program will ask for file path having web pages stored offline.
   Eg.: D:\IRWS\Repeat_Corpus\
   Please give input correctly for appropriate path.

5) After giving file path, program will ask for page name and extension.
   Eg.: mainpage.html
   Please give input correctly with correct naming convention.

6) Crawler will start crawling all pages linked to each other and also tokens will be generated for all identified pages.
   Program will also identify and display list of stopwords as per assignment requirement.
   Program will ask to input any one of given options:
   i) To continue with identified and displayed stopwords.
   ii) To upload new list of stopwords.

7) If you wish to go for option 1, program will execute further else it will ask for stopwords file path with file name and extension.
   Eg.: D:\IRWS\Repeat_Corpus\stopwords.txt
   Please give appropriate input with correct path and file name and extension.

8) Program will display:
   i) New stopwords list.
   ii) Tokens list of all pages with their occurances after removing stopwords.
   iii) Tokens list of all pages with their occurances after stemming words.
   iv) Maximum frequency for all pages.
   v) IDF of all words (tokens) in all pages combined.
   vi) TF.IDF of all words (tokens) in all pages.
   vii) Page length (i.e. Page Weights) as per Vector Space Model.

9) Program will now prompt to select one of below options :
   i) To perform query search on scanned web pages.
   ii) To exit Part 2 of program.
   Please enter number 1 to select 1st option or enter 2 to exit part 2.

10) On selecting option 1, program will prompt to enter 2 queries to perform search operation.
   Please enter 2 queries appropriately.

11) Program will display:
   i) Tokens list of both queries with their occurances.
   ii) Tokens list of all pages with their occurances after stemming words.
   iii) Maximum frequency for all queries.
   iv) IDF of all words (tokens) in all queries combined.
   v) TF.IDF of all words (tokens) in all queries.
   vi) Query length (i.e. Query Weights) as per Vector Space Model.
   vii) Cosine values of each query against each document in tabular format.
   viii) Page rank as per Vector Space Model.

12) Program will again prompt for input to select one of below options :
   i) To perform query search on scanned web pages.
   ii) To exit Part 2 of program.
   Please enter number 1 to select 1st option or enter 2 to exit part 2.

13) Now if you select to exit from part 2 in above step, program will prompt to enter web page name from displayed identified web page list.
   This is to give initial page rank of 100 to selected page. Please enter page name appropriately to continue.

14) Program will calculate page rank for 20 iterations and display as below :
   i) Page weights of all pages in each iteration.
   ii) Page rank of all pages in iteration 20.
   Same data for assignment part 3 will be stored in a file on displayed path with file name : IRWS_Part3.txt

###############################################################################################################
