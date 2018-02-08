package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CWebUsers extends CTable
{
    public CWebUsers()
    {
        super("web_users");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("web_users"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Web users
            UTILS.DB.executeUpdate("CREATE TABLE web_users(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                             +"adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             +"user VARCHAR(50) NOT NULL DEFAULT '', "
                                                             +"pass VARCHAR(100) NOT NULL DEFAULT '', "
                                                             +"email VARCHAR(100) NOT NULL DEFAULT '', "
                                                             +"IP VARCHAR(20) NOT NULL DEFAULT '', "
                                                             +"cou VARCHAR(20) NOT NULL DEFAULT 'US', "
                                                             +"status VARCHAR(50) NOT NULL DEFAULT '', "
                                                             +"api_key VARCHAR(100) NOT NULL DEFAULT '', "
                                                             +"ref_adr BIGINT NOT NULL DEFAULT 0, "
                                                             +"tstamp BIGINT NOT NULL DEFAULT 0, "
                                                             +"sms_tel VARCHAR(50) NOT NULL DEFAULT '', "
                                                             +"sms_code VARCHAR(25) NOT NULL DEFAULT '', "
                                                             +"sms_confirmed VARCHAR(10) NOT NULL DEFAULT '', "
                                                             +"online BIGINT NOT NULL DEFAULT 0, "
                                                             +"block BIGINT NOT NULL DEFAULT 0, "
                                                             +"unread_esc BIGINT NOT NULL DEFAULT 0, "
                                                             +"unread_events BIGINT NOT NULL DEFAULT 0, "
                                                             +"unread_mes BIGINT NOT NULL DEFAULT 0, "
                                                             +"unread_ref BIGINT NOT NULL DEFAULT 0, "
                                                             +"unread_trans BIGINT NOT NULL DEFAULT 0)");
             
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX web_users_user ON web_users(user)");
            UTILS.DB.executeUpdate("CREATE INDEX web_users_email ON web_users(email)");
            UTILS.DB.executeUpdate("CREATE INDEX web_users_tel ON web_users(sms_tel)");
            
            // Populate
            this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        // Insert root user
        UTILS.DB.executeUpdate("INSERT INTO web_users "
                                     + "SET user='root', "
                                         + "pass='"+UTILS.BASIC.hash("root")+"', "
                                         + "email='', "
                                         + "status='ID_ACTIVE', "
                                         + "tstamp='"+UTILS.BASIC.tstamp()+"'");
             
    }
}