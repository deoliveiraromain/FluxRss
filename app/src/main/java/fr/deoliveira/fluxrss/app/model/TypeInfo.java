package fr.deoliveira.fluxrss.app.model;

/**
 * Created by Romain on 17/06/2015.
 */
public enum TypeInfo {

    UNE("A la une"), ECONOMIE("Economie"), SPORT("Sport"), INTERNATIONAL("International"), TECHNOLOGIES("Technologies"),
    CULTURE("Culture"),SANTE("Santé");

    private String value;

    TypeInfo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    }
