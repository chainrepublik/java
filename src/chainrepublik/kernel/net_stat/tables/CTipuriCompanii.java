package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTipuriCompanii extends CTable
{
    public CTipuriCompanii()
    {
        super("tipuri_companii");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("tipuri_companii"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE tipuri_companii (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                              + "tip VARCHAR(50) NOT NULL DEFAULT '', "
                                                              + "name VARCHAR(100) NOT NULL DEFAULT '', "
                                                              + "cladire VARCHAR(50) NOT NULL DEFAULT '', "
                                                              + "utilaje VARCHAR(50) NOT NULL DEFAULT '', "
                                                              + "pic VARCHAR(45) DEFAULT NULL)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX tipuri_companii_tip ON tipuri_companii(tip)");
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_OIL', name='Oil Mine', cladire='ID_BUILD_COM_OIL', utilaje='ID_TOOLS_PROD_OIL', pic='ID_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_ELECTRICITY', name='Power Plant', cladire='ID_BUILD_COM_ELECTRICITY', utilaje='ID_TOOLS_PROD_ELECTRICITY', pic='ID_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_GAS', name='Natural Gas Company', cladire='ID_BUILD_COM_GAS', utilaje='ID_TOOLS_PROD_GAS', pic='ID_GAS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_WOOD', name='Wood Company', cladire='ID_BUILD_COM_WOOD', utilaje='ID_TOOLS_PROD_WOOD', pic='ID_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_COTTON', name='Cotton Company', cladire='ID_BUILD_COM_COTTON', utilaje='ID_TOOLS_PROD_COTTON', pic='ID_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_MATERIAL', name='Material Company', cladire='ID_BUILD_COM_MATERIAL', utilaje='ID_TOOLS_PROD_MATERIAL', pic='ID_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_PAPER', name='Paper Company', cladire='ID_BUILD_COM_PAPER', utilaje='ID_TOOLS_PROD_PAPER', pic='ID_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_FARM', name='Farm', cladire='ID_BUILD_COM_FARM', utilaje='ID_TOOLS_PROD_FARM', pic='ID_EGG' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CLOTHES', name='Clothes Company', cladire='ID_BUILD_COM_CLOTHES', utilaje='ID_TOOLS_PROD_CLOTHES', pic='ID_GHETE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_DYNAMITE', name='Dynamite Company', cladire='ID_BUILD_COM_DYNAMITE', utilaje='ID_TOOLS_PROD_DYNAMITE', pic='ID_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_GUNPOWDER', name='Gunpowder Company', cladire='ID_BUILD_COM_GUNPOWDER', utilaje='ID_TOOLS_PROD_GUNPOWDER', pic='ID_IRON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_IRON', name='Iron Mine', cladire='ID_BUILD_COM_IRON_MINE', utilaje='ID_TOOLS_PROD_IRON', pic='ID_IRON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_PRECIOUS_METALS', name='Precious Metals Company', cladire='ID_BUILD_COM_PRECIOUS_METALS', utilaje='ID_TOOLS_PROD_PRECIOUS_METALS', pic='ID_GOLD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_TUTUN', name='Tobacco Comapny', cladire='ID_BUILD_COM_TUTUN', utilaje='ID_TOOLS_PROD_TUTUN', pic='ID_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CIGARS', name='Cigars Company', cladire='ID_BUILD_COM_CIGARS', utilaje='ID_TOOLS_PROD_CIGARS', pic='ID_CIG_TORO' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_SUGARCANE', name='Sugarcane Plantation', cladire='ID_BUILD_COM_SUGARCANE', utilaje='ID_TOOLS_PROD_SUGARCANE', pic='ID_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_ALCOHOOL', name='Alcohool Factory', cladire='ID_BUILD_COM_ALCOHOOL', utilaje='ID_TOOLS_PROD_ALCOHOOL', pic='ID_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_SUGAR', name='Sugar Factory', cladire='ID_BUILD_COM_SUGAR', utilaje='ID_TOOLS_PROD_SUGAR', pic='ID_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_VEGETABLES', name='Vegetables & Fruits Garden', cladire='ID_BUILD_COM_VEGS', utilaje='ID_TOOLS_PROD_VEGS', pic='ID_OLIVES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_BAR', name='Bar', cladire='ID_BUILD_COM_BAR', utilaje='ID_TOOLS_PROD_BAR', pic='ID_BAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_WHEAT', name='Wheat Company', cladire='ID_BUILD_COM_WHEAT', utilaje='ID_TOOLS_PROD_WHEAT', pic='ID_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_FLOUR', name='Flour Company', cladire='ID_BUILD_COM_FLOUR', utilaje='ID_TOOLS_PROD_FLOUR', pic='ID_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_BREAD', name='Bakery', cladire='ID_BUILD_COM_BAKERY', utilaje='ID_TOOLS_PROD_BAKERY', pic='ID_BREAD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_RESTAURANT', name='Restaurant', cladire='ID_BUILD_COM_RESTAURANT', utilaje='ID_TOOLS_PROD_RESTAURANT', pic='ID_BURGER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_JEWELRY', name='Jewelry', cladire='ID_BUILD_COM_JEWELRY', utilaje='ID_TOOLS_PROD_JEWELRY', pic='ID_INEL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_SAND', name='Sand Company', cladire='ID_BUILD_COM_SAND', utilaje='ID_TOOLS_PROD_SAND', pic='ID_SAND' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_GLASS', name='Glass Production Company', cladire='ID_BUILD_COM_GLASS', utilaje='ID_TOOLS_PROD_GLASS', pic='ID_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_PLASTICS', name='Plastic Production Company', cladire='ID_BUILD_COM_PLASTIC', utilaje='ID_TOOLS_PROD_PLASTIC', pic='ID_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CLAY', name='Claypit', cladire='ID_BUILD_COM_CLAY', utilaje='ID_TOOLS_PROD_CLAY', pic='ID_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_BRICKS', name='Bricks Production Company', cladire='ID_BUILD_COM_BRICKS', utilaje='ID_TOOLS_PROD_BRICKS', pic='ID_BRICK' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CEMENT', name='Cement Production Company', cladire='ID_BUILD_COM_CEMENT', utilaje='ID_TOOLS_PROD_CEMENT', pic='ID_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CARS', name='Cars Factory', cladire='ID_BUILD_COM_CARS', utilaje='ID_TOOLS_PROD_CARS', pic='ID_CAR_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_CONSTRUCTION', name='Construction Company', cladire='ID_BUILD_COM_CONSTRUCTION', utilaje='ID_TOOLS_PROD_CONSTRUCTION', pic='ID_HOUSE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_GRAPES', name='Vineyard', cladire='ID_BUILD_COM_GRAPES', utilaje='ID_TOOLS_PROD_GRAPES', pic='ID_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_BOTTLES', name='Bottles Company', cladire='ID_BUILD_COM_BOTTLES', utilaje='ID_TOOLS_PROD_BOTTLES', pic='ID_BOTTLE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_WINE', name='Winery', cladire='ID_BUILD_COM_WINE', utilaje='ID_TOOLS_PROD_WINE', pic='ID_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_TRAVEL_TICKETS', name='Travel Tickets', cladire='ID_BUILD_COM_TRAVEL_TICKETS', utilaje='ID_TOOLS_PROD_TRAVEL_TICKETS', pic='ID_TRAVEL_TICKET' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_SMALL_WEAPONS', name='Small Weapons Company', cladire='ID_BUILD_COM_SMALL_WEAPONS', utilaje='ID_TOOLS_PROD_SMALL_WEAPONS', pic='ID_PISTOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_AUTONOMOUS', name='Autonomous Corporation', cladire='ID_BUILD_COM_AUTONOMOUS', utilaje='ID_TOOLS_PROD_AUTONOMOUS', pic='ID_CHIP' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_GIFT', name='Gifts Company', cladire='ID_BUILD_COM_GIFTS', utilaje='ID_TOOLS_PROD_GIFTS', pic='ID_GIFT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_TOOLS', name='Tools Production Company', cladire='ID_BUILD_COM_TOOLS', utilaje='ID_TOOLS_PROD_TOOLS', pic='ID_TOOLS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_companii SET tip='ID_COM_HEAVY_WEAPONS', name='Heavy Weapons and Ammunition Company', cladire='ID_BUILD_COM_BIG_WEAPONS', utilaje='ID_TOOLS_PROD_BIG_WEAPONS', pic='ID_TANK' ");
}
}