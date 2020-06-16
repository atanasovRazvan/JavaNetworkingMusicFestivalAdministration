package Entities;

public class Scena implements Entity<Integer> {

    private Integer id_scena;
    private String denumire;

    public Scena(Integer id_spectacol, String place) {
        this.id_scena = id_spectacol;
        this.denumire = place;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public Integer getId() {
        return this.id_scena;
    }

    @Override
    public void setId(Integer integer) {
        this.id_scena = integer;
    }

    @Override
    public String toString() {
        return "Scena{" +
                "id_scena=" + id_scena +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}
