# Aufgabenblatt 7

## Aufgabe 7.2 (DDL).
Die Norwind-Datenbank soll um eine Lagerverwaltung erweitert werden. Die Nordwind GmbH lagert alle Artikel hinsichtlich der zugeordneten Kategorie in entsprechende Lagerabteilungen. Alle Artikel einer Kategorie sind also in genau einer Abteilung des Lagers zu finden. Eine Analyse lieferte die folgenden Relationenschemata:

### Lager (Abteilung, Kapazitaet, Verantwortlicher) 
- Schlüssel: Abteilung
- Fremdschlüssel: Verantwortlicher ! Personal (Personal-Nr)

### KategorieLager (Abteilung, Kategorie)
- Schlüssel: Abteilung oder Kategorie
- Fremdschlüssel: Abteilung ! Lager (Abteilung)
- Fremdschlüssel: Kategorie ! Kategorien (Kategorie-Nr)

### ArtikelLager (Artikel, Abteilung, Mindestbestand, BestellteEinheiten, Lagerstand)
- Schlüssel: Artikel
- Fremdschlüssel: Artikel ! Artikel (Artikel-Nr)
- Fremdschlüssel: Abteilung ! Lager (Abteilung)

Die Typen aller Attribute, die sich nicht aus den bereits bestehenden Tabellen der Nordwinddatenbank ableiten lassen, sind INTEGER.

Es gelten darüber hinaus die folgenden Integritätsbedingungen:
- Referenzielle Integrität: Wenn eine ganze Kategorie aus dem Angebot gelöscht wird, dann soll die entsprechende Lagerabteilung zunächst ohne Bezug zu einer Kategorie verbleiben.
- Check-Constraint: Die Lagerkapazität einer Abteilungen soll 100 nicht unterschreiten und 10.000 nicht überschreiten.
- Mit Ausnahme des Attributs “Verantwortlicher” und Attributen, die implizit NULL erlauben müssen, sind für alle anderen Attribute aller Relationen keine NULL-Werte erlaubt.

(a) Erstellen Sie unter Verwendung geeigneter SQL-Statements für alle drei Relationen die resultierenden Tabellen.

(b) Füllen Sie Ihre neuen Tabellen nun mit Daten:
Tabelle Lager: Erstellen Sie initial Lagerabteilungen, indem Sie die folgenden Tupel mit einem INSERT-Befehl einfügen: (1, 500, NULL)
- (2, 1000, NULL)
- (3, 500, NULL)
- (4, 400, NULL)
- (5, 300, NULL)
- (6, 300, NULL)
- (7, 400, NULL)
- (8, 700, NULL)

Tabelle KategorieLager: Ordnen Sie dynamisch über eine Kombination INSERT / SELECT alle Kategorienummern aus der Tabelle Kateogien exakt der selben Abteilungsnummer zu (z.B.: Kategorie-Nr 1 zu Abteilung 1).

Tabelle ArtikelLager: Ordnen Sie dynamisch über eine Kombination INSERT / SELECT alle Artikelnummern aus der Tabelle Artikel über die jeweils zugeordnete Kategorie unter Verwendung der Zuordnung in der Tabelle KategorieLager der entsprechenden Abteilungsnummer zu.

Versuchen Sie eine weitere Lagerabteilung mit der Kapazität 99 hinzuzufügen. Funktioniert es? Warum?

(c) JavaDB unterstützt leider keine Assertions. Wie würde eine Assertion zu erstellen sein, die verhindert, dass die Summe der Lagerbestände aller Artikel einer Kategorie (ArtikelLager) die Lagerkapazität (Lager) überschreitet. Führen Sie ausschließlich den SELECT-Ausdruck Ihrer Assertion aus und ermitteln, ob der aktuellen Datenbestand vor dem Hintergrund dieser Integritätsbedingung gültig ist.
