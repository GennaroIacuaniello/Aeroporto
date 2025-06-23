package gui;

import controller.Controller;
import model.Customer;
import model.Flight;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class SearchPanel extends JPanel{

    private JButton search_arriving_button;
    private JButton search_departing_button;
    private JButton search_button;
    private JPanel invisiblePanel;
    private Constraints constraints;

    private JTextField search_from;
    private JTextField search_to;

    private JLabel search_from_text;
    private JLabel search_to_text;

    private JLabel date_label;
    private JLabel time_label;
    private JLabel time_separator_label;

    private JTextField date_field;
    private JTextField time_from_field;
    private JTextField time_to_field;


    public SearchPanel(ArrayList<JFrame> callingFrames, Controller controller){

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();

        this.set_search_from_text_label();
        this.set_search_from_text_field();

        this.set_search_arriving_button();
        this.set_search_departing_button();

        this.set_search_to_text_label();
        this.set_search_to_text_field();

        this.set_date_label();
        this.set_date_text_field();

        this.set_time_label();
        this.set_time_from_text_field();

        this.set_time_separator_label();
        this.set_time_to_text_field();

        this.set_search_button(callingFrames, controller);

        this.setVisible(true);
    }


    private void set_search_from_text_label()
    {

        search_from_text = new JLabel("Da:");

        //search_from_text.setLayout(new GridBagLayout ());

        constraints.setConstraints (0, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(10,5,5,2));
        this.add(search_from_text, constraints.getConstraints());
        search_from_text.setVisible (true);
    }

    private void set_search_to_text_label()
    {

        search_to_text = new JLabel("A");

        //search_to_text.setLayout(new GridBagLayout ());

        constraints.setConstraints (4, 1, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(10,5,5,2));

        this.add(search_to_text, constraints.getConstraints());
        search_to_text.setVisible (true);
    }

    private void set_search_from_text_field(){

        search_from = new JTextField(10);

        //search_from.setLayout(new GridBagLayout ());

        constraints.setConstraints (1, 1, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(10,2,5,15));
        //search_from.setSize(30,30);
        this.add(search_from, constraints.getConstraints());
        search_from.setVisible(true);
    }

    private void set_search_to_text_field(){
        search_to = new JTextField(10);

        //search_to.setLayout(new GridBagLayout ());

        constraints.setConstraints (5, 1, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(10,2,5,15));
        //search_to.setSize(30,1);
        this.add(search_to, constraints.getConstraints());
        search_to.setVisible(true);
    }

    private void set_search_arriving_button(){

        search_arriving_button = new JButton("Cerca voli in arrivo");
        //search_arriving_button.setLayout(new GridBagLayout());
        search_arriving_button.setEnabled(true);
        search_arriving_button.setVisible(true);
        search_arriving_button.setFocusable(false);
        //search_arriving_button.setFocusable(false);

        search_arriving_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_arriving_button.setEnabled(false);
                search_to.setText("Napoli");
                search_to.setEnabled(false);
                search_departing_button.setEnabled(true);
                search_from.setText("");
                search_from.setEnabled(true);
            }
        });

        constraints.setConstraints (0, 0, 4, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(5,5,5,5));


        this.add(search_arriving_button, constraints.getConstraints());
    }

    private void set_search_departing_button(){

        search_departing_button = new JButton("Cerca voli in partenza");
        //search_arriving_button.setLayout(new GridBagLayout());
        search_departing_button.setEnabled(true);
        search_departing_button.setVisible(true);
        search_departing_button.setFocusable(false);
        //search_departing_button.setEnabled(false);
        //search_from.setText("Napoli");
        //search_from.setEnabled(false);
        //search_arriving_button.setFocusable(false);

        search_departing_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_departing_button.setEnabled(false);
                search_from.setText("Napoli");
                search_from.setEnabled(false);
                search_arriving_button.setEnabled(true);
                search_to.setText("");
                search_to.setEnabled(true);
            }
        });

        constraints.setConstraints (4, 0, 3, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(5,5,5,5));


        this.add(search_departing_button, constraints.getConstraints());
    }


    private void set_date_label(){

        date_label = new JLabel("Data:");

        //search_from_text.setLayout(new GridBagLayout ());

        constraints.setConstraints (0, 2, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(10,5,5,2));
        this.add(date_label, constraints.getConstraints());
        date_label.setVisible (true);
    }

    private void set_date_text_field(){

        date_field = new JTextField(8);

        //search_from.setLayout(new GridBagLayout ());

        constraints.setConstraints (1, 2, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 0.5f, 0.0f, new Insets(10,2,5,15));
        //search_from.setSize(30,30);
        this.add(date_field, constraints.getConstraints());
        date_field.setVisible(true);
    }

    private void set_time_label(){

        time_label = new JLabel("Fascia oraria:");

        //search_from_text.setLayout(new GridBagLayout ());

        constraints.setConstraints (3, 2, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, new Insets(10,5,5,2));
        this.add(time_label, constraints.getConstraints());
        time_label.setVisible (true);
    }

    private void set_time_from_text_field(){

        time_from_field = new JTextField(5);

        //search_from.setLayout(new GridBagLayout ());

        constraints.setConstraints (4, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 1.0f, 0.0f, new Insets(10,2,5,5));
        //search_from.setSize(30,30);
        this.add(time_from_field, constraints.getConstraints());
        time_from_field.setVisible(true);
    }

    private void set_time_separator_label(){

        time_separator_label = new JLabel("-  ");

        //search_from_text.setLayout(new GridBagLayout ());

        constraints.setConstraints (5, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.CENTER, 0.0f, 0.0f, new Insets(10,5,5,0));
        this.add(time_separator_label, constraints.getConstraints());
        time_separator_label.setVisible (true);
    }

    private void set_time_to_text_field(){

        time_to_field = new JTextField(5);

        //search_from.setLayout(new GridBagLayout ());

        constraints.setConstraints (6, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(10,5,5,2));
        //search_from.setSize(30,30);
        this.add(time_to_field, constraints.getConstraints());
        time_to_field.setVisible(true);
    }

    private void set_search_button(ArrayList<JFrame> callingFrames, Controller controller){

        search_button = new JButton("Cerca");
        //search_arriving_button.setLayout(new GridBagLayout());
        search_button.setEnabled(true);
        search_button.setVisible(true);
        search_button.setFocusable(false);
        //search_arriving_button.setFocusable(false);

        search_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Flight> searched_flights = controller.search_flight_customer(search_from_text.getText(), search_to_text.getText(), date_field.getText(), time_from_field.getText(), time_to_field.getText() );

                new SearchFlightResult(callingFrames, controller, searched_flights);
            }
        });

        constraints.setConstraints (0, 3, 7, 1, GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, new Insets(5,5,5,5));


        this.add(search_button, constraints.getConstraints());

    }
}
