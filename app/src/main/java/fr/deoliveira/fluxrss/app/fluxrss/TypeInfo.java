package fr.deoliveira.fluxrss.app.fluxrss;

/**
 * Created by Romain on 17/06/2015.
 */
public enum TypeInfo {

    UNE("� la une"), ECONOMIE("�conomie"), SPORT("sport"), INTERNATIONAL("international"), TECHNOLOGIES("Technologies");

    private String value;

    TypeInfo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    }
