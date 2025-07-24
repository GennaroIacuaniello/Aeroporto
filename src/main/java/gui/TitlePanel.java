package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Specialized title panel component providing consistent airport branding and system identification throughout the airport management system interfaces.
 * <p>
 * This class extends {@link JPanel} to provide a standardized title display component that delivers
 * consistent airport branding and system identification across all interfaces within the airport
 * management system. The TitlePanel serves as the primary branding component for interface headers,
 * offering professional HTML-formatted title presentation with centered alignment and optimal
 * visual hierarchy through an intuitive, reusable design optimized for airport management operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see GridBagLayout
 * @see Constraints
 * @see HomePageAdmin
 * @see HomePageCustomer
 * @see BookingPage
 * @see MyBookingsCustomerMainFrame
 */
public class TitlePanel extends JPanel {

    /**
     * Layout constraints utility providing standardized GridBagConstraints configuration for consistent component positioning.
     * <p>
     * This Constraints instance serves as the primary layout management utility for the title panel,
     * providing standardized GridBagConstraints configuration that ensures consistent component
     * positioning, spacing, and alignment throughout the title display presentation. The constraints
     * utility maintains visual consistency with the broader airport management system design standards
     * and supports responsive layout behavior across different interface contexts.
     * </p>
     */
    Constraints constraints;

    /**
     * Constructs a new TitlePanel with comprehensive HTML title rendering and centered alignment for professional airport branding display.
     * <p>
     * This constructor initializes the complete title panel interface by establishing layout management,
     * creating the formatted title label, and configuring visual presentation for optimal branding
     * display throughout airport management system interfaces. The constructor creates a fully
     * functional title component ready for immediate integration within parent containers with
     * professional HTML formatting and centered alignment for consistent visual hierarchy.
     * </p>
     *
     * @param title the title text for HTML rendering and branding display throughout airport management system interfaces
     */
    public TitlePanel(String title) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setTitleLabel(title);

        this.setVisible(true);
    }

    /**
     * Creates and configures the HTML-formatted title label with centered alignment and professional typography for optimal branding presentation.
     * <p>
     * This private method establishes the complete title label configuration including HTML h1 formatting,
     * horizontal and vertical center alignment, and GridBagConstraints application for optimal
     * visual presentation throughout airport management system interfaces. The method creates
     * a professional title display with browser-quality HTML rendering and precise positioning
     * for consistent branding and visual hierarchy across all system interfaces.
     * </p>
     *
     * @param title the title text for HTML h1 formatting and professional branding display throughout system interfaces
     */
    private void setTitleLabel(String title) {
        JLabel titleLabel;

        titleLabel = new JLabel("<html><h1>" + title + "</h1></html>");

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 50, GridBagConstraints.CENTER);

        this.add(titleLabel, constraints.getGridBagConstraints());
        titleLabel.setVisible(true);
    }
}