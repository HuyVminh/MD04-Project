CREATE DATABASE project_module04;
use project_module04;

-- ------------------CATEGORY----------------------
create table category
(
    id          int primary key auto_increment,
    name        varchar(255) not null,
    description varchar(255),
    status      boolean default true
);

delimiter //
create procedure PROC_SHOW_CATEGORY()
begin
    select * from category;
end;

create procedure PROC_FIND_CATEGORY_BY_ID(in find_id int)
begin
    select * from category where id = find_id;
end;

create procedure PROC_FIND_CATEGORY_BY_NAME(in find_name varchar(100))
begin
    select * from category where name like concat('%',find_name,'%');
end;

create procedure PROC_ADD_CATEGORY(in c_name varchar(255), in c_description varchar(255))
begin
    insert into category(name, description) values(c_name,c_description);
end;

create procedure PROC_UPDATE_CATEGORY(in update_id int,in c_name varchar(255), in c_description varchar(255),in c_status boolean)
begin
    update category set name = c_name, description = c_description, status = c_status where id = update_id;
end;
//
-- --------------------------------------------------------

create table product
(
    id          int primary key auto_increment,
    name        varchar(255),
    description varchar(255),
    price       float   default not null,
    category_id int not null,
    foreign key (category_id) references category (id),
    status      boolean default true
);