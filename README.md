# Ticket Booking System
## tbs-api (Backend) 
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

## tbs-frontend (Frontend)
1. Run npm install
2. Run npm start 

## Download and Set Up RabbitMQ Locally (Windows) 
1. Ensure that choco is installed locally in your machine (If not, refer to this link: https://chocolatey.org/install
2. After choco is installed, use the following command in your powershell:
choco install rabbitmq
if not use the rabbitmq installer: https://www.rabbitmq.com/docs/install-windows
3. Download Erlang : https://www.erlang.org/downloads
