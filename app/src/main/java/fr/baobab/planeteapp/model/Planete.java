package fr.baobab.planeteapp.model;

import com.google.gson.annotations.SerializedName;

public class Planete {
    @SerializedName("name")
    private String nom;
    private int distance;
    private String imageBase64;
    public Planete(){}
    public Planete(String nom, int distance, String imageBase64) {
        this.nom = nom;
        this.distance = distance;
        this.imageBase64 = imageBase64;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public String toString() {
        return getNom();
    }


}
