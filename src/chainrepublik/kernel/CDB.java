// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;



public class CDB 
{
	// Connection
	public Connection con;
        
        // File location
        public String fileLoc;
        
	ArrayList<Statement> cons=new ArrayList<Statement>();
        int a=0;
        
        
   public CDB() throws Exception
   {
       
	   try 
	   {
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+UTILS.SETTINGS.db_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					         UTILS.SETTINGS.db_user,
					         UTILS.SETTINGS.db_pass);
                 
            
              
  	   } 
	   catch (SQLException ex) 
	   { 
		   System.out.println(ex.getMessage());
                   System.exit(0);
	   }
	   catch (Exception ex) 
	   {
		   System.out.println(ex.getMessage());
                   System.exit(0);
  	   }
     
           this.executeUpdate("SET LOCAL TRANSACTION ISOLATION LEVEL SERIALIZABLE");
	   System.out.println("DB initialized...");
           
   }
   
   // Check connection
   public boolean checkConnection() throws Exception
   {
       try
       {
          if (this.con.isClosed())
          {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+UTILS.SETTINGS.db_name,
					         UTILS.SETTINGS.db_user,
					         UTILS.SETTINGS.db_pass);
               
              // Abort
              if (con.isClosed()) System.exit(0);
          }
          else
          {
              return true;
          }
       }
       catch (Exception ex) 
       {
            System.out.println(ex.getMessage());
            System.exit(0);
       }
       
       return false;
   }
   
   
   public ResultSet executeQuery(String query) throws Exception
   {
       if (UTILS.SETTINGS.db_debug.equals("true"))
          System.out.println(query);
           
       this.checkConnection();
        
      // Create statement
      Statement s=con.createStatement();
      
      // Execute
      ResultSet rs=s.executeQuery(query); 
      
      // Adds to pool
      cons.add(s);
          
      return rs;
   }
   
   public void clearCons() throws Exception
   {
        if (cons.size()>20000) 
        {
            for (int a=0; a<=cons.size()-10000; a++)
            {
              // Close
              Statement st=cons.get(a);
              
              // Close
              if (st!=null) st.close();
              
              // Remove
              cons.remove(a);
            }
        }    
   }
   
    // Checks if result set contains any data 
    public boolean hasData(ResultSet rs) throws Exception
    {
        if (rs==null) return false;
			
	if (!rs.isBeforeFirst())
	       return false;
	    else
	       return true;
    }
	
   public long executeUpdate(String query) throws Exception
   {
       if (UTILS.SETTINGS.db_debug.equals("true"))
          System.out.println(query);
      
      // Check connection
      this.checkConnection();
      
      // Statement
      PreparedStatement p=con.prepareStatement(query);
      
      // execute update
      long a=p.executeUpdate();
      
      // Close rs
      p.close(); 
      
     
      // Return
      return a;
   } 
   
   
   
   public void reset() throws Exception
   {
       this.executeUpdate("DROP database wallet");
       this.executeUpdate("CREATE database wallet");
       System.exit(0);
   }
   
   public void begin() throws Exception
   {
       this.executeUpdate("BEGIN");
   }
   
   public void commit() throws Exception
   {
       this.executeUpdate("COMMIT");
   }
   
   public void rollback() throws Exception
   {
       this.executeUpdate("ROLLBACK");
   }
   
   public void loadFileLoc() throws Exception
   {
       ResultSet rs=UTILS.DB.executeQuery("SHOW VARIABLES LIKE 'secure_file_priv'");
       
       if (!UTILS.DB.hasData(rs))
       {
           this.fileLoc="";
       }
       else
       {
           rs.next();
           this.fileLoc=rs.getString("Value");
       }
       
       // Print
       System.out.println("DB secure file set to "+this.fileLoc+".");
   }
   
   public long getRowsNo(ResultSet rs) throws Exception
   {
       // Go to last row
       rs.last();
       
       // Get line
       long lines=rs.getRow();
       
       // Move to first
       rs.beforeFirst();
       
       // Return
       return lines;
   }
  
}