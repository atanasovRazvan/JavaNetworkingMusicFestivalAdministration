package Repositories;

import Entities.DTOArtistSpectacol;
import Entities.DTOForTable;
import Entities.Spectacol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpectacolRepository implements ISpectacolRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void add(Spectacol e)  {

        try {
            Connection conn = getNewConnection();

            LocalDateTime now = e.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);

            String cmd = "INSERT INTO Spectacol(Aid, Sid, L_libere, L_ocupate, Data) VALUES" +
                    "(" + e.getId_artist() + ", " + e.getId_scena() + ", " + e.getLocuri_libere() + ", " + e.getLocuri_ocupate() +
                    ", '" + formatDateTime + "');";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a adaugat spectacolul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void delete(Integer id1, Integer id2)  {

        try {
            Connection conn = getNewConnection();
            Spectacol s = this.findOne(id1, id2);

            String cmd = "DELETE FROM Spectacol WHERE Aid=" + id1 + " and Sid=" + id2 + ";";
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a sters spectacolul " + s.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public void update(Spectacol e)  {

        try {
            Connection conn = getNewConnection();

            LocalDateTime now = e.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);

            String cmd = "UPDATE Spectacol SET L_libere=" + e.getLocuri_libere() + ", L_ocupate=" + e.getLocuri_ocupate() +
                    ", Data='" + formatDateTime + "' WHERE Aid=" + e.getId_artist() + " and Sid=" + e.getId_scena();
            try (Statement stmt = conn.createStatement()) {
                int res = stmt.executeUpdate(cmd);
            }
            logger.info("S-a modificat spectacolul " + e.toString());
            conn.close();
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
        }

    }

    @Override
    public Spectacol findOne(Integer id1, Integer id2)  {

        try {
            Connection conn = getNewConnection();

            String cmd = "SELECT * FROM Spectacol WHERE Aid=" + id1 + " and Sid=" + id2 + ";";
            Integer libere = 0, ocupate = 0;
            String date = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cmd)) {
                while (rs.next()) {
                    libere = rs.getInt("L_libere");
                    ocupate = rs.getInt("L_ocupate");
                    date = rs.getString("Data");
                }
            }
            Spectacol s = new Spectacol(libere, ocupate, LocalDateTime.parse(date, formatter), id1, id2);
            logger.info("S-a cautat spectacolul " + s.toString());
            conn.close();
            return s;
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

            String cmd = "SELECT COUNT(*) FROM Spectacol";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            logger.info("S-au numarat {} spectacole.", rs.getInt(1));
            int result = rs.getInt(1);
            conn.close();
            return result;
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
            return 0;
        }
    }

    public ArrayList<DTOArtistSpectacol> cautare(LocalDate data){

        ArrayList<DTOArtistSpectacol> list = new ArrayList<>();
        try{
            Connection conn = getNewConnection();

            String cmd = "SELECT Aid, Sid, L_libere, Data FROM Spectacol WHERE Data >= '" + data + "' AND Data < '" + data.plusDays(1) +"';";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            while(rs.next()){
                Integer aid = rs.getInt("Aid");
                Integer sid = rs.getInt("Sid");
                Integer libere = rs.getInt("L_libere");
                String hour = rs.getString("Data");
                hour = hour.substring(hour.indexOf(" ")+1);
                DTOArtistSpectacol dto = new DTOArtistSpectacol(aid, sid, libere, hour);
                list.add(dto);
            }
            logger.info("S-au filtrat {} spectacole din data " + data, list.size());
            return list;
        }
        catch(SQLException ex){
            logger.error("Exception: {}", ex.getMessage());
            return null;
        }

    }

    public List<Spectacol> getAll(){

        try{

            List<Spectacol> data = new ArrayList<>();
            Connection conn = getNewConnection();
            String cmd = "SELECT * FROM Spectacol";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cmd);
            while(rs.next()){
                Integer id_artist = rs.getInt("Aid");
                Integer id_scena = rs.getInt("Sid");
                String date = rs.getString("Data");
                Integer locuri_libere = rs.getInt("L_libere");
                Integer locuri_ocupate = rs.getInt("L_ocupate");
                Spectacol spectacol = this.findOne(id_artist, id_scena);
                data.add(spectacol);
            }
            return data;

        }
        catch(Exception ex){
            return null;
        }

    }

//    public List<DTOForTable> getAll(ArtistService artistService, ScenaService scenaService){
//
//        try{
//            List<DTOForTable> data = new ArrayList<>();
//            Connection conn = getNewConnection();
//            String cmd = "SELECT * FROM Spectacol";
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(cmd);
//            while(rs.next()){
//                Integer id_artist = rs.getInt("Aid");
//                Integer id_scena = rs.getInt("Sid");
//                String date = rs.getString("Data");
//                Integer locuri_libere = rs.getInt("L_libere");
//                Integer locuri_ocupate = rs.getInt("L_ocupate");
//                DTOForTable dto = new DTOForTable(artistService.findOne(id_artist).getNume(), date, scenaService.findOne(id_scena).getDenumire(),
//                        String.valueOf(locuri_libere), String.valueOf(locuri_ocupate));
//                data.add(dto);
//            }
//            return data;
//
//        }
//        catch(SQLException e){
//            return null;
//        }
//
//    }

}
