package com.path.finder.console;

import java.util.ArrayList;
import java.util.List;

import com.path.finder.model.Graph;

public class PathFinder 
{
	private Graph graph;
	private static String[] labels = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P" };
	private static String[] coordinates = { "1,2", "1,1", "1,0", "2,0", "2,1", "2,2", "3,1", "3,0", "4,0", "5,0", "5,1", "5,2","6,2", "6,1", "6,0", "4,1" };
	private static String[] edges = { "A-B", "A-F", "E-F", "B-E", "B-C", "C-D", "D-E", "E-G", "D-H", "H-G", "H-I", "I-P", "P-K","I-J", "J-K", "K-N", "J-O", "O-N", "K-L", "L-M", "N-M" };
//	private static String[] labels = {"A","B","C","D"};
//	private static String[] coordinates = {"0,1","1,1","2,1","1,0"};
//	private static String[] edges = {"A-B","A-D","B-C","D-C","B-D"};
//	private static String[] labels = {"A","B","C","D","E"};
//	private static String[] coordinates = {"0,1","1,1","2,1","3,1","2,0"};
//	private static String[] edges = {"A-B","A-E","B-E","B-C","C-E","C-D","D-E"};

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
	public void findShortestPath(String source, String destination)
	{
		List<String> pathList = graph.useDijkstra(source, destination);		
		graph.printDjkstraResult();
		if(pathList != null && pathList.size() != 0)
		{
			System.out.println("The shortest path from " + source + " to destination " + destination + " is");
			for(String path : pathList)
			{
				System.out.print(" " + path + " ");
			}
			System.out.println("");
		}
		else
		{
			System.out.println("No shortest path found");
		}
	}
	
	//Method for finding shortest path from list of source and destination provided
	public void findShortestPath(List<String> souDesList)
	{
		for(String temp : souDesList)
		{
			String[] sarr = temp.split("-");
			findShortestPath(sarr[0], sarr[1]);
		}
	}
	
	private void checkForCollision()
	{
		
	}
	
	
	public static void main(String[] args)
	{
		PathFinder p = new PathFinder();
		p.addVertices(labels,edges,coordinates);
		List<String> pathList = new ArrayList<String>();
		pathList.add("A-N");
		pathList.add("M-B");
		pathList.add("A-D");
		p.findShortestPath(pathList);
	}
}
