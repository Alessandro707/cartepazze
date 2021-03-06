package sample.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import sample.Main;

public class PlayerManagement extends Group {
	private static final float IMG_SIZE = Main.HEIGHT - 300 * (float)Main.HEIGHT / 700;
	
	private final TextField nome = new TextField(Main.player.getName());
	private final ImageView immagine = new ImageView(new Image(Main.player.getImmagine(), IMG_SIZE, IMG_SIZE, true, true));
	private final Button back = new Button("<----");
	
	// TODO: nome <= 16
	public PlayerManagement(){
		nome.setFont(new Font(30));
		nome.setTranslateX(20 * (float) Main.WIDTH / 700);
		nome.setTranslateY(20 * (float)Main.HEIGHT / 700);
		nome.setMaxSize(200 * (float)Main.WIDTH / 1300, 50 * (float)Main.HEIGHT / 700);
		nome.setMinSize(200 * (float)Main.WIDTH / 1300, 50 * (float)Main.HEIGHT / 700);
		
		immagine.setTranslateX((Main.WIDTH - IMG_SIZE) / 2);
		immagine.setTranslateY((Main.HEIGHT - IMG_SIZE) / 2);
		
		back.setFont(new Font(15));
		back.setMaxSize(100 * (float)Main.WIDTH / 1300, 30 * (float)Main.HEIGHT / 700);
		back.setMinSize(100 * (float)Main.WIDTH / 1300, 30 * (float)Main.HEIGHT / 700);
		back.setTranslateX(10 * (float)Main.WIDTH / 1300);
		back.setTranslateY(Main.HEIGHT - (30 + 10) * (float)Main.HEIGHT / 700);
		
		nome.setOnKeyPressed((KeyEvent e) -> {
			if(e.getCode().equals(KeyCode.ENTER)){
				Main.player.updateName(nome.getText());
			}
		});
		
		back.setOnAction((ActionEvent e) -> Main.stage.setScene(new Scene(new Home(), Main.WIDTH, Main.HEIGHT)));
		
		this.getChildren().addAll(nome, immagine, back);
	}
}
