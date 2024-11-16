package edu.hotelmanagment;

import edu.hotelmanagment.gui.MainMenuWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application
{
    public void start(Stage primaryStage)
    {
        new MainMenuWindow();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
