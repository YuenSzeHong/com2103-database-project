# COM2103 Library Management System (LMS)

本项目为香港恒生大学 COM2103 课程的数据库课程设计，实现了一个基于 Java Swing 的图书馆管理系统。

## 项目结构
- 基于 Java 8 开发，使用 Maven 管理依赖。
- 图形界面采用 Swing，表单由 IntelliJ IDEA GUI Designer 生成。
- 数据库使用 MySQL，数据库名为 `lms`。
- 主要代码目录：
  - `src/main/java/com2103/lmsProject/`：主程序、各功能页面、数据库连接配置。
  - `lms.datadump.17.04.2022.sql`：数据库结构及初始数据。
  - `date.txt`：系统日期配置文件。

## 依赖
- [mysql-connector-java](https://mvnrepository.com/artifact/mysql/mysql-connector-java) 8.0.28
- [IntelliJ forms_rt](https://mvnrepository.com/artifact/com.intellij/forms_rt) 7.0.3

依赖已在 `pom.xml` 配置，使用 Maven 自动下载。

## 数据库设置
1. 创建数据库 `lms`：
   ```mysql
   CREATE DATABASE lms;
   ```
2. 导入数据：
   ```bash
   mysql -u lms -p lms < lms.datadump.17.04.2022.sql
   ```
3. 创建用户并授权：
   ```mysql
   CREATE USER 'lms'@'localhost' IDENTIFIED BY 'lmsroot';
   GRANT ALL PRIVILEGES ON lms.* TO 'lms'@'localhost';
   ```

## 运行方式
1. 使用 IDE（如 IntelliJ IDEA）导入本项目（Maven 项目）。
2. 配置本地 MySQL 数据库，确保用户名和密码与 `src/main/java/com2103/lmsProject/config/DBconn.java` 中一致（默认均为 `lms`/`lmsroot`）。
3. 运行 `LMSMain.java`，启动图形界面。

## 主要功能
- 图书信息管理
- 用户信息管理
- 借阅与归还管理
- 排行榜与统计

## 备注
- 若需修改数据库连接信息，请编辑 `src/main/java/com2103/lmsProject/config/DBconn.java`。
- 系统日期由 `date.txt` 文件维护。

如有问题请联系项目维护者。
