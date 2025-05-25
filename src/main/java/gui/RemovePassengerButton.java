package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.Controller;

public class RemovePassengerButton extends JButton {

    int index;
    Constraints constraints;

    public RemovePassengerButton(Book book, Controller controller, ArrayList<PassengerPanel> passengerPanels, ArrayList<RemovePassengerButton> removePassengerButtons, int idx, JPanel passengersPage, int currPage, JButton nextPageButton) {

        super();

        index = idx;

        this.setText("Remove Passenger");
        this.setFocusable(false);

        constraints = new Constraints();

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //crea variabili temporanee
                PassengerPanel tmppassengerPanel = passengerPanels.get(index);
                RemovePassengerButton tmpremovePassengerButton = removePassengerButtons.get(index);
                tmppassengerPanel.setVisible(false);
                tmpremovePassengerButton.setVisible(false);

                Boolean lastPage = (currPage == (passengerPanels.size() - 1) / 3);

                //rimuovi dalla lista di passeggeri e bottoni
                passengerPanels.remove(index);
                removePassengerButtons.remove(index);

                //rimuovi dalla pagina
                passengersPage.remove(tmppassengerPanel);
                passengersPage.remove(tmpremovePassengerButton);

                for (int i = index; i < passengerPanels.size(); i++) {

                    //shift passengerPanel
                    passengersPage.remove(passengerPanels.get(i));
                    constraints.setConstraints (0, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                    passengersPage.add(passengerPanels.get(i), constraints.getConstraints());

                    //shift removePassengerButton
                    passengersPage.remove(removePassengerButtons.get(i));
                    constraints.setConstraints (1, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                    passengersPage.add(removePassengerButtons.get(i), constraints.getConstraints());

                    //shift indici
                    removePassengerButtons.get(i).index--;
                }

                //visibilità su pagina
                //calcolo quanti ne ho dopo
                int nNextPassengers = passengerPanels.size() - index;

                //calcolo quanti ne devo spostare
                int nToShiftPassengers = 3 - (index % 3);

                //li metto visibili
                for (int i = 0; i < Math.min (nNextPassengers, nToShiftPassengers); i++) {

                    passengerPanels.get(index + i).setVisible(true);
                    removePassengerButtons.get(index + i).setVisible(true);
                }

                //caso devo tornare a paginaa precedente
                if (nNextPassengers == 0 && index % 3 == 0) {

                    for (int i = 3; i > 0; i--) {

                        passengerPanels.get(index - i).setVisible(true);
                        removePassengerButtons.get(index - i).setVisible(true);
                    }

                    book.decreaseCurrPage ();
                }

                //attivabilità nextPageButton
                if (lastPage == false && (currPage == (passengerPanels.size() - 1) / 3)) {  //prima non ero all'ultima pagina ora si
                    nextPageButton.setEnabled(false);
                }

                //attivabilità primo bottone
                if (removePassengerButtons.size() == 1) {
                    removePassengerButtons.get(0).setEnabled(false);
                }
            }
        });

        this.setEnabled(true);
    }
}
