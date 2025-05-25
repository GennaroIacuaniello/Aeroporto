package gui;

import model.Flight;

import javax.swing.*;
import java.util.ArrayList;

public class SearchFlightResult extends JFrame {

    private ArrayList<Flight> search_result;

    private JPanel flightsPanel;
    private JPanel headerPanel;
    private JButton prev_button;
    private JButton next_button;
    private JLabel pageInfoLabel;

    private int pages;

    public SearchFlightResult(ArrayList<Flight> searched_flights, String searchTitle) {

        super("Risultati Ricerca: " + searchTitle);
        this.search_result = new ArrayList<Flight>(searched_flights);

        if (this.search_result.size() == 0) {
            this.pages = 0;
        } else {
            if(search_result.size() <= 3 ){
                this.pages = 1;
            }else{
                this.pages = (int) Math.ceil( (double)search_result.size() / 3 );
            }

        }

        

    }

}
