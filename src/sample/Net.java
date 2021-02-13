package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public interface Net {
	String WEB_URL = "http://cartepazzedelcirco.altervista.org/";
	String CREA = "CreaAccount.php";
	String LEGGI = "LeggiAccount.php";
	String VITTORIA = "Vittoria.php";
	String SCONFITTA = "Sconfitta.php";
	
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
			parameters.put("profile_img", "0".repeat(500)); // TODO: send img
			
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
				MyLogger.error("Response code while updating the player info: " + status);
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
				MyLogger.error("Response code while updating the player info: " + status);
				con.disconnect();
				Main.stage.close();
				return;
			}
			
			con.disconnect();
		} catch (IOException e) {
			MyLogger.error("Can't connect to the server: " + Net.WEB_URL + Net.SCONFITTA);
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
}
