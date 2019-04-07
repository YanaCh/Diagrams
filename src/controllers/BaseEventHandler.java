package controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

 abstract public class BaseEventHandler implements EventHandler<MouseEvent> {

     protected boolean active = true;


     public  void setActive(boolean active) {
         this.active = active;
     }

     public abstract void doHandle( MouseEvent event);

     @Override
     public void handle(MouseEvent event) {
         if(active)
             doHandle(event);

     }
 }
