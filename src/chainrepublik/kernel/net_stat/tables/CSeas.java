package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CSeas extends CTable
{
    public CSeas()
    {
        super("seas");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
                    UTILS.DB.executeUpdate("CREATE TABLE seas(ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                           + "name VARCHAR(100) NOT NULL DEFAULT '', "
                                                           + "posX BIGINT(20) NOT NULL DEFAULT 0, "
                                                           + "posY BIGINT(20) NOT NULL DEFAULT 0, "
                                                           + "seaID BIGINT(20) NOT NULL DEFAULT 0)");
	    
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX seas_seaID ON seas(seaID)");
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Aden', posX='0', posY='0', seaID='1528889506796'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Alaska ', posX='0', posY='0', seaID='1528888962519'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ambracian Gulf', posX='0', posY='0', seaID='1528889513870'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Amundsen Gulf ', posX='0', posY='0', seaID='1528889783599'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Aqaba ', posX='0', posY='0', seaID='1528889586859'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Argolic Gulf', posX='0', posY='0', seaID='1528889641771'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Persian Gulf', posX='0', posY='0', seaID='1528889680243'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Bothnia', posX='0', posY='0', seaID='1528889402636'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Cádiz', posX='0', posY='0', seaID='1528889762219'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of California', posX='0', posY='0', seaID='1528889457247'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Carpentaria', posX='0', posY='0', seaID='1528889195704'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Cazones ', posX='0', posY='0', seaID='1528888903033'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Corinth', posX='0', posY='0', seaID='1528889850226'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Corryvreckan', posX='0', posY='0', seaID='1528889155718'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Davao Gulf ', posX='0', posY='0', seaID='1528889129708'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Exmouth Gulf', posX='0', posY='0', seaID='1528889491019'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of the Farallones', posX='0', posY='0', seaID='1528889709346'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Finland', posX='0', posY='0', seaID='1528888918941'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Fonseca', posX='0', posY='0', seaID='1528889004156'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Gabès', posX='0', posY='0', seaID='1528889075040'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Genoa', posX='0', posY='0', seaID='1528889665178'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Guinea ', posX='0', posY='0', seaID='1528889371419'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Hammamet', posX='0', posY='0', seaID='1528889655139'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Hauraki Gulf', posX='0', posY='0', seaID='1528889525688'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Izmir', posX='0', posY='0', seaID='1528889397861'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Khambhat', posX='0', posY='0', seaID='1528889209077'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Kusadasi', posX='0', posY='0', seaID='1528889844444'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Kutch', posX='0', posY='0', seaID='1528888996866'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Lingayen Gulf', posX='0', posY='0', seaID='1528889039685'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Lion', posX='0', posY='0', seaID='1528889027016'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Maine', posX='0', posY='0', seaID='1528889405235'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Malian Gulf', posX='0', posY='0', seaID='1528888879028'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Mannar', posX='0', posY='0', seaID='1528889748325'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Mexico', posX='0', posY='0', seaID='1528889667658'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Morbihan', posX='0', posY='0', seaID='1528889238628'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Nicoya', posX='0', posY='0', seaID='1528889339115'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Odessa', posX='0', posY='0', seaID='1528889818950'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Oman', posX='0', posY='0', seaID='1528889685016'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Oristano', posX='0', posY='0', seaID='1528889791287'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Panama', posX='0', posY='0', seaID='1528889816840'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Riga', posX='0', posY='0', seaID='1528889477586'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Roses', posX='0', posY='0', seaID='1528889326503'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Saint Lawrence', posX='0', posY='0', seaID='1528889738952'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf St Vincent', posX='0', posY='0', seaID='1528889293280'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Sidra', posX='0', posY='0', seaID='1528889858418'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Spencer Gulf', posX='0', posY='0', seaID='1528889770554'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Suez', posX='0', posY='0', seaID='1528889476719'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Thailand ', posX='0', posY='0', seaID='1528889625248'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Tonkin ', posX='0', posY='0', seaID='1528889309399'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Tunis', posX='0', posY='0', seaID='1528889226594'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Davis Strait', posX='0', posY='0', seaID='1528889674235'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Labrador Sea', posX='0', posY='0', seaID='1528889747746'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Nantucket Sound', posX='0', posY='0', seaID='1528889093291'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Vineyard Sound', posX='0', posY='0', seaID='1528888903177'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Buzzards Bay', posX='0', posY='0', seaID='1528889592263'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Narragansett Bay', posX='0', posY='0', seaID='1528889086786'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Rhode Island Sound', posX='0', posY='0', seaID='1528889755425'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Block Island Sound', posX='0', posY='0', seaID='1528889780908'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Fishers Island Sound', posX='0', posY='0', seaID='1528889077022'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Long Island Sound', posX='0', posY='0', seaID='1528889096200'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='New York Bay', posX='0', posY='0', seaID='1528888914805'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Jamaica Bay', posX='0', posY='0', seaID='1528889588570'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Raritan Bay', posX='0', posY='0', seaID='1528889149470'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sandy Hook Bay', posX='0', posY='0', seaID='1528889434586'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Delaware Bay', posX='0', posY='0', seaID='1528889001772'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Chesapeake Bay', posX='0', posY='0', seaID='1528889374120'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Albemarle Sound', posX='0', posY='0', seaID='1528888889096'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Caribbean Sea', posX='0', posY='0', seaID='1528888932310'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Argentine Sea', posX='0', posY='0', seaID='1528889513314'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Norwegian Sea', posX='0', posY='0', seaID='1528889586587'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='North Sea', posX='0', posY='0', seaID='1528888875147'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Wadden Sea', posX='0', posY='0', seaID='1528889163547'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Baltic Sea', posX='0', posY='0', seaID='1528889686194'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='English Channel', posX='0', posY='0', seaID='1528889640580'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Irish Sea', posX='0', posY='0', seaID='1528889793015'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Celtic Sea', posX='0', posY='0', seaID='1528889134614'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bay of Biscay', posX='0', posY='0', seaID='1528889098411'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Cantabrian Sea', posX='0', posY='0', seaID='1528889522036'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mediterranean Sea', posX='0', posY='0', seaID='1528888939427'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Adriatic Sea', posX='0', posY='0', seaID='1528889327405'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Aegean Sea', posX='0', posY='0', seaID='1528889601157'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Alboran Sea', posX='0', posY='0', seaID='1528889111996'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Balearic Sea', posX='0', posY='0', seaID='1528889267372'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ionian Sea', posX='0', posY='0', seaID='1528889095506'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Levantine Sea', posX='0', posY='0', seaID='1528889156214'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Libyan Sea', posX='0', posY='0', seaID='1528889554602'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ligurian Sea', posX='0', posY='0', seaID='1528889300112'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Sardinia', posX='0', posY='0', seaID='1528889200623'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Sicily', posX='0', posY='0', seaID='1528889607898'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Marmara Sea', posX='0', posY='0', seaID='1528889176204'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Black Sea', posX='0', posY='0', seaID='1528889713389'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Azov', posX='0', posY='0', seaID='1528889098243'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Irminger Sea', posX='0', posY='0', seaID='1528889153982'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Denmark Strait ', posX='0', posY='0', seaID='1528889174320'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Chukchi Sea', posX='0', posY='0', seaID='1528889635931'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='East Siberian Sea', posX='0', posY='0', seaID='1528889433980'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Laptev Sea', posX='0', posY='0', seaID='1528889296626'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Kara Sea', posX='0', posY='0', seaID='1528889538439'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Barents Sea', posX='0', posY='0', seaID='1528889750510'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Queen Victoria Sea', posX='0', posY='0', seaID='1528889095853'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Wandel Sea', posX='0', posY='0', seaID='1528889352209'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Greenland Sea', posX='0', posY='0', seaID='1528889438992'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Lincoln Sea ', posX='0', posY='0', seaID='1528889091397'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Baffin Bay', posX='0', posY='0', seaID='1528889082783'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='The Northwest Passages', posX='0', posY='0', seaID='1528889178049'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Hudson Strait', posX='0', posY='0', seaID='1528889626521'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Beaufort Sea', posX='0', posY='0', seaID='1528889028672'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Amundsen Sea', posX='0', posY='0', seaID='1528889395877'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bass Strait', posX='0', posY='0', seaID='1528889634360'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bellingshausen Sea', posX='0', posY='0', seaID='1528889219995'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Davis Sea', posX='0', posY='0', seaID='1528889305901'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='DUrville Sea', posX='0', posY='0', seaID='1528889136615'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Drake Passage', posX='0', posY='0', seaID='1528889441366'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Great Australian Bight', posX='0', posY='0', seaID='1528889631354'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Investigator Strait', posX='0', posY='0', seaID='1528889154888'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Lazarev Sea', posX='0', posY='0', seaID='1528889004841'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mawson Sea', posX='0', posY='0', seaID='1528889514405'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Riiser-Larsen Sea', posX='0', posY='0', seaID='1528889272823'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ross Sea', posX='0', posY='0', seaID='1528889449692'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Scotia Sea', posX='0', posY='0', seaID='1528889429503'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Somov Sea', posX='0', posY='0', seaID='1528889563874'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Weddell Sea', posX='0', posY='0', seaID='1528889124141'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Andaman Sea', posX='0', posY='0', seaID='1528889581805'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Arabian Sea', posX='0', posY='0', seaID='1528889636358'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bay of Bengal', posX='0', posY='0', seaID='1528889732299'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Laccadive Sea', posX='0', posY='0', seaID='1528889198828'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mozambique Channel', posX='0', posY='0', seaID='1528889239425'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Red Sea', posX='0', posY='0', seaID='1528889059428'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Timor Sea', posX='0', posY='0', seaID='1528889307360'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bering Sea', posX='0', posY='0', seaID='1528889441730'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Chilean Sea', posX='0', posY='0', seaID='1528889555892'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Chiloé', posX='0', posY='0', seaID='1528889636761'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mar de Grau', posX='0', posY='0', seaID='1528889097571'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Salish Sea', posX='0', posY='0', seaID='1528889614523'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Arafura Sea', posX='0', posY='0', seaID='1528889155916'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bali Sea', posX='0', posY='0', seaID='1528889696078'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Banda Sea', posX='0', posY='0', seaID='1528888886432'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bismarck Sea', posX='0', posY='0', seaID='1528889281499'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bohol Sea ', posX='0', posY='0', seaID='1528889472917'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Camotes Sea', posX='0', posY='0', seaID='1528889332215'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Celebes Sea', posX='0', posY='0', seaID='1528889491184'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ceram Sea', posX='0', posY='0', seaID='1528889094895'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Coral Sea', posX='0', posY='0', seaID='1528889336751'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='East China Sea', posX='0', posY='0', seaID='1528889405765'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Flores Sea', posX='0', posY='0', seaID='1528889343942'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Halmahera Sea', posX='0', posY='0', seaID='1528889197174'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Java Sea', posX='0', posY='0', seaID='1528889238373'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Koro Sea', posX='0', posY='0', seaID='1528889551153'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Molucca Sea', posX='0', posY='0', seaID='1528889185806'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Philippine Sea', posX='0', posY='0', seaID='1528889707085'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Savu Sea', posX='0', posY='0', seaID='1528888866429'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Japan', posX='0', posY='0', seaID='1528888872377'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Okhotsk', posX='0', posY='0', seaID='1528889418653'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Seto Inland Sea', posX='0', posY='0', seaID='1528888936993'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sibuyan Sea', posX='0', posY='0', seaID='1528889480496'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Solomon Sea', posX='0', posY='0', seaID='1528889325983'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='South China Sea', posX='0', posY='0', seaID='1528888894669'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sulu Sea', posX='0', posY='0', seaID='1528889200886'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Tasman Sea', posX='0', posY='0', seaID='1528889436685'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Visayan Sea', posX='0', posY='0', seaID='1528889143314'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Yellow Sea', posX='0', posY='0', seaID='1528889440306'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mediteranean Sea', posX='0', posY='0', seaID='1528889815628'");
    }
}
