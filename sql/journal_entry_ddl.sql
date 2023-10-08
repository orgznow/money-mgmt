show databases;
use budget;

drop table journal_entry;

create table journal_entry (
	journal_entry_id int auto_increment,
    entry_date date not null,
    description varchar(100) not null,
    base_amount decimal(7,2) not null,
    discount_amount decimal(7, 2),
    is_taxable char(1) not null,
    final_amount decimal(7, 2) not null,
    category_id int,
    comments varchar(500),
    store_id int,
    transaction_type_id int,

    primary key (journal_entry_id),
    
    index category_ind(category_id),
    index store_ind(store_id),
    index transaction_type_ind(transaction_type_id),

    foreign key (category_id)
        references category(category_id),
    foreign key (store_id)
		references establishment(establishment_id),
    foreign key (transaction_type_id)
		references transaction_type(transaction_type_id)
);

select * from journal_entry;


