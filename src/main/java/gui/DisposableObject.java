package gui;

import controller.Controller;

import javax.swing.*;
import java.util.List;

public abstract class DisposableObject {

    public void doOnDispose(List<DisposableObject> callingObjects, Controller controller) {}
    public void doOnRestore(List<DisposableObject> callingObjects, Controller controller) {}

    public abstract JFrame getFrame();
}
