CREATE TABLE Admin (

	id_admin SERIAL PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	mail VARCHAR(50) NOT NULL,
	hashed_password CHAR(64) NOT NULL,
	is_deleted BOOLEAN NOT NULL DEFAULT false,

	CONSTRAINT correctness_of_username_minimal_length CHECK( LENGTH(username) >= 4 ),
	CONSTRAINT correct_mail_format CHECK( mail LIKE '%@%.%' AND mail NOT LIKE '%@%@%')
	--controllo ci sia una e una sola @ e almeno un punto

);

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Customer (

	id_customer SERIAL PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	mail VARCHAR(50) NOT NULL,
	hashed_password CHAR(64) NOT NULL,
	is_deleted BOOLEAN NOT NULL DEFAULT false,

	CONSTRAINT correctness_of_username_minimal_length CHECK( LENGTH(username) >= 4 ),
	CONSTRAINT correct_mail_format CHECK( mail LIKE '%@%.%' AND mail NOT LIKE '%@%@%')
	--controllo ci sia una e una sola @ e almeno un punto

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNO USERNAME NON SI RIPETE NELLA TABELLA ADMIN TRA GLI ACCOUNT NON CANCELLATI

CREATE OR REPLACE FUNCTION fun_unique_username_admin_not_deleted()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = false THEN

		IF EXISTS(SELECT * FROM ADMIN A
				WHERE A.id_admin <> NEW.id_admin AND A.is_deleted = false AND A.username = NEW.username) THEN 

			RAISE EXCEPTION 'Username % già utilizzato da un altro utente!', NEW.username;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER unique_username_admin_not_deleted
BEFORE INSERT OR UPDATE OF username ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_username_admin_not_deleted();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNA MAIL NON SI RIPETE NELLA TABELLA ADMIN TRA GLI ACCOUNT NON CANCELLATI

CREATE OR REPLACE FUNCTION fun_unique_mail_admin_not_deleted()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = false THEN

		IF EXISTS(SELECT * FROM ADMIN A
				WHERE A.id_admin <> NEW.id_admin AND A.is_deleted = false AND A.mail = NEW.mail) THEN 

			RAISE EXCEPTION 'Mail % già utilizzata da un altro utente!', NEW.mail;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER unique_mail_admin_not_deleted
BEFORE INSERT OR UPDATE OF mail ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_mail_admin_not_deleted();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNO USERNAME NON SI RIPETE NELLA TABELLA CUSTOMER TRA GLI ACCOUNT NON CANCELLATI

CREATE OR REPLACE FUNCTION fun_unique_username_customer_not_deleted()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = false THEN

		IF EXISTS(SELECT * FROM CUSTOMER C
				WHERE C.id_customer <> NEW.id_customer AND C.is_deleted = false AND C.username = NEW.username) THEN 

			RAISE EXCEPTION 'Username % già utilizzato da un altro utente!', NEW.username;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER unique_username_customer_not_deleted
BEFORE INSERT OR UPDATE OF username ON CUSTOMER
FOR EACH ROW
EXECUTE FUNCTION fun_unique_username_customer_not_deleted();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNA MAIL NON SI RIPETE NELLA TABELLA CUSTOMER TRA GLI ACCOUNT NON CANCELLATI

CREATE OR REPLACE FUNCTION fun_unique_mail_customer_not_deleted()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = false THEN

		IF EXISTS(SELECT * FROM CUSTOMER C
				  WHERE C.id_customer <> NEW.id_customer AND C.is_deleted = false AND C.mail = NEW.mail) THEN 

			RAISE EXCEPTION 'Mail % già utilizzata da un altro utente!', NEW.mail;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER unique_mail_customer_not_deleted
BEFORE INSERT OR UPDATE OF mail ON CUSTOMER
FOR EACH ROW
EXECUTE FUNCTION fun_unique_mail_customer_not_deleted();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNO USERNAME NON SI RIPETE TRA LE TABELLE ADMIN E CUSTOMER (LATO ADMIN)

CREATE OR REPLACE FUNCTION fun_unique_username_admin_with_customer()
RETURNS TRIGGER
AS $$
BEGIN

	IF EXISTS(SELECT * FROM CUSTOMER C
			  WHERE C.username = NEW.username AND C.is_deleted = false) THEN 

		RAISE EXCEPTION 'Username % già utilizzato da un altro utente!', NEW.username;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER unique_username_admin_with_customer
BEFORE INSERT OR UPDATE OF username ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_username_admin_with_customer();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNO USERNAME NON SI RIPETE TRA LE TABELLE ADMIN E CUSTOMER (LATO CUSTOMER)

CREATE OR REPLACE FUNCTION fun_unique_username_customer_with_admin()
RETURNS TRIGGER
AS $$
BEGIN

	IF EXISTS(SELECT * FROM ADMIN A
			  WHERE A.username = NEW.username AND A.is_deleted = false) THEN 

		RAISE EXCEPTION 'Username % già utilizzato da un altro utente!', NEW.username;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_username_customer_with_admin
BEFORE INSERT OR UPDATE OF username ON Customer
FOR EACH ROW
EXECUTE FUNCTION fun_unique_username_customer_with_admin();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNA MAIL NON SI RIPETE TRA LE TABELLE ADMIN E CUSTOMER (LATO ADMIN)

CREATE OR REPLACE FUNCTION fun_unique_mail_admin_with_customer()
RETURNS TRIGGER
AS $$
BEGIN

	IF EXISTS(SELECT * FROM CUSTOMER C
			  WHERE C.mail = NEW.mail AND C.is_deleted = false) THEN 

		RAISE EXCEPTION 'Mail % già utilizzato da un altro utente!', NEW.mail;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_mail_admin_with_customer
BEFORE INSERT OR UPDATE OF mail ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_mail_admin_with_customer();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNA MAIL NON SI RIPETE TRA LE TABELLE ADMIN E CUSTOMER (LATO CUSTOMER)

CREATE OR REPLACE FUNCTION fun_unique_mail_customer_with_admin()
RETURNS TRIGGER
AS $$
BEGIN

	IF EXISTS(SELECT * FROM ADMIN A
			  WHERE A.mail = NEW.mail AND A.is_deleted = false) THEN 

		RAISE EXCEPTION 'Mail % già utilizzato da un altro utente!', NEW.mail;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_mail_customer_with_admin
BEFORE INSERT OR UPDATE OF mail ON Customer
FOR EACH ROW
EXECUTE FUNCTION fun_unique_mail_customer_with_admin();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCO LE MODIFICHE SU UN ACCOUNT ADMIN CANCELLATO

CREATE OR REPLACE FUNCTION fun_block_upd_canc_admin()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.is_deleted = true THEN

		RAISE EXCEPTION 'L''account dell''utente % è cancellato, non può essere modificato!', OLD.id_admin;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_upd_canc_admin
BEFORE UPDATE ON ADMIN
FOR EACH ROW
EXECUTE FUNCTION fun_block_upd_canc_admin();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCO LE MODIFICHE SU UN ACCOUNT CUSTOMER CANCELLATO

CREATE OR REPLACE FUNCTION fun_block_upd_canc_customer()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.is_deleted = true THEN

		RAISE EXCEPTION 'L''account dell''utente % è cancellato, non può essere modificato!', OLD.id_customer;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_upd_canc_customer
BEFORE UPDATE ON CUSTOMER
FOR EACH ROW
EXECUTE FUNCTION fun_block_upd_canc_customer();

-------------------------------------------------------------------------------------------------------------------------

CREATE TYPE FlightStatus AS ENUM ('programmed', 'cancelled', 'aboutToDepart', 'departed', 'delayed', 'landed', 'aboutToArrive');

-------------------------------------------------------------------------------------------------------------------------

CREATE DOMAIN Minutes AS int;

-------------------------------------------------------------------------------------------------------------------------

CREATE DOMAIN FlightType AS boolean;	--true = departing, false = arriving

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Flight (

	id_flight VARCHAR(15) PRIMARY KEY,
	company_name VARCHAR(32) NOT NULL,
	departure_time TIMESTAMP NOT NULL,
	arrival_time TIMESTAMP NOT NULL,
	flight_status FlightStatus NOT NULL,
	max_seats SMALLINT NOT NULL,
	free_seats SMALLINT NOT NULL,
	destination_or_origin VARCHAR(64) NOT NULL,
	flight_delay Minutes NOT NULL,
	flight_type FlightType NOT NULL,
	id_gate SMALLINT,
	
	CONSTRAINT id_flight_not_empty CHECK(LENGTH(id_flight) > 0),
	CONSTRAINT comapny_name_not_empty CHECK(LENGTH(company_name) > 0),
	CONSTRAINT arrival_after_departure CHECK( arrival_time > departure_time),
	CONSTRAINT max_seats_positive CHECK(max_seats > 0),
	CONSTRAINT free_seats_not_negative CHECK(free_seats >= 0),
	CONSTRAINT max_free_seats CHECK(free_seats <= max_seats),
	CONSTRAINT destination_or_origin_not_empty CHECK(LENGTH(destination_or_origin) > 0),
	CONSTRAINT destination_or_origin_never_Napoli CHECK(destination_or_origin NOT LIKE 'Napoli'),
	--perché a priori, che sia departing o arriving, memorizziamo sempre l' "altra città", non Napoli
	CONSTRAINT flight_delay_not_negative CHECK(flight_delay >= 0),
	CONSTRAINT correctness_of_id_gate CHECK(id_gate IS NULL OR id_gate BETWEEN 0 AND 19) --l'aeroporto di Napoli ha 20 gate

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SI PUò MODIFICARE ID_FLIGHT DI FLIGHT SOLO FINCHè IL VOLO è PROGRAMMED O CANCELLED

CREATE OR REPLACE FUNCTION fun_block_upd_id_flight_aToDep_or_more()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_status <> 'programmed' AND NEW.flight_status <> 'cancelled' THEN

		IF OLD.id_flight <> NEW.id_flight THEN

			RAISE EXCEPTION 'Il volo % non è in stato ''programmaato'' o ''cancellato'' non può cambiare id!', OLD.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_upd_id_flight_aToDep_or_more
BEFORE UPDATE OF id_flight ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_upd_id_flight_aToDep_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO MODIFICARE IL DEPARTURE TIME E I FREE_SEATS PER UN VOLO DEPARTING DEPARTED O LANDED

CREATE OR REPLACE FUNCTION fun_blocked_upd_departing_dep_time_free_seats_if_dep_land()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = true THEN 

		IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN

			IF OLD.departure_time <> NEW.departure_time OR OLD.free_seats <> NEW.free_seats THEN

				RAISE EXCEPTION 'Il volo % è già partito, non si possono modificare i suoi: orario di partenza, posti liberi!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_departing_dep_time_free_seats_if_dep_land
BEFORE UPDATE OF departure_time, free_seats ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_departing_dep_time_free_seats_if_dep_land();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO MODIFICARE I FREE_SEATS PER UN VOLO ARRIVING DELAYED, ABOUTTOARRIVE O LANDED

CREATE OR REPLACE FUNCTION fun_blocked_upd_arriving_free_seats_if_del_aToArr_land()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = false THEN 

		IF NEW.flight_status = 'delayed' OR NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN

			IF OLD.free_seats <> NEW.free_seats THEN

				RAISE EXCEPTION 'Il volo % è già partito, non si possono modificare i suoi posti liberi!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_arriving_free_seats_if_del_aToArr_land
BEFORE UPDATE OF free_seats ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_arriving_free_seats_if_del_aToArr_land();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò MODIFICARE IL DEPARTURE TIME PER UN VOLO ARRIVING DEPARTED, DELAYED, ABOUTTOARRIVE O LANDED

CREATE OR REPLACE FUNCTION fun_blocked_upd_arriving_dep_time_if_dep_or_more()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = false THEN 

		IF NEW.flight_status = 'departed' OR NEW.flight_status = 'delayed' OR NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN

			IF OLD.departure_time <> NEW.departure_time THEN

				RAISE EXCEPTION 'Il volo % è già partito, non si può modificare il suo orario di partenza!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_arriving_dep_time_if_dep_or_more
BEFORE UPDATE OF departure_time ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_arriving_dep_time_if_dep_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò MODIFICARE L'ARRIVAL TIME PER UN VOLO LANDED

CREATE OR REPLACE FUNCTION fun_blocked_upd_arr_time_if_landed()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_status = 'landed' AND NEW.flight_status = 'landed' THEN

		IF OLD.arrival_time <> NEW.arrival_time THEN

			RAISE EXCEPTION 'Il volo % è già atterrato, non si può modificare il suo orario di arrivo!', OLD.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_arr_time_if_landed
BEFORE UPDATE OF arrival_time ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_arr_time_if_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUÒ MODIFICARE IL FLIGHT_DELAY PER UN VOLO DEPARTING DEPARTED O PIù

CREATE OR REPLACE FUNCTION fun_blocked_upd_departing_delay_if_dep_or_more()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = true THEN 

		IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN

			IF OLD.flight_delay <> NEW.flight_delay THEN

				RAISE EXCEPTION 'Il volo da Napoli % è già partito, non si può modificare il ritardo di partenza!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_departing_delay_if_dep_or_more
BEFORE UPDATE OF flight_delay ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_departing_delay_if_dep_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUÒ MODIFICARE IL FLIGHT_DELAY PER UN VOLO ARRIVING LANDED

CREATE OR REPLACE FUNCTION fun_blocked_upd_arriving_delay_if_dep()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = false THEN 

		IF NEW.flight_status = 'landed' THEN

			IF OLD.flight_delay <> NEW.flight_delay THEN

				RAISE EXCEPTION 'Il volo per Napoli % è già atterrato, non si può modificare il ritardo di arrivo!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_upd_arriving_delay_if_dep
BEFORE UPDATE OF flight_delay ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_upd_arriving_delay_if_dep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO DEPARTING PUò AVERE FLIGHT_STATUS AD ABOUTTODEPART

CREATE OR REPLACE FUNCTION fun_only_a_dep_flight_can_become_aToDepart()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = false THEN

		IF NEW.flight_status = 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo % è verso Napoli, non può avere stato ''in partenza''', NEW.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_a_dep_flight_can_become_aToDepart
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_a_dep_flight_can_become_aToDepart();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO ARRIVING PUò AVERE FLIGHT_STATUS AD ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_only_an_arr_flight_can_become_aToArrive()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = true THEN

		IF NEW.flight_status = 'aboutToArrive' THEN

			RAISE EXCEPTION 'Il volo % è da Napoli, non può avere stato ''in arrivo''', NEW.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_an_arr_flight_can_become_aToArrive
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_an_arr_flight_can_become_aToArrive();

-------------------------------------------------------------------------------------------------------------------------

CREATE TYPE BookingStatus AS ENUM ('confirmed', 'pending', 'cancelled');

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Booking (

	id_booking SERIAL PRIMARY KEY,
	booking_status BookingStatus NOT NULL,
	booking_time TIMESTAMP NOT NULL,
	buyer INTEGER NOT NULL,
	id_flight VARCHAR(15) NOT NULL,

	CONSTRAINT correctness_of_booking_time CHECK( booking_time <= CURRENT_TIMESTAMP),
	CONSTRAINT buyer_FK FOREIGN KEY(buyer) REFERENCES Customer(id_customer) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight) ON DELETE CASCADE ON UPDATE CASCADE

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO MODIFICARE GLI ATTRIBUTI COMPANY_NAME, MAX_SEATS, DESTINATION_OR_ORIGIN, FLIGHT_TYPE 
--DI FLIGHT SE IL VOLO HA ALMENO UNA PRENOTAZIONE ASSOCIATA

CREATE OR REPLACE FUNCTION fun_blocked_updates_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE;

BEGIN

	IF OLD.company_name <> NEW.company_name OR OLD.max_seats <> NEW.max_seats OR OLD.destination_or_origin <> NEW.destination_or_origin OR OLD.flight_type <> NEW.flight_type THEN

		IF EXISTS(SELECT * FROM BOOKING B
				  WHERE B.id_flight = OLD.id_flight) THEN

			RAISE EXCEPTION 'Il volo % ha già almeno una prenotazione associata, non si possono modificare i suoi: nome compagnia, posti massimi, destinazione o origine, tipo!', OLD.id_flight;

		END IF;

	END IF;


	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_updates_flight
BEFORE UPDATE OF company_name, max_seats, destination_or_origin, flight_type ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_updates_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER ID BOOKING IN BOOKING SI PUò MODIFICARE SOLO SE IL VOLO ASSOCIATO è PROGRAMMED O CANCELLED

CREATE OR REPLACE FUNCTION fun_block_mod_id_booking_if_f_not_prog_canc()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
										 WHERE F.id_flight = OLD.id_flight);

BEGIN
	
	IF OLD.id_booking <> NEW.id_booking THEN

		IF associated_flight.flight_status <> 'programmed' AND associated_flight.flight_status <> 'cancelled' THEN 

			RAISE EXCEPTION 'Il volo %, associato alla prenotazione %, non è in stato ''programmato'' o ''cancellato'', non si può modificare l''id della prenotazione!', 
																																associated_flight.id_flight, OLD.id_booking;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_id_booking_if_f_not_prog_canc
BEFORE UPDATE OF id_booking ON Booking
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_id_booking_if_f_not_prog_canc();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò MAI MODIFICARE L'ATTRIBUTO BOOKING_TIME DI BOOKING

CREATE OR REPLACE FUNCTION fun_block_mod_booking_time()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF OLD.booking_time <> NEW.booking_time THEN

		RAISE EXCEPTION 'Non è possibile modificare il momento in cui è avvenuta la prenotazione!';

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_booking_time
BEFORE UPDATE OF booking_time ON Booking
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_booking_time();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO è aboutToDepart, DEPARTED, aboutToArrive o  LANDED, LE PRENOTAZIONI NON POSSONO PIÙ ESSERE MODIFICATE (NEMMENO CANCELLATE)

CREATE OR REPLACE FUNCTION fun_block_mod_booking_if_flight_aToDep_dep_aToArr_landed()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = OLD.id_flight);

BEGIN
	

	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo % è in partenza/partito/sta per atterrare/atterrato, non si può modificare la prenotazione!', NEW.id_flight;

	END IF;


	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_booking_if_flight_aToDep_dep_aToArr_landed
BEFORE UPDATE ON Booking
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_booking_if_flight_aToDep_dep_aToArr_landed();

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Passenger (

	
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	birth_date DATE,
	SSN VARCHAR(16) PRIMARY KEY,

	CONSTRAINT first_name_not_empty CHECK(first_name IS NULL OR LENGTH(first_name) > 0),
	CONSTRAINT last_name_not_empty CHECK(last_name IS NULL OR LENGTH(last_name) > 0)

);

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Ticket (
	
	ticket_number CHAR(13) PRIMARY KEY,
	seat INTEGER,
	checked_in BOOLEAN NOT NULL DEFAULT false,
	id_booking INTEGER NOT NULL,
	id_passenger VARCHAR(16) NOT NULL,
	id_flight  VARCHAR(15) NOT NULL,

	CONSTRAINT booking_FK FOREIGN KEY(id_booking) REFERENCES Booking(id_booking) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT passenger_FK FOREIGN KEY(id_passenger) REFERENCES Passenger(SSN) ON DELETE RESTRICT ON UPDATE CASCADE,
	--ON DELETE RESTRICT, quindi un passeggero non può essere cancellato finchè ha ancora almeno un biglietto ad esso associato
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT numeric_ticket_number CHECK( ticket_number ~ '^[0-9]+$' )

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER UN DATO BIGLIETTO, ID_FLIGHT è LO STESSO DELLA PRENOTAZIONE ASSOCIATA (lato BOOKING)

CREATE OR REPLACE FUNCTION fun_correctness_of_id_flight_booking_with_tickets()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF OLD.id_flight <> NEW.id_flight THEN

		IF EXISTS(SELECT * FROM TICKET T
				  WHERE T.id_booking = OLD.id_booking AND T.id_flight <> NEW.id_flight) THEN

			RAISE EXCEPTION 'La prenotazione % ha almeno un biglietto associato che si riferisce ad un altro volo, non è possibile renderla una prenotazione per il volo %!', 
																																				OLD.id_booking, NEW.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER correctness_of_id_flight_booking_with_tickets
BEFORE UPDATE OF id_flight ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_correctness_of_id_flight_booking_with_tickets();

--Qui non serve il BEFORE INSERT, perchè non posso avere inserito TICKET per una prenotazione che ancora non c'era

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER UN DATO BIGLIETTO, ID_FLIGHT è LO STESSO DELLA PRENOTAZIONE ASSOCIATA (lato TICKET)

CREATE OR REPLACE FUNCTION fun_correctness_of_id_flight_ticket_with_booking()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
										   WHERE B.id_booking = OLD.id_booking);

BEGIN
	
	IF OLD.id_flight <> NEW.id_flight THEN

		IF NEW.id_flight <> associated_booking.id_flight THEN

			RAISE EXCEPTION 'Il biglietto con numero % è associato ad una prenotazione per un altro volo, non è possibile renderl un biglietto per il volo %!', 
																																OLD.ticket_number, NEW.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER correctness_of_id_flight_ticket_with_booking
BEFORE UPDATE OF id_flight ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_correctness_of_id_flight_ticket_with_booking();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER TICKET_NUMBER IN TICKET SI PUò MODIFICARE SOLO SE IL VOLO ASSOCIATO è PROGRAMMED O CANCELLED

CREATE OR REPLACE FUNCTION fun_block_mod_ticket_number_if_f_not_prog_canc()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
										 WHERE F.id_flight = OLD.id_flight);

BEGIN
	
	IF OLD.ticket_number <> NEW.ticket_number THEN

		IF associated_flight.flight_status <> 'programmed' AND associated_flight.flight_status <> 'cancelled' THEN 

			RAISE EXCEPTION 'Il volo %, associato al biglietto con numero %, non è in stato ''programmato'' o ''cancellato'', non si può modificare il numero del biglietto!', 
																																associated_flight.id_flight, OLD.ticket_number;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_ticket_number_if_f_not_prog_canc
BEFORE UPDATE OF ticket_number ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_ticket_number_if_f_not_prog_canc();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE SI FA DELETE DI UN TICKET, CONTROLLO LA PRENOTAZIONE ASSOCIATA E IL PASSEGGERO ASSOCIATO, 
--PER ENNTRAMBI, SE NON HANNO ALTRI TICKET, SI FA DELETE ANCHE DI LORO

CREATE OR REPLACE FUNCTION fun_check_if_del_pass_and_booking_after_del_ticket()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
										   WHERE B.id_booking = OLD.id_booking);

	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
										   	   WHERE P.SSN = OLD.id_passenger);

BEGIN
	

	IF NOT EXISTS(SELECT * FROM TICKET T
				  WHERE T.id_booking = associated_booking.id_booking) THEN
				  --non devo fare il controllo su T.ticket_number <> OLD.ticket_number perchè il trigger è AFTER DELETE,
				  --il biglietto è già stato cancellato

		DELETE
		FROM BOOKING
		WHERE id_booking = associated_booking.id_booking;

	END IF;

	IF NOT EXISTS(SELECT * FROM TICKET T
				  WHERE T.id_passenger = associated_passenger.SSN) THEN
				  --non devo fare il controllo su T.ticket_number <> OLD.ticket_number perchè il trigger è AFTER DELETE,
				  --il biglietto è già stato cancellato

		DELETE
		FROM PASSENGER
		WHERE SSN = associated_passenger.SSN;

	END IF;

	RETURN OLD;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_if_del_pass_and_booking_after_del_ticket
AFTER DELETE ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_check_if_del_pass_and_booking_after_del_ticket();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSTI SONO INTERI DA 0 A ASSOCIATED_FLIGHT.MAX_SEATS - 1

CREATE OR REPLACE FUNCTION fun_valid_ticket_seat()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					     				 WHERE F.id_flight = NEW.id_flight);

BEGIN

	IF NEW.seat IS NOT NULL THEN
 
		IF NOT (NEW.seat BETWEEN 0 AND associated_flight.max_seats - 1) THEN
			
			RAISE EXCEPTION 'Posto non valido per il volo %', NEW.id_flight;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_ticket_seat
BEFORE INSERT OR UPDATE OF seat ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_valid_ticket_seat();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER I PASSEGERI POSSONO AVERE QUALUNQUE DATO (TRANNE L'SSN PK) A NULL, MA QUESTO SOLO SE NESSUNA LORO PRENOTAZIONE È 'CONFFIRMED', 
--SE INVECE ALMENO UNA LO È, NIENTE DEVE ESSERE NULL

CREATE OR REPLACE FUNCTION fun_valid_passenger_data()
RETURNS TRIGGER
AS $$
DECLARE

	selected_ticket TICKET%ROWTYPE;
	associated_booking BOOKING%ROWTYPE;

BEGIN

	IF NEW.first_name IS NULL OR NEW.last_name IS NULL OR NEW.birth_date IS NULL THEN
	--solo se effettivamente qualcosa è a null faccio il controllo

		FOR selected_ticket IN (SELECT * FROM TICKET T
								WHERE T.id_passenger = NEW.SSN) LOOP

			associated_booking := (SELECT * FROM BOOKING B
								   WHERE B.id_booking = selected_ticket.id_booking);

			IF associated_booking.booking_status = 'confirmed' THEN

				RAISE EXCEPTION 'Dati mancanti per il passeggero il cui biglietto ha numero %, per la prenotazione %', 
															selected_ticket.ticket_number, associated_booking.id_booking;

			END IF;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_passenger_data
BEFORE INSERT OR UPDATE ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_valid_passenger_data();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER PRIMA DI METTERE UN PRENOTAZIONE A CONFERMATA, CONTROLLO CHE TUTTI I PASSEGGERI AD ESSA ASSOCIATA NON ABBIANO DATI NULL

CREATE OR REPLACE FUNCTION fun_check_passenger_data_before_conf_booking()
RETURNS TRIGGER
AS $$
DECLARE

	selected_ticket TICKET%ROWTYPE;
	associated_passenger PASSENGER%ROWTYPE;

BEGIN

	IF OLD.booking_status <> 'confirmed' AND NEW.booking_status = 'confirmed' THEN

		FOR selected_ticket IN (SELECT * FROM TICKET T
								WHERE T.id_booking = OLD.id_booking) LOOP

			associated_passenger := (SELECT * FROM PASSENGER P
								     WHERE P.SSN = selected_ticket.id_passenger);

			IF associated_passenger.first_name IS NULL OR associated_passenger.last_name IS NULL OR associated_passenger.birth_date IS NULL THEN

				RAISE EXCEPTION 'Dati mancanti per il passeggero il cui biglietto ha numero %, la prenotazione % non può essere confermata!', 
																								selected_ticket.ticket_number, NEW.id_booking;

			END IF;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_passenger_data_before_conf_booking
BEFORE UPDATE OF booking_status ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_check_passenger_data_before_conf_booking();

-------------------------------------------------------------------------------------------------------------------------


--TRIGGER SE FACCIO IL CHECK-IN (OSSIA SE CHECK-IN = TRUE), ALLORA SE POSTO È NULL, ASSEGNO AL TICKET IL PRIMO POSTO LIBERO 
--(E QUESTO TRIGGER È BEFORE INSERT OR UPDATE OF CHECKED_IN, SEAT; COSÌ NON PUÒ MAI ACCADERE CHE UN TICKET CON CHECK-IN TRUE NON ABBIA POSTO)

CREATE OR REPLACE FUNCTION fun_valid_seat_after_check_in()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     WHERE id_flight = NEW.id_flight);

	prev_seat TICKET.seat%TYPE := -1; -- (-1) perché i posti iniziano da 0
	
	selected_seat TICKET.seat%TYPE;

BEGIN

	IF NEW.checked_in = true AND NEW.seat IS NULL THEN
 	
		FOR selected_seat IN (SELECT T.seat FROM TICKET T
				    		  WHERE T.ticket_number <> NEW.ticket_number AND T.id_flight = NEW.id_flight AND 
								    (SELECT B.booking_status FROM BOOKING B
									 WHERE B.id_booking = T.id_booking) <> 'cancelled' AND T.seat IS NOT NULL
					 		  ORDER BY T.seat) LOOP
			
			IF selected_seat = prev_seat + 1 THEN
				
				prev_seat := selected_seat;
			
			ELSE
				
				NEW.seat := prev_seat + 1;
				EXIT;
			
			END IF;
				
			
		END LOOP;

		IF NEW.seat IS NULL THEN	--se non c'era un 'buco' tra i posti disponibili, assegno il posto fuori dal loop
			
			NEW.seat := prev_seat + 1;
		
		END IF;
		
		IF NEW.seat >= associated_flight.max_seats THEN		--controllo di non aver sforato il posto massimo
			
			RAISE EXCEPTION 'Errore nell''assegnazione del posto per il passeggero con ticket_number: %, per il volo: %', NEW.ticket_number, NEW.id_flight;
		
		END IF;


	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_seat_after_check_in
BEFORE INSERT OR UPDATE OF checked_in, seat ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_valid_seat_after_check_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE ALMENO UN VOLO DI UN PASSEGGERO NON È IN STATO PROGRAMMED O CANCELLED, I DATI DEL PASSEGGERO NON POSSONO PIÙ ESSERE MODIFICATI

CREATE OR REPLACE FUNCTION fun_block_upd_pass_if_flight_departed_aToArr_landed()
RETURNS TRIGGER
AS $$
DECLARE

	selected_ticket TICKET%ROWTYPE;
	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	IF NEW.first_name <> OLD.first_name OR NEW.last_name <> OLD.last_name OR NEW.birth_date <> OLD.birth_date OR NEW.SSN <> OLD.SSN THEN

		FOR selected_ticket IN (SELECT * FROM TICKET T
								WHERE T.id_passenger = OLD.SSN) LOOP
								--uso old perchè potenzialmente NEW.SSN <> OLD.SSN

			associated_flight := (SELECT * FROM FLIGHT F
				    			 WHERE F.id_flight = selected_ticket.id_flight);
			
			IF associated_flight.flight_status <> 'programmed' AND associated_flight.flight_status <> 'cancelled' THEN

				RAISE EXCEPTION 'Il volo % è in stato %, non si possono modificare i dati del passeggero con SSN %!', 
															  associated_flight.id_flight, associated_flight.flight_status, NEW.SSN;

			END IF;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_upd_pass_if_flight_departed_aToArr_landed
BEFORE UPDATE ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_block_upd_pass_if_flight_departed_aToArr_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO È aboutToDepart, PUÒ ESSERE MOFIFICATO SOLO IL POSTO O CHECKED_IN DI UN TICKET

CREATE OR REPLACE FUNCTION fun_only_mod_seat_checked_in_if_flight_aToDep()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					     				 WHERE F.id_flight = OLD.id_flight);

BEGIN
	
	IF associated_flight.flight_status = 'aboutToDepart' THEN

		IF OLD.ticket_number <> NEW.ticket_number OR OLD.id_booking <> NEW.id_booking OR OLD.id_passenger <> NEW.id_passenger OR OLD.id_flight <> NEW.id_flight THEN
		
			RAISE EXCEPTION 'Il volo % è in stato ''in partenza'', per il biglietto con ticket_number % si può solo modificare il posto o fare il check-in!',
																															NEW.id_flight, NEW.ticket_number;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_mod_seat_checked_in_if_flight_aToDep
BEFORE UPDATE ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_only_mod_seat_checked_in_if_flight_aToDep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO È DEPARTED o aboutToArrive O LANDED, I DATI DI UN TICKET NON POSSONO PIÙ ESSERE MODIFICATI

CREATE OR REPLACE FUNCTION fun_block_mod_ticket_if_flight_dep_or_more()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					     				 WHERE F.id_flight = OLD.id_flight);

BEGIN
	
	IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN


		RAISE EXCEPTION 'Il volo % è in stato %, non si possono modificare i dati del biglietto con ticket_number %!',
														NEW.id_flight, associated_flight.flight_status, NEW.ticket_number;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_ticket_if_flight_dep_or_more
BEFORE UPDATE ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_ticket_if_flight_dep_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON CI SONO BIGLIETTI PER LO STESSO POSTO SU UN DATO VOLO

CREATE OR REPLACE FUNCTION fun_unique_ticket_per_seat_per_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     				   WHERE B.id_booking = NEW.id_booking);

BEGIN
	
	IF associated_booking.booking_status <> 'cancelled' AND NEW.seat IS NOT NULL THEN

		IF EXISTS(SELECT * FROM TICKET T
		  	      WHERE T.ticket_number <> NEW.ticket_number AND T.id_flight = NEW.id_flight AND T.seat = NEW.seat 
				  AND (SELECT B.booking_status FROM BOOKING B
				       WHERE B.id_booking = T.id_booking) <> 'cancelled' ) THEN
		
			RAISE EXCEPTION 'Posto già occupato per il volo %', NEW.id_flight;

		END IF;
	END IF;
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_ticket_per_seat_per_flight
BEFORE INSERT OR UPDATE OF seat ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_unique_ticket_per_seat_per_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER CHE AGGIORNA I FREE_SEATS DI UN VOLO ALL'INSERIMENTO DI UN BIGLIETTO (DI UNA PRENOTAZIONE NON CANCELLATA) PER QUEL VOLO

CREATE OR REPLACE FUNCTION fun_if_ticket_inserted_upd_free_seats()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     				   WHERE B.id_booking = NEW.id_booking);


BEGIN
	
	IF associated_booking.booking_status <> 'cancelled' THEN

		IF associated_flight.free_seats = 0 THEN
	
			RAISE EXCEPTION 'Volo % pieno!', NEW.id_flight;
	
		END IF;
	
	
		UPDATE FLIGHT
		SET free_seats = free_seats - 1
		WHERE id_flight = NEW.id_flight;


	END IF;
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER if_ticket_inserted_upd_free_seats
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_if_ticket_inserted_upd_free_seats();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO PER LE PRENOTAZIONI 'CONFIRMED' UN BIGLIETTO PUò ESSERE CHECKED_IN

CREATE OR REPLACE FUNCTION fun_check_ticket_checked_in_only_if_conf_book()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     				   WHERE B.id_booking = NEW.id_booking);

BEGIN
	
	IF NEW.checked_in = true THEN

		IF associated_booking.booking_status <> 'confirmed' THEN

			RAISE EXCEPTION 'La prenotazione % non è confermata, non si può fare il check-in!', NEW.id_booking;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_ticket_checked_in_only_if_conf_book
BEFORE INSERT OR UPDATE OF checked_in ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_check_ticket_checked_in_only_if_conf_book();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO PER I VOLI aboutToDepart, DEPARTED, aboutToArrive o LANDED UN BIGLIETTO PUÒ ESSERE CHECKED_IN

CREATE OR REPLACE FUNCTION fun_check_ticket_checked_in_only_if_flight_aToDep_dep_lan()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

BEGIN
	
	IF NEW.checked_in = true THEN

		IF associated_flight.flight_status <> 'aboutToDepart' AND associated_flight.flight_status <> 'departed' AND associated_flight.flight_status <> 'aboutToArrive' AND associated_flight.flight_status <> 'landed' THEN

			RAISE EXCEPTION 'Il volo % non è in partenza/partito/sta per atterrare/atterrato, non si può essere checked-in!', NEW.id_flight;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_ticket_checked_in_only_if_flight_aToDep_dep_lan
BEFORE INSERT OR UPDATE OF checked_in ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_check_ticket_checked_in_only_if_flight_aToDep_dep_lan();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL CHECK-IN È ANNULLABILE SOLO SE IL VOLO NON È DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_check_canc_check_in_only_if_flight_not_dep_lan()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = OLD.id_flight);

BEGIN
	
	IF OLD.checked_in = true AND NEW.checked_in = false THEN

		IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

			RAISE EXCEPTION 'Il volo % è già partito, non si può annullare il check-in!', NEW.id_flight;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_canc_check_in_only_if_flight_not_dep_lan
BEFORE UPDATE OF checked_in ON Ticket
FOR EACH ROW
EXECUTE FUNCTION fun_check_canc_check_in_only_if_flight_not_dep_lan();

-------------------------------------------------------------------------------------------------------------------------

CREATE TYPE LuggageStatus AS ENUM ('booked', 'loaded', 'withdrawable', 'lost');

-------------------------------------------------------------------------------------------------------------------------

CREATE TYPE LuggageType AS ENUM ('carry_on', 'checked');

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Luggage (

	id_luggage SERIAL PRIMARY KEY,
	id_luggage_after_check_in VARCHAR(20) UNIQUE,
	luggage_type LuggageType,
	luggage_status LuggageStatus NOT NULL,
	id_ticket CHAR(13) NOT NULL,


	CONSTRAINT ticket_FK FOREIGN KEY(id_ticket) REFERENCES Ticket(ticket_number) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT correctness_of_id_luggage_after_check_in_minimal_length CHECK( id_luggage_after_check_in IS NULL OR LENGTH(id_luggage_after_check_in) > 13)
	--questa check serve per come è costruito id_luggage_after_check_in, 
	--ossia come concatenazione di ticket_number del passeggero associato + 'numero del bagaglio'
);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER ID LUGGAGE E ID TICKET IN LUGGAGE SI POSSONO MODIFICARE SOLO SE IL VOLO ASSOCIATO è PROGRAMMED

CREATE OR REPLACE FUNCTION fun_block_mod_id_lug_ticket_if_f_not_prog_canc()
RETURNS TRIGGER
AS $$
DECLARE

	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
										 WHERE T.ticket_number = OLD.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	IF (OLD.id_luggage <> NEW.id_luggage) OR (OLD.id_ticket <> NEW.id_ticket) THEN

		associated_flight := (SELECT * FROM FLIGHT F
							  WHERE F.id_flight = associated_ticket.id_flight);

		IF associated_flight.flight_status <> 'programmed' AND associated_flight.flight_status <> 'cancelled' THEN 

			RAISE EXCEPTION 'Il volo %, associato al bagaglio %, non è in stato ''programmato'' o ''cancellato'', non si può modificare l''id del bagaglio o il suo biglietto associato!', 
																																				associated_flight.id_flight, OLD.id_luggage;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_id_lug_ticket_if_f_not_prog_canc
BEFORE UPDATE OF id_luggage, id_ticket ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_id_lug_ticket_if_f_not_prog_canc();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UNA VOLTA GENERATO, NON SI PUò MODIFICARE id_luggage_after_check_in

CREATE OR REPLACE FUNCTION fun_block_mod_id_lug_after_check_in_after_generation()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF (OLD.id_luggage_after_check_in IS NOT NULL) AND (OLD.id_luggage_after_check_in <> NEW.id_luggage_after_check_in) THEN

		RAISE EXCEPTION 'Per il bagaglio % è già stato generato l''id after check-in, non si può modificarlo!', OLD.id_luggage;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_id_lug_after_check_in_after_generation
BEFORE UPDATE OF id_luggage_after_check_in ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_id_lug_after_check_in_after_generation();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER I BAGAGLI, SE LA PRENOTAZIONE DEL BIGLIETTO ASSOCIATO NON è CONFIRMED, IL TIPO PUò ESSERE A NULL, MA SE è CONFIRMED NO

CREATE OR REPLACE FUNCTION fun_valid_luggage_type()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_booking BOOKING%ROWTYPE;

BEGIN
	
	associated_booking := (SELECT * FROM BOOKING B
			      		   WHERE B.id_booking = associated_ticket.id_booking);

	IF associated_booking.booking_status = 'confirmed' THEN
 
		IF NEW.luggage_type IS NULL THEN
			
			RAISE EXCEPTION 'Dati mancanti per il bagaglio % del passeggero il cui biglietto ha numero %', NEW.id_luggage, associated_ticket.ticket_number;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_luggage_type
BEFORE INSERT OR UPDATE ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_valid_luggage_type();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE IL BLIGLIETTO NON è CHECKED_IN, IL LUGGAGE_STATUS PUò ESSERE SOLO BOOKED

CREATE OR REPLACE FUNCTION fun_lug_status_only_booked_if_booking_canc()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

BEGIN

	IF associated_ticket.checked_in = false THEN
 
		IF NEW.luggage_status <> 'booked' THEN
			
			RAISE EXCEPTION 'Il biglietto con ticket number % non ha ancora fatto il check_in, il bagaglio % non può avere stato divero da ''prenotato''!', associated_ticket.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER lug_status_only_booked_if_booking_canc
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_lug_status_only_booked_if_booking_canc();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO PER UN BIGLIETTO VIENE FATTO IL CHECK-IN, VIENE GENERATO L'id_luggage_after_check_in PER TUTTI I SUOI BAGAGLI
--(VIENE GENERATO COME ticket_number concatenato un intero da 0 a numero di bagagli del passeggero - 1)

CREATE OR REPLACE FUNCTION fun_generation_of_id_luggage_after_check_in()
RETURNS TRIGGER
AS $$
DECLARE

	i INTEGER := 0;

	selected_luggage LUGGAGE%ROWTYPE;

BEGIN
	
	IF OLD.checked_in = false AND NEW.checked_in = true THEN

		FOR selected_luggage IN (SELECT * FROM LUGGAGE L
								 WHERE L.id_ticket = NEW.ticket_number) LOOP

			UPDATE LUGGAGE
			SET id_luggage_after_check_in = NEW.ticket_number || i
			WHERE id_luggage = selected_luggage.id_luggage;

			i := i + 1;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER generation_of_id_luggage_after_check_in
AFTER UPDATE OF checked_in ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_generation_of_id_luggage_after_check_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SU id_luggage_after_check_in NOT NULL SE IL SUO BIGLIETTO è CHECKED-IN

CREATE OR REPLACE FUNCTION fun_check_id_lug_after_check_in_not_null_if_ticket_checked_in()
RETURNS TRIGGER
AS $$
DECLARE

	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

BEGIN
	
	IF associated_ticket.checked_in = true THEN

		IF NEW.id_luggage_after_check_in IS NULL THEN

			RAISE EXCEPTION 'Il biglietto con ticket number % ha efettuato il check_in, il suo bagaglio non può non avere id_luggage_after_check_in!', 
																														associated_ticket.ticket_number;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_id_lug_after_check_in_not_null_if_ticket_checked_in
BEFORE INSERT OR UPDATE OF id_luggage_after_check_in ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_check_id_lug_after_check_in_not_null_if_ticket_checked_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS (DEI BAGAGLI DELLE PRENOTAZIONI NON CANCELLATE) NON PUÒ ESSERE BOOKED SE IL VOLO È DEPARTED, ABOUTTOARRIVE O LANDED

CREATE OR REPLACE FUNCTION fun_valid_luggage_status_after_departure()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_booking BOOKING%ROWTYPE;
	associated_flight FLIGHT%ROWTYPE;

BEGIN

	IF NEW.luggage_status = 'booked' THEN

		associated_booking := (SELECT * FROM BOOKING B
						   	   WHERE B.id_booking = associated_ticket.id_booking);

		IF associated_booking.booking_status <> 'cancelled' THEN

			associated_flight := (SELECT * FROM FLIGHT F
								WHERE F.id_flight = associated_ticket.id_flight);

			IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN
	
				RAISE EXCEPTION 'Il volo % del passeggero con ticket number % è già partito, il bagaglio % non può avere stato ''prenotato''!', 
																	associated_ticket.id_flight, associated_ticket.ticket_number, NEW.id_luggage;
		
			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_luggage_status_after_departure
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_valid_luggage_status_after_departure();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE withdrawable SE IL VOLO NON è LANDED

CREATE OR REPLACE FUNCTION fun_luggage_not_withd_if_flight_not_landed()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	IF NEW.luggage_status = 'withdrawable' THEN
		
		associated_flight := (SELECT * FROM FLIGHT F
			      			  WHERE F.id_flight = associated_ticket.id_flight);

		IF associated_flight.flight_status <> 'landed' THEN
			
			RAISE EXCEPTION 'Il volo % del passeggero con ticket number % non è ancora atterrato, il bagaglio % non può avere stato ''ritirabile''!', 
																			associated_ticket.id_flight, associated_ticket.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_not_withd_if_flight_not_landed
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_not_withd_if_flight_not_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE LOST SE IL VOLO NON è LANDED

CREATE OR REPLACE FUNCTION fun_luggage_not_lost_if_flight_not_landed()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	IF NEW.luggage_status = 'lost' THEN

		associated_flight := (SELECT * FROM FLIGHT F
			      			  WHERE F.id_flight = associated_ticket.id_flight);
 
		IF associated_flight.flight_status <> 'landed' THEN
			
			RAISE EXCEPTION 'Il volo % del passeggero con ticket number % non è ancora atterrato, il bagaglio % non può avere stato ''smarrito''!', 
																			associated_ticket.id_flight, associated_ticket.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_not_lost_if_flight_not_landed
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_not_lost_if_flight_not_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE LOADED SE IL VOLO è PROGRAMMED, CANCELLED O LANDED

CREATE OR REPLACE FUNCTION fun_luggage_not_loaded_if_flight_prog_canc_land()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	IF NEW.luggage_status = 'loaded' THEN

		associated_flight := (SELECT * FROM FLIGHT F
			      			  WHERE F.id_flight = associated_ticket.id_flight);

		IF associated_flight.flight_status = 'programmed' OR associated_flight.flight_status = 'cancelled' OR associated_flight.flight_status = 'landed' THEN
			
			RAISE EXCEPTION 'Il volo % del passeggero con ticket number % è progammato/cancellato/atterrato, il bagaglio % non può avere stato ''caricato''!', 
																					associated_ticket.id_flight, associated_ticket.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_not_loaded_if_flight_prog_canc_land
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_not_loaded_if_flight_prog_canc_land();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE DIVERSO DA BOOKED SE LA PRENOTAZIONE è CANCELLED

CREATE OR REPLACE FUNCTION fun_luggage_status_only_booked_if_book_canc()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_booking BOOKING%ROWTYPE;

BEGIN
	
	IF NEW.luggage_status <> 'booked' THEN

		associated_booking := (SELECT * FROM BOOKING B
			      			   WHERE B.id_booking = associated_ticket.id_booking);

		IF associated_booking.booking_status = 'cancelled' THEN
				
			RAISE EXCEPTION 'La prenotazione % del passeggero con ticket number % è cancellata, il bagaglio % non può avere stato divero da ''prenotato''!', 
																				associated_ticket.id_booking, associated_ticket.ticket_number, NEW.id_luggage;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_status_only_booked_if_book_canc
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_only_booked_if_book_canc();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS BOOKED PUò DIVENTARE LOADED

CREATE OR REPLACE FUNCTION fun_lug_status_can_become_loaded_only_if_booked()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.luggage_status <> OLD.luggage_status THEN
 
		IF NEW.luggage_status = 'loaded' AND OLD.luggage_status <> 'booked' THEN
			
			RAISE EXCEPTION 'Il bagaglio % non era in stato ''prenotato'', non può diventare ''caricato''!', OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER lug_status_can_become_loaded_only_if_booked
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_lug_status_can_become_loaded_only_if_booked();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS LOADED PUò DIVENTARE withdrawable

CREATE OR REPLACE FUNCTION fun_lug_status_can_become_withd_only_if_loaded()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.luggage_status <> OLD.luggage_status THEN
 
		IF NEW.luggage_status = 'withdrawable' AND OLD.luggage_status <> 'loaded' THEN
			
			RAISE EXCEPTION 'Il bagaglio % non era in stato ''caricato'', non può diventare ''ritirabile''!', OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER lug_status_can_become_withd_only_if_loaded
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_lug_status_can_become_withd_only_if_loaded();

----------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS withdrawable PUò DIVENTARE LOST

CREATE OR REPLACE FUNCTION fun_lug_status_can_become_lost_only_if_withd()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.luggage_status <> OLD.luggage_status THEN
 
		IF NEW.luggage_status = 'lost' AND OLD.luggage_status <> 'withdrawable' THEN
			
			RAISE EXCEPTION 'Il bagaglio % non era in stato ''ritirabile'', non può diventare ''smarrito''!', OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER lug_status_can_become_lost_only_if_withd
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_lug_status_can_become_lost_only_if_withd();

----------------------------------------------------------------------------------------------

--TRIGGER QUANDO UN VOLO DEPARTING PARTE, TUTTI I LUGGAGE_STATUS DEI BAGAGLI DEI SUOI BIGLIETTI CON CHECKED_IN A TRUE VENGONO MESSI A LOADED

CREATE OR REPLACE FUNCTION fun_luggages_loaded_when_depart()
RETURNS TRIGGER
AS $$
DECLARE

	selected_booking BOOKING%ROWTYPE;
	selected_ticket TICKET%ROWTYPE;
	selected_luggage LUGGAGE%ROWTYPE;

BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF OLD.flight_status <> 'departed' AND NEW.flight_status = 'departed' THEN

			--questo if serve perché solo un volo aboutToDepart può essere impostato a departed
			IF OLD.flight_status <> 'aboutToDepart' THEN

				RAISE EXCEPTION 'Il volo da Napoli % non era in stato ''in partenza'', non può diventare ''partito''!', OLD.id_flight;

			END IF;

			FOR selected_booking IN (SELECT * FROM BOOKING B
									 WHERE B.id_flight = NEW.id_flight AND B.booking_status <> 'cancelled') LOOP

					
				--non devo controllare la prenotazione non sia pending, 
				--perchè tanto il volo era per forza aboutToDepart, e quindi le sue prenotazioni già non potevano essere pending
				FOR selected_ticket IN (SELECT * FROM TICKET T
											WHERE T.id_booking = selected_booking.id_booking AND T.checked_in = true) LOOP

					FOR selected_luggage IN (SELECT * FROM LUGGAGE L
											 WHERE L.id_ticket = selected_ticket.ticket_number) LOOP

						UPDATE LUGGAGE
						SET luggage_status = 'loaded'
						WHERE id_luggage = selected_luggage.id_luggage;

					END LOOP;

				END LOOP;

					

			END LOOP;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggages_loaded_when_depart
AFTER UPDATE OF flight_status ON Flight
FOR EACH ROW
EXECUTE FUNCTION fun_luggages_loaded_when_depart();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO UN VOLO ATTERRA, TUTTI I LUGGAGE_STATUS DEI BAGAGLI DEI SUOI BIGLIETTI CHECKED_IN VENGONO MESSI A withdrawable

CREATE OR REPLACE FUNCTION fun_luggages_withdrawable_when_landed()
RETURNS TRIGGER
AS $$
DECLARE

	selected_booking BOOKING%ROWTYPE;
	selected_ticket TICKET%ROWTYPE;
	selected_luggage LUGGAGE%ROWTYPE;

BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF OLD.flight_status <> 'landed' AND NEW.flight_status = 'landed' THEN 

			--questo if serve perché solo un volo departed o aboutToArrive può essere impostato a departed
			IF OLD.flight_status <> 'departed' AND OLD.flight_status <> 'aboutToArrive' THEN

				RAISE EXCEPTION 'Il volo % non era in stato ''partito'' o ''sta per arrivare'', non può diventare ''atterrato''!', OLD.id_flight;

			END IF;

			FOR selected_booking IN (SELECT * FROM BOOKING B
										WHERE B.id_flight = NEW.id_flight AND B.booking_status <> 'cancelled') LOOP

					
				--non devo controllare la prenotazione non sia pending, 
				--perchè tanto il volo era già partito, e quindi le sue prenotazioni già non potevano essere pending
				FOR selected_ticket IN (SELECT * FROM TICKET T
											WHERE T.id_booking = selected_booking.id_booking AND T.checked_in = true) LOOP

					FOR selected_luggage IN (SELECT * FROM LUGGAGE L
												WHERE L.id_ticket = selected_ticket.ticket_number) LOOP

						UPDATE LUGGAGE
						SET luggage_status = 'withdrawable'
						WHERE id_luggage = selected_luggage.id_luggage;

					END LOOP;

				END LOOP;

			END LOOP;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggages_withdrawable_when_landed
AFTER UPDATE OF flight_status ON Flight
FOR EACH ROW
EXECUTE FUNCTION fun_luggages_withdrawable_when_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO PUÒ ESSERE aboutToDepart O DEPARTED O aboutToArrive O LANDED SOLO SE FLIGHT.DEPARTURE_TIME::DATE <= DATA_CORRENTE (IMPLEMENTATO CON IF (aboutToDepart O DEPARTED O LANDED) THEN IF DATA > DATA_CORRENTE THEN RAISE EXCEPTION )

CREATE OR REPLACE FUNCTION fun_check_date_before_aToDep_or_more()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF NEW.flight_status = 'aboutToDepart' THEN
	
		IF (NEW.departure_time::date) > CURRENT_DATE + 1 THEN
		--posso aprire i check-in (e quindi mettere ad aboutToDepart) al più il giorno prima della partenza del volo
		--e quindi non posso aprirli solo se NEW.departure_time::date non è nè oggi nè domani

			RAISE EXCEPTION 'Non possono ancora essere aperti i check-in per il volo %! La sua data di partenza è % con ritardo di % minuti!', NEW.id_flight, NEW.departure_time::date, NEW.flight_delay;

		END IF;
	
	ELSIF  NEW.flight_status = 'departed' OR NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN 

		IF (NEW.departure_time::date) > CURRENT_DATE THEN
		--un volo deve invece partire almeno nella data stabilita, al più partirà dopo per ritardi

			RAISE EXCEPTION 'Il volo % non può ancora partire! La sua data di partenza è % con ritardo di % minuti!', NEW.id_flight, NEW.departure_time::date, NEW.flight_delay;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_date_before_aToDep_or_more
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_check_date_before_aToDep_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO DEPARTING PROGRAMMED Può ESSERE AGGIORNATO AD aboutToDepart

CREATE OR REPLACE FUNCTION fun_only_prog_flight_can_become_aToDep()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF NEW.flight_status = 'aboutToDepart' THEN

		IF OLD.flight_type = false THEN

			RAISE EXCEPTION 'Il volo % è verso Napoli, non può avere stato ''in partenza''', OLD.id_flight;

		END IF;

		IF OLD.flight_status <> 'programmed' THEN

			RAISE EXCEPTION 'Il volo % non era in stato ''programmato'', non può diventare ''in partenza''!', OLD.id_flight;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_prog_flight_can_become_aToDep
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_prog_flight_can_become_aToDep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING Può ESSERE AGGIORNATO A DEPARTED SOLO SE ERA aboutToDepart 

CREATE OR REPLACE FUNCTION fun_only_aToDep_flight_can_become_dep()
RETURNS TRIGGER
AS $$
BEGIN
	--non servono controlli sul flight_type, basta controllare OLD.flight_status, 
	--dato che solo un volo departing può diventare aboutToDepart
	IF NEW.flight_status = 'departed' THEN
	
		IF OLD.flight_status <> 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo % non era in stato ''in partenza'', non può diventare ''partito''!', OLD.id_flight;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_aToDep_flight_can_become_dep
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_aToDep_flight_can_become_dep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO PROGRAMMED O ABOUTTODEPART O DEPARTED O ABOUTTOARRIVE Può ESSERE AGGIORNATO A DELAYED

CREATE OR REPLACE FUNCTION fun_only_prog_or_aToDep_flight_can_become_delayed()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF NEW.flight_status = 'delayed' THEN
	
		IF OLD.flight_status <> 'programmed' AND OLD.flight_status <> 'aboutToDepart' AND OLD.flight_status <> 'departed' AND OLD.flight_status <> 'aboutToArrive' THEN

			RAISE EXCEPTION 'Il volo % non era in stato ''programmato'' o ''in partenza'', non può diventare ''in ritardo''!', OLD.id_flight;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_prog_or_aToDep_flight_can_become_delayed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_prog_or_aToDep_flight_can_become_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING Può ESSERE AGGIORNATO A LANDED SOLO SE DEPARTED

CREATE OR REPLACE FUNCTION fun_dep_flight_can_become_landed_only_if_dep()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF NEW.flight_status = 'landed' THEN
		
			IF OLD.flight_status <> 'departed' THEN

				RAISE EXCEPTION 'Il volo % non era in stato ''partito'', non può diventare ''atterrato''!', OLD.id_flight;

			END IF;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER dep_flight_can_become_landed_only_if_dep
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_dep_flight_can_become_landed_only_if_dep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO ARRIVING DEPARTED Può ESSERE AGGIORNATO A ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_only_departed_arriving_flight_can_become_aToArr()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN

		IF NEW.flight_status = 'aboutToArrive' THEN
		
			IF OLD.flight_status <> 'departed' THEN

				RAISE EXCEPTION 'Il volo % non era in stato ''partito'', non può diventare ''in arrivo''!', OLD.id_flight;

			END IF;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_departed_arriving_flight_can_become_aToArr
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_departed_arriving_flight_can_become_aToArr();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO ARRIVING Può ESSERE AGGIORNATO A LANDED SOLO SE DEPARTED O ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_arriving_f_can_become_land_only_if_dep_or_aToArr()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN

		IF NEW.flight_status = 'landed' THEN
		
			IF OLD.flight_status <> 'departed' AND OLD.flight_status <> 'aboutToArrive' THEN

				RAISE EXCEPTION 'Il volo % non era in stato ''partito'' o ''in arrivo'', non può diventare ''atterrato''!', OLD.id_flight;

			END IF;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER arriving_f_can_become_land_only_if_dep_or_aToArr
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_arriving_f_can_become_land_only_if_dep_or_aToArr();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO LANDED NON Può PIù CAMBIARE FLIGHT_STATUS
CREATE OR REPLACE FUNCTION fun_block_mod_flight_status_if_landed()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF OLD.flight_status = 'landed' AND NEW.flight_status <> 'landed' THEN

		RAISE EXCEPTION 'Il volo % è già atterrato, non può più cambiare stato!', OLD.id_flight;
		
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_flight_status_if_landed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_flight_status_if_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO IL FLIGHT STATUS DI UN VOLO DEPARTING DIVENTA 'aboutToDepart', LE PRENOTAZIONI 'PENDING' DIVENTANO 'CANCELLED'

CREATE OR REPLACE FUNCTION fun_change_booking_status_when_dep_aToDep()
RETURNS TRIGGER
AS $$
DECLARE

	selected_booking BOOKING%ROWTYPE;

BEGIN

	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN
		
		--questo if serve perché solo un volo programmed può essere impostato ad aboutToDepart
		IF OLD.flight_status <> 'programmed' THEN

			RAISE EXCEPTION 'Il volo da Napoli % non era in stato ''programmato'', non può diventare ''in partenza''!', OLD.id_flight;

		END IF;

		IF OLD.flight_status <> 'aboutToDepart' AND NEW.flight_status = 'aboutToDepart' THEN
		
			FOR selected_booking IN (SELECT * FROM BOOKING B
										WHERE B.id_flight = OLD.id_flight AND B.booking_status = 'pending') LOOP

				UPDATE BOOKING
				SET booking_status = 'cancelled'
				WHERE id_booking = selected_booking.id_booking;

			END LOOP;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER change_booking_status_when_dep_aToDep
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_change_booking_status_when_dep_aToDep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO IL FLIGHT STATUS DI UN VOLO ARRIVING DIVENTA 'departed', SIMULIAMO UNA CONNESSIONE CON L'AEROPORTO DI PARTENZA:
--LE PRENOTAZIONI 'PENDING' DIVENTANO 'CANCELLED', E FARE IN MODO CHE VENGANO AGGIORNATI ANCHE I FREE_SEATS DI CONSEGUENZA
--CIRCA IL 90% DEI SUOI PASSEGGERI CON PRENOTAZIONI CONFIRMED AVRANNO FATTO IL CHECK-IN, 
--E TUTTI I LUGGAGE_STATUS DEI BAGAGLI DEI SUOI PASSEGGERI CON CHECKED_IN A TRUE VENGONO MESSI A LOADED

CREATE OR REPLACE FUNCTION fun_simulate_connection_when_arriving_departed()
RETURNS TRIGGER
AS $$
DECLARE

	i INTEGER := 0;
	j INTEGER := 0;

	selected_booking BOOKING%ROWTYPE;
	selected_ticket TICKET%ROWTYPE;
	selected_luggage LUGGAGE%ROWTYPE;

BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN
	
		
		--questo if serve perché solo un volo programmed o aboutToDepart può essere impostato a departed
		IF OLD.flight_status <> 'programmed' AND OLD.flight_status <> 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo verso Napoli % non era in stato ''programmato'' o ''in partenza'', non può diventare ''decollato''!', OLD.id_flight;

		END IF;


		IF OLD.flight_status <> 'departed' AND NEW.flight_status = 'departed' THEN
		
			FOR selected_booking IN (SELECT * FROM BOOKING B
									 WHERE B.id_flight = OLD.id_flight AND B.booking_status <> 'cancelled') LOOP

				IF selected_booking.booking_status = 'pending' THEN

					UPDATE BOOKING
					SET booking_status = 'cancelled'
					WHERE id_booking = selected_booking.id_booking;

				ELSE
				--basta else, perchè tanto quelle già a cancelled non le prendo proprio con la select
					FOR selected_ticket IN (SELECT * FROM TICKET T
										    WHERE T.id_booking = selected_booking.id_booking) LOOP

						IF (i % 10) <> 0 THEN

							UPDATE TICKET
							SET checked_in = true
							WHERE ticket_number = selected_ticket.ticket_number;

							j := 0;
							FOR selected_luggage IN (SELECT * FROM LUGGAGE L
													 WHERE L.id_ticket = selected_ticket.ticket_number) LOOP

								UPDATE LUGGAGE
								SET luggage_status = 'loaded', id_luggage_after_check_in = selected_ticket.ticket_number || j
								WHERE id_luggage = selected_luggage.id_luggage;

								j := j + 1;

							END LOOP;

						END IF;

						i := i + 1;

					END LOOP;

				END IF;

				

			END LOOP;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER simulate_connection_when_arriving_departed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_simulate_connection_when_arriving_departed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING DEPARTED O LANDED NON HA PRENOTAZIONI PENDING

CREATE OR REPLACE FUNCTION fun_check_on_booking_status_after_departing()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF NEW.flight_type = true THEN

		IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN
		--non controllo se è 'aboutToDepart' perché se lo fosse, c'è un altro trigger che aggiorna le 'pending' a 'cancelled'
		
			IF EXISTS(SELECT * FROM BOOKING B
					  WHERE B.id_flight = NEW.id_flight AND B.booking_status = 'pending') THEN

				RAISE EXCEPTION 'Il volo % non può partire finchè ha prenotazioni pending!', NEW.id_flight;

			END IF;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_on_booking_status_after_departing
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_check_on_booking_status_after_departing();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO ARRIVING aboutToArrive O LANDED NON HA PRENOTAZIONI PENDING
--non serve il controllo su departed, perchè il trigger simulate_connection_when_arriving_departed le cancella BEFORE UPDATE, 
--mentre, se viene inserito direttamente un volo come departed, allora non posso essere inserite prenotazioni pending

CREATE OR REPLACE FUNCTION fun_check_on_booking_status_after_departing()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF NEW.flight_type = false THEN

		IF NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN
		
			IF EXISTS(SELECT * FROM BOOKING B
					  WHERE B.id_flight = NEW.id_flight AND B.booking_status = 'pending') THEN

				RAISE EXCEPTION 'Il volo % non può partire finchè ha prenotazioni pending!', NEW.id_flight;

			END IF;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_on_booking_status_after_departing
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_check_on_booking_status_after_departing();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO LE PRENOTAZIONI DIVENTANO 'CANCELLED', AGGIORNO I FREE_SEATS DEL VOLO ASSOCIATO

CREATE OR REPLACE FUNCTION fun_upd_free_seats_on_canc_booking()
RETURNS TRIGGER
AS $$
DECLARE

	n_passenger INTEGER := 0;
	
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

	selected_ticket TICKET%ROWTYPE;

BEGIN
		
	IF OLD.booking_status <> 'cancelled' AND NEW.booking_status = 'cancelled' THEN
	
		FOR selected_ticket IN (SELECT * FROM TICKET T
								WHERE T.id_booking = NEW.id_booking) LOOP

			n_passenger := n_passenger + 1;

		END LOOP;

		UPDATE FLIGHT
		SET free_seats = free_seats + n_passenger
		WHERE id_flight = associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER upd_free_seats_on_canc_booking
AFTER UPDATE OF booking_status ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_upd_free_seats_on_canc_booking();

--Non serve invece il trigger per decremenrare i free_seats se una prenotazione passa da 'cancelled' a qualcos'altro, perché non è possibile 'ripristinare' una prenotazione

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER CHE IMPEDISCE DI METTERE UNA PRENOTAZIONE DA CANCELLED A QUALCOS'ALTRO

CREATE OR REPLACE FUNCTION fun_block_restore_cancelled_booking()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF OLD.booking_status = 'cancelled' AND NEW.booking_status <> 'cancelled' THEN
	
		RAISE EXCEPTION 'Non è possibile ripristinare una prenotazione cancellata!';
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_restore_cancelled_booking
BEFORE UPDATE OF booking_status ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_restore_cancelled_booking();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO INSERIRE UN VOLO DEPARTING CON UN GATE SOLO SE è ABOUTTODEPART O DELAYED O DEPARTED O LANDED

CREATE OR REPLACE FUNCTION fun_departing_check_insert_with_gate_only_if_aToDep_more()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF NEW.flight_type = true THEN

		IF NEW.id_gate IS NOT NULL THEN

			IF NEW.flight_status <> 'aboutToDepart' AND NEW.flight_status <> 'delayed' AND NEW.flight_status <> 'departed' AND NEW.flight_status <> 'landed' THEN

				RAISE EXCEPTION 'Il volo da Napoli % non è in stato ''in partenza'', ''in ritardo'', ''partito'', ''atterrato'', non lo si può inserire con un gate!', NEW.id_flight;

			END IF;

		END IF;
	
	END IF;
	
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER departing_check_insert_with_gate_only_if_aToDep_more
BEFORE INSERT ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_departing_check_insert_with_gate_only_if_aToDep_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO INSERIRE UN VOLO ARRIVING CON UN GATE SOLO SE è ABOUTTOARRIVE O DELAYED O DEPARTED O LANDED

CREATE OR REPLACE FUNCTION fun_arriving_check_insert_with_gate_only_if_aToDep_more()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = false THEN
	
		IF NEW.id_gate IS NOT NULL THEN

			IF NEW.flight_status <> 'aboutToArrive' AND NEW.flight_status <> 'delayed' AND NEW.flight_status <> 'departed' AND NEW.flight_status <> 'landed' THEN

				RAISE EXCEPTION 'Il volo per Napoli % non è in stato ''in arrivo'', ''in ritardo'', ''partito'', ''atterrato'', non lo si può inserire con un gate!', NEW.id_flight;

			END IF;

		END IF;

	END IF;
	
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER arriving_check_insert_with_gate_only_if_aToDep_more
BEFORE INSERT ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_arriving_check_insert_with_gate_only_if_aToDep_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO ASSEGNARE UN GATE AD UN VOLO DEPARTING SOLO SE è ABOUTTODEPART O DELAYED (BEFORE UPDATE)

CREATE OR REPLACE FUNCTION fun_departing_check_assign_gate_only_if_aToDep_delayed()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF NEW.id_gate IS NOT NULL THEN

			IF NEW.flight_status <> 'aboutToDepart' AND NEW.flight_status <> 'delayed' THEN

				RAISE EXCEPTION 'Il volo da Napoli % non è in stato ''in partenza'' o ''in ritardo'', non gli si può assegnare un gate!', OLD.id_flight;

			END IF;

		END IF;
	
	END IF;
	
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER departing_check_assign_gate_only_if_aToDep_delayed
BEFORE UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_departing_check_assign_gate_only_if_aToDep_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO ASSEGNARE UN GATE AD UN VOLO ARRIVING SOLO SE è ABOUTTOARRIVE O DELAYED (BEFORE UPDATE)

CREATE OR REPLACE FUNCTION fun_arriving_check_assign_gate_only_if_aToArr_delayed()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = false THEN

		IF NEW.id_gate IS NOT NULL THEN

			IF NEW.flight_status <> 'aboutToArrive' AND NEW.flight_status <> 'delayed' THEN

				RAISE EXCEPTION 'Il volo verso Napoli % non è in stato ''in arrivo'' o ''in ritardo'', non gli si può assegnare un gate!', NEW.id_flight;

			END IF;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER arriving_check_assign_gate_only_if_aToArr_delayed
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_arriving_check_assign_gate_only_if_aToArr_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI PROGRAMMED O CANCELLED CON UN GATE

CREATE OR REPLACE FUNCTION fun_block_assign_gate_to_a_prog_or_canc_flight()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.id_gate IS NOT NULL THEN

		IF NEW.flight_status = 'programmed' OR NEW.flight_status = 'cancelled' THEN

			RAISE EXCEPTION 'Il volo % è in stato %, non gli si può assegnare un gate!', NEW.id_flight, NEW.flight_status;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_assign_gate_to_a_prog_or_canc_flight
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_assign_gate_to_a_prog_or_canc_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI DEPARTING DEPARTED O LANDED SENZA GATE

CREATE OR REPLACE FUNCTION fun_block_departing_flight_dep_or_landed_without_gate()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = true THEN
	
		IF NEW.id_gate IS NULL THEN

			IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo da napoli % è in stato %, non può non avere un gate da cui è partito!', NEW.id_flight, NEW.flight_status;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_departing_flight_dep_or_landed_without_gate
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_departing_flight_dep_or_landed_without_gate();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI ARRIVING LANDED SENZA GATE

CREATE OR REPLACE FUNCTION fun_block_arriving_flight_landed_without_gate()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = false THEN
	
		IF NEW.id_gate IS NULL THEN

			IF NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo verso napoli % è in stato ''atterrato'', non può non avere un gate dove è atterrato!', NEW.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_arriving_flight_landed_without_gate
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_arriving_flight_landed_without_gate();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò MODIFICARE IL GATE DI UN VOLO DEPARTING DEPARTED o più

CREATE OR REPLACE FUNCTION fun_block_mod_gate_departing_dep()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = true THEN
	
		IF OLD.id_gate <> NEW.id_gate THEN

			IF (OLD.flight_status = 'departed' OR OLD.flight_status = 'landed') AND (NEW.flight_status = 'departed' OR NEW.flight_status = 'landed') THEN

				RAISE EXCEPTION 'Il volo da napoli % è in stato %, non si può modificare il gate da cui è partito!', OLD.id_flight, NEW.flight_status;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_gate_departing_dep
BEFORE UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_gate_departing_dep();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò MODIFICARE IL GATE DI UN VOLO ARRIVING LANDED

CREATE OR REPLACE FUNCTION fun_block_mod_gate_arriving_landed()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = false THEN
	
		IF OLD.id_gate <> NEW.id_gate THEN

			IF OLD.flight_status = 'landed' AND NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo per napoli % è in stato ''atterrato'', non si può modificare il gate in cui è atterrato!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_mod_gate_arriving_landed
BEFORE UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_mod_gate_arriving_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN ACCOUNT CUSTOMER VIENE CANCELLATO, METTO A CANCELLED TUTTE LE SUE PRENOTAZIONI PER I VOLI CHE DEVONO ANCORA PARTIRE
--(OSSIA IL CUI FLIGHT_STATUS è ANCORA PROGRAMMED)

CREATE OR REPLACE FUNCTION fun_canc_future_booking_if_cust_canc()
RETURNS TRIGGER
AS $$
DECLARE

	selected_booking BOOKING%ROWTYPE;
	associated_flight FLIGHT%ROWTYPE;

BEGIN

	IF OLD.is_deleted = false AND NEW.is_deleted = true THEN
	
		FOR selected_booking IN (SELECT * FROM BOOKING B
								 WHERE B.buyer = OLD.id_customer AND B.booking_status <> 'cancelled') LOOP

			associated_flight := (SELECT * FROM FLIGHT F
			      		  		  WHERE F.id_flight = selected_booking.id_flight);
			
			IF associated_flight.flight_status = 'programmed' THEN

				UPDATE BOOKING
				SET booking_status = 'cancelled'
				WHERE id_booking = selected_booking.id_booking;

			END IF;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER canc_future_booking_if_cust_canc
BEFORE UPDATE OF is_deleted ON CUSTOMER
FOR EACH ROW
EXECUTE FUNCTION fun_canc_future_booking_if_cust_canc();

-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------


-----------------------------------------------------POPOLAMENTO---------------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

-- Dati per la tabella Admin

INSERT INTO Admin (username, mail, hashed_password, is_deleted) VALUES
('admin_user1', 'admin1@aureportodinapoli.it', 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', false),
('admin_user2', 'admin2@adn.it', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', false),
('admin_user3', 'admin3@aureportodinapoli.it', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', false),
('admin_user4', 'admin4@adn.it', 'c147773260467885b57f0064379361a45484814c1945a6c4295d53a9926d24f0', false),
('admin_user5', 'admin5@aureportodinapoli.it', '1a9e7011d8fb6168e376a9177f525867201c1070e6a392b49de489b0365778a0', false),
('admin_user6', 'admin6@adn.it', '2a67776a3b7d1591e1d0859550b07c87c093a0b41170cf82a6081c7f4e91a2a4', false),
('admin_user7', 'admin7@aureportodinapoli.it', '3a78887b4c8e2602f2e1960661c18d98d1a42b152281df93b7192d805f02b3b5', false),
('admin_user8', 'admin8@adn.it', '4b89998c5d9f3713g3f2071772d29e09e2b53c263392eg04c8203e916g13c4c6', false),
('admin_user9', 'admin9@aureportodinapoli.it', '5c90009d6e0g4824h4g3182883e30f10f3c64d374403fh15d9314f027h24d5d7', false),
('admin_user10', 'admin10@adn.it', '6d01110e7f1h5935i5h4293994f41g21g4d75e485514gi26e045i136e6e8', false),
('admin_deleted1', 'deleted1@aureportodinapoli.it', 'deletedhash1', true),
('admin_deleted2', 'deleted2@adn.it', 'deletedhash2', true);

-- Dati per la tabella Customer

INSERT INTO Customer (username, mail, hashed_password, is_deleted) VALUES
('customer_user1', 'customer1@example.com', 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', false),
('customer_user2', 'customer2@test.org', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', false),
('customer_user3', 'customer3@mail.net', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', false),
('customer_user4', 'customer4@service.co', 'c147773260467885b57f0064379361a45484814c1945a6c4295d53a9926d24f0', false),
('customer_user5', 'customer5@domain.info', '1a9e7011d8fb6168e376a9177f525867201c1070e6a392b49de489b0365778a0', false),
('customer_user6', 'customer6@web.biz', '2a67776a3b7d1591e1d0859550b07c87c093a0b41170cf82a6081c7f4e91a2a4', false),
('customer_user7', 'customer7@company.xyz', '3a78887b4c8e2602f2e1960661c18d98d1a42b152281df93b7192d805f02b3b5', false),
('customer_user8', 'customer8@site.club', '4b89998c5d9f3713g3f2071772d29e09e2b53c263392eg04c8203e916g13c4c6', false),
('customer_user9', 'customer9@app.space', '5c90009d6e0g4824h4g3182883e30f10f3c64d374403fh15d9314f027h24d5d7', false),
('customer_user10', 'customer10@data.io', '6d01110e7f1h5935i5h4293994f41g21g4d75e485514gi26e045i136e6e8', false),
('customer_deleted1', 'deletedcust1@example.com', 'deletedhashcust1', true),
('customer_deleted2', 'deletedcust2@test.org', 'deletedhashcust2', true);

-- Dati per la tabella Flight

INSERT INTO Flight (id_flight, company_name, departure_time, arrival_time, flight_status, max_seats, free_seats, destination_or_origin, flight_delay, flight_type, id_gate) VALUES
('AZ1001', 'Alitalia', '2025-08-01 10:00:00', '2025-08-01 12:00:00', 'programmed', 150, 150, 'Rome', 0, true, 1),
('BA2002', 'British Airways', '2025-08-02 14:30:00', '2025-08-02 16:30:00', 'programmed', 200, 200, 'London', 0, false, 2),
('LH3003', 'Lufthansa', '2025-08-03 09:15:00', '2025-08-03 11:15:00', 'programmed', 180, 180, 'Frankfurt', 0, true, 3),
('AF4004', 'Air France', '2025-08-04 18:00:00', '2025-08-04 20:00:00', 'programmed', 160, 160, 'Paris', 0, false, 4),
('UA5005', 'United Airlines', '2025-08-05 07:45:00', '2025-08-05 10:45:00', 'programmed', 250, 250, 'New York', 0, true, 5),
('EK6006', 'Emirates', '2025-08-06 22:00:00', '2025-08-07 05:00:00', 'programmed', 300, 300, 'Dubai', 0, false, 6),
('QR7007', 'Qatar Airways', '2025-08-07 11:00:00', '2025-08-07 14:00:00', 'programmed', 220, 220, 'Doha', 0, true, 7),
('TK8008', 'Turkish Airlines', '2025-08-08 13:00:00', '2025-08-08 16:00:00', 'programmed', 190, 190, 'Istanbul', 0, false, 8),
('DL9009', 'Delta Airlines', '2025-08-09 16:00:00', '2025-08-09 19:00:00', 'programmed', 210, 210, 'Atlanta', 0, true, 9),
('LX1010', 'Swiss International Air Lines', '2025-08-10 08:30:00', '2025-08-10 10:30:00', 'programmed', 170, 170, 'Zurich', 0, false, 10),
('FR1111', 'Ryanair', '2025-08-11 10:00:00', '2025-08-11 12:00:00', 'cancelled', 100, 100, 'Dublin', 0, true, NULL),
('VY1212', 'Vueling', '2025-08-12 14:00:00', '2025-08-12 16:00:00', 'delayed', 120, 120, 'Barcelona', 60, false, 11);

-- Dati per la tabella Passenger

INSERT INTO Passenger (first_name, last_name, birth_date, SSN) VALUES
('Mario', 'Rossi', '1980-01-15', 'MRORSS80A15H501F'),
('Anna', 'Bianchi', '1992-03-22', 'NNABNC92C22G273J'),
('Giuseppe', 'Verdi', '1975-07-01', 'GSPVRD75G01L219K'),
('Maria', 'Gialli', '1998-11-30', 'MRAGLL98S70F839A'),
('Paolo', 'Neri', '1985-05-10', 'PAONRI85E10C351B'),
('Laura', 'Brambilla', '1990-09-05', 'LRAZMZ90P05D612C'),
('Luca', 'Colombo', '1983-02-18', 'LCACLM83B18E089D'),
('Sara', 'Ferrari', '1995-06-25', 'SRAFRA95F25H701E'),
('Marco', 'Ricci', '1970-12-03', 'MRCRCC70T03L389G'),
('Elena', 'Esposito', '1988-04-08', 'ELNSPT88D08I170H'),
('Francesco', 'Russo', '1982-01-01', 'FRNRSS82A01F111A'),
('Sofia', 'Mancini', '1991-02-02', 'SFAMNC91B02G222B'),
('Alessandro', 'Costa', '1977-03-03', 'LSSCSC77C03H333C'),
('Chiara', 'Romano', '1993-04-04', 'CHRRMNN93D04I444D'),
('Simone', 'Gallo', '1986-05-05', 'SMNGLL86E05J555E'),
('Valentina', 'Fontana', '1994-06-06', 'VLTFNT94F06K666F'),
('Andrea', 'Conti', '1979-07-07', 'NDRCNT79G07L777G'),
('Beatrice', 'Greco', '1996-08-08', 'BTBGRC96H08M888H'),
('Giovanni', 'Riva', '1981-09-09', 'GVNRIV81I09N999I'),
('Francesca', 'Mariani', '1989-10-10', 'FRNMRA89R10O000J');

-- Dati per la tabella Booking

INSERT INTO Booking (booking_status, booking_time, buyer, id_flight) VALUES
('confirmed', '2025-07-10 10:30:00', 1, 'AZ1001'), -- ID Booking 1
('pending', '2025-07-11 11:00:00', 2, 'BA2002'), -- ID Booking 2
('confirmed', '2025-07-12 12:15:00', 3, 'LH3003'), -- ID Booking 3
('pending', '2025-07-13 13:45:00', 4, 'AF4004'), -- ID Booking 4
('confirmed', '2025-07-14 09:00:00', 5, 'UA5005'), -- ID Booking 5
('pending', '2025-07-15 14:00:00', 6, 'EK6006'), -- ID Booking 6
('confirmed', '2025-07-10 15:30:00', 7, 'QR7007'), -- ID Booking 7
('pending', '2025-07-11 16:00:00', 8, 'TK8008'), -- ID Booking 8
('confirmed', '2025-07-12 17:15:00', 9, 'DL9009'), -- ID Booking 9
('pending', '2025-07-13 18:45:00', 10, 'LX1010'), -- ID Booking 10
('cancelled', '2025-07-09 09:00:00', 1, 'FR1111'), -- ID Booking 11,
('confirmed', '2025-07-10 10:00:00', 3, 'FR1111'), -- ID Booking 12,
('confirmed', '2025-07-14 11:00:00', 5, 'VY1212'), -- ID Booking 13,
('pending', '2025-07-15 12:00:00', 7, 'VY1212'), -- ID Booking 14,
('confirmed', '2025-07-16 09:30:00', 1, 'AZ1001'), -- ID Booking 15
('confirmed', '2025-07-16 09:35:00', 2, 'AZ1001'); -- ID Booking 16

-- Dati per la tabella Ticket

INSERT INTO Ticket (ticket_number, seat, checked_in, id_booking, id_passenger, id_flight) VALUES

-- Booking 1 (confirmed, AZ1001) - 3 tickets
('0000000000001', 0, false, 1, 'MRORSS80A15H501F', 'AZ1001'),
('0000000000002', 1, false, 1, 'NNABNC92C22G273J', 'AZ1001'),
('0000000000003', 2, false, 1, 'GSPVRD75G01L219K', 'AZ1001'),
-- Booking 2 (pending, BA2002) - 2 tickets
('0000000000004', NULL, false, 2, 'MRAGLL98S70F839A', 'BA2002'),
('0000000000005', NULL, false, 2, 'PAONRI85E10C351B', 'BA2002'),
-- Booking 3 (confirmed, LH3003) - 3 tickets, some checked-in
('0000000000006', 3, true, 3, 'LRAZMZ90P05D612C', 'LH3003'),
('0000000000007', 4, false, 3, 'LCACLM83B18E089D', 'LH3003'),
('0000000000008', 5, true, 3, 'SRAFRA95F25H701E', 'LH3003'),
-- Booking 4 (pending, AF4004) - 2 tickets
('0000000000009', NULL, false, 4, 'MRCRCC70T03L389G', 'AF4004'),
('0000000000010', NULL, false, 4, 'ELNSPT88D08I170H', 'AF4004'),
-- Booking 5 (confirmed, UA5005) - 3 tickets
('0000000000011', 0, false, 5, 'FRNRSS82A01F111A', 'UA5005'),
('0000000000012', 1, false, 5, 'SFAMNC91B02G222B', 'UA5005'),
('0000000000013', 2, false, 5, 'LSSCSC77C03H333C', 'UA5005'),
-- Booking 6 (pending, EK6006) - 2 tickets
('0000000000014', NULL, false, 6, 'CHRRMNN93D04I444D', 'EK6006'),
('0000000000015', NULL, false, 6, 'SMNGLL86E05J555E', 'EK6006'),
-- Booking 7 (confirmed, QR7007) - 3 tickets
('0000000000016', 0, false, 7, 'VLTFNT94F06K666F', 'QR7007'),
('0000000000017', 1, false, 7, 'NDRCNT79G07L777G', 'QR7007'),
('0000000000018', 2, false, 7, 'BTBGRC96H08M888H', 'QR7007'),
-- Booking 8 (pending, TK8008) - 2 tickets
('0000000000019', NULL, false, 8, 'GVNRIV81I09N999I', 'TK8008'),
('0000000000020', NULL, false, 8, 'FRNMRA89R10O000J', 'TK8008'),
-- Booking 9 (confirmed, DL9009) - 3 tickets
('0000000000021', 0, false, 9, 'MRORSS80A15H501F', 'DL9009'),
('0000000000022', 1, false, 9, 'NNABNC92C22G273J', 'DL9009'),
('0000000000023', 2, false, 9, 'GSPVRD75G01L219K', 'DL9009'),
-- Booking 10 (pending, LX1010) - 2 tickets
('0000000000024', NULL, false, 10, 'MRAGLL98S70F839A', 'LX1010'),
('0000000000025', NULL, false, 10, 'PAONRI85E10C351B', 'LX1010'),
-- Booking 11 (cancelled, FR1111) - 2 tickets
('0000000000026', 0, false, 11, 'LRAZMZ90P05D612C', 'FR1111'),
('0000000000027', 1, false, 11, 'LCACLM83B18E089D', 'FR1111'),
-- Booking 12 (confirmed for cancelled flight, FR1111) - 2 tickets
('0000000000028', 2, true, 12, 'SRAFRA95F25H701E', 'FR1111'),
('0000000000029', 3, false, 12, 'MRCRCC70T03L389G', 'FR1111'),
-- Booking 13 (confirmed, VY1212 - delayed flight) - 2 tickets
('0000000000030', 0, false, 13, 'ELNSPT88D08I170H', 'VY1212'),
('0000000000031', 1, false, 13, 'FRNRSS82A01F111A', 'VY1212'),
-- Booking 14 (pending, VY1212 - delayed flight) - 2 tickets
('0000000000032', NULL, false, 14, 'SFAMNC91B02G222B', 'VY1212'),
('0000000000033', NULL, false, 14, 'LSSCSC77C03H333C', 'VY1212');


-- Dati per la tabella Luggage

INSERT INTO Luggage (luggage_type, luggage_status, id_ticket) VALUES

-- Per ticket 0000000000001 (Booking 1)
('checked', 'booked', '0000000000001'),
('carry_on', 'booked', '0000000000001'),
-- Per ticket 0000000000002 (Booking 1)
('checked', 'booked', '0000000000002'),
-- Per ticket 0000000000003 (Booking 1)
('carry_on', 'booked', '0000000000003'),
('checked', 'booked', '0000000000003'),
-- Per ticket 0000000000004 (Booking 2)
(NULL, 'booked', '0000000000004'),
-- Per ticket 0000000000006 (Booking 3)
('checked', 'booked', '0000000000006'),
('carry_on', 'booked', '0000000000006'),
-- Per ticket 0000000000007 (Booking 3)
('checked', 'booked', '0000000000007'),
-- Per ticket 0000000000008 (Booking 3)
('checked', 'booked', '0000000000008'),
('carry_on', 'booked', '0000000000008'),
-- Per ticket 0000000000009 (Booking 4)
(NULL, 'booked', '0000000000009'),
-- Per ticket 0000000000011 (Booking 5)
('checked', 'booked', '0000000000011'),
('carry_on', 'booked', '0000000000011'),
-- Per ticket 0000000000012 (Booking 5)
('checked', 'booked', '0000000000012'),
-- Per ticket 0000000000013 (Booking 5)
('carry_on', 'booked', '0000000000013'),
('checked', 'booked', '0000000000013'),
-- Per ticket 0000000000014 (Booking 6)
(NULL, 'booked', '0000000000014'),
-- Per ticket 0000000000016 (Booking 7)
('checked', 'booked', '0000000000016'),
('carry_on', 'booked', '0000000000016'),
-- Per ticket 0000000000017 (Booking 7)
('checked', 'booked', '0000000000017'),
-- Per ticket 0000000000018 (Booking 7)
('carry_on', 'booked', '0000000000018'),
('checked', 'booked', '0000000000018'),
-- Per ticket 0000000000019 (Booking 8)
(NULL, 'booked', '0000000000019'),
-- Per ticket 0000000000021 (Booking 9)
('checked', 'booked', '0000000000021'),
('carry_on', 'booked', '0000000000021'),
-- Per ticket 0000000000022 (Booking 9)
('checked', 'booked', '0000000000022'),
-- Per ticket 0000000000023 (Booking 9)
('carry_on', 'booked', '0000000000023'),
('checked', 'booked', '0000000000023'),
-- Per ticket 0000000000024 (Booking 10)
(NULL, 'booked', '0000000000024'),
-- Per ticket 0000000000026 (Booking 11)
('checked', 'booked', '0000000000026'),
-- Per ticket 0000000000028 (Booking 12)
('checked', 'booked', '0000000000028'),
('carry_on', 'booked', '0000000000028');

-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------


-----------------------------------------------TRIGGER POST POPOLAMENTO-------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò INSERIRE UN ACCOUNT ADMIN CANCELLATO

CREATE OR REPLACE FUNCTION fun_block_ins_deleted_admin()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = true THEN

		RAISE EXCEPTION 'Non si può inserire un account già cancellato!';

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER block_ins_deleted_admin
BEFORE INSERT ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_deleted_admin();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI PUò INSERIRE UN ACCOUNT CUSTOMER CANCELLATO

CREATE OR REPLACE FUNCTION fun_block_ins_deleted_customer()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.is_deleted = true THEN

		RAISE EXCEPTION 'Non si può inserire un account già cancellato!';

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER block_ins_deleted_customer
BEFORE INSERT ON CUSTOMER
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_deleted_customer();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE PRENOTAZIONE SU UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_booking_an_aToDep_or_more_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

BEGIN
				
	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo % è in partenza oppure è già partito, non si possono inserire nuove prenotazioni!', NEW.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_booking_an_aToDep_or_more_flight
BEFORE INSERT ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_booking_an_aToDep_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE PASSEGGERO SU UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_ins_tickets_aToDep_or_more_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				             			 WHERE id_flight = NEW.id_flight);

BEGIN
				
	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo % è in partenza oppure è già partito, non si possono inserire biglietti!', NEW.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_tickets_aToDep_or_more_flight
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_tickets_aToDep_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE BAGAGLIO PER UN PASSEGGERO DI UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_ins_luggage_aToDep_or_more_flight()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
					          			 WHERE T.ticket_number = NEW.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      		  WHERE F.id_flight = associated_ticket.id_flight);
			
	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo % è in partenza oppure è già partito, il bagaglio non può essere inserito!', associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_luggage_aToDep_or_more_flight
BEFORE INSERT ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_luggage_aToDep_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO AGGIUNGERE NUOVE PRENOTAZIONI (BEFORE INSERT ON BOOKING) SE IL CUSTOMER ASSOCIATO HA IS_DELETED = TRUE

CREATE OR REPLACE FUNCTION fun_block_ins_booking_deleted_accounts()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_customer CUSTOMER%ROWTYPE := (SELECT * FROM CUSTOMER C
					          			 	 WHERE C.id_customer = NEW.buyer);

BEGIN
	
	IF associated_customer.is_deleted = true THEN

		RAISE EXCEPTION 'L''account dell''utente % è stato cancellato, non è possibile inserire nuove prenotazioni!', associated_customer.id_customer;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_booking_deleted_accounts
BEFORE INSERT ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_booking_deleted_accounts();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO AGGIUNGERE PRENOTAZIONI (BEFORE INSERT ON) PER VOLI NON PROGRAMMED

CREATE OR REPLACE FUNCTION fun_block_ins_booking_not_prog_flights()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					          			 WHERE F.id_flight = NEW.id_flight);

BEGIN
	
	IF associated_flight.flight_status <> 'programmed' THEN

		RAISE EXCEPTION 'Il volo % non è in stato ''programmato'', non è possibile inserire nuove prenotazioni!', associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_booking_not_prog_flights
BEFORE INSERT ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_booking_not_prog_flights();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO AGGIUNGERE TICKET (BEFORE INSERT ON) PER VOLI NON PROGRAMMED

CREATE OR REPLACE FUNCTION fun_block_ins_tickets_not_prog_flights()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					          			 WHERE F.id_flight = NEW.id_flight);

BEGIN
	
	IF associated_flight.flight_status <> 'programmed' THEN

		RAISE EXCEPTION 'Il volo % non è in stato ''programmato'', non è possibile inserire nuovi biglietti!', associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_tickets_not_prog_flights
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_tickets_not_prog_flights();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO AGGIUNGERE TICKET (BEFORE INSERT ON) PER PRENOTAZIONI CANCELLED

CREATE OR REPLACE FUNCTION fun_block_ins_tickets_canc_bookings()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					          			   WHERE B.id_booking = NEW.id_booking);

BEGIN
	
	IF associated_booking.booking_status = 'cancelled' THEN

		RAISE EXCEPTION 'La prenotazione % è stata cancellata, non è possibile inserire nuovi biglietti!', associated_booking.id_booking;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_tickets_canc_bookings
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_tickets_canc_bookings();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE BAGAGLI (BEFORE INSERT ON) PER VOLI NON PROGRAMMED

CREATE OR REPLACE FUNCTION fun_block_ins_luggages_not_prog_flights()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
										 WHERE T.ticket_number = NEW.id_ticket);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
					      WHERE F.id_flight = associated_ticket.id_flight);

	IF associated_flight.flight_status <> 'programmed' THEN

		RAISE EXCEPTION 'Il volo % non è in stato ''programmato'', non è possibile inserire nuovi bagagli!', associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_luggages_not_prog_flights
BEFORE INSERT ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_luggages_not_prog_flights();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE BAGAGLI (BEFORE INSERT ON) PER TICKET GIà CON CHECKE_IN A TRUE
--e questo (insieme al trigger successivo) garantisce anche non possano essere inseriti bagagli per voli già partiti

CREATE OR REPLACE FUNCTION fun_block_ins_luggages_checked_in_tickets()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
										 WHERE T.ticket_number = NEW.id_ticket);

BEGIN
	
	IF associated_ticket.checked_in = true THEN

		RAISE EXCEPTION 'Per il biglietto con numero % è già stato fatto il check-in, non è possibile inserire nuovi bagagli!', associated_ticket.ticket_number;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_luggages_checked_in_tickets
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_luggages_checked_in_tickets();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE BAGAGLI (BEFORE INSERT ON) PER PRENOTAZIONI CANCELLED
--e questo (insieme al trigger precedente) garantisce anche non possano essere inseriti bagagli per voli già partiti

CREATE OR REPLACE FUNCTION fun_block_ins_luggages_canc_bookings()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_ticket TICKET%ROWTYPE := (SELECT * FROM TICKET T
										 WHERE T.ticket_number = NEW.id_ticket);

	associated_booking BOOKING%ROWTYPE;

BEGIN
	
	associated_booking := (SELECT * FROM BOOKING B
					       WHERE B.id_booking = associated_ticket.id_booking);

	IF associated_booking.booking_status = 'cancelled' THEN

		RAISE EXCEPTION 'La prenotazione % è stata cancellata, non è possibile inserire nuovi bagagli!', associated_booking.id_booking;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_luggages_canc_bookings
BEFORE INSERT ON TICKET
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_luggages_canc_bookings();

-------------------------------------------------------------------------------------------------------------------------




