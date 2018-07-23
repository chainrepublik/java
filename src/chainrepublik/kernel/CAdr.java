package chainrepublik.kernel;

public class CAdr implements java.io.Serializable 
{
   public String pub_key;
   public String priv_key;
   public String desc;
   
   public CAdr(String pub_key, 
               String priv_key, 
               String desc)
   {
       this.pub_key=pub_key;
       this.priv_key=priv_key;
       this.desc=desc;
   }
}
