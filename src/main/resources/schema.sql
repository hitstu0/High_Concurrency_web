truncate table User;
truncate table Goods;
truncate table Order;
truncate table GoodsDetail;
create table  if not exists User(
    id int not null auto_increment primary key,
    mobile bigint not null,
    name varchar(8) not null,
    password char(32) not null,
    salt char(10) not null ,
    key (mobile)
);
create table if not exists Order (
    id int not null auto_increment primary key,
    userId int not null,
    goodsId int not null,
    nums int not null,
    key(userId),
    key(goodsId)
);
create table if not exists Goods (
    id int not null auto_increment primary key,
    details int not null,
    name varchar(25) not null,
    price DECIMAL(10,2) not null,
    store int not null,
    image varchar(10) not null,
    UNIQUE KEY(details)
);
create table if not exists GoodsDetail (
    id int not null auto_increment primary key,    
    name varchar(25) not null,
    price DECIMAL(10,2) not null,
    store int not null,
    image varchar(10) not null,
    description varchar(50) not null,
    maker varchar(20) not null    
);