insert into car(id, number, seats) values(1, 'IL11ABC', 3);
insert into driver (id, first_name, last_name) values(1, 'Cosmin', 'Z');
insert into driver_cars (driver_id, cars_id) values(1, 1);
insert into ride (id, status, when, car_id, from_id, to_id) values(1, 'PENDING', current_time(), 1, 1, 2);
