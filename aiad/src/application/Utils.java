package application;

import algorithms.PathFinder.Node;
import visual.DriverLog;

public class Utils {
	
	public static int[][] map;
        public static DriverLog dialog = null;
	
	public static void markAccident(Node n) {
		map[n.y][n.x] = 0;
	}
	

}
