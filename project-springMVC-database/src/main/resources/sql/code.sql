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
    select * from category where name like concat('%', find_name, '%');
end;

create procedure PROC_ADD_CATEGORY(in c_name varchar(255), in c_description varchar(255))
begin
    insert into category(name, description) values (c_name, c_description);
end;

create procedure PROC_UPDATE_CATEGORY(in update_id int, in c_name varchar(255), in c_description varchar(255),
                                      in c_status boolean)
begin
    update category set name = c_name, description = c_description, status = c_status where id = update_id;
end;

create procedure PROC_BLOCK_CATEGORY(in b_id int)
begin
    UPDATE category
    SET status = IF(status = true, false, true)
    WHERE id = b_id;
end
//
-- --------------------------------------------------------

-- ---------------------PRODUCT----------------------------
create table product
(
    id          int primary key auto_increment,
    name        varchar(255) not null unique,
    description text,
    price       float        not null,
    stock       int          not null,
    category_id int          not null,
    foreign key (category_id) references category (id),
    status      boolean default true
);

CREATE TABLE image
(
    image_id   INT PRIMARY KEY auto_increment,
    product_id INT,
    image_url  VARCHAR(255) not null,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

delimiter //
create procedure PROC_SHOW_PRODUCT()
begin
    select * from product order by id desc;
end;

create procedure PROC_FIND_PRODUCT_BY_ID(in find_id int)
begin
    select * from product where id = find_id;
end;

create procedure PROC_FIND_PRODUCT_BY_NAME(in find_name varchar(100))
begin
    select * from product where lcase(name) like concat('%', find_name, '%');
end;

create procedure PROC_ADD_PRODUCT(
    in p_name varchar(255),
    in p_description text,
    in p_price float,
    in p_stock int,
    in p_category_id int
)
begin
    insert into product(name, description, price, stock, category_id)
    values (p_name, p_description, p_price, p_stock, p_category_id);
end;

create procedure PROC_UPDATE_PRODUCT(
    in id_update int,
    in p_name varchar(255),
    in p_description text,
    in p_price float,
    in p_stock int,
    in p_category_id int
)
begin
    update product
    set name        = p_name,
        description = p_description,
        price       = p_price,
        stock       = p_stock,
        category_id = p_category_id
    where id = id_update;
end;

create procedure PROC_BLOCK_PRODUCT(in b_id int)
begin
    UPDATE product
    SET status = IF(status = true, false, true)
    WHERE id = b_id;
end

//
-- --------------------------------------------------------

-- ------------------------USER----------------------------
create table user
(
    id       int primary key auto_increment,
    username varchar(255) not null unique,
    fullname varchar(255),
    email    varchar(255) not null unique,
    password varchar(255) not null,
    avatar   varchar(255),
    address  text,
    phone    varchar(255),
    role     boolean default true,
    status   boolean default true
);

create table cart
(
    id         int primary key auto_increment,
    user_id    int,
    foreign key (user_id) references user (id),
    product_id int,
    foreign key (product_id) references product (id),
    quantity   int default 0
);

delimiter //
create procedure PROC_SHOW_USER()
begin
    select * from user where role = true;
end;

create procedure PROC_FIND_USER_BY_ID(in user_id int)
begin
    select * from user where id = user_id and role = true;
end;

create procedure PROC_ADD_USER(in u_name varchar(255),
                               in u_email varchar(255),
                               in u_pass varchar(255),
                               in u_phone varchar(255))
begin
    insert into user(username, email, password, phone) values (u_name, u_email, u_pass, u_phone);
end
//