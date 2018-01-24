package com.path.finder.console;

import java.util.List;
import com.path.finder.model.Graph;

public class PathFinder 
{
	private Graph graph;
	private static String[] labels = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P" };
	private static String[] coordinates = { "1,2", "1,1", "1,0", "2,0", "2,1", "2,2", "3,1", "3,0", "4,0", "5,0", "5,1", "5,2","6,2", "6,1", "6,0", "4,1" };
	private static String[] edges = { "A-B", "A-F", "E-F", "B-E", "B-C", "C-D", "D-E", "E-G", "D-H", "H-G", "H-I", "I-P", "P-K","I-J", "J-K", "K-N", "J-O", "O-N", "K-L", "L-M", "N-M" };
	
	public PathFinder()
	{
		graph = new Graph();		
	}
	
	//Method for adding new vertices to graph without coordinates
	public void addVertices(String[] labels, String[] edges)
	{
		for (int i = 0; i < labels.length; i++) 
		{
			graph.addVertex(labels[i]);
		}
		for (int j = 0; j < edges.length; j++) 
		{
			String[] subEdges = edges[j].split("-");
			graph.addEdge(subEdges[0], subEdges[1]);
		}
	}
	
	//Method for adding new vertices to graph with coordinates
	public void addVertices(String[] labels, String[] edges, String[] coordinates)
	{
		for (int i = 0; i < labels.length; i++) 
		{
			String[] subCoordinates = coordinates[i].split(",");
			graph.addVertex(labels[i], Integer.valueOf(subCoordinates[0]), Integer.valueOf(subCoordinates[1]));
		}
		for (int j = 0; j < edges.length; j++) 
		{
			String[] subEdges = edges[j].split("-");
			graph.addEdge(subEdges[0], subEdges[1]);
		}
	}
	
	//Method for finding shortest path from source to destination
	public List<String> findShortestPath(String source, String destination)
	{
		List<String> pathList = graph.useDijkstra(source, destination);		
		graph.printDjkstraResult();
		if(pathList != null && !pathList.isEmpty())
		{
			System.out.println("The shortest path from " + source + " to destination " + destination + " is");
			for(String path : pathList)
			{
				System.out.print(" " + path + " ");
			}
			System.out.println("\n");
		}
		else
		{
			System.out.println("No shortest path found");
		}
		return pathList;
	}
	
	//Method for detecting collision across the two obtained path and provide suggestions for collision free way
	private void collisionAvoidance(List<String> path1, List<String> path2)
	{
		graph.collisionAvoidance(path1, path2);
	}
	
	public static void main(String[] args)
	{
		PathFinder p = new PathFinder();
		p.addVertices(labels,edges,coordinates);
		List<String> path1 = p.findShortestPath("A","M");
		List<String> path2 = p.findShortestPath("N", "B");
		p.collisionAvoidance(path1, path2);
	}
}
