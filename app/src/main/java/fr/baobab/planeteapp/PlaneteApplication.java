package fr.baobab.planeteapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaneteApplication extends Application {
    private PlaneteService service;
    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cf12cab952f5.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PlaneteService.class);
    }

    public PlaneteService getService() {
        return service;
    }

    public void setService(PlaneteService service) {
        this.service = service;
    }
}
