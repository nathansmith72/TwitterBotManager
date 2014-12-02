//this file is not used yet. Still needs tweeking

package action;

import javax.swing.text.BadLocationException;
import java.io.Serializable;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class Action implements Serializable{
	//possible actions
	static final String LOG = "Log";
	static final String TWEET = "Tweet";
	static final String FAVORITE = "Favorite";
	static final String RETWEET = "Retweet";
	static final String REPLY = "Reply";

	//possible conditions
	static final String ON_STREAM_KEYWORD = "On Keyword";
	static final String ON_TIMER = "On Delay";
	static final String ON_KEYWORD_AND_DELAY = "On Keyword and Delay";
	
	//variables
	private String action;
	private String conditionType;
	private long delay;
	private String[] keyWords;
	private String tweetText;

	public Action(String action, String conditionType, long delay, String tweetText, String[] keyWords){
		this.action = action;
		this.conditionType = conditionType;
		this.delay = delay;
		this.tweetText = tweetText;
		this.keyWords = keyWords.clone();
	}
	
	public String getAction(){
		return action;
	}
	
	public String getConditionType(){
		return conditionType;
	}
	
	public long getDelay(){
		return delay;
	}
	
	public String getTweetText(){
		return tweetText;
	}
	
	public String[] getKeywords(){
		return keyWords;
	}
	
	public String getFormattedKeywords(){
		String formattedKeywords = "";
		for(int i = 0; i<keyWords.length; i++){
			if(i < keyWords.length-1){
				formattedKeywords = formattedKeywords + keyWords[i] + ", ";
			} else {
				formattedKeywords = formattedKeywords + keyWords[i];
			}
		}
		return formattedKeywords;
	}
}
