/**
 * Author:  lukas
 */

-- Aufgabe 3 DDL

-- (a) Erweiterung des DB-Schemas
CREATE TABLE Lager (
    Abteilung INTEGER PRIMARY KEY,
    Kapazitaet INTEGER NOT NULL CHECK (Kapazitaet >= 100 AND Kapazitaet <= 10000),
    Verantwortlicher INTEGER REFERENCES "Personal"("Personal-Nr")
);
CREATE TABLE KategorieLager (
    Abteilung INTEGER PRIMARY KEY REFERENCES Lager(Abteilung),
    Kategorie INTEGER UNIQUE REFERENCES "Kategorien"("Kategorie-Nr")
                ON DELETE SET NULL
);
CREATE TABLE ArtikelLager (
    Artikel INTEGER PRIMARY KEY REFERENCES "Artikel"("Artikel-Nr"),
    Abteilung INTEGER NOT NULL REFERENCES Lager(Abteilung),
    Mindestbestand INTEGER NOT NULL,
    BestellteEinheiten INTEGER NOT NULL,
    Lagerbestand INTEGER NOT NULL
);

-- (c) Füllen der Tabellen
INSERT INTO Lager VALUES
    (1, 500, NULL),
    (2, 1000, NULL),
    (3, 500, NULL),
    (4, 400, NULL),
    (5, 300, NULL),
    (6, 300, NULL),
    (7, 400, NULL),
    (8, 700, NULL)

INSERT INTO KategorieLager (
    SELECT "Kategorie-Nr", "Kategorie-Nr"
    FROM "Kategorien"
)

INSERT INTO ArtikelLager (
    SELECT "Artikel-Nr", Abteilung, "Mindestbestand", "BestellteEinheiten", "Lagerbestand"
    FROM "Artikel" A, KategorieLager KL
    WHERE A."Kategorie-Nr" = KL.Kategorie
)

INSERT INTO Lager VALUES (9, 99, NULL) 
-- muss fehlschlagen, da die CHECK-Bedingung den Wertebereich auf 100 <= x <= 10000 einschränkt. 

-- (c) Assertion
CREATE ASSERTION KeinKapazitaetsueberlauf CHECK (
    NOT EXISTS (
        SELECT L.Abteilung, L.Kapazitaet
        FROM Lager L, ArtikelLager AL
        WHERE L.Abteilung = AL.Abteilung
        GROUP BY L.Abteilung, L.Kapazitaet
        HAVING SUM(AL.Lagerbestand) > L.Kapazitaet
    )
);

SELECT L.Abteilung, L.Kapazitaet
FROM Lager L, ArtikelLager AL
WHERE L.Abteilung = AL.Abteilung
GROUP BY L.Abteilung, L.Kapazitaet
HAVING SUM(AL.Lagerbestand) > L.Kapazitaet
-- --> Abteilung 1, 5 und 8 

