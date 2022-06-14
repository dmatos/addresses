
# Setup MySQL
create database db_example;
create user 'dbuser'@'%' identified by 'testE!1234';
grant all on db_example.* to 'dbuser'@'%';

# Run server
./mvnw spring-boot:run