# com2103-database-project
database project for com2103 in Hang Seng University Of Hong Kong

## database set up
create a database named `lms` in your selected DBMS (MySQL or MariaDB)
```mysql
CREATE DATABASE lms;
```

then import the data dump included \
file name should like this
`lms.datadump.dd.mm.yyyy.sql`\
latest `lms.datadump.14.04.2022.sql`
```bash
mysql -u lms -p lms < lms.datadump.14.04.2022.sql
```
create a user named `lms` with password `lmsroot` \
then grant that account with permissions. 
```mysql
CREATE USER 'lms'@'localhost' IDENTIFIED BY 'lmsroot';
GRANT ALL PRIVILEGES ON lms . * TO 'lms'@'localhost';
```

