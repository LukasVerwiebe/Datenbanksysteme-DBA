# Aufgabenblatt 4

## Aufgabe 4.1 (Einrichten der SQL-Arbeitsumgebung).
Sie sollen im Rahmen des Praktikums nun SQL-Anfragen gegen eine echte Datenbank absetzen. Dazu müssen Sie zuerst ein passendes Arbeitsumfeld einrichten. Sie benötigen
die folgenden Komponenten zur Bearbeitung der folgenden Aufgaben:

1. Ein relationales Datenbankmanagementsystem (DBMS) Als RDBMS sollen Sie JavaDB lokal im Serverbetrieb verwenden. JavaDB wird mittlerweile von der Apache-Community weiterentwickelt und läuft dort unter dem Namen Apache Derby.

2. Eine vorgegebene und bereits mit Daten gefüllte Datenbank Eine hinreichend komplexe und gut gefüllte Beispiel-Datenbank lieferte Microsoft mit dem Desktop-Datenbanksystem MS Access bis zur Version 2000 aus. Diese Datenbank bildet das fachliche Modell der Nordwind GmbH, einem fiktiven Versandunternehmen, ab. Sie liegt portiert als JavaDB-Version vor und soll als Ziel aller zukünftigen Anfragen der Veranstaltung dienen.

3. Ein Arbeitsumfeld, das es Ihnen erlaubt SQL-Anfragen absetzen zu können. Hierfür soll Netbeans zum Einsatz kommen. Netbeans bietet neben einem komfortablen SQL-Editor die Möglichkeit, Datenbanken leicht zu integrieren. Im Moodle-Kurs können Sie JavaDB und die NordwindDB herunterladen. Außerdem finden Sie dort geeignete Tutorials zum Einrichten der Arbeitsumgebung. Im Anhang des Übungsblattes finden Sie eine unter MS Access verfügbare Übersicht der Relationen und Beziehungen der Nordwind-DB. Bitte machen Sie sich mit dem Schema vertraut.


## Aufgabe 4.2 (Erste SQL-Anfragen).
Formulieren Sie die folgenden Anfragen in SQL. Liefern Sie für jede Lösung, in denen Join-Bedingungen nötig sind, je eine Variante mit und eine Variante ohne expliziten JOINOperator.

(a) Finden Sie alle Bestellungen (Bestell-Nr) mit mindestens einem Artikel im Wert von über 100 ¤.

(b) Gesucht sind alle Bestellungen (Bestell-Nr) über “Chai” und “Tofu”.

(c) Finden Sie alle Artikel (Artikel-Nr, Artikelname), die in der Vergangenheit von der Firma “Ernst Handel” bestellt wurden.

(d) Für welche Artikel (Artikelname), die keine Auslaufartikel sind, müssen Nachbestellungen veranlasst werden?

(e) Finden Sie die Nummern der Bestellungen, die zu spät versandt wurden (Lieferdatumist das geplante Datum).

(f) Gesucht sind alle Kunden außerhalb Nordamerikas.

(g) Finden Sie die Nummern aller Bestellungen nach Skandinavien.

(h) Finden Sie alle Bestellungen (Bestell-Nr, Bestelldatum) der Firma ‘Ernst Handel”, die eine Bestellposition über “Camembert Pierrot” aufweisen.

(i) Finden Sie alle Bestellungen (Bestell-Nr, Bestelldatum), die von Frau Davolio bearbeitet wurden.

(j) Welche Kunden (Firma) haben irgendetwas anderes als Getränke bestellt?

(k) Finden Sie alle Artikel, deren Namen mit C beginnen.

(l) Finden Sie alle Artikel (Artikelname), die zur gleichen Kategorie gehören wie “Tofu”.

(m) Welches sind die Lieferanten (Firma) für “Chai”, “Tofu” und “Lakkalikööri”?

(n) Welche Mitarbeiter (Nachname) arbeiten in einem anderen Land als Frau Davolio?

(o) Finden Sie den teuersten Artikel (Artikelname).

(p) Gesucht ist der Lieferant (Firma), der den teuersten Artikel liefert.

(q) Finden Sie den Kunden (Firma), der die Bestellung mit den höchsten Frachtkosten in Auftrag gegeben hat!

(r) Finden Sie die Artikel (Artikelname), die überhaupt noch nicht verkauft wurden.
