show databases;
use budget;

drop table unit_type;

create table unit_type (
	unit_type_id int auto_increment,
    name varchar(50) not null, 
    description varchar(100),
    is_default boolean not null default false,

    primary key (unit_type_id)
);

drop table unit_type_seq;

create table unit_type_seq (
  next_val bigint default null
);

insert into unit_type values (1, 'Count', 'Count of Items');
insert into unit_type values (2, 'Volume - Gal', 'Volume - Gallons');
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
insert into unit_type values (15, 'Count - Tube', 'Count of Tubes');
insert into unit_type values (16, 'Count - Cup', 'Count of Cups');
insert into unit_type values (17, 'Count - Glass', 'Count of Glasses', false);
insert into unit_type values (18, 'Count - Carton', 'Count of Cartons', false);
insert into unit_type values (19, 'Count - Plate', 'Count of Plates', false);
insert into unit_type values (20, 'Count - Bowl', 'Count of Bowls', false);
insert into unit_type values (21, 'Count - Side', 'Count of Sides', false);
insert into unit_type values (22, 'Count - Order', 'Count of Orders', false);
insert into unit_type values (23, 'Count - Slice', 'Count of Slices', false);
insert into unit_type values (24, 'Count - Jug', 'Count of Jugs', false);
insert into unit_type values (25, 'Count - Loaf', 'Count of Loaves', false);
insert into unit_type values (26, 'Count - Bar', 'Count of Bars', false);
insert into unit_type values (27, 'Count - Wedge', 'Count of Wedges', false);
insert into unit_type values (28, 'Count - Wheel', 'Count of Wheels', false);
insert into unit_type values (29, 'Count - Stick', 'Count of Sticks', false);
insert into unit_type values (30, 'Count - Cnstr.', 'Count of Canisters', false);
insert into unit_type values (31, 'Fraction - Split', 'Fraction of Splits', false);
insert into unit_type values (32, 'Count - Block', 'Count of Blocks', false);

select * from unit_type;


