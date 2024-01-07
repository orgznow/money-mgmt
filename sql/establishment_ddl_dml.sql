show databases;
use budget;

drop table establishment;

 create table establishment (
	establishment_id int,
    name varchar(50) not null, 
    description varchar(100),
    tax_percentage decimal(5,2) default 7.50,
    zip_code char(10),
    
    primary key (establishment_id)
);

insert into establishment values (1, 'First Federal Savings', 'Home Mortgage Provider', 7.50);
insert into establishment values (2, 'Columbia Gas', 'Home Gas Utility Company', 7.50);
insert into establishment values (3, 'AEP', 'Home Electric Utility Company', 7.50);
insert into establishment values (4, 'Home Depot', 'Home Improvement Store', 7.50);
insert into establishment values (5, 'Lowe\'s', 'Home Improvement Store', 7.50);
insert into establishment values (6, 'Giant Eagle', 'Groceries, Personal Care, Home Care and Pharmacy Store', 7.50);
insert into establishment values (7, 'Kroger', 'Groceries, Personal Care, Home Care and Pharmacy Store', 7.50);
insert into establishment values (8, 'Meijer', 'Groceries, Personal Care, Home Care and Pharmacy Store', 7.50);
insert into establishment values (9, 'Trader Joe\'s', 'Groceries, Personal Care Store', 7.50);
insert into establishment values (10, 'Pacific Eatery', 'Pan Asian Restaurant', 7.50);
insert into establishment values (11, 'Asian Gourmet', 'Pan Asian Restaurant', 7.50);
insert into establishment values (12, 'Biryani Corner', 'Indian Takeout Restaurant', 7.50);
insert into establishment values (13, 'Wendy\'s', 'Fast Food Restaurant', 7.50);
insert into establishment values (14, 'Mc Donald\'s', 'Fast Food Restaurant', 7.50);
insert into establishment values (15, 'Burger King', 'Fast Food Restaurant', 7.50);
insert into establishment values (16, 'KFC', 'Fast Food Restaurant', 7.50);
insert into establishment values (17, 'Tim Horton\'s', 'Breakfast & Coffee Place', 7.50);
insert into establishment values (18, 'Starbuck\'s', 'Breakfast & Coffee Place', 7.50);
insert into establishment values (19, 'Spectrum', 'Broadband Internet Access Establishment', 7.50);
insert into establishment values (20, 'Sling TV', 'Streaming Video Establishment', 7.50);
insert into establishment values (21, 'Peacock TV', 'Streaming Video Establishment', 7.50);
insert into establishment values (22, 'Paramount TV', 'Streaming Video Establishment', 7.50);
insert into establishment values (23, 'NYTimes', 'eNewspaper Establishment', 7.50);
insert into establishment values (24, 'MSOffice', 'MSOffice Products', 7.50);
insert into establishment values (25, 'BasicTalk', 'VOIP Home Phone Provider', 7.50);
insert into establishment values (26, 'T-Mobile', 'Cellular Phone Provider', 7.50);
insert into establishment values (27, 'YMCA', 'Local Gym', 7.50);
insert into establishment values (28, 'United Health Golden Rule (Medical)', 'HealthCare Provider', 7.50);
insert into establishment values (29, 'United Health Golden Rule (Dental & Vision)', 'HealthCare Provider', 7.50);
insert into establishment values (30, 'United Health Golden Rule (Dental & Vision)', 'HealthCare Provider', 7.50);
insert into establishment values (31, 'Valvoline', 'Car Service Provider', 7.50);
insert into establishment values (32, 'Getgo', 'Gasoline Station', 7.50);
insert into establishment values (33, 'AWOL', 'Nightclub', 7.50);
insert into establishment values (34, 'Union Station', 'Cafe & Nightclub', 7.50);
insert into establishment values (35, 'Axis', 'Nightclub', 7.50);
insert into establishment values (36, 'Capital One', 'Credit Card Provider', 7.50);
insert into establishment values (37, 'Citibank', 'Credit Card Provider', 7.50);
insert into establishment values (38, 'Synchrony', 'Crate & Barrel Credit Card Provider', 7.50);
insert into establishment values (39, 'KeyBank', 'Banking & Line of Credit Services Provider', 7.50);
insert into establishment values (40, 'Emirates', 'International Airline', 7.50);
insert into establishment values (41, 'SouthWest', 'Domestic Airline', 7.50);
insert into establishment values (42, 'ChildFund', 'Charity Services (India)', null);
insert into establishment values (43, 'Miscellaneous', 'Miscellaneous', 7.50);
insert into establishment values (44, 'Trinethra', 'Indian Grocery Store', 7.50);
insert into establishment values (45, 'Amazon', 'Amazon.com eTailer/Prime Services', 7.50);
insert into establishment values (46, 'Walmart', 'Department Store', 7.50);
insert into establishment values (47, 'Woods @ Shagbark', 'Condo Association', 7.50);
insert into establishment values (48, 'Cafe Creekside', 'Diner & Cafe', 7.50, '43230');
insert into establishment values (49, 'Michelle Tuesday\'s', 'Music School', 7.50, '43230');
insert into establishment values (50, 'Harry\'s', 'Shaving Plan', 7.50, null);
insert into establishment values (51, 'ParkCMH', 'CMH Parking Services', 7.50, null);
insert into establishment values (52, 'HBK', 'House of Biryanis & Kebabs', 7.50, 43240);
insert into establishment values (53, 'Garuda', 'Garuda Indian Supermarket', 7.50, 43240);
insert into establishment values (54, 'Fresh Thyme', 'Fresh Thyme Market', 7.50, 43230);
insert into establishment values (55, 'OfficeMax', 'Office Depot OfficeMax', 7.50, 43219);
insert into establishment values (56, 'Street Vendor', 'Generic Street Vendor', 0.00, null);
insert into establishment values (57, 'NYTimes Cooking', 'NYTimes Cooking App', 0.00, null);
insert into establishment values (58, 'BridgeCrest/Carvana', 'BridgeCrest Financing w/ Carvana', 0.00, null);
insert into establishment values (59, 'ACS', 'American Communications Solutions', 0.00, 22039);
insert into establishment values (60, 'Boscoe\'s', 'Boscoe\'s Nightclub', 7.5, 43206);
insert into establishment values (61, 'State Farm', 'State Farm', 7.5, null);
insert into establishment values (62, 'Macy\'s', 'Macy\'s', 7.5, null);
insert into establishment values (63, 'Macy\'s Online', 'Macy\'s Online - macys.com', 7.5, null);
insert into establishment values (64, 'BestBuy Polaris', 'BestBuy - Polaris', 7.5, 43240);
insert into establishment values (65, 'Target Easton', 'Target - Easton', 7.5, 43219);
insert into establishment values (66, 'Target Polaris', 'Target - Polaris', 7.5, 43240);
insert into establishment values (67, 'Columbus Dispatch', 'Columbus Dispatch/Gannett Newspaper', 7.5, null);
insert into establishment values (68, 'El Acapulco', 'El Acapulco Restaurant', 7.5, 43235);
insert into establishment values (69, 'BestBuy Easton', 'BestBuy - Easton', 7.5, 43219);
insert into establishment values (70, 'Nazareth', 'Nazareth Restaurant', 7.5, 43230);
insert into establishment values (71, 'Mediterranean Food', 'Mediterranean Food Import & Bakery #2', 7.5, 43235);
insert into establishment values (72, 'Damian\'s Expert Tailor', 'Damian\'s Expert Tailoring', 7.5, 43214);
insert into establishment values (73, 'Micro Center', 'Micro Center Bethel', 7.5, 43214);
insert into establishment values (74, 'Target New Albany', 'Target - New Albany', 7.5, 43081);
insert into establishment values (75, 'BestBuy.com', 'BestBuy', 0.00, null);
insert into establishment values (76, 'Wikimedia.org', 'Wikimedia Org', 0.00, null);
insert into establishment values (77, 'Staples Easton', 'Staples - Easton', 7.5, 43219);
insert into establishment values (78, 'Delta', 'Delta Airlines', 7.50, null);
insert into establishment values (79, 'Torso - ShoNo', 'Torso - Short North', 7.50, 43215);
insert into establishment values (80, 'Jeni\'s - ShoNo', 'Jeni\'s - Short North', 7.50, 43215);
insert into establishment values (81, 'Columbus Duchess 631', 'Columbus Duchess BP 631', 7.50, 43230);
insert into establishment values (82, 'Dadu\'s Bakery', 'Dadu\'s Bakery & Sweets', 7.50, 43240);
insert into establishment values (83, 'Kuwait Airways', 'Kuwait Airways International Airline', 0.00, null);
insert into establishment values (84, 'India Grocers', 'India Grocers Sawmill', 7.50, 43017);
insert into establishment values (85, 'Music Go Round', 'Music Go Round Stoneridge', 7.50, 43230);

select * from establishment;


