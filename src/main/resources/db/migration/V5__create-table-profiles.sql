CREATE TABLE IF NOT EXISTS profiles(
    id bigint not null auto_increment,
    name varchar(300) not null unique,
    active tinyint not null,

    primary key(id)
);