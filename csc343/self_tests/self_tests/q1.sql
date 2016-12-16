SET search_path TO bnb, public;
drop view if exists request cascade;
drop view if exists bookings cascade;
drop view if exists allnum cascade;


create view request as
select extract(year from BookingRequest.startdate) as year, travelerId, requestId
from BookingRequest
where extract(year from BookingRequest.startdate)>=2007;

create view bookings as
select extract(year from Booking.startdate) as year, travelerId, listingId
from Booking
where extract(year from Booking.startdate)>=2007;

create view allnum as
select travelerId, email, year, requestId, listingId
from (request NATURAL FULL JOIN bookings) NATURAL FULL JOIN Traveler;

select travelerId, email, CAST(year as int), count(requestId) as numRequests, count(listingId) as numBooking
from allnum
group by travelerId, email, year
order by year;



