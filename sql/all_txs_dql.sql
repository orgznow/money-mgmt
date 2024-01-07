select 		visit.establishment_visit_id, visit.description, visit.comments, visit.tax_percentage, visit.visit_total_amount,
			entry.journal_entry_id ID, entry.entry_date purchase_dt, entry.description description, entry.rate_amount, entry.quantity, unit_typ.name unit,
            entry.is_taxable is_taxable, entry.tax_amount tax_amount, entry.tip_amount tip_amount, entry.final_amount final_amount, 
			categ.name category, entry.comments comments, store.name store_name, tx_typ.name tx_type
from		establishment_visit visit
				inner join journal_entry entry on visit.establishment_visit_id = entry.establishment_visit_id
                inner join unit_type unit_typ on entry.unit_type_id = unit_typ.unit_type_id
				inner join category categ on entry.category_id = categ.category_id
				inner join establishment store on visit.establishment_id = store.establishment_id
				inner join transaction_type tx_typ on visit.transaction_type_id = tx_typ.transaction_type_id
order by	visit.establishment_visit_id desc, entry.journal_entry_id desc; 	            

-- All transactions where the total amount does not equal the sum of the line items
select 		visit.establishment_visit_id, visit.description, visit.comments, visit.tax_percentage, visit.visit_date, visit.visit_total_amount, sum(entry.final_amount)
from		establishment_visit visit
				inner join journal_entry entry on visit.establishment_visit_id = entry.establishment_visit_id
group by 	visit.establishment_visit_id, visit.description, visit.comments, visit.tax_percentage, visit.visit_date, visit.visit_total_amount
having		visit.visit_total_amount != sum(entry.final_amount)
order by	visit.establishment_visit_id desc; 

-- Update all total amounts to equal the sum of the associated line items
update 		establishment_visit visit
set 		visit.visit_total_amount = 
			(
				select 		sum(entry.final_amount)
                from		journal_entry entry
                where		entry.establishment_visit_id = visit.establishment_visit_id
            )
