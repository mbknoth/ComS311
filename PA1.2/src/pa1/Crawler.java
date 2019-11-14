package pa1;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import api.Graph;
import pa1.GraphService;
import api.Util;

/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author Matthew Smith and Mitchell Knoth
 */
public class Crawler
{
	/**
	 * Constructs a Crawler that will start with the given seed url, including
	 * only up to maxPages pages at distance up to maxDepth from the seed url.
	 * @param seedUrl
	 * @param maxDepth
	 * @param maxPages
	 * @throws IOException 
	 */

	private String seedUrl;
	private int maxDepth;
	private int maxPages;

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
	 * @throws IOException 
	 */
	public Graph<String> crawl()
	{

		Queue<String> queue = new PriorityQueue<String>();
		Map<String, Boolean> discovered = new HashMap<String, Boolean>();
		GraphService<String> graph = new GraphService<String>();
		Map<String, Integer> distance = new HashMap<String, Integer>();
		int layer = 0;
	
		distance.put(this.seedUrl, layer);
		queue.add(this.seedUrl);
		graph.addVertex(this.seedUrl);
		discovered.put(seedUrl, true);
		
		System.out.println("Fetching " + this.seedUrl);
	
		try {
			while(!queue.isEmpty()) {

				String currentPage = queue.remove();
				Document doc = Jsoup.connect(currentPage).get();
				Elements links = doc.select("a[href]");
				if(layer >= maxDepth) {
					break;
				}
				layer++;
				for(Element link: links){
					
					String v = link.attr("abs:href");
					System.out.println("Found " + v);
					Document temp = null;
					if(discovered.containsKey(v)) {
						continue;
					}
					
					if(!Util.ignoreLink(currentPage, v)) {
						
						try {
							temp = Jsoup.connect(v).get();
							queue.add(v);
							distance.put(v, layer);
							graph.addVertex(v);
							graph.addEdge(currentPage, v);
							if(graph.vertexData().size() >= maxPages) {
								graph.vertexDataWithIncomingCounts();
								return graph;
							}
							discovered.put(v, true);
							
						}catch (UnsupportedMimeTypeException e){
							e.printStackTrace();

						}catch (HttpStatusException  e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		graph.vertexDataWithIncomingCounts();
		return graph;
	}
}
