package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Dispose timers.
 */
public class DisposeTimers {

    private final Timer decreaseOpacityTimer;

    /**
     * Instantiates a new Dispose timers.
     *
     * @param window the window
     */
    public DisposeTimers(JWindow window) {

        decreaseOpacityTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(window.getOpacity() > 0.01f){
                    window.setOpacity(window.getOpacity()-0.01f);
                }
                else{
                    window.setOpacity(0.0f);
                }
            }
        });
        decreaseOpacityTimer.setRepeats(true);

        Timer startDecreaseOpacityTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                decreaseOpacityTimer.start();
            }
        });
        startDecreaseOpacityTimer.setRepeats(false);

        Timer disposeTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                ((Timer) e.getSource()).stop();
                decreaseOpacityTimer.setRepeats(false);
                decreaseOpacityTimer.stop();
            }
        });

        disposeTimer.start();
        startDecreaseOpacityTimer.start();
    }
}
