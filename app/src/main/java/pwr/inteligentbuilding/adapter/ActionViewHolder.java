package pwr.inteligentbuilding.adapter;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pwr.inteligentbuilding.R;

class ActionViewHolder extends RecyclerView.ViewHolder {

    private final Spinner triggerFunction;
    private final TextView functionParam;
    private final Spinner actionFunction;
    private final Spinner actionParam;
    private ActionsAdapter adapter;

    public ActionViewHolder(@NonNull View itemView) {
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
                    .setPositiveButton("Tak", (dialog, whichButton) -> {
                        adapter.actions.remove(getAdapterPosition());
                        adapter.notifyItemRemoved(getAdapterPosition());
                        dialog.dismiss();
                    })
                    .setNegativeButton("Nie", (dialog, which) -> dialog.dismiss())
                    .create();

            confirmRemove.show();
        });
    }

    public Spinner getTriggerFunction() {
        return triggerFunction;
    }

    public TextView getFunctionParam() {
        return functionParam;
    }

    public Spinner getActionFunction() {
        return actionFunction;
    }

    public Spinner getActionParam() {
        return actionParam;
    }

    public ActionViewHolder linkAdapter(ActionsAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}
