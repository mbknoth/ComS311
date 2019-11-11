package pa1;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import api.Util;
import api.Graph;

/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author PLEASE FILL IN TEAM MEMBER NAMES HERE
 */
public class Crawler
{
	String seedUrl;
	int maxDepth, maxPages;

	/**
	 * Constructs a Crawler that will start with the given seed url, including
	 * only up to maxPages pages at distance up to maxDepth from the seed url.
	 * @param seedUrl
	 * @param maxDepth
	 * @param maxPages
	 */
	public Crawler(String seedUrl, int maxDepth, int maxPages)
	{
		this.seedUrl = seedUrl;
		this.maxDepth = maxDepth;
		this.maxPages = maxPages;
	}

	/**
	 * Creates a web graph for the portion of the web obtained by a BFS of the 
	 * web starting with the seed url for this object, subject to the restrictions
	 * implied by maxDepth and maxPages.  
	 * @return
	 *   an instance of Graph representing this portion of the web
	 */
	public Graph<String> crawl()
	{
		Queue<String> queue = new PriorityQueue<String>();
		List<Boolean> discovered = new ArrayList<Boolean>();

		queue.add(seedUrl);

		System.out.println("Fetching " + this.seedUrl);
		Document doc;
		try {
			doc = Jsoup.connect(this.seedUrl).get();
			Elements links = doc.select("a[href]");
			for(Element link: links){
				String v = link.attr("abs:href");
				System.out.println("Found " + v);

				Document temp = null;
				if(!Util.ignoreLink(this.seedUrl, v)) {

				}

			

				return null;

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

