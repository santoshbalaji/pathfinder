package com.path.finder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph 
{
	private Map<String, Vertex> vertexMap;
	private Map<String, Edge> edgeMap;
	private int vertexCount;
	private int edgeCount;
	
	//Constructor which initializes the vertex and edge map 
	public Graph() 
	{
		vertexMap = new HashMap<String, Vertex>();
		edgeMap = new HashMap<String, Edge>();
	}
	
	//Adding new vertex for graph without coordinates
	public void addVertex(String label) 
	{
		Vertex vertex = new Vertex(label);
		vertexMap.put(vertex.getLabel(), vertex);
		vertexCount++;
	}

	//Adding new vertex for graph with coordinates
	public void addVertex(String label, int x, int y) 
	{
		Vertex vertex = new Vertex(label, x, y);
		vertexMap.put(vertex.getLabel(), vertex);
		vertexCount++;
	}
	
	//Adding new edges 
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
	
	//Method for running depth first search traversal
	public void useDFS(String source,String destination,int option)
	{
		if(option == 1)
		{
			System.out.println("---------Discovering the whole graph---------");
			System.out.println("---------Starting source -" + source + "-------");
			useDFSExplore(source, new ArrayList<String>());
		}
		else if(option == 2)
		{
			System.out.println("---------Discovering all path for given destination from source---------");
			System.out.println("---------Starting source -" + source + "Destination -------");
			useDFSFindAllPath(source,destination);
		}
	}
	
	private void useDFSExplore(String source, List<String> currentPath)
	{
		Vertex sourceVertex = vertexMap.get(source);
		List<Edge> neighboursList = sourceVertex.getEdgeList();
		sourceVertex.setVisited(true);	
		currentPath.add(source);
		for(Edge edge : neighboursList)
		{
			if(!edge.getTwo().isVisited())
			{
				useDFSExplore(edge.getTwo().getLabel(), currentPath);
			}
		}
		
		for(String path : currentPath)
		{
			System.out.print(" " + path + " ");
		}
		System.out.println("");
		currentPath.remove(currentPath.size() - 1);
		sourceVertex.setVisited(false);
	}
	
	private void useDFSFindAllPath(String source,String destination)
	{
		
	}
	

	public boolean useDFSTraversal(String source,String destination)
	{
		System.out.println("-------Source Vertex-" + source + "-----------");
		System.out.println("-------Destin Vertex-" + destination + "----------");
		Vertex sourceVertex = vertexMap.get(source);
		Vertex destinationVertex = vertexMap.get(destination);
		sourceVertex.setVisited(true);
		
		if(sourceVertex.getLabel().equals(destinationVertex.getLabel()))
		{
			System.out.println("Matching record found");
			return true;
		}
		
		List<Edge> neighboursList = sourceVertex.getEdgeList();
		for(Edge edge : neighboursList)
		{
			if(!edge.getTwo().isVisited())
			{
				boolean status = useDFSTraversal(edge.getTwo().getLabel(), destination);
				if(status)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void useBFSTraversal(String source,String destination)
	{
		
	}

	public Map<String, Vertex> getVertexMap() 
	{
		return vertexMap;
	}

	public void setVertexMap(Map<String, Vertex> vertexMap) 
	{
		this.vertexMap = vertexMap;
	}

	public Map<String, Edge> getEdgeMap() 
	{
		return edgeMap;
	}

	public void setEdgeMap(Map<String, Edge> edgeMap) 
	{
		this.edgeMap = edgeMap;
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
		
		graph.useDFS("A", null, 1);
				
	}
}
