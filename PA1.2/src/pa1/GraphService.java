package pa1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.Graph;
import api.TaggedVertex;

/**
 * An implementation of the graph interface.
 * @author Matthew Smith and Mitchell Knoth
 *
 * @param <E>
 */

public class GraphService<E> implements Graph<E>{
	
	private Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
	
	private ArrayList<E> vertexList = new ArrayList<E>();
	
	/**
	 * Allows us to add a vertex to our graph.
	 * @param vert
	 */
	public void addVertex(E vert) {
		vertexList.add(vert);
		adjList.put(vertexList.size() - 1, new ArrayList<Integer>());
	}
	
	/**
	 * Allows us to add an edge to our graph with a 
	 * given source and destination vertex.
	 * @param source
	 * @param destination
	 */
	public void addEdge(E source, E destination) {
		if(!vertexList.contains(source)) return;
		if(!vertexList.contains(destination)) return;
		
		Integer srcIdx = vertexList.indexOf(source);
		Integer destIdx = vertexList.indexOf(destination);
		
		if(adjList.get(srcIdx).contains(destIdx)) return;
		else adjList.get(srcIdx).add(destIdx);
	}

	/**
	   * Returns an ArrayList of the actual objects constituting the vertices
	   * of this graph.
	   * @return
	   *   ArrayList of objects in the graph
	   */
	@Override
	public ArrayList<E> vertexData() {
		return this.vertexList;
	}

	 /**
	   * Returns an ArrayList that is identical to that returned by vertexData(), except
	   * that each vertex is associated with its incoming edge count.
	   * @return
	   *   ArrayList of objects in the graph, each associated with its incoming edge count
	   */
	@Override
	public ArrayList<TaggedVertex<E>> vertexDataWithIncomingCounts() {
		
		ArrayList<TaggedVertex<E>> list = new ArrayList<TaggedVertex<E>>();
		for(E vertex: vertexList) {
			
			TaggedVertex taggedVertex = new TaggedVertex(vertex, getIncoming(vertexList.indexOf(vertex)).size());
			list.add(taggedVertex);
		}
		return list;
	}

	/**
	   * Returns a list of outgoing edges, that is, a list of indices for neighbors
	   * of the vertex with given index.
	   * This method may throw ArrayIndexOutOfBoundsException if the index 
	   * is invalid.
	   * @param index
	   *   index of the given vertex according to vertexData()
	   * @return
	   *   list of outgoing edges
	   */
	@Override
	public List<Integer> getNeighbors(int index) {
		return new ArrayList<Integer>(adjList.get(index));
	}

	/**
	   * Returns a list of incoming edges, that is, a list of indices for vertices 
	   * having the given vertex as a neighbor.
	   * This method may throw ArrayIndexOutOfBoundsException if the index 
	   * is invalid. 
	   * @param index
	   *   index of the given vertex according to vertexData()
	   * @return
	   *   list of incoming edges
	   */
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
