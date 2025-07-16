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

			RAISE EXCEPTION 'Username %L già utilizzato da un altro utente!', NEW.username;

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

			RAISE EXCEPTION 'Mail %L già utilizzata da un altro utente!', NEW.mail;

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

			RAISE EXCEPTION 'Username %L già utilizzato da un altro utente!', NEW.username;

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

			RAISE EXCEPTION 'Mail %L già utilizzata da un altro utente!', NEW.mail;

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

		RAISE EXCEPTION 'Username %L già utilizzato da un altro utente!', NEW.username;

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

		RAISE EXCEPTION 'Username %L già utilizzato da un altro utente!', NEW.username;

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

		RAISE EXCEPTION 'Mail %L già utilizzato da un altro utente!', NEW.mail;

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

		RAISE EXCEPTION 'Mail %L già utilizzato da un altro utente!', NEW.mail;

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

		RAISE EXCEPTION 'L''account dell''utente %L è cancellato, non può essere modificato!', OLD.id_admin;

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

		RAISE EXCEPTION 'L''account dell''utente %L è cancellato, non può essere modificato!', OLD.id_customer;

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

--TRIGGER NON SI POSSONO MAI MODIFICARE GLI ATTRIBUTI FLIGHT_DATE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_SEATS, FLIGHT_TYPE DI FLIGHT

CREATE OR REPLACE FUNCTION fun_blocked_updates_flight()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_date <> NEW.flight_date OR OLD.departure_time <> NEW.departure_time OR OLD.arrival_time <> NEW.arrival_time OR OLD.max_seats <> NEW.max_seats OR OLD.flight_type <> NEW.flight_type THEN

		RAISE EXCEPTION 'I voli non possono cambiare data di partenza/ ora di partenza/ numero posti massimi/ tipo!';

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocked_updates_flight
BEFORE UPDATE OF flight_type ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_blocked_updates_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO DEPARTING PUò AVERE FLIGHT_STATUS AD ABOUTTODEPART

CREATE OR REPLACE FUNCTION fun_only_a_dep_flight_can_become_aToDepart()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.flight_type = false THEN

		IF NEW.flight_status = 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo %L è verso Napoli, non può avere stato ''in partenza''', NEW.id_flight;

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

			RAISE EXCEPTION 'Il volo %L è da Napoli, non può avere stato ''in arrivo''', NEW.id_flight;

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

	CONSTRAINT correctness_of_booking_time CHECK( booking_time <= CURRENT_TIME),
	CONSTRAINT buyer_FK FOREIGN KEY(buyer) REFERENCES Customer(id_customer) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight) ON DELETE CASCADE ON UPDATE CASCADE

);

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

		RAISE EXCEPTION 'Il volo %L è in partenza/partito/sta per atterrare/atterrato, non si può modificare la prenotazione!', NEW.id_flight;

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
	CONSTRAINT passenger_FK FOREIGN KEY(id_passenger) REFERENCES Passenger(SSN) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT numeric_ticket_number CHECK( ticket_number ~ '^[0-9]+$' )

);

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
			
			RAISE EXCEPTION 'Posto non valido per il volo %L', NEW.id_flight;
	
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

				RAISE EXCEPTION 'Dati mancanti per il passeggero il cui biglietto ha numero %L, per la prenotazione %L', 
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

				RAISE EXCEPTION 'Dati mancanti per il passeggero il cui biglietto ha numero %L, la prenotazione %L non può essere confermata!', 
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
			
			RAISE EXCEPTION 'Errore nell''assegnazione del posto per il passeggero con ticket_number: %L, per il volo: %L', NEW.ticket_number, NEW.id_flight;
		
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

				RAISE EXCEPTION 'Il volo %L è in stato %L, non si possono modificare i dati del passeggero con SSN %L!', 
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
		
			RAISE EXCEPTION 'Il volo %L è in stato ''in partenza'', per il biglietto con ticket_number %L si può solo modificare il posto o fare il check-in!',
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


		RAISE EXCEPTION 'Il volo %L è in stato %L, non si possono modificare i dati del biglietto con ticket_number %L!',
														NEW.id_flight, associated_flight.flight_status NEW.ticket_number;

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
		
			RAISE EXCEPTION 'Posto già occupato per il volo %L', NEW.id_flight;

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
	
			RAISE EXCEPTION 'Volo %L pieno!', NEW.id_flight;
	
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

			RAISE EXCEPTION 'La prenotazione %L non è confermata, non si può fare il check-in!', NEW.id_booking;
		
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

			RAISE EXCEPTION 'Il volo %L non è in partenza/partito/sta per atterrare/atterrato, non si può essere checked-in!', NEW.id_flight;
		
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

			RAISE EXCEPTION 'Il volo %L è già partito, non si può annullare il check-in!', NEW.id_flight;
		
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
			
			RAISE EXCEPTION 'Dati mancanti per il bagaglio %L del passeggero il cui biglietto ha numero %L', NEW.id_luggage, associated_ticket.ticket_number;
	
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
			
			RAISE EXCEPTION 'Il biglietto con ticket number %L non ha ancora fatto il check_in, il bagaglio %L non può avere stato divero da ''prenotato''!', associated_ticket.ticket_number, NEW.id_luggage;
	
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

			RAISE EXCEPTION 'Il biglietto con ticket number %L ha efettuato il check_in, il suo bagaglio non può non avere id_luggage_after_check_in!', 
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
	
				RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L è già partito, il bagaglio %L non può avere stato ''prenotato''!', 
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
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L non è ancora atterrato, il bagaglio %L non può avere stato ''ritirabile''!', 
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
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L non è ancora atterrato, il bagaglio %L non può avere stato ''smarrito''!', 
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
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L è progammato/cancellato/atterrato, il bagaglio %L non può avere stato ''caricato''!', 
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
				
			RAISE EXCEPTION 'La prenotazione %L del passeggero con ticket number %L è cancellata, il bagaglio %L non può avere stato divero da ''prenotato''!', 
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
			
			RAISE EXCEPTION 'Il bagaglio %L non era in stato ''prenotato'', non può diventare ''caricato''!', OLD.id_luggage;
	
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
			
			RAISE EXCEPTION 'Il bagaglio %L non era in stato ''caricato'', non può diventare ''ritirabile''!', OLD.id_luggage;
	
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
			
			RAISE EXCEPTION 'Il bagaglio %L non era in stato ''ritirabile'', non può diventare ''smarrito''!', OLD.id_luggage;
	
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

				RAISE EXCEPTION 'Il volo da Napoli %L non era in stato ''in partenza'', non può diventare ''partito''!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non era in stato ''partito'' o ''sta per arrivare'', non può diventare ''atterrato''!', OLD.id_flight;

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

			RAISE EXCEPTION 'Non possono ancora essere aperti i check-in per il volo %L! La sua data di partenza è %L con ritardo di %L minuti!', NEW.id_flight, NEW.departure_time::date, NEW.flight_delay;

		END IF;
	
	ELSIF  NEW.flight_status = 'departed' OR NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN 

		IF (NEW.departure_time::date) > CURRENT_DATE THEN
		--un volo deve invece partire almeno nella data stabilita, al più partirà dopo per ritardi

			RAISE EXCEPTION 'Il volo %L non può ancora partire! La sua data di partenza è %L con ritardo di %L minuti!', NEW.id_flight, NEW.departure_time::date, NEW.flight_delay;

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

			RAISE EXCEPTION 'Il volo %L è verso Napoli, non può avere stato ''in partenza''', OLD.id_flight;

		END IF;

		IF OLD.flight_status <> 'programmed' THEN

			RAISE EXCEPTION 'Il volo %L non era in stato ''programmato'', non può diventare ''in partenza''!', OLD.id_flight;

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

			RAISE EXCEPTION 'Il volo %L non era in stato ''in partenza'', non può diventare ''partito''!', OLD.id_flight;

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

			RAISE EXCEPTION 'Il volo %L non era in stato ''programmato'' o ''in partenza'', non può diventare ''in ritardo''!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non era in stato ''partito'', non può diventare ''atterrato''!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non era in stato ''partito'', non può diventare ''in arrivo''!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non era in stato ''partito'' o ''in arrivo'', non può diventare ''atterrato''!', OLD.id_flight;

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

		RAISE EXCEPTION 'Il volo %L è già atterrato, non può più cambiare stato!', OLD.id_flight;
		
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

			RAISE EXCEPTION 'Il volo da Napoli %L non era in stato ''programmato'', non può diventare ''in partenza''!', OLD.id_flight;

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
	
		
		--questo if serve perché solo un volo programmed può essere impostato ad aboutToDepart
		IF OLD.flight_status <> 'programmed' AND OLD.flight_status <> 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo verso Napoli %L non era in stato ''programmato'' o ''in partenza'', non può diventare ''decollato''!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non può partire finchè ha prenotazioni pending!', NEW.id_flight;

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

				RAISE EXCEPTION 'Il volo %L non può partire finchè ha prenotazioni pending!', NEW.id_flight;

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

				RAISE EXCEPTION 'Il volo da Napoli %L non è in stato ''in partenza'', ''in ritardo'', ''partito'', ''atterrato'', non lo si può inserire con un gate!', NEW.id_flight;

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

				RAISE EXCEPTION 'Il volo per Napoli %L non è in stato ''in arrivo'', ''in ritardo'', ''partito'', ''atterrato'', non lo si può inserire con un gate!', NEW.id_flight;

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

				RAISE EXCEPTION 'Il volo da Napoli %L non è in stato ''in partenza'' o ''in ritardo'', non gli si può assegnare un gate!', OLD.id_flight;

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

				RAISE EXCEPTION 'Il volo verso Napoli %L non è in stato ''in arrivo'' o ''in ritardo'', non gli si può assegnare un gate!', NEW.id_flight;

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

			RAISE EXCEPTION 'Il volo %L è in stato %L, non gli si può assegnare un gate!', NEW.id_flight, NEW.flight_status;

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

				RAISE EXCEPTION 'Il volo da napoli %L è in stato %L, non può non avere un gate da cui è partito!', NEW.id_flight, NEW.flight_status;

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

				RAISE EXCEPTION 'Il volo verso napoli %L è in stato ''atterrato'', non può non avere un gate dove è atterrato!', NEW.id_flight;

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

				RAISE EXCEPTION 'Il volo da napoli %L è in stato %L, non si può modificare il gate da cui è partito!', OLD.id_flight, NEW.flight_status;

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

				RAISE EXCEPTION 'Il volo per napoli %L è in stato ''atterrato'', non si può modificare il gate in cui è atterrato!', OLD.id_flight;

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

		RAISE EXCEPTION 'Il volo %L è in partenza oppure è già partito, non si possono inserire nuove prenotazioni!', NEW.id_flight;

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

		RAISE EXCEPTION 'Il volo %L è in partenza oppure è già partito, non si possono inserire biglietti!', NEW.id_flight;

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

		RAISE EXCEPTION 'Il volo %L è in partenza oppure è già partito, il bagaglio non può essere inserito!', associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_ins_luggage_aToDep_or_more_flight
BEFORE INSERT ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_ins_luggage_aToDep_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------









