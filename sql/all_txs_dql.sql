select 		visit.establishment_visit_id, visit.description, visit.comments, visit.tax_percentage, visit.visit_total_amount,
			entry.journal_entry_id ID, entry.entry_date purchase_dt, entry.description description, entry.rate_amount, entry.quantity, unit_typ.name unit,
            entry.final_amount amount, entry.is_taxable is_taxable, 
			categ.name category, entry.comments comments, store.name store_name, tx_typ.name tx_type
from		establishment_visit visit
				inner join journal_entry entry on visit.establishment_visit_id = entry.establishment_visit_id
                inner join unit_type unit_typ on entry.unit_type_id = unit_typ.unit_type_id
				inner join category categ on entry.category_id = categ.category_id
				inner join establishment store on visit.establishment_id = store.establishment_id
				inner join transaction_type tx_typ on visit.transaction_type_id = tx_typ.transaction_type_id
order by	visit.establishment_visit_id desc, entry.journal_entry_id desc; 	            