package com.path.finder.gui;

import java.awt.FlowLayout;
import java.awt.Frame;

public class PathInterface extends Frame
{
	public PathInterface()
	{
		setLayout(new FlowLayout());
		setTitle("Robot Gui");
		setSize(800, 400);
		setVisible(true);
		
		
	}
	
	public static void main(String[] args)
	{
		PathInterface pathInterface = new PathInterface();	
	}
}
