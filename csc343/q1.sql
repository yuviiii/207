

SET search_path TO bnb, public;
DROP VIEW if exists HadRequest cascade;
DROP VIEW if exists HadBooking cascade;
DROP VIEW if exists numRequest cascade;
DROP VIEW if exists numBooking cascade;


CREATE VIEW HadRequest AS
SELECT travelerId, requestId, CAST(EXTRACT(YEAR FROM br.startdate) AS INT) AS year
FROM BookingRequest br
WHERE EXTRACT(YEAR FROM br.startdate) >= EXTRACT(YEAR FROM CURRENT_DATE) - 10
		AND EXTRACT(YEAR FROM br.startdate) < EXTRACT(YEAR FROM CURRENT_DATE);

CREATE VIEW numRequest AS
SELECT travelerId, year, count(*) AS numRequests
FROM HadRequest
GROUP BY travelerId, year;

CREATE VIEW HadBooking AS
SELECT travelerID, listingId, CAST(EXTRACT(YEAR FROM Booking.startdate) AS INT) AS year
FROM  Booking
WHERE EXTRACT(YEAR FROM Booking.startdate) >= EXTRACT(YEAR FROM CURRENT_DATE) - 10
		AND EXTRACT(YEAR FROM Booking.startdate) < EXTRACT(YEAR FROM CURRENT_DATE);

CREATE VIEW numBooking AS
SELECT travelerId, year, count(*) AS numBooking
FROM HadBooking
GROUP BY travelerId, year;

SELECT travelerId, email, year, COALESCE(numRequests, 0) AS numRequests, COALESCE(numBooking, 0) AS numBooking
FROM (numRequest NATURAL FULL JOIN numBooking) NATURAL FULL JOIN Traveler
ORDER BY year DESC, travelerId;

