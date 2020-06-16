package Entities;

import java.io.Serializable;

public class DTOForBuyTable implements Serializable {

    private String nume, denumire, hour, locuri_libere;
    private Integer id_artist, id_scena;

    public DTOForBuyTable(String nume, String denumire, String hour, String locuri_libere, Integer id_artist, Integer id_scena) {
        this.nume = nume;
        this.denumire = denumire;
        this.hour = hour;
        this.locuri_libere = locuri_libere;
        this.id_artist = id_artist;
        this.id_scena = id_scena;
    }

    public String getNume() {
        return nume;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getHour() {
        return hour;
    }

    public String getLocuri_libere() {
        return locuri_libere;
    }

    public Integer getId_artist() {
        return id_artist;
    }

    public Integer getId_scena() {
        return id_scena;
    }

    public void setId_artist(Integer id_artist) {
        this.id_artist = id_artist;
    }

    public void setId_scena(Integer id_scena) {
        this.id_scena = id_scena;
    }
}
