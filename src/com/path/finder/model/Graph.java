package com.path.finder.model;

import java.util.ArrayList;
import java.util.Collections;
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
	
	//Method for finding shortest path from source to destination using dijkstra algorithm
	public List<String> useDijkstra(String source, String destination)
	{
		boolean status = false;
		List<String> pathList = new ArrayList<String>();
		//Running dijkstra algorithm from source node
		useDijkstra(source);
		while(!status)
		{	
			//Finding the shortest path from data obtained in dijkstra algorithm
			if(distanceFromMap.containsKey(destination))
			{
				if(source.equals(destination))
				{
					status = true;
				}
				pathList.add(destination);
				destination = distanceFromMap.get(destination);
			}
			else
			{
				status = true;
				pathList = new ArrayList<String>();
			}
		}
		Collections.reverse(pathList);
		return pathList;
	}
	
	//Method for using dijkstra algorithm on graph from a source node
	private void useDijkstra(String source)
	{
		if(source != null)
		{
			Vertex sourceVertex = vertexMap.get(source);
			if(sourceVertex != null)
			{
				List<Edge> queue = new ArrayList<Edge>();
				distanceMap = new HashMap<String,Integer>();
				distanceFromMap = new HashMap<String,String>();
				sourceVertex.setVisited(true);
				queue.addAll(sourceVertex.getEdgeList());
				distanceMap.put(source, 0);
				distanceFromMap.put(source, source);
				while(!queue.isEmpty())
				{
					if(!queue.get(0).getTwo().isVisited())
					{
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
				for(String key : vertexMap.keySet())
				{
					vertexMap.get(key).setVisited(false);
				}
			}
		}
	}
	
	//Method for printing results after dijkstra algorithm completes
	public void printDjkstraResult()
	{
		for(String k : distanceMap.keySet())
		{
			System.out.print(" " + k + "-" + distanceMap.get(k) + " ");
		}
		System.out.println("");
		for(String k : distanceFromMap.keySet())
		{
			System.out.print(" " + k + "-" + distanceFromMap.get(k) + " ");	
		}
		System.out.println("");
	}
	
	//Method for detecting collision across the two obtained path and provide suggestions for collision free way
	public void collisionAvoidance(List<String> path1, List<String> path2)
	{
		int position = -1,backPos1 = 0, frontPos1 = 0, backPos2 = 0, frontPos2 = 0;
		//1. Looking for collision for robots on the path. My assumption is that only one robot can use one path and occupy one node. 
		for(int i = 0; i < path1.size() && i < path2.size(); i++)
		{
			if(path1.get(i).equals(path2.get(i)))
			{
				position = i;
				backPos1 = -1;
				frontPos1 = 1;
				backPos2 = -1;
				frontPos2 = 1;
				break;
			}
			else if ( i+1 < path2.size() && path1.get(i).equals(path2.get(i + 1)))
			{
				position = i;
				backPos1 =  -1;
				frontPos1 = 2;
				backPos2 = 0;
				frontPos2 = 1;
				break;
			}
		}
		
		//2. Check for collision presence. If present then route is re-examined to provide collision free way.
		if(position == -1)
		{
			System.out.println("No collision detected. You are good to go !!!!!");
		}
		else
		{
			collisionAvoidanecByWaiting(position, backPos1, frontPos1,path1, path2);
			collisionAvoidanceByReRouting(position, backPos2, frontPos2 ,path1,path2);
		}
	}
	
	//Method to provide collision free way by making a robot wait at a node
	private void collisionAvoidanecByWaiting(int position, int backPos, int frontPos, List<String> path1, List<String> path2)
	{
		System.out.println("Collision detected at position " + position + " . Looking for possible route ...");
		System.out.println("Any of the following ways can be adopted to avoid collision ....");
		for(int i = position + backPos,j = position + frontPos;  i >= 0 && j < path2.size(); i--,j++)
		{
			if(!path1.get(i).equals(path2.get(j)))
			{
				System.out.println("The bot 1 can be stopped at " +  path1.get(i) + " for "+ (j - i -1) +" second to avoid collision");
				break;
			}
		}
		for(int i = position + backPos,j = position + frontPos;  i >= 0 && j < path1.size(); i--,j++)
		{
			if(!path2.get(i).equals(path1.get(j)))
			{
				System.out.println("The bot 2 can be stopped at " +  path2.get(i) + " for " + (j - i - 1) +" second to avoid collision");
				break;
			}
		}
	}
	
	//Method to provide collision free way by waiting in a near by node and following the original path later 
	public void collisionAvoidanceByReRouting(int position,int backPos, int frontPos, List<String> path1, List<String> path2)
	{
		boolean status = false;
		for(int i = position + backPos,j = position + frontPos;  i >= 0 && j < path2.size(); i--,j++)
		{
			for(Edge edge : vertexMap.get(path1.get(i)).getEdgeList())
			{
				if(!edge.getTwo().getLabel().equals(path2.get(j)) && !path1.contains(edge.getTwo().getLabel()))
				{
					System.out.println("The bot 1 can be routed to " + edge.getTwo().getLabel() + " from " + edge.getOne().getLabel() + " with additional time of " + (j -i + 1 ) + " seconds");
					status = true;
					break;
				}
			}
			if(status)
			{
				break;
			}
		}
		for(int i = position + backPos,j = position + frontPos;  i >= 0 && j < path1.size(); i--,j++)
		{
			for(Edge edge : vertexMap.get(path2.get(i)).getEdgeList())
			{
				if(!edge.getTwo().getLabel().equals(path1.get(j)) && !path2.contains(edge.getTwo().getLabel()))
				{
					System.out.println("The bot 2 can be routed to " + edge.getTwo().getLabel() + " from " + edge.getOne().getLabel() + " with additional time of " + (j -i + 1 ) + " seconds");
					status = true;
					break;
				}
			}
			if(status)
			{
				break;
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
}
