create table product
(
    product_number VARCHAR not null,
    type           VARCHAR not null,
    selling_status VARCHAR not null,
    name           VARCHAR not null,
    price          INTEGER not null
);
insert into product(product_number, type, selling_status, name, price)
values ('001', 'HANDMADE', 'SELLING', '아메리카노', 4000),
       ('002', 'HANDMADE', 'HOLD', '카페라떼', 4500),
       ('003', 'BAKERY', 'STOP_SELLING', '크루아상', 3500);