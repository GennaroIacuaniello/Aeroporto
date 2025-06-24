package gui;

import controller.Controller;
import model.Arriving;
import model.Departing;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchFlightResult {

    private JFrame main_frame;
    private JPanel result_panel;
    private ArrayList<Flight> search_result;
    private int current_page = 0;
    private int total_pages;
    private int flights_per_pages = 3;

    private JButton prev_button;
    private JButton next_button;
    private ArrayList<JLabel> headers;
    private ArrayList<JLabel> current_flights_shown;
    private ArrayList<JButton> booking_buttons;

    Constraints constraints;


    public SearchFlightResult(ArrayList<JFrame> callingFrames, Controller controller, ArrayList<Flight> searched_flights) {

        this.search_result = new ArrayList<Flight>(searched_flights);

        constraints = new Constraints();

        this.setMainFrame(callingFrames);

        result_panel = new JPanel();

        result_panel.setLayout(new GridBagLayout());

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH,0, 0, GridBagConstraints.FIRST_LINE_START);

        main_frame.add(result_panel, constraints.getConstraints());
        result_panel.setVisible(true);

        this.add_headers();

        if (this.search_result.size() == 0) {
            this.total_pages = 0;
        } else {
            if(search_result.size() <= flights_per_pages ){
                this.total_pages = 1;
            }else{
                this.total_pages = (int) Math.ceil( (double)search_result.size() / flights_per_pages );
            }

        }

        booking_buttons = new ArrayList<JButton>(flights_per_pages);

        this.add_flights_on_page_i(0, search_result, total_pages, flights_per_pages, callingFrames, controller);

        this.set_prev_next_button(callingFrames, controller, search_result);

        main_frame.setVisible(true);

        

    }

    private void setMainFrame(ArrayList<JFrame> callingFrames){

        main_frame = new JFrame("Risultati ricerca");
        callingFrames.addLast(main_frame);
        main_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        main_frame.setLayout(new GridBagLayout());
        main_frame.setSize(640, 480);
        main_frame.setBackground(Color.BLACK);
    }


    private void add_headers(){

        headers = new ArrayList<JLabel>(0);

        ArrayList<String> headers_strings = new ArrayList<String>(0);

        headers_strings.add("Compagnia");
        headers_strings.add("Da->A");
        headers_strings.add("Data");
        headers_strings.add("Orario partenza");
        headers_strings.add("Ritardo");
        headers_strings.add("Orario arrivo");
        headers_strings.add("Durata volo");
        headers_strings.add("Stato volo");
        headers_strings.add("Disponibilità posti");

        for(int i = 0; i < headers_strings.size(); i++){

            headers.add(i, new JLabel(headers_strings.get(i)));
            constraints.setConstraints (i, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);//, 0.0f, 0.0f, new Insets(10,5,5,2));
            result_panel.add(headers.get(i), constraints.getConstraints());
            headers.get(i).setVisible (true);
        }


    }

    private void add_flights_on_page_i(int x, ArrayList<Flight> searched_results, int par_total_pages, int par_flights_per_pages, ArrayList<JFrame> callingFrames, Controller controller){


        if(par_total_pages > 0){
            current_flights_shown = new ArrayList<JLabel>(0);
            for(int i = 0; i < par_flights_per_pages && ((x * par_flights_per_pages + i) < searched_results.size() ) ; i++){

                ArrayList<String> current_flight_strings = new ArrayList<String>(0);

                current_flight_strings.add(searched_results.get(x*par_flights_per_pages + i).get_company_name());
                if(searched_results.get(x*par_flights_per_pages + i) instanceof Arriving){
                    current_flight_strings.add( (((Arriving) searched_results.get(x*par_flights_per_pages + i)).get_origin() + " -> Napoli"));
                }else{
                    current_flight_strings.add( "Napoli -> " + (((Departing) searched_results.get(x*par_flights_per_pages + i)).get_destination()));

                }

                current_flight_strings.add(searched_results.get(x*par_flights_per_pages + i).get_date().getDay() + " / " + searched_results.get(x*par_flights_per_pages + i).get_date().getMonth() );


                current_flight_strings.add(searched_results.get(x*par_flights_per_pages + i).get_departure_time());

                if(searched_results.get(x*par_flights_per_pages + i) instanceof Arriving){
                    current_flight_strings.add( String.valueOf((Integer)(((Arriving) searched_results.get(x*par_flights_per_pages + i)).get_arrival_delay())) );
                }else{
                    current_flight_strings.add( String.valueOf((Integer)(((Departing) searched_results.get(x*par_flights_per_pages + i)).get_departure_delay())) );

                }

                current_flight_strings.add(searched_results.get(x*par_flights_per_pages + i).get_arrival_time());


                current_flight_strings.add("TO DO");
                current_flight_strings.add(searched_results.get(x*par_flights_per_pages + i).get_status().toString());
                current_flight_strings.add(String.valueOf(searched_results.get(x*par_flights_per_pages + i).get_free_seats()) + " / " + String.valueOf(searched_results.get(x*par_flights_per_pages + i).get_max_seats()));

                 for(int j = 0; j < 9; j++){
                     current_flights_shown.add(j, new JLabel(current_flight_strings.get(j)));
                     constraints.setConstraints (j, x + 1 + i, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);//, 0.0f, 0.0f, new Insets(10,5,5,2));
                     result_panel.add(current_flights_shown.get(j), constraints.getConstraints());
                     current_flights_shown.get(j).setVisible (true);
                 }
                 this.add_booking_button(9, x + 1 + i, callingFrames, controller/*, searched_results.get(x*par_flights_per_pages + i)*/);



            }

        }else{
            JLabel no_results = new JLabel("Nessun risultato");
            constraints.setConstraints (0, 4, 10, 3, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);//, 0.0f, 0.0f, new Insets(10,5,5,2));
            result_panel.add(no_results, constraints.getConstraints());
            no_results.setVisible (true);
        }


    }

    private void set_prev_next_button(ArrayList<JFrame> callingFrames, Controller controller, ArrayList<Flight> searched_results){

        prev_button = new JButton("Precedente");
        //search_arriving_button.setLayout(new GridBagLayout());
        prev_button.setEnabled(false);
        prev_button.setVisible(true);
        prev_button.setFocusable(false);
        //search_arriving_button.setFocusable(false);

        prev_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               if(current_page > 0){

                   add_flights_on_page_i(current_page-1, searched_results, total_pages,flights_per_pages, callingFrames, controller);
                   if(current_page - 1 == 0){
                       prev_button.setEnabled(false);
                   }
                   next_button.setEnabled(true);
                   current_page--;
               }
            }
        });

        constraints.setConstraints (5, flights_per_pages + 2, 3, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(5,5,5,5));


        result_panel.add(prev_button, constraints.getConstraints());

        next_button = new JButton("Successivo");
        //search_arriving_button.setLayout(new GridBagLayout());
        if(total_pages > 1){
            next_button.setEnabled(true);
        }else{
            next_button.setEnabled(false);
        }
        next_button.setVisible(true);
        next_button.setFocusable(false);
        //search_arriving_button.setFocusable(false);

        next_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(current_page + 1 < total_pages){

                    add_flights_on_page_i(current_page+1, searched_results, total_pages,flights_per_pages, callingFrames, controller);
                    if(current_page + 2 == total_pages ){
                        next_button.setEnabled(false);
                    }
                    current_page++;
                    prev_button.setEnabled(true);
                }
            }
        });

        constraints.setConstraints (8, flights_per_pages + 2, 3, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(5,5,5,5));


        result_panel.add(next_button, constraints.getConstraints());

    }

    private void add_booking_button(int x, int y, ArrayList<JFrame> callingFrames, Controller controller){

        booking_buttons.add(y-1, new JButton( "Prenota"));

        //search_arriving_button.setLayout(new GridBagLayout());
        booking_buttons.get(y-1).setEnabled(true);
        booking_buttons.get(y-1).setVisible(true);
        booking_buttons.get(y-1).setFocusable(false);
        //search_departing_button.setEnabled(false);
        //search_from.setText("Napoli");
        //search_from.setEnabled(false);
        //search_arriving_button.setFocusable(false);

        booking_buttons.get(y-1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callingFrames.get(callingFrames.size() - 2).setVisible(false);
                //callingFrames.get(callingFrames.size() - 2).dispose();
                callingFrames.get(callingFrames.size() - 1).setVisible(false);
                callingFrames.getLast().dispose();
                callingFrames.removeLast();
                //callingFrames.removeLast();
                new Book(callingFrames, controller);

            }
        });

        constraints.setConstraints (x, y, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);//, 0.0f, 0.0f, new Insets(5,5,5,5));


        result_panel.add(booking_buttons.get(y-1), constraints.getConstraints());

    }

}



//for(int i = 0; i < total_pages; i++){
  //      for(int j = 0; j < par_flights_per_pages && ((i * par_flights_per_pages + j) < searched_results.size() ) ; j++){



    //    }
      //  }
