package chainrepublik.agents.VM;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CVar 
{
    // Name
    public String name="";
    
    // String val
    public String s_val="";
    
    // Double value
    public String[] array=null;
    
    // ResultSet
    public ResultSet rs=null;
    
    // Type
    public String type;

    // Init
    public CVar(String name, String val) throws Exception
    {
        // Name
        this.name=name;
        
        // Assign value
        this.s_val=val;
        
        // Type
        this.type="ID_STRING";
    }
    
    // Init
    public CVar(String name, CVar var) throws Exception
    {
        // Name
        this.name=var.name;
        
        // Assign value
        this.s_val=var.s_val;
        
        // Type
        this.type=var.type;
    }
    
    // Set string
    public void set(String val, int pos) throws Exception
    {
        // Set
        if (pos==0)
        {
           // Set string value
           this.s_val=val;
           
           // Set array to null
           this.array=null;
           
           // Set type as string
           this.type="ID_STRING";
        }
        else
        {
            // Set string value
            this.s_val="";
            
            // Create array
            this.array=new String[1000];
            
            // Set array to null
            this.array[pos]=val;
            
            // Set type as string
            this.type="ID_ARAY";
        }
    }
    
    
    public String val(int pos) throws Exception
    {
        if (this.type.equals("ID_STRING"))
            return this.s_val;
        
        else if (this.type.equals("ID_ARRAY"))
            return this.array[pos];
        
        return "";
    }
    
    public String val(String col) throws Exception
    {
        if (this.type.equals("ID_RS"))
          if (this.rs!=null)
            return this.rs.getString(col);
        
        return "";
    }
    
    public boolean next() throws Exception
    {
        if (this.type.equals("ID_RS"))
            if (this.rs!=null)
              return this.rs.next();
        
        return false;
    }
}
