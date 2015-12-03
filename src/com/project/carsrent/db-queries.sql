create database carproject;
use carproject;

create table test (client_id int primary key auto_increment, login varchar(255) not null, password varchar(255) not null, name varchar(255) not null,  family_name varchar(255) not null);

insert into client values(0,'test','password','Anna','Smith');
insert into client values(0,'admin','password2','John','Blueberry');
create table car (car_id int primary key auto_increment, model varchar(255) not null, year int not null, price int not null);

insert into car values(0,'bmw',2010,100);
insert into car values(0,'mercedes',2014, 90);
insert into car values(0,'volvo',2011, 95);
insert into car values(0,'ferrari',2009, 120);
create table order_table (order_id int primary key auto_increment, client_id int not null, car_id int not null, hour int not null, price int not null);