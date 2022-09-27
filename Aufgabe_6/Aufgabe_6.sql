/**
 * Author:  lukas
 */

-- Aufgabe 2 DML – Insert, Update und Delete

-- a) Ändern Sie den Lieferanten von Artikel mit der Artikel-Nr 1 auf die Lieferanten-Nr 7. 
--    Setzten Sie anschließend die Lieferanten-Nr zurück auf den ursprünglichen Wert (Lieferanten-Nr 1).
UPDATE "Artikel"
SET "Lieferanten-Nr" = 7
WHERE "Artikel-Nr" = 1

-- Zurücksetzen
UPDATE "Artikel"
SET "Lieferanten-Nr" = 1
WHERE "Artikel-Nr" = 1

-- b) Erhöhen Sie den Artikelpreis von Chai um 10%.
UPDATE "Artikel"
SET "Einzelpreis" = 1.1 * "Einzelpreis"
WHERE "Artikelname" ='Chai'

-- c) Erhöhen Sie die Preise aller Gewürze um 50%.
UPDATE "Artikel"
SET "Einzelpreis" = 1.5 * "Einzelpreis"
WHERE "Kategorie-Nr" = (
    SELECT "Kategorie-Nr"
    FROM "Kategorien" K
    WHERE K."Kategoriename" = 'Gewürze')

-- d) Senken Sie den Mindestbestand aller Artikel, die mehr als 15 € kosten, auf die Hälfte.
UPDATE "Artikel"
SET "Mindestbestand" = 0.5 * "Mindestbestand"
WHERE "Einzelpreis" > 15

-- e) Fügen Sie einen neuen Artikel „Polohemd“ hinzu, Einzelpreis = 20€. 
--    Brauchen Sie weitere Angaben? Wenn ja, welche? Wenn nein, was wäre sinnvoll?
INSERT INTO "Artikel" ("Artikelname", "Einzelpreis")
VALUES ('Polohemd',20)

-- Artikel-Nr wird automatisch vergeben, ist deshalb hier nicht nötig 
-- (real ist eine händische Vergabe meist sinnvoller)
-- zumindest die Felder, die Beziehungen zu anderen Tabellen herstellen 
-- (real meist Fremdschlüssel) sollten möglichst gleich mit eingepflegt werden. 
-- Dies würde ggf. erst die Neuanlage eines Lieferanten bedeuten.

-- f) Der Lieferant des Hemdes ist die Firma „Duck“. Ergänzen Sie die DB entsprechend.
--    Was ist tun? Welche SQL-Befehle sind nötig?
--    Wie würde der UPDATE-Befehl aussehen, wenn dem Artikel Polohemd der Lieferant
--    Duck ausschließlich über Verwendung des Firmennamens zugewiesen wird?

--> 1. Zunächst prüfen , ob Lieferant Duck bereits existiert:
SELECT "Firma"
FROM "Lieferanten"
WHERE "Firma" = 'Duck'
--> 2. Wenn er bereits existiert, dann entfällt das neu anlegen, sonst:
--     Anlegen des Lieferanten:
INSERT into "Lieferanten" ("Firma")
VALUES ('Duck') 
--     Hinweis: auch hier wird der Schlüssel (Lieferanten-Nr) automatisch vergeben
--> 3. Artikel entsprechen ergänzen:
UPDATE "Artikel"
SET "Lieferanten-Nr" = (
    SELECT L."Lieferanten-Nr"
    FROM "Lieferanten" L
    WHERE L."Firma" = 'Duck')
WHERE "Artikelname" = 'Polohemd'

-- g) Löschen Sie nun wieder den Lieferante „Duck“ und das Polohemd.
--    Ist die Reihenfolge relevant?
-- Die Löschreihenfolge ist wichtig, da die referentielle Integrität das 
-- Löschen des Lieferanten "Duck" verhindert, solange noch irgendein Artikel
-- "Duck" referenziert (hier nur das Polohemd)
-- 1.
DELETE FROM "Artikel"
WHERE "Artikelname" = 'Polohemd'
-- 2.
DELETE FROM "Lieferanten"
WHERE "Firma" = 'Duck'

-- h) Löschen Sie alle Bestellungen des Kunden "Ernst Handel".
--    Beachten Sie die Löschreihenfolge.
--  1. Bestelldetails löschen
DELETE FROM "Bestelldetails" BD
WHERE BD."Bestell-Nr" in (
    SELECT "Bestell-Nr"
    FROM "Bestellungen"
    WHERE "Kunden-Code" = (
        SELECT "Kunden-Code"
        FROM "Kunden"
        WHERE "Firma" = 'Ernst Handel')
)
--  2. Bestellungen löschen
DELETE FROM "Bestellungen" B
WHERE "Kunden-Code" = (
    SELECT "Kunden-Code"
    FROM "Kunden"
    WHERE "Firma" = 'Ernst Handel'
)
