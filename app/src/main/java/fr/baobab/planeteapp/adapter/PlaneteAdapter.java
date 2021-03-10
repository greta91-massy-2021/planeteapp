package fr.baobab.planeteapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;
import java.util.List;

import fr.baobab.planeteapp.R;
import fr.baobab.planeteapp.model.Planete;
import fr.baobab.planeteapp.view.PlaneteView;

public class PlaneteAdapter extends RecyclerView.Adapter<PlaneteView> {
    private List<Planete> list;
    private int clickedPosition = RecyclerView.NO_POSITION;
    private View.OnCreateContextMenuListener menuListener;

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
    public void onBindViewHolder(@NonNull PlaneteView holder, final int position) {
        holder.setItem(list.get(position));
        //ajouter un écouteur d'évènement de type click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("planeteAdapter ", position+"");
                setClickedPosition(position);
            }
        });
        //holder.itemView.setOnClickListener((view)->setClickedPosition(position));//expression Lambda
        holder.itemView.setOnLongClickListener((view)->{
            Log.i("planeteAdapter ", position+"");
            setClickedPosition(position);
            return false;
        });
        /*int color = Color.TRANSPARENT;
        if (getClickedPosition() == position){
            color = Color.LTGRAY;
        }
        holder.itemView.setBackgroundColor(color);*/
        holder.itemView.setBackgroundColor(getClickedPosition() == position ? Color.LTGRAY : Color.TRANSPARENT);
        holder.itemView.setOnCreateContextMenuListener(menuListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getClickedPosition() {
        return clickedPosition;
    }

    public void setClickedPosition(int clickedPosition) {
        notifyItemChanged(this.clickedPosition);
        this.clickedPosition = clickedPosition;
        notifyItemChanged(clickedPosition);
    }

    public View.OnCreateContextMenuListener getMenuListener() {
        return menuListener;
    }

    public void setMenuListener(View.OnCreateContextMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public void addPlanete(Planete planete) {
        list.add(planete);
        notifyItemInserted(list.size()-1);
    }

    public void setPlanetes(List<Planete> planetes) {
        list = planetes;
    }

    public Planete getItem(int position) {
        return list.get(position);
    }

    public void deletePlanete(long id) {
        for(Iterator<Planete> iterator = list.iterator(); iterator.hasNext();){
            Planete p = iterator.next();
            if(p.getId() == id){
                int position = list.indexOf(p);
                list.remove(p);
                notifyItemRemoved(position);
                break;
            }
        }
    }

    public void editPlanete(Planete planete) {
        for(Iterator<Planete> iterator = list.iterator(); iterator.hasNext();){
            Planete p = iterator.next();
            if(p.getId() == planete.getId()){
                int position = list.indexOf(p);
                list.set(position, planete);
                notifyItemChanged(position);
                break;
            }
        }
    }
}
