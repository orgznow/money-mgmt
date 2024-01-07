show databases;
use budget;

drop table transaction_type;

 create table transaction_type (
	transaction_type_id int,
    name varchar(50) not null, 
    description varchar(100), 

    primary key (transaction_type_id)
);

insert into transaction_type values (1, 'Citi CC', 'Citibank Credit Card');
insert into transaction_type values (2, 'Cap One CC', 'Capital One Credit Card');
insert into transaction_type values (3, 'Key OP', 'KeyBank Online Payment');
insert into transaction_type values (4, 'Key DD', 'KeyBank Direct Deposit');
insert into transaction_type values (5, 'Key AP', 'KeyBank AutoPay');
insert into transaction_type values (6, 'Cash', 'Cash');
insert into transaction_type values (7, 'Key Trsf', 'KeyBank Transfer');
insert into transaction_type values (8, 'Key Zelle', 'KeyBank Zelle Transfer');
insert into transaction_type values (9, 'Key DC', 'KeyBank Debit Card');
insert into transaction_type values (10, 'BestBuy CC', 'BestBuy Credit Card');
insert into transaction_type values (11, 'Miscellaneous', 'Miscellaneous');
insert into transaction_type values (12, 'Key Int', 'Key Interest');
insert into transaction_type values (13, 'Gift Card', 'Gift Card');

update transaction_type set name = 'Key DD', description = 'KeyBank Direct Deposit' where transaction_type_id = 4;

select * from transaction_type;

