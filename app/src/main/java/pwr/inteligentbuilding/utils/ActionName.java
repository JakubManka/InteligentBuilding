package pwr.inteligentbuilding.utils;

import java.util.ArrayList;
import java.util.List;

public class ActionName {
    public static List<String> getActions(){
        List<String> actions = new ArrayList<>();
        actions.add(".mActions[0]");
        actions.add(".mActions[1]");
        actions.add(".mActions[2]");
        actions.add(".mActions[3]");
        actions.add(".mActions[4]");
        actions.add(".mActions[5]");
        actions.add(".mActions[6]");
        actions.add(".mActions[7]");
        actions.add(".mActions[8]");
        actions.add(".mActions[9]");
        return actions;
    }

//    public static final String ACTIONS_0 = ".mActions[0]";
//    public static final String ACTIONS_1 = ".mActions[1]";
//    public static final String ACTIONS_2 = ".mActions[2]";
//    public static final String ACTIONS_3 = ".mActions[3]";
//    public static final String ACTIONS_4 = ".mActions[4]";
//    public static final String ACTIONS_5 = ".mActions[5]";
//    public static final String ACTIONS_6 = ".mActions[6]";
//    public static final String ACTIONS_7 = ".mActions[7]";
//    public static final String ACTIONS_8 = ".mActions[8]";
//    public static final String ACTIONS_9 = ".mActions[9]";
    public static final String DIMENSIONS = ".mActions.Dimensions";
    public static final String INDEX_MIN = ".mActions.IndexMin";
    public static final String INDEX_MAX = ".mActions.IndexMax";

    public static final String TRIGGER_FUNCTION = ".mTriggerFunction";
    public static final String TRIGGER_FUNCTION_PARAM = ".mTriggerFunctionParam";
    public static final String ACTION_FUNCTION = ".mActionFunction";
    public static final String ACTION_FUNCTION_PARAM = ".mActionFunctionParam";

}
