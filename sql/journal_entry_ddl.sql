show databases;
use budget;

drop table journal_entry;

create table journal_entry (
	journal_entry_id int auto_increment,
    entry_date date not null,
    description varchar(100) not null,
    rate_amount decimal(7,2) not null,
    quantity decimal(7,2) not null,
    unit_type_id int not null,
	base_amount decimal(7,2) not null,
    discount_amount decimal(7, 2),
    is_taxable char(1) not null,
	tax_amount decimal(7, 2) not null,
    final_amount decimal(7, 2) not null,
    comments varchar(500),
	establishment_visit_id int not null,
    category_id int,

    primary key (journal_entry_id),
    
	index establishment_visit_ind(establishment_visit_id),
    index category_ind(category_id),

    foreign key (establishment_visit_id)
        references establishment_visit(establishment_visit_id)
    foreign key (category_id)
        references category(category_id)
);

select * from journal_entry;


