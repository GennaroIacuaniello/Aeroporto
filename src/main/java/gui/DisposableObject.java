package gui;

import controller.Controller;

import javax.swing.*;
import java.util.List;

/**
 * The type Disposable object.
 */
public abstract class DisposableObject {

    /**
     * Do on dispose.
     *
     * @param callingObjects the calling objects
     * @param controller     the controller
     */
    public void doOnDispose(List<DisposableObject> callingObjects, Controller controller) {}

    /**
     * Do on restore.
     *
     * @param callingObjects the calling objects
     * @param controller     the controller
     */
    public void doOnRestore(List<DisposableObject> callingObjects, Controller controller) {}

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public abstract JFrame getFrame();
}
