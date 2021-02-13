package sample.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Carta;
import sample.Main;
import sample.Player;

import java.util.Objects;

public class Home extends Group {
	private final Text titolo = new Text("CARTEPAZZE");
	private final Button play = new Button("PLAY!");
	private final HBox playerData, playerStats;
	private final Group cartaDelGiorno;
	
	public Home() {
		titolo.setTranslateY(50);
		titolo.setFont(new Font(30));
		
		play.setFont(new Font(25));
		play.setOnAction(this::play);
		
		playerData = Player.get().getGraphicsData();
		playerStats = Player.get().getGraphicsStats();
		
		cartaDelGiorno = Objects.requireNonNull(Carta.get(0)).getGraphic(Carta.Size.BIG.ratio);
		cartaDelGiorno.setTranslateX((float)Main.WIDTH / 2 - (float)Carta.WIDTH / (2 * Carta.Size.BIG.ratio));
		cartaDelGiorno.setTranslateY(80 * (float)Main.HEIGHT / 700);
		
		titolo.setTranslateX((float)Main.WIDTH / 2 - 80 * (float)Main.WIDTH / 1300);
		play.setTranslateX((float)Main.WIDTH / 2 - 50 * (float)Main.WIDTH / 1300);
		play.setTranslateY(Main.HEIGHT - 100 * (float)Main.HEIGHT / 700);
		playerStats.setTranslateX((float)Main.WIDTH - 150 * (float)Main.WIDTH / 1300);
		
		this.getChildren().addAll(titolo, play, playerData, playerStats, cartaDelGiorno);
	}
	
	// search for another player on the server looking for a match (read the first line of the db, first player queuing for a match, & remove it)
	private void play(ActionEvent e){
		System.out.println("play");
	}
}
