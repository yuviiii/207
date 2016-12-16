

SET search_path TO bnb, public;
DROP VIEW if exists tRating cascade;
DROP VIEW if exists hRating cascade;
DROP VIEW if exists isReciprocal cascade;
DROP VIEW if exists numReciprocal cascade;
DROP VIEW if exists backScratches cascade;
DROP VIEW if exists allBackScratches cascade;


CREATE VIEW tRating AS
SELECT listingID, startDate, travelerID, rating AS tRate
FROM TravelerRating NATURAL JOIN Booking;

CREATE VIEW hRating AS
SELECT listingID, startDate, travelerID, rating AS hRate
FROM HomeownerRating NATURAL JOIN Booking;

CREATE VIEW isReciprocal AS
SELECT *
FROM tRating NATURAL FULL JOIN hRating
WHERE tRate IS NOT NULL AND hRate IS NOT NULL;

CREATE VIEW numReciprocal AS
SELECT travelerID, count(listingID) AS reciprocals
FROM isReciprocal NATURAL FULL JOIN (SELECT travelerID FROM Traveler) t
GROUP BY travelerID;

CREATE VIEW backScratches AS
SELECT *
FROM isReciprocal
WHERE ABS(tRate - hRate) <= 1 OR ((tRate IS NULL) AND (hRate IS NULL));

CREATE VIEW allBackScratches AS
SELECT travelerID, count(listingID) AS backScratches
FROM backScratches NATURAL FULL JOIN (SELECT travelerID FROM Traveler) t
GROUP BY travelerID;

SELECT *
FROM (SELECT travelerID FROM Traveler) t 
					NATURAL FULL JOIN numReciprocal 
					NATURAL FULL JOIN allBackScratches
ORDER BY reciprocals DESC, backScratches DESC, travelerID;

