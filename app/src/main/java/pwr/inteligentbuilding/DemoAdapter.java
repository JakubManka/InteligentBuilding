package pwr.inteligentbuilding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pwr.inteligentbuilding.model.Action;

public class DemoAdapter extends RecyclerView.Adapter<DemoVH> {

    List<Action> actions;

    public DemoAdapter(List<Action> actions) {
        this.actions = actions;
    }

    @NonNull
    @Override
    public DemoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edit_action_item, parent, false);

        return new DemoVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoVH holder, int position) {
        ArrayAdapter<CharSequence> triggerFunctionAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.triggerFunctions, android.R.layout.simple_spinner_item);
        triggerFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.triggerFunction.setAdapter(triggerFunctionAdapter);
        holder.triggerFunction.setSelection(triggerFunctionAdapter.getPosition(actions.get(position).getTriggerFunction()));

        ArrayAdapter<CharSequence> actionFunctionAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.actionFunction, android.R.layout.simple_spinner_item);
        actionFunctionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.actionFunction.setAdapter(actionFunctionAdapter);
        holder.actionFunction.setSelection(actionFunctionAdapter.getPosition(actions.get(position).getActionFunction()));


        ArrayAdapter<CharSequence> actionParamAdapter = ArrayAdapter.createFromResource(holder.itemView.getContext(), R.array.actionParam, android.R.layout.simple_spinner_item);
        actionParamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.actionParam.setAdapter(actionParamAdapter);
        holder.actionParam.setSelection(actionParamAdapter.getPosition(actions.get(position).getActionFunctionParam()));

        holder.functionParam.setText(actions.get(position).getTriggerFunctionParam());
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}

class DemoVH extends RecyclerView.ViewHolder {

    Spinner triggerFunction;
    TextView functionParam;
    Spinner actionFunction;
    Spinner actionParam;
    private DemoAdapter adapter;

    public DemoVH(@NonNull View itemView) {
        super(itemView);
        triggerFunction = itemView.findViewById(R.id.triggerFunction);
        functionParam = itemView.findViewById(R.id.functionParam);
        actionFunction = itemView.findViewById(R.id.actionFunction);
        actionParam = itemView.findViewById(R.id.actionParam);
        itemView.findViewById(R.id.delete).setOnClickListener(view -> {
            AlertDialog confirmRemove = new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Usuń")
                    .setMessage("Czy na pewno chcesz usunąć?")
                    .setIcon(R.drawable.ic_delete)
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            adapter.actions.remove(getAdapterPosition());
                            adapter.notifyItemRemoved(getAdapterPosition());
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();

            confirmRemove.show();
        });
    }

    public DemoVH linkAdapter(DemoAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}