package processor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawContentGetter {
	private String rawString;

	public RawContentGetter(String rawHTMLString) {
		rawString = rawHTMLString;
	}

	public ArrayList<String> getName() {
		try {
			ArrayList<String> authorNames = new ArrayList<String>();
			String reForAuthorExtraction = "<span id=\"cit-name-display\" "
					+ "class=\"cit-in-place-nohover\">(.*?)</span>";
			Pattern patternObject = Pattern.compile(reForAuthorExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			while (matcherObject.find()) {
				authorNames.add(matcherObject.group(1));
			}
			return authorNames;

		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return null;
	}

	public int getAllCitation() {
		try {
			String citation = new String();
			String reForCitationExtraction = "<tr class=\"cit-borderbottom\"><td class=\"cit-caption\"><a href=(.*?) class=\"cit-dark-link\" onclick=(.*?) title=(.*?)>Citations</a></td><td class=\"cit-borderleft cit-data\">(.*?)</td><td class=\"cit-borderleft cit-data\">(.*?)</td></tr>";
			// String reForCitationExtraction = "<td class=\"cit-borderleft
			// cit-data\">(.*?)</td>";
			Pattern patternObject = Pattern.compile(reForCitationExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			if (matcherObject.find()) {
				citation = matcherObject.group(4);
			}
			int citationNum = Integer.parseInt(citation);
			return citationNum;
		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return (Integer) null;
	}

	public int getI10IndexCitation() {
		try {
			String citation = new String();
			String reForI10IndexCitationExtraction = "<tr><td class=\"cit-caption\"><a href=(.*?) class=\"cit-dark-link\" onclick=(.*?) title=(.*?)>i10-index</a></td><td class=\"cit-borderleft cit-data\">(.*?)</td><td class=\"cit-borderleft cit-data\">(.*?)</td></tr>";
			Pattern patternObject = Pattern.compile(reForI10IndexCitationExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			if (matcherObject.find()) {
				citation = matcherObject.group(5);
			}
			int citationNum = Integer.parseInt(citation);
			return citationNum;
		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return (Integer) null;
	}

	public ArrayList<String> getFirstThreePublications() {
		try {
			ArrayList<String> firstThreePublications = new ArrayList<String>();
			String reForPublicationsExtraction = "<a href=(.*?) class=\"cit-dark-large-link\">(.*?)</a>";
			Pattern patternObject = Pattern.compile(reForPublicationsExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			int i = 0;
			while (matcherObject.find() && i < 3) {
				String publications = matcherObject.group(2);
				firstThreePublications.add(publications);
				i++;
			}
			return firstThreePublications;
		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return null;
	}

	public int getPaperCitation() {
		try {
			String reForPaperCitationExtraction = "<td id=\"col-citedby\"><a class=\"cit-dark-link\" href=(.*?)>(.*?)</a></td>";
			Pattern patternObject = Pattern.compile(reForPaperCitationExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			int i = 0;
			int citationNum = 0;
			while (matcherObject.find() && i < 5) {
				String paperCitation = matcherObject.group(2);
				citationNum += Integer.parseInt(paperCitation);
				i++;
			}
			return citationNum;
		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return 0;
	}

	public ArrayList<String> getCoAuthor() {
		try {
			ArrayList<String> coAuthorNames = new ArrayList<String>();
			String reForCoAuthorExtraction = "<a class=\"cit-dark-link\" href=(.*?) title=(.*?)>(.*?)</a>";
			Pattern patternObject = Pattern.compile(reForCoAuthorExtraction);
			Matcher matcherObject = patternObject.matcher(rawString);
			while (matcherObject.find()) {
				if (!matcherObject.group(3).equals("Citations")) {
					coAuthorNames.add(matcherObject.group(3));
				}
			}
			return coAuthorNames;

		} catch (Exception e) {
			System.out.println("malformed URL or cannot open connection to " + "given URL");
		}
		return null;
	}
}
