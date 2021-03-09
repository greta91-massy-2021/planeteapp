package fr.baobab.planeteapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import fr.baobab.planeteapp.R;
import fr.baobab.planeteapp.model.Planete;

public class PlaneteView extends RecyclerView.ViewHolder {
    private TextView tvName;
    private TextView tvDistance;
    private ImageView ivImage;
    public PlaneteView(View view){
        super(view);
        findViews(view);
    }

    /*public static PlaneteView create(ViewGroup parent){
        //expanser la vue
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PlaneteView planeteView = (PlaneteView) inflater.inflate(R.layout.item, parent, false);
        //initialiser les variables d'instance de cet objet palneteView
        planeteView.findViews();
        return planeteView;
    }*/

    private void findViews(View view) {
        this.tvName = view.findViewById(R.id.nomPlanete);
        this.tvDistance = view.findViewById(R.id.distancePlanete);
        this.ivImage = view.findViewById(R.id.imagePlanete);
    }
    public void setItem(final Planete planete) {
        this.tvName.setText(planete.getNom());
        this.tvDistance.setText(planete.getDistance()+"Gm");
        this.ivImage.setImageResource(planete.getIdImage());
    }
}
