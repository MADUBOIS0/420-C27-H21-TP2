import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire {
    private String nom;
    private int numSerie;
    private String categorie;
    private double prix;
    private LocalDate dateAchat;
    private String description;
    private LinkedHashMap<LocalDate,String> entretiens;

    public Inventaire(String nom, int numSerie, String categorie, double prix, LocalDate dateAchat, String description){
        this.nom = nom;
        this.numSerie = numSerie;
        this.categorie = categorie;
        this.prix = prix;
        this.dateAchat = dateAchat;
        this.description = description;
        this.entretiens = new LinkedHashMap<LocalDate,String>();
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public int getNumSerie(){
        return this.numSerie;
    }

    public void setNumSerie(int numSerie){
        this.numSerie = numSerie;
    }

    public String getCategorie(){
        return this.categorie;
    }

    public void setCategorie(String categorie){
        this.categorie= categorie;
    }

    public double getPrix(){
        return this.prix;
    }

    public void setPrix(double prix){
        this.prix = prix;
    }

    public LocalDate getDateAchat(){
        return this.dateAchat;
    }

    public void setLocalDate(LocalDate date){
        this.dateAchat = date;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void addEntretien(LocalDate dateEntretien, String description){
        this.entretiens.put(dateEntretien, description);
    }

    public void removeEntretien(LocalDate dateEntretien){
        this.entretiens.remove(dateEntretien);
    }
}
