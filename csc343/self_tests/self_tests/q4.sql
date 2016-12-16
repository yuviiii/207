SET search_path TO bnb, public;
drop view if exists hasRatings cascade;
drop view if exists aveRatings cascade;
drop view if exists notNonDecreasing cascade;
drop view if exists percentage cascade;

create view hasRatings as
select Listing.listingId, rating, startdate, owner
from TravelerRating, Listing
where TravelerRating.listingId=Listing.listingId;
select * from hasRatings;

create view aveRatings as
select owner, AVG(rating) as aveRating, extract(year from startdate) as year
from hasRatings
group by owner,  extract(year from startdate);
select * from aveRatings;

create view notNonDecreasing as
select owner
from aveRatings a1
where exists (select *
              from aveRatings a2
              where a1.owner=a2.owner
              and a1.year<a2.year
              and a1.aveRating>a2.aveRating);
select * from notNonDecreasing;

create view percentage as
select cast((cast((select count(distinct hasRatings.owner) as total from hasRatings)-count(notNonDecreasing.owner) 
	as float))*100/cast((select count(distinct hasRatings.owner) as total from hasRatings) as float) as int) as percentage
from notNonDecreasing;
select * from percentage;

