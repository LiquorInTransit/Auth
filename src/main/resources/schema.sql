create table USER (
	id INTEGER PRIMARY KEY,
	fname VARCHAR(50),
	lname VARCHAR(50),
	email VARCHAR(50),
	username VARCHAR(30),
	password VARCHAR(60),
	enabled BOOLEAN,
	non_expired BOOLEAN,
	non_locked BOOLEAN
);