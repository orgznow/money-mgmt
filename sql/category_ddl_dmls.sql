show databases;
use budget;

drop table category;

 create table category (
	category_id int, 
    name varchar(50) not null, 
    description varchar(100), 
    is_expense  char(1),
    
    primary key (category_id)
);

insert into category values (1, 'Home Payment', 'Home Payment', 'T');
insert into category values (2, 'Home Gas Utility', 'Home Gas Utility', 'T');
insert into category values (3, 'Home Electric Utility', 'Home Electric Utility', 'T');
insert into category values (4, 'Home Improvement', 'Home Improvement', 'T');
insert into category values (5, 'Home Stuff', 'Home Stuff', 'T');
insert into category values (6, 'Groceries', 'Groceries', 'T');
insert into category values (7, 'Snacks', 'Snacks', 'T');
insert into category values (8, 'Eat out', 'Eat out', 'T');
insert into category values (9, 'Media', 'Media', 'T');
insert into category values (10, 'Health', 'Health', 'T');
insert into category values (11, 'Car Services', 'Car Services', 'T');
insert into category values (12, 'Gasoline', 'Gasoline', 'T');
insert into category values (13, 'Entertainment', 'Entertainment', 'T');
insert into category values (14, 'Loan Interest', 'Loan Interest', 'T');
insert into category values (16, 'Loan Paydown', 'Loan Paydown', 'T');
insert into category values (17, 'Vacation', 'Vacation', 'T');
insert into category values (18, 'Charity', 'Charity', 'T');
insert into category values (19, 'Miscellaneous', 'Miscellaneous', 'T');
insert into category values (20, 'Salary', 'Salary', 'F');
insert into category values (21, 'Savings Interest', 'Savings Interest', 'F');
insert into category values (22, 'Miscellaneous Credit', 'Miscellaneous Credit', 'F');
insert into category values (23, 'Home Phone', 'Home Phone', 'T');
insert into category values (24, 'Cell Phone', 'Cell Phone', 'T');
insert into category values (25, 'Personal Improvement', 'Personal Improvement', 'T');
insert into category values (26, 'Personal Stuff', 'Personal Stuff', 'T');

select * from category;

