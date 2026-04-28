package model;

public class Alumne {
    private int id;
    private String nom;
    private String email;
    private Curs curs;

    public Alumne() {
    }

    public Alumne(int id, String nom, String email, Curs curs) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.curs = curs;
    }

    public Alumne(String nom, String email, Curs curs) {
        this.nom = nom;
        this.email = email;
        this.curs = curs;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Curs getCurs() { return curs; }
    public void setCurs(Curs curs) { this.curs = curs; }

    @Override
    public String toString() {
        return "Alumne{id=" + id + ", nom='" + nom + "', email='" + email + "', curs=" + (curs == null ? "null" : curs.getNom()) + "}";
    }
}
