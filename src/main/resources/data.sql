/*
 * Phil, Dan, Nadine, and John passwords are LIT2017
*/

insert into USER (id, first_name, last_name, email, username, password, roles, enabled, non_expired, non_locked) values (1, 'Chris', 'Peckover', 'cjpeckover@hotmail.ca', 'cjpeckover', '$2a$06$tRC3rmZvTdfpb0/Mqcp0FuR/v16iO1h1PUuDn8JjJ0rIaQxfRZI.i', 'CUSTOMER,DRIVER,ADMIN', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, username, password, roles, enabled, non_expired, non_locked) values (2, 'Phil', 'Pedersen', 'phil@pedersen.com', 'phpedersen', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, username, password, roles, enabled, non_expired, non_locked) values (3, 'Daniel', 'Dennis', 'daniel@dennis.com', 'dadennis', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, username, password, roles, enabled, non_expired, non_locked) values (4, 'Nadine', 'Midany', 'nadine@midany.com', 'namidany', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, username, password, roles, enabled, non_expired, non_locked) values (5, 'John', 'Smith', 'john@smith.com', 'josmith', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);