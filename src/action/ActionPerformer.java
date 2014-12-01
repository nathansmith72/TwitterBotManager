/**
**  File: ActionPerformer.java
**  Description: This file will perform tasks on twitter based off a Action class object that
**  it is initialized with
**
*/

package action;

import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import bot.Bot;

public class ActionPerformer{

	private ArrayList<Action> actions;
	private Bot bot;
	private static TwitterStream twitterStream;
	
	public ActionPerformer(Bot bot){
		this.bot = bot;
		ArrayList<Action> actions = bot.getActions();
		System.out.println(actions.get(0).getAction());
		for(int i = 0; i<actions.size(); i++){
			if(actions.get(i).getAction().equals(Action.LOG)){
				System.out.println("inside the if statement");
				logOnKeyword(actions.get(i).getKeywords());
			}
		}
	}
	
	public void tweetOnDelay(){
		
	}
	
	public void logOnKeyword(String[] keywords){
		StatusListener listener = new StatusListener(){
		            public void onStatus(Status status) {
		            	gui.GUI.updateStatusText(status.getUser().getName() + " : " + status.getText() + "\n\n");
		            }
		            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
		            public void onException(Exception ex) {
		                ex.printStackTrace();
		            }
					@Override
					public void onScrubGeo(long arg0, long arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onStallWarning(StallWarning arg0) {
						// TODO Auto-generated method stub

					}
		        };
		        ConfigurationBuilder builder = new ConfigurationBuilder();

				builder.setOAuthAccessToken(bot.getAccessToken());
				builder.setOAuthAccessTokenSecret(bot.getAccessSecret());
				builder.setOAuthConsumerKey(bot.getConsumerKey());
				builder.setOAuthConsumerSecret(bot.getConsumerSecret());

				twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
				twitterStream.addListener(listener);
				// sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
				FilterQuery filter = new FilterQuery();
				filter.track(keywords);
				twitterStream.filter(filter);
		        gui.GUI.updateStatusText("Running bot" +"\n\n");
				
	}
}