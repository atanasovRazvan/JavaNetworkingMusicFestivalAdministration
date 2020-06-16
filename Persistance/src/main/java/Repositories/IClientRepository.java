package Repositories;


import Entities.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public interface IClientRepository extends CrudRepository<Integer, Client> {
    default Connection getNewConnection(){

        Properties props=new Properties(System.getProperties());
        try {
            props.load(new FileInputStream("C:\\Users\\Razvan\\Desktop\\Facultate\\Anul_2\\MPP\\MPP_Project_Java\\Server\\src\\main\\resources\\bd.properties"));
            System.setProperties(props);
        }
        catch (IOException e) {
            System.out.println("Eroare: "+e);
        }
        String url = System.getProperty("jdbc.url");
        Connection conn = null;
        try {
            conn= DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println("Eroare stabilire conexiune "+e);
            e.printStackTrace();
        }
        return conn;
    }
}
