package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.scenes.Home;
import sample.scenes.Login;
import sample.scenes.Register;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

public class Main extends Application {
	public static Stage stage;
	public static final int WIDTH = 1300, HEIGHT = 700;
	public static final int NUMBER_OF_CARDS = 1, NAME_LENGTH = 16, PW_LENGTH = 16, IMG_SIZE = 255;
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Settings.initSettings();
    	
    	stage = primaryStage;
        stage.setTitle("");
        if(new File("src/res/player/info").exists()) {
	        if(Settings.getSetting(Settings.SettingOption.REMEMBER_USER).equals("0"))
	        	stage.setScene(new Scene(new Login(), WIDTH, HEIGHT));
	        else
	        	stage.setScene(new Scene(new Home(), WIDTH, HEIGHT));
        }
        else
        	stage.setScene(new Scene(new Register(), WIDTH, HEIGHT));
        stage.show();
        
        stage.setResizable(false);
    }
    

    public static void main(String[] args) {
        launch(args);
        
        quit();
    }
    
    public static void quit(){
    	Settings.writeSettings();
    }
}
