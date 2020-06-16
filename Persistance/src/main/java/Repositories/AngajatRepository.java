package Repositories;

import Entities.Angajat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AngajatRepository implements IAngajatRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(Angajat e){

        try {
            Connection conn = getNewConnection();

            String cmd = "INSERT INTO Angajat(Aid, Password) VALUES(" + e.getId() + ", '" + e.getPassword() + "');";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a adaugat angajatul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void delete(Integer integer){

        try {
            Connection conn = getNewConnection();
            Angajat a = this.findOne(integer);

            String cmd = "DELETE FROM Angajat WHERE Aid=" + integer + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a sters angajatul " + a.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void update(Angajat e){

        try {
            Connection conn = getNewConnection();

            String cmd = "UPDATE Angajat SET Password='" + e.getPassword() + "' WHERE Aid=" + e.getId() + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a modificat angajatul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public Angajat findOne(Integer integer){

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT * FROM Angajat WHERE Aid=" + integer + ";";
            String password = "";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cmd)) {
                while (rs.next()) {
                    password = rs.getString("Password");
                }
            }
            Angajat a = new Angajat(integer, password);
            logger.info("S-a cautat angajatul " + a.toString());
            conn.close();
            return a;
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
            return null;
        }

    }

    @Override
    public Integer size(){

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT COUNT(*) FROM Angajat";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            logger.info("S-au numarat {} angajati.", rs.getInt(1));
            int result = rs.getInt(1);
            conn.close();
            return result;
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
            return 0;
        }
    }
}
