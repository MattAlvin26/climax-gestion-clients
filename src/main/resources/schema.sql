DROP TABLE IF EXISTS clients;

CREATE TABLE clients (
  id_client INT AUTO_INCREMENT  PRIMARY KEY,
  nom_client VARCHAR(250) NOT NULL,
  prenom_client VARCHAR(250) NOT NULL,
  profession VARCHAR(250) NOT NULL,
  niveau_revenu Int NOT NULL
);

INSERT INTO clients (nom_client, prenom_client, profession,niveau_revenu) VALUES
('Aliko', 'Dangote', 'Industrialist',35),
('Bill', 'Gates', 'Tech Entrepreneur',40),
('Folrunsho', 'Alakija', 'Oil Magnate',45);