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
		//MÉTODO PRINCIPAL
		ReadWithScanner parser = new ReadWithScanner(filePathOfLog);
		parser.processLineByLine();
		parser.log("Was fun. :)");
	}

	public ReadWithScanner(String aFileName) {
		//MÉTODO CONSTRUTOR
		fFilePath = Paths.get(aFileName);
	}

	public final void processLineByLine() throws IOException {
		//MÉTODO QUE FARÁ A LEITURA LINHA POR LINHA DO ARQUIVO .LOG
		try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine());
			}
		}
	}

	protected void processLine(String aLine) {
		/*Scanner scanner = new Scanner(aLine);*/
		//MÉTODO DE PROCESSAMENTO DE LINHA, ONDE SE USA EXPRESSÕES REGULARES PARA ENCONTRAR O PADRÃO DESEJADO.
		Pattern kill = Pattern.compile("\\d+:\\d+ Kill: \\d+ \\d+ \\d+: (?<killer>.+) killed (?<killed>.+) by (?<weapon>\\w+)$");
		Pattern initGame = Pattern.compile("\\d+:\\d+ ShutdownGame:$");
		 Matcher matchKill = kill.matcher(aLine);
		 Matcher matchInitGame = initGame.matcher(aLine);
		
		 //CASO ENCONTRE MORTE
		  if (matchKill.find()) {
		    String killer = matchKill.group("killer");
		    String killed = matchKill.group("killed");
		    String weapon = matchKill.group("weapon");
		    
		    //SE MORTE FOI PARA O <world> É RETIRADO 1 PONTO, CASO NÃO SEJA, É ADICIONADO.
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
		    	
		      // DESCONTA KILL DO MORTO
		    } else {
		    	addKillers(killer);
		    
		    	addWeapon(weapon);		    
		      // CONTA KILL PRO KILLER
		      // ATUALIZA LISTA DE PLAYERS (Put) COM QUEM MATOU E MORREU
		      // CONTA ESTATÍSTICA DE ARMA
		    }
		  }
		  
		  //SE INICIO DE PARTIDA, É CHAMADO MÉTODO LOG PARA MOSTRAR OS DETALHES DA PARTIDA ANTERIOR E SÃO RESETADAS 
		  //AS VARIAVEIS PARA QUE UMA NOVA PARTIDA SE DÊ INICIO.
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
	
	//ADICIONA ARMA
	public void addWeapon(String weapon){
		if(weapons.containsKey(weapon)){
    		int weaponValue = weapons.get(weapon);
    		weapons.put(weapon, weaponValue+1);
    	}else{
    		weapons.put(weapon, 1);
    	}
	}
	
	//ADICIONA MATADOR
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
	
	//MÉTODO ONDE É MONTADO O JSON DO OUTPUT
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

	
	//----------------------GETTERS E SETTERS------------------------
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
