package driver;

import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

public class RecalculatePath extends Plan {

	@Override
	public void body() {
		
		getBeliefbase().getBelief("newradiomsg").setFact(false);
		
		IGoal go_target = createGoal("goDestiny");
		dispatchTopLevelGoal(go_target);
		
	}

}
