INSERT IGNORE INTO vending_machine (id, inserted_money, temperature, balance) VALUES ('f9fa1000-eebc-4e5a-b1a5-8ce0de6fa682', 0, 25.0, 0);
INSERT IGNORE INTO product (id, name, price, quantity, vending_machine_id) VALUES (1, 'Water', 25, 0, 'f9fa1000-eebc-4e5a-b1a5-8ce0de6fa682');
INSERT IGNORE INTO product (id, name, price, quantity, vending_machine_id) VALUES (2, 'Coke', 35, 0, 'f9fa1000-eebc-4e5a-b1a5-8ce0de6fa682');
INSERT IGNORE INTO product (id, name, price, quantity, vending_machine_id) VALUES (3, 'Soda', 45, 0, 'f9fa1000-eebc-4e5a-b1a5-8ce0de6fa682');