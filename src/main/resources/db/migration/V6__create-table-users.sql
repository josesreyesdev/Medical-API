CREATE TABLE IF NOT EXISTS users(
    id bigint not null auto_increment  primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    active tinyint not null
);

CREATE TABLE users_profiles (
    user_id bigint not null,
    profile_id bigint not null,

    primary key (user_id, profile_id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (profile_id) references profiles (id) on delete cascade
);