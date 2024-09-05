update 	establishment_visit vst 
set 	vst.visit_date = '2024-08-08'/*, vst.description = 'Axis 3/16/24 Visit' */
where 	vst.establishment_visit_id in (3403)

update establishment_visit vst set vst.visit_total_amount = 1000 where vst.establishment_visit_id in (3571)

update 	journal_entry ent 
set 	ent.rate_amount=2.52, ent.base_amount=2.52, ent.final_amount=2.52
where 	ent.journal_entry_id = 3744

update 	journal_entry ent 
set 	ent.comments = 'Get discount on previously purchased Pinky Tea Canister Drinking Glasses now on sale (see 5/28/24 Kroger Purchase)'
where 	ent.establishment_visit_id = 2893

update establishment_visit vst set vst.visit_date = '2024-06-22' where vst.establishment_visit_id in (3053)

update establishment_visit vst set vst.establishment_id = 39 where vst.establishment_visit_id in (3454)

update establishment_visit vst set vst.description = 'Alamo Chicago Toll Passthru Fee', vst.comments = null where vst.establishment_visit_id in (2708)

update establishment_visit vst set vst.comments = 'Get discount on Pinky Tea Canister Drinking Glasses (see 5/28/24 Kroger Purchase)' where vst.establishment_visit_id in (2893)

update establishment_visit vst set vst.description = 'Feb 2024 Paramount+/CBS Subscription', vst.establishment_id = 22 where vst.establishment_visit_id in (1099)

update establishment_visit vst set vst.transaction_type_id = 1 where vst.establishment_visit_id in (3503)

update establishment_visit vst set vst.comments = 'Pride Week Festivities' where vst.establishment_visit_id in (3023)

update 	journal_entry ent 
set 	ent.rate_amount=10.98, ent.is_taxable='T', ent.tax_amount=0.82, ent.tip_amount=5.00, ent.final_amount=16.80, 
		ent.comments='Eggs Benedict (Benny) + Coffee'
where 	ent.journal_entry_id = 4

update 	journal_entry ent 
set 	ent.rate_amount=14, ent.base_amount=14, ent.final_amount=14
where 	ent.journal_entry_id = 2802

update 	journal_entry ent 
set 	ent.comments=null
where 	ent.journal_entry_id = 1543

update 	journal_entry ent 
set 	ent.description ='Haagen Dazs Mango Ice Cream'
where 	ent.journal_entry_id = 4526

update 	journal_entry ent 
set 	ent.description ='Chicago Tolls', ent.comments = 'Chicago Road Tolls Passthru via Alamo', ent.category_id = 17
where 	ent.journal_entry_id = 3317

update 	journal_entry ent 
set 	ent.description ='Jan 2024 Key HELOC Advance', ent.category_id = 55
where 	ent.journal_entry_id = 1254

update 	journal_entry ent 
set 	ent.category_id = 62
where 	ent.journal_entry_id in (1456, 1343, 986, 552)

update category set name='Groceries - Frozen Prep.', description='Groceries - Frozen Prepared Foods' where category_id=37;

delete from journal_entry where journal_entry_id in (1587)

delete from journal_entry where establishment_visit_id in (3565)

delete from establishment_visit where establishment_visit_id in (3565)

select * from establishment_visit vst where vst.description like '%Basic%'

select * from establishment_visit vst where vst.establishment_visit_id = 1004

select * from journal_entry ent where ent.establishment_visit_id = 1004

