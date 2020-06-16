package Repositories;

import Entities.Artist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistRepository implements IArtistRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(Artist e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "INSERT INTO Artist(Aid, Nume) VALUES(" + e.getId() + ", '" + e.getNume() + "');";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a adaugat artistul " + e.toString());
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
            Artist a = this.findOne(integer);

            String cmd = "DELETE FROM Artist WHERE Aid=" + integer + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a sters artistul " + a.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void update(Artist e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "UPDATE Artist SET Nume='" + e.getNume() + "' WHERE Aid=" + e.getId() + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a modificat artistul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public Artist findOne(Integer integer)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT * FROM Artist WHERE Aid=" + integer + ";";
            String nume = "";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cmd)) {
                while (rs.next()) {
                    nume = rs.getString("Nume");
                }
            }
            Artist a = new Artist(integer, nume);
            logger.info("S-a cautat artistul " + a.toString());
            conn.close();
            return a;
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

            String cmd = "SELECT COUNT(*) FROM Artist";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            logger.info("S-au numarat {} artisti.", rs.getInt(1));
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
