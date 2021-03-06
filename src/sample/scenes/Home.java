package sample.scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Carta;
import sample.Main;
import sample.Player;

import java.io.File;
import java.util.Objects;

public class Home extends Group {
	private final Text titolo = new Text("CARTEPAZZE");
	private final Button play = new Button("PLAY!");
	private final HBox playerData, playerStats;
	private final Group cartaDelGiorno;
	private final Button deck = new Button("Deck");
	
	public Home() {
		titolo.setTranslateY(50);
		titolo.setFont(new Font(30 * (float)Main.WIDTH / 1300));
		
		play.setFont(new Font(25 * (float)Main.WIDTH / 1300));
		play.setOnAction(this::play);
		
		
		playerData = new HBox();
		playerData.setSpacing(20);
		Text data = new Text(Main.player.getName() + " - " + Main.player.getSoldi() + "$");
		data.setFont(new Font(20 * (float)Main.WIDTH / 1300));
		data.setTranslateY(10 * (float)Main.HEIGHT / 700);
		ImageView imageView = new ImageView(new Image(Main.player.getImmagine(), 75 * (float)Main.WIDTH / 1300, 75 * (float)Main.WIDTH / 1300,true, true));
		playerData.getChildren().addAll(imageView, data);
		playerData.setOnMouseClicked((MouseEvent e) -> {
			Main.stage.setScene(new Scene(new PlayerManagement(), Main.WIDTH, Main.HEIGHT));
		});
		
		playerStats = new HBox();
		Text stats = new Text("wr: " + Main.player.getWr() + " - carte: " + Main.player.getCarte().size() / Main.NUMBER_OF_CARDS * 100);
		stats.setFont(new Font(20 * (float)Main.WIDTH / 1300));
		stats.setTranslateY(10 * (float)Main.HEIGHT / 700);
		playerStats.getChildren().addAll(stats);
		
		
		cartaDelGiorno = Objects.requireNonNull(Carta.get(0)).getGraphic(Carta.Size.BIG.ratio);
		cartaDelGiorno.setTranslateX((float)Main.WIDTH / 2 - (float)Carta.WIDTH / (2 * Carta.Size.BIG.ratio) * (float)Main.WIDTH / 1300);
		cartaDelGiorno.setTranslateY(80 * (float)Main.HEIGHT / 700);
		
		
		titolo.setTranslateX((float)Main.WIDTH / 2 - 80 * (float)Main.WIDTH / 1300);
		play.setTranslateX((float)Main.WIDTH / 2 - 50 * (float)Main.WIDTH / 1300);
		play.setTranslateY(Main.HEIGHT - 100 * (float)Main.HEIGHT / 700);
		playerStats.setTranslateX((float)Main.WIDTH - 150 * (float)Main.WIDTH / 1300);
		
		deck.setFont(play.getFont());
		deck.setOnAction(this::deck);
		deck.setTranslateY((float)Main.HEIGHT / 2 - 50 * (float)Main.HEIGHT / 700);
		deck.setTranslateX((float)Main.HEIGHT / 4 - 50 * (float)Main.WIDTH / 1300);
		
		this.getChildren().addAll(titolo, play, playerData, playerStats, cartaDelGiorno, deck);
	}
	
	// search for another player on the server looking for a match (read the first line of the db, first player queuing for a match, & remove it)
	private void play(ActionEvent e){
		System.out.println("play");
	} // TODO: todo: do this
	
	private void deck(ActionEvent e){
		Main.stage.setScene(new Scene(new DeckMenu(), Main.WIDTH, Main.HEIGHT));
	}
}
