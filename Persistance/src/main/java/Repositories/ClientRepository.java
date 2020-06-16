package Repositories;

import Entities.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientRepository implements IClientRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(Client e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "INSERT INTO Client(Cid, Nume, Nr_locuri, Aid, Sid) VALUES(" + e.getId() + ", '" + e.getNume() +
                    "', " + e.getNr_Locuri() + ", " + e.getId_artist() + ", " + e.getId_scena() + ");";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a adaugat clientul " + e.toString());
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
            Client c = this.findOne(integer);

            String cmd = "DELETE FROM Client WHERE Cid=" + integer + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a sters clientul " + c.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void update(Client e)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "UPDATE Client SET Nume='" + e.getNume() + "', Nr_locuri=" + e.getNr_Locuri() + ", Aid=" + e.getId_artist() +
                    ", Sid=" + e.getId_scena() + " WHERE Cid=" + e.getId() + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a modificat clientul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public Client findOne(Integer integer)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT * FROM Client WHERE Cid=" + integer + ";";
            String nume = "";
            int nr_locuri = 0, aid = 0, sid = 0;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cmd)) {
                while (rs.next()) {
                    nume = rs.getString("Nume");
                    nr_locuri = rs.getInt("Nr_locuri");
                    aid = rs.getInt("Aid");
                    sid = rs.getInt("Sid");
                }
            }
            Client c = new Client(integer, nr_locuri, nume, sid, aid);
            logger.info("S-a cautat clientul " + c.toString());
            conn.close();
            return new Client(integer, nr_locuri, nume, sid, aid);
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

            String cmd = "SELECT COUNT(*) FROM Client";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            logger.info("S-au numarat {} clienti.", rs.getInt(1));
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
