# com2103-database-project
database project for com2103 in Hang Seng University Of Hong Kong

## set up
create a database named `lms` in your selected DBMS
```mysql
CREATE DATABASE lms;
```

then import the data dump included \
file name should like this
`lms.datadump.15.03.2022.sql`\
to be changed
```bash
mysql -u lms -p lms < lms.datadump.<date in dd.mm.yyyy>.sql
```
create a user named `lms` with password `lmsroot` \
then grant that account with permissions. 
```mysql
CREATE USER 'lms'@'localhost' IDENTIFIED BY 'lmsroot';
GRANT ALL PRIVILEGES ON lms . * TO 'lms'@'localhost';
```

