package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import algorithms.PathFinder;

import jadex.bridge.service.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector1Double;
import jadex.extension.envsupport.math.Vector2Int;

public class BuildMap extends SimplePropertyObject implements ISpaceProcess {

	@Override
	public Object getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getPropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasProperty(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setProperty(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(IClockService arg0, IEnvironmentSpace arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown(IEnvironmentSpace arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(IClockService arg0, IEnvironmentSpace space) {
		// TODO Auto-generated method stub
		// Initialize the field.
		Space2D grid = (Space2D)space;
		int sizex = grid.getAreaSize().getXAsInteger();
		int sizey = grid.getAreaSize().getYAsInteger();
		
		Utils.map = new int[][]{
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, 
				{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, 
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
		};
		
		
		for (int i = 0; i < sizex; i++) {
            for (int j = i+1; j < sizey; j++) {
                int temp = Utils.map[i][j];
                Utils.map[i][j] = Utils.map[j][i];
                Utils.map[j][i] = temp;
            }
        }
		
		for(int x=0; x< Utils.map.length; x++)
		{
			for(int y=0; y< Utils.map[x].length; y++)
			{
				if(Utils.map[y][x] == 1){
					Map props = new HashMap();			
					props.put("road", true);
					props.put(Space2D.PROPERTY_POSITION, new Vector2Int(y, x));
					grid.createSpaceObject("cell", props, null);
				}
			}
		}
		
		for (int i = 0; i < sizex; i++) {
            for (int j = i+1; j < sizey; j++) {
                int temp = Utils.map[i][j];
                Utils.map[i][j] = Utils.map[j][i];
                Utils.map[j][i] = temp;
            }
        }
		
		/*

		for(int i = 0; i < estradas.length; i++){
			for(int j = 0; j < estradas[0].length; j++)
					System.out.print(estradas[i][j] + " ");
			System.out.println();
		}*/
		
		
	}

}
