package model;
import java.util.ArrayList;
import java.util.Scanner;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    -attenzione alle return messe solo per evitare errori

 */

public class Customer extends User{

    private ArrayList<Booking> bookings;

    public Customer(String par_username, char[] par_password){
        super(par_username, par_password);
        this.bookings = new ArrayList<Booking>(0);
    }

    private void book_flight(){};
    //private Booking search_booking(){};   commentata per evitare errori di compilazione (dovrebbe avere una return)

    private void update_boooking( Booking to_modify){

        Scanner scanner=new Scanner(System.in);

        int op = 0;

        do{
            System.out.println("Cosa si desidera modificare?");
            System.out.println("1)Cambiare data volo");
            System.out.println("2)Cambia bagaglio di un passegero");
            System.out.println("3)Aggiungere passegero");
            System.out.println("4)Rimuovere passegero");
            System.out.println("5)Modifica dati passegero");
            System.out.println("6)Nulla");

            op = scanner.nextInt();

            switch(op){
                case 1:
                    //change_flight();      sarà implementata poi
                    break;
                case 2:
                    //update_passenger_luggage();   sarà implementata poi
                    break;
                case 3:
                    System.out.println("Inserisci nome passegero: ");
                    String tmp_name = scanner.nextLine();
                    System.out.println("Inserisci cognome passegero: ");
                    String tmp_lastname = scanner.nextLine();
                    System.out.println("Inserisci codife fiscale passegero: ");
                    String tmp_SSN = scanner.nextLine();

                    to_modify.add_passenger( new Passenger(tmp_name, tmp_lastname, tmp_SSN, 1/*choose_seat()*/) );
                    //choose_seat();        sarà implementata poi
                    break;
                case 4:
                    Passenger to_remove; //=search_passenger();   sarà implementata poi
                    //to_modify.remove_passenger(to_remove);
                    break;
                case 5:
                    Passenger p_to_modify; //=search_passenger();   sarà implementata poi
                    //cambiamenti ai dati del passegero
                case 6:
                    System.out.println("Arrivederci!");
                default:
                    System.out.println("Numero inserito non valido!");
            }
        }while(op != 6);


    };
    //private BookingStatus check_booking_status(){};
    /*
    private Passenger search_passenger(){};
    private Luggage search_luggage(){};
     */
    private void report_missing_luggage(){};

    public int add_booking(Booking par_booking){
        this.bookings.add(par_booking);
        return 0;
    }

    public int remove_booking(Booking par_booking){
        boolean control = this.bookings.remove(par_booking);
        if(control){
            return 0;
        }else{
            return -1;
        }
    }

}
