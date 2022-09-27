/**
 * Author:  lukas
 */

-- a) Finden Sie alle Bestellungen (Bestell-Nr) mit mindestens einem Artikel im Wert von über 100 €.

SELECT distinct "Bestell-Nr"
FROM "Bestelldetails"
WHERE "Einzelpreis" > 100;


-- b) Gesucht sind alle Bestellungen (Bestell-Nr) über „Chai“ und „Tofu“.

SELECT "Bestell-Nr"
FROM "Bestelldetails" bs, "Artikel" a
WHERE bs."Artikel-Nr" = a."Artikel-Nr" and
      ("Artikelname" = 'Tofu' or "Artikelname" = 'Chai');

-- Mit JOIN-Operator
SELECT "Bestell-Nr"
FROM "Bestelldetails" bs JOIN "Artikel" a ON (bs."Artikel-Nr" = a."Artikel-Nr")
WHERE "Artikelname" = 'Tofu' or "Artikelname" = 'Chai';
-- Natural Join schließt fälschlicherweise Spalte "Einzelpreis" mit ein.


-- c) Finden Sie alle Artikel (Artikel-Nr, Artikelname), die in der Vergangenheit von der Firma „Ernst Handel“ bestellt wurden.

SELECT distinct a."Artikel-Nr", "Artikelname"
FROM "Artikel" a, "Bestelldetails" bs, "Bestellungen" b, "Kunden" k
WHERE a."Artikel-Nr" = bs."Artikel-Nr" and
      bs."Bestell-Nr" = b."Bestell-Nr" and
      b."Kunden-Code" = k."Kunden-Code" and
      "Firma" = 'Ernst Handel';

-- Mit JOIN-Operator
SELECT distinct a."Artikel-Nr", "Artikelname"
FROM (("Artikel" a JOIN "Bestelldetails" bs ON (a."Artikel-Nr" = bs."Artikel-Nr"))
      JOIN "Bestellungen" b ON (bs."Bestell-Nr" = b."Bestell-Nr"))
      JOIN "Kunden" k ON (b."Kunden-Code" = k."Kunden-Code")
WHERE "Firma" = 'Ernst Handel'



-- d) Für welche Artikel (Artikelname), die keine Auslaufartikel sind, müssen Nachbestellungen veranlasst werden?

SELECT "Artikelname"
FROM "Artikel"
WHERE not "Auslaufartikel" and
      ("Lagerbestand" + "BestellteEinheiten" < "Mindestbestand");



-- e) Finden Sie die Nummern der Bestellungen, die zu spät versandt wurden (Lieferdatum ist das geplante Datum).

SELECT "Bestell-Nr"
FROM "Bestellungen"
WHERE "Versanddatum" > "Lieferdatum";



-- f) Gesucht sind alle Kunden außerhalb Nordamerikas.

SELECT "Firma"
FROM "Kunden"
WHERE "Land" <> 'USA' and "Land" <> 'Kanada'

SELECT "Firma"
FROM "Kunden"
WHERE "Land" not in ('USA', 'Kanada')



-- g) Finden Sie die Nummern aller Bestellungen nach Skandinavien.

SELECT "Bestell-Nr"
FROM "Bestellungen"
WHERE "Bestimmungsland" = 'Finnland' or
      "Bestimmungsland" = 'Schweden' or
      "Bestimmungsland" = 'Norwegen' or
      "Bestimmungsland" = 'Dänemark'

SELECT "Bestell-Nr"
FROM "Bestellungen"
WHERE "Bestimmungsland" in ('Finnland','Schweden','Norwegen','Dänemark')



-- h) Finden Sie alle Bestellungen (Bestell-Nr, Bestelldatum) der Firma „Ernst Handel“, die eine Bestellposition über „Camembert Pierrot“ aufweisen.

SELECT b."Bestell-Nr", "Bestelldatum"
FROM "Bestellungen" b, "Bestelldetails" bs, "Artikel" a, "Kunden" k
WHERE b."Bestell-Nr" = bs."Bestell-Nr" and
      b."Kunden-Code"= k."Kunden-Code" and
      bs."Artikel-Nr" = a."Artikel-Nr" and
      "Firma" = 'Ernst Handel' and
      "Artikelname" = 'Camembert Pierrot'

-- Mit JOIN-Operator
SELECT b."Bestell-Nr", "Bestelldatum"
FROM (("Bestellungen" b JOIN "Bestelldetails" bs ON (b."Bestell-Nr" = bs."Bestell-Nr"))
      JOIN "Artikel" a ON (bs."Artikel-Nr" = a."Artikel-Nr"))
      JOIN "Kunden" k ON (b."Kunden-Code"= k."Kunden-Code")
WHERE "Firma" = 'Ernst Handel' and
      "Artikelname" = 'Camembert Pierrot'



-- i) Finden Sie alle Bestellungen (Bestell-Nr, Bestelldatum), die von Frau Davolio bearbeitet wurden.

SELECT "Bestell-Nr", "Bestelldatum"
FROM "Bestellungen" b, "Personal" p
WHERE b."Personal-Nr" = p."Personal-Nr" and
      "Nachname" = 'Davolio'

-- Mit JOIN-Operator
SELECT "Bestell-Nr", "Bestelldatum"
FROM "Bestellungen" b JOIN "Personal" p ON (b."Personal-Nr" = p."Personal-Nr")
WHERE "Nachname" = 'Davolio'




-- j) Welche Kunden (Firma) haben irgendetwas anderes als Getränke bestellt?

SELECT distinct "Firma"
FROM "Kunden" ku, "Bestellungen" b, "Bestelldetails" bs, "Artikel" a,
     "Kategorien" k
WHERE ku."Kunden-Code" = b."Kunden-Code" and
      b."Bestell-Nr" = bs."Bestell-Nr" and
      bs."Artikel-Nr" = a."Artikel-Nr" and
      a."Kategorie-Nr" = k."Kategorie-Nr" and
      "Kategoriename" <> 'Getränke'

-- Mit JOIN-Operator
SELECT distinct "Firma"
FROM ((("Kunden" ku JOIN "Bestellungen" b ON (ku."Kunden-Code" = b."Kunden-Code"))
      JOIN "Bestelldetails" bs ON (b."Bestell-Nr" = bs."Bestell-Nr"))
      JOIN "Artikel" a ON (bs."Artikel-Nr" = a."Artikel-Nr"))
      JOIN "Kategorien" k ON (a."Kategorie-Nr" = k."Kategorie-Nr")
WHERE "Kategoriename" <> 'Getränke'



-- k) Finden Sie alle Artikel, deren Namen mit C beginnen

SELECT *
FROM "Artikel"
WHERE "Artikelname" like 'C%';



-- l) Finden Sie alle Artikel (Artikelname), die zur gleichen Kategorie gehören wie "Tofu"

SELECT A1."Artikelname"
FROM "Artikel" A1, "Artikel" A2
WHERE A1."Kategorie-Nr" = A2."Kategorie-Nr" and
      A2."Artikelname" = 'Tofu';

-- Mit JOIN-Operator
SELECT A1."Artikelname"
FROM "Artikel" A1 JOIN "Artikel" A2 ON (A1."Kategorie-Nr" = A2."Kategorie-Nr")
WHERE A2."Artikelname" = 'Tofu'


-- m) Welches sind die Lieferanten (Firma) für "Chai", "Tofu" und "Lakkalikööri"?

SELECT "Firma"
FROM "Lieferanten" L, "Artikel" A
WHERE L."Lieferanten-Nr" = A."Lieferanten-Nr" and
      "Artikelname" in ('Chai', 'Tofu', 'Lakkalikööri')

-- Mit JOIN-Operator
SELECT "Firma"
FROM "Lieferanten" L JOIN "Artikel" A ON (L."Lieferanten-Nr" = A."Lieferanten-Nr")
WHERE "Artikelname" in ('Chai', 'Tofu', 'Lakkalikööri')


-- n) Welche Mitarbeiter (Nachname) arbeiten in einem anderen Land als Frau Davolio?

SELECT P1."Nachname"
FROM "Personal" P1, "Personal" P2
WHERE P1."Land" <> P2."Land" and
      P2."Nachname" = 'Davolio' and
      P2."Anrede" = 'Frau';

-- Mit JOIN-Operator
SELECT P1."Nachname"
FROM "Personal" P1 JOIN "Personal" P2 ON (P1."Land" <> P2."Land")
WHERE P2."Nachname" = 'Davolio' and
      P2."Anrede" = 'Frau';



-- o) Finden Sie den teuersten Artikel (Artikelname).

SELECT "Artikelname"
FROM "Artikel"
WHERE "Einzelpreis" >= all (SELECT "Einzelpreis" FROM "Artikel")

SELECT "Artikelname"
FROM "Artikel"
WHERE "Einzelpreis" = (SELECT max("Einzelpreis") FROM "Artikel")



-- p) Gesucht ist der Lieferant (Firma), der den teuersten Artikel liefert.

SELECT "Firma"
FROM "Artikel" A, "Lieferanten" L
WHERE A."Lieferanten-Nr" = L."Lieferanten-Nr" and
	"Einzelpreis" >= all (SELECT "Einzelpreis" FROM "Artikel")

-- Mit JOIN-Operator
SELECT "Firma"
FROM "Artikel" A JOIN "Lieferanten" L ON (A."Lieferanten-Nr" = L."Lieferanten-Nr")
WHERE "Einzelpreis" >= all (SELECT "Einzelpreis" FROM "Artikel")



-- q) Finden Sie den Kunden (Firma), der die Bestellung mit den höchsten Frachtkosten in Auftrag gegeben hat!

SELECT "Firma"
FROM "Kunden" K, "Bestellungen" B
WHERE K."Kunden-Code" = B."Kunden-Code" and
	"Frachtkosten" >= all (SELECT "Frachtkosten" FROM "Bestellungen")

-- Mit JOIN-Operator
SELECT "Firma"
FROM "Kunden" K JOIN "Bestellungen" B ON (K."Kunden-Code" = B."Kunden-Code")
WHERE "Frachtkosten" >= all (SELECT "Frachtkosten" FROM "Bestellungen")



-- r) Finden Sie die Artikel (Artikelname), die überhaupt noch nicht verkauft wurden.

SELECT "Artikelname"
FROM "Artikel"
WHERE "Artikel-Nr" not in (SELECT "Artikel-Nr" FROM "Bestelldetails");


-- s) Finden Sie alle deutschen Firmen (Firma, Kontaktperson, Typ), die Kunde oder Lieferant der Nordwind GmbH sind,
--    und geben den entsprechenden Typ (Typ = Kunde oder Lieferant) mit aus.

(SELECT "Firma", "Kontaktperson", 'Kunde' || "Firma" AS "Typ"
 FROM "Kunden"
 WHERE "Land" = 'Deutschland')
UNION
(SELECT "Firma", "Kontaktperson", 'Lieferant' AS "Typ"
 FROM "Lieferanten"
 WHERE "Land" = 'Deutschland');

SELECT *
From (
   (SELECT "Firma", "Kontaktperson", "Land", 'Kunde' AS "Typ"
    FROM "Kunden")
    UNION
   (SELECT "Firma", "Kontaktperson", "Land", 'Lieferant' AS "Typ"
    FROM "Lieferanten")
) firmen  -- Alias ist für Schachtelung innerhalb der FROM-Klausuel wichtig
WHERE "Land" = 'Deutschland';
