create table account (
    accountid UUID not null primary key,
    account_number varchar(50),
    account_holder_name varchar(50),
    password varchar(50),
    account_balance float
);