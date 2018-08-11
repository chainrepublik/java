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
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Aden', posX='2744', posY='1354', seaID='1528889506796'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Alaska ', posX='630', posY='570', seaID='1528888962519'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ambracian Gulf', posX='2400', posY='1020', seaID='1528889513870'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Amundsen Gulf ', posX='1050', posY='320', seaID='1528889783599'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Aqaba ', posX='2567', posY='1149', seaID='1528889586859'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Argolic Gulf', posX='2422', posY='1038', seaID='1528889641771'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Persian Gulf', posX='2750', posY='1160', seaID='1528889680243'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Bothnia', posX='2360', posY='642', seaID='1528889402636'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Cadiz', posX='2078', posY='1052', seaID='1528889762219'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of California', posX='830', posY='1110', seaID='1528889457247'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Carpentaria', posX='3823', posY='1700', seaID='1528889195704'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Corinth', posX='2417', posY='1028', seaID='1528889850226'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Corryvreckan', posX='2100', posY='747', seaID='1528889155718'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Davao Gulf ', posX='3670', posY='1420', seaID='1528889129708'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Exmouth Gulf', posX='3517', posY='1790', seaID='1528889491019'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of the Farallones', posX='735', posY='995', seaID='1528889709346'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Finland', posX='2442', posY='683', seaID='1528888918941'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Fonseca', posX='1110', posY='1348', seaID='1528889004156'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Gabes', posX='2300', posY='1075', seaID='1528889075040'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Genoa', posX='2260', posY='948', seaID='1528889665178'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Guinea ', posX='2180', posY='1480', seaID='1528889371419'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Hammamet', posX='2286', posY='1057', seaID='1528889655139'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Hauraki Gulf', posX='4192', posY='2054', seaID='1528889525688'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Khambhat', posX='3018', posY='1239', seaID='1528889209077'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Kutch', posX='2988', posY='1228', seaID='1528888996866'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Lingayen Gulf', posX='3594', posY='1312', seaID='1528889039685'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Maine', posX='1400', posY='937', seaID='1528889405235'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Malian Gulf', posX='2420', posY='1010', seaID='1528888879028'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Mannar', posX='3110', posY='1391', seaID='1528889748325'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Mexico', posX='1078', posY='1188', seaID='1528889667658'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Morbihan', posX='2121', posY='891', seaID='1528889238628'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Nicoya', posX='1144', posY='1389', seaID='1528889339115'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Odessa', posX='2500', posY='909', seaID='1528889818950'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Oman', posX='2858', posY='1200', seaID='1528889685016'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Oristano', posX='2255', posY='1000', seaID='1528889791287'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Panama', posX='1207', posY='1411', seaID='1528889816840'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Riga', posX='2407', posY='726', seaID='1528889477586'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Roses', posX='2200', posY='970', seaID='1528889326503'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Saint Lawrence', posX='1470', posY='880', seaID='1528889738952'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf St Vincent', posX='3777', posY='2002', seaID='1528889293280'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Sidra', posX='2380', posY='1120', seaID='1528889858418'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Spencer Gulf', posX='3759', posY='1977', seaID='1528889770554'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Suez', posX='2540', posY='1144', seaID='1528889476719'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Thailand ', posX='3375', posY='1380', seaID='1528889625248'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Tonkin ', posX='3445', posY='1258', seaID='1528889309399'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Gulf of Tunis', posX='2280', posY='1044', seaID='1528889226594'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Davis Strait', posX='1600', posY='530', seaID='1528889674235'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Labrador Sea', posX='1600', posY='678', seaID='1528889747746'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Block Island Sound', posX='1350', posY='970', seaID='1528889780908'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Jamaica Bay', posX='1328', posY='977', seaID='1528889588570'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Delaware Bay', posX='1300', posY='1000', seaID='1528889001772'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Chesapeake Bay', posX='1286', posY='1020', seaID='1528889374120'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Albemarle Sound', posX='1278', posY='1057', seaID='1528888889096'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Caribbean Sea', posX='1229', posY='1342', seaID='1528888932310'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Argentine Sea', posX='1500', posY='2110', seaID='1528889513314'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Norwegian Sea', posX='2140', posY='560', seaID='1528889586587'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='North Sea', posX='2190', posY='750', seaID='1528888875147'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Wadden Sea', posX='2238', posY='794', seaID='1528889163547'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Baltic Sea', posX='2356', posY='754', seaID='1528889686194'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='English Channel', posX='2150', posY='850', seaID='1528889640580'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Irish Sea', posX='2100', posY='800', seaID='1528889793015'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Celtic Sea', posX='2064', posY='251', seaID='1528889134614'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bay of Biscay', posX='2100', posY='927', seaID='1528889098411'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mediterranean Sea', posX='2389', posY='1072', seaID='1528888939427'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Adriatic Sea', posX='2334', posY='964', seaID='1528889327405'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Aegean Sea', posX='2440', posY='1012', seaID='1528889601157'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Alboran Sea', posX='2120', posY='1060', seaID='1528889111996'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Balearic Sea', posX='2148', posY='1000', seaID='1528889267372'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ionian Sea', posX='2374', posY='1035', seaID='1528889095506'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ligurian Sea', posX='2262', posY='953', seaID='1528889300112'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Sardinia', posX='2222', posY='1000', seaID='1528889200623'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Marmara Sea', posX='2480', posY='990', seaID='1528889176204'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Black Sea', posX='2537', posY='965', seaID='1528889713389'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Azov', posX='2570', posY='910', seaID='1528889098243'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Chukchi Sea', posX='520', posY='160', seaID='1528889635931'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='East Siberian Sea', posX='428', posY='54', seaID='1528889433980'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Laptev Sea', posX='3225', posY='150', seaID='1528889296626'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Kara Sea', posX='2729', posY='346', seaID='1528889538439'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Barents Sea', posX='2530', posY='307', seaID='1528889750510'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Greenland Sea', posX='2160', posY='210', seaID='1528889438992'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Lincoln Sea ', posX='1820', posY='10', seaID='1528889091397'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Baffin Bay', posX='1568', posY='380', seaID='1528889082783'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Beaufort Sea', posX='870', posY='190', seaID='1528889028672'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bass Strait', posX='3841', posY='2080', seaID='1528889634360'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bellingshausen Sea', posX='2000', posY='2593', seaID='1528889219995'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Drake Passage', posX='1500', posY='2340', seaID='1528889441366'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Great Australian Bight', posX='3680', posY='1950', seaID='1528889631354'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ross Sea', posX='2880', posY='2500', seaID='1528889449692'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Andaman Sea', posX='3300', posY='1380', seaID='1528889581805'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Arabian Sea', posX='2920', posY='1273', seaID='1528889636358'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bay of Bengal', posX='3200', posY='1350', seaID='1528889732299'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Laccadive Sea', posX='3100', posY='1427', seaID='1528889198828'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Mozambique Channel', posX='2650', posY='1729', seaID='1528889239425'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Red Sea', posX='2600', posY='1232', seaID='1528889059428'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Timor Sea', posX='3676', posY='1650', seaID='1528889307360'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bering Sea', posX='224', posY='521', seaID='1528889441730'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Salish Sea', posX='795', posY='803', seaID='1528889614523'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Arafura Sea', posX='3820', posY='1627', seaID='1528889155916'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bali Sea', posX='3547', posY='1602', seaID='1528889696078'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Banda Sea', posX='3700', posY='1575', seaID='1528888886432'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bismarck Sea', posX='3945', posY='1558', seaID='1528889281499'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Bohol Sea ', posX='3648', posY='1400', seaID='1528889472917'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Celebes Sea', posX='3821', posY='1471', seaID='1528889491184'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Ceram Sea', posX='3732', posY='1550', seaID='1528889094895'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Coral Sea', posX='3980', posY='1680', seaID='1528889336751'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='East China Sea', posX='3620', posY='1110', seaID='1528889405765'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Flores Sea', posX='3600', posY='1600', seaID='1528889343942'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Halmahera Sea', posX='3708', posY='1513', seaID='1528889197174'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Java Sea', posX='3470', posY='1570', seaID='1528889238373'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Molucca Sea', posX='3660', posY='1506', seaID='1528889185806'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Philippine Sea', posX='3750', posY='1290', seaID='1528889707085'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Savu Sea', posX='3625', posY='1628', seaID='1528888866429'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Japan', posX='3700', posY='945', seaID='1528888872377'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sea of Okhotsk', posX='3762', posY='600', seaID='1528889418653'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Seto Inland Sea', posX='3728', posY='1036', seaID='1528888936993'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sibuyan Sea', posX='3633', posY='1353', seaID='1528889480496'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Solomon Sea', posX='4057', posY='1650', seaID='1528889325983'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='South China Sea', posX='3528', posY='1357', seaID='1528888894669'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Sulu Sea', posX='3600', posY='1400', seaID='1528889200886'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Tasman Sea', posX='4000', posY='2100', seaID='1528889436685'");
        UTILS.DB.executeUpdate("INSERT INTO seas SET name='Yellow Sea', posX='3595', posY='1032', seaID='1528889440306'");

    }
}
