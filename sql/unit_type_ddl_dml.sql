show databases;
use budget;

drop table unit_type;

create table unit_type (
	unit_type_id int auto_increment,
    name varchar(50) not null, 
    description varchar(100),

    primary key (unit_type_id)
);

drop table unit_type_seq;

create table unit_type_seq (
  next_val bigint default null
);

insert into unit_type values (1, 'Count', 'Count of Items');
insert into unit_type values (2, 'Volume', 'Fluid Ounces/Milliliters/Gallons/Liters etc.');
insert into unit_type values (3, 'Weight - lb', 'Weight in pounds');
insert into unit_type values (4, 'Miscellaneous', 'Miscellaneous/Unknown');
insert into unit_type values (5, 'Count - Pks', 'Count of Packets');
insert into unit_type values (6, 'Count - Tub', 'Count of Tubs');
insert into unit_type values (7, 'Count - Bag', 'Count of Bags');
insert into unit_type values (8, 'Count - Box', 'Count of Boxes');
insert into unit_type values (9, 'Count - Btl', 'Count of Bottles');
insert into unit_type values (10, 'Count - Can', 'Count of Cans');
insert into unit_type values (11, 'Count - Jar', 'Count of Jars');
insert into unit_type values (12, 'Count - Cntnr.', 'Count of Containers');
insert into unit_type values (13, 'Count - Crate', 'Count of Crates');
insert into unit_type values (14, 'Count - Bunch', 'Count of Bunches');

select * from unit_type;


