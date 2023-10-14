CREATE TABLE if not exists product (
    id CHAR PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    price INT,
    quantity INT,
    vending_machine_id CHAR,
    FOREIGN KEY (vending_machine_id) REFERENCES vending_machine(id)
);

CREATE TABLE if not exists vending_machine (
    id CHAR PRIMARY KEY,
    inserted_money INT,
    temperature FLOAT,
    balance INT
);