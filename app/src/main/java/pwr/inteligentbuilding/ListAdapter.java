package pwr.inteligentbuilding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import pwr.inteligentbuilding.model.Action;

public class ListAdapter extends ArrayAdapter<Action> {

    public ListAdapter(Context context, ArrayList<Action> actions){
        super(context, R.layout.edit_action_item, actions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Action action = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.action_item, parent, false);
        }

        TextView triggerFunction = convertView.findViewById(R.id.triggerFunctionText);
        TextView functionParam = convertView.findViewById(R.id.functionParamText);
        TextView actionFunction = convertView.findViewById(R.id.actionFunctionText);
        TextView actionParam = convertView.findViewById(R.id.actionParamText);

        triggerFunction.setText(action.getTriggerFunction());
        functionParam.setText(action.getTriggerFunctionParam());
        actionFunction.setText(action.getActionFunction());
        actionParam.setText(action.getActionFunctionParam());

        return super.getView(position, convertView, parent);
    }
}
