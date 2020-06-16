package Entities;

import java.io.Serializable;

public class DTOForTable implements Serializable {

    private String nume, date, denumire, locuri_libere, locuri_ocupate;

    public DTOForTable(String nume, String date, String denumire, String locuri_libere, String locuri_ocupate) {
        this.nume = nume;
        this.date = date;
        this.denumire = denumire;
        this.locuri_libere = locuri_libere;
        this.locuri_ocupate = locuri_ocupate;
    }

    public String getNume() {
        return nume;
    }

    public String getDate() {
        return date;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getLocuri_libere() {
        return locuri_libere;
    }

    public String getLocuri_ocupate() {
        return locuri_ocupate;
    }
}
