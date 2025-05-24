package gui;

import controller.Controller;
import model.User;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class Search_panel extends JPanel{

    private JButton search_arriving_button;
    private JButton search_departing_button;
    private JButton search_button;
    private JPanel invisiblePanel;
    private Constraints constraints;

    private JTextField search_from;
    private JTextField search_to;

    private JLabel search_from_text;
    private JLabel search_to_text;

    public Search_panel(ArrayList<JFrame> callingFrames, Controller controller){

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();

        this.set_search_from_text_label();
        search_from = new JTextField();
        search_from.setVisible(true);
        search_from_text = new JLabel("DA");
        search_from_text.setVisible(true);
        search_to = new JTextField();
        search_to.setVisible(true);
        search_from_text = new JLabel("A");
        search_to_text.setVisible(true);


        this.set_search_arriving_button();
        //this.set_search_departing_button();
        //this.set_search_button();

        this.setVisible(true);
    }


    private void set_search_from_text_label ()
    {

        search_from_text = new JLabel("DA");
        search_from_text.setVisible(true);

        search_from_text.setLayout(new GridBagLayout ());

        //constraints.setConstraints (2, 0, 17, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.LINE_START);
        this.add (search_from_text);
        search_from_text.setVisible (true);
    }

    private void set_search_to_text_label ()
    {

        search_to_text = new JLabel("A");
        search_to_text.setVisible(true);

        search_to_text.setLayout(new GridBagLayout ());

        //constraints.setConstraints (2, 0, 17, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.LINE_START);
        this.add (search_to_text);
        search_to_text.setVisible (true);
    }

    private void set_search_arriving_button(){

        search_arriving_button = new JButton("Cerca voli in arrivo");
        search_arriving_button.setLayout(new GridBagLayout());
        search_arriving_button.setEnabled(false);
        search_arriving_button.setVisible(true);
        search_arriving_button.setFocusable(false);

        search_arriving_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_arriving_button.setEnabled(false);
                //search_to
            }
        });

    }


}
