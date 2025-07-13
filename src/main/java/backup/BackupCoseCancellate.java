package backup;

public class BackupCoseCancellate {

    /*------------------------------------------------------------------------------------------------------*/
    //Booking
    /*------------------------------------------------------------------------------------------------------*/

    /*public int add_passenger(Passenger par_passenger){

        this.passengers.add(par_passenger);

        int control = this.bookedFlight.addPassenger(par_passenger);

        if(control != 0) {
            return -1;
        }

        return -1;


    }

    public int remove_passenger(Passenger par_passenger){
        boolean control = this.passengers.remove(par_passenger);
        if(control) {
            this.bookedFlight.removePassenger(par_passenger);
            return 0;
        }
        else{
            return -1;
        }

    }*/

    /*------------------------------------------------------------------------------------------------------*/
    //Flight
    /*------------------------------------------------------------------------------------------------------*/
    /*
    public void rollBackAddBooking(int count){
        for(int i=0; i < count; i++) {
            this.tickets.removeLast();
        }
        this.bookings.removeLast();
    }

    public int addBooking(Booking parBooking){
        this.bookings.add(parBooking);
        int control = 0, count = 0;
        for(Passenger x : parBooking.get_passengers()){

            control = this.addPassenger(x);
            if(control != 0){
                rollBackAddBooking(count);
                return control;
            }
            count++;
        }
        return 0;
    }

    public int addPassenger(Passenger parPassenger){
        if(this.freeSeats > 0){
            this.freeSeats--;
            this.tickets.add(parPassenger);
            return 0;
        }

        return -1;
    }

    public int removeBooking(Booking parBooking){
        boolean control = this.bookings.remove(parBooking);
        if(control){
            for(Passenger x : parBooking.passengers){
                this.removePassenger(x);
            }
            return 0;
        }

        return -1;

    }

    public int removePassenger(Passenger parPassenger){
        boolean control = this.tickets.remove(parPassenger);
        if(control) {
            this.freeSeats++;
            return 0;
        }

        return -1;
    }*/

}
