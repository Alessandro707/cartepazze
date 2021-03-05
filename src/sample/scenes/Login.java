package sample.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Main;
import sample.Player;
import sample.Settings;

public class Login extends Group {
	public final Text text = new Text("LOGIN");
	public final TextField name = new TextField();
	public final TextField password = new TextField();
	public final Button login = new Button("login");
	public final Button rememberB = new Button();
	public final Text rememberT = new Text();
	
	boolean remembered;
	
	public Login(){
		this.text.setFont(new Font(30));
		this.text.setTranslateY(50 * (float)Main.HEIGHT / 700);
		this.text.setTranslateX((float)Main.WIDTH / 2 - 40 * (float)Main.WIDTH / 1300);
		
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
		
		this.login.setFont(new Font(20));
		this.login.setMaxSize(150, 50);
		this.login.setMinSize(150, 50);
		this.login.setTranslateY(270 * (float)Main.HEIGHT / 700);
		this.login.setTranslateX((float)Main.WIDTH / 2 + 50 * (float)Main.WIDTH / 1300);
		
		
		this.rememberB.setOnAction(this::remember);
		this.login.setOnAction(this::login);
		
		this.getChildren().addAll(text, name, password, rememberB, rememberT, login);
	}
	
	private void remember(ActionEvent e){
		this.remembered = !this.remembered;
		this.rememberT.setText("Remember: " + ((this.remembered) ? "on" : "off"));
	}
	
	private void login(ActionEvent e){
		if(this.name.getText().equals(Main.player.getName()) && this.password.getText().equals(Main.player.getPassword())) {
			Settings.setSetting(Settings.SettingOption.REMEMBER_USER, (this.remembered) ? "1" : "0");
			Main.stage.setScene(new Scene(new Home(), Main.WIDTH, Main.HEIGHT));
		}
		else{
			this.name.setText("");
			this.name.setPromptText("Wrong name");
			this.password.setText("");
			this.password.setPromptText("Wrong password");
		}
	}
}
