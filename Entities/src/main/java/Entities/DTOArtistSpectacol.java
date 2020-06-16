package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DTOArtistSpectacol implements Serializable {

    private Integer id_artist;
    private Integer locuri_libere;
    private String hour;
    private Integer id_scena;

    public DTOArtistSpectacol(Integer id_artist, Integer id_scena, Integer locuri_libere, String hour) {
        this.id_artist = id_artist;
        this.locuri_libere = locuri_libere;
        this.hour = hour;
        this.id_scena = id_scena;
    }

    public Integer getId_artist() {
        return id_artist;
    }

    public void setId_artist(Integer id_artist) {
        this.id_artist = id_artist;
    }

    public Integer getLocuri_libere() {
        return locuri_libere;
    }

    public void setLocuri_libere(Integer locuri_libere) {
        this.locuri_libere = locuri_libere;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getId_scena() {
        return id_scena;
    }

    public void setId_scena(Integer id_scena) {
        this.id_scena = id_scena;
    }
}
