package fr.baobab.planeteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.baobab.planeteapp.R;
import fr.baobab.planeteapp.model.Planete;
import fr.baobab.planeteapp.view.PlaneteView;

public class PlaneteAdapter extends RecyclerView.Adapter<PlaneteView> {
    private List<Planete> list;
    public PlaneteAdapter(@NonNull List<Planete> planetes) {
        super();
        list = planetes;
    }

    @NonNull
    @Override
    public PlaneteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.item, parent, false);
        return new PlaneteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaneteView holder, int position) {
        holder.setItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
