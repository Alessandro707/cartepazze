package sample;

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
	private String name, password;
	private int soldi;
	//private Image immagineProfilo;
	private String formatoImmagine;
	private String immagine;
	private int id = -1;
	private int partiteTotali, vittorie, sconfitte = 0;
	private float wr;
	private final ArrayList<Carta> carte = new ArrayList<>();
	
	public Player() {
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
	
	public void updateName(String name){
		this.name = name;
		Net.cambiaNome(this);
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
	
	//public void setImmagineProfilo(Image immagineProfilo) {
	//	this.immagineProfilo = immagineProfilo;
	//}
	
	public void setFormatoImmagine(String formato){
		this.formatoImmagine = formato;
	}
	
	public void setImmagine(String immagine){
		this.immagine = immagine;
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
	
	//public Image getImmagineProfilo() {
	//	return immagineProfilo;
	//}
	
	public String getFormatoImmagine(){
		return formatoImmagine;
	}
	
	public String getImmagine(){
		return immagine;
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
	
	public ArrayList<Carta> getCarte(){
		return carte;
	}
	

	@Override
	public String toString() {
		return this.name + " " + this.soldi + "$ - win ratio: " + this.wr + " - partite totali: " + this.partiteTotali;
	}
}
