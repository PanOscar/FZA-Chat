
create table if not exists room
(
    id   serial primary key,
    name varchar(255) not null unique
);

create table if not exists messages
(
    id        serial primary key,
    text      varchar(1000) not null,
    created   timestamp,
    person_id int,
    room_id   int references room (id)
)