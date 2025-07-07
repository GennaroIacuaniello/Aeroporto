CREATE TABLE Admin (

	username VARCHAR(20) PRIMARY KEY,
	mail VARCHAR(50) UNIQUE NOT NULL,
	hashed_password CHAR(64) NOT NULL,

	CONSTRAINT correctness_of_username_minimal_lenght CHECK( LENGHT(username) >= 4 )

);

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Customer (

	username VARCHAR(20) PRIMARY KEY,
	mail VARCHAR(50) UNIQUE,
	hashed_password CHAR(64) NOT NULL,

	CONSTRAINT correctness_of_username_minimal_lenght CHECK( LENGHT(username) >= 4 )

);

-------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION fun_unique_attribute_between_two_tables() --TG_ARGV[0] = attr VARCHAR(100), TG_ARGV[1] = other_table VARCHAR(100)
RETURNS trigger AS $$
DECLARE
	
	attr VARCHAR(100) := TG_ARGV[0];
	other_table VARCHAR(100) := TG_ARGV[1];
	found BOOLEAN := false;

BEGIN

	EXECUTE format('EXISTS(SELECT * FROM %I T WHERE T.%I = (SELECT ($1).%I)', other_table, attr, attr) INTO found USING NEW;
	-- (SELECT ($1).%I) è il modo per selezionare dinamicamente un attributo dalla tupla ($1), in questo caso l'intera tupla NEW

	IF found THEN
		RAISE EXCEPTION '%I già presente tra la tabella %I e la tabella %I!', INITCAP(attr), TG_TABLE_NAME, other_table;
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_username_admin_with_customer
BEFORE INSERT OR UPDATE OF username ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_attribute_between_two_tables('username', 'customer');

CREATE OR REPLACE TRIGGER unique_username_customer_with_admin
BEFORE INSERT OR UPDATE OF username ON Customer
FOR EACH ROW
EXECUTE FUNCTION fun_unique_attribute_between_two_tables('username', 'admin');

CREATE OR REPLACE TRIGGER unique_mail_admin_with_customer
BEFORE INSERT OR UPDATE OF mail ON Admin
FOR EACH ROW
EXECUTE FUNCTION fun_unique_attribute_between_two_tables('mail', 'customer');

CREATE OR REPLACE TRIGGER unique_mail_customer_with_admin
BEFORE INSERT OR UPDATE OF mail ON Customer
FOR EACH ROW
EXECUTE FUNCTION fun_unique_attribute_between_two_tables('mail', 'admin');

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
	date DATE NOT NULL,
	departure_time	TIME NOT NULL,
	arrival_time TIME NOT NULL,
	flight_status FlightStatus NOT NULL,
	max_seats SMALLINT NOT NULL,
	free_seats SMALLINT NOT NULL,
	destination_or_origin VARCHAR(64) NOT NULL,
	flight_delay Minutes,
	flight_type FlightType NOT NULL,
	id_gate SMALLINT,
	
	
	CONSTRAINT max_free_seats CHECK(free_seats <= max_seats),
	CONSTRAINT arrival_after_departure CHECK( arrival_time > departure_time),
	CONSTRAINT destination_or_origin_never_Napoli CHECK(destination_or_origin NOT LIKE 'Napoli'),
	--perché a priori, che sia departing o arriving, memorizziamo sempre l' "altra città", non Napoli
	CONSTRAINT delay_not_negative CHECK(flight_delay IS NULL OR flight_delay > 0),
	CONSTRAINT correctness_of_id_gate CHECK(id_gate IS NULL OR id_gate BETWEEN 0 AND 19) --l'aeroporto di Napoli ha 20 gate

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO NON PUò MAI CAMBIARE TIPO

CREATE OR REPLACE FUNCTION fun_block_update_of_flight_type()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type <> NEW.flight_type THEN

		RAISE EXCEPTION 'I voli non possono cambiare tipo!';

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_update_of_flight_type
BEFORE UPDATE OF flight_type ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_update_of_flight_type();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO DEPARTING PUò AVERE FLIGHT_STATUS AD ABOUTTODEPART

CREATE OR REPLACE FUNCTION fun_only_a_departing_flight_can_become_aboutToDepart()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = false THEN

		IF NEW.flight_status = 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo %L è verso Napoli, non può avere stato ''in partenza''', OLD.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_a_departing_flight_can_become_aboutToDepart
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_a_departing_flight_can_become_aboutToDepart();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO ARRIVING PUò AVERE FLIGHT_STATUS AD ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_only_an_arriving_flight_can_become_aboutToArrive()
RETURNS TRIGGER
AS $$
BEGIN

	IF OLD.flight_type = true THEN

		IF NEW.flight_status = 'aboutToArrive' THEN

			RAISE EXCEPTION 'Il volo %L è da Napoli, non può avere stato ''in arrivo''', OLD.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER only_an_arriving_flight_can_become_aboutToArrive
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_an_arriving_flight_can_become_aboutToArrive();

-------------------------------------------------------------------------------------------------------------------------

CREATE TYPE BookingStatus AS ENUM ('confirmed', 'pending', 'cancelled');

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Booking (

	id_booking SERIAL PRIMARY KEY,
	booking_status BookingStatus NOT NULL,
	booking_date DATE NOT NULL,
	buyer VARCHAR(20) NOT NULL,
	id_flight  VARCHAR(15) NOT NULL,

	CONSTRAINT buyer_FK FOREIGN KEY(buyer) REFERENCES Customer(username),
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight)

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO MAI MODIFICARE I CAMPI id_booking, buyer, id_flight DI UNA PRENOTAZIONE

CREATE OR REPLACE FUNCTION fun_block_updates_of_id_booking_buyer_id_flight_on_passenger()
RETURNS TRIGGER
AS $$
BEGIN

	RAISE EXCEPTION 'Non si possono modificare i dati di prenotazione associata, acquirente, volo associato di una prenotazione!';

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_updates_of_id_booking_buyer_id_flight_on_passenger
BEFORE UPDATE OF id_booking, buyer, id_flight ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_updates_of_id_booking_buyer_id_flight_on_passenger();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO è aboutToDepart, DEPARTED, aboutToArrive, o  LANDED, LE PRENOTAZIONI NON POSSONO PIÙ ESSERE MODIFICATE (NEMMENO CANCELLATE)

CREATE OR REPLACE FUNCTION fun_block_modifying_booking_if_flight_aboutToDepart_departed_aboutToArrive_landed()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     WHERE id_flight = NEW.id_flight);

BEGIN
	

	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo %L è in partenza/partito/sta per atterrare/atterrato, non si può modificare la prenotazione!', NEW.id_flight;

	END IF;


	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_modifying_booking_if_flight_aboutToDepart_departed_aboutToArrive_landed
BEFORE UPDATE ON Booking
FOR EACH ROW
EXECUTE FUNCTION fun_block_modifying_booking_if_flight_aboutToDepart_departed_aboutToArrive_landed();

-------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Passenger (

	ticket_number CHAR(13) PRIMARY KEY,
	full_name VARCHAR(50),
	SSN VARCHAR(16),
	seat INTEGER,
	checked_in BOOLEAN NOT NULL DEFAULT false,
	id_booking INTEGER NOT NULL,
	id_flight  VARCHAR(15) NOT NULL,


	CONSTRAINT booking_FK FOREIGN KEY(id_booking) REFERENCES Booking(id_booking),
	CONSTRAINT id_flight_FK FOREIGN KEY(id_flight) REFERENCES Flight(id_flight),
	CONSTRAINT numeric_ticket_number CHECK( ticket_number ~ '^[0-9]+$' )

);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSTI SONO INTERI DA 0 A ASSOCIATED_FLIGHT.MAX_SEATS - 1

CREATE OR REPLACE FUNCTION fun_valid_passenger_seat()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT F
					     WHERE F.id_flight = NEW.id_flight);

BEGIN

	IF seat IS NOT NULL THEN
 
		IF NOT (seat BETWEEN 0 AND associated_flight.max_seats - 1) THEN
			
			RAISE EXCEPTION 'Posto non valido per il volo %L', NEW.id_flight;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_passenger_seat
BEFORE INSERT OR UPDATE OF seat ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_valid_passenger_seat();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER I PASSEGERI POSSONO AVERE QUALUNQUE DATO (APPARTE IL TICKET_NUMBER PK) A NULL, MA QUESTO SOLO SE LA PRENOTAZIONE NON È 'CONFFIRMED', 
--SE INVECE LO È, SOLO IL POSTO PUÒ ESSERE NULL

CREATE OR REPLACE FUNCTION fun_valid_booking_data()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     WHERE B.id_booking = NEW.id_booking);

BEGIN

	IF associated_booking.booking_status = 'confirmed' THEN
 
		IF (NEW.full_name IS NULL) OR (NEW.SSN IS NULL) THEN
			
			RAISE EXCEPTION 'Dati mancanti per il passeggero il cui biglietto ha numero %L', NEW.ticket_number;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER valid_booking_data
BEFORE INSERT OR UPDATE ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_valid_booking_data();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE FACCIO IL CHECK-IN (OSSIA SE CHECK-IN = TRUE), ALLORA SE POSTO È NULL, ASSEGNO AL PASSEGGERO IL PRIMO POSTO LIBERO 
--(E QUESTO TRIGGER È BEFORE INSERT OR UPDATE OF CHECKED_IN, SEAT; COSÌ NON PUÒ MAI ACCADERE CHE UN PASSEGGERO CON CHECK-IN TRUE NON ABBIA POSTO)

CREATE OR REPLACE FUNCTION fun_valid_passenger_seat_after_check_in()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     WHERE id_flight = NEW.id_flight);

	prev_seat PASSENGER.seat%TYPE := -1; -- (-1) perché i posti iniziano da 0

BEGIN

	IF NEW.checked_in = true AND NEW.seat IS NULL THEN
 	
		FOR selected_seat IN (SELECT P.seat FROM PASSENGER P
				      WHERE P.ticket_number <> NEW.ticket_number AND P.id_flight = NEW.id_flight AND 
					(SELECT B.booking_status FROM BOOKING B
					 WHERE B.id_booking = P.id_booking) <> 'cancelled' AND P.seat IS NOT NULL
					 ORDER BY P.seat) LOOP
			
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

CREATE OR REPLACE TRIGGER valid_passenger_seat_after_check_in
BEFORE INSERT OR UPDATE OF checked_in, seat ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_valid_passenger_seat_after_check_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO È aboutToDepart, PUÒ ESSERE MOFIFICATO SOLO IL POSTO DI UN PASSEGGERO

CREATE OR REPLACE FUNCTION fun_block_updates_not_of_seat_of_passenger_if_flight_aboutToDepart()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     WHERE id_flight = NEW.id_flight);

BEGIN
	
	IF NEW.full_name <> OLD.full_name OR NEW.SSN <> OLD.SSN THEN

		IF associated_flight.flight_status = 'aboutToDepart' THEN

			RAISE EXCEPTION 'Il volo %L è in partenza, non si possono modificare i dati personali del passeggero!', NEW.id_flight;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_updates_not_of_seat_of_passenger_if_flight_aboutToDepart
BEFORE UPDATE OF full_name, SSN ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_block_updates_not_of_seat_of_passenger_if_flight_aboutToDepart();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SE UN VOLO È DEPARTED o aboutToArrive O LANDED, I DATI DEI PASSEGGERI NON POSSONO PIÙ ESSERE MODIFICATI

CREATE OR REPLACE FUNCTION fun_block_updates_on_passenger_if_flight_departed_aboutToArrive_landed()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				    					 WHERE id_flight = NEW.id_flight);

BEGIN
	
	IF NEW.full_name <> OLD.full_name OR NEW.SSN <> OLD.SSN OR NEW.seat <> OLD.seat THEN

		IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

			RAISE EXCEPTION 'Il volo %L è partito/sta per atterrare/atterrato, non si possono modificare i dati del passeggero %L!', NEW.id_flight, NEW.ticket_number;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_updates_on_passenger_if_flight_departed_aboutToArrive_landed
BEFORE UPDATE OF full_name, SSN, seat ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_block_updates_on_passenger_if_flight_departed_aboutToArrive_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO MAI MODIFICARE I CAMPI ticket_number, id_booking e id_flight DI UN PASSEGGERO

CREATE OR REPLACE FUNCTION fun_block_updates_of_ticket_number_id_booking_id_flight_on_passenger()
RETURNS TRIGGER
AS $$
BEGIN

	RAISE EXCEPTION 'Non si possono modificare i dati ticket_number, prenotazione associata, volo associato di un passeggero!';

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_updates_of_ticket_number_id_booking_id_flight_on_passenger
BEFORE UPDATE OF ticket_number, id_booking, id_flight ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_block_updates_of_ticket_number_id_booking_id_flight_on_passenger();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON CI SONO PASSEGGERI CHE OCCUPANO LO STESSO POSTO SU UN DATO VOLO

CREATE OR REPLACE FUNCTION fun_unique_passenger_per_seat_per_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking FLIGHT%ROWTYPE := (SELECT * FROM BOOKING B
					     				  WHERE B.id_booking = NEW.id_booking);

BEGIN
	
	IF associate_booking.booking_status <> 'cancelled' AND NEW.seat IS NOT NULL THEN

		IF EXISTS(SELECT * FROM PASSENGER P
		  	  WHERE P.ticket_number <> NEW.ticket_number AND P.id_flight = NEW.id_flight AND P.seat = NEW.seat AND (SELECT B.booking_status FROM BOOKING B
					     																							WHERE B.id_booking = P.id_booking) <> 'cancelled' ) THEN
		
			RAISE EXCEPTION 'Posto già occupato per il volo %L', NEW.id_flight;

		END IF;
	END IF;
	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER unique_passenger_per_seat_per_flight
BEFORE INSERT OR UPDATE OF seat ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_unique_passenger_per_seat_per_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON CI SONO PIÙ PASSEGGERI CHE POSTI MASSIMI PER UN VOLO 

CREATE OR REPLACE FUNCTION fun_not_more_passengers_then_seats()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

	associated_booking FLIGHT%ROWTYPE := (SELECT * FROM BOOKING B
					     				  WHERE B.id_booking = NEW.id_booking);

	n_actually_occupied_seats FLIGHT.max_seats%TYPE;

BEGIN
	
	IF associate_booking.booking_status <> 'cancelled' THEN

		SELECT COUNT(*) INTO n_actually_occupied_seats
		FROM PASSENGER P
		WHERE P.id_flight = NEW.id_flight AND (SELECT B.booking_status FROM BOOKING B
					    	       			   WHERE B.id_booking = P.id_booking) <> 'cancelled';

		IF n_actually_occupied_seats + 1 > associated_flight.max_seats THEN
		-- + 1 perché sono in BEFORE INSERT, quindi "simulo" l'inserimento
	
			RAISE EXCEPTION 'Il volo %L è già pieno!', NEW.id_flight;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER not_more_passengers_then_seats
BEFORE INSERT ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_not_more_passengers_then_seats();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER CHE AGGIORNA I FREE_SEATS DI UN VOLO ALL'INSERIMENTO DI UN PASSEGGERO (DI UNA PRENOTAZIONE NON CANCELLATA) PER QUEL VOLO

CREATE OR REPLACE FUNCTION fun_if_passenger_inserted_update_free_seats()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     				   WHERE B.id_booking = NEW.id_booking);


BEGIN
	
	IF associate_booking.booking_status <> 'cancelled' THEN

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

CREATE OR REPLACE TRIGGER if_passenger_inserted_update_free_seats
BEFORE INSERT ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_if_passenger_inserted_update_free_seats();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO PER LE PRENOTAZIONI 'CONFIRMED' UN PASSEGERO PUò ESSERE CHECKED_IN

CREATE OR REPLACE FUNCTION fun_check_passenger_checked_in_only_if_confirmed_booking()
RETURNS TRIGGER
AS $$
DECLARE

	associated_booking BOOKING%ROWTYPE := (SELECT * FROM BOOKING B
					     				   WHERE B.id_booking = NEW.id_booking);

BEGIN
	
	IF NEW.checked_in = true THEN

		IF associate_booking.booking_status <> 'confirmed' THEN

			RAISE EXCEPTION 'La prenotazione %L non è confermata, non si può fare il check-in!', NEW.id_booking;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_passenger_checked_in_only_if_confirmed_booking
BEFORE INSERT OR UPDATE OF checked_in ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_check_passenger_checked_in_only_if_confirmed_booking();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO PER I VOLI aboutToDepart, DEPARTED, aboutToArrive o LANDED UN PASSEGERO PUÒ ESSERE CHECKED_IN

CREATE OR REPLACE FUNCTION fun_check_passenger_checked_in_only_if_flight_aboutToDepart_departed_landed()
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

CREATE OR REPLACE TRIGGER check_passenger_checked_in_only_if_flight_aboutToDepart_departed_landed
BEFORE INSERT OR UPDATE OF checked_in ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_check_passenger_checked_in_only_if_flight_aboutToDepart_departed_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL CHECK-IN È ANNULLABILE SOLO SE IL VOLO NON È DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_check_cancelling_check_in_only_if_flight_not_departed_landed()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     					 WHERE id_flight = NEW.id_flight);

BEGIN
	
	IF OLD.checked_in = true AND NEW.checked_in = false THEN

		IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

			RAISE EXCEPTION 'Il volo %L è già partito, non si può annullare il check-in!', NEW.id_flight;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_cancelling_check_in_only_if_flight_not_departed_landed
BEFORE UPDATE OF checked_in ON Passenger
FOR EACH ROW
EXECUTE FUNCTION fun_check_cancelling_check_in_only_if_flight_not_departed_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER CONTROLLO CHE L'ATTRIBBUTO FREE_SEATS SIA CORRETTO QUANDO LO SI INSERISCE/MODIFICA
--POTREI FARLO SOLO SU INSERIMENTO, MA, DATO CHE MI SERVE SIA MODIFICABILE, POTREI POI POTENZIALMENTE MODIFICARLO A CASO

CREATE OR REPLACE FUNCTION fun_correctness_of_free_seats_per_flight()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF NEW.free_seats <> NEW.max_seats - (SELECT COUNT (*)
					      				  FROM PASSENGER P
					      				  WHERE P.id_flight = NEW.id_flight AND (SELECT B.booking_status FROM BOOKING B
					    	       				     					WHERE B.id_booking = P.id_booking) <> 'cancelled') THEN

		RAISE EXCEPTION 'Errore nei posti liberi del volo %L!', NEW.id_flight;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER correctness_of_free_seats_per_flight
BEFORE INSERT OR UPDATE OF free_seats ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_correctness_of_free_seats_per_flight();

-- È BEFORE perché altrimenti dà conflitti quando si fa l'update dei free_seats quando si inserisce un passeggero o si fa l'update di un booking_status 
--(verrebbe controllato il nuovo valore dei free_seats, ma senza aver effettivamente aggiornato la tabella PASSENGER/BOOKING, dando quindi errore)

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
	id_passenger CHAR(13) NOT NULL,


	CONSTRAINT passenger_FK FOREIGN KEY(id_passenger) REFERENCES Passenger(ticket_number),
	CONSTRAINT correctness_of_id_luggage_after_check_in_minimal_lenght CHECK( id_luggage_after_check_in IS NULL OR LENGHT(id_luggage_after_check_in) > 13)
	--questa check serve per come è costruito id_luggage_after_check_in, 
	--ossia come concatenazione di ticket_number del passeggero associato + 'numero del bagaglio'
);

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER PER I BAGAGLI, SE LA PRENOTAZIONE DEL PASSEGGERO ASSOCIATO NON è CONFIRMED, IL TIPO PUò ESSERE A NULL, MA SE è CONFIRMED NO

CREATE OR REPLACE FUNCTION fun_valid_luggage_type()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_booking BOOKING%ROWTYPE;

BEGIN
	
	associated_booking := (SELECT * FROM BOOKING B
			      		   WHERE B.id_booking = associated_passenger.id_booking);

	IF associated_booking.booking_status = 'confirmed' THEN
 
		IF NEW.luggage_type IS NULL THEN
			
			RAISE EXCEPTION 'Dati mancanti per il bagaglio %L del passeggero il cui biglietto ha numero %L', NEW.id_luggage, associated_passenger.ticket_number;
	
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

--TRIGGER SE IL PASSEGGERO NON è CHECKED_IN, IL LUGGAGE_STATUS PUò ESSERE SOLO BOOKED

CREATE OR REPLACE FUNCTION fun_luggage_status_only_booked_if_booking_cancelled()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

BEGIN

	IF associated_passenger.checked_in = false THEN
 
		IF NEW.luggage_status <> 'booked' THEN
			
			RAISE EXCEPTION 'Il passeggero con ticket number %L non ha ancora fatto il check_in, il bagaglio %L non può avere stato divero da ''prenotato''!', associated_passenger.ticket_number, OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_status_only_booked_if_booking_cancelled
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_only_booked_if_booking_cancelled();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO UN PASSEGGERO FA IL CHECK-IN, VIENE GENERATO L'id_luggage_after_check_in PER TUTTI I SUOI BAGAGLI
--(VIENE GENERATO COME ticket_number concatenato un intero da 0 a numero di bagagli del passeggero - 1)

CREATE OR REPLACE FUNCTION fun_generation_of_id_luggage_after_check_in()
RETURNS TRIGGER
AS $$
DECLARE

	i INTEGER := 0;

BEGIN
	
	IF OLD.checked_in = false AND NEW.checked_in = true THEN

		FOR selected_luggage IN (SELECT * FROM LUGGAGE L
								 WHERE L.id_passenger = NEW.ticket_number) LOOP

			UPDATE LUGGAGE
			SET id_luggage_after_check_in = selected_passenger.ticket_number || i;
			WHERE id_luggage = selected_luggage.id_luggage;

			i := i + 1;

		END LOOP;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER generation_of_id_luggage_after_check_in
AFTER UPDATE OF checked_in ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_generation_of_id_luggage_after_check_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SU id_luggage_after_check_in NOT NULL SE IL SUO PASSEGGERO è CHECKED-IN

CREATE OR REPLACE FUNCTION fun_check_id_luggage_after_check_in_not_null_if_passenger_checked_in()
RETURNS TRIGGER
AS $$
DECLARE

	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          				   WHERE P.ticket_number = NEW.id_passenger);

BEGIN
	
	IF associated_passenger.checked_in = true THEN

		IF NEW.id_luggage_after_check_in IS NULL THEN

			RAISE EXCEPTION 'Il passeggero con ticket number %L ha efettuato il check_in, il suo bagaglio non può non avere id_luggage_after_check_in!', associated_passenger.ticket_number;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_id_luggage_after_check_in_not_null_if_passenger_checked_in
BEFORE INSERT OR UPDATE OF id_luggage_after_check_in ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_check_id_luggage_after_check_in_not_null_if_passenger_checked_in();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON PUÒ ESSERE BOOKED SE IL VOLO È DEPARTED, ABOUTTOARRIVE O LANDED

CREATE OR REPLACE FUNCTION fun_valid_luggage_status_after_departure()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      WHERE F.id_flight = associated_passenger.id_flight);

	IF associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN
 
		IF NEW.luggage_status = 'booked' THEN
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L è già partito, il bagaglio %L non può avere stato ''prenotato''!', associated_passenger.id_flight, associated_passenger.ticket_number, NEW.id_luggage;
	
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

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE WITHDROWABLE SE IL VOLO NON è LANDED

CREATE OR REPLACE FUNCTION fun_luggage_not_witdrowable_if_flight_not_landed()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      WHERE F.id_flight = associated_passenger.id_flight);

	IF NEW.luggage_status = 'withdrowable' THEN
 
		IF associated_flight.flight_status <> 'landed' THEN
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L non è ancora atterrato, il bagaglio %L non può avere stato ''ritirabile''!', associated_passenger.id_flight, associated_passenger.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_not_witdrowable_if_flight_not_landed
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_not_witdrowable_if_flight_not_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE LOST SE IL VOLO NON è LANDED

CREATE OR REPLACE FUNCTION fun_luggage_not_lost_if_flight_not_landed()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      WHERE F.id_flight = associated_passenger.id_flight);

	IF NEW.luggage_status = 'lost' THEN
 
		IF associated_flight.flight_status <> 'landed' THEN
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L non è ancora atterrato, il bagaglio %L non può avere stato ''smarrito''!', 	associated_passenger.id_flight, associated_passenger.ticket_number, NEW.id_luggage;
	
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

CREATE OR REPLACE FUNCTION fun_luggage_not_loaded_if_flight_pogrammed_cancelled_landed()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      WHERE F.id_flight = associated_passenger.id_flight);

	IF NEW.luggage_status = 'loaded' THEN
 
		IF associated_flight.flight_status = 'programmed' OR associated_flight.flight_status = 'cancelled' OR associated_flight.flight_status = 'landed' THEN
			
			RAISE EXCEPTION 'Il volo %L del passeggero con ticket number %L è progammato/cancellato/atterrato, il bagaglio %L non può avere stato ''caricato''!', associated_passenger.id_flight, associated_passenger.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_not_loaded_if_flight_pogrammed_cancelled_landed
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_not_loaded_if_flight_pogrammed_cancelled_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IL LUGGAGE_STATUS NON Può ESSERE DIVERSO DA BOOKED SE LA PRENOTAZIONE è CANCELLED

CREATE OR REPLACE FUNCTION fun_luggage_status_only_booked_if_booking_cancelled()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_booking BOOKING%ROWTYPE;

BEGIN
	
	associated_booking := (SELECT * FROM BOOKING B
			      WHERE B.id_booking = associated_passenger.id_booking);

	IF associated_booking.booking_status = 'cancelled' THEN
 
		IF NEW.luggage_status <> 'booked' THEN
			
			RAISE EXCEPTION 'La prenotazione %L del passeggero con ticket number %L è cancellata, il bagaglio %L non può avere stato divero da ''prenotato''!', associated_passenger.id_booking, associated_passenger.ticket_number, NEW.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_status_only_booked_if_booking_cancelled
BEFORE INSERT OR UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_only_booked_if_booking_cancelled();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS BOOKED PUò DIVENTARE LOADED

CREATE OR REPLACE FUNCTION fun_luggage_status_can_become_loaded_only_if_booked()
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

CREATE OR REPLACE TRIGGER luggage_status_can_become_loaded_only_if_booked
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_can_become_loaded_only_if_booked();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS LOADED PUò DIVENTARE WITHDROWABLE

CREATE OR REPLACE FUNCTION fun_luggage_status_can_become_withdrowable_only_if_loaded()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.luggage_status <> OLD.luggage_status THEN
 
		IF NEW.luggage_status = 'withdrowable' AND OLD.luggage_status <> 'loaded' THEN
			
			RAISE EXCEPTION 'Il bagaglio %L non era in stato ''caricato'', non può diventare ''ritirabile''!', OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_status_can_become_withdrowable_only_if_loaded
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_can_become_withdrowable_only_if_loaded();

----------------------------------------------------------------------------------------------

--TRIGGER SOLO UN BAGAGLIO CON LUGGAGE STATUS WITHDROWABLE PUò DIVENTARE LOST

CREATE OR REPLACE FUNCTION fun_luggage_status_can_become_lost_only_if_withdrowable()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.luggage_status <> OLD.luggage_status THEN
 
		IF NEW.luggage_status = 'lost' AND OLD.luggage_status <> 'withdrowable' THEN
			
			RAISE EXCEPTION 'Il bagaglio %L non era in stato ''ritirabile'', non può diventare ''smarrito''!', OLD.id_luggage;
	
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggage_status_can_become_lost_only_if_withdrowable
BEFORE UPDATE OF luggage_status ON Luggage
FOR EACH ROW
EXECUTE FUNCTION fun_luggage_status_can_become_lost_only_if_withdrowable();

----------------------------------------------------------------------------------------------


--TRIGGER QUANDO UN VOLO DEPARTING PARTE, TUTTI I LUGGAGE_STATUS DEI BAGAGLI DEI SUOI PASSEGGERI CON CHECKED_IN A TRUE VENGONO MESSI A LOADED

CREATE OR REPLACE FUNCTION fun_luggages_loaded_when_depart()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF OLD.flight_status <> 'departed' AND NEW.flight_status = 'departed' THEN

			--questo if serve perché solo un volo aboutToDepart può essere impostato a departed
			IF OLD.flight_status <> 'aboutToDepart' THEN

				RAISE EXCEPTION 'Il volo da Napoli %L non era in stato ''in partenza'', non può diventare ''partito''!', OLD.id_flight;

			END IF;

			FOR selected_booking IN (SELECT * FROM BOOKING B
									WHERE B.id_flight = NEW.id_flight AND B.boking_status <> 'cancelled') LOOP

					
				--non devo controllare la prenotazione non sia pending, 
				--perchè tanto il volo era per forza aboutToDepart, e quindi le sue prenotazioni già non potevano essere pending
				FOR selected_passenger IN (SELECT * FROM PASSENGER P
											WHERE P.id_booking = selected_booking.id_booking AND P.checked_in = true) LOOP

					FOR selected_luggage IN (SELECT * FROM LUGGAGE L
											WHERE L.id_passenger = selected_passenger.ticket_number) LOOP

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

--TRIGGER QUANDO UN VOLO ATTERRA, TUTTI I LUGGAGE_STATUS DEI BAGAGLI DEI SUOI PASSEGGERI CHECKED_IN VENGONO MESSI A WITHDROWABLE

CREATE OR REPLACE FUNCTION fun_luggages_withdrowable_when_landed()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF OLD.flight_status <> 'landed' AND NEW.flight_status = 'landed' THEN 

		--questo if serve perché solo un volo departed o aboutToArrive può essere impostato a departed
		IF OLD.flight_status <> 'departed' AND OLD.flight_status <> 'aboutToArrive' THEN

			RAISE EXCEPTION 'Il volo %L non era in stato ''partito'' o ''sta per arrivare'', non può diventare ''atterrato''!', OLD.id_flight;

		END IF;

		FOR selected_booking IN (SELECT * FROM BOOKING B
									WHERE B.id_flight = NEW.id_flight AND B.boking_status <> 'cancelled') LOOP

				
			--non devo controllare la prenotazione non sia pending, 
			--perchè tanto il volo era già partito, e quindi le sue prenotazioni già non potevano essere pending
			FOR selected_passenger IN (SELECT * FROM PASSENGER P
										WHERE P.id_booking = selected_booking.id_booking AND P.checked_in = true) LOOP

				FOR selected_luggage IN (SELECT * FROM LUGGAGE L
											WHERE L.id_passenger = selected_passenger.ticket_number) LOOP

					UPDATE LUGGAGE
					SET luggage_status = 'withdrowable'
					WHERE id_luggage = selected_luggage.id_luggage;

				END LOOP;

			END LOOP;

		END LOOP;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER luggages_withdrowable_when_landed
AFTER UPDATE OF flight_status ON Flight
FOR EACH ROW
EXECUTE FUNCTION fun_luggages_withdrowable_when_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO PUÒ ESSERE aboutToDepart O DEPARTED aboutToArrive O LANDED SOLO SE FLIGHT.DATE <= DATA_CORRENTE (IMPLEMENTATO CON IF (aboutToDepart O DEPARTED O LANDED) THEN IF DATA > DATA_CORRENTE THEN RAISE EXCEPTION )

CREATE OR REPLACE FUNCTION fun_check_on_date_before_aboutToDepart_or_more()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF NEW.flight_status = 'aboutToDepart' THEN
	
		IF NEW.date > CURRENT_DATE + 1 THEN
		--posso aprire i check-in (e quindi mettere ad aboutToDepart) al più il giorno prima della partenza del volo
		--e quindi non posso aprirli solo se NEW.date non è nè oggi nè domani

			RAISE EXCEPTION 'Non possono ancora essere aperti i check-in per il volo %L! La sua data di partenza è %L con ritardo di %L minuti!', NEW.id_flight, NEW.date, NEW.flight_delay;

		END IF;
	
	ELSIF  NEW.flight_status = 'departed' OR NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN 

		IF NEW.date > CURRENT_DATE THEN
		--un volo deve invece partire almeno nella data stabilita, al più partirà dopo per ritardi

			RAISE EXCEPTION 'Il volo %L non può ancora partire! La sua data di partenza è %L con ritardo di %L minuti!', NEW.id_flight, NEW.date, NEW.flight_delay;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_on_date_before_aboutToDepart_or_more
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_check_on_date_before_aboutToDepart_or_more();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO DEPARTING PROGRAMMED Può ESSERE AGGIORNATO AD aboutToDepart

CREATE OR REPLACE FUNCTION fun_only_a_programmed_flight_can_become_aboutToDepart()
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

CREATE OR REPLACE TRIGGER only_a_programmed_flight_can_become_aboutToDepart
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_a_programmed_flight_can_become_aboutToDepart();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING Può ESSERE AGGIORNATO A DEPARTED SOLO SE ERA aboutToDepart 

CREATE OR REPLACE FUNCTION fun_only_an_aboutToDepart_flight_can_become_departed()
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

CREATE OR REPLACE TRIGGER only_an_aboutToDepart_flight_can_become_departed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_an_aboutToDepart_flight_can_become_departed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO PROGRAMMED O ABOUTTODEPART O DEPARTED O ABOUTTOARRIVE Può ESSERE AGGIORNATO A DELAYED

CREATE OR REPLACE FUNCTION fun_only_a_programmed_or_aboutToDepart_flight_can_become_delayed()
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

CREATE OR REPLACE TRIGGER only_a_programmed_or_aboutToDepart_flight_can_become_delayed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_a_programmed_or_aboutToDepart_flight_can_become_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING Può ESSERE AGGIORNATO A LANDED SOLO SE DEPARTED

CREATE OR REPLACE FUNCTION fun_departing_flight_can_become_landed_only_if_departed()
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

CREATE OR REPLACE TRIGGER departing_flight_can_become_landed_only_if_departed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_departing_flight_can_become_landed_only_if_departed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER SOLO UN VOLO ARRIVING DEPARTED Può ESSERE AGGIORNATO A ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_only_a_departed_arriving_flight_can_become_aboutToArrive()
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

CREATE OR REPLACE TRIGGER only_a_departed_arriving_flight_can_become_aboutToArrive
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_only_a_departed_arriving_flight_can_become_aboutToArrive();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO ARRIVING Può ESSERE AGGIORNATO A LANDED SOLO SE DEPARTED O ABOUTTOARRIVE

CREATE OR REPLACE FUNCTION fun_arriving_flight_can_become_landed_only_if_departed_or_aboutToArrive()
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

CREATE OR REPLACE TRIGGER arriving_flight_can_become_landed_only_if_departed_or_aboutToArrive
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_arriving_flight_can_become_landed_only_if_departed_or_aboutToArrive();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO LANDED NON Può PIù CAMBIARE FLIGHT_STATUS
CREATE OR REPLACE FUNCTION fun_block_modifying_flight_status_if_landed()
RETURNS TRIGGER
AS $$
BEGIN
	
	IF OLD.flight_status = 'landed' AND NEW.flight_status <> 'landed' THEN

		RAISE EXCEPTION 'Il volo %L è già atterrato, non può più cambiare stato!', OLD.id_flight;
		
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_modifying_flight_status_if_landed
BEFORE UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_modifying_flight_status_if_landed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER IMPEDIRE L'AGGIORNAMENTO DI DATE PER UN VOLO GIA DEPARTED O aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_updating_date_if_already_departed()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF OLD.flight_status = 'departed' OR OLD.flight_status = 'aboutToArrive' OR OLD.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo %L è già partito, nn si può modificare la sua data di partenza!', OLD.id_flight;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_updating_date_if_already_departed
BEFORE UPDATE OF date ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_updating_date_if_already_departed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER QUANDO IL FLIGHT STATUS DI UN VOLO DEPARTING DIVENTA 'aboutToDepart', LE PRENOTAZIONI 'PENDING' DIVENTANO 'CANCELLED'

CREATE OR REPLACE FUNCTION fun_change_booking_status_when_departing_abutToDepart()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN
	
		IF TG_OP <> 'INSERT' THEN 
		--questi due if servono in caso di update, perché solo un volo programmed può essere impostato ad aboutToDepart
			IF OLD.flight_status <> 'programmed' THEN

				RAISE EXCEPTION 'Il volo da Napoli %L non era in stato ''programmato'', non può diventare ''in partenza''!', OLD.id_flight;

			END IF;

		END IF;

		IF OLD.flight_status <> 'aboutToDepart' AND NEW.flight_status = 'aboutToDepart' THEN
		
			FOR selected_booking IN (SELECT * FROM BOOKING B
							WHERE B.id_flight = NEW.id_flight AND B.boking_status = 'pending') LOOP

				UPDATE BOOKING
				SET booking_status = 'cancelled'
				WHERE id_booking = selected_booking.id_booking;

			END LOOP;
		
		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER change_booking_status_when_departing_abutToDepart
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_change_booking_status_when_departing_abutToDepart();

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

BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN
	
		IF TG_OP <> 'INSERT' THEN 
		--questi due if servono in caso di update, perché solo un volo programmed può essere impostato ad aboutToDepart
			IF OLD.flight_status <> 'programmed' AND OLD.flight_status <> 'aboutToDepart' THEN

				RAISE EXCEPTION 'Il volo verso Napoli %L non era in stato ''programmato'' o ''in partenza'', non può diventare ''decollato''!', OLD.id_flight;

			END IF;

		END IF;

		IF OLD.flight_status <> 'departed' AND NEW.flight_status = 'departed' THEN
		
			FOR selected_booking IN (SELECT * FROM BOOKING B
									 WHERE B.id_flight = NEW.id_flight AND B.boking_status <> 'cancelled') LOOP

				IF selected_booking.booking_status = 'pending' THEN

					UPDATE BOOKING
					SET booking_status = 'cancelled'
					WHERE id_booking = selected_booking.id_booking;

				ELSE
				--basta else, perchè tanto quelle già a cancelled non le prendo proprio con la select
					FOR selected_passenger IN (SELECT * FROM PASSENGER P
											   WHERE P.id_booking = selected_booking.id_booking) LOOP

						IF (i % 10) <> 0 THEN

							UPDATE PASSENGER
							SET checked_in = true
							WHERE ticket_number = selected_passenger.ticket_number;

							j := 0;
							FOR selected_luggage IN (SELECT * FROM LUGGAGE L
													 WHERE L.id_passenger = selected_passenger.ticket_number) LOOP

								UPDATE LUGGAGE
								SET luggage_status = 'loaded', id_luggage_after_check_in = selected_passenger.ticket_number || j;
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
BEFORE INSERT OR UPDATE OF flight_status ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_simulate_connection_when_arriving_departed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER UN VOLO DEPARTING DEPARTED O LANDED NON HA PRENOTAZIONI PENDING

CREATE OR REPLACE FUNCTION fun_check_on_booking_status_after_departing()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN

		IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN
		--non controllo se è 'aboutToDepart' perché se lo fosse, c'è un altro trigger che aggiorna le 'pending' a 'cancelled'
		
			IF EXISTS(SELECT * FROM BOOKING B
				WHERE B.id_flight = NEW.id_flight AND B.boking_status = 'pending') THEN

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
--non serve il controllo su departed, perchè il trigger simulate_connection_when_arriving_departed è BEFORE INSERT OR UPDATE, 
--quindi, in ogni caso, se lo stato è departed, le pending vengono cancellate

CREATE OR REPLACE FUNCTION fun_check_on_booking_status_after_departing()
RETURNS TRIGGER
AS $$
BEGIN
	
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN

		IF NEW.flight_status = 'aboutToArrive' OR NEW.flight_status = 'landed' THEN
		
			IF EXISTS(SELECT * FROM BOOKING B
				WHERE B.id_flight = NEW.id_flight AND B.boking_status = 'pending') THEN

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

CREATE OR REPLACE FUNCTION fun_update_free_seats_on_cancelling_booking()
RETURNS TRIGGER
AS $$
DECLARE

	n_passenger INTEGER := 0;
	--serve la variabile n_passenger perchè altrimenti questo trigger va in conflitto
	--con quello che controlla la correttezza dei free_seats
	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				     WHERE id_flight = NEW.id_flight);

BEGIN
		
	IF OLD.booking_status <> 'cancelled' AND NEW.booking_status = 'cancelled' THEN
	
		FOR selected_passenger IN (SELECT * FROM PASSENGER P
					 WHERE P.id_booking = NEW.id_booking) LOOP

			n_passenger := n_passenger + 1;

		END LOOP;

		UPDATE FLIGHT
		SET free_seats = free_seats + n_passenger
		WHERE id_flight = associated_flight.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_free_seats_on_cancelling_booking
AFTER UPDATE OF booking_status ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_update_free_seats_on_cancelling_booking();

--Non serve invece il trigger per decremenrare i free_seats se una prenotazione passa da 'cancelled' a qualcos'altro, perché non è possibile 'ripristinare' una prenotazione

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER CHE IMPEDISCE DI METTERE UNA PRENOTAZIONE DA CANCELLED A QUALCOS'ALTRO

CREATE OR REPLACE FUNCTION fun_blocking_restore_cancelled_booking()
RETURNS TRIGGER
AS $$
BEGIN
		
	IF OLD.booking_status = 'cancelled' THEN
	
		RAISE EXCEPTION 'Non è possibile ripristinare una prenotazione cancellata!';
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER blocking_restore_cancelled_booking
BEFORE UPDATE OF booking_status ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_blocking_restore_cancelled_booking();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO ASSEGNARE UN GATE AD UN VOLO DEPARTING SOLO SE è ABOUTTODEPART O DELAYED

CREATE OR REPLACE FUNCTION fun_departing_check_assignment_of_a_gate_only_if_aboutToDepart_delayed()
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

CREATE OR REPLACE TRIGGER departing_check_assignment_of_a_gate_only_if_aboutToDepart_delayed
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_departing_check_assignment_of_a_gate_only_if_aboutToDepart_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER POSSO ASSEGNARE UN GATE AD UN VOLO ARRIVING SOLO SE è ABOUTTOARRIVE O DELAYED

CREATE OR REPLACE FUNCTION fun_arriving_check_assignment_of_a_gate_only_if_aboutToArrive_delayed()
RETURNS TRIGGER
AS $$
BEGIN
		
	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN

		IF NEW.id_gate IS NOT NULL THEN

			IF NEW.flight_status <> 'aboutToArrive' AND NEW.flight_status <> 'delayed' THEN

				RAISE EXCEPTION 'Il volo verso Napoli %L non è in stato ''in arrivo'' o ''in ritardo'', non gli si può assegnare un gate!', OLD.id_flight;

			END IF;

		END IF;
	
	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER arriving_check_assignment_of_a_gate_only_if_aboutToArrive_delayed
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_arriving_check_assignment_of_a_gate_only_if_aboutToArrive_delayed();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI PROGRAMMED O CANCELLED CON UN GATE

CREATE OR REPLACE FUNCTION fun_block_assignment_of_a_gate_to_a_programmed_or_cancelled_flight()
RETURNS TRIGGER
AS $$
BEGIN

	IF NEW.id_gate IS NOT NULL THEN

		IF NEW.flight_status = 'programmed' OR NEW.flight_status = 'cancelled' THEN

			RAISE EXCEPTION 'Il volo %L è in stato %L, non gli si può assegnare un gate!', OLD.id_flight, OLD.flight_status;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_assignment_of_a_gate_to_a_programmed_or_cancelled_flight
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_assignment_of_a_gate_to_a_programmed_or_cancelled_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI DEPARTING DEPARTED O LANDED SENZA GATE

CREATE OR REPLACE FUNCTION fun_block_having_a_departing_flight_departed_or_landed_without_gate()
RETURNS TRIGGER
AS $$
BEGIN

	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN
	
		IF NEW.id_gate IS NULL THEN

			IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo da napoli %L è in stato %L, non può non avere un gate da cui è partito!', OLD.id_flight, OLD.flight_status;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_having_a_departing_flight_departed_or_landed_without_gate
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_having_a_departing_flight_departed_or_landed_without_gate();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI ARRIVING LANDED SENZA GATE

CREATE OR REPLACE FUNCTION fun_block_having_an_arriving_flight_landed_without_gate()
RETURNS TRIGGER
AS $$
BEGIN

	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = false AND NEW.flight_type = false THEN
	
		IF NEW.id_gate IS NULL THEN

			IF NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo verso napoli %L è in stato ''atterrato'', non può non avere un gate dove è atterrato!', OLD.id_flight;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_having_an_arriving_flight_landed_without_gate
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_having_an_arriving_flight_landed_without_gate();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER NON SI POSSONO INSERIRE VOLI DEPARTING DEPARTED O LANDED SENZA GATE

CREATE OR REPLACE FUNCTION fun_block_having_a_departing_flight_departed_or_landed_without_gate()
RETURNS TRIGGER
AS $$
BEGIN

	--serve if old and new per controllare che un volo non abbia cambiato tipo (cosa non consentita)
	IF OLD.flight_type = true AND NEW.flight_type = true THEN
	
		IF NEW.id_gate IS NULL THEN

			IF NEW.flight_status = 'departed' OR NEW.flight_status = 'landed' THEN

				RAISE EXCEPTION 'Il volo da napoli %L è in stato %L, non può non avere un gate da cui è partito!', OLD.id_flight, OLD.flight_status;

			END IF;

		END IF;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_having_a_departing_flight_departed_or_landed_without_gate
BEFORE INSERT OR UPDATE OF id_gate ON FLIGHT
FOR EACH ROW
EXECUTE FUNCTION fun_block_having_a_departing_flight_departed_or_landed_without_gate();

-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------


-----------------------------------------------TRIGGER POST POPOLAMENTO-------------------------------------------------


-------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE PRENOTAZIONE SU UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_booking_an_aboutToDepart_or_more_flight()
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

CREATE OR REPLACE TRIGGER block_booking_an_aboutToDepart_or_more_flight
BEFORE INSERT ON BOOKING
FOR EACH ROW
EXECUTE FUNCTION fun_block_booking_an_aboutToDepart_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE PASSEGGERO SU UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_inserting_a_passenger_on_an_aboutToDepart_or_more_flight()
RETURNS TRIGGER
AS $$
DECLARE

	associated_flight FLIGHT%ROWTYPE := (SELECT * FROM FLIGHT
				             WHERE id_flight = NEW.id_flight);

BEGIN
				
	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo %L è in partenza oppure è già partito!', NEW.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_inserting_a_passenger_on_an_aboutToDepart_or_more_flight
BEFORE INSERT ON PASSENGER
FOR EACH ROW
EXECUTE FUNCTION fun_block_inserting_a_passenger_on_an_aboutToDepart_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------

--TRIGGER BLOCCARE L'INSERIMENTO DI QUALUNQUE BAGAGLIO PER UN PASSEGGERO DI UN VOLO IL CUI FLIGHT_STATUS SIA aboutToDepart, DEPARTED, aboutToArrive O LANDED

CREATE OR REPLACE FUNCTION fun_block_inserting_a_luggage_for_a_passenger_on_an_aboutToDepart_or_more_flight()
RETURNS TRIGGER
AS $$
DECLARE
	
	associated_passenger PASSENGER%ROWTYPE := (SELECT * FROM PASSENGER P
					          WHERE P.ticket_number = NEW.id_passenger);

	associated_flight FLIGHT%ROWTYPE;

BEGIN
	
	associated_flight := (SELECT * FROM FLIGHT F
			      WHERE F.id_flight = associated_flight.id_flight);
			
	IF associated_flight.flight_status = 'aboutToDepart' OR associated_flight.flight_status = 'departed' OR associated_flight.flight_status = 'aboutToArrive' OR associated_flight.flight_status = 'landed' THEN

		RAISE EXCEPTION 'Il volo %L è in partenza oppure è già partito, il bagaglio nn può essere inserito!', associated_flght.id_flight;

	END IF;

	RETURN NEW;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER block_inserting_a_luggage_for_a_passenger_on_an_aboutToDepart_or_more_flight
BEFORE INSERT ON LUGGAGE
FOR EACH ROW
EXECUTE FUNCTION fun_block_inserting_a_luggage_for_a_passenger_on_an_aboutToDepart_or_more_flight();

-------------------------------------------------------------------------------------------------------------------------









