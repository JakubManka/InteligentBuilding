package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;

import java.io.Serializable;

import pwr.inteligentbuilding.utils.ActionName;
import pwr.inteligentbuilding.utils.opcUtils.SessionElement;

public class Action implements Serializable {

    private String triggerFunction;
    private String triggerFunctionParam;
    private String actionFunction;
    private String actionFunctionParam;
    private final String nodeId;
    private final int namespace;

    public Action(String triggerFunction, String triggerFunctionParam, String actionFunction, String actionFunctionParam, String nodeId, int namespace) {
        this.triggerFunction = triggerFunction;
        this.triggerFunctionParam = triggerFunctionParam;
        this.actionFunction = actionFunction;
        this.actionFunctionParam = actionFunctionParam;
        this.nodeId = nodeId;
        this.namespace = namespace;
    }

    public void updateActions(SessionElement sessionElement) {
        triggerFunction = readAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION).toString();
        triggerFunctionParam = readAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION_PARAM).toString();
        actionFunction = readAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION).toString();
        actionFunctionParam = readAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION_PARAM).toString();
        System.out.println(triggerFunction);
    }

    private Variant readAction(SessionElement sessionElement, String nodeId) {
        Variant result = new Variant("undefined");
        try {
            ReadResponse res = sessionElement.getSession().Read(null, 0d, TimestampsToReturn.Both,
                    new ReadValueId(new NodeId(namespace, nodeId), Attributes.Value, null, null));
            result = res.getResults()[0].getValue();
        } catch (ServiceResultException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getTriggerFunction() {
        return triggerFunction;
    }

    public String getTriggerFunctionParam() {
        return triggerFunctionParam;
    }

    public String getActionFunction() {
        return actionFunction;
    }

    public String getActionFunctionParam() {
        return actionFunctionParam;
    }

    public void setTriggerFunction(String triggerFunction) {
        this.triggerFunction = triggerFunction;
    }
}
