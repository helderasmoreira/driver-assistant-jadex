package application;

import algorithms.PathFinder.Node;

public class Utils {
	
	public static int[][] map;
	
	public static void markAccident(Node n) {
		map[n.y][n.x] = 0;
	}
	

}
