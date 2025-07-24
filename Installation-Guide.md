1) Install Prerequisites
Java 21 (for backend)
Eclipse IDE for enterprise Java and web developers
(JAVA_HOME is set to C:\path-to-jdk21\bin and Ensure eclipse.ini has -vm C:\path-to-jdk21 (JAVA_HOME) specifed in it.)

Node.js & npm (for frontend running)
PostgreSQL (for the database) with pg admin4 (for its UI + Script running) with its command line usage
=>from https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
While installation if asked- port:5432


Open cmd with admin access-> 
net start postgresql-x64-17 (or -16)
(If unsuccessful, check services.msc the service might already been running)

cd postgresql\bin
...\postgresql\bin>psql -U postgres
Password for user postgres:password
(the password set at installing Postgres)

Create Database and User- 
CREATE DATABASE petition_db;

Import Tables-
From postgresql/bin directory, run:
psql -U postgres -d petition_db -f D:/your path/to/cw2_tdg6/database/tdg6.sql
(If syntax error anywhere check for spaces or ; in command)

FOR VERIFYING TABLES- run:
\c petition_db
\dt

You should see-
app_user (Stores user information)
petition (Stores petitions)
signature (Stores petition signatures)

If unsuccesful with Import tables- 
CREATE USER slpp_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON SCHEMA PUBLIC TO slpp_user;
From postgresql/bin directory, run:
psql -U slpp_user -d petition_db -f D:/your path/to/cw2_tdg6/database/tdg6.sql


---- IGRNORE THIS IF TABLES IMPORTED ----
IF ALL THESE INSTRUCTIONS FAIL TO EXECUTE ANYHOW(tables are missing) 
, CREATE THE ABOVE 3 TABLES AND refer applications.properties in slpp-backend folder

-- Users Table
CREATE TABLE app_user (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  biometric_id VARCHAR(100) UNIQUE NOT NULL,
  date_of_birth DATE NOT NULL,
  role VARCHAR(20) NOT NULL
);

-- Petitions Table
CREATE TABLE petition (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  text TEXT NOT NULL,
  response TEXT,
  signatures INTEGER DEFAULT 0,
  status VARCHAR(20) DEFAULT 'open',
  creator_id INTEGER REFERENCES app_user(id)
);

-- Signatures Table
CREATE TABLE signature (
  id SERIAL PRIMARY KEY,
  petition_id INTEGER REFERENCES petition(id),
  user_id INTEGER REFERENCES app_user(id)
);

-- IGRNORE UPTO THIS IF TABLES IMPORTED --


2)BACKEND SETUP (Sprng MVC and Spring Boot)-
 
Following dependencies are used with Spring MVC- 
Spring Web
Spring Data JPA
PostgreSQL Driver (or MySQL Driver if you prefer MySQL)
Spring Security (for authentication)



Check configured application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/petition_db
spring.datasource.username=slpp_user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

Run Backend- PetitionApplication.java in com.example.petition

If this doesn't run, use Maven- Update Project- Force Update of Snapshots/Releases
(for errors like- failed to configure a DataSource: 'url' attribute is not specified )

Backend runs on http://localhost:8080



3) ANGULAR SETUP
Open VS code
cd slpp-frontend

# Angular core and CLI

npm config get prefix
This will output something like:
Windows: C:\Users\<YourUsername>\AppData\Roaming\npm
macOS/Linux: /usr/local
Add this path to System Environment variables 

npm install -g @angular/cli@latest

npm install
npm install ng2-charts@5.0.0 chart.js@4.4.0 --legacy-peer-deps (or --force if it doesnt wok)
npm install bootstrap@5.3.0 --legacy-peer-deps
npm install @zxing/ngx-scanner@17.0.0 --legacy-peer-deps


ng serve (from slpp-frontend)
Frontend runs on http://localhost:4200


4) Run functionalities and check logs for ref 