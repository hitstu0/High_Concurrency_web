/*初始化*/
truncate table User;
truncate table GoodsDetail;
truncate table MiaoShaGood;
truncate table MyOrder;


/*正常业务的表*/
create table  if not exists User(
    id int not null auto_increment primary key,
    mobile bigint not null,
    name varchar(8) not null,
    password char(32) not null,
    salt char(10) not null ,
    key (mobile)
);
create table if not exists MyOrder (
    id int not null auto_increment primary key,
    userId int not null,
    goodsId int not null,
    nums int not null,
    payed boolean not null,
    unique key (userId,goodsId),
    key(userId),
    key(goodsId)
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
/*流水表用于记录支付动作*/
create table if not exists RunningWater (
    id int not null auto_increment primary key,
    userId int not null ,
    goodsId int not null
);
/*秒杀业务表*/
create table if not exists MiaoShaGood (
   id int not null auto_increment primary key,
   goodsId int not null,
   nums int not null,
   price DECIMAL(10,2) not null,
   beginTime datetime not null,
   endTime datetime not null,
   key(goodsId)
);

insert into GoodsDetail(name,price,store,image,description,maker) 
values ("phone",1,100,"none","none","none");

insert into MiaoShaGood(goodsId,nums,price,beginTime,endTime)
values (1,100,1,'2022-01-01 00:00:00','2022-03-03 00:00:00'); 