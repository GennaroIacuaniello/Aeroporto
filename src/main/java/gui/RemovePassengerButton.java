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

    public RemovePassengerButton(BookingPage book, Controller controller, ArrayList<PassengerPanel> passengerPanels,
                                 ArrayList<RemovePassengerButton> removePassengerButtons, int idx) {

        super("Remove Passenger");

        index = idx;

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

                //rimuovi dalla lista di passeggeri e bottoni
                passengerPanels.remove(index);
                removePassengerButtons.remove(index);

                //rimuovi dalla pagina
                book.getPassengerPage().remove(tmppassengerPanel);
                book.getPassengerPage().remove(tmpremovePassengerButton);

                //rimuovi eventuali panel aggiuntivi
                if (tmppassengerPanel.getLuggagesView() != null) tmppassengerPanel.getLuggagesView().dispose();
                if (tmppassengerPanel.getSeatChooser() != null) tmppassengerPanel.getSeatChooser().dispose();

                for (int i = index; i < passengerPanels.size(); i++) {

                    //shift passengerPanel
                    book.getPassengerPage().remove(passengerPanels.get(i));
                    constraints.setConstraints(0, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                    book.getPassengerPage().add(passengerPanels.get(i), constraints.getConstraints());

                    //shift removePassengerButton
                    book.getPassengerPage().remove(removePassengerButtons.get(i));
                    constraints.setConstraints(1, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                    book.getPassengerPage().add(removePassengerButtons.get(i), constraints.getConstraints());

                    //shift indici
                    removePassengerButtons.get(i).index--;
                }

                //visibilità su pagina
                //calcolo quanti ne ho dopo
                int nNextPassengers = passengerPanels.size() - index;

                //calcolo quanti ne devo spostare
                int nToShiftPassengers = 3 - (index % 3);

                //li metto visibili
                for (int i = 0; i < Math.min(nNextPassengers, nToShiftPassengers); i++) {

                    passengerPanels.get(index + i).setVisible(true);
                    removePassengerButtons.get(index + i).setVisible(true);
                }

                //caso devo tornare a paginaa precedente
                if (nNextPassengers == 0 && index % 3 == 0) {

                    for (int i = 3; i > 0; i--) {

                        passengerPanels.get(index - i).setVisible(true);
                        removePassengerButtons.get(index - i).setVisible(true);
                    }

                    book.decreaseCurrPage();
                }

                //attivabilità nextPageButton
                if (book.getCurrPage() == ((passengerPanels.size() - 1) / 3)) {  //ora sono all'ultima pagina
                    book.getNextButton().setEnabled(false);
                }

                //attivabilità primo bottone
                if (removePassengerButtons.size() == 1) {
                    removePassengerButtons.getFirst().setEnabled(false);
                }
/*
                System.out.println("Current state:");
                System.out.println ("Current Page: " + (book.getCurrPage ()));
                System.out.println ("#PassengerPanels: " + passengerPanels.size());
                System.out.println ("#Pages: " + (((passengerPanels.size() - 1) / 3) + 1));
                System.out.println ();
 */
            }
        });

        this.setEnabled(true);
    }

    public RemovePassengerButton(Book book, Controller controller, ArrayList<PassengerPanel> passengerPanels,
                                 ArrayList<RemovePassengerButton> removePassengerButtons, int idx, JPanel passengersPage, JButton nextPageButton) {}
}
