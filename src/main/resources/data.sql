-- ============================================
-- PHARMACY SUPPLY SYSTEM - SAMPLE DATA
-- Practical Examination Test Data
-- ============================================

-- Clear existing data (optional - comment out if not needed)
-- DELETE FROM supplier_drug;
-- DELETE FROM drugs;
-- DELETE FROM suppliers;
-- DELETE FROM categories;
-- DELETE FROM users;
-- DELETE FROM locations;

-- ============================================
-- 1. INSERT CATEGORIES (One-to-Many parent)
-- ============================================
INSERT INTO categories (id, name) VALUES
(1, 'Antibiotics'),
(2, 'Pain Relievers'),
(3, 'Vitamins'),
(4, 'Antihistamines'),
(5, 'Antacids');

-- ============================================
-- 2. INSERT LOCATIONS (hierarchical self-referencing table)
-- ============================================
-- provinces
INSERT INTO locations (id, name, code, type, parent_id) VALUES
(1, 'Kigali City', 'KC', 'PROVINCE', NULL),
(2, 'Eastern Province', 'EP', 'PROVINCE', NULL);

-- districts
INSERT INTO locations (id, name, code, type, parent_id) VALUES
(3, 'Nyarugenge', 'NY', 'DISTRICT', 1),
(4, 'Gasabo', 'GA', 'DISTRICT', 1),
(5, 'Rwamagana', 'RW', 'DISTRICT', 2);

-- sectors
INSERT INTO locations (id, name, code, type, parent_id) VALUES
(6, 'Muhima', 'MH', 'SECTOR', 3),
(7, 'Kacyiru', 'KCY', 'SECTOR', 3),
(8, 'Kigali Sector', 'KGS', 'SECTOR', 5);

-- cells
INSERT INTO locations (id, name, code, type, parent_id) VALUES
(9, 'Muhima Cell', 'MC', 'CELL', 6),
(10, 'Cell2', 'C2', 'CELL', 7);

-- villages
INSERT INTO locations (id, name, code, type, parent_id) VALUES
(11, 'Village One', 'V1', 'VILLAGE', 9),
(12, 'Village Two', 'V2', 'VILLAGE', 10);

-- ============================================
-- 3. INSERT USERS (One-to-One with village Locations)
-- ============================================
INSERT INTO users (id, name, email, location_id) VALUES
(1, 'Shema Ken', 'shema.ken@auca.ac.rw', 11),
(2, 'Alice Mukamana', 'alice@example.com', 11),
(3, 'Bob Nkusi', 'bob@example.com', 12);

-- ============================================
-- 4. INSERT DRUGS (Many-to-One with Categories)
-- ============================================
INSERT INTO drugs (id, name, price, category_id) VALUES
-- Antibiotics (category_id = 1)
(1, 'Amoxicillin 500mg', 15.99, 1),
(2, 'Penicillin 250mg', 18.50, 1),
(3, 'Ciprofloxacin 500mg', 22.00, 1),
(4, 'Azithromycin 250mg', 25.99, 1),

-- Pain Relievers (category_id = 2)
(5, 'Aspirin 500mg', 9.99, 2),
(6, 'Ibuprofen 400mg', 11.99, 2),
(7, 'Paracetamol 500mg', 7.50, 2),
(8, 'Naproxen 250mg', 13.99, 2),

-- Vitamins (category_id = 3)
(9, 'Vitamin C 1000mg', 12.50, 3),
(10, 'Vitamin D3 2000IU', 14.99, 3),
(11, 'Multivitamin Complex', 19.99, 3),
(12, 'Vitamin B12 1000mcg', 16.50, 3),

-- Antihistamines (category_id = 4)
(13, 'Cetirizine 10mg', 8.99, 4),
(14, 'Loratadine 10mg', 9.50, 4),
(15, 'Diphenhydramine 25mg', 7.99, 4),

-- Antacids (category_id = 5)
(16, 'Omeprazole 20mg', 17.99, 5),
(17, 'Ranitidine 150mg', 12.99, 5),
(18, 'Calcium Carbonate 500mg', 6.99, 5);

-- ============================================
-- 5. INSERT SUPPLIERS (Many-to-Many with Drugs)
-- ============================================
INSERT INTO suppliers (id, name, contact_email) VALUES
(1, 'MedSupply Rwanda Ltd', 'contact@medsupply.rw'),
(2, 'PharmaCorp International', 'info@pharmacorp.com'),
(3, 'HealthPlus Distributors', 'sales@healthplus.rw'),
(4, 'Global Pharma Solutions', 'support@globalpharma.com'),
(5, 'Rwanda Medical Supplies', 'orders@rms.rw');

-- ============================================
-- 6. INSERT SUPPLIER_DRUG (Many-to-Many Join Table)
-- ============================================
INSERT INTO supplier_drug (supplier_id, drug_id) VALUES
-- MedSupply Rwanda supplies multiple drugs
(1, 1), (1, 2), (1, 5), (1, 6), (1, 9), (1, 10),

-- PharmaCorp International supplies different drugs
(2, 1), (2, 3), (2, 4), (2, 7), (2, 8), (2, 13), (2, 14),

-- HealthPlus Distributors
(3, 5), (3, 6), (3, 9), (3, 11), (3, 12), (3, 15),

-- Global Pharma Solutions
(4, 3), (4, 4), (4, 8), (4, 13), (4, 16), (4, 17), (4, 18),

-- Rwanda Medical Supplies
(5, 1), (5, 2), (5, 7), (5, 10), (5, 11), (5, 14), (5, 15), (5, 16);

-- ============================================
-- Reset sequences to continue from last ID
-- ============================================
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('locations_id_seq', (SELECT MAX(id) FROM locations));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('drugs_id_seq', (SELECT MAX(id) FROM drugs));
SELECT setval('suppliers_id_seq', (SELECT MAX(id) FROM suppliers));

-- ============================================
-- VERIFICATION QUERIES
-- ============================================

-- Count records in each table
-- SELECT 'Categories' as table_name, COUNT(*) as count FROM categories
-- UNION ALL
-- SELECT 'Locations', COUNT(*) FROM locations
-- UNION ALL
-- SELECT 'Users', COUNT(*) FROM users
-- UNION ALL
-- SELECT 'Drugs', COUNT(*) FROM drugs
-- UNION ALL
-- SELECT 'Suppliers', COUNT(*) FROM suppliers
-- UNION ALL
-- SELECT 'Supplier-Drug Relations', COUNT(*) FROM supplier_drug;
