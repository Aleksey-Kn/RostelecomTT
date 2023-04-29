create schema if not exists links_database;

create table if not exists links_database.link_mapping (
    short_url varchar(45) primary key not null,
    long_url varchar(255) not null
);

insert into links_database.link_mapping(short_url, long_url)
values ('123', 'https://mail.yandex.ru/'),
       ('xxx', 'https://github.com/'),
       ('shrt', 'https://habr.com/ru/'),
       ('lnk', 'https://translate.yandex.ru/');
