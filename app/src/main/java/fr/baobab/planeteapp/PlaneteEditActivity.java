package fr.baobab.planeteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaneteEditActivity extends AppCompatActivity {
    private Planete planete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planete_create);
        Intent intent = getIntent();
        final long id = intent.getLongExtra("id", 0);
        //envoyer une requete get pour récupérer la planete par Id
        PlaneteApplication applicationContext =
                (PlaneteApplication) getApplicationContext();
        PlaneteService service = applicationContext.getService();
        Call<Planete> planeteById = service.getPlaneteById(id);
        planeteById.enqueue(new Callback<Planete>() {
            @Override
            public void onResponse(Call<Planete> call, Response<Planete> response) {
                if(response.isSuccessful()){
                    planete = response.body();
                    EditText etNom = (EditText)findViewById(R.id.planete_name);
                    EditText etDistance = (EditText)findViewById(R.id.planete_distance);
                    ImageView ivImage = (ImageView)findViewById(R.id.planete_image);
                    etNom.setText(planete.getNom());
                    etDistance.setText(planete.getDistance() +"");
                    if(planete.getImageBase64() != null){
                        byte[] decode = Base64.decode(planete.getImageBase64(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                        ivImage.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<Planete> call, Throwable t) {

            }
        });
        /*String nom = intent.getStringExtra("nom");
        int distance = intent.getIntExtra("distance", 0);
        String imagebase64 = intent.getStringExtra("imagebase64");
        planete = new Planete(nom, distance, imagebase64);
        planete.setId(id);
        EditText etNom = (EditText)findViewById(R.id.planete_name);
        EditText etDistance = (EditText)findViewById(R.id.planete_distance);
        ImageView ivImage = (ImageView)findViewById(R.id.planete_image);
        etNom.setText(nom);
        etDistance.setText(distance +"");
        if(imagebase64 != null){
            byte[] decode = Base64.decode(imagebase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            ivImage.setImageBitmap(bitmap);
        }*/
        Button submitBtn = (Button)findViewById(R.id.planete_create_submit_btn);
        submitBtn.setText("Modifier Planète");
    }
}