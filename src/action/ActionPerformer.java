/**
**  File: ActionPerformer.java
**  Description: This file will perform tasks on twitter based off a Action class object that
**  it is initialized with
**
*/

package action;

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

public class ActionPerformer implements Runnable{
	public void run(){
	
	}
	
	private Action action;
	
	public ActionPerformer(Action action){
		this.action = action;
	}
	
	public void tweetOnDelay(){
		new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                Twitter twitter = TwitterFactory.getSingleton();
				Status status = twitter.updateStatus(action.getTweetText());
				System.out.println("Successfully updated the status to [" + status.getText() + "].");
            }
        }, 
        action.getOnTimerDelay());
	}
}