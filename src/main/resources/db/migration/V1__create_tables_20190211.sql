create table game (
  id UUID primary key not null,
  guess integer not null,
  created timestamp with time zone not null default clock_timestamp(),
  updated timestamp with time zone not null default clock_timestamp()
);

insert into game values ('dcebef3c-34a0-4aaf-9866-3ee1cbf160ed', 300);
insert into game values ('dcebef3c-34a0-4aaf-9866-3ee1cbf160ef', 200);
insert into game values ('dcebef3c-34a0-4aaf-9866-3ee1cbf160eb', 500);
