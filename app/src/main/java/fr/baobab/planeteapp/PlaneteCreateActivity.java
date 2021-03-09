package fr.baobab.planeteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class PlaneteCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planete_create);
        //récupérer le bouton
        final View button = findViewById(R.id.planete_create_submit_btn);
        //ajouter un écouteur d'évenèment sur ce bouton
        button.setOnClickListener((view)->{
            //récupérer l'editText nom
            final EditText etNom = (EditText)findViewById(R.id.planete_name);
            //récupérer l'editText distance
            final EditText etDistance = (EditText)findViewById(R.id.planete_distance);
            String nomPlanete = etNom.getText().toString();
            String tempDistance = etDistance.getText().toString();
            int distancePlanete = tempDistance.isEmpty() ?
                                            0 :
                                            Integer.parseInt(etDistance.getText().toString());
            //validation des données
            boolean error = false;
            nomPlanete = nomPlanete.trim();
            if(nomPlanete.isEmpty()){
                etNom.setBackgroundColor(Color.RED);
                etNom.setTextColor(Color.WHITE);
                error = true;
            }
            //validation à faire en utilisant RegEXP
            if(distancePlanete < 0){
                etDistance.setBackgroundColor(Color.RED);
                etDistance.setTextColor(Color.WHITE);
                error = true;
            }
            if(error) return;
            //récupérer l'objet Intent pour envoyer la réponse
            Intent intent = getIntent();
            intent.putExtra("nomPlanete", nomPlanete);
            intent.putExtra("distancePlanete", distancePlanete);
            //envoyer la réponse vers MainActivity
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
