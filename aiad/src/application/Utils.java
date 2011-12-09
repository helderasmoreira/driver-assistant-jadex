package application;

import algorithms.PathFinder.Node;
import jadex.extension.envsupport.environment.ISpaceObject;
import visual.DriverLog;
import visual.WorldOptions;

public class Utils {

    public static int[][] map;
    public static int[][] map2;
    public static DriverLog dialog = null;
    public static WorldOptions worldOptionsDialog = null;
    public static boolean start = false;
    public static boolean radio = false;
    public static boolean firstRadio = true;
    public static ISpaceObject acidente = null;

    public static void markAccident(Node n) {
        map[n.y][n.x] = 0;
    }
}
