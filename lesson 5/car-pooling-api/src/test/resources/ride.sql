
insert into driver (id, first_name, last_name) values(1, 'Cosmin', 'Z');
insert into car(id, number, seats, driver_id) values(1, 'IL11ABC', 2, 1);
-- insert into driver_cars (driver_id, cars_id) values(1, 1);
insert into ride (id, status, when, car_id, from_id, to_id) values(1, 'PENDING', CURRENT_TIMESTAMP(), 1, 1, 2);
insert into ride_passengers (ride_id, passengers_id) values(1, 1);
insert into ride_passengers (ride_id, passengers_id) values(1, 2);