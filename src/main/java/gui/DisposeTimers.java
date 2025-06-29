package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisposeTimers {

    private Timer decreaseOpacityTimer;
    private Timer startDecreaseOpacityTimer;
    private Timer disposeTimer;

    public DisposeTimers(JWindow window) {

        decreaseOpacityTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                window.setOpacity(window.getOpacity()-0.01f);
            }
        });
        decreaseOpacityTimer.setRepeats(true);

        startDecreaseOpacityTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ((Timer) e.getSource()).stop();
                decreaseOpacityTimer.start();
            }
        });
        startDecreaseOpacityTimer.setRepeats(false);

        disposeTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
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
