package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Specialized JPanel component with rounded corners and customizable styling for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide enhanced visual appearance with rounded corner rendering,
 * customizable background colors, and configurable border styling. The RoundedPanel serves as a foundational
 * UI component throughout the airport management system, particularly integrated with the {@link FloatingMessage}
 * system to provide modern, visually appealing interface elements with professional rounded corner aesthetics.
 * </p>
 * <p>
 * The RoundedPanel class provides comprehensive visual enhancement functionality including:
 * </p>
 * <ul>
 *   <li><strong>Rounded Corner Rendering:</strong> Professional 30-pixel corner radius with anti-aliasing for smooth visual appearance</li>
 *   <li><strong>Custom Background Colors:</strong> Independent background color management with immediate visual updates</li>
 *   <li><strong>Configurable Border Styling:</strong> Customizable border colors with automatic repainting for dynamic styling</li>
 *   <li><strong>Layout Manager Integration:</strong> Full compatibility with standard Swing layout managers</li>
 *   <li><strong>Transparent Background Support:</strong> Opaque false configuration for optimal integration with parent containers</li>
 *   <li><strong>High-Quality Rendering:</strong> Graphics2D anti-aliasing for professional visual presentation</li>
 * </ul>
 * <p>
 * The component is designed with modern UI principles, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Professional Appearance:</strong> Rounded corners create modern, polished interface elements</li>
 *   <li><strong>Visual Consistency:</strong> Standardized corner radius and styling across all usage contexts</li>
 *   <li><strong>Enhanced Readability:</strong> Smooth edges reduce visual noise and improve content focus</li>
 *   <li><strong>Flexible Styling:</strong> Dynamic color changes support various message types and interface states</li>
 *   <li><strong>Seamless Integration:</strong> Transparent background enables proper layering with parent components</li>
 * </ul>
 * <p>
 * Rendering architecture utilizes advanced Graphics2D capabilities to provide high-quality visual output
 * with anti-aliasing enabled for smooth curve rendering. The rendering system includes precise coordinate
 * calculations for optimal corner radius application and consistent visual appearance across different
 * screen resolutions and display configurations.
 * </p>
 * <p>
 * Color management provides independent control over background and border colors through dedicated
 * field storage and override methods. The color system ensures immediate visual updates through
 * automatic repainting when colors are modified, maintaining real-time visual feedback for
 * dynamic interface elements and message type indicators.
 * </p>
 * <p>
 * Layout integration maintains full compatibility with standard Swing layout managers including
 * BorderLayout, GridBagLayout, and FlowLayout. The component accepts layout managers through
 * constructor parameters and properly delegates layout responsibilities to the parent JPanel
 * implementation while providing enhanced visual rendering.
 * </p>
 * <p>
 * FloatingMessage integration provides the visual foundation for message notifications throughout
 * the airport management system. The rounded panel serves as the container for message content,
 * providing type-specific styling with customizable background and border colors that distinguish
 * different message categories including information, warnings, and error notifications.
 * </p>
 * <p>
 * Performance optimization includes efficient painting algorithms that minimize computational
 * overhead during rendering operations. The painting system utilizes cached color values and
 * optimized Graphics2D operations to ensure smooth visual performance during interface updates
 * and dynamic color changes throughout message display workflows.
 * </p>
 * <p>
 * Visual design follows modern interface design principles with consistent corner radius
 * application and professional color management. The 30-pixel corner radius provides optimal
 * visual balance between subtle styling and clear geometric definition, ensuring professional
 * appearance without excessive visual distraction.
 * </p>
 * <p>
 * Integration architecture enables seamless use across multiple interface contexts including
 * message displays, dialog panels, and specialized interface components. The component maintains
 * design consistency while providing flexible styling options for different usage scenarios
 * throughout the airport management system.
 * </p>
 * <p>
 * The class follows standard Swing component patterns with proper component lifecycle management,
 * event handling integration, and parent class method overrides. The implementation maintains
 * compatibility with existing Swing applications while providing enhanced visual capabilities
 * for modern interface requirements.
 * </p>
 * <p>
 * Resource management includes efficient color object handling and optimized repainting logic
 * that minimizes system resource usage during extended application sessions. The component
 * ensures optimal performance without compromising visual quality or interface responsiveness
 * throughout airport management system operations.
 * </p>
 * <p>
 * The RoundedPanel serves as a critical visual enhancement component throughout the airport
 * management system, providing consistent rounded corner styling that elevates the overall
 * interface aesthetic while maintaining full functional compatibility with standard Swing
 * component architecture and layout management systems.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see FloatingMessage
 * @see Graphics2D
 * @see LayoutManager
 * @see Color
 */
public class RoundedPanel extends JPanel {

    /**
     * Custom background color for independent color management and visual styling.
     * <p>
     * This field maintains the panel's background color independently from the parent
     * JPanel background color system, enabling precise color control for rounded
     * corner rendering. The background color is used during custom painting operations
     * to fill the rounded rectangle area with the desired visual appearance.
     * </p>
     */
    private Color backgroundColor;
    
    /**
     * Custom border color for rounded corner outline styling and visual definition.
     * <p>
     * This field stores the color used for drawing the rounded border outline around
     * the panel perimeter. The border color provides visual definition and separation
     * from parent containers while maintaining professional appearance through
     * coordinated color schemes and type-specific styling throughout the system.
     * </p>
     */
    private Color roundBorderColor;

    /**
     * Constructs a new RoundedPanel with specified layout manager and enhanced visual styling.
     * <p>
     * This constructor initializes the rounded panel component with comprehensive visual
     * enhancements including rounded corner rendering, custom color management, and
     * transparent background configuration. The constructor establishes the foundation
     * for professional visual presentation while maintaining full compatibility with
     * standard Swing layout management systems.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Parent Initialization:</strong> Standard JPanel construction with specified layout manager</li>
     *   <li><strong>Transparency Configuration:</strong> Opaque false setting for optimal parent container integration</li>
     *   <li><strong>Background Color Setup:</strong> Initial background color capture for independent color management</li>
     *   <li><strong>Border Color Initialization:</strong> Default border color setup based on foreground color</li>
     *   <li><strong>Visual Preparation:</strong> Component preparation for custom rounded corner rendering</li>
     * </ul>
     * <p>
     * Parent initialization delegates to the superclass JPanel constructor with the
     * provided layout manager, ensuring proper layout management functionality while
     * preparing the component for enhanced visual rendering capabilities and
     * custom painting operations.
     * </p>
     * <p>
     * Transparency configuration sets the component as non-opaque (opaque = false),
     * enabling proper visual integration with parent containers and supporting
     * layered visual effects. The transparency setting ensures that rounded corners
     * blend naturally with parent component backgrounds.
     * </p>
     * <p>
     * Background color setup captures the initial background color from the parent
     * JPanel system and stores it in the custom backgroundColor field for independent
     * color management. This enables precise color control during custom painting
     * operations while maintaining compatibility with standard Swing color systems.
     * </p>
     * <p>
     * Border color initialization establishes the default border color based on the
     * component's foreground color, providing immediate visual definition while
     * enabling later customization through the setRoundBorderColor method for
     * type-specific styling and dynamic color management.
     * </p>
     * <p>
     * Visual preparation establishes the component foundation for custom rounded
     * corner rendering, ensuring that all necessary color and configuration elements
     * are properly initialized for immediate visual presentation upon component
     * display and integration within parent container layouts.
     * </p>
     * <p>
     * The constructor provides immediate readiness for integration within various
     * interface contexts including FloatingMessage displays, dialog panels, and
     * specialized interface components throughout the airport management system
     * while maintaining full layout manager compatibility.
     * </p>
     *
     * @param layout the LayoutManager to be used for organizing child components within the rounded panel
     */
    public RoundedPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        this.backgroundColor = getBackground();
        this.roundBorderColor = getForeground();
    }

    /**
     * Performs custom painting with rounded corners and anti-aliased rendering for professional visual appearance.
     * <p>
     * This method overrides the standard JPanel painting behavior to provide enhanced visual
     * rendering with rounded corners, custom color application, and high-quality anti-aliasing.
     * The painting implementation creates professional-looking interface elements with smooth
     * curved edges and precise color control for optimal visual presentation throughout
     * the airport management system interface.
     * </p>
     * <p>
     * The painting process includes:
     * </p>
     * <ul>
     *   <li><strong>Corner Radius Configuration:</strong> 30-pixel radius establishment for consistent rounded corner appearance</li>
     *   <li><strong>Dimension Calculation:</strong> Component width and height determination for precise rendering boundaries</li>
     *   <li><strong>Graphics Enhancement:</strong> Graphics2D casting and anti-aliasing activation for smooth visual output</li>
     *   <li><strong>Background Rendering:</strong> Filled rounded rectangle creation with custom background color</li>
     *   <li><strong>Border Drawing:</strong> Outlined rounded rectangle creation with custom border color</li>
     * </ul>
     * <p>
     * Corner radius configuration establishes a consistent 30-pixel radius for all rounded
     * corners, providing optimal visual balance between subtle styling and clear geometric
     * definition. The radius creates professional appearance without excessive visual
     * distraction while maintaining clear component boundaries.
     * </p>
     * <p>
     * Dimension calculation determines the current component width and height for precise
     * painting boundary establishment. The dimensions ensure accurate rounded rectangle
     * rendering that properly fills the component area while maintaining consistent
     * corner radius application across different component sizes.
     * </p>
     * <p>
     * Graphics enhancement includes Graphics2D casting for advanced rendering capabilities
     * and anti-aliasing activation (RenderingHints.VALUE_ANTIALIAS_ON) to ensure smooth
     * curve rendering. The anti-aliasing provides professional visual quality with
     * smooth edges that enhance overall interface appearance.
     * </p>
     * <p>
     * Background rendering fills the rounded rectangle area with the custom backgroundColor,
     * providing complete visual coverage within the rounded boundaries. The background
     * rendering uses precise coordinate calculations (width-1, height-1) to ensure
     * proper edge alignment and visual consistency.
     * </p>
     * <p>
     * Border drawing creates the rounded rectangle outline using the custom roundBorderColor,
     * providing visual definition and separation from parent containers. The border
     * drawing maintains the same corner radius as the background for consistent
     * visual integration and professional appearance.
     * </p>
     * <p>
     * The painting method ensures consistent visual appearance across different display
     * configurations and screen resolutions while maintaining optimal performance through
     * efficient Graphics2D operations and minimal computational overhead during
     * rendering operations throughout interface updates.
     * </p>
     *
     * @param g the Graphics context for painting operations and visual rendering
     */
    @Override
    protected void paintComponent(Graphics g) {

        int cornerRadius = 30;
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);

        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        graphics.setColor(roundBorderColor);
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }

    /**
     * Sets the rounded border color with immediate visual update and automatic repainting.
     * <p>
     * This method updates the color used for drawing the rounded border outline around
     * the panel perimeter, providing dynamic styling capabilities for different message
     * types and interface states. The method includes automatic repainting to ensure
     * immediate visual feedback when border colors are modified throughout interface
     * operations and message display workflows.
     * </p>
     * <p>
     * Border color modification includes:
     * </p>
     * <ul>
     *   <li><strong>Color Storage:</strong> Updates the internal roundBorderColor field with the new color value</li>
     *   <li><strong>Immediate Update:</strong> Triggers automatic component repainting for instant visual changes</li>
     *   <li><strong>Visual Consistency:</strong> Ensures consistent border appearance across all rounded corners</li>
     *   <li><strong>Type-Specific Styling:</strong> Enables dynamic color changes for different message categories</li>
     * </ul>
     * <p>
     * Color storage updates the internal roundBorderColor field with the provided color
     * value, ensuring that subsequent painting operations use the new border color.
     * The color storage maintains color consistency throughout component lifecycle
     * and interface state changes.
     * </p>
     * <p>
     * Immediate update functionality includes automatic repaint() invocation to trigger
     * component repainting with the new border color. The immediate update ensures
     * that color changes are visually reflected without requiring external repainting
     * requests or interface refresh operations.
     * </p>
     * <p>
     * Visual consistency ensures that the new border color is applied uniformly across
     * all rounded corners and border segments, maintaining professional appearance
     * and consistent visual integration with the custom background color and
     * overall component styling throughout the interface.
     * </p>
     * <p>
     * Type-specific styling enables dynamic border color changes for different message
     * categories in the FloatingMessage system, allowing information messages, warnings,
     * and error notifications to display distinct visual styling while maintaining
     * consistent rounded corner appearance and professional interface design.
     * </p>
     *
     * @param color the new Color to be used for the rounded border outline and visual definition
     */
    public void setRoundBorderColor(Color color) {
        this.roundBorderColor = color;
        repaint();
    }

    /**
     * Sets the background color with immediate visual update and parent class coordination.
     * <p>
     * This method overrides the standard JPanel setBackground behavior to provide enhanced
     * background color management with independent color storage and immediate visual updates.
     * The method maintains coordination with the parent JPanel color system while enabling
     * precise color control for rounded corner rendering and custom painting operations
     * throughout the airport management system interface.
     * </p>
     * <p>
     * Background color management includes:
     * </p>
     * <ul>
     *   <li><strong>Custom Color Storage:</strong> Updates the internal backgroundColor field for independent color management</li>
     *   <li><strong>Parent Coordination:</strong> Delegates to parent JPanel setBackground for system compatibility</li>
     *   <li><strong>Immediate Update:</strong> Triggers automatic component repainting for instant visual changes</li>
     *   <li><strong>Rendering Integration:</strong> Ensures custom painting operations use the updated background color</li>
     * </ul>
     * <p>
     * Custom color storage updates the internal backgroundColor field with the provided
     * color value, ensuring that custom painting operations use the new background color
     * for rounded rectangle filling. The custom storage enables precise color control
     * independent from parent component color management systems.
     * </p>
     * <p>
     * Parent coordination includes calling the parent JPanel setBackground method to
     * maintain compatibility with standard Swing color management systems. The parent
     * coordination ensures that the component integrates properly with layout managers
     * and parent container color schemes while providing enhanced visual capabilities.
     * </p>
     * <p>
     * Immediate update functionality includes automatic repaint() invocation to trigger
     * component repainting with the new background color. The immediate update ensures
     * that color changes are visually reflected without requiring external repainting
     * requests or interface refresh operations throughout dynamic styling scenarios.
     * </p>
     * <p>
     * Rendering integration ensures that the paintComponent method uses the updated
     * backgroundColor field during rounded rectangle filling operations. The rendering
     * integration provides consistent color application across all visual elements
     * and maintains professional appearance throughout color change operations.
     * </p>
     * <p>
     * The method supports dynamic background color changes for different message types
     * in the FloatingMessage system and other interface components, enabling type-specific
     * visual styling while maintaining consistent rounded corner appearance and
     * professional interface design throughout the airport management system.
     * </p>
     *
     * @param bg the new Color to be used for the background filling and visual appearance
     */
    @Override
    public void setBackground(Color bg) {
        this.backgroundColor = bg;
        super.setBackground(bg);
        repaint();
    }

    /**
     * Returns the current custom background color for color coordination and interface consistency.
     * <p>
     * This method overrides the standard JPanel getBackground behavior to return the custom
     * backgroundColor field value rather than the parent component background color. The method
     * provides access to the independently managed background color used during custom painting
     * operations, enabling color coordination and consistency checking throughout interface
     * components and styling management systems.
     * </p>
     * <p>
     * Background color access includes:
     * </p>
     * <ul>
     *   <li><strong>Independent Color Return:</strong> Returns the custom backgroundColor field rather than parent color</li>
     *   <li><strong>Consistency Support:</strong> Enables color coordination across multiple interface components</li>
     *   <li><strong>Rendering Coordination:</strong> Provides access to the actual color used during painting operations</li>
     *   <li><strong>Interface Integration:</strong> Supports color-based logic and conditional styling throughout the system</li>
     * </ul>
     * <p>
     * Independent color return ensures that the method returns the actual color used
     * during custom painting operations rather than the standard JPanel background color.
     * The independent return enables precise color tracking and management throughout
     * dynamic styling operations and interface state changes.
     * </p>
     * <p>
     * Consistency support enables color coordination across multiple RoundedPanel instances
     * and related interface components. The color access supports consistent visual
     * theming and coordinated color schemes throughout the airport management system
     * interface while maintaining individual component color independence.
     * </p>
     * <p>
     * Rendering coordination provides access to the exact color used during paintComponent
     * operations, enabling external components to coordinate their styling with the
     * rounded panel appearance. The rendering coordination supports complex interface
     * layouts with coordinated color schemes and visual consistency.
     * </p>
     * <p>
     * Interface integration supports color-based conditional logic throughout the system,
     * enabling components to adapt their behavior based on current background colors.
     * The integration enables sophisticated visual effects and coordinated styling
     * throughout dynamic interface operations and message display workflows.
     * </p>
     *
     * @return the current custom background Color used for rounded rectangle filling and visual appearance
     */
    @Override
    public Color getBackground() {
        return backgroundColor;
    }
}