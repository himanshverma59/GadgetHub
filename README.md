"# GadgetHub - E-Commerce Website (JSP-Servlet)" 
# 📦 GadgetHub - E-Commerce Website (JSP-Servlet)

GadgetHub is a fully functional **e-commerce platform** built using **JSP, Servlets, JDBC, and MySQL**. It follows the **MVC architecture** and provides user authentication, product management, and email notifications.

---

## 🚀 Features  
- User Registration & Login  
- Product Browsing & Shopping Cart  
- Admin Dashboard for Product Management  
- Email Notifications (JavaMail API)  
- Secure Database Connectivity (JDBC & DAO Pattern)  

---

## 🔧 Setup Guide  

### **1️⃣ Install Required Software**  
Before running the project, ensure you have:  
✅ **Java 8** (JDK 1.8) - [Download](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)  
✅ **Apache Tomcat 8.x** - [Download](https://tomcat.apache.org/download-80.cgi)  
✅ **MySQL Server 8.x** - [Download](https://dev.mysql.com/downloads/mysql/)  

---

### **2️⃣ Database Setup (`GadgetHub.sql`)**  
1️⃣ Open **MySQL Workbench** or **phpMyAdmin**.  
2️⃣ Create a new database:  
   ```sql
   CREATE DATABASE gadgethub;
3️⃣ Import GadgetHub.sql from the database folder:

sh
Copy
Edit
mysql -u root -p gadgethub < database/GadgetHub.sql
4️⃣ Update Database Credentials in web.xml

Open WebContent/WEB-INF/web.xml
Change the MySQL connection details:
xml
Copy
Edit
<context-param>
    <param-name>url</param-name>
    <param-value>jdbc:mysql://localhost:3306/gadgethub</param-value>
</context-param>
<context-param>
    <param-name>username</param-name>
    <param-value>root</param-value>
</context-param>
<context-param>
    <param-name>password</param-name>
    <param-value>YOUR_PASSWORD_HERE</param-value>
</context-param>
3️⃣ Add Required JAR Files
📁 Place the following JAR files in WebContent/WEB-INF/lib/:

JAR File	Purpose
mysql-connector-java-8.0.xx.jar	MySQL JDBC Driver
javax.mail.jar	JavaMail API for email notifications
activation.jar	Required for JavaMail API
servlet-api.jar	Java Servlets support (comes with Tomcat)
4️⃣ Run the Project
1️⃣ Open Apache Tomcat and add the project in Tomcat Manager.
2️⃣ Deploy and start the server.
3️⃣ Open your browser and visit:

arduino
Copy
Edit
http://localhost:8080/GadgetHub/LandingServlet


run landingServlet