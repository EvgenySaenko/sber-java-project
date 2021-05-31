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
('Cake',450.0),
('Chocolade',100.0),
('Chicken',170.0),
('Potato',70.0),
('Cherry',180.0),
('Apple',70.0),
('Tomato',130.0),
('Banana',80.0),
('Lime',60.0),
('Mango',320.0);