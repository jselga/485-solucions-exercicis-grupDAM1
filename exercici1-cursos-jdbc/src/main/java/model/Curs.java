package model;

public class Curs {
    private int id;
    private String nom;
    private String nivell;

    public Curs() {
    }

    public Curs(int id, String nom, String nivell) {
        this.id = id;
        this.nom = nom;
        this.nivell = nivell;
    }

    public Curs(String nom, String nivell) {
        this.nom = nom;
        this.nivell = nivell;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNivell() { return nivell; }
    public void setNivell(String nivell) { this.nivell = nivell; }

    @Override
    public String toString() {
        return "Curs{id=" + id + ", nom='" + nom + "', nivell='" + nivell + "'}";
    }
}
