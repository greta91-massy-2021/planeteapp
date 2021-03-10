package fr.baobab.planeteapp;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import fr.baobab.planeteapp.MainActivity;
import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPlanetesRetrofitCallback implements Callback<List<Planete>> {
    private MainActivity activity;
    public GetPlanetesRetrofitCallback(MainActivity activity){
        this.activity=activity;
    }
    @Override
    public void onResponse(Call<List<Planete>> call, Response<List<Planete>> response) {
        if (response.isSuccessful()){
            Log.d("GetPlanetesRetrofitCallback", response.body().toString());
            List<Planete> Planetes = response.body();
            activity.getAdapter().setPlanetes(Planetes);
            activity.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<List<Planete>> call, Throwable t) {
        Toast.makeText(activity, t.toString(), Toast.LENGTH_LONG).show();
        Log.i("GetPlanetesRetrofitCallback", t.toString());
    }
}
