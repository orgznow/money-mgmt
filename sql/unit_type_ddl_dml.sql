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
insert into unit_type values (3, 'Weight', 'Ounces/Milligrams/Pounds/Kilograms etc.');
insert into unit_type values (4, 'Miscellaneous', 'Miscellaneous/Unknown');

select * from unit_type;


