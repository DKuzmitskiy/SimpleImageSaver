package lab4;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class MySQL {
    
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String CONN_STRING = 
            "jdbc:mysql://localhost:3306/?user=root&password=root&useSSL=false";
    private Connection conn = null;
    final private ObservableList<String> items;

    public MySQL(ObservableList<String> items) {
        this.items = items;
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL driver error!");
        }
        
        try {
            conn = DriverManager.getConnection(CONN_STRING);
        } catch (SQLException ex) {
            System.err.println("Cannot open connection to server!");
        }
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("create database lab4");  
            st.executeUpdate("USE lab4");
            st.executeUpdate("create table Images (filename varchar(255), data LONGBLOB)");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public int putPhoto(String name, String path) {
        
        File file = new File(path);
        int size = (int) file.length();
        
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("use lab4");            
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file), size);           
            
            PreparedStatement pst = null;            
            pst = conn.prepareStatement("insert into Images (filename, data) values (?,?)");
            pst.setString(1, name);
            pst.setBinaryStream(2, fis);
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }
    
    public void getPhotos(){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("use lab4");                       
            ResultSet rs = st.executeQuery("select filename from Images");
            
            while (rs.next()) {
                System.out.println(rs.getString("filename"));
                items.add(rs.getString("filename"));
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public Image getPhoto(String name){
//        System.out.println(name);
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("use lab4"); 
            ResultSet rs = st.executeQuery("select data from Images where filename='"+name+"'");
            
            while (rs.next()) {
                System.out.println(rs.getBytes("data"));
//                items.add(rs.getString("filename"));
                return new Image(new ByteArrayInputStream(rs.getBytes("data")));
            }
            
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public int DeletePhoto(String name){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("use lab4");                    
            
            PreparedStatement pst = null;            
            pst = conn.prepareStatement("delete from Images where filename=?");
            pst.setString(1, name);
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0;
    }
    
}
