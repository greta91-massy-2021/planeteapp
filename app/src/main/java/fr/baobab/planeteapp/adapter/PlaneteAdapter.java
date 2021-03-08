package fr.baobab.planeteapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.baobab.planeteapp.model.Planete;
import fr.baobab.planeteapp.view.PlaneteView;

public class PlaneteAdapter extends RecyclerView.Adapter<PlaneteView> {
    private List<Planete> list;
    public PlaneteAdapter(@NonNull Context context, @NonNull List<Planete> planetes) {
        super(context, 0, planetes);
        list = planetes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlaneteView planeteView = (PlaneteView)convertView;
        if(null == planeteView){
            planeteView = PlaneteView.create(parent);
        }
        planeteView.setItem(this.list.get(position));
        return planeteView;
    }
}
