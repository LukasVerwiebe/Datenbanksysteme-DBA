
package myfirstjdbcexample;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DBA-Praktikum WS 2021/22
 * 
 * Loesungen fuer die Aufgaben des Uebungsblatts 5
 * 
 * @author lukas
 */
public class Blatt5 {
    private static final String NORDWIND_DB_URL 
                                    = "jdbc:derby://localhost:1527/nordwind";
    
    private static Connection con;
    
    public static void main(String[] args) {
        // Lösung Aufgabe 1.a
        aufgabe1_a();

        // Lösung Aufgabe 1.b
//        aufgabe1_b();

        // Lösung Aufgabe 1.c
//        aufgabe1_c("Ernst Handel");
//        aufgabe1_c("Alfreds Futterkiste");
//        aufgabe1_c("Unbekannt");
//
        // Lösung Aufgabe 1.d
//        aufgabe1_d("Ernst Handel");
//        aufgabe1_d("Alfreds Futterkiste");
//        aufgabe1_d("Unbekannt");

        // Lösung Aufgabe 1.e
//        aufgabe1_e("Ernst Handel");
//        aufgabe1_e("Alfreds Futterkiste");
//        aufgabe1_e("Unbekannt");

        // Lösung Aufgabe 1.f
//        aufgabe1_f("Ernst Handel");
//        aufgabe1_f("Great Lakes Food Market");
//        aufgabe1_f("LILA-Supermercado");

        // Lösung Aufgabe 2.a
//        aufgabe2_a("Chang");
//        aufgabe2_a("Blutwurst");
//        aufgabe2_a("Thüringer Rostbratwurst");

        // Lösung Aufgabe 2.b
//        aufgabe2_b("Chang", 10.99D);
//        aufgabe2_b("Blutwurst", 5.39D);
//        aufgabe2_b("Thüringer Rostbratwurst", 2.89D);

        // Lösung Aufgabe 2.c
//        aufgabe2_c("Chips", 2.99D, 100, "30 Tüten pro Karton");
//        aufgabe2_c("Fahrrad", 389D, 20, "1 Stk.");
//        aufgabe2_c("Pralinen", 5.99D, 150, "50 Packungen pro Karton");

        // Lösung Aufgabe 2.d
//        aufgabe2_d("Chips");
//        aufgabe2_d("Fahrrad");
//        aufgabe2_d("Pralinen");
//        aufgabe2_d("Unbekannt");

        // Lösung Aufgabe 2.e
//        aufgabe2_e();
    }
    
    public static void aufgabe2_e() {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Statement-Objekte werden per Auto-Closable geschlossen
            try (Statement updateChang = con.createStatement();
                 Statement updateOrderDetail = con.createStatement()) {
                
                // Manuelle Transaktion ermöglichen und starten
                con.setAutoCommit(false);
                
                updateChang.execute(
                    "UPDATE \"Artikel\" SET \"Einzelpreis\" = 15 " + 
                    "WHERE \"Artikelname\" = 'Chang'");
                updateOrderDetail.execute(
                    "UPDATE \"Bestelldetails\" SET \"Einzelpreis\" = 15  " + 
                    "WHERE \"Artikel-Nr\" = (SELECT \"Artikel-Nr\" " + 
                                            "FROM \"Artikel\" " +
                                            "WHERE \"Artikelname\" = 'Chang')");
                
                // Manuelle Transaktion erfolgreich beenden und endgültig 
                // speichern
                con.commit();
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                // Aenderungen der manuellen Transaktion rueckgaengig machen
                try {
                    con.rollback();
                } catch (SQLException sqlex) {
                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Zuruecksetzen der Transaktion!", 
                            sqlex);
                }
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {
                
                // Automatische  Transaktionsverwaltung wieder aktivieren
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqlex) {
                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Aktivieren der automatischen Transaktion!", 
                            sqlex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe2_d(String artikelname) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement delete = con.prepareStatement(
                    "DELETE FROM \"Artikel\" " + 
                    "WHERE \"Artikelname\" = ?")) {
                
                // Parameter setzen
                delete.setString(1, artikelname);
                
                // Anfrage ausfuehren
                if (delete.executeUpdate() == 0) {
                    
                    // Es wurde kein Artikel mit dem uebergebenen Namen 
                    // zum Loeschen gefunden
                    System.out.println("Artikel " + artikelname + " konnte " +
                                       "nicht gelöscht werden.");
                } else {
                    
                    // Mindestens ein Artikel mit dem uebergenbenen Namen 
                    // konnte geloescht werden
                    System.out.println("Artikel " + artikelname + 
                                       " erfolgreich gelöscht.");
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {
                    
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe2_c(String artikelname, double einzelreis,
                                  int mindestbestand, String liefereinheten) {
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
                    
                    // Der Artikel wurde hinzugefuegt
                    System.out.println("Artikel " + artikelname + 
                                       " erfolgreich hinzugefügt.");
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {
                    
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe2_b(String artikel, double neuerPreis) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement update = con.prepareStatement(
                    "UPDATE \"Artikel\" " +
                    "SET \"Einzelpreis\" = ? " +
                    "WHERE \"Artikelname\" = ?")) {
                
                // Parameter setzen
                update.setDouble(1, neuerPreis);
                update.setString(2, artikel);
                
                // Anfrage ausfuehren
                if (update.executeUpdate() == 0) {
                    
                    // Es wurde kein Artikel mit dem übergebenen Namen gefunden
                    System.out.println("Artikel " + artikel + " existiert " +
                                       "nicht.");
                } else {
                    
                    // Es konnte mindestens ein Artikelpreis angepasst werden
                    System.out.println("Einzelpreis für Artikel " + artikel + 
                                       " erfolgreich angepasst.");
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {
                    
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe2_a(String artikel) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement update = con.prepareStatement(
                    "UPDATE \"Artikel\" " +
                    "SET \"Einzelpreis\" = \"Einzelpreis\" * 1.1 " +
                    "WHERE \"Artikelname\" = ?")) {
                
                // Parameter setzen
                update.setString(1, artikel);
                
                // Anfrage ausfuehren
                if (update.executeUpdate() == 0) {
                    
                    // Es wurde kein Artikel mit dem übergebenen Namen gefunden
                    System.out.println("Artikel " + artikel + " existiert " +
                                       "nicht.");
                } else {
                    
                    // Es konnte mindestens ein Artikelpreis angepasst werden
                    System.out.println("Einzelpreis für Artikel " + artikel + 
                                       " erfolgreich angepasst.");
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Fehler bei der Ausführung!", 
                        ex);
            } finally {
                    
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_f(String firma) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Deklaration der Variablen fuer die benoetigten Ressourcen
            ResultSet result = null;
            
            // Zur Formatierung von Datumswerten
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement query = con.prepareStatement(
                "SELECT \"Bestell-Nr\", \"Versanddatum\" " +
                        "FROM \"Bestellungen\" B, \"Kunden\" K " +
                        "WHERE B.\"Kunden-Code\" = K.\"Kunden-Code\" AND " +
                              "\"Firma\" = ?")) {
                
                // Parameter setzen
                query.setString(1, firma);
                
                // Anfrage ausfuehren
                result = query.executeQuery();
                
                System.out.println("Ergebnis (" + firma + "):");
                
                // Ergebnis verarbeiten
                while (result.next()) {
                    String versand;
                    int bestellnr = result.getInt(1);
                    Date versandtdatum = result.getDate(2);
                    
                    if (result.wasNull()) {
                        
                        // Wenn NULL, wiurde die Bestellung noch nicht versandt
                        versand = "Noch nicht versandt";
                    } else {
                        versand = dateFormatter.format(versandtdatum);
                    }
                    
                    System.out.println("\t" + bestellnr + " " + versand);
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Anfrage konnte nicht ausgefuehrt werden", 
                        ex);
            } finally {
                // Result-Set ggf. schließen
                
                try {
                    
                    // Result-Set geoeffnet?
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }                                        
                } catch (SQLException ex) {
                    // Fehlerbehandlung

                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Schließen des Result-Sets", 
                            ex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_e(String firma) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
                        
            // Deklaration der Variablen fuer die benoetigten Ressourcen
            ResultSet result = null;
            
            // Zur Formatierung von Euro-Werten
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement query = con.prepareStatement(
                "SELECT \"Artikel-Nr\", \"Artikelname\", \"Einzelpreis\" " +
                        "FROM \"Artikel\" " +
                        "WHERE \"Artikel-Nr\" not in (" +
                        "SELECT \"Artikel-Nr\" " +
                        "FROM \"Bestelldetails\" BD, \"Bestellungen\" B, " +
                             "\"Kunden\" K " +
                        "WHERE BD.\"Bestell-Nr\" = B.\"Bestell-Nr\" AND " +
                              "B.\"Kunden-Code\" = K.\"Kunden-Code\" AND " +
                              "\"Firma\" = ?)")) {
                
                // Parameter setzen
                query.setString(1, firma);
                
                // Anfrage ausfuehren
                result = query.executeQuery();
                
                System.out.println("Ergebnis (" + firma + "):");
                
                // Ergebnis verarbeiten
                while (result.next()) {
                    int artikelnr = result.getInt(1);
                    String artikelname = result.getString(2);
                    String einzelpreis = nf.format(result.getDouble(3));
                    
                    System.out.println("\t" + artikelname + " (Nr.: " + 
                                       artikelnr + ", Preis: " + einzelpreis + 
                                       " Euro)");
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Anfrage konnte nicht ausgefuehrt werden", 
                        ex);
            } finally {
                // Result-Set ggf. schließen
                
                try {
                    
                    // Result-Set geoeffnet?
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }                    
                } catch (SQLException ex) {
                    // Fehlerbehandlung

                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Schließen des Result-Sets", 
                            ex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_d(String firma) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
            
            // Deklaration der Variablen fuer die benoetigten Ressourcen
            ResultSet result = null;
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (PreparedStatement query = con.prepareStatement(
                "SELECT \"Artikelname\" " +
                        "FROM \"Artikel\" " +
                        "WHERE \"Artikel-Nr\" not in (" +
                        "SELECT \"Artikel-Nr\" " +
                        "FROM \"Bestelldetails\" BD, \"Bestellungen\" B, " +
                             "\"Kunden\" K " +
                        "WHERE BD.\"Bestell-Nr\" = B.\"Bestell-Nr\" AND " +
                              "B.\"Kunden-Code\" = K.\"Kunden-Code\" AND " +
                              "\"Firma\" = ?)")) {
                
                // Parameter setzen
                query.setString(1, firma);
                
                // Anfrage ausfuehren
                result = query.executeQuery();
                
                System.out.println("Ergebnis (" + firma + "):");
                
                // Ergebnis verarbeiten
                while (result.next()) {
                    String artikelname = result.getString(1);
                    System.out.println("\t" + artikelname);
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Anfrage konnte nicht ausgefuehrt werden", 
                        ex);
            } finally {
                // Result-Set ggf. schließen
                
                try {
                    
                    // Result-Set geoeffnet?
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }                    
                } catch (SQLException ex) {
                    // Fehlerbehandlung

                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Schließen des Result-Sets", 
                            ex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_c(String firma) {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
            
            // Deklaration der Variablen fuer die benoetigten Ressourcen
            ResultSet result = null;
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (Statement query = con.createStatement()) {
                
                // Anfrage ausfuehren
                result = query.executeQuery(
                "SELECT \"Artikelname\" " +
                        "FROM \"Artikel\" " +
                        "WHERE \"Artikel-Nr\" not in (" +
                        "SELECT \"Artikel-Nr\" " +
                        "FROM \"Bestelldetails\" BD, \"Bestellungen\" B, " +
                             "\"Kunden\" K " +
                        "WHERE BD.\"Bestell-Nr\" = B.\"Bestell-Nr\" AND " +
                              "B.\"Kunden-Code\" = K.\"Kunden-Code\" AND " +
                              "\"Firma\" = '" + firma + "')"
                );
                
                System.out.println("Ergebnis (" + firma + "):");
                
                // Ergebnis verarbeiten
                while (result.next()) {
                    String artikelname = result.getString(1);
                    System.out.println("\t" + artikelname);
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Anfrage konnte nicht ausgefuehrt werden", 
                        ex);
            } finally {
                // Result-Set ggf. schließen
                
                try {
                    
                    // Result-Set geoeffnet?
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }
                    
                } catch (SQLException ex) {
                    // Fehlerbehandlung

                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Schließen des Result-Sets", 
                            ex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_b() {
        if (connect()) {
            // Verbindung zur Nordwind-DB erstellt
            
            // Deklaration der Variablen fuer die benoetigten Ressourcen
            ResultSet result = null;
            
            // Statement-Objekt wird per Auto-Closable geschlossen
            try (Statement query = con.createStatement()) {
                
                // Anfrage ausfuehren
                result = query.executeQuery(
                "SELECT \"Artikelname\" " +
                        "FROM \"Artikel\" " +
                        "WHERE \"Artikel-Nr\" not in (" +
                        "SELECT \"Artikel-Nr\" " +
                        "FROM \"Bestelldetails\" BD, \"Bestellungen\" B, " +
                             "\"Kunden\" K " +
                        "WHERE BD.\"Bestell-Nr\" = B.\"Bestell-Nr\" AND " +
                              "B.\"Kunden-Code\" = K.\"Kunden-Code\" AND " +
                              "\"Firma\" = 'Ernst Handel')"
                );
                
                System.out.println("Ergebnis:");
                
                // Ergebnis verarbeiten
                while (result.next()) {
                    String artikelname = result.getString(1);
                    System.out.println(artikelname);
                }
                
            } catch (SQLException ex) {
                // Fehlerbehandlung
                
                Logger.getLogger(Blatt5.class.getName()).log(
                        Level.SEVERE, 
                        "Anfrage konnte nicht ausgefuehrt werden", 
                        ex);
            } finally {
                // Result-Set ggf. schließen
                
                try {
                    
                    // Result-Set geoeffnet?
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }
                } catch (SQLException ex) {
                    // Fehlerbehandlung

                    Logger.getLogger(Blatt5.class.getName()).log(
                            Level.SEVERE, 
                            "Fehler beim Schließen des Result-Sets", 
                            ex);
                }
                
                // Datenbankverbindung schließen
                close();
            }
        }
    }
    
    public static void aufgabe1_a() {
        if (connect()) {
            
            if (close()) {
            } else {
                System.out.println("ERROR: Connection to " + NORDWIND_DB_URL 
                                   + " not closed!");
            }        
        } else {
            System.out.println("ERROR: Connection to " + NORDWIND_DB_URL 
                               + " not established!");
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
            Logger.getLogger(Blatt5.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Blatt5.class.getName()).log(Level.SEVERE, 
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
            Logger.getLogger(Blatt5.class.getName()).log(Level.SEVERE, 
                                    "Schließen der Verbindungzu " + 
                                    NORDWIND_DB_URL + " fehlgeschlagen", ex);
        }
        
        return isConnected;
    }
    
}
