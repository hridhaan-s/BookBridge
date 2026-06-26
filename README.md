# BookBridge

A Java Swing + MySQL desktop application developed as a CBSE Class XII IT project.

## Features

- User Login
- Donate Books
- Browse Books
- Reserve Books
- MySQL Database Integration

## Technologies

- Java Swing
- JDBC
- MySQL 8.0
- Apache NetBeans

## Run

Import the SQL database.

Compile:

```bash
javac -cp ".;lib/mysql-connector-j-9.7.0.jar" src/bookbridge/*.java
```

Run:

```bash
java -cp ".;src;lib/mysql-connector-j-9.7.0.jar" bookbridge.LoginFrame
```
