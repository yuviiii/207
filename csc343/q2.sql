

SET search_path TO bnb, public;
DROP VIEW if exists NeverBooked cascade;
DROP VIEW if exists moreThanTenTimesRequest cascade;
DROP VIEW if exists RequestedCity cascade;
DROP VIEW if exists mostRequestedCity cascade;
DROP VIEW if exists cityCount cascade;
DROP VIEW if exists KeepOne cascade;
DROP VIEW if exists almostFinal cascade;

-- find the travelerId that the traveler has request but never booked
CREATE VIEW NeverBooked AS
(SELECT travelerId
FROM BookingRequest
GROUP BY travelerId)
	EXCEPT
	(SELECT travelerId
		From Booking
		GROUP BY travelerId);


CREATE VIEW moreThanTenTimesRequest AS
SELECT travelerId, count(requestId) AS numRequests
FROM NeverBooked NATURAL JOIN BookingRequest
GROUP BY travelerId
HAVING count(requestId) > 10 * (SELECT count(requestId) From BookingRequest)
							 / (SELECT count(distinct travelerId)
							 	FROM traveler);

CREATE VIEW RequestedCity AS
SELECT mt.travelerId AS travelerId, city, numRequests
FROM (moreThanTenTimesRequest mt CROSS JOIN BookingRequest br) CROSS JOIN Listing
WHERE mt.travelerId = br.travelerId and br.listingId = Listing.listingId;

CREATE VIEW cityCount AS
SELECT travelerId, count(city) AS cityNum, city
FROM RequestedCity
GROUP BY travelerId, city;

CREATE VIEW mostRequestedcity AS
SELECT c1.travelerId,c1.city, c1.cityNum
FROM cityCount c1
WHERE cityNum = (SELECT max(c2.cityNum) 
					FROM cityCount c2 
					WHERE c1.travelerId = c2.travelerId
					GROUP BY c2.travelerId);

CREATE VIEW KeepOne AS
SELECT m2.travelerId, m2.city, m2.cityNum
FROM mostRequestedcity m2
WHERE m2.city = (SELECT city 
				FROM mostRequestedcity m1 
				WHERE m1.travelerId = m2.travelerId
				ORDER BY m1.city
				LIMIT 1);

CREATE VIEW almostFinal AS
SELECT m.travelerId, firstname || ' ' || surname AS name, 
COALESCE(email, 'unknown') AS email, numRequests
FROM moreThanTenTimesRequest m LEFT JOIN Traveler ON m.travelerId = Traveler.travelerId;

SELECT travelerId, name, email, city AS mostRequestedcity, numRequests
FROM KeepOne NATURAL FULL JOIN almostFinal
ORDER BY numRequests DESC, travelerId;

