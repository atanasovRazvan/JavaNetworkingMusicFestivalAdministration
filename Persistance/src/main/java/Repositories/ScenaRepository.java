package Repositories;

import Entities.Scena;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScenaRepository implements IScenaRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(Scena e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "INSERT INTO Scena(Sid, Denumire) VALUES(" + e.getId() + ", '" + e.getDenumire() + "');";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a adaugat scena " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void delete(Integer integer)  {

        try {
            Connection conn = getNewConnection();
            Scena s = this.findOne(integer);

            String cmd = "DELETE FROM Scena WHERE Sid=" + integer + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a sters scena " + s.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void update(Scena e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "UPDATE Scena SET Denumire='" + e.getDenumire() + "' WHERE Sid=" + e.getId() + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a modificat scena " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public Scena findOne(Integer integer)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT * FROM Scena WHERE Sid=" + integer + ";";
            String denumire = "";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cmd)) {
                while (rs.next()) {
                    denumire = rs.getString("Denumire");
                }
            }
            Scena s = new Scena(integer, denumire);
            logger.info("S-a cautat scena " + s.toString());
            conn.close();
            return new Scena(integer, denumire);
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
            return null;
        }

    }

    @Override
    public Integer size()  {

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT COUNT(*) FROM Scena";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            logger.info("S-au numarat {} scene.", rs.getInt(1));
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
