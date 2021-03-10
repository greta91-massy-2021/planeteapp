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
            Intent i;
            switch (method) {
                case "get":
                    //getPlanete
                    adapter = ((MainActivity) activity).getAdapter();
                    adapter.addPlanete(planete);
                    break;
                case "post":
                    //createPlanete
                case "put":
                    //editPlanete
                    i = activity.getIntent();
                    i.putExtra("planeteId", planete.getId());
                    activity.setResult(activity.RESULT_OK, i);
                    //activity.finish();
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
