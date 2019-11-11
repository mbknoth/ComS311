package example;

import java.io.IOException;

import pa1.Crawler;

public class Testing {

	public static void main(String[] args) throws IOException {
		
		String url = "http://web.cs.iastate.edu/~smkautz";
		int maxDepth = 4;
		int maxPages = 30;
		Crawler crawler = new Crawler(url, maxDepth, maxPages);
		crawler.crawl();

	}

}
