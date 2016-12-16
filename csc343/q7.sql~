SET search_path TO bnb, public;
drop view if exists travelerBooking cascade;

create view bookings as
select Booking.listingId, Booking.startdate, Booking.travelerId, BookingRequest.offerPrice, Booking.price
from BookingRequest, Booking
where Booking.listingId=BookingRequest.listingId and Booking.startdate=BookingRequest.startdate;
select * from bookings;