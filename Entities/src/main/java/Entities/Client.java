package Entities;

public class Client implements Entity<Integer> {

    private Integer id_client, nr_Locuri;
    private String nume;
    private Integer id_scena, id_artist;

    public Client(Integer id_client, Integer nr_Locuri, String nume, Integer id_scena, Integer id_artist) {
        this.nr_Locuri = nr_Locuri;
        this.nume = nume;
        this.id_scena = id_scena;
        this.id_artist = id_artist;
        this.id_client = id_client;
    }

    public Integer getNr_Locuri() {
        return nr_Locuri;
    }

    public void setNr_Locuri(Integer nr_Locuri) {
        this.nr_Locuri = nr_Locuri;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getId_scena() {
        return id_scena;
    }

    public void setId_scena(Integer id_scena) {
        this.id_scena = id_scena;
    }

    public Integer getId_artist() {
        return id_artist;
    }

    public void setId_artist(Integer id_artist) {
        this.id_artist = id_artist;
    }

    @Override
    public Integer getId() {
        return id_client;
    }

    @Override
    public void setId(Integer integer) {
        id_client = integer;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", nr_Locuri=" + nr_Locuri +
                ", nume='" + nume + '\'' +
                ", id_scena=" + id_scena +
                ", id_artist=" + id_artist +
                '}';
    }
}
