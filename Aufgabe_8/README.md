# Aufgabenblatt 8

## Aufgabe 8.3 (Anfrageoptimierung - Vorbereitung).
Im Folgenden betrachten wir die Auswertung von SQL Anfragen und deren Laufzeit. Wir wollen (ganz grob) untersuchen welche Anfragen der DB “leicht” fallen und welche eher
schwierig sind. 

(a) Datenvorbereitung: Um die Grenzen unserer Beispiel-DB zu erforschen, benötigen Sie eine größere Tabelle. Schreiben Sie ein Java Program, das Ihnen 100.000 neue Einträge zu die Tabelle “Artikel” hinzufügt (und evtl. diese wieder schnell löschen kann). 

Tipp: Die Lösung von Aufgabe 2.(c) auf Blatt 5 lässt sich hier einfach und schnell erweitern.

(b) Erste Analysen: Führen Sie die folgenden Schritte ein paar Mal hintereinander aus:
- Führen Sie das folgenden Statement auf der Tabelle “Artikel” aus: SELECT COUNT(*) FROM Artikel
- Notieren Sie die angegeben Laufzeit.
- Fügen Sie 100.000 Einträge zu Artikel hinzu. 

Was fällt Ihnen auf?


(c) Setzen Sie die Tabelle Artikel wieder zurück und starten Sie erneut mit der folgenden Anweisung:
- Führen Sie das folgenden Statement auf der Tabelle “Artikel” aus: SELECT COUNT(*) FROM Artikel, Bestelldetails
- Notieren Sie die angegeben Laufzeit.
- Fügen Sie 100.000 Einträge zu Artikel hinzu. 

Was fällt Ihnen auf?

(d) Setzen Sie die Tabelle Artikel wieder zurück und starten Sie erneut mit der folgenden Anweisung:
- Führen Sie das folgenden Statement auf der Tabelle “Artikel” aus: SELECT COUNT(*) FROM Artikel INNER JOIN Bestelldetails ON Artikel.ArtikelNr = Bestelldetails.ArtikelNr
- Notieren Sie die angegeben Laufzeit.
- Fügen Sie 100.000 Einträge zu Artikel hinzu.

Was fällt Ihnen auf?

(e) Setzen Sie die Tabelle Artikel wieder zurück und starten Sie erneut mit der folgenden Anweisung:
- Führen Sie das folgenden Statement auf der Tabelle “Artikel” aus: SELECT COUNT(*) FROM Artikel INNER JOIN Bestelldetails ON Artikel.ArtikelNr <> Bestelldetails.ArtikelNr
- Notieren Sie die angegeben Laufzeit.
- Fügen Sie 100.000 Einträge zu Artikel hinzu.

Was fällt Ihnen auf?

(f) (Knobel-Aufgabe, nicht klausurrelvant) Auch die letzte Anfrage läuft wieder sehr langsam. Finden Sie eine Möglichkeit die Antwort trotzdem effizient zu berechnen?
