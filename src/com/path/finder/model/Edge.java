package com.path.finder.model;

public class Edge {
	private Vertex one;
	private Vertex two;
	private int weight;
	
	public Edge(Vertex one, Vertex two)
	{
		this.one = one;
		this.two = two;
		this.weight = GraphConstant.DEFAULT_WEIGHT;
	}
	
	public Edge(Vertex one, Vertex two, int weight)
	{
		this.one = one;
		this.two = two;
		this.weight = weight;
	}

	public Vertex getOne() {
		return one;
	}

	public void setOne(Vertex one) {
		this.one = one;
	}

	public Vertex getTwo() {
		return two;
	}

	public void setTwo(Vertex two) {
		this.two = two;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
