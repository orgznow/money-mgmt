select 		entry.journal_entry_id ID, entry.entry_date PURCHASE_DT, entry.description DESCRIPTION, entry.final_amount AMOUNT, entry.is_taxable IS_TAXABLE,
			categ.name CATEGORY, entry.comments COMMENTS, store.name STORE_NAME, tx_typ.name TX_TYPE
from		journal_entry entry
				inner join category categ on entry.category_id = categ.category_id
				inner join establishment store on entry.store_id = store.establishment_id
				inner join transaction_type tx_typ on entry.transaction_type_id = tx_typ.transaction_type_id
order by	entry.journal_entry_id asc; 	            