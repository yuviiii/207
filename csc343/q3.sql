SET search_path TO bnb, public;
drop view if exists bookedlisting cascade;
drop view if exists invalidListings cascade;
drop view if exists validListings cascade;
drop view if exists notSpanTwoYears cascade;
drop view if exists spanTwoYears cascade;
drop view if exists newValid cascade;
drop view if exists listingCityRegulation cascade;
drop view if exists violateMin cascade;
drop view if exists violateMax cascade;

create view bookedlisting as
select listingId, propertyType, city, startdate, numNights, owner, (startdate+numNights) AS enddate
from Booking NATURAL JOIN Listing;
select * from bookedlisting;

create view invalidListings as
select listingId, propertyType, city, startdate, numNights, owner, enddate
from bookedlisting b1
where exists (select b1.listingId
              from bookedlisting b2
              where b1.listingId=b2.listingId and b1.startdate!=b2.startdate
              and b2.startdate>b1.startdate and b2.startdate<b1.startdate+b1.numNights);
select * from invalidListings;

create view validListings as
select listingId, propertyType, startdate, city, owner, enddate, numNights
from bookedlisting NATURAL JOIN
      ((select listingId from Listing)
       except
      (select listingId from invalidListings)) as valid;
select * from validListings;

create view notSpanTwoYears as
select *
from validListings
where extract(year from startdate)=extract(year from enddate);
select * from notSpanTwoYears;

create view spanTwoYears as
select *
from validListings
where extract(year from startdate)!=extract(year from enddate);
select * from spanTwoYears;

create view newValid as
(select listingId, propertyType, extract(year from startdate) as year, city, owner, numNights
from notSpanTwoYears)
union
(select listingId, propertyType, extract(year from startdate) as year, city, owner, enddate - to_date(to_char(extract(year from enddate),'9999')||'-01-01','YY-MM-DD') as numNights
from spanTwoYears)
union
(select listingId, propertyType, extract(year from startdate) as year, city, owner, to_date(to_char(extract(year from enddate),'9999')||'-01-01','YY-MM-DD')-startdate as numNights
from spanTwoYears);
select * from newValid;

create view listingCityRegulation as
select listingId, Listing.city, CityRegulation.propertyType, regulationType, days
from Listing, CityRegulation
where CityRegulation.city = Listing.city and CityRegulation.propertyType = Listing.propertyType;
select * from listingCityRegulation;

create view violateMin as
select owner as homeowner, newValid.listingId, year, newValid.city
from newValid, listingCityRegulation
where newValid.listingId=listingCityRegulation.listingId and newValid.city=listingCityRegulation.city and newValid.propertyType=listingCityRegulation.propertyType
and regulationType='min' and numNights<days;
select * from violateMin;

create view violateMax as
select owner as homeowner, newValid.listingId, year, newValid.city
from newValid, listingCityRegulation
where newValid.listingId=listingCityRegulation.listingId and newValid.city=listingCityRegulation.city and newValid.propertyType=listingCityRegulation.propertyType
and regulationType='max' and numNights>days;
select * from violateMax;

create view result as
(select * from violateMin) UNION (select * from violateMax);
select * from result;