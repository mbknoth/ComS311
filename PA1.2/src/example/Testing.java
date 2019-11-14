package example;

import java.io.IOException;
import java.util.List;

import api.Graph;
import api.TaggedVertex;
import pa1.Crawler;
import pa1.Index;

public class Testing {

	public static void main(String[] args) throws IOException {
		
		String url = "https://en.wikipedia.org/wiki/Gouraud_shading";
		int maxDepth = 4;
		int maxPages = 25;
		Crawler crawler = new Crawler(url, maxDepth, maxPages);
		Graph<String> graph = crawler.crawl();
	
		
		
		
		Index index = new Index(graph.vertexDataWithIncomingCounts());
		index.makeIndex();
		//List<TaggedVertex<String>> search = index.search("Link");
		List<TaggedVertex<String>> search = index.searchWithAnd("shading", "Gouraud");

		//System.out.println(search);

	}

}
