package driver;

import application.Utils;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

public class RecalculatePath extends Plan {

    @Override
    public void body() {

        getBeliefbase().getBelief("newradiomsg").setFact(false);

        Utils.dialog.changeText("Nova mensagem de rádio!");
        Utils.dialog.changeText("Recalculando caminho...");
        IGoal go_target = createGoal("goDestiny");
        dispatchTopLevelGoal(go_target);

    }
}
