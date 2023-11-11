show databases;
use budget;

drop table establishment_visit;

create table establishment_visit (
	establishment_visit_id int auto_increment,
    visit_date date not null,
    description varchar(100),
    visit_total_amount decimal(7, 2) not null,
    tax_percentage decimal(5, 3) not null,
    comments varchar(500),
    establishment_id int not null,
    transaction_type_id int not null,

    primary key (establishment_visit_id),
    
    index establishment_ind(establishment_id),
    index transaction_type_ind(transaction_type_id),

    foreign key (establishment_id)
		references establishment(establishment_id),
    foreign key (transaction_type_id)
		references transaction_type(transaction_type_id)
);

/*create table establishment_visit_seq (
  next_val int
);*/

select * from establishment_visit;


