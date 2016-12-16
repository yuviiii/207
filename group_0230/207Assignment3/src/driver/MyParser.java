//**********************************************************
//Assignment3:
//CDF user_name:c5dingyi
//
//Author:YiTian Ding
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//*********************************************************
package driver;

import java.util.ArrayList;
import java.util.Collections;
import processor.ConsoleOut;
import processor.FileOut;
import processor.Formatter;
import processor.HtmlGetter;
import processor.RawContentGetter;

public class MyParser {

	private static ArrayList<String> allCoAuthor = new ArrayList<String>();
/**
* @param args
 * @throws Exception 
*/
public static void main(String[] args) throws Exception {
	char mode = 0;
	String fileName = "";
	if (args.length==1){
		mode = 'c';
	} else {
		mode = 'f';
		fileName = args[1];
	}
	String output="";
	 String inputFiles[] = args[0].split(",");
	 for (String inputFile : inputFiles) {
	   output+=execute(inputFile,mode);
	 }
	 if (!output.equals("")){
		output += "\n";
		output += "-----------------------------------------------------------------------\n";
		output += "7. Co-Author list sorted (Total: " + allCoAuthor.size() + "):\n";
		Collections.sort(allCoAuthor);
		for (String name:allCoAuthor){
			output += name+"\n";
		}
	 }
	 if (mode == 'c'){
		 ConsoleOut consoleOut = new ConsoleOut(output);
		 consoleOut.printInConsole();
	 } else{
		 FileOut fileOut = new FileOut(output, fileName);
		 fileOut.writeToFile();
	 }
}

private static String execute(String googleScholarURL,char mode) throws Exception{
	   HtmlGetter htmlGetter= new HtmlGetter();
	   String rawHTMLString = htmlGetter.getHTML(googleScholarURL);	
	   
	   RawContentGetter rawContentGetter = new RawContentGetter(rawHTMLString);
	   ArrayList<String> authorNames = rawContentGetter.getName();
	   ArrayList<String> coAuthorNames = rawContentGetter.getCoAuthor();
	   int totalCitation = rawContentGetter.getAllCitation();
	   int paperCitation = rawContentGetter.getPaperCitation();
	   ArrayList<String> firstThreePublication = rawContentGetter.getFirstThreePublications();
	   int i10IndexCitation = rawContentGetter.getI10IndexCitation();
	   
	   for (String name:coAuthorNames){
	       allCoAuthor.add(name);
	   }
	   
	   Formatter formatter = new Formatter(authorNames, coAuthorNames, totalCitation,paperCitation,firstThreePublication,i10IndexCitation);
	   String format = formatter.format();
	   return format;
}
}
