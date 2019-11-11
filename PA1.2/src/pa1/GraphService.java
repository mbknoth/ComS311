package pa1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.Graph;

public class GraphService<E> implements Graph<E>{
	
	private Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
	
	private ArrayList<E> vertexList = new ArrayList<E>();
	
	public void addVertex(E vert) {
		vertexList.add(vert);
		adjList.put(vertexList.size() - 1, new ArrayList<Integer>());
	}
	
	public void addEdge(E source, E destination) {
		if(!vertexList.contains(source)) return;
		if(!vertexList.contains(destination)) return;
		
		Integer srcIdx = vertexList.indexOf(source);
		Integer destIdx = vertexList.indexOf(destination);
		
		if(adjList.get(srcIdx).contains(destIdx)) return;
		else adjList.get(srcIdx).add(destIdx);
	}

	@Override
	public ArrayList<E> vertexData() {
		return this.vertexList;
	}

	@Override
	public ArrayList vertexDataWithIncomingCounts() {
		int in[] = new int[adjList.size()];
		ArrayList<Integer> list = new ArrayList<Integer>();
  
        for (int i = 0; i < adjList.size(); i++) { 
  
            list = (ArrayList<Integer>) adjList.get(i); 
  
            // Out degree for ith vertex will be the count 
            // of direct paths from i to other vertices 
            for (int j = 0; j < list.size(); j++) 
  
                // Every vertex that has an incoming  
                // edge from i 
                in[list.get(j)]++; 
        } 
        return list;
	}

	@Override
	public List<Integer> getNeighbors(int index) {
		return new ArrayList<Integer>(adjList.get(index));
	}

	@Override
	public List<Integer> getIncoming(int index) {

		List<Integer> inc = new ArrayList<>();
		
		for(Integer i : adjList.keySet()) {
			if (adjList.get(i).contains(index)) {
				inc.add(i);
			}
		}
		
		return inc;
	}

}