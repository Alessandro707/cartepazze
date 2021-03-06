package sample.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Main;
import sample.Net;
import sample.Settings;


public class Register extends Group {
	public final Text text = new Text("REGISTER");
	public final TextField name = new TextField();
	public final TextField password = new TextField();
	public final Button register = new Button("register");
	public final Button rememberB = new Button();
	public final Text rememberT = new Text();
	
	boolean remembered;
	
	public Register(){
		this.text.setFont(new Font(30));
		this.text.setTranslateY(50 * (float)Main.HEIGHT / 700);
		this.text.setTranslateX((float)Main.WIDTH / 2 - 70 * (float)Main.WIDTH / 1300);
		
		this.name.setPromptText("Name");
		this.name.setFont(new Font(25));
		this.name.setMaxSize(400, 70);
		this.name.setMinSize(400, 70);
		this.name.setTranslateY(80 * (float)Main.HEIGHT / 700);
		this.name.setTranslateX((float)Main.WIDTH / 2 - 200 * (float)Main.WIDTH / 1300);
		
		this.password.setPromptText("Password");
		this.password.setFont(new Font(25));
		this.password.setMaxSize(400, 70);
		this.password.setMinSize(400, 70);
		this.password.setTranslateY(180 * (float)Main.HEIGHT / 700);
		this.password.setTranslateX((float)Main.WIDTH / 2 - 200 * (float)Main.WIDTH / 1300);
		
		this.remembered = Settings.getSetting(Settings.SettingOption.REMEMBER_USER).equals("1");
		this.rememberT.setText("Remember: " + ((this.remembered) ? "on" : "off"));
		this.rememberT.setFont(new Font(20));
		this.rememberT.setTranslateY(300 * (float)Main.HEIGHT / 700);
		this.rememberT.setTranslateX((float)Main.WIDTH / 2 - 200 * (float)Main.WIDTH / 1300);
		
		this.rememberB.setMaxSize(25, 25);
		this.rememberB.setMinSize(25, 25);
		this.rememberB.setTranslateY(280 * (float)Main.HEIGHT / 700);
		this.rememberB.setTranslateX((float)Main.WIDTH / 2 - 60 * (float)Main.WIDTH / 1300);
		
		this.register.setFont(new Font(20));
		this.register.setMaxSize(150, 50);
		this.register.setMinSize(150, 50);
		this.register.setTranslateY(270 * (float)Main.HEIGHT / 700);
		this.register.setTranslateX((float)Main.WIDTH / 2 + 50 * (float)Main.WIDTH / 1300);
		
		
		this.rememberB.setOnAction(this::remember);
		this.register.setOnAction(this::register);
		
		this.getChildren().addAll(text, name, password, rememberB, rememberT, register);
	}
	
	private void remember(ActionEvent e){
		this.remembered = !this.remembered;
		this.rememberT.setText("Remember: " + ((this.remembered) ? "on" : "off"));
		Settings.setSetting(Settings.SettingOption.REMEMBER_USER, (this.remembered) ? "1" : "0");
	}
	
	private void register(ActionEvent e){
		if(this.name.getText().length() > 16 || this.name.getText().length() == 0){
			this.name.setText("");
			this.name.setPromptText("Invalid name: too long / short");
			return;
		}
		if(this.password.getText().length() > 16 || this.password.getText().length() == 0){
			this.password.setText("");
			this.password.setPromptText("Invalid password: too long / short");
			return;
		}
		Main.player.setName(this.name.getText());
		Main.player.setPassword(this.password.getText());
		
		Net.createAccount(Main.player);
		Main.player.createAccount();
		
		Main.stage.setScene(new Scene(new Home(), Main.WIDTH, Main.HEIGHT));
	}
}