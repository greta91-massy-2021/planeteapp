package fr.baobab.planeteapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import fr.baobab.planeteapp.model.Planete;

public class PlaneteAdapter extends ArrayAdapter<Planete> {

    public PlaneteAdapter(@NonNull Context context, @NonNull List<Planete> planetes) {
        super(context, 0, planetes);
    }
}
