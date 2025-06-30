package gui;

import controller.Controller;

import javax.swing.*;
import java.util.ArrayList;

public abstract class DisposableObject {
    abstract public void doOnDispose(ArrayList<DisposableObject> callingObjects, Controller controller);
    abstract public JFrame getFrame();
}
