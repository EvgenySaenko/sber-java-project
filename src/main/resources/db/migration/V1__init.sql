
drop table if exists users;
create table users (
   id                    bigserial primary key,
   username              VARCHAR(30) NOT NULL UNIQUE,
   password              VARCHAR(80) NOT NULL,
   first_name            VARCHAR(255) NOT NULL,
   last_name             VARCHAR(255),
   email                 VARCHAR(50) UNIQUE,
   phone                 VARCHAR(255) NOT NULL UNIQUE,
   activation_code       VARCHAR(255),
   created_at            timestamp default current_timestamp,
   updated_at            timestamp default current_timestamp
);


drop table if exists roles;
create table roles (
   id                    bigserial primary key,
   name                  VARCHAR(50) not null UNIQUE,
   created_at            timestamp default current_timestamp,
   updated_at            timestamp default current_timestamp
);


drop table if exists users_roles;
create table users_roles (
   user_id               bigint NOT NULL references users (id),
   role_id               bigint NOT NULL references roles (id),
   primary key (user_id, role_id)
);


insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');


insert into users (username, password, first_name, last_name, email, phone)
values
('bob','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','Bobby','Fischer','bob@gmail.com','8-988-555-35-35'),
('john','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','Jhon','Connor','john@gmail.com','8-904-222-33-77');


insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);


create table products (
     id                bigserial primary key,
     title             varchar(255),
     price             numeric(8, 2),
     created_at        timestamp default current_timestamp,
     updated_at        timestamp default current_timestamp
);


insert into products(title, price)
values
('Bread', 24.0),
('Milk',120.0),
('Hamburger',170.0),
('Marmalade',120.0),
('Butter',100.0),
('Sugar',55.0),
('Mineral water',55.0),
('Cake',450.0),
('Chocolade',100.0),
('Chicken',170.0),
('Potato',70.0),
('Cherry',180.0),
('Apple',70.0),
('COCA_COLA',130.0),
('Fruit juice',130.0),
('PEPSI',130.0),
('Hot chocolate',130.0),
('Tomato',130.0),
('Lemonade',130.0),
('Tomato',130.0),
('Banana',80.0),
('Lime',60.0),
('Mango',320.0);



drop table if exists orders cascade;
create table orders (
     id                         bigserial primary key,
     user_id                    bigint not null,
     price                      numeric(8, 2) not null,
     address                    varchar (255) not null,
     email                      varchar(30) not null,
     created_at                 timestamp default current_timestamp,
     updated_at                 timestamp default current_timestamp,
     constraint fk_user_id foreign key (user_id) references users (id)
);


drop table if exists order_items cascade;
create table order_items (
     id                        bigserial primary key,
     order_id                  bigint not null,
     product_id                bigint not null,
     quantity                  int,
     price_per_product         numeric(8, 2),
     price                     numeric(8, 2),
     created_at                timestamp default current_timestamp,
     updated_at                timestamp default current_timestamp,
     constraint fk_prod_id foreign key (product_id) references products (id),
     constraint fk_order_id foreign key (order_id) references orders (id)
);



create table carts (
    id                         UUID primary key,
    owner_id                   bigint references users (id),
    price                      numeric(8, 2)
);



create table cart_items (
    id                         bigserial primary key,
    cart_id                    UUID references carts (id),
    product_id                 bigint references products(id),
    title                      varchar(255),
    quantity                   int,
    price_per_product          numeric(8, 2),
    price                      numeric(8, 2),
    created_at                 timestamp default current_timestamp,
    updated_at                 timestamp default current_timestamp
);



