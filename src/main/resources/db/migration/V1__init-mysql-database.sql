
    drop table if exists customer;

    drop table if exists product;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table product (
        price decimal(38,2) not null,
        product_style tinyint not null check (product_style between 0 and 9),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        product_name varchar(50) not null,
        upc varchar(200) not null,
        primary key (id)
    ) engine=InnoDB;
