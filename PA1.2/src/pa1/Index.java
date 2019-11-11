package pa1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import api.TaggedVertex;
import api.Util;

/**
 * Implementation of an inverted index for a web graph.
 * 
 * @author Matthew Smith and Mitchell Knoth
 */
public class Index
{
	List<TaggedVertex<String>> urls;
	HashMap<String, Integer> invertedIndex;
	/**
	 * Constructs an index from the given list of urls.  The
	 * tag value for each url is the indegree of the corresponding
	 * node in the graph to be indexed.
	 * @param urls
	 *   information about graph to be indexed
	 * @throws IOException 
	 */
	public Index(List<TaggedVertex<String>> urls) throws IOException
	{

		invertedIndex = new HashMap<String, Integer>();

		for(TaggedVertex url: urls) {
			String doc = Jsoup.connect(url.getVertexData().toString()).get().body().text();
			Util.stripPunctuation(doc);
			Scanner scan = new Scanner(doc);
			while(scan.hasNext()) {
				String word = scan.next();
				if(!Util.isStopWord(word)) {
					if(invertedIndex.containsKey(word)) {
						int oldValue = getWordCount(word);
						invertedIndex.replace(word, oldValue++);
					}else if(!invertedIndex.containsKey(word)) {
						invertedIndex.put(word, 0);
					}
				}

			}
		}
	}

	private int getWordCount(String word) {
		return invertedIndex.get(word);
	}

	/**
	 * Creates the index.
	 */
	public void makeIndex()
	{
		// TODO
	}

	/**
	 * Searches the index for pages containing keyword w.  Returns a list
	 * of urls ordered by ranking (largest to smallest).  The tag 
	 * value associated with each url is its ranking.  
	 * The ranking for a given page is the number of occurrences
	 * of the keyword multiplied by the indegree of its url in
	 * the associated graph.  No pages with rank zero are included.
	 * @param w
	 *   keyword to search for
	 * @return
	 *   ranked list of urls
	 */
	public List<TaggedVertex<String>> search(String w)
	{
		// TODO
		return null;
	}


	/**
	 * Searches the index for pages containing both of the keywords w1
	 * and w2.  Returns a list of qualifying
	 * urls ordered by ranking (largest to smallest).  The tag 
	 * value associated with each url is its ranking.  
	 * The ranking for a given page is the number of occurrences
	 * of w1 plus number of occurrences of w2, all multiplied by the 
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 * @param w1
	 *   first keyword to search for
	 * @param w2
	 *   second keyword to search for
	 * @return
	 *   ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithAnd(String w1, String w2)
	{
		// TODO
		return null;
	}

	/**
	 * Searches the index for pages containing at least one of the keywords w1
	 * and w2.  Returns a list of qualifying
	 * urls ordered by ranking (largest to smallest).  The tag 
	 * value associated with each url is its ranking.  
	 * The ranking for a given page is the number of occurrences
	 * of w1 plus number of occurrences of w2, all multiplied by the 
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 * @param w1
	 *   first keyword to search for
	 * @param w2
	 *   second keyword to search for
	 * @return
	 *   ranked list of urls
	 */
	public List<TaggedVertex<String>> searchWithOr(String w1, String w2)
	{
		// TODO
		return null;
	}

	/**
	 * Searches the index for pages containing keyword w1
	 * but NOT w2.  Returns a list of qualifying urls
	 * ordered by ranking (largest to smallest).  The tag 
	 * value associated with each url is its ranking.  
	 * The ranking for a given page is the number of occurrences
	 * of w1, multiplied by the 
	 * indegree of its url in the associated graph.
	 * No pages with rank zero are included.
	 * @param w1
	 *   first keyword to search for
	 * @param w2
	 *   second keyword to search for
	 * @return
	 *   ranked list of urls
	 */
	public List<TaggedVertex<String>> searchAndNot(String w1, String w2)
	{
		// TODO
		return null;
	}
}
