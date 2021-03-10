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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaneteCreateActivity extends AppCompatActivity {
    private Planete planete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planete_create);
        planete = new Planete();
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

            planete.setNom(nomPlanete);
            planete.setDistance(distancePlanete);

            //envoyer la requete post vers le serveur /planetes
            //créer l'objet de type Retrofit
            /*Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://7d079bddaee2.ngrok.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            PlaneteService service = retrofit.create(PlaneteService.class);*/
            PlaneteApplication applicationContext =
                    (PlaneteApplication)getApplicationContext();
            PlaneteService service = applicationContext.getService();
            //PlaneteService service =
            //              ((PlaneteApplication) getApplicationContext()).getService();
            Call<Planete> call = service.createPlanete(this.planete);
            call.enqueue(new Callback<Planete>() {
                @Override
                public void onResponse(Call<Planete> call, Response<Planete> response) {
                    if(response.isSuccessful()){
                        Planete planete = response.body();
                        Intent i = getIntent();
                        i.putExtra("planeteId", planete.getId());
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Planete> call, Throwable t) {
                    Log.i("planeteCreateActivity", t.toString());
                }
            });
            /*//récupérer l'objet Intent pour envoyer la réponse
            Intent intent = getIntent();
            intent.putExtra("nomPlanete", nomPlanete);
            intent.putExtra("distancePlanete", distancePlanete);
            //envoyer la réponse vers MainActivity
            setResult(RESULT_OK, intent);
            finish();*/
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
                //convertir bitmap en byte tableau de byte
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();
                //convertir tableau de byte en base64 string
                String imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                planete.setImageBase64(imageBase64);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
