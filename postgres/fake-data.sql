-- ============================================
-- SCRIPT DE GÉNÉRATION DE DONNÉES DE TEST
-- Insurance API - Clients et Contrats
-- ============================================

-- Nettoyage des données existantes
DELETE FROM contracts;
DELETE FROM clients;

-- Réinitialiser les séquences
ALTER SEQUENCE clients_id_seq RESTART WITH 1;
ALTER SEQUENCE contracts_id_seq RESTART WITH 1;

-- ============================================
-- INSERTION DE CLIENTS PERSON
-- ============================================

INSERT INTO clients (client_type, name, email, phone, birthdate, company_identifier) VALUES
                                                                                         ('PERSON', 'Jean Dupont', 'jean.dupont@example.com', '+33612345678', '1985-03-15', NULL),
                                                                                         ('PERSON', 'Marie Curie', 'marie.curie@example.com', '+33623456789', '1990-07-22', NULL),
                                                                                         ('PERSON', 'Pierre Martin', 'pierre.martin@example.com', '+33634567890', '1978-11-30', NULL),
                                                                                         ('PERSON', 'Sophie Bernard', 'sophie.bernard@example.com', '+33645678901', '1995-05-10', NULL),
                                                                                         ('PERSON', 'Lucas Dubois', 'lucas.dubois@example.com', '+33656789012', '1982-09-18', NULL);

-- ============================================
-- INSERTION DE CLIENTS COMPANY
-- ============================================

INSERT INTO clients (client_type, name, email, phone, birthdate, company_identifier) VALUES
                                                                                         ('COMPANY', 'Acme Corporation', 'contact@acme.com', '+33698765432', NULL, 'acm-123'),
                                                                                         ('COMPANY', 'TechStart SAS', 'info@techstart.fr', '+33687654321', NULL, 'tch-456'),
                                                                                         ('COMPANY', 'GreenEnergy SARL', 'contact@greenenergy.fr', '+33676543210', NULL, 'grn-789'),
                                                                                         ('COMPANY', 'BuildCo France', 'admin@buildco.fr', '+33665432109', NULL, 'bld-321'),
                                                                                         ('COMPANY', 'FastDelivery SA', 'support@fastdelivery.com', '+33654321098', NULL, 'fst-654');

-- ============================================
-- CONTRATS POUR JEAN DUPONT (Client ID: 1)
-- ============================================

-- Contrat actif (en cours)
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (1, '2024-01-01', '2025-12-31', 1500.00, '2024-01-01');

-- Contrat actif sans date de fin
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (1, '2023-06-15', NULL, 800.50, '2024-10-15');

-- Contrat expiré
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (1, '2022-01-01', '2023-12-31', 1200.00, '2023-12-31');

-- ============================================
-- CONTRATS POUR MARIE CURIE (Client ID: 2)
-- ============================================

-- Contrat actif récent
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (2, '2024-10-01', '2026-09-30', 2000.00, '2024-10-01');

-- Contrat expiré il y a peu
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (2, '2023-01-01', '2024-06-30', 1500.00, '2024-06-30');

-- ============================================
-- CONTRATS POUR PIERRE MARTIN (Client ID: 3)
-- ============================================

-- Plusieurs contrats actifs
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (3, '2024-01-15', '2025-01-14', 950.75, '2024-09-20'),
                                                                                      (3, '2024-03-01', NULL, 1200.00, '2024-10-10'),
                                                                                      (3, '2024-07-01', '2025-06-30', 750.25, '2024-07-01');

-- Contrats expirés
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (3, '2021-05-01', '2023-04-30', 1100.00, '2023-04-30'),
                                                                                      (3, '2020-01-01', '2022-12-31', 900.00, '2022-12-31');

-- ============================================
-- CONTRATS POUR SOPHIE BERNARD (Client ID: 4)
-- ============================================

-- Un seul contrat actif
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (4, '2024-05-01', NULL, 650.00, '2024-10-18');

-- ============================================
-- CONTRATS POUR LUCAS DUBOIS (Client ID: 5)
-- ============================================

-- Tous les contrats sont expirés
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (5, '2022-01-01', '2023-12-31', 1800.00, '2023-12-31'),
                                                                                      (5, '2023-01-01', '2024-06-30', 1600.00, '2024-06-30');

-- ============================================
-- CONTRATS POUR ACME CORPORATION (Client ID: 6)
-- ============================================

-- Contrats d'entreprise (montants plus élevés)
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (6, '2024-01-01', '2025-12-31', 15000.00, '2024-01-01'),
                                                                                      (6, '2023-07-01', NULL, 8500.00, '2024-10-12'),
                                                                                      (6, '2024-06-01', '2025-05-31', 12000.00, '2024-10-15');

-- Contrat expiré
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
    (6, '2022-01-01', '2023-12-31', 20000.00, '2023-12-31');

-- ============================================
-- CONTRATS POUR TECHSTART SAS (Client ID: 7)
-- ============================================

INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (7, '2024-02-01', '2026-01-31', 5500.00, '2024-02-01'),
                                                                                      (7, '2024-08-15', NULL, 3200.00, '2024-10-19');

-- ============================================
-- CONTRATS POUR GREENENERGY SARL (Client ID: 8)
-- ============================================

-- Mix de contrats actifs et expirés
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (8, '2024-03-01', '2025-02-28', 7800.00, '2024-03-01'),
                                                                                      (8, '2023-01-01', '2024-09-30', 6500.00, '2024-09-30'),
                                                                                      (8, '2022-06-01', '2023-05-31', 5000.00, '2023-05-31');

-- ============================================
-- CONTRATS POUR BUILDCO FRANCE (Client ID: 9)
-- ============================================

INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (9, '2024-01-15', NULL, 18000.00, '2024-10-17'),
                                                                                      (9, '2023-09-01', '2024-08-31', 16000.00, '2024-08-31');

-- ============================================
-- CONTRATS POUR FASTDELIVERY SA (Client ID: 10)
-- ============================================

-- Contrats actifs uniquement
INSERT INTO contracts (client_id, start_date, end_date, cost_amount, update_date) VALUES
                                                                                      (10, '2024-04-01', '2025-03-31', 9500.00, '2024-04-01'),
                                                                                      (10, '2024-09-01', '2025-08-31', 11000.00, '2024-10-18'),
                                                                                      (10, '2024-10-01', NULL, 7500.00, '2024-10-18');
