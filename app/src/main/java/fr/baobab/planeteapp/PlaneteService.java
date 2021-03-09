package fr.baobab.planeteapp;

import java.util.List;

import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaneteService {
    @GET("planetes")
    Call<List<Planete>> getPlanetes();

    @GET("planetes/{id}")
    Call<Planete> getPlaneteById(@Path("id") long id);

    @POST("planetes")
    Call<Planete> createPlanete(@Body Planete planete);



}
