package Entities;

import java.time.LocalDateTime;

public class Spectacol implements Entity<Integer> {

    private Integer locuri_libere, locuri_ocupate;
    private LocalDateTime date;
    private Integer id_artist;
    private Integer id_scena;

    public Spectacol(Integer locuri_libere, Integer locuri_ocupate, LocalDateTime date, Integer id_artist, Integer id_scena) {
        this.locuri_libere = locuri_libere;
        this.locuri_ocupate = locuri_ocupate;
        this.date = date;
        this.id_artist = id_artist;
        this.id_scena = id_scena;
    }

    public Integer getLocuri_libere() {
        return locuri_libere;
    }

    public void setLocuri_libere(Integer locuri_libere) {
        this.locuri_libere = locuri_libere;
    }

    public Integer getLocuri_ocupate() {
        return locuri_ocupate;
    }

    public void setLocuri_ocupate(Integer locuri_ocupate) {
        this.locuri_ocupate = locuri_ocupate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getId_artist() {
        return id_artist;
    }

    public void setId_artist(Integer id_artist) {
        this.id_artist = id_artist;
    }

    public Integer getId_scena() {
        return id_scena;
    }

    public void setId_scena(Integer id_scena) {
        this.id_scena = id_scena;
    }

    @Override
    public Integer getId() {
        return Integer.parseInt(String.valueOf(this.getId_artist()) + this.getId_scena());
    }

    @Override
    public void setId(Integer integer) {
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "locuri_libere=" + locuri_libere +
                ", locuri_ocupate=" + locuri_ocupate +
                ", date=" + date +
                ", id_artist=" + id_artist +
                ", id_scena=" + id_scena +
                '}';
    }
}
