package example;

import java.io.IOException;

import api.Graph;
import pa1.Crawler;
import pa1.Index;

public class Testing {

	public static void main(String[] args) throws IOException {
		
		String url = "http://web.cs.iastate.edu/~smkautz";
		int maxDepth = 4;
		int maxPages = 30;
		Crawler crawler = new Crawler(url, maxDepth, maxPages);
		Graph<String> graph = crawler.crawl();
		Index index = new Index(graph.vertexDataWithIncomingCounts());
		index.makeIndex();

	}

}
