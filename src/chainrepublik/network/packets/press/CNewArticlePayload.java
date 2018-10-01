/*
 * Copyright 2018 Vlad Cristian (vcris@gmx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chainrepublik.network.packets.press;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;



public class CNewArticlePayload extends CPayload 
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
   
   // TweetID
   long tweetID;
   
   // Mil unit
   long mil_unit;
   
   // Pol party
   long pol_party;
   
   // Days
   long days;
   
   // Serial
   private static final long serialVersionUID = 100;
   
   public CNewArticlePayload(String adr, 
		             String title, 
                             String mes, 
                             String categ,
                             String cou,
                             String pic,
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
                                 this.tweetID+
                                 this.mil_unit+
                                 this.pol_party+
                                 this.days);
 	
   }
   
   public void check(CBlockPayload block) throws Exception
   {
        // Check
        super.check(block);
        
        // Check for null
        if (this.categ==null ||
            this.cou==null ||
            this.mes==null ||
            this.pic==null ||
            this.title==null)
         throw new Exception("Null assertion failed - CNewTweetPayload.java, 68");
        
        // Check energy
        this.checkEnergy(5);
       
       // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CNewTweetPayload.java, 68");
        
        // Days
        if (days<30)
            throw new Exception("Invalid days - CNewTweetPayload.java");
        
        // ID valid ?
        if (UTILS.BASIC.isID(this.tweetID))
           throw new Exception("Invalid tweetID - CNewTweetPayload.java");
        
        // Language
        if (!this.cou.equals("EN"))
          if (!UTILS.BASIC.isCountry(this.cou))
            throw new Exception("Invalid language - CNewTweetPayload.java");
        
        // Title
        if (!UTILS.BASIC.isTitle(this.title))
            throw new Exception("Invalid title - CNewTweetPayload.java");
        
        // Message
        if (!UTILS.BASIC.isDesc(this.mes, 50000))
            throw new Exception("Invalid message - CNewTweetPayload.java");
        
        // Pic
        if (!this.pic.equals(""))
          if (!UTILS.BASIC.isPic(this.pic))
            throw new Exception("Invalid pic - CNewTweetPayload.java");
        
        // Category
        if (!this.categ.equals("ID_ADULTS") && 
            !this.categ.equals("ID_ART") && 
            !this.categ.equals("ID_AUTOMOTIVE") && 
            !this.categ.equals("ID_BEAUTY") && 
            !this.categ.equals("ID_BUSINESS") && 
            !this.categ.equals("ID_COMEDY") && 
            !this.categ.equals("ID_CRYPTO") && 
            !this.categ.equals("ID_EDUCATION") && 
            !this.categ.equals("ID_ENTERTAINMENT") && 
            !this.categ.equals("ID_FAMILY") && 
            !this.categ.equals("ID_FASHION") && 
            !this.categ.equals("ID_FOOD") && 
            !this.categ.equals("ID_GAMING") && 
            !this.categ.equals("ID_HEALTH") && 
            !this.categ.equals("ID_HOWTO") && 
            !this.categ.equals("ID_JOURNALS") && 
            !this.categ.equals("ID_LIFESTYLE") && 
            !this.categ.equals("ID_HOWTO") && 
            !this.categ.equals("ID_CHAINREPUBLIK") && 
            !this.categ.equals("ID_MOVIES") && 
            !this.categ.equals("ID_MUSIC") && 
            !this.categ.equals("ID_NEWS") && 
            !this.categ.equals("ID_PETS") && 
            !this.categ.equals("ID_PHOTOGRAPHY") && 
            !this.categ.equals("ID_POLITICS") && 
            !this.categ.equals("ID_SCIENCE") && 
            !this.categ.equals("ID_SHOPPING") && 
            !this.categ.equals("ID_SPORTS") && 
            !this.categ.equals("ID_TECH") && 
            !this.categ.equals("ID_TRAVEL") && 
            !this.categ.equals("ID_OTHER"))
        throw new Exception("Invalid category - CNewTweetPayload.java");
        
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
        
        // Check Hash
	String h=UTILS.BASIC.hash(this.getHash()+
 			          this.title+
                                  this.mes+
                                  this.pic+
                                  this.cou+
                                  this.categ+
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
                                            + "mil_unit='"+this.mil_unit+"', "
                                            + "pol_party='"+this.pol_party+"', "
                                            + "block='"+this.block+"'");
           
           
           // Position type
           UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
   }
}
