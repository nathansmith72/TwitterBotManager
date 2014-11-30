

package bot;

import action.Action;

import java.util.ArrayList;
import java.io.Serializable;

public class Bot implements Serializable{

	/**
	**	Variable Declarations
	*/
	private String name, consumerKey, consumerSecret, accessToken, accessSecret;
	private ArrayList<Action> actions;

	/**
	**	Default Constructor. All of these variables must be filled in on the GUI. The GUI validates
	**	That all of the data is provided.
	*/
	public Bot(String name, String consumerKey, String consumerSecret,
		String accessToken, String accessSecret){
		this.name = name;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessSecret = accessSecret;
		actions = new ArrayList<Action>();
	}

	public void addAction(Action action){
		actions.add(action);
	}
	
	public void deleteAction(int index){
		actions.remove(index);
	}
	
	public ArrayList<Action> getActions(){
		return actions;
	}
	
	public String getName(){
		return new String(name);
	}
	
	public String getConsumerKey(){
		return new String(consumerKey);
	}
	
	public String getConsumerSecret(){
		return new String(consumerSecret);
	}
	
	public String getAccessToken(){
		return new String(accessToken);
	}
	
	public String getAccessSecret(){
		return new String(accessSecret);
	}
}