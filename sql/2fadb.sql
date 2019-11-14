drop table users;
drop table auth_2fa_users;

create table users (
  id              bigserial       not null primary key,
  username        varchar(64)     not null,
  email_address   varchar(256)    null,
  first_name      varchar(128)    not null,
  last_name       varchar(128)    not null,
  hashed_passwd   varchar(64)     not null,
  update_time     timestamptz     not null default now()
);

create table auth_2fa_users (
  username        varchar(64)     not null primary key,
  secret_2fa_code varchar(32)     not null,
  update_time     timestamptz     not null default now()
);

