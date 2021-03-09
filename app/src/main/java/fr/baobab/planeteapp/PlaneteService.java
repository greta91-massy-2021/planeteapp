package fr.baobab.planeteapp;

import java.util.List;

import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaneteService {
    @GET("planetes")
    Call<List<Planete>> getPlanetes();

}
