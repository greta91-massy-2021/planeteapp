package fr.baobab.planeteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class PlaneteCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planete_create);
        //
        final View uploadBtn = findViewById(R.id.planete_upload_btn);
        uploadBtn.setOnClickListener((view)->{
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            Intent fileChooser = Intent.createChooser(i, "Sélectionner une image");
            startActivityForResult(fileChooser, 1);
        });
        //récupérer le bouton "créer planete"
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && null != data){
            Uri uri = data.getData();
            try {
                ParcelFileDescriptor r = getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = r.getFileDescriptor();
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                ImageView ivImage = (ImageView)findViewById(R.id.planete_image);
                ivImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
