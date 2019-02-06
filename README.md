# Issue-tracker-with-db

This project contains API endpoints, database design for Issue Tracker.

# API provides end-point for the following.

1. Total number of open issues
2. Number of open issues that were opened in the last 24 hours
3. Number of open issues that were opened more than 24 hours ago but less than 7 days ago
4. Number of open issues that were opened more than 7 days ago 

# Technology Used as follows.

1. Programming Language - Java 8
2. Framework - Spring Boot
3. Database - Cassandra
4. API Documentation - Swagger
5. Other Libraries - Lombok to reduce boiler plate code

# Database schema 

CREATE KEYSPACE issue_tracker WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE issue_tracker.issues (
    issue_id text,
    issue_time text,
    description text,
    status text,
    PRIMARY KEY (issue_id, issue_time)
) WITH CLUSTERING ORDER BY (issue_time ASC);

# Insert queries

CREATE KEYSPACE issue_tracker WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE issue_tracker.issues (
    issue_id text,
    issue_time text,
    description text,
    status text,
    PRIMARY KEY (issue_id, issue_time)
) WITH CLUSTERING ORDER BY (issue_time ASC);


insert into issue_tracker.issues (issue_id , issue_time , description , status ) VALUES ('124', '2019-02-04 19:37', 'description 1', 'OPENED' );

insert into issue_tracker.issues (issue_id , issue_time , description , status ) VALUES ('124A', '2019-02-04 19:37', 'description 2', 'CLOSED' );

insert into issue_tracker.issues (issue_id , issue_time , description , status ) VALUES ('124B', '2019-02-03 19:37', 'description 3', 'OPENED' );

insert into issue_tracker.issues (issue_id , issue_time , description , status ) VALUES ('124C', '2019-01-017 19:37', 'description 4', 'OPENED' );

# Swagger API Documentation 

http://localhost:8080/swagger-ui.html

Attached postman test file for referene
