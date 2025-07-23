package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import static gui.FloatingMessage.ERROR_MESSAGE;
import static gui.FloatingMessage.SUCCESS_MESSAGE;

public class AddNewFlightDialog extends JDialog {

    private final Constraints constraints; // Oggetto per la gestione dei vincoli

    private JLabel cityLabel;
    private JTextField flightIdField;
    private JTextField companyField;
    private JTextField cityField;
    private JTextField maxSeatsField;
    private DatePicker flightDatePicker;
    private TimePicker departureTimePicker;
    private TimePicker arrivalTimePicker;
    private JRadioButton arrivingRadio;
    private JRadioButton departingRadio;

    private final JButton confirmButton;

    public AddNewFlightDialog(Frame owner, Controller controller) {

        super(owner, "Aggiungi Nuovo Volo", true);
        constraints = new Constraints();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 242, 245));


        JLabel titleLabel = new JLabel("Inserisci le informazioni del volo da aggiungere");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 0, 20, 0));
        mainPanel.add(titleLabel, constraints.getGridBagConstraints());


        JPanel formPanel = createFormPanel();
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 0, 20, 0));
        mainPanel.add(formPanel, constraints.getGridBagConstraints());


        confirmButton = new JButton("Conferma");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        confirmButton.setBackground(new Color(0, 120, 215));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);

        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setPreferredSize(new Dimension(150, 40));

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAction(controller);
            }
        });

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_END, 1.0f, 0.0f, new Insets(10, 0, 0, 0));
        mainPanel.add(confirmButton, constraints.getGridBagConstraints());

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
    }

    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        Insets labelInsets = new Insets(5, 5, 5, 10);
        Insets fieldInsets = new Insets(5, 0, 5, 25);


        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("ID volo:"), constraints.getGridBagConstraints());

        flightIdField = new JTextField(15);
        setFieldAppearance(flightIdField);

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(flightIdField, constraints.getGridBagConstraints());


        constraints.setConstraints(2, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Compagnia aerea:"), constraints.getGridBagConstraints());

        companyField = new JTextField(15);
        setFieldAppearance(companyField);

        constraints.setConstraints(3, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(companyField, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Data del volo:"), constraints.getGridBagConstraints());

        flightDatePicker = new DatePicker();
        flightDatePicker.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(flightDatePicker, constraints.getGridBagConstraints());


        cityLabel = createLabel("Città di partenza:");
        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(cityLabel, constraints.getGridBagConstraints());

        cityField = new JTextField(15);
        setFieldAppearance(cityField);

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(cityField, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Orario di partenza:"), constraints.getGridBagConstraints());

        departureTimePicker = new TimePicker();
        departureTimePicker.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(departureTimePicker, constraints.getGridBagConstraints());


        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Orario di arrivo:"), constraints.getGridBagConstraints());

        arrivalTimePicker = new TimePicker();
        arrivalTimePicker.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(arrivalTimePicker, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 3, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Posti massimi:"), constraints.getGridBagConstraints());

        maxSeatsField = new JTextField(15);
        setFieldAppearance(maxSeatsField);

        constraints.setConstraints(1, 3, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(maxSeatsField, constraints.getGridBagConstraints());


        arrivingRadio = new JRadioButton("Volo per Napoli");
        arrivingRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arrivingRadio.setOpaque(false);

        departingRadio = new JRadioButton("Volo in partenza da Napoli");
        departingRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        departingRadio.setOpaque(false);

        ButtonGroup flightTypeGroup = new ButtonGroup();

        flightTypeGroup.add(arrivingRadio);
        flightTypeGroup.add(departingRadio);

        arrivingRadio.setSelected(true);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.setOpaque(false);

        radioPanel.add(arrivingRadio);
        radioPanel.add(departingRadio);

        constraints.setConstraints(2, 3, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(5, 5, 5, 5));
        panel.add(radioPanel, constraints.getGridBagConstraints());

        arrivingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrivingRadio.isSelected()) {
                    cityLabel.setText("Città di partenza:");
                } else {
                    cityLabel.setText("Destinazione:");
                }
            }
        });

        departingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrivingRadio.isSelected()) {
                    cityLabel.setText("Città di partenza:");
                } else {
                    cityLabel.setText("Destinazione:");
                }
            }
        });

        return panel;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;

    }

    private void setFieldAppearance(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    }

    private void confirmAction(Controller controller) {

        String flightId = flightIdField.getText();
        String companyName = companyField.getText();
        LocalDate flightDate = flightDatePicker.getDate();
        String otherCity = cityField.getText();
        LocalTime departureTime = departureTimePicker.getTime();
        LocalTime arrivalTime = arrivalTimePicker.getTime();
        String maxSeatsStr = maxSeatsField.getText();

        boolean flightType = departingRadio.isSelected();

        if (flightId.isEmpty() || companyName.isEmpty() || flightDate == null || otherCity.isEmpty() ||
                departureTime == null || arrivalTime == null || maxSeatsStr.isEmpty()) {

            new FloatingMessage("Tutti i campi sono obbligatori!", confirmButton, ERROR_MESSAGE);

            return;
        }

        try {
            int maxSeats = Integer.parseInt(maxSeatsStr);

            if (maxSeats <= 0) {

                new FloatingMessage("Il numero di posti massimi deve essere maggiore di zero!", confirmButton, ERROR_MESSAGE);
                return;

            }else if(flightDate.isBefore(LocalDate.now())){

                new FloatingMessage("La data deve essere successiva alla data odierna!", confirmButton, ERROR_MESSAGE);
                return;

            }else if(otherCity.equalsIgnoreCase("Napoli")){

                new FloatingMessage("La città non può essere Napoli!", confirmButton, ERROR_MESSAGE);
                return;

            }


            if(controller.getFlightController().addFlight(flightId, companyName, flightDate, departureTime, arrivalTime, maxSeats, otherCity, flightType, confirmButton)){

                new FloatingMessage("Volo aggiunto con successo!", confirmButton, SUCCESS_MESSAGE);
                dispose();

            }



        } catch (NumberFormatException e) {

            new FloatingMessage("Il numero di posti massimi non è un numero valido.", confirmButton, ERROR_MESSAGE);

        }
    }
}