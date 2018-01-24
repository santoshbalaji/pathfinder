package com.path.finder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph 
{
	//Map to store the vertex 
	private Map<String, Vertex> vertexMap;
	//Map to store the edges
	private Map<String, Edge> edgeMap;
	//List to store the different available path for DFS
	private List<List<String>> pathList;
	//Map to store distance info for Dijkstra
	private Map<String,Integer> distanceMap;
	//Map to store distance from info for Dijkstra
	private Map<String,String> distanceFromMap;
	//Integer to store vertex count
	private int vertexCount;
	//Integer to store edge count
	private int edgeCount;
	
	//Constructor Initializing the Vertex and Edge Map
 	public Graph() 
	{
		vertexMap = new HashMap<String, Vertex>();
		edgeMap = new HashMap<String, Edge>();
	}
	
	//Adding new vertex without coordinates
	public void addVertex(String label) 
	{
		Vertex vertex = new Vertex(label);
		vertexMap.put(vertex.getLabel(), vertex);
		vertexCount++;
	}

	//Adding new vertex with coordinates
	public void addVertex(String label, int x, int y) 
	{
		Vertex vertex = new Vertex(label, x, y);
		vertexMap.put(vertex.getLabel(), vertex);
		vertexCount++;
	}
	
	//Add new edges for vertex
	public void addEdge(String label1, String label2) 
	{
		Edge edge1 = new Edge(vertexMap.get(label1), vertexMap.get(label2));
		Edge edge2 = new Edge(vertexMap.get(label2), vertexMap.get(label1));
		edgeMap.put(label1 + "-" + label2, edge1);
		edgeMap.put(label2 + "-" + label1, edge2);
		vertexMap.get(label1).getEdgeList().add(edge1);
		vertexMap.get(label2).getEdgeList().add(edge2);
		edgeCount = edgeCount + 2;
	}
	
	//Main method for Depth first traversal
	public void useDFS(String source,String destination,int option)
	{
		pathList = new ArrayList<List<String>>();
		if(option == 1)
		{
			useDFSExplore(source, new ArrayList<String>());
		}
		else if(option == 2)
		{
			useDFSFindAllPath(source,destination, new ArrayList<String>());
			for(List<String> list : pathList)
			{
				for(String path : list)
				{
					System.out.print(path);
				}
				System.out.println("");
			}
		}
	}
	
	//Depth first search to explore the whole graph from source to all destinations.
	private void useDFSExplore(String source, List<String> completedPath)
	{
		Vertex sourceVertex = vertexMap.get(source);
		completedPath.add(sourceVertex.getLabel());
		sourceVertex.setVisited(true);
		List<Edge> neighboursList = sourceVertex.getEdgeList();
		for(Edge edge : neighboursList)
		{
			if(!edge.getTwo().isVisited())
			{
				useDFSExplore(edge.getTwo().getLabel(), completedPath);
			}
		}
		
		for(String path : completedPath)
		{
			System.out.print( " " + path + " " );
		}
		System.out.println("");
		completedPath.remove(completedPath.size() - 1);
		sourceVertex.setVisited(false);
	}
	
	//Depth first search to explore all available path from source to specified destination
	private void useDFSFindAllPath(String source,String destination, List<String> completedPath)
	{
		Vertex sourceVertex = vertexMap.get(source);
		completedPath.add(source);
		sourceVertex.setVisited(true);
		List<Edge> neighboursList = sourceVertex.getEdgeList();
		if(source.equals(destination))
		{
			List<String> list = new ArrayList<>();
			list.addAll(completedPath);
			pathList.add(list);
		}
		else
		{
			for(Edge edge : neighboursList)
			{
				if(!edge.getTwo().isVisited() && !source.equals(destination))
				{
					useDFSFindAllPath(edge.getTwo().getLabel(),destination, completedPath);
				}
			}
		}
		completedPath.remove(completedPath.size() - 1);
		sourceVertex.setVisited(false);
	}
	
	//Main method for Breadth first search 
	public void useBFS(String source,String destination, int option)
	{
		if(option == 1)
		{
			useBFSExplore(source);
		}
	}
	
	//Breadth first search to explore all vertex at least once
	public void useBFSExplore(String source)
	{
		List<Edge> queue = new ArrayList<Edge>();
		Vertex sourceVertex = vertexMap.get(source);
		if(sourceVertex != null)
		{
			sourceVertex.setVisited(true);
			queue.addAll(sourceVertex.getEdgeList());
			System.out.print(source);
			while(!queue.isEmpty())
			{
				if(!queue.get(0).getTwo().isVisited())
				{
					System.out.print(queue.get(0).getTwo().getLabel());
					queue.get(0).getTwo().setVisited(true);
					queue.addAll(queue.get(0).getTwo().getEdgeList());
				}
				queue.remove(0);
			}
		}
	}
	
	public void useDijkstra(String source)
	{
		Vertex sourceVertex = vertexMap.get(source);
		List<Edge> queue = new ArrayList<Edge>();
		distanceMap = new HashMap<String,Integer>();
		distanceFromMap = new HashMap<String,String>();
		
		if(sourceVertex != null)
		{
			sourceVertex.setVisited(true);
			queue.addAll(sourceVertex.getEdgeList());
			distanceMap.put(source, 0);
			while(!queue.isEmpty())
			{
				if(!queue.get(0).getTwo().isVisited())
				{
					System.out.println(queue.get(0).getTwo().getLabel());
					if(distanceMap.containsKey(queue.get(0).getTwo().getLabel()) && distanceMap.get(queue.get(0).getTwo().getLabel()) > (distanceMap.get(queue.get(0).getOne().getLabel()) + queue.get(0).getWeight()))
					{
						distanceMap.put(queue.get(0).getTwo().getLabel(),distanceMap.get(queue.get(0).getOne().getLabel()) + queue.get(0).getWeight());
						distanceFromMap.put(queue.get(0).getTwo().getLabel(), queue.get(0).getOne().getLabel());
					}
					else
					{
						distanceMap.put(queue.get(0).getTwo().getLabel(), distanceMap.get(queue.get(0).getOne().getLabel()) + queue.get(0).getWeight());
						distanceFromMap.put(queue.get(0).getTwo().getLabel(), queue.get(0).getOne().getLabel());
						
					}
					queue.get(0).getTwo().setVisited(true);
					queue.addAll(queue.get(0).getTwo().getEdgeList());
				}
				queue.remove(0);
			}
			
			System.out.println(distanceMap.size());
			System.out.println(distanceFromMap.size());
			for(String k : distanceMap.keySet())
			{
				System.out.print(" " + k + "-" + distanceMap.get(k) + " ");
			}
			System.out.println("");
			for(String k : distanceFromMap.keySet())
			{
				System.out.print(" " + k + "-" + distanceFromMap.get(k) + " ");	
			}
		}
	}
	
	public int getVertexCount() 
	{
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) 
	{
		this.vertexCount = vertexCount;
	}

	public int getEdgeCount() 
	{
		return edgeCount;
	}

	public void setEdgeCount(int edgeCount) 
	{
		this.edgeCount = edgeCount;
	}

	public static void main(String[] args) 
	{
		String[] labels = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P" };
		String[] coordinates = { "1,2", "1,1", "1,0", "2,0", "2,1", "2,2", "3,1", "3,0", "4,0", "5,0", "5,1", "5,2","6,2", "6,1", "6,0", "4,1" };
		String[] edges = { "A-B", "A-F", "E-F", "B-E", "B-C", "C-D", "D-E", "E-G", "D-H", "H-G", "H-I", "I-P", "P-K","I-J", "J-K", "K-N", "J-O", "O-N", "K-L", "L-M", "N-M" };
//		String[] labels = {"A","B","C","D"};
//		String[] coordinates = {"0,1","1,1","2,1","1,0"};
//		String[] edges = {"A-B","A-D","B-C","D-C","B-D"};
		
//		String[] labels = {"A","B","C","D","E"};
//		String[] coordinates = {"0,1","1,1","2,1","3,1","2,0"};
//		String[] edges = {"A-B","A-E","B-E","B-C","C-E","C-D","D-E"};
		
		Graph graph = new Graph();
		
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
		
//		graph.useDFS("A",null, 1);
//		graph.useDFS("A", "C", 2);
//		graph.useBFS("A", null, 1);
		graph.useDijkstra("N");		
	}
}
