package pa1;

import java.io.IOException;
import java.util.ArrayList;
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
	HashMap<String, List<TaggedVertex<String>>> invertedIndex;
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
		this.urls = urls;
	}

	/**
 	 * This method will find the corresponding word in the inverted index
	 * and will iterate through the List of TaggedVertexes. Once the correct url is found
	 * the method will incremement the wordcount stored at that value by one. 
	 * @param word
	 *   the word in the inverted index
	 * @param url
	 *   the corresponding url for the word
	 */
	public void updateWordCount(String word, String url) {
		List<TaggedVertex<String>> urlAndCountList = invertedIndex.get(word);
		
		

		if(urlAndCountList == null) {
			urlAndCountList = new ArrayList<TaggedVertex<String>>();
			TaggedVertex<String> newUrlAndCount = new TaggedVertex<String>(url, 1);
			urlAndCountList.add(newUrlAndCount);
			invertedIndex.put(word, urlAndCountList);
		}
		else {
			//for(TaggedVertex<String> urlAndCount : urlAndCountList) {.getVertexData().equals(url)
			for(int i = 0; i < urlAndCountList.size(); i++) {
				if(urlAndCountList.get(i).getVertexData().equals(url)) {
					int oldCount = urlAndCountList.get(i).getTagValue();
					oldCount++;
					urlAndCountList.set(i, new TaggedVertex<String>(url.toString(), oldCount));
					return;
				}
			}
			urlAndCountList.add(new TaggedVertex<String>(url, 1));
		}
	}

	/**
	 * Creates the index.
	 * @throws IOException 
	 */
	public void makeIndex()
	{
		invertedIndex = new HashMap<String, List<TaggedVertex<String>>>();

		for(TaggedVertex<String> url: urls) {
			String doc;
			try {
				doc = Jsoup.connect(url.getVertexData().toString()).get().body().text();

				Util.stripPunctuation(doc);
				Scanner scan = new Scanner(doc);
				while(scan.hasNext()) {
					String word = scan.next();
					if(!Util.isStopWord(word)) {
						updateWordCount(word, url.getVertexData());
					}
				}
				scan.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		List<TaggedVertex<String>> wordList = invertedIndex.get(w);
		List<TaggedVertex<String>> rankedList = new ArrayList<>();
				
		for(TaggedVertex<String> url: wordList) {
			for(TaggedVertex<String> invertedIndexUrl: urls) {
				if(url.getVertexData().equals(invertedIndexUrl.getVertexData())) {
					int rank = url.getTagValue() * invertedIndexUrl.getTagValue();
					if(rank == 0) {
						break;
					}
					rankedList.add(new TaggedVertex<String>(url.getVertexData(), rank));
					break;
				}
			}
		}
		
		System.out.println(rankedList);
		return sortingOfRanks(rankedList);
	}

	/**
	 * Takes in the given array and will sort it using same approach to selection sort.
	 *
	 * @param list
	 * 	List that is in need of sorting in descending order
	 * @return
	 * 	an array with the rank value of each element from largest to smallest
	 */
	public List<TaggedVertex<String>> sortingOfRanks(List<TaggedVertex<String>> list){

		for(int i = 0; i < list.size(); i++) {
			int pos = i;
			for(int j = i; j < list.size(); j++) {
				if(list.get(j).getTagValue() > list.get(pos).getTagValue()) {
					pos = j;
				}
			}
			
			TaggedVertex<String> max = list.get(pos);
			list.set(pos, list.get(i));
			list.set(i, max);
		}

		return list;
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
		List<TaggedVertex<String>> rankedList1 = new ArrayList<>();
		List<TaggedVertex<String>> rankedList2 = new ArrayList<>();
		List<TaggedVertex<String>> resultRankedList = new ArrayList<>();
		
		rankedList1 = search(w1);
		rankedList2 = search(w2);
		
		for(int i = 0; i < rankedList1.size(); i++) {
			for(int j = 0; j < rankedList2.size(); j++) {
				if(rankedList1.get(i).getVertexData().equals(rankedList2.get(j).getVertexData())) {
					if(!(rankedList1.get(i).getTagValue() == 0) && !(rankedList2.get(j).getTagValue() == 0)){
						int resultRank = rankedList1.get(i).getTagValue() + rankedList2.get(j).getTagValue();
						resultRankedList.add(new TaggedVertex<String>(rankedList1.get(i).getVertexData(), resultRank));
					}
				}
			}
		}
		
		return sortingOfRanks(resultRankedList);
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
