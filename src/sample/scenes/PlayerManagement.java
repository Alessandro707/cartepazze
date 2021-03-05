package sample.scenes;

import javafx.scene.Group;
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
	
	public PlayerManagement(){
		nome.setFont(new Font(30));
		nome.setTranslateX(20 * (float) Main.WIDTH / 700);
		
		immagine.setTranslateX((Main.WIDTH - IMG_SIZE) / 2);
		immagine.setTranslateY((Main.HEIGHT - IMG_SIZE) / 2);
		
		nome.setOnKeyPressed((KeyEvent e) -> {
			if(e.getCode().equals(KeyCode.ENTER)){
				Main.player.setName(nome.getText());
			}
		});
		
		this.getChildren().addAll(nome, immagine);
	}
}
