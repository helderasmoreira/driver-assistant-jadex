package application;

import jadex.extension.envsupport.math.Vector2Double;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Accident {
	
	protected static final Random	random	= new Random(23);
	
	/**
	 *  Generate properties for a random disaster.
	 */
	public static Map	generateAccident()
	{
		System.out.println("YA");
		
		Map	ret	= new HashMap();
		ret.put("state", "notavoid");
		
		double mapsize = 20;
		
		int x = (int) (random.nextDouble()*20);
		int y = (int) (random.nextDouble()*20);
		
		if(Utils.map[y][x] == 1)
		{
			ret.put("position", new Vector2Double(x,y));
			System.out.println("YASD");
		}
		else	
			ret.put("position", new Vector2Double(-1,-1));
		
		System.out.println(x+" " +y);
		
		return ret;
	}
}
