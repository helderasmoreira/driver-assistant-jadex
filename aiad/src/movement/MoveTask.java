package movement;

import jadex.bdi.runtime.IBDIExternalAccess;
import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.clock.IClockService;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector1Double;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.xml.annotation.XMLClassname;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import algorithms.PathFinder.Node;

/**
 *  Move an object towards a destination.
 */
public class MoveTask extends AbstractTask {
    //-------- constants --------

    /** The destination property. */
    public static final String PROPERTY_TYPENAME = "move";
    /** The destination property. */
    public static final String PROPERTY_DESTINATION = "destination";
    /** The scope property. */
    public static final String PROPERTY_SCOPE = "scope";
    /** The speed property of the moving object (units per second). */
    public static final String PROPERTY_SPEED = "speed";
    /** The vision property of the moving object (radius in units). */
    public static final String PROPERTY_VISION = "vision";
    public static List<Node> path;

    //-------- IObjectTask methods --------
    /**
     *  Executes the task.
     *  Handles exceptions. Subclasses should implement doExecute() instead.
     *  @param space	The environment in which the task is executing.
     *  @param obj	The object that is executing the task.
     *  @param progress	The time that has passed according to the environment executor.
     */
    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock) {
        path = (List<Node>) getProperty("path");

        if (path.size() == 0) {
            setFinished(space, obj, true);
        }

        final IVector2 destination = (IVector2) getProperty(PROPERTY_DESTINATION);

        final IVector2 interdestination = new Vector2Double(path.get(0).x, path.get(0).y);

        final IBDIExternalAccess agent = (IBDIExternalAccess) getProperty(PROPERTY_SCOPE);

        double speed = ((Number) obj.getProperty(PROPERTY_SPEED)).doubleValue();
        double maxdist = progress * speed * 0.001;

        IVector2 loc = (IVector2) obj.getProperty(Space2D.PROPERTY_POSITION);

        IVector2 newloc = ((Space2D) space).getDistance(loc, interdestination).getAsDouble() <= maxdist
                ? interdestination : interdestination.copy().subtract(loc).normalize().multiply(maxdist).add(loc);

        ((Space2D) space).setPosition(obj.getId(), newloc);

        if (newloc == interdestination) {
            path.remove(0);
        }

        // Process vision at new location.
        double vision = ((Number) obj.getProperty(PROPERTY_VISION)).doubleValue();
        final Set objects = ((Space2D) space).getNearObjects((IVector2) obj.getProperty(Space2D.PROPERTY_POSITION), new Vector1Double(vision));
        if (objects != null) {

            agent.scheduleStep(new IComponentStep<Void>() {

                @XMLClassname("add")
                public IFuture<Void> execute(IInternalAccess ia) {
                    IBDIInternalAccess bia = (IBDIInternalAccess) ia;

                    for (Iterator it = objects.iterator(); it.hasNext();) {
                        final ISpaceObject so = (ISpaceObject) it.next();
                        if (so.getType().equals("accident")) {
                            if (bia.getBeliefbase().getBelief("acidente").getFact() != so) {
                                so.setProperty("state", "notavoid");
                                bia.getBeliefbase().getBelief("acidente").setFact(so);
                            }

                        }
                    }
                    return IFuture.DONE;
                }
            });

        }

        if (newloc == destination) {
            setFinished(space, obj, true);
        }
    }
}
