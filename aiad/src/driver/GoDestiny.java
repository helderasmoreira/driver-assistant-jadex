package driver;

import java.util.List;

import algorithms.PathFinder;
import algorithms.PathFinder.Node;
import application.Utils;
import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;

public class GoDestiny  extends Plan {

		protected boolean equal(Node node, Node end){
			return (node.x == end.x) && (node.y == end.y);
		}
	
		@Override
		public void body() {
			
			// Inicializacao de variaveis
			IGoal go_target = createGoal("move.move_dest");
			ISpaceObject	myself	= (ISpaceObject)getBeliefbase().getBelief("myself").getFact();

			Space2D	space	= (Space2D)getBeliefbase().getBelief("environment").getFact();
			
			boolean turistica = (Boolean) getBeliefbase().getBelief("turistica").getFact();
			
			if(turistica)
			{
				while(true)
				{
					
					// Procura o point of interest mais proximo e ainda nao visitado
					IVector2	mypos	= (IVector2)myself.getProperty(Space2D.PROPERTY_POSITION);
					ISpaceObject	target	= null;
					int size = 0;
					ISpaceObject[]	poi	= space.getSpaceObjectsByType("pointofinterest");
					
					for(int i=0; i<poi.length; i++)
					{
						
						if((poi[i].getProperty("state")).equals("notvisited"))
						{
							
							IVector2	newpos	= (IVector2)poi[i].getProperty(Space2D.PROPERTY_POSITION);
							PathFinder pf = new PathFinder(Utils.map);
							
							System.out.println(((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger() + " " +((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger() );
							System.out.println(newpos.getXAsInteger() + " " + newpos.getYAsInteger());
							
							
							List<Node> nodes = pf.compute(new PathFinder.Node(((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
									((IVector2)myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()), 
									new PathFinder.Node(newpos.getXAsInteger(), 
											newpos.getYAsInteger()));

							System.out.println(nodes);
							if(target == null)
							{
								target = poi[i];
								size = nodes.size();
							}
							else if(nodes.size() < size )
							{
								target	= poi[i];
								size = nodes.size();
							}		
						}
					}
					
					// Verifica se existe point of interests por visitar e vai ate la
					if(target!=null)
					{
						IEnvironmentSpace env = (IEnvironmentSpace)getBeliefbase().getBelief("move.environment").getFact();
						ISpaceObject	fd	= env.getSpaceObjectsByType("finaldestination")[0];
	
						// Move-se para o POI
						go_target = createGoal("move.move_dest");
						go_target.getParameter("destination").setValue(target.getProperty(Space2D.PROPERTY_POSITION));
						dispatchSubgoalAndWait(go_target);
						target.setProperty("state", "visited");
						
						
						
					}
					// Se nao existir nada a visitar, desloca-se para a posicao final
					else
					{
						IEnvironmentSpace env = (IEnvironmentSpace)getBeliefbase().getBelief("move.environment").getFact();
						ISpaceObject	fd	= env.getSpaceObjectsByType("finaldestination")[0];
	
						// Move-se para a posicao final
						go_target = createGoal("move.move_dest");
						go_target.getParameter("destination").setValue(fd.getProperty(Space2D.PROPERTY_POSITION));
						
						dispatchSubgoalAndWait(go_target);
						return;
					}
					
					
				}
			}
			else
			{
				IEnvironmentSpace env = (IEnvironmentSpace)getBeliefbase().getBelief("move.environment").getFact();
				ISpaceObject	fd	= env.getSpaceObjectsByType("finaldestination")[0];

				// Move-se para a posicao final
				go_target = createGoal("move.move_dest");
				go_target.getParameter("destination").setValue(fd.getProperty(Space2D.PROPERTY_POSITION));
				
				dispatchSubgoalAndWait(go_target);
				return;
			}
}
		}
