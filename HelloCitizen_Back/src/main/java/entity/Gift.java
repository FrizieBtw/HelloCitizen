package entity;

import jakarta.persistence.*;

@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer ageMin;

    @Column(nullable = false)
    private Integer ageMax;

    @Column(unique = true, nullable = false)
    private String codeBarres;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String libelle;

    public Gift(Long id) {
        this.id = id;
    }

    public Gift() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public String getCodeBarres() {
        return codeBarres;
    }

    public void setCodeBarres(String codeBarres) {
        this.codeBarres = codeBarres;
    }

    public Double getPrice() {
        return price;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
