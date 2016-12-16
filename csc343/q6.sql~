SET search_path TO bnb, public;
drop view if exists travelerBooking cascade;
drop view if exists reciprocalRatings cascade;
drop view if exists reciprocalTravelers cascade;
drop view if exists countReciprocal cascade;
drop view if exists backScratches cascade;
drop view if exists result cascade;

create view travelerBooking as
select Traveler.travelerId, startdate, listingId
from Traveler NATURAL FULL JOIN Booking;
select * from travelerBooking;

create view reciprocalRatings as
select HomeownerRating.listingId, HomeownerRating.startdate, HomeownerRating.rating as HomeownerRatings, TravelerRating.rating as TravelerRatings
from HomeownerRating, TravelerRating
where HomeownerRating.listingId=TravelerRating.listingId and HomeownerRating.startdate=TravelerRating.startdate;
select * from reciprocalRatings;

create view reciprocalTravelers as
select travelerBooking.listingId, travelerBooking.startdate, travelerId, HomeownerRatings, TravelerRatings
from travelerBooking NATURAL JOIN reciprocalRatings;
select * from reciprocalTravelers;

create view countReciprocal as
select travelerId, count(*)
from reciprocalTravelers
group by travelerId;
select * from countReciprocal;

create view backScratches as
select travelerId, count(*)
from reciprocalTravelers
where HomeownerRatings-TravelerRatings<=1 and HomeownerRatings-TravelerRatings>=-1
group by travelerId;
select * from backScratches;

create view result as
select countReciprocal.travelerId, countReciprocal.count as reciprocals, backScratches.count as backScratches
from countReciprocal, backScratches
where countReciprocal.travelerId=backScratches.travelerId
order by reciprocals DESC, backScratches DESC;
select * from result;

select travelerId,COALESCE(reciprocals, 0) AS reciprocals,COALESCE(backScratches, 0) AS backScratches
from result NATURAL FULL JOIN (select travelerId from traveler) as t
order by reciprocals DESC,backScratches DESC,travelerId ASc;
