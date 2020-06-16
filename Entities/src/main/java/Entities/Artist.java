package Entities;

public class Artist implements Entity<Integer> {

    private Integer id_artist;
    private String nume;

    public Artist(Integer id_artist, String nume) {
        this.id_artist = id_artist;
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public Integer getId() {
        return id_artist;
    }

    @Override
    public void setId(Integer integer) {
        id_artist = integer;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id_artist=" + id_artist +
                ", nume='" + nume + '\'' +
                '}';
    }
}
