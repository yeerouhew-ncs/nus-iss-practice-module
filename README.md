# Ticket Booking System
## tbs-api
1. Open using intelliJ.
2. Right click pom.xml, select 'Add as Maven Project'.

## Database (Local)
1. Go to SQL Server Management Studio, connect to Database Engine
2. Choose your server name
3. Choose 'Windows Authentication' as Authentication and click 'Connect'
4. Right click on 'Databases' and Select 'New Database'
   a. Create a database named 'TBSDB'.
5. Go under Security > Logins, right click and select 'New Login'
   a. Enter 'user' as Login Name
   b. Select SQL Server Authentication
   c. Enter 'password' as Password
   d. Uncheck the checkbox for 'Enforce password policy', 'Enforce password expiration', 'User must change password at next login'
   e. Go to User Mapping tab, select 'TBSDB' and select 'db owner'
8. Go to tbs-api/src/main/resources/application.yml, make sure your username and password is correct.
9. Maven run TbsApiApplication

## tbs-frontend
1. Run npm install
2. Run npm start
