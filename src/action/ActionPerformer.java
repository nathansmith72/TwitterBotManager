/**
**  File: ActionPerformer.java
**  Description: This file will perform tasks on twitter based off a Action class object that
**  it is initialized with
**
*/

package action;

import java.util.ArrayList;
import java.lang.System;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.auth.AccessToken;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterException;

import bot.Bot;

public class ActionPerformer{

	private ArrayList<Action> actions;
	private Bot bot;
	private static TwitterStream twitterStream;
	private long previousActionTime;
	
	public ActionPerformer(){
		
	}
	
	public void runBot(Bot bot){
		this.bot = bot;
		ArrayList<Action> actions = bot.getActions();
		System.out.println(actions.get(0).getAction());
		for(int i = 0; i<actions.size(); i++){
			if(actions.get(i).getAction().equals(Action.LOG)){
				logOnKeyword(actions.get(i).getKeywords(), actions.get(i).getDelay()*1000);
			}
		}
		for(int i = 0; i<actions.size(); i++){
			if(actions.get(i).getAction().equals(Action.REPLY)){
				replyOnKeyword(actions.get(i).getKeywords(), actions.get(i).getDelay()*1000, actions.get(i).getTweetText());
			}
		}
	}
	
	public void stopBot(){
		twitterStream.cleanUp(); // shutdown internal stream consuming thread
		twitterStream.shutdown();
		gui.GUI.updateStatusText("Stopping Bot" +"\n\n");
	}
	
	public void tweetOnDelay(){
		
	}
	
	public void replyOnKeyword(String[] keywords, long delay, final String replyText){
		System.out.println(delay);
		final long minDelay = 60000; //min delay in milliseconds
		if(delay < minDelay){
			delay = minDelay;
		}
		final long finalDelay = delay;
		previousActionTime = 0;
		System.out.println(previousActionTime);
		StatusListener listener = new StatusListener(){
		    public void onStatus(Status status) {
				long currentTime = System.currentTimeMillis();
				if((currentTime - previousActionTime) > finalDelay){
					String tweetText = "@" + status.getUser().getScreenName() + " " + replyText;
					TwitterFactory factory = new TwitterFactory();
					Twitter twitter = factory.getInstance();
					twitter.setOAuthConsumer(bot.getConsumerKey(), bot.getConsumerSecret());
					AccessToken accessToken = new AccessToken(bot.getAccessToken(), bot.getAccessSecret());
					twitter.setOAuthAccessToken(accessToken);
					StatusUpdate statusUpdate = new StatusUpdate(tweetText);
					//statusUpdate.setInReplyToStatusId(status.getId());
					try{
						Status updateStatus = twitter.updateStatus(statusUpdate);
					} catch(TwitterException e){
						e.printStackTrace();
					}
					gui.GUI.updateStatusText("TWEETED: \"" + replyText + "\" to " + status.getUser().getScreenName() + "\n\n");
					previousActionTime = System.currentTimeMillis();
				}
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
		gui.GUI.updateStatusText("Running Bot" +"\n\n");
	}
	
	public void logOnKeyword(String[] keywords, long delay){
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
		gui.GUI.updateStatusText("Running Bot" +"\n\n");
	}
}