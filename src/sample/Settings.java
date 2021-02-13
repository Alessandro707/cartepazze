package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	private static final HashMap<SettingOption, String> options = new HashMap<>();
	
	public static void initSettings(){
		for(SettingOption option : SettingOption.values()){
			options.put(option, option.default_value);
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader("src/res/settings"));
			String line;
			while((line = reader.readLine()) != null){
				String[] vals = line.split(":");
				options.replace(SettingOption.valueOf(vals[0]), vals[1]);
			}
			reader.close();
		}catch (Exception e){
			MyLogger.error("Can't load the settings file");
		}
	}
	
	public static void writeSettings(){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/res/settings"));
			
			for(Map.Entry<SettingOption, String> option : options.entrySet()){
				writer.write(option.getKey().toString() + ":" + option.getValue() + "\n");
				writer.flush();
			}
			writer.close();
		}catch (Exception e){
			System.out.println("Can't save the settings to file");
		}
	}
	
	public static void setSetting(SettingOption option, String newVal){
		Settings.options.replace(option, newVal);
	}
	
	public static String getSetting(SettingOption option){
		return Settings.options.get(option);
	}
	
	public enum SettingOption{
		REMEMBER_USER("0");
		
		String default_value;
		SettingOption(String default_value){
			this.default_value = default_value;
		}
	}
}
