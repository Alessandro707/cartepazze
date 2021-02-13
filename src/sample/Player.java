package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

// alla prima apertura (non c'è il file con l'id) ti chiede di creare un accout, che crea un account su db e hai l'id che viene scritto sul
// file e alle aperture dopo lo prende dal db
public class Player {
	private static Player instance;
	
	private String name, password;
	private int soldi;
	private Image immagineProfilo;
	private int id = -1;
	private int partiteTotali, vittorie, sconfitte = 0;
	private float wr;
	private final ArrayList<Carta> carte = new ArrayList<>();
	
	private Player() {
		File file = new File("src/res/player/info");
		if(file.exists()){
			init();
		}
	}
	
	// asks the server for a new account, sending name (prompted by user) and starting credits
	// server returns id, to save on the file and in the attribute
	public void createAccount(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/res/player/info"));
			writer.write(this.id + "");
			writer.flush();
			writer.close();
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// getting the user from the server from id, init everything
	private void init(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/res/player/info")); // <- contiene l'id
			this.id = Integer.parseInt(reader.readLine());
			
			Net.leggiAccount(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void vittoria(){
		this.vittorie++;
		this.partiteTotali++;
		this.wr = (this.sconfitte == 0) ? 1.0f : (float)this.vittorie / this.sconfitte;
		Net.vittoria(this);
	}
	
	public void sconfitta(){
		this.sconfitte++;
		this.partiteTotali++;
		this.wr = (this.sconfitte == 0) ? 1.0f : (float)this.vittorie / this.sconfitte;
		Net.sconfitta(this);
	}
	
	public void setId(int id){
		if(this.id == -1){
			this.id = id;
		}
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setSoldi(int soldi) {
		this.soldi = soldi;
	}
	
	public void setWr() {
		this.wr = (this.sconfitte == 0) ? 1.0f : (float)this.vittorie / this.sconfitte;
	}
	
	public void setPartiteTotali(int partiteTotali) {
		this.partiteTotali = partiteTotali;
	}
	
	public void setImmagineProfilo(Image immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	
	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}
	
	public void setSconfitte(int sconfitte) {
		this.sconfitte = sconfitte;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getSoldi() {
		return soldi;
	}
	
	public Image getImmagineProfilo() {
		return immagineProfilo;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPartiteTotali() {
		return partiteTotali;
	}
	
	public int getVittorie() {
		return vittorie;
	}
	
	public int getSconfitte() {
		return sconfitte;
	}
	
	public float getWr() {
		return wr;
	}
	
	
	
	public static Player get(){
		if(instance == null)
			instance = new Player();
		
		return instance;
	}
	
	public HBox getGraphicsData(){
		HBox div = new HBox();
		div.setSpacing(20);
		
		Text text = new Text(this.name + " - " + this.soldi + "$");
		text.setFont(new Font(20));
		text.setTranslateY(10);
		
		ImageView imageView = new ImageView(this.immagineProfilo);
		
		div.getChildren().addAll(imageView, text);
		return div;
	}
	
	
	public HBox getGraphicsStats(){
		HBox div = new HBox();
	
		Text text = new Text("wr: " + this.wr + " - carte: " + this.carte.size() / 50 * 100); // TODO: sostituire con tot carte
		text.setFont(new Font(20));
		text.setTranslateY(10);
		
		div.getChildren().addAll(text);
		
		return div;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.soldi + "$ - win ratio: " + this.wr + " - partite totali: " + this.partiteTotali;
	}
}
