CREATE TABLE  departments(id SERIAL PRIMARY KEY,name VARCHAR,description VARCHAR,no_OfEmployees int);

CREATE TABLE news(id SERIAL PRIMARY KEY ,content VARCHAR,department_id INTEGER);

CREATE TABLE  users(id SERIAL PRIMARY KEY ,name VARCHAR,position VARCHAR,roles VARCHAR);

CREATE TABLE departments_users(id SERIAL PRIMARY KEY ,users_id INTEGER,department_id INTEGER);

CREATE DATABASE organization_api_test WITH TEMPLATE organization;
