

SET search_path TO bnb, public;
DROP VIEW if exists avgPrice cascade;
DROP VIEW if exists avgPriceEachListing cascade;
DROP VIEW if exists PayEachTraveler cascade;
DROP VIEW if exists bargainerCandidate cascade;
DROP VIEW if exists maxPercentage cascade;


CREATE VIEW avgPrice AS
SELECT listingId, sum(price) as sumPrice, sum(numNights) as sumNight
FROM Booking
GROUP BY listingId;

CREATE VIEW avgPriceEachListing AS
SELECT listingId, avg(sumPrice::numeric::float/sumNight::numeric::float) AS avgPrice
FROM avgPrice
GROUP BY listingId;

--the price that each traveler pays for each of their listings
CREATE VIEW PayEachTraveler AS
SELECT listingId, travelerid, price/numNights AS price
FROM Booking;

CREATE VIEW bargainerCandidate AS
SELECT travelerid, t1.listingId AS listingId, (1 - price/avgPrice) * 100 AS percentage
FROM avgPriceEachListing t1, PayEachTraveler t2
WHERE t1.listingId = t2.listingId AND price <= 0.75 * avgPrice;

CREATE VIEW maxPercentage AS
SELECT travelerid, max(percentage) AS percentage
FROM bargainerCandidate
GROUP BY travelerid
HAVING count(DISTINCT listingId) >= 3;

SELECT travelerid, CAST(percentage AS INT) AS largestBargainPercentage, listingId
FROM maxPercentage NATURAL JOIN bargainerCandidate
ORDER BY largestBargainPercentage DESC, travelerid;

