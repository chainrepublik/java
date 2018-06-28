// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;



public class CNewTweetPayload extends CPayload 
{
   // Title
   String title;
   
   // Message
   String mes;
   
   // Pic
   String pic;
   
   // Lang
   String cou;
   
   // Categ
   String categ;
   
   // Retweet tweet id
   long retweet_tweet_ID;
   
   // TweetID
   long tweetID;
   
   // Mil unit
   long mil_unit;
   
   // Pol party
   long pol_party;
   
   // Days
   long days;
   
   // Serial
   private static final long serialVersionUID = 1L;
   
   public CNewTweetPayload(String adr, 
		           String title, 
                           String mes, 
                           String categ,
                           String cou,
                           String pic,
                           long retweet_tweet_ID,
                           long mil_unit,
                           long pol_party,
                           long days) throws Exception
   {
	  // Superclass
	   super(adr);
	   
           // Title
           this.title=title;
           
	   // Message
           this.mes=mes;
           
           // Pic
           this.pic=pic;
           
           // Language
           this.cou=cou;
           
           // Category
           this.categ=categ;
              
           // Retweet tweeet ID
           this.retweet_tweet_ID=retweet_tweet_ID;
           
           // Tweet ID
           this.tweetID=UTILS.BASIC.getID();
           
           // Mil unit
           this.mil_unit=mil_unit;
           
           // Pol party
           this.pol_party=pol_party;
           
           // Days
           this.days=days;
           
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.title+
                                 this.mes+
                                 this.pic+
                                 this.cou+
                                 this.categ+
                                 this.retweet_tweet_ID+
                                 this.tweetID+
                                 this.mil_unit+
                                 this.pol_party+
                                 this.days);
 	
   }
   
   public void check(CBlockPayload block) throws Exception
   {
        // Check
        super.check(block);
        
         // Check energy
       this.checkEnergy(5);
        
        // Days
        if (days<1)
            throw new Exception("Invalid days - CNewTweetPayload.java");
        
        // ID valid ?
        if (UTILS.BASIC.isID(this.tweetID))
           throw new Exception("Invalid tweetID - CNewTweetPayload.java");
        
        // Language
        if (!UTILS.BASIC.isCou(this.cou))
            throw new Exception("Invalid language - CNewTweetPayload.java");
        
        // Category
        if (!this.categ.equals("ID_ADULTS") && !this.categ.equals("ID_ART") && !this.categ.equals("ID_AUTOMOTIVE") && 
            !this.categ.equals("ID_BEAUTY") && !this.categ.equals("ID_BUSINESS") && !this.categ.equals("ID_COMEDY") && 
            !this.categ.equals("ID_CRYPTO") && !this.categ.equals("ID_EDUCATION") && !this.categ.equals("ID_ENTERTAINMENT") && 
            !this.categ.equals("ID_FAMILY") && !this.categ.equals("ID_FASHION") && !this.categ.equals("ID_FOOD") && 
            !this.categ.equals("ID_GAMING") && !this.categ.equals("ID_HEALTH") && !this.categ.equals("ID_HOWTO") && 
            !this.categ.equals("ID_JOURNALS") && !this.categ.equals("ID_LIFESTYLE") && !this.categ.equals("ID_HOWTO") && 
            !this.categ.equals("ID_CHAINREPUBLIK") && !this.categ.equals("ID_MOVIES") && !this.categ.equals("ID_MUSIC") && 
            !this.categ.equals("ID_NEWS") && !this.categ.equals("ID_PETS") && !this.categ.equals("ID_PHOTOGRAPHY") && 
            !this.categ.equals("ID_POLITICS") && !this.categ.equals("ID_SCIENCE") && !this.categ.equals("ID_SHOPPING") && 
            !this.categ.equals("ID_SPORTS") && !this.categ.equals("ID_TECH") && !this.categ.equals("ID_TRAVEL") && 
            !this.categ.equals("ID_OTHER"))
        throw new Exception("Invalid location - CNewTweetPayload.java");
        
        // Not a retweet
        if (this.retweet_tweet_ID==0)
        {
           // Check title
           if (!UTILS.BASIC.isTitle(this.title))
               throw new Exception ("Invalid title - CNewTweetPayload.java");
        
   	   // Check Message
           if (!UTILS.BASIC.isDesc(this.mes, 10000))
            throw new Exception ("Invalid message - CNewTweetPayload.java");
           
           // Pic
           if (!this.pic.equals(""))
            if (!UTILS.BASIC.isPic(this.pic))
                throw new Exception ("Invalid pic - CNewTweetPayload.java");
        }
            
        // Check if retweet ID exist
        if (this.retweet_tweet_ID>0)
           if (!UTILS.BASIC.targetValid("ID_POST", this.retweet_tweet_ID))
              throw new Exception("Invalid retweet_tweet_ID - CNewTweetPayload.java");
        
        // Miliary unit ?
        if (this.mil_unit>0)
        {
            // Address mil unit
            long mu=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "mil_unit"));
            
            // Member ?
            if (mu!=this.mil_unit)
                throw new Exception("Invalid military unit - CNewTweetPayload.java");
        }
        
        // Political party ?
        if (this.pol_party>0)
        {
            // Address mil unit
            long pp=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
            
            // Member ?
            if (pp!=this.pol_party)
                throw new Exception("Invalid political party - CNewTweetPayload.java");
        }
        
        // Language
        if (this.mil_unit>0 || this.pol_party>0)
            if (this.cou.equals("EN"))
               throw new Exception("Invalid language - CNewTweetPayload.java");
        
        // Check Hash
	String h=UTILS.BASIC.hash(this.getHash()+
 			          this.title+
                                  this.mes+
                                  this.pic+
                                  this.cou+
                                  this.categ+
                                  this.retweet_tweet_ID+
                                  this.tweetID+
                                  this.mil_unit+
                                  this.pol_party+
                                  this.days);
	  
   	if (!h.equals(this.hash)) 
           throw new Exception ("Invalid hash - CNewTweetPayload.java");
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
          // Superclass
          super.commit(block);
          
          // Commit
          UTILS.DB.executeUpdate("INSERT INTO tweets "
                                       + "SET tweetID='"+this.tweetID+"', "
   		   		            + "adr='"+this.target_adr+"', "
   		   		            + "title='"+UTILS.BASIC.base64_encode(this.title)+"', "
                                            + "mes='"+UTILS.BASIC.base64_encode(this.mes)+"', "
   		                            + "pic='"+UTILS.BASIC.base64_encode(this.pic)+"', "
                                            + "cou='"+this.cou+"', "
                                            + "categ='"+this.categ+"', "
                                            + "expires='"+(this.block+(this.days*1440))+"', "
                                            + "retweet_tweet_ID='"+this.retweet_tweet_ID+"', "
                                            + "mil_unit='"+this.mil_unit+"', "
                                            + "pol_party='"+this.pol_party+"', "
                                            + "block='"+this.block+"'");
           
           // Retweet ?
           if (this.retweet_tweet_ID>0)
           UTILS.DB.executeUpdate("UPDATE tweets "
                                   + "SET retweets=retweets+1, "
                                       + "block='"+this.block+"' "
                                 + "WHERE tweetID='"+this.retweet_tweet_ID+"'");
           
           // Position type
           UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
   }
}
