package br.com.parserJava;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadWithScanner {
	
	private static String filePathOfLog = "C:\\game.log";
	
	private HashMap<String,Integer> weapons = new HashMap<String,Integer>();
	private HashMap<String,Integer> killers = new HashMap<String,Integer>();
	private int totalKills = 0;
	private int countGame = 0;
	private String players="";
	private String playersAndKills="";
	private String killsByMeans="";


	public static void main(String[] args) throws IOException {
		ReadWithScanner parser = new ReadWithScanner(filePathOfLog);
		parser.processLineByLine();
		parser.log("Was fun. :)");
	}

	public ReadWithScanner(String aFileName) {
		fFilePath = Paths.get(aFileName);
	}

	public final void processLineByLine() throws IOException {
		try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine());
			}
		}
	}

	protected void processLine(String aLine) {
		/*Scanner scanner = new Scanner(aLine);*/

		Pattern kill = Pattern.compile("\\d+:\\d+ Kill: \\d+ \\d+ \\d+: (?<killer>.+) killed (?<killed>.+) by (?<weapon>\\w+)$");
		Pattern initGame = Pattern.compile("\\d+:\\d+ ShutdownGame:$");
		 Matcher matchKill = kill.matcher(aLine);
		 Matcher matchInitGame = initGame.matcher(aLine);
		
		  if (matchKill.find()) {
		    String killer = matchKill.group("killer");
		    String killed = matchKill.group("killed");
		    String weapon = matchKill.group("weapon");
		    if (killer.equalsIgnoreCase("<world>")) {
		    	if(killers.containsKey(killed)){
		    		int i = killers.get(killed);
		    		killers.put(killed, i-1);
		    		totalKills++;
		    		addWeapon(weapon);
		    	}else{
		    		killers.put(killed, -1);
		    		totalKills++;
		    		addWeapon(weapon);
		    	}
		    	
		      // desconta kill do killed
		    } else {
		    	addKillers(killer);
		    
		    	addWeapon(weapon);		    
		      // conta kill pro killer
		      // atualiza lista de players (Put) com o killer e killed
		      // conta estatistica de arma
		    }
		  }
		  if(matchInitGame.find()){
			  countGame++;
			  log(mountLogPerGame(countGame, killers, weapons));
			  killers.clear();
			  weapons.clear();
			  players="";
			  playersAndKills="";
			  killsByMeans="";
			  totalKills=0; 
		  }
	}
	
	public void addWeapon(String weapon){
		if(weapons.containsKey(weapon)){
    		int weaponValue = weapons.get(weapon);
    		weapons.put(weapon, weaponValue+1);
    	}else{
    		weapons.put(weapon, 1);
    	}
	}
	
	public void addKillers(String killer){
		if(killers.containsKey(killer)){
    		int killerValue = killers.get(killer);
    		killers.put(killer, killerValue+1);
    		totalKills++;
    	}else{
    		killers.put(killer, 1);
    		totalKills++;
    	}
	}
	public String mountLogPerGame(int gameCount, HashMap<String, Integer> mapKillers, HashMap<String, Integer> mapWeapon){
		Set<String> chavesPlayers = mapKillers.keySet();
		Set<String> chavesWeapons = mapWeapon.keySet();
		
		for (Iterator<String> iterator = chavesPlayers.iterator(); iterator.hasNext();)
		{
			String chave = iterator.next();
			if(chave != null)
		//	"Dono da bola"
			 players = players+"\""+chave+"\",";
		//	"Dono da bola": 5,
			 playersAndKills = playersAndKills+"\""+chave+"\":"+mapKillers.get(chave)+",";
		}
		
		for (Iterator<String> iterator = chavesWeapons.iterator(); iterator.hasNext();)
		{
			String chaveWeapon = iterator.next();
			if(chaveWeapon != null)
		//	"MOD_SHOTGUN": 10"
				killsByMeans = killsByMeans+"\""+chaveWeapon+"\":"+mapWeapon.get(chaveWeapon)+",";
		}
		
		return "game_"+gameCount+": {\n    total_kills: "+totalKills+";\n    "
				+ "players: ["+players+"];\n    kills: {"+playersAndKills+"};\n"
						+ "kills_by_means: {"+killsByMeans+"}";
	}
	
	private final Path fFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	public void log(Object aObject) {
		System.out.println(String.valueOf(aObject));
	}

	public HashMap<String, Integer> getWeapons() {
		return weapons;
	}

	public void setWeapons(HashMap<String, Integer> weapons) {
		this.weapons = weapons;
	}

	public HashMap<String, Integer> getKillers() {
		return killers;
	}

	public void setKillers(HashMap<String, Integer> killers) {
		this.killers = killers;
	}

	public int getTotalKills() {
		return totalKills;
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}

	public int getCountGame() {
		return countGame;
	}

	public void setCountGame(int countGame) {
		this.countGame = countGame;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public String getPlayersAndKills() {
		return playersAndKills;
	}

	public void setPlayersAndKills(String playersAndKills) {
		this.playersAndKills = playersAndKills;
	}

	public String getKillsByMeans() {
		return killsByMeans;
	}

	public void setKillsByMeans(String killsByMeans) {
		this.killsByMeans = killsByMeans;
	}


}
