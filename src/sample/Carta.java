package sample;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Carta {
	private static final ArrayList<Carta> carte = new ArrayList<>();
	private static final Carta cartaDefault = new Carta("ERROR", "MANY ERRORS", "res/carte/immagini/defaultCard.png");
	
	public static final int WIDTH = (int)(400 * (float)Main.WIDTH / 1300), HEIGHT = (int)(500 * (float)Main.HEIGHT / 700);
	private final String nome, descrizione, immagine;
	
	private Carta(String nome, String descrizione, String immagine) {
		this.nome = nome;
		this.descrizione = descrizione.replace("\\n", "\n");
		this.immagine = immagine;
	}
	
	public Group getGraphic(float ratio){
		Group div = new Group();
		
		ImageView immagine = new ImageView();
		Text fieldNome = new Text(), fieldDescrizione = new Text();
		Rectangle border = new Rectangle(WIDTH / ratio * (float)Main.WIDTH / 1300, HEIGHT / ratio * (float)Main.HEIGHT / 700);
		
		fieldNome.setText(nome.toUpperCase());
		fieldNome.setFont(new Font(20.0f / ratio));
		fieldNome.setTranslateX(25.0f / ratio * (float)Main.WIDTH / 1300);
		fieldNome.setTranslateY(35.0f / ratio * (float)Main.HEIGHT / 700);
		
		fieldDescrizione.setText(descrizione);
		fieldDescrizione.setFont(new Font(15.0f / ratio));
		fieldDescrizione.setTranslateY(375.0f / ratio * (float)Main.HEIGHT / 700);
		fieldDescrizione.setTranslateX(25.0f / ratio * (float)Main.WIDTH / 1300);
		
		immagine.setImage(new Image(this.immagine, 350.0f / ratio * (float)Main.WIDTH / 1300, 270.0f / ratio * (float)Main.HEIGHT / 700, false, true)); // immagini in formato 350 - 270
		immagine.setTranslateX(25.0f / ratio * (float)Main.WIDTH / 1300);
		immagine.setTranslateY(60.0f / ratio * (float)Main.HEIGHT / 700);

		border.setStroke(Paint.valueOf("#000000"));
		border.setFill(Paint.valueOf("#ffffffff"));
		border.setStrokeWidth(3.0f / ratio);
		
		div.getChildren().addAll(border, fieldNome, fieldDescrizione, immagine);
		
		return div;
	}
	
	public ImageView getImage(float ratio){
		return new ImageView(new Image(immagine.replace(".png", "Placed.png"),WIDTH / ratio, HEIGHT / ratio, false, true));
	}
	
	
	public enum Size{
		SMALL(3), MEDIUM(2), BIG(1);
		
		public int ratio;
		Size(int a) {
			this.ratio = a;
		}
	}
	
	
	
	private static void generaCarte() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/res/carte/carte"));
			String line;
			while ((line = reader.readLine()) != null){
				line = line.strip();
				BufferedReader reader1 = new BufferedReader(new FileReader("src/res/carte/descrizioni/" + line));
				Carta carta = new Carta(reader1.readLine(), reader1.readLine(), "res/carte/immagini/" + line + ".png");
				Carta.carte.add(carta);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Carta get(int ind){
		if(carte.size() == 0)
			generaCarte();
		
		if(ind < carte.size())
			return carte.get(ind);
		return cartaDefault;
	}
	
}
