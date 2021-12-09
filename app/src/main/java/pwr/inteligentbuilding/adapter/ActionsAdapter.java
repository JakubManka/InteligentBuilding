package pwr.inteligentbuilding.adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Calendar;
import java.util.List;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Action;

public class ActionsAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    List<Action> actions;

    public ActionsAdapter(List<Action> actions) {
        this.actions = actions;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_action_item, parent, false);

        return new ActionViewHolder(view).linkAdapter(this);
    }


    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        ArrayAdapter<CharSequence> triggerFunctionAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.triggerFunctions, android.R.layout.simple_spinner_item);
        triggerFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.getTriggerFunction().setAdapter(triggerFunctionAdapter);
        holder.getTriggerFunction().setSelection(triggerFunctionAdapter.getPosition(actions.get(position).getTriggerFunction()));

        ArrayAdapter<CharSequence> actionFunctionAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.actionFunction, android.R.layout.simple_spinner_item);
        actionFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.getActionFunction().setAdapter(actionFunctionAdapter);
        holder.getActionFunction().setSelection(actionFunctionAdapter.getPosition(actions.get(position).getActionFunction()));

        ArrayAdapter<CharSequence> actionParamAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.actionParam, android.R.layout.simple_spinner_item);
        actionParamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.getActionParam().setAdapter(actionParamAdapter);
        holder.getActionParam().setSelection(actionParamAdapter.getPosition(actions.get(position).getActionFunctionParam()));

        holder.getFunctionParam().setText(actions.get(position).getTriggerFunctionParam());


        holder.getTriggerFunction().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                actions.get(position).setTriggerFunction(text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.getActionFunction().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                actions.get(position).setActionFunction(text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.getActionParam().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                actions.get(position).setActionFunctionParam(text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.getFunctionParam().setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(holder.itemView.getContext(), (timePicker, hour, minute) -> {
                Calendar calendar = Calendar.getInstance();

                calendar.set(0,0,0,hour,minute);

                CharSequence time = DateFormat.format("HH:mm", calendar);
                holder.getFunctionParam().setText(time);
                actions.get(position).setTriggerFunctionParam(time.toString());
            }, 12, 0, true);
            String time = actions.get(position).getTriggerFunctionParam();

            if (time.equals("")) {
                dialog.updateTime(0, 0);
            } else {
                String[] s = time.split(":");
                dialog.updateTime(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            }

            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}

