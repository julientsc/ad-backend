package model.campaign;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.auth.Token;
import model.auth.Tokens;

public class Campaigns {

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Tokens.class.getName()); 
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final static String PATH = "./campaigns.json";
	
	private static Campaigns INSTANCE = null;
	private HashMap<Long, HashMap<Long, Campaign>> campaigns = null; // USER_ID // CAMPAIGN_ID
	private long currentCampaignId = 0;
	
	public static Campaigns getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = load(PATH);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				INSTANCE = new Campaigns();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}
	
	private static Campaigns load(String path) throws IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(path));
		Campaigns campaigns = GSON.fromJson(br, Campaigns.class);
		br.close();
		return campaigns;
	}
	
	
	private Campaigns() {
		this.campaigns = new HashMap<Long, HashMap<Long,Campaign>>();
		this.currentCampaignId = 0;
	}
	
	
	public Campaign addCampaign(Token token) {
		long id = currentCampaignId++;
		Campaign campaign = new Campaign(id);
		long userId = token.getUserId();
		
		if (!campaigns.containsKey(userId)) {
			campaigns.put(userId, new HashMap<Long, Campaign>()); 
		}
		
		campaigns.get(userId).put(id, campaign);
		save();
		
		return campaign;		
	}
	
	public boolean removeCampaign(Token token, long id) {
		long userId = token.getUserId();
		if (!campaigns.containsKey(userId)) {
			return false;
		}
		
		if(campaigns.get(userId).containsKey(id) && campaigns.get(userId).get(id).isActive()) {
			campaigns.get(userId).get(id).setActive(false);
			save();
			return true;
		}
		return false;
	}
	
	public Campaign getCampaign(Token token, long id) {
		long userId = token.getUserId();
		if (!campaigns.containsKey(userId)) {
			return null;
		}
		
		if(campaigns.get(userId).containsKey(id) && campaigns.get(userId).get(id).isActive())
			return campaigns.get(userId).get(id);
		return null;
	}
	
	public void save() {
		try {
			PrintWriter pw = new PrintWriter(PATH);
			pw.write(GSON.toJson(INSTANCE));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Set<Long> getCampaignIds(Token token) {
		if(!campaigns.containsKey(token.getUserId()))
			campaigns.put(token.getUserId(), new HashMap<Long, Campaign>() );
		
		return campaigns.get(token.getUserId()).keySet();
	}
	
	
}
