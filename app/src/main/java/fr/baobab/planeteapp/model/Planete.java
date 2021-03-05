package fr.baobab.planeteapp.model;

public class Planete {
    private String nom;
    private int distance;

    public Planete(String nom, int distance) {
        this.nom = nom;
        this.distance = distance;
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

    @Override
    public String toString() {
        return getNom();
    }
}
