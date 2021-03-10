package fr.baobab.planeteapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import fr.baobab.planeteapp.MainActivity;
import fr.baobab.planeteapp.R;
import fr.baobab.planeteapp.adapter.PlaneteAdapter;
import fr.baobab.planeteapp.model.Planete;
import fr.baobab.planeteapp.adapter.PlaneteAdapter;
import fr.baobab.planeteapp.model.Planete;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherRetrofitCallback implements Callback<Planete> {
    private Activity activity;

    public OtherRetrofitCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Planete> call, Response<Planete> response) {
        String method = call.request().method().toLowerCase();
        if (response.isSuccessful()) {
            Log.d(activity.getLocalClassName(), response.body().toString());
            Planete planete = response.body();
            PlaneteAdapter adapter;
            switch (method) {
                case "get":
                    //getPlanete
                    Log.d("responseGET", response.body().toString());
                    adapter = ((MainActivity) activity).getAdapter();
                    adapter.addPlanete(planete);
                case "post":
                    //createPlanete
                    Intent i = activity.getIntent();
                    i.putExtra("PlaneteId", planete.getId());
                    activity.setResult(activity.RESULT_OK, i);
                    activity.finish();
                case "put":
                    //editPlanete
                    /*((EditText) activity.findViewById(R.id.name)).setText(planete.getName());
                    ((EditText) activity.findViewById(R.id.description)).setText(planete.getDescription());
                    ((EditText) activity.findViewById(R.id.price)).setText("" + planete.getPrice());
                    String imageBase64 = planete.getImageBase64();
                    if (imageBase64 != null) {
                        byte[] bytes = Base64.decode(imageBase64, Base64.DEFAULT);
                        ((ImageView) activity.findViewById(R.id.imageView)).setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    }*/
                case "delete":
                    //deletePlanete
                    adapter = ((MainActivity) activity).getAdapter();
                    adapter.deletePlanete(planete.getId());
                default:
                    //do nothing
            }
        }

    }

    @Override
    public void onFailure(Call<Planete> call, Throwable t) {
        Toast.makeText(activity, t.toString(), Toast.LENGTH_LONG).show();
        Log.i(activity.getLocalClassName(), t.toString());
    }
}
