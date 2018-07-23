package chainrepublik.kernel;

import java.util.ArrayList;

public class CAddressess  implements java.io.Serializable 
{
    // Addresses
    public ArrayList<CAdr> adr=new ArrayList<CAdr>();
    
    public void add(CAdr adr)
    {
        this.adr.add(adr);
    }
}
