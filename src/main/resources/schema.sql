create table  if not exists User(
    id int not null auto_increment primary key,
    mobile bigint not null,
    name varchar(8) not null,
    password char(32) not null,
    salt char(10) not null ,
    key mobile
);