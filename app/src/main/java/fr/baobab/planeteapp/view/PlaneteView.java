package fr.baobab.planeteapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
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
        if(planete.getImageBase64() != null){
            byte[] decode = Base64.decode(planete.getImageBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            this.ivImage.setImageBitmap(bitmap);
        }
        //this.ivImage.setImageResource(planete.getIdImage());
    }
}
