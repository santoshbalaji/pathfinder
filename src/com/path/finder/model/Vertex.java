package com.path.finder.model;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String label;
	private List<Edge> edgeList;
	private int x;
	private int y;
	private boolean visited;

	public Vertex(String label) {
		this.label = label;
		this.x = GraphConstant.DEFAULT_X;
		this.y = GraphConstant.DEFAULT_Y;
		this.edgeList = new ArrayList<Edge>();
	}

	public Vertex(String label, int x, int y) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.edgeList = new ArrayList<Edge>();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
