package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTipuriLicente extends CTable
{
    public CTipuriLicente()
    {
        super("tipuri_licente");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("tipuri_licente"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE tipuri_licente (ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                                                              + "tip VARCHAR(50) NOT NULL DEFAULT '', "
                                                              + "name VARCHAR(100) NOT NULL DEFAULT '', "
                                                              + "com_type VARCHAR(50) NOT NULL DEFAULT '', "
                                                              + "price DOUBLE(20,4) NOT NULL DEFAULT '0.01', "
                                                              + "prod VARCHAR(50) NOT NULL DEFAULT '')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX tipuri_licente_tip ON tipuri_licente(tip)");
            UTILS.DB.executeUpdate("CREATE INDEX tipuri_licente_com_type ON tipuri_licente(com_type)");
            UTILS.DB.executeUpdate("CREATE INDEX tipuri_licente_prod ON tipuri_licente(prod)");
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CIG_CHURCHILL', name='Churchill Cigar Production Licence', com_type='ID_COM_CIGARS', price='0.01', prod='ID_CIG_CHURCHILL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CIG_PANATELA', name='Panatela Cigar Production Licence', com_type='ID_COM_CIGARS', price='0.01', prod='ID_CIG_PANATELA' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SOSETE_Q1', name='Socks Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_SOSETE_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SOSETE_Q2', name='Socks Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_SOSETE_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SOSETE_Q3', name='Socks High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_SOSETE_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GHETE_Q1', name='Boots Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_GHETE_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_LEATHER', name='Leather Production Licence', com_type='ID_COM_FARM', price='0.01', prod='ID_LEATHER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CIG_TORPEDO', name='Torpedo Cigar Production Licence', com_type='ID_COM_CIGARS', price='0.01', prod='ID_CIG_TORPEDO' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CIG_CORONA', name='Corona Cigar Production Licence', com_type='ID_COM_CIGARS', price='0.01', prod='ID_CIG_CORONA' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CIG_TORO', name='Toro Cigar Production Licence', com_type='ID_COM_CIGARS', price='0.01', prod='ID_CIG_TORO' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_ALCOHOOL', name='Alcohool Production Licence', com_type='ID_COM_ALCOHOOL', price='0.01', prod='ID_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_OLIVES', name='Olives Production Licence', com_type='ID_COM_VEGETABLES', price='0.01', prod='ID_OLIVES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_LIME', name='Limes Production Licence', com_type='ID_COM_VEGETABLES', price='0.01', prod='ID_LIME' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SAMPANIE', name='Champagne Glass  Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_SAMPANIE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MARTINI', name='Martini Glass Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_MARTINI' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MOJITO', name='Mojito Glass Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_MOJITO' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOMATO', name='Tomato Production Licence', com_type='ID_COM_VEGETABLES', price='0.01', prod='ID_TOMATO' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MARY', name='Bloody Mary Glass Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_MARY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PINEAPPLE', name='Pineaple Production Licence', com_type='ID_COM_VEGETABLES', price='0.01', prod='ID_PINEAPPLE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_COCONUT', name='Coconuts Production Licence', com_type='ID_COM_VEGETABLES', price='0.01', prod='ID_COCONUT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SINGAPORE', name='Singapore Sling Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_SINGAPORE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PINA', name='Pina Colada Glass Production Licence', com_type='ID_COM_BAR', price='0.01', prod='ID_PINA' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_EGG', name='Egg Production Licence', com_type='ID_COM_FARM', price='0.01', prod='ID_EGG' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MILK', name='Milk Production Licence', com_type='ID_COM_FARM', price='0.01', prod='ID_MILK' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CROISANT', name='Croissant Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_CROISANT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MEAT', name='Meat Production Licence', com_type='ID_COM_FARM', price='0.01', prod='ID_MEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_HOT_DOG', name='Hot Dog Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_HOT_DOG' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PASTA', name='Pasta Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_PASTA' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BURGER', name='Burger Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_BURGER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BIG_BURGER', name='Big Burger Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_BIG_BURGER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PIZZA', name='Pizza Production Licence', com_type='ID_COM_RESTAURANT', price='0.01', prod='ID_PIZZA' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GHETE_Q2', name='Boots Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_GHETE_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GHETE_Q3', name='Boots High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_GHETE_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PANTALONI_Q1', name='Pants Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PANTALONI_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PANTALONI_Q2', name='Pants Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PANTALONI_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PANTALONI_Q3', name='Pants High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PANTALONI_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PULOVER_Q1', name='Sweater Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PULOVER_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PULOVER_Q2', name='Sweater Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PULOVER_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PULOVER_Q3', name='Sweater High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PULOVER_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PALTON_Q1', name='Coat Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PALTON_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PALTON_Q2', name='Coat Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PALTON_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PALTON_Q3', name='Coat High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_PALTON_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SILVER', name='Silver Production Licence', com_type='ID_COM_PRECIOUS_METALS', price='0.01', prod='ID_SILVER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GOLD', name='Gold Production Licence', com_type='ID_COM_PRECIOUS_METALS', price='0.01', prod='ID_GOLD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PLATINUM', name='Platinum Production Licence', com_type='ID_COM_PRECIOUS_METALS', price='0.01', prod='ID_PLATINUM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SILVER_RING', name='Silver Ring Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_SILVER_RING' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GOLD_RING', name='Gold Ring Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_GOLD_RING' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PLATINUM_RING', name='Platinum Ring Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_PLATINUM_RING' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CERCEI_Q1', name='Silver Earings Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CERCEI_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CERCEI_Q2', name='Gold Earings Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CERCEI_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CERCEI_Q3', name='Platinum Earings Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CERCEI_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_COLIER_Q1', name='Silver Pandant Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_COLIER_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_COLIER_Q2', name='Gold Pandant Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_COLIER_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_COLIER_Q3', name='Platinum Pandant Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_COLIER_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CEAS_Q1', name='Silver Watch Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CEAS_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CEAS_Q2', name='Gold Watch Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CEAS_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CEAS_Q3', name='Platinum Watch Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_CEAS_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BRATARA_Q1', name='Silver Bracelet Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_BRATARA_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BRATARA_Q2', name='Gold Bracelet Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_BRATARA_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BRATARA_Q3', name='Platinum Bracelet Production Licence', com_type='ID_COM_JEWELRY', price='0.01', prod='ID_BRATARA_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAR_Q1', name='Low Quality Car Production Licence', com_type='ID_COM_CARS', price='0.01', prod='ID_CAR_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAR_Q2', name='Medium Quality Car Production Licence', com_type='ID_COM_CARS', price='0.01', prod='ID_CAR_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAR_Q3', name='High Quality Car Production Licence', com_type='ID_COM_CARS', price='0.01', prod='ID_CAR_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CLAY', name='Clay Production Licence', com_type='ID_COM_CLAY', price='0.01', prod='ID_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CEMENT', name='Cement Production Licence', com_type='ID_COM_CEMENT', price='0.01', prod='ID_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_HOUSE_Q1', name='Small Condo Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_HOUSE_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_HOUSE_Q2', name='Two Bedroom Apartment Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_HOUSE_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_HOUSE_Q3', name='Three Bedrooms Apartment Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_HOUSE_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TRAVEL_TICKET_Q1', name='Travel Ticket Very Short Distance Production Licence', com_type='ID_COM_TRAVEL_TICKETS', price='0.01', prod='ID_TRAVEL_TICKET_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TRAVEL_TICKET_Q2', name='Travel Ticket Low Distance Production Licence', com_type='ID_COM_TRAVEL_TICKETS', price='0.01', prod='ID_TRAVEL_TICKET_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TRAVEL_TICKET_Q3', name='Travel Ticket Medium Distance Production Licence', com_type='ID_COM_TRAVEL_TICKETS', price='0.01', prod='ID_TRAVEL_TICKET_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TRAVEL_TICKET_Q4', name='Travel Tickets Long Distance Production Licence', com_type='ID_COM_TRAVEL_TICKETS', price='0.01', prod='ID_TRAVEL_TICKET_Q4' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TRAVEL_TICKET_Q5', name='Travel Ticket Very Long Distance Production Licence', com_type='ID_COM_TRAVEL_TICKETS', price='0.01', prod='ID_TRAVEL_TICKET_Q5' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAMASA_Q1', name='Shirt Low Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_CAMASA_Q1' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAMASA_Q2', name='Shirt Medium Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_CAMASA_Q2' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_CAMASA_Q3', name='Shirt High Quality Production Licence', com_type='ID_COM_CLOTHES', price='0.01', prod='ID_CAMASA_Q3' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_OIL', name='Oil Mine Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ELECTRICITY', name='Power Plant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WOOD', name='Wood Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_COTTON', name='Cotton Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_MATERIAL', name='Material Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PAPER', name='Paper Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FARM', name='Farm Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_FARM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLOTHES', name='Clothes Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CLOTHES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_DYNAMITE', name='Dynamite Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GUNPOWDER', name='Gunpowder Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GUNPOWDER', name='Iron Mine Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PRECIOUS_METALS', name='Precious Metals Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PRECIOUS_METALS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_TUTUN', name='Tobacco Comapny Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CIGARS', name='Cigars Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CIGARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGARCANE', name='Sugarcane Plantation Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ALCOHOOL', name='Alcohool Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGAR', name='Sugar Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_VEGS', name='Vegetables & Fruits Garden Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_VEGS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAR', name='Bar Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WHEAT', name='Wheat Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FLOUR', name='Flour Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAKERY', name='Bakery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BAKERY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_RESTAURANT', name='Restaurant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_RESTAURANT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_JEWELRY', name='Jewelry Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_JEWELRY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GLASS', name='Glass Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PLASTIC', name='Plastic Production cCompany Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLAY', name='Claypit Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BRICKS', name='Bricks Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BRICKS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CEMENT', name='Cement Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CARS', name='Cars Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CONSTRUCTION', name='Construction Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CONSTRUCTION' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GRAPES', name='Vineyard Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BOTTLES', name='Bottles Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BOTTLES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WINE', name='Winery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_OIL', name='Oil Mine Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_ELECTRICITY', name='Power Plant Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_GAS', name='Gas Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_GAS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_WOOD', name='Wood Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_COTTON', name='Cotton Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_MATERIAL', name='Material Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_PAPER', name='Paper Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_FARM', name='Farm Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_FARM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CLOTHES', name='Clothes Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CLOTHES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_DYNAMITE', name='Dynamite Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_GUNPOWDER', name='Gunpowder Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_IRON_MINE', name='Iron Mine Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_IRON_MINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_PRECIOUS_METALS', name='Precious Metals Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_PRECIOUS_METALS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_TUTUN', name='Tobacco Comapny Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CIGARS', name='Cigars Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CIGARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_SUGARCANE', name='Sugarcane Plantation Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_ALCOHOOL', name='Alcohool Factory Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_SUGAR', name='Sugar Factory Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_VEGS', name='Vegetables & Fruits Garden Production Buildin Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_VEGS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_BAR', name='Bar Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_BAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_WHEAT', name='Wheat Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_FLOUR', name='Flour Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_BAKERY', name='Bakery Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_BAKERY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_RESTAURANT', name='Restaurant Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_RESTAURANT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_JEWELRY', name='Jewelry Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_JEWELRY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_GLASS', name='Glass Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_PLASTIC', name='Plastic Company Production Buildi Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CLAY', name='Claypit Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_BRICKS', name='Bricks Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_BRICKS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CEMENT', name='Cement Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CARS', name='Cars Factory Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_CONSTRUCTION', name='Construction Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_CONSTRUCTION' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_GRAPES', name='Vineyard Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_BOTTLES', name='Bottles Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_BOTTLES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_WINE', name='Winery Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_TRAVEL_TICKETS', name='Travel Tickets Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_TRAVEL_TICKETS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_WEAPONS', name='Weapons Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_WEAPONS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BUILD_COM_AMMUNITION', name='Ammunition Company Production Building Production Licence', com_type='ID_COM_CONSTRUCTION', price='0.01', prod='ID_BUILD_COM_AMMUNITION' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PISTOL', name='Pistol Production Licence', com_type='ID_COM_WEAPONS', price='0.01', prod='ID_PISTOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLET_PISTOL', name='Pistol Bullets Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLET_PISTOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLET_REVOLVER', name='Revolver Bullets Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLET_REVOLVER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLETS_AKM', name='AKM Bullets Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLETS_AKM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLETS_SNIPER', name='Sniper Bullet Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLETS_SNIPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLETS_SHOTGUN', name='Shotgun Bullet Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLETS_SHOTGUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GRENADE', name='Hand Grenade Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_GRENADE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BULLETS_TANK', name='M829 Tank Round Production Licence', com_type='ID_COM_AMMUNITION', price='0.01', prod='ID_BULLETS_TANK' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_OIL', name='Oil Mine Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ELECTRICITY', name='Power Plant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GAS', name='Natural Gas Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GAS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WOOD', name='Wood Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_COTTON', name='Cotton Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_MATERIAL', name='Material Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PAPER', name='Paper Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FARM', name='Farm Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_FARM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLOTHES', name='Clothes Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CLOTHES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_DYNAMITE', name='Dynamite Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GUNPOWDER', name='Gunpowder Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PRECIOUS_METALS', name='Precious Metals Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PRECIOUS_METALS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_TUTUN', name='Tobacco Comapny Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CIGARS', name='Cigars Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CIGARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGARCANE', name='Sugarcane Plantation Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ALCOHOOL', name='Alcohool Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGAR', name='Sugar Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_VEGS', name='Vegetables & Fruits Garden Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_VEGS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAR', name='Bar Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WHEAT', name='Wheat Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FLOUR', name='Flour Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAKERY', name='Bakery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BAKERY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_RESTAURANT', name='Restaurant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_RESTAURANT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_JEWELRY', name='Jewelry Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_JEWELRY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SAND', name='Sand Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_SAND' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GLASS', name='Glass Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PLASTIC', name='Plastic Production cCompany Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLAY', name='Claypit Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BRICKS', name='Bricks Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BRICKS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CEMENT', name='Cement Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CARS', name='Cars Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CONSTRUCTION', name='Construction Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_CONSTRUCTION' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GRAPES', name='Vineyard Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BOTTLES', name='Bottles Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_BOTTLES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WINE', name='Winery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.01', prod='ID_TOOLS_PROD_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_ELECTRICITY', name='Electricity Production Licence', com_type='ID_COM_ELECTRICITY', price='0.1', prod='ID_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GAS', name='Natural Gas Production Licence', com_type='ID_COM_GAS', price='0.1', prod='ID_GAS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_OIL', name='Oil Production Licence', com_type='ID_COM_OIL', price='0.1', prod='ID_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_WOOD', name='Wood Production Licence', com_type='ID_COM_WOOD', price='0.1', prod='ID_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_COTTON', name='Cotton Production Licence', com_type='ID_COM_COTTON', price='0.1', prod='ID_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MATERIAL', name='Material Production Licence', com_type='ID_COM_MATERIAL', price='0.1', prod='ID_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PAPER', name='Paper Production Licence', com_type='ID_COM_PAPER', price='0.1', prod='ID_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GUNPOWDER', name='Gunpowder Production Licence', com_type='ID_COM_GUNPOWDER', price='0.1', prod='ID_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TUTUN', name='Tobacco Production Licence', com_type='ID_COM_TUTUN', price='0.1', prod='ID_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SUGARCANE', name='Sugarcane Production Licence', com_type='ID_COM_SUGARCANE', price='0.1', prod='ID_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SUGAR', name='Sugar Production Licence', com_type='ID_COM_SUGAR', price='0.1', prod='ID_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_WHEAT', name='Wheat Production Licence', com_type='ID_COM_WHEAT', price='0.1', prod='ID_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_FLOUR', name='Flour Production Licence', com_type='ID_COM_FLOUR', price='0.1', prod='ID_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BREAD', name='Bread Production Licence', com_type='ID_COM_BREAD', price='0.1', prod='ID_BREAD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_DYNAMITE', name='Dynamite Production Licence', com_type='ID_COM_DYNAMITE', price='0.1', prod='ID_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_IRON', name='Iron Production Licence', com_type='ID_COM_IRON', price='0.1', prod='ID_IRON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_SAND', name='Sand Production Licence', com_type='ID_COM_SAND', price='0.1', prod='ID_SAND' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GLASS', name='Glass Production Licence', com_type='ID_COM_GLASS', price='0.1', prod='ID_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_PLASTIC', name='Plastic Production Licence', com_type='ID_COM_PLASTICS', price='0.1', prod='ID_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BRICK', name='Bricks Production Licence', com_type='ID_COM_BRICKS', price='0.1', prod='ID_BRICK' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_GRAPES', name='Grapes Production Licence', com_type='ID_COM_GRAPES', price='0.1', prod='ID_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BOTTLE', name='Bottle Production Licence', com_type='ID_COM_BOTTLES', price='0.1', prod='ID_BOTTLE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_WINE', name='Wine Bottle Production Licence', com_type='ID_COM_WINE', price='0.1', prod='ID_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_OIL', name='Oil Mine Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_OIL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ELECTRICITY', name='Power Plant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_ELECTRICITY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GAS', name='Natural Gas Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_GAS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WOOD', name='Wood Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_WOOD' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_COTTON', name='Cotton Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_COTTON' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_MATERIAL', name='Material Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_MATERIAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PAPER', name='Paper Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_PAPER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FARM', name='Farm Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_FARM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLOTHES', name='Clothes Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CLOTHES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_DYNAMITE', name='Dynamite Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_DYNAMITE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GUNPOWDER', name='Gunpowder Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_GUNPOWDER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PRECIOUS_METALS', name='Precious Metals Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_PRECIOUS_METALS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_TUTUN', name='Tobacco Comapny Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_TUTUN' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CIGARS', name='Cigars Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CIGARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGARCANE', name='Sugarcane Plantation Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_SUGARCANE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_ALCOHOOL', name='Alcohool Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_ALCOHOOL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SUGAR', name='Sugar Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_SUGAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_VEGS', name='Vegetables & Fruits Garden Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_VEGS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAR', name='Bar Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_BAR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WHEAT', name='Wheat Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_WHEAT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_FLOUR', name='Flour Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_FLOUR' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BAKERY', name='Bakery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_BAKERY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_RESTAURANT', name='Restaurant Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_RESTAURANT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_JEWELRY', name='Jewelry Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_JEWELRY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_SAND', name='Sand Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_SAND' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GLASS', name='Glass Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_GLASS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_PLASTIC', name='Plastic Production cCompany Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_PLASTIC' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CLAY', name='Claypit Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CLAY' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BRICKS', name='Bricks Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_BRICKS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CEMENT', name='Cement Production Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CEMENT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CARS', name='Cars Factory Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CARS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_CONSTRUCTION', name='Construction Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_CONSTRUCTION' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_GRAPES', name='Vineyard Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_GRAPES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_BOTTLES', name='Bottles Company Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_BOTTLES' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TOOLS_PROD_WINE', name='Winery Production Tools Production Licence', com_type='ID_COM_TOOLS', price='0.1', prod='ID_TOOLS_PROD_WINE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_REVOLVER', name='0.357 Magnum Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_REVOLVER' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_AKM', name='AKM Assault Rifle Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_AKM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_HK416', name='HK416 Assault Rifle Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_HK416' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_500MILLS', name='500MILLS Shotgun Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_500MILLS' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_M110', name='M110 Sniper Rifle Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_M110' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_RPG', name='RPG-7 Rocket Launcher', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_RPG' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_MISSLE_HELLFIRE', name='AGM-114 Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_MISSILE_HELLFIRE' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BALLISTIC_SHORT', name='Short Range Ballistic Missile Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_MISSILE_BALLISTIC_SHORT' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BALLISTIC_MEDIUM', name='Medium Range Ballistic Missile Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_MISSILE_BALLISTIC_MEDIUM' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BALLISTIC_LONG', name='Long Range Ballistic Missile Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_MISSILE_BALLISTIC_LONG' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_BALLISTIC_INTER', name='Intercontinental Ballistic Missile Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_MISSILE_BALLISTIC_INTERCONTINENTAL' ");
        UTILS.DB.executeUpdate("INSERT INTO tipuri_licente SET tip='ID_LIC_PROD_TANK', name='M1 Abrams Tank Production Licence', com_type='ID_COM_WEAPONS', price='1.0', prod='ID_TANK' ");

    }
}