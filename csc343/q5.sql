

SET search_path TO bnb, public;
DROP VIEW if exists HomeownerFive cascade;
DROP VIEW if exists HomeownerFour cascade;
DROP VIEW if exists HomeownerThree cascade;
DROP VIEW if exists HomeownerTwo cascade;
DROP VIEW if exists HomeownerOne cascade;
DROP VIEW if exists allOwner cascade;

CREATE VIEW HomeownerFive AS
SELECT owner, count(rating) AS r5
FROM TravelerRating tr, Listing
WHERE tr.listingID = Listing.listingID and rating = 5
GROUP BY owner;

CREATE VIEW HomeownerFour AS
SELECT owner, count(rating) AS r4
FROM TravelerRating tr, Listing
WHERE tr.listingID = Listing.listingID and rating = 4
GROUP BY owner;

CREATE VIEW HomeownerThree AS
SELECT owner, count(rating) AS r3
FROM TravelerRating tr, Listing
WHERE tr.listingID = Listing.listingID and rating = 3
GROUP BY owner;

CREATE VIEW HomeownerTwo AS
SELECT owner, count(rating) AS r2
FROM TravelerRating tr, Listing
WHERE tr.listingID = Listing.listingID and rating = 2
GROUP BY owner;

CREATE VIEW HomeownerOne AS
SELECT owner, count(rating) AS r1
FROM TravelerRating tr, Listing
WHERE tr.listingID = Listing.listingID and rating = 1
GROUP BY owner;

CREATE VIEW allOwner AS
SELECT homeownerId AS owner
FROM Homeowner;

SELECT owner AS homeownerid, r5, r4, r3, r2, r1
FROM allOwner NATURAL LEFT JOIN HomeownerFive 
				NATURAL LEFT JOIN HomeownerFour
				NATURAL LEFT JOIN HomeownerThree
				NATURAL LEFT JOIN HomeownerTwo
				NATURAL LEFT JOIN HomeownerOne
ORDER BY r5 DESC, r4 DESC, r3 DESC, r2 DESC, r1 DESC, homeownerid;

