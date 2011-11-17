package application;

import jadex.bridge.service.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Accident extends SimplePropertyObject implements ISpaceProcess {

	private Space2D space;

	@Override
	public void execute(IClockService arg0, IEnvironmentSpace space) {
		
	}
	
	class RunnableThread implements Runnable {

		Thread runner;
		public RunnableThread() {
		}
		public RunnableThread(String threadName) {
			runner = new Thread(this, threadName); 
			runner.start(); 
		}
		public void run() {
			while(true)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String x = null, y = null;
				Space2D grid = (Space2D) space;
				try {
					x = br.readLine();
					y = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Map props = new HashMap();
				Boolean alive = new Boolean(Math.random()>0.7);
				props.put("state", "notavoid");
				props.put(Space2D.PROPERTY_POSITION, new Vector2Int(Integer.parseInt(x), Integer.parseInt(y)));
				grid.createSpaceObject("accident", props, null);
			}
			
		}
	}

	@Override
	public void shutdown(IEnvironmentSpace arg0) {
		
	}

	@Override
	public void start(IClockService arg0, IEnvironmentSpace space) {
		this.space = (Space2D) space;
		Thread thread1 = new Thread(new RunnableThread(), "thread1");
		thread1.start();
	}
	
	
}
