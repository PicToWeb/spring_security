package fr.diginamic.spring_security.dto;

import java.util.Objects;

public class DepartementApiDTO {

    private String nom;
    private String code;
    private String codeRegion;

    public DepartementApiDTO(String nom,String code,String codeRegion) {
        this.nom = nom;
        this.code = code;
        this.codeRegion = codeRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartementApiDTO that = (DepartementApiDTO) o;
        return Objects.equals(nom, that.nom) && Objects.equals(code, that.code) && Objects.equals(codeRegion, that.codeRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, code, codeRegion);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }
}
