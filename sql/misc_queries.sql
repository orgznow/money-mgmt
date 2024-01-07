update establishment_visit vst set vst.visit_date = '2023-12-18' where vst.establishment_visit_id in (863)

update establishment_visit vst set vst.visit_total_amount = 1167.68 where vst.establishment_visit_id in (876)

update 	journal_entry ent 
set 	ent.rate_amount=10.98, ent.is_taxable='T', ent.tax_amount=0.82, ent.tip_amount=5.00, ent.final_amount=16.80, ent.comments='Eggs Benedict (Benny) + Coffee'
where 	ent.journal_entry_id = 830

update 	journal_entry ent 
set 	ent.comments='I noticed the new text message about the charge 3 days after it was incurred; the date of transaction was not prominent, which is why I thought it was a fraudulent charge'
where 	ent.journal_entry_id = 859

update category set name='Groceries - Snacks', description='Groceries - Snacks' where category_id=7;
