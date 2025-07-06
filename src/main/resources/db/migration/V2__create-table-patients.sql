CREATE TABLE IF NOT EXISTS patients(
    id bigint not null auto_increment,
    name varchar(100) not null,
    avatar varchar(300),
    email varchar(100) not null unique,
    identity_document varchar(9) not null unique,
    phone_number varchar (20) not null,
    street varchar(200) not null,
    state_or_province varchar(150) not null,
    municipality_or_delegation varchar(150) not null,
    city varchar(100) not null,
    zip_code varchar(10) not null,
    country varchar(100) not null,
    external_number varchar(20) not null,
    internal_number varchar(20),
    complement varchar(200),

    primary key(id)
);