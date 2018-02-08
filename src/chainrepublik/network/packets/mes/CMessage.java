// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.mes;


public class CMessage 
{
    // Serial
   private static final long serialVersionUID = 100L;
   
	// Subject
	public String subject;
	
	// Message
	public String mes;
	
	public CMessage(String subject, String mes) 
	{
	    // Subject
            this.subject=subject;
		
	    // Message
	    this.mes=mes;
	}
	
	public void check() throws Exception
	{
            // Check subject
            if (this.subject.length()<5 || this.subject.length()>50)
                throw new Exception("Invalid subject");
	  
            // Check message
            if (this.mes.length()<5 || this.mes.length()>50)
                throw new Exception("Invalid subject");
	}
}
