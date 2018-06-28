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
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Aden', posX='2744', posY='1354', seaID='1528889506796'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Alaska ', posX='0', posY='0', seaID='1528888962519'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Ambracian Gulf', posX='2400', posY='1020', seaID='1528889513870'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Amundsen Gulf ', posX='1050', posY='320', seaID='1528889783599'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Aqaba ', posX='0', posY='0', seaID='1528889586859'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Argolic Gulf', posX='2422', posY='1038', seaID='1528889641771'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Persian Gulf', posX='0', posY='0', seaID='1528889680243'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Bothnia', posX='0', posY='0', seaID='1528889402636'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Cadiz', posX='0', posY='0', seaID='1528889762219'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of California', posX='0', posY='0', seaID='1528889457247'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Carpentaria', posX='0', posY='0', seaID='1528889195704'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Cazones ', posX='0', posY='0', seaID='1528888903033'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Corinth', posX='0', posY='0', seaID='1528889850226'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Corryvreckan', posX='0', posY='0', seaID='1528889155718'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Davao Gulf ', posX='3670', posY='1420', seaID='1528889129708'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Exmouth Gulf', posX='3517', posY='1790', seaID='1528889491019'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of the Farallones', posX='0', posY='0', seaID='1528889709346'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Finland', posX='0', posY='0', seaID='1528888918941'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Fonseca', posX='0', posY='0', seaID='1528889004156'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Gabes', posX='0', posY='0', seaID='1528889075040'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Genoa', posX='0', posY='0', seaID='1528889665178'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Guinea ', posX='0', posY='0', seaID='1528889371419'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Hammamet', posX='0', posY='0', seaID='1528889655139'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Hauraki Gulf', posX='0', posY='0', seaID='1528889525688'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Izmir', posX='0', posY='0', seaID='1528889397861'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Khambhat', posX='0', posY='0', seaID='1528889209077'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Kusadasi', posX='0', posY='0', seaID='1528889844444'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Kutch', posX='0', posY='0', seaID='1528888996866'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Lingayen Gulf', posX='0', posY='0', seaID='1528889039685'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Lion', posX='0', posY='0', seaID='1528889027016'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Maine', posX='0', posY='0', seaID='1528889405235'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Malian Gulf', posX='0', posY='0', seaID='1528888879028'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Mannar', posX='0', posY='0', seaID='1528889748325'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Mexico', posX='0', posY='0', seaID='1528889667658'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Morbihan', posX='0', posY='0', seaID='1528889238628'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Nicoya', posX='0', posY='0', seaID='1528889339115'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Odessa', posX='0', posY='0', seaID='1528889818950'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Oman', posX='0', posY='0', seaID='1528889685016'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Oristano', posX='0', posY='0', seaID='1528889791287'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Panama', posX='0', posY='0', seaID='1528889816840'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Riga', posX='0', posY='0', seaID='1528889477586'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Roses', posX='0', posY='0', seaID='1528889326503'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Saint Lawrence', posX='0', posY='0', seaID='1528889738952'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf St Vincent', posX='0', posY='0', seaID='1528889293280'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Sidra', posX='0', posY='0', seaID='1528889858418'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Spencer Gulf', posX='0', posY='0', seaID='1528889770554'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Suez', posX='0', posY='0', seaID='1528889476719'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Thailand ', posX='0', posY='0', seaID='1528889625248'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Tonkin ', posX='0', posY='0', seaID='1528889309399'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Gulf of Tunis', posX='0', posY='0', seaID='1528889226594'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Davis Strait', posX='1600', posY='530', seaID='1528889674235'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Labrador Sea', posX='0', posY='0', seaID='1528889747746'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Nantucket Sound', posX='0', posY='0', seaID='1528889093291'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Vineyard Sound', posX='0', posY='0', seaID='1528888903177'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Narragansett Bay', posX='0', posY='0', seaID='1528889086786'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Rhode Island Sound', posX='0', posY='0', seaID='1528889755425'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Block Island Sound', posX='1350', posY='970', seaID='1528889780908'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Long Island Sound', posX='0', posY='0', seaID='1528889096200'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='New York Bay', posX='0', posY='0', seaID='1528888914805'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Jamaica Bay', posX='0', posY='0', seaID='1528889588570'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Raritan Bay', posX='0', posY='0', seaID='1528889149470'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sandy Hook Bay', posX='0', posY='0', seaID='1528889434586'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Delaware Bay', posX='1300', posY='1000', seaID='1528889001772'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Chesapeake Bay', posX='1286', posY='1020', seaID='1528889374120'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Albemarle Sound', posX='1278', posY='1057', seaID='1528888889096'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Caribbean Sea', posX='1229', posY='1342', seaID='1528888932310'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Argentine Sea', posX='1500', posY='2110', seaID='1528889513314'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Norwegian Sea', posX='0', posY='0', seaID='1528889586587'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='North Sea', posX='0', posY='0', seaID='1528888875147'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Wadden Sea', posX='0', posY='0', seaID='1528889163547'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Baltic Sea', posX='2356', posY='754', seaID='1528889686194'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='English Channel', posX='2150', posY='850', seaID='1528889640580'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Irish Sea', posX='0', posY='0', seaID='1528889793015'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Celtic Sea', posX='2064', posY='251', seaID='1528889134614'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bay of Biscay', posX='2100', posY='927', seaID='1528889098411'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Mediterranean Sea', posX='2389', posY='1072', seaID='1528888939427'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Adriatic Sea', posX='2334', posY='964', seaID='1528889327405'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Aegean Sea', posX='2440', posY='1012', seaID='1528889601157'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Alboran Sea', posX='2120', posY='1060', seaID='1528889111996'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Balearic Sea', posX='2148', posY='1000', seaID='1528889267372'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Ionian Sea', posX='0', posY='0', seaID='1528889095506'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Levantine Sea', posX='0', posY='0', seaID='1528889156214'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Libyan Sea', posX='0', posY='0', seaID='1528889554602'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Ligurian Sea', posX='0', posY='0', seaID='1528889300112'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Sardinia', posX='0', posY='0', seaID='1528889200623'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Sicily', posX='0', posY='0', seaID='1528889607898'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Marmara Sea', posX='0', posY='0', seaID='1528889176204'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Black Sea', posX='2537', posY='965', seaID='1528889713389'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Azov', posX='0', posY='0', seaID='1528889098243'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Irminger Sea', posX='0', posY='0', seaID='1528889153982'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Chukchi Sea', posX='520', posY='160', seaID='1528889635931'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='East Siberian Sea', posX='428', posY='54', seaID='1528889433980'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Laptev Sea', posX='0', posY='0', seaID='1528889296626'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Kara Sea', posX='0', posY='0', seaID='1528889538439'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Barents Sea', posX='2530', posY='307', seaID='1528889750510'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Queen Victoria Sea', posX='0', posY='0', seaID='1528889095853'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Wandel Sea', posX='0', posY='0', seaID='1528889352209'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Greenland Sea', posX='2160', posY='210', seaID='1528889438992'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Lincoln Sea ', posX='0', posY='0', seaID='1528889091397'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Baffin Bay', posX='1568', posY='380', seaID='1528889082783'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='The Northwest Passages', posX='0', posY='0', seaID='1528889178049'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Hudson Strait', posX='0', posY='0', seaID='1528889626521'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Beaufort Sea', posX='870', posY='190', seaID='1528889028672'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bass Strait', posX='3841', posY='2080', seaID='1528889634360'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bellingshausen Sea', posX='2000', posY='2593', seaID='1528889219995'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Drake Passage', posX='1500', posY='2340', seaID='1528889441366'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Great Australian Bight', posX='3680', posY='1950', seaID='1528889631354'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Investigator Strait', posX='0', posY='0', seaID='1528889154888'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Lazarev Sea', posX='0', posY='0', seaID='1528889004841'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Mawson Sea', posX='0', posY='0', seaID='1528889514405'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Riiser-Larsen Sea', posX='0', posY='0', seaID='1528889272823'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Ross Sea', posX='0', posY='0', seaID='1528889449692'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Scotia Sea', posX='0', posY='0', seaID='1528889429503'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Somov Sea', posX='0', posY='0', seaID='1528889563874'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Weddell Sea', posX='0', posY='0', seaID='1528889124141'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Andaman Sea', posX='3300', posY='1380', seaID='1528889581805'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Arabian Sea', posX='2920', posY='1273', seaID='1528889636358'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bay of Bengal', posX='3200', posY='1350', seaID='1528889732299'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Laccadive Sea', posX='0', posY='0', seaID='1528889198828'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Mozambique Channel', posX='0', posY='0', seaID='1528889239425'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Red Sea', posX='0', posY='0', seaID='1528889059428'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Timor Sea', posX='0', posY='0', seaID='1528889307360'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bering Sea', posX='224', posY='521', seaID='1528889441730'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Chiloe', posX='0', posY='0', seaID='1528889636761'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Mar de Grau', posX='0', posY='0', seaID='1528889097571'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Salish Sea', posX='0', posY='0', seaID='1528889614523'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Arafura Sea', posX='3820', posY='1627', seaID='1528889155916'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bali Sea', posX='3547', posY='1602', seaID='1528889696078'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Banda Sea', posX='3700', posY='1575', seaID='1528888886432'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bismarck Sea', posX='3945', posY='1558', seaID='1528889281499'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Bohol Sea ', posX='3648', posY='1400', seaID='1528889472917'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Celebes Sea', posX='3821', posY='1471', seaID='1528889491184'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Ceram Sea', posX='3732', posY='1550', seaID='1528889094895'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Coral Sea', posX='3980', posY='1680', seaID='1528889336751'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='East China Sea', posX='3620', posY='1110', seaID='1528889405765'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Flores Sea', posX='3600', posY='1600', seaID='1528889343942'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Halmahera Sea', posX='0', posY='0', seaID='1528889197174'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Java Sea', posX='0', posY='0', seaID='1528889238373'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Koro Sea', posX='0', posY='0', seaID='1528889551153'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Molucca Sea', posX='0', posY='0', seaID='1528889185806'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Philippine Sea', posX='0', posY='0', seaID='1528889707085'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Savu Sea', posX='0', posY='0', seaID='1528888866429'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Japan', posX='0', posY='0', seaID='1528888872377'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sea of Okhotsk', posX='0', posY='0', seaID='1528889418653'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Seto Inland Sea', posX='0', posY='0', seaID='1528888936993'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sibuyan Sea', posX='0', posY='0', seaID='1528889480496'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Solomon Sea', posX='0', posY='0', seaID='1528889325983'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='South China Sea', posX='0', posY='0', seaID='1528888894669'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Sulu Sea', posX='0', posY='0', seaID='1528889200886'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Tasman Sea', posX='0', posY='0', seaID='1528889436685'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Visayan Sea', posX='0', posY='0', seaID='1528889143314'");
        UTILS.DB.executeUpdate("INSERT INT seas SET name='Yellow Sea', posX='0', posY='0', seaID='1528889440306'");

    }
}
