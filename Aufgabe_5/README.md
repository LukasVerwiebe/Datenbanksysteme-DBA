# Aufgabenblatt 5

## Aufgabe 5.1 (Ein erstes JDBC-Projekt).
Im Rahmen dieser Aufgabe sollen die Konzepte zur Anfrageverarbeitung von JDBC sukzessive praktisch geübt werden. Dabei sollen Sie im Wesentlichen den in der Vorlesung
vorgestellten Standardablauf zugrunde legen. Als Datenbank soll weiterhin die Nordwind-DB zum Einsatz kommen. Diese lässt sich genau so, wie Sie sie bereits zur Ausführung der SQL-Anfragen verwendet haben, weiterhin innerhalb von Netbeans für den zu erstellenden Java-Code betreiben.

Als Arbeitsumfeld benötigen Sie ein Java-Application-Projekt (Netbeans 11+). Fügen Sie dem Projekt unter “Dependencies” die JavaDB-Treiber-Bibliotheken org.apache.derby:derbynet (Suche über Query “derbynet”) und org.apache.derby:derbyclient (Suche über Query “derbyclient”) jeweils in der Version 10.15.2 hinzu. Erstellen Sie zur Lösung dieser Aufgabe und der folgenden Aufgaben nur eine einzige Klasse (z.B. Uebungsblatt5). Die Aufgabenstellung ist so konzipiert, dass für jede Teilaufgabe eine statische Methode zur Umsetzung entstehen soll (z.B. private static void aufgabe1_a(. . . )). Innerhalb dieser Methode können Sie natürlich beliebig weitere Methoden und statische Variablen definieren und verwenden, um Ihre Programmlogik übersichtlich zu strukturieren. Es sollte aber so sein, dass sich die gesamte Lösung einer Teilaufgabe nur durch Aufruf einer einzigen, der Teilaufgabe zugeordneten statischen Methode ausführen lässt. Dann kann der konkrete Aufruf dieser Methoden innerhalb der main-Methode erfolgen. Kommentieren Sie die Methodenaufrufe bereits fertig gestellter Lösungen einfach beliebig ein und aus.

Benutzerinteraktionen lassen sich simulieren, indem innerhalb der main-Methode der aufzurufenden Methode einfach die entsprechenden Daten per Parameter übergeben werden
(z.B. der Firmenname für eine Suche nach Bestellungen für diesen Kunden).

(a) Anbindung der Nordwind-DB mit JDBC Erstellen Sie eine Methode, die eine Verbindung zur Nordwind-Datenbank aufbaut und anschließend wieder schließt. Das Verbindungsobjekt soll dabei in einer statischen Variable “con” hinterlegt werden. Lagern Sie die jeweilige Logik sinnvoll in Untermethoden (connect, close) aus, um das
Aufbauen und Schließen der Datenbankverbindung wiederverwenden zu können. Behandeln Sie mögliche Ausnahmen (SQLExceptions) sinnvoll und geben Sie entsprechende Log-Meldungen über die Standardausgabe aus. Es sollen mindestens die folgenden Artefakte entstehen:

- Eine statische Methode, die die Teilaufgabe repräsentiert.
- Eine statische Methode für den Verbindungsaufbau (connect).
- Eine statische Methode für den Verbindungsabbau (close).
- Eine statische Variable, die das Verbindungsobjekt speichert (con).

(b) Verarbeitung einer einfachen Anfrage Erstellen Sie eine Methode, die die Anfrage
- Welche Artikel (Artikelname) sind von der Firma “Ernst Handel” noch nicht bestellt worden?

(siehe auch Blatt 4) ausführt und das Ergebnis in die Standardausgabe schreibt. Nutzen Sie zunächst ein Objekt des Typs java.sql.Statement, um die SQL-Anfrage zu repräsentieren und führen Sie sie mit executeQuery aus. Vergessen Sie nicht, vor dem Schließen der Datenbankverbindung alle verwendeten Ressourcen zu schließen. Sie können ausgewählte Ressourcen auch unter Verwendung von try-with-resources automatisch schließen lassen. Es sollen mindestens die folgenden Artefakte entstehen:
- Eine statische Methode, die die Teilaufgabe repräsentiert.

(c) Parametrisierung der Anfrage Wir möchten die Anfrage aus der Teilaufgabe 1(b) nun variabel gestalten und einen Parameter einführen. Erstellen Sie eine Methode, die die Anfrage aus der Teilaufgabe 1(b) ausführt aber die Firma nicht mehr statisch im SQL-Ausdruck festlegt, sondern als Parameter übergeben bekommt. Verwenden Sie weiterhin ein Objekt des Typs Statement und fügen Sie den übergebenen Firmennamen per String-Verkettung in den SQL-Ausdruck ein. Führen Sie in Ihrer main-Methode die erstellte Methode mit unterschiedlichen Firmennamen aus und verwenden Sie auch Namen, die als Kunde nicht in der Nordwind-Datenbank vorkommen. Es sollen mindestens die folgenden Artefakte entstehen: 
- Eine statische Methode, die die Teilaufgabe repräsentiert.

(d) Verwendung von Prepared-Statements Lösen Sie die Teilaufgabe 1(c) nun unter Verwendung eines Objekts des Typs java.sql.PreparedStatement. Es sollen mindestens die folgenden Artefakte entstehen:
- Eine statische Methode, die die Teilaufgabe repräsentiert.

(e) Erweiterung der Ergebnisprojektion Wir möchten nun die ermittelten Artikeldaten aus der Teilaufgabe 1(d) um weitere Artikeleigenschaften erweitern und die Ausgabe
entsprechend anpassen. Erstellen Sie eine Methode, die die Anfrage aus der Teilaufgabe 1(d) dahingehend erweitert, dass neben dem Artikelnamen auch die Artikel-Nr und der Einzelpreis in der Form

![image](https://user-images.githubusercontent.com/63674539/192626774-3eefb810-d29f-429a-a21a-57e55b198e57.png)

Es sollen mindestens die folgenden Artefakte entstehen:
-  Eine statische Methode, die die Teilaufgabe repräsentiert.

Hinweis: Sie können zur Formatierung des Einzelpreises NumberFormat verwenden.

(f) Behandlung von NULL-Werten Erstellen Sie eine Methode, die alle Bestellungen (Bestell-Nr und Versanddatum) eines über einen Parameter übergebenen Kunden (Firma) in der Standardausgabe ausgibt. Wenn die Bestellung noch nicht versandt wurde (NULL-Wert), soll die Ausgabe die Meldung “Noch nicht versandt” anstelle eines Datums ausgeben. Führen Sie in Ihrer main-Methode die erstellte Methode mit den Firmennamen “Ernst Handel”, “Great Lakes Food Market” und “LILA-Supermercado” aus. Es sollen mindestens die folgenden Artefakte entstehen:
- Eine statische Methode, die die Teilaufgabe repräsentiert.

Hinweise:
- Nutzen Sie zur Prüfung auf NULL die passende Methode des Result-Sets-Objekts.
- Sie können für die Formatierung der Ausgabe des Versanddatums z.B. SimpleDateFormat verwenden.


## Aufgabe 5.2 (Manipulation der Datenbank).
Im Rahmen dieser Aufgabe möchten wir nun Konzepte von JDBC anwenden, um Datenbankinhalte zu manipulieren. Die Lösungen der Teilaufgaben sollen nach dem selben Prinzip erarbeitet werden wie in Aufgabe 1. Verwenden Sie die Methoden zur Verwaltung der Datenbankverbindung (connect und close) aus der Aufgabe 1 auch in dieser Aufgabe. Verwenden Sie nicht die optionalen Funktionen des Result-Sets zum Ändern, Löschen und Hinzufügen von Datensätzen. Verwenden Sie ausschließlich die entsprechenden SQL-Ausdrücke. Nutzen Sie für parametrisierte Ausdrücke konsequent Objekte der Klasse Prepared-Statement!

(a) Änderung von DB-Inhalten Erstellen Sie eine Methode, die für einen per Parameter übergebenen Artikel (Artikelname) den Einzelpreis um 10% erhöht und geben Sie über die Standardausgabe eine Mitteilung aus, ob die Änderung erfolgreich durchgeführt werden konnte. Bedenken Sie dabei, dass ein UPDATE auch dann erfolgreich ausgeführt wird, wenn kein Datensatz verändert wurde. Sie müssen also auswerten, ob überhaupt Datensätze mit dem UPDATE geändert wurden. Führen Sie in Ihrer main-Methode die erstellte Methode mit drei beliebigen Artikeln aus. Verwenden Sie auch einen Artikelnamen, den die Nordwind GmbH nicht anbietet!

(b) Änderung von DB-Inhalten Erstellen Sie eine Kopie der Methode aus (a) und passen Sie sie so an, dass nun der Einzelpreis direkt über einen per Parameterübergabe
angegeben Wert geändert werden kann. Führen Sie wieder in Ihrer main-Methode die erstellte Methode mit drei beliebigen Artikeln aus. Verwenden Sie auch hier wieder einen Artikelnamen, den die Nordwind GmbH nicht anbietet!

(c) Hinzufügen von Datensätzen Erstellen Sie eine Methode, die einen neuen Artikel auf Basis der folgenden Angaben hinzufügt:
- Artikelname
- Einzelpreis
- Mindestbestand
- Liefereinheit

Diese sollen als Parameter übergeben werden. Die Werte für Lagerbestand und BestellteEinheiten sollen mit demWert 0 und Auslaufartikel mit false initialisiert werden. Die Werte für Lieferanten-Nr und Kategorie-Nr sollen NULL bleiben. Die Artikel-Nr wird automatisch generiert und kann ignoriert werden.

Führen Sie in Ihrer main-Methode die erstellte Methode mit den folgenden drei neuen Artikeln aus:

![image](https://user-images.githubusercontent.com/63674539/192627624-7ad72396-ab53-41f6-b81d-e774a8a8e214.png)

(d) Löschen von Datensätzen Erstellen Sie eine Methode, die den per Parameter übergebenen Artikel (Artikelname) löscht und geben über die Standardausgabe eine Mitteilung aus, ob die Löschung erfolgreich durchgeführt werden konnte. Führen Sie in Ihrer main-Methode die erstellte Methode für die in der vorherigen Teilaufgabe
2(c) neu angelegten Artikel und einen beliebigen, nicht in der Datenbank befindlichen Artikelnamen aus.

(e) Manuelle Transaktionen JDBC fasst standardmäßig alle Statement-Ausführungen automatisch in eine Transaktion zusammen. Bei Datenbankmanipulationen, die aus mehr als einem Statement bestehen, welche zusammen als atomare Einheit betrachtet werden sollen, muss dieser Automatismus abgeschaltet und die Transaktion manuell verwaltet werden. Erstellen Sie eine Methode, die den Einzelpreis für Chang auf 15,00 Euro setzt und alle Bestelldetails, die auf Chang verweisen, entsprechend aktualisiert. Fassen Sie die beiden notwendigen Updates in eine Transaktion zusammen und wenden Sie für die Umsetzung die aus der Vorlesung bekannte try-catch-finally Schablone an.
