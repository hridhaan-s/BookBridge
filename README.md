# BookBridge

A Java Swing + MySQL desktop application developed as a CBSE Class XII IT project.

Click here to view the Project file: https://user-cdn.hackclub-assets.com/019f0fe4-eb67-7b17-8c3c-e7756f2c3d11/Project%20File%20Class%2012%20IT%20Final%20.pdf


<img width="1102" height="597" alt="Screenshot 2026-06-26 164649" src="https://github.com/user-attachments/assets/9f878f1e-7d79-46da-bc29-0423b7dd7f1e" />
<img width="390" height="283" alt="Screenshot 2026-06-26 164458" src="https://github.com/user-attachments/assets/a71d3b07-8dc1-4ba6-aacd-509210659554" />

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


<img width="390" height="283" alt="Screenshot 2026-06-26 164458" src="https://github.com/user-attachments/assets/68d50753-391a-4c4c-b74c-e617f20c6f8b" />
<img width="390" height="283" alt="Screenshot 2026-06-26 164458" src="https://github.com/user-attachments/assets/6c1d7479-1b0d-4729-a091-8bd1be1b4d1a" />
 Quick Setup

## Requirements

* Java JDK 17 or later (Tested on JDK 24)
* MySQL 8.0
* MySQL Connector/J
* Windows

## Database Setup

Open MySQL Command Line Client and run:

```sql
source sql/schema.sql;
```

This creates:

* bookbridge database
* users table
* books table
* sample data

## Configure Database

Open:

```
src/bookbridge/DBConnection.java
```

Update:

```java
private static final String USER = "root";
private static final String PASSWORD = "YOUR_PASSWORD";
```

## Run

Double-click `RUN.bat`

or

```bash
javac -cp ".;lib/mysql-connector-j-9.7.0.jar" src/bookbridge/*.java
java -cp ".;src;lib/mysql-connector-j-9.7.0.jar" bookbridge.LoginFrame
```

Demo Login

```
Username: admin
Password: admin123
```
