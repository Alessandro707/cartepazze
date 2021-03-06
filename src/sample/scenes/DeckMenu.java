package sample.scenes;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.Carta;
import sample.Main;
import sample.Player;

import java.util.ArrayList;

public class DeckMenu extends VBox {
	private final ArrayList<Carta> carte = new ArrayList<>();
	private final VBox unlocked = new VBox();
	private final VBox locked = new VBox();
	
	private double scrollY = 0;
	
	public DeckMenu(){
		Player player = Main.player;
		
		unlocked.setSpacing(5);
		locked.setSpacing(5);
		
		HBox rowa = new HBox();
		HBox rowb = new HBox();
		rowa.setSpacing(5);
		rowb.setSpacing(5);
		for(int i = 0; i < Main.NUMBER_OF_CARDS * 100; i++){
			char c = player.getCardsString().charAt(i);
			if(c == '1'){
				if(rowa.getChildren().size() < 9) {
					rowa.getChildren().add(Carta.get(i).getImage(Carta.Size.SMALL.ratio));
				}
				else{
					unlocked.getChildren().add(rowa);
					rowa = new HBox();
					rowa.setSpacing(5);
				}
			}
			else{
				if(rowb.getChildren().size() < 9) {
					rowb.getChildren().add(Carta.get(i).getImage(Carta.Size.SMALL.ratio));
				}
				else{
					locked.getChildren().add(rowb);
					rowb = new HBox();
					rowb.setSpacing(5);
				}
			}
		}
		if(rowa.getChildren().size() > 0)
			unlocked.getChildren().add(rowa);
		if(rowb.getChildren().size() > 0)
			locked.getChildren().add(rowb);
		
		unlocked.setBorder(new Border(new BorderStroke(Paint.valueOf("000000ff"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
		locked.setBorder(new Border(new BorderStroke(Paint.valueOf("000000ff"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
		
		this.setSpacing(50);
		this.getChildren().addAll(unlocked, locked);
		
		this.setOnScroll((ScrollEvent e) -> {
			scrollY += e.getDeltaY();
			// altezza carta * (numero di righe totale) - altezza finestra
			float altezzaCarta = (float)Carta.HEIGHT / Carta.Size.SMALL.ratio * Main.HEIGHT / 700;
			float numeroDiRighe = (unlocked.getChildren().size() + locked.getChildren().size());
			scrollY = Math.min(0, Math.max(scrollY, -(altezzaCarta * numeroDiRighe - Main.HEIGHT + 50)));
			this.setTranslateY(scrollY);
		});
		
	}
}
