package processor;

import java.util.ArrayList;

public class Formatter {

	private ArrayList<String> authorNames;
	private ArrayList<String> coAuthorNames;
	private int totalCitationNum;
	private int paperCitation;
	private ArrayList<String> firstThreePublications;
	private int i10IndexCitation;

	public Formatter(ArrayList<String> authorNames, ArrayList<String> coAuthorNames, int totalCitationNum,
			int paperCitation, ArrayList<String> firstThreePublications, int i10IndexCitation) {
		this.authorNames = authorNames;
		this.coAuthorNames = coAuthorNames;
		this.totalCitationNum = totalCitationNum;
		this.paperCitation = paperCitation;
		this.firstThreePublications = firstThreePublications;
		this.i10IndexCitation = i10IndexCitation;
	}

	public String format(){
		String result = "";
		result += "-----------------------------------------------------------------------\n";
		result += "1. Name of Author:\n";
		for (int i=0; i < authorNames.size(); i++){
			result += "\t" + authorNames.get(i) + "\n";
		}
		result += "2. Number of All Citations:\n" + "\t" + totalCitationNum + "\n";
		result += "3. Number of i10-index after 2009:\n" + "\t" + i10IndexCitation + "\n";
		result += "4. Title of the first 3 publications:\n";
		for (int j=0; j < firstThreePublications.size(); j++){
			result += "\t" + (j+1) + "-   " + firstThreePublications.get(j) + "\n";
		}
		result += "5. Total paper citation (first 5 papers):\n" + "\t" + paperCitation + "\n";
		result += "6. Total Co-Authors:\n" + "\t" +  coAuthorNames.size() + "\n";
		return result;
	}
}
