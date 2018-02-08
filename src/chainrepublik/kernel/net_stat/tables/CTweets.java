package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTweets extends CTable
{
    public CTweets() 
    {
        super("tweets");
    }
    
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE tweets(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                        + "tweetID BIGINT NOT NULL DEFAULT '0', "
                                                        + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                        + "categ VARCHAR(50) NOT NULL DEFAULT '', "
                                                        + "mes VARCHAR(20000) NOT NULL DEFAULT '', "
                                                        + "title VARCHAR(250) NOT NULL DEFAULT '', "
                                                        + "pic VARCHAR(250) NOT NULL DEFAULT '', "
                                                        + "block BIGINT NOT NULL DEFAULT '0', "
                                                        + "expires BIGINT NOT NULL DEFAULT '0', "
                                                        + "retweet VARCHAR(2) NOT NULL DEFAULT 'N', "
                                                        + "retweet_tweet_ID BIGINT NOT NULL DEFAULT '0', "
                                                        + "cou VARCHAR(10) NOT NULL DEFAULT '', "
                                                        + "of_dec BIGINT NOT NULL DEFAULT 0, "
                                                        + "pol_party BIGINT NOT NULL DEFAULT 0, "
                                                        + "mil_unit BIGINT NOT NULL DEFAULT 0, "
                                                        + "retweets BIGINT NOT NULL DEFAULT '0')");
             
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX tweets_tweetID ON tweets(tweetID)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_adr ON tweets(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_retweet_tweet_ID ON tweets(retweet_tweet_ID)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_expires ON tweets(expires)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                             + "FROM tweets "
                            + "WHERE expires<='"+block+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
}
