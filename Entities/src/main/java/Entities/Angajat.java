package Entities;

public class Angajat implements Entity<Integer> {

    private Integer id;
    private String password;

    public Angajat(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
