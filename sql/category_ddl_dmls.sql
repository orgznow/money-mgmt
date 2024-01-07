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
insert into category values (7, 'Groceries - Snacks', 'Groceries - Snacks', 'T');
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
insert into category values (24, 'Cell Phone Plan', 'Cell Phone Plan', 'T');
insert into category values (25, 'Personal Improvement', 'Personal Improvement', 'T');
insert into category values (26, 'Personal Stuff', 'Personal Stuff', 'T');
insert into category values (27, 'Gifts', 'Gifts', 'T');
insert into category values (28, 'Clothing', 'Clothing', 'T');
insert into category values (29, 'Income', 'Income', 'T');
insert into category values (30, 'Cell Phone Purchase', 'Cell Phone Purchase', 'T');
insert into category values (31, 'Cell Phone Services', 'Cell Phone Services', 'T');
insert into category values (32, 'Internet Access', 'Internet Access', 'T');
insert into category values (33, 'Groceries - Prepared', 'Groceries - Prepared Foods', 'T');
insert into category values (34, 'Groceries - Frozen', 'Groceries - Frozen Foods', 'T');
insert into category values (35, 'Groceries - Canned', 'Groceries - Canned Foods', 'T');
insert into category values (36, 'Groceries - Preserved', 'Groceries - Preserved Foods', 'T');
insert into category values (37, 'Groceries - Processed', 'Groceries - Processed Foods', 'T');
insert into category values (38, 'Groceries - Celeb.', 'Groceries - Celebrations', 'T');
insert into category values (39, 'Celebrations', 'Celebrations', 'T');
insert into category values (40, 'Entertainment - Alc.', 'Entertainment - Alcohol', 'T');
insert into category values (41, 'Withdrawal - Kitty', 'Withdrawal - Cash Kitty Purse', 'T');
insert into category values (42, 'Car Insurance', 'Car Insurance', 'T');
insert into category values (43, 'Home Insurance', 'Home Insurance', 'T');
insert into category values (44, 'Groceries - Cndmt.', 'Groceries - Condiments', 'T');
insert into category values (45, 'Home Electronics', 'Home Electronics', 'T');
insert into category values (46, 'Home Electrics', 'Home Electrics', 'T');
insert into category values (47, 'Personal Electronics', 'Personal Electronics', 'T');
insert into category values (48, 'Checking Interest', 'Checking Interest', 'F');
insert into category values (49, 'Entertainment - Prkng.', 'Entertainment - Parking', 'T');
insert into category values (50, 'Misc. - Prkng.', 'Miscellaneous - Parking', 'T');
insert into category values (51, 'Groceries - Entert.', 'Groceries - Home Entertainment', 'F');
insert into category values (52, 'Groceries - Alchl.', 'Groceries - Home Alcohol', 'T');
insert into category values (53, 'Groceries - Entert. Alchl.', 'Groceries - Home Entertainment Alcohol', 'T');
insert into category values (54, 'Donation', 'Donation', 'F');

select * from category;

