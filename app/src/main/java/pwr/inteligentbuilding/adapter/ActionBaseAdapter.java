package pwr.inteligentbuilding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Action;

public class ActionBaseAdapter extends BaseAdapter {

    private final Context context;
    private final List<Action> actions;
    LayoutInflater inflater;

    public ActionBaseAdapter(Context context, List<Action> actions) {
        this.context = context;
        this.actions = actions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return actions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.action_item, null);

        TextView triggerFunction = view.findViewById(R.id.triggerFunctionText);
        TextView functionParam = view.findViewById(R.id.functionParamText);
        TextView actionFunction = view.findViewById(R.id.actionFunctionText);
        TextView actionParam = view.findViewById(R.id.actionParamText);

        triggerFunction.setText(actions.get(i).getTriggerFunction());
        functionParam.setText(actions.get(i).getTriggerFunctionParam());
        actionFunction.setText(actions.get(i).getActionFunction());
        actionParam.setText(actions.get(i).getActionFunctionParam());
        return view;
    }
}
