package chainrepublik.kernel.net_stat.tables;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;


public class CEmails extends CTable
{
    public CEmails()
    {
        // Constructor
        super("emails");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table emails...");
           
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE emails (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                        + "email VARCHAR(250) NOT NULL DEFAULT '', "
                                                        + "tip VARCHAR(100) NOT NULL DEFAULT '', "
                                                        + "par_1 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                        + "par_2 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                        + "par_3 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                        + "par_4 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                        + "par_5 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                        + "priority BIGINT NOT NULL DEFAULT 0, "
                                                        + "block BIGINT NOT NULL DEFAULT 0, "
                                                        + "sended BIGINT NOT NULL DEFAULT 0)");
        
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX emails_email ON emails(email)");
            UTILS.DB.executeUpdate("CREATE INDEX emails_tip ON emails(tip)");
	    UTILS.DB.executeUpdate("CREATE INDEX emails_prority ON emails(priority)");
            UTILS.DB.executeUpdate("CREATE INDEX emails_block ON emails(block)");
            UTILS.DB.executeUpdate("CREATE INDEX emails_sended ON emails(sended)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM emails "
                                   + "WHERE block<="+(block-14400));
    }
}
