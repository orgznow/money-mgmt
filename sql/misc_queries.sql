update 	establishment_visit vst 
set 	/*vst.visit_date = '2024-01-13',*/ vst.description = 'Axis 3/16/24 Visit' 
where 	vst.establishment_visit_id in (1354)

update establishment_visit vst set vst.visit_total_amount = 1167.68 where vst.establishment_visit_id in (876)

update establishment_visit vst set vst.visit_date = '2024-03-17' where vst.establishment_visit_id in (1358)

update establishment_visit vst set vst.establishment_id = 26 where vst.establishment_visit_id in (1006)

update establishment_visit vst set vst.description = 'Feb 2024 Paramount+/CBS Subscription' where vst.establishment_visit_id in (1099)

update establishment_visit vst set vst.description = 'Feb 2024 Paramount+/CBS Subscription', vst.establishment_id = 22 where vst.establishment_visit_id in (1099)

update establishment_visit vst set vst.transaction_type_id = 1 where vst.establishment_visit_id in (1164)

update 	journal_entry ent 
set 	ent.rate_amount=10.98, ent.is_taxable='T', ent.tax_amount=0.82, ent.tip_amount=5.00, ent.final_amount=16.80, ent.comments='Eggs Benedict (Benny) + Coffee'
where 	ent.journal_entry_id = 830

update 	journal_entry ent 
set 	ent.comments=null
where 	ent.journal_entry_id = 1543

update 	journal_entry ent 
set 	ent.description ='Feb 2024 Paramount+/CBS Subscription'
where 	ent.journal_entry_id = 1466

update 	journal_entry ent 
set 	ent.description ='Jan 2024 Key HELOC Advance', ent.category_id = 55
where 	ent.journal_entry_id = 1254

update category set name='Groceries - Frozen Prep.', description='Groceries - Frozen Prepared Foods' where category_id=37;

delete from journal_entry where journal_entry_id in (1587)

delete from journal_entry where establishment_visit_id in (1902, 1952, 2002, 2003)

delete from establishment_visit where establishment_visit_id in (1902, 1952, 2002, 2003)

select * from establishment_visit vst where vst.description like '%Basic%'

select * from establishment_visit vst where vst.establishment_visit_id = 1004

select * from journal_entry ent where ent.establishment_visit_id = 1004

