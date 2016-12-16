

SET search_path TO bnb, public;
DROP VIEW if exists requestNotBooked cascade;
DROP VIEW if exists commitedTraveler cascade;
DROP VIEW if exists numberListings cascade;
DROP VIEW if exists HomeownerTwo cascade;

-- find the listing tha has been requested by each traveler
CREATE VIEW requestNotBooked AS
(SELECT travelerId, listingId
FROM BookingRequest
GROUP BY travelerId, listingId)
	EXCEPT
	(SELECT travelerID, listingId
		FROM Booking
		GROUP BY travelerID, listingId);

-- traveler that has booked all the request he made
CREATE VIEW commitedTraveler AS
(SELECT travelerID
FROM BookingRequest)
	EXCEPT
	(SELECT travelerID
		FROM requestNotBooked);

-- find the number of listings that each traveler has booked
CREATE VIEW numberListings AS
SELECT travelerId, count(DISTINCT listingId) AS numListings
FROM commitedTraveler NATURAL JOIN Booking
GROUP BY travelerId;

SELECT travelerId, surname, numListings
FROM numberListings NATURAL JOIN Traveler
ORDER BY travelerId;

