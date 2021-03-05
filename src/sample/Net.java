package sample;


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.imageio.ImageIO;

public interface Net {
	String WEB_URL = "http://cartepazzedelcirco.altervista.org/";
	String CREA = "CreaAccount.php", LEGGI = "LeggiAccount.php", VITTORIA = "Vittoria.php", SCONFITTA = "Sconfitta.php";
	String CAMBIA_NOME = "CambiaNome.php";
	
	static void leggiAccount(Player player) {
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL + Net.LEGGI);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("id", player.getId() + "");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status != 200){
				MyLogger.error("Response code while loading the account from the server: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			status = con.getResponseCode();
			if(status == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String msg = in.readLine();
				in.close();
				
				Net.elabora(player, msg);
				
				MyLogger.info("Successfully loaded the account");
			}
			else{
				MyLogger.error("Response code while loading the account from the server: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.LEGGI);
		}
	}
	
	static void elabora(Player player, String msg){
		try {
			JSONArray jsonArray =(JSONArray)( new JSONParser().parse(msg));
			for (Object json:jsonArray) {
				JSONObject jo = (JSONObject) json;
				player.setName((String)jo.get("name"));
				player.setPassword((String)jo.get("password"));
				player.setSoldi(Integer.parseInt((String)jo.get("credits")));
				player.setPartiteTotali(Integer.parseInt((String)jo.get("total_plays")));
				player.setVittorie(Integer.parseInt((String)jo.get("wins")));
				player.setSconfitte(Integer.parseInt((String)jo.get("losses")));
				//player.setImmagineProfilo(stringToImage((String)jo.get("profile_img"), (String)jo.get("img_format")));
				if(createImageFromString((String)jo.get("profile_img"), (String)jo.get("img_format"), "src/res/player/", "profileImage")){
					player.setImmagine("res/player/profileImage." + jo.get("img_format"));
				}
				else{
					player.setImmagine("res/player/basePlayerImage.png");
				}
				player.setFormatoImmagine((String)jo.get("img_format"));
			}
		} catch (Exception e){
			MyLogger.error("Can't get the player info from the server. Server msg: " + msg);
		}
	}
	
	static void createAccount(Player player) {
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL + Net.CREA);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("name", player.getName());
			parameters.put("password", player.getPassword());
			String img = imageToString("src/res/player/basePlayerImage.png", "png"); // TODO: c'è qualcosa che non va
			parameters.put("profile_img", img);
			parameters.put("img_format", "png");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String msg = in.readLine();
				in.close();
				
				try {
					JSONArray jsonArray =(JSONArray)( new JSONParser().parse(msg));
					for (Object json:jsonArray) {
						JSONObject jo = (JSONObject) json;
						player.setId(Integer.parseInt((String)jo.get("id")));
					}
					
					MyLogger.info("Succesfully created a new account");
				} catch (Exception e){
					MyLogger.error("Can't get the user account from the db");
					con.disconnect();
					Main.stage.close();
					return;
				}
			}
			else {
				MyLogger.error("Response code while creating the account: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.CREA);
			Main.stage.close();
		}
	}
	
	static void vittoria(Player player){
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL + Net.VITTORIA);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("id", player.getId() + "");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status != 200){
				MyLogger.error("Response code while updating the player victories: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.VITTORIA);
		}
	}
	
	static void sconfitta(Player player){
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL + Net.SCONFITTA);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("id", player.getId() + "");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status != 200){
				MyLogger.error("Response code while updating the player losses: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.SCONFITTA);
		}
	}
	
	static void cambiaNome(Player player){
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL + Net.CAMBIA_NOME);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("id", player.getId() + "");
			parameters.put("nome", player.getName() + "");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status != 200){
				MyLogger.error("Response code while updating the player name: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.CAMBIA_NOME);
		}
	}
	
	private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
			result.append("&");
		}
		
		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}
	
	private static boolean createImageFromString(String bits, String format, String path, String imgName){
		try {
			bits = bits.substring(0, bits.length() - 1); // remove last : at the end
			String[] vals = bits.split(":");
			byte[] data = new byte[vals.length];
			for(int i = 0; i < vals.length; i++) {
				data[i] = Byte.parseByte(vals[i]);
			}
			
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, format, new File(path + imgName + "." + format));
			return true;
		} catch (IOException e) {
			MyLogger.error("Can't load the profile image");
		}
		
		return false;
	}
	/*
	private static Image stringToImage(String bits, String format){
		try {
			bits = bits.substring(0, bits.length() - 1); // remove last : at the end
			String[] vals = bits.split(":");
			byte[] data = new byte[vals.length];
			for(int i = 0; i < vals.length; i++) {
				data[i] = Byte.parseByte(vals[i]);
			}
			
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, format, new File("src/res/player/profileImage." + format));
			return new Image("res/player/profileImage." + format, 75 * (float)Main.WIDTH / 1300, 75 * (float)Main.WIDTH / 1300,true, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new Image("src/res/player/basePlayerImage.png");
	}
	*/
	
	private static String imageToString(String path, String format){
		try {
			BufferedImage bImage = ImageIO.read(new File(path));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bImage, format, bos);
			byte [] data = bos.toByteArray();
			
			String res = "";
			for(byte b : data){
				res = res.concat(b + ":");
			}
			return res;
		}catch (Exception e){
			MyLogger.error("Can't send the user profile image " + path + "." + format + " to the server");
		}
		return "0".repeat(500);
	}
}


/*          for(byte b : data){
				String temp;
				if(b < 0) {
					temp = "1";
					b = (byte)(-b - 1);
				}
				else
					temp = "0";
				
				int v = 128;
				while(temp.length() != 8){
					if(b - v >= 0){
						temp = temp.concat("1");
						v /= 2;
					}
					else{
						temp = temp.concat("0");
						v /= 2;
					}
				}
				
				res = res.concat(temp);
			}*/


/*
			String res = "";
			File file = new File(path);
			BufferedImage img = ImageIO.read(file);
			for (int y = 0; y < Math.min(img.getHeight(), 335); y++) {
				for (int x = 0; x < Math.min(img.getWidth(), 335); x++) {
					Color color = new Color(img.getRGB(x, y), true);
					int red = color.getRed();
					int green = color.getGreen();
					int blue = color.getBlue();

					String r = "";
					int v = 128;
					while(r.length() != 8){
						if(red - v >= 0){
							r = r.concat("1");
							v /= 2;
						}
						else{
							r = r.concat("0");
							v /= 2;
						}
					}
					String g = "";
					v = 128;
					while(g.length() != 8){
						if(green - v >= 0){
							g = g.concat("1");
							v /= 2;
						}
						else{
							g = g.concat("0");
							v /= 2;
						}
					}
					String b = "";
					v = 128;
					while(b.length() != 8){
						if(blue - v >= 0){
							b = b.concat("1");
							v /= 2;
						}
						else{
							b = b.concat("0");
							v /= 2;
						}
					}
					res = res.concat(r + g + b);
				}
			}
			return res;
			*/



			/*
			byte[] data = new byte[bits.length() / 8];
			for(int i = 0; i < bits.length() / 8; i++){
				String temp = bits.substring(i * 8, i * 8 + 8);
				
				byte val = 1;
				if(temp.charAt(0) == '1')
					val = -1;
				
				int v = 64;
				for(int j = 1; j < temp.length(); j++){
					if(temp.charAt(j) == '1')
						val += v;
					v /= 2;
				}
				data[i] = val;
			}
			
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, format, new File("src/res/player/profileImage." + format));
			
			return new Image("src/res/player/profileImage." + format);
			 */
