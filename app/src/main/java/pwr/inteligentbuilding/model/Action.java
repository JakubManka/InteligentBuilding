package pwr.inteligentbuilding.model;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.core.WriteResponse;

import java.io.Serializable;

import pwr.inteligentbuilding.utils.ActionName;
import pwr.inteligentbuilding.utils.opcUtils.ConnectionThread.ThreadWrite;
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

    public Action(String nodeId) {
        this.triggerFunction = "";
        this.triggerFunctionParam = "";
        this.actionFunction = "";
        this.actionFunctionParam = "";
        this.nodeId = nodeId;
        this.namespace = 4;
    }

    public void readActions(SessionElement sessionElement) {
        triggerFunction = readAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION).toString();
        triggerFunctionParam = readAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION_PARAM).toString();
        actionFunction = readAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION).toString();
        actionFunctionParam = readAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION_PARAM).toString();
    }

    public void writeActions(SessionElement sessionElement) {
        writeAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION, new Variant(this.triggerFunction));
        writeAction(sessionElement, nodeId + ActionName.TRIGGER_FUNCTION_PARAM, new Variant(this.triggerFunctionParam));
        writeAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION, new Variant(this.actionFunction));
        writeAction(sessionElement, nodeId + ActionName.ACTION_FUNCTION_PARAM, new Variant(this.actionFunctionParam));
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

    private void writeAction(SessionElement sessionElement, String nodeId, Variant value) {
        ThreadWrite t;

        t = new ThreadWrite(sessionElement.getSession(), namespace, nodeId, Attributes.Value, value);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                WriteResponse res = (WriteResponse) msg.obj;
                String response = res.getResults()[0].getDescription();
                System.out.println("values sent " + response);
            }
        };
        t.start(handler);
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

    public void setTriggerFunctionParam(String triggerFunctionParam) {
        this.triggerFunctionParam = triggerFunctionParam;
    }

    public void setActionFunction(String actionFunction) {
        this.actionFunction = actionFunction;
    }

    public void setActionFunctionParam(String actionFunctionParam) {
        this.actionFunctionParam = actionFunctionParam;
    }

    @Override
    public String toString() {
        return "Action{" +
                "triggerFunction='" + triggerFunction + '\'' +
                ", triggerFunctionParam='" + triggerFunctionParam + '\'' +
                ", actionFunction='" + actionFunction + '\'' +
                ", actionFunctionParam='" + actionFunctionParam + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", namespace=" + namespace +
                '}';
    }
}
