SET search_path TO bnb, public;
drop view if exists requestNumber cascade;
drop view if exists tentimestravelers cascade;
drop view if exists withBooking cascade;
drop view if exists scrapper cascade;
drop view if exists scrapperListing cascade;
drop view if exists scrapperCity cascade;
drop view if exists mostCity cascade;
drop view if exists alphabeticFirst cascade;
drop view if exists averageNum cascade;

create view requestNumber as
select travelerId, count(*) as requestNum
from BookingRequest
group by travelerId;
select * from requestNumber;

create view averageNum as
select 10*(count(requestId)/count(distinct travelerId)) AS average
from BookingRequest;
select * from averageNum;

create view tentimestravelers as
select requestNumber.travelerId
from requestNumber, averageNum
where requestNum>=average;
select * from tentimestravelers;

create view withBooking as
select travelerId
from tentimestravelers NATURAL JOIN Booking;
select * from withBooking;

create view scrapper as
(select travelerId
from tentimestravelers)
except
(select travelerId
from withBooking);
select * from scrapper;

create view scrapperListing as
select travelerId, listingId
from scrapper NATURAL JOIN BookingRequest;
select * from scrapperListing;

create view scrapperCity as
select scrapperListing.travelerId, city, count(*) as cityNum
from scrapperListing NATURAL JOIN Listing
group by scrapperListing.travelerId, city;
select * from scrapperCity;

create view mostCity as
select travelerId, city, cityNum
from (select travelerId, MAX(cityNum) AS maxcity
	from scrapperCity
	group by travelerId) as maxcity NATURAL JOIN scrapperCity
where cityNum = maxcity;
select * from mostCity;

create view alphabeticFirst as 
select travelerId, min(city) as mostRequestedCity, requestNumber.requestNum
from mostCity NATURAL JOIN requestNumber
group by travelerId, requestNumber.requestNum;
select * from alphabeticFirst;

select travelerId, firstname||surname as name, COALESCE(CAST(email AS VARCHAR(10)), 'unknown') AS email, mostRequestedCity, requestNum
from alphabeticFirst NATURAL JOIN Traveler;