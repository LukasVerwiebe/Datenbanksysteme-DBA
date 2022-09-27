/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blatt8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author laarmann
 */
public class Blatt8 {

    private static final String ARTIKEL_PREFIX = "___NEW___";
    
    private static final String NORDWIND_DB_URL 
                                    = "jdbc:derby://localhost:1527/nordwind";
    
    private static Connection con;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // 100.000 Artikel hinzufügen
        populateArtikel();
        
        // 100.000 Artikel wieder entfernen
//        deletePopulatedArtikel();
    }
    
    public static void deletePopulatedArtikel() {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
            
            try (Statement delete = con.createStatement()) {
                
                int count = delete.executeUpdate(
                        "DELETE FROM \"Artikel\" " +
                        "WHERE \"Artikelname\" LIKE '" + ARTIKEL_PREFIX + "%'");

                System.out.println(count + " Artikel wurden gelöscht.");
 
            } catch (SQLException ex) {
                // Fehlerbehandlung

                Logger.getLogger(Blatt8.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {

                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void populateArtikel() {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement insert = con.prepareStatement(
                "INSERT INTO \"Artikel\" (\"Artikelname\", " + 
                        "\"Einzelpreis\", \"Mindestbestand\", " + 
                        "\"Liefereinheit\", \"Lagerbestand\", " +
                        "\"BestellteEinheiten\", \"Auslaufartikel\", " +
                        "\"Lieferanten-Nr\", \"Kategorie-Nr\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                int i;
                
                for (i = 0; i < 100000; i++) {
                    addArtikel(insert, 
                               ARTIKEL_PREFIX + "Testartikel " + i, 
                               Math.random() * 100, 
                               0, 
                               "Stk.");
                }
                
                // Der Artikel wurde hinzugefuegt
                System.out.println(i + " Artikel erfolgreich hinzugefügt.");
            
            } catch (SQLException ex) {
                // Fehlerbehandlung

                Logger.getLogger(Blatt8.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {

                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void addArtikel(PreparedStatement insert, String artikelname, 
                                  double einzelreis, int mindestbestand, 
                                  String liefereinheten)
                       throws SQLException {


            // Parameter setzen
            insert.setString(1, artikelname);
            insert.setDouble(2, einzelreis);
            insert.setInt(3, mindestbestand);
            insert.setString(4, liefereinheten);
            insert.setInt(5, 0);
            insert.setInt(6, 0);
            insert.setBoolean(7, false);
            insert.setNull(8, Types.INTEGER);
            insert.setNull(9, Types.INTEGER);

            // Anfrage ausfuehren
            if (insert.executeUpdate() == 0) {

                // Der Artkel konnte nicht hinzugefuegt werden
                System.out.println("Artikel " + artikelname + " konnte " +
                                   "nicht hinzugefügt werden.");
            } else {

//                // Der Artikel wurde hinzugefuegt
//                System.out.println("Artikel " + artikelname + 
//                                   " erfolgreich hinzugefügt.");
            }
    }
    
    private static boolean connect() {
        boolean success = false;
        
        try {
            con = DriverManager.getConnection (NORDWIND_DB_URL, "app", "app");
            success = true;
            System.out.println("Connection to " + NORDWIND_DB_URL 
                               + " established!");
        } catch (SQLException ex) {
            Logger.getLogger(Blatt8.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    private static boolean close() {
        boolean success = false;
        
        try {
            if (isConnected()) {
                con.close();
                success = true;
                System.out.println("Connection to " + NORDWIND_DB_URL 
                                    + " closed!");
            } else {
                System.out.println("Connection to " + NORDWIND_DB_URL 
                                    + " not open!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Blatt8.class.getName()).log(Level.SEVERE, 
                                    "Verbindungsaufbau zu " + NORDWIND_DB_URL + 
                                    " fehlgeschlagen", ex);
        }
        
        return success;
    }
    
    private static boolean isConnected() {
        boolean isConnected = false;
        
        try {
            // Nur wenn die Verbindung existiert und nicht geschlossen ist,
            // ist sie geoeffnet.
            isConnected = (con != null && !con.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(Blatt8.class.getName()).log(Level.SEVERE, 
                                    "Schließen der Verbindungzu " + 
                                    NORDWIND_DB_URL + " fehlgeschlagen", ex);
        }
        
        return isConnected;
    }

}
