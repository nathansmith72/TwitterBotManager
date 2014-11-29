/* this file is currently in early alpha and should not be used */

package twitter;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHelper {
	private static TwitterStream twitterStream;

	public static void startBot(){

	}

	public static void stopBot(){

	}

	public static void startFilterStream(String[] keywords, StatusListener listener){

		//String bot = (String) botList.getSelectedValue(); this function will be used later to select the bot to run


		ConfigurationBuilder builder = new ConfigurationBuilder();

		/*these are the keys for the account. this should be changed to retrieve them from the db */
        builder.setOAuthAccessToken("2809713350-ENiupVlePRKlCSKbXZxxRtlYykec2dPhEDAKSg2");
        builder.setOAuthAccessTokenSecret("H5Ccj1ry95WErEitM6lEcKdhGytbx9mgyuJxqejmYIJ35");
        builder.setOAuthConsumerKey("HeDpAoV19goEcqzigobz1uL14");
        builder.setOAuthConsumerSecret("Iy5fugfpD1csJwVGh0Ib8n3t8NzRi5rhm0uFkrBWbrKaltpPdN");

		twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
        twitterStream.addListener(listener);
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        FilterQuery filter = new FilterQuery();
        filter.track(keywords);
        twitterStream.filter(filter);
	}

	public static void stopFilterStream(){
		twitterStream.cleanUp(); // shutdown internal stream consuming thread
		twitterStream.shutdown();
	}

	public static void ReTweet(){

	}
}
