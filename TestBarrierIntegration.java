package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class TestBarrierIntegration {
    String station_name;
    Map<Double,Integer> prices = new HashMap<>();
    Network network_valid, network_notValid;
    Station station, s_namboku_1, s_namboku_2, s_namboku_3;
    Barrier bar1, bar2, bar3;

    //Pierre
    Network n;
    String tozai;
    Station c2, t01, t02, t03, t04, t05, t06, t07, t08, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19;
    Map<Double, Integer> price;
    ITicket ticketAdulte, ticketEnfant;

    @Before     // indicates that the method should be executed before each test
    public void setup() {
        station_name  = "Ora ora ora ora";
        network_valid = new Network();
        network_notValid = new Network();
        station = new Station(station_name);
        s_namboku_1 = new Station("Asabu","Namboku Line",1,0);
        s_namboku_2 = new Station("Kita sanjuyo jo","Namboku Line",2,3.0);
        s_namboku_3 = new Station("Kita nijuyo yo","Namboku Line",3,5.0);
        network_valid.addStation(s_namboku_1);
        network_valid.addStation(s_namboku_2);
        network_valid.addStation(s_namboku_3);
        prices.put(0.0, 100);
        prices.put(3.0, 250);
        prices.put(5.0, 400);
        bar1 = Barrier.build(network_valid,s_namboku_1.getName(),prices);
        bar2 = Barrier.build(network_valid, s_namboku_2.getName(),prices);
        bar3 = Barrier.build(network_valid,s_namboku_3.getName(),prices);

        //Pierre
        n = new Network();

        tozai = "Tozai Line";

        c2 = new Station("Odori");
        t01 = new Station("Miyanosawa", tozai, 1, 0.0);
        t02 = new Station("Hassamu minami", tozai, 2, 1.5);
        t03 = new Station("Kotoni", tozai, 3, 2.8);
        t04 = new Station("Nijuyonken", tozai, 4, 3.7);
        t05 = new Station("Nishi nijuhatchome", tozai, 5, 4.9);
        t06 = new Station("Maruyama koen", tozai, 6, 5.7);
        t07 = new Station("Nishi juhatchome", tozai, 7, 6.6);
        t08 = new Station("Nishi juitchome", tozai, 8, 7.5);
        c2.addLine(tozai, 9, 8.5);
        t10 = new Station("Bus Center mae", tozai, 10, 9.3);
        t11 = new Station("Kikusui", tozai, 11, 10.4);
        t12 = new Station("Higashi Sapporo", tozai, 12, 11.6);
        t13 = new Station("Shiroishi", tozai, 13, 12.7);
        t14 = new Station("Nango nana chome", tozai, 14, 14.1);
        t15 = new Station("Nango jusan chome", tozai, 15, 15.2);
        t16 = new Station("Nango juhatchome", tozai, 16, 16.4);
        t17 = new Station("Oyachi", tozai, 17, 17.9);
        t18 = new Station("Hibarigaoka", tozai, 18, 18.9);
        t19 = new Station("Shin Sapporo", tozai, 19, 20.1);
        n.addStation(t01);n.addStation(t02);n.addStation(t03);n.addStation(t04);n.addStation(t05);n.addStation(t06);n.addStation(t07);n.addStation(t08);n.addStation(c2);n.addStation(t10);n.addStation(t11);n.addStation(t12);n.addStation(t13);n.addStation(t14);n.addStation(t15);n.addStation(t16);n.addStation(t17);n.addStation(t18);n.addStation(t19);

        price = new HashMap<>();
        price.put(0.0, 100);
        price.put(5.0, 375);
        price.put(7.5, 500);
        price.put(12.0, 700);
        price.put(15.0, 850);
        price.put(21.0, 1000);

        ticketAdulte = new BaseTicket(false, 1000);
        ticketEnfant = new BaseTicket(true, 600);
    }

    // ------------------------------------------------------------------------------- //
    // Test build(Network n, String s, Map<Double,Integer> p)

    //test des scénarios

    @Test
    public void testBarrierBuild_NetworkNull(){
        Assert.assertNull(Barrier.build(null,station_name,prices));
    }

    @Test
    public void testBarrierBuild_StationNull(){
        Assert.assertNull(Barrier.build(network_valid,null,prices));
    }

    @Test
    public void testBarrierBuild_PricesNull(){
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),null));
    }

    @Test
    public void testBarrierBuild_NetworkNotValid(){
        Assert.assertNull(Barrier.build(network_notValid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_PricesIsEmpty(){
        prices = new HashMap<>();
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_StationIsNotInNetwork(){
        Assert.assertNull(Barrier.build(network_valid,"Ora ora ora oraaaaaa",prices));
    }

    @Test
    public void testBarrierBuild_PricesNotContains0(){
        prices.remove(0.0);
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_PricesKeyNegative(){
        prices.put(-1.0, 150);
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_PricesValue0(){
        prices.put(0.5, 0);
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_PricesValueNegative(){
        prices.put(0.5, -175);
        Assert.assertNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    @Test
    public void testBarrierBuild_AllGood(){
        Assert.assertNotNull(Barrier.build(network_valid,s_namboku_1.getName(),prices));
    }

    // ------------------------------------------------------------------------------- //
    // Test enter(ITicket t)
    @Test
    public void testEnter_ticketNotValid(){
        ITicket ticket = new BaseTicket(false, 1000);
        ticket.invalidate();
        Assert.assertFalse(bar1.enter(ticket));
    }

    @Test
    public void testEnter_ticketNotEnteringInThisStation(){
        ITicket ticket = new BaseTicket(false, 1000);
        Assert.assertTrue(bar2.enter(ticket));
        Assert.assertFalse(bar1.enter(ticket));
    }

    @Test
    public void testEnter_tryingToEnterTwice(){
        ITicket ticket = new BaseTicket(false, 1000);
        Assert.assertTrue(bar1.enter(ticket));
        Assert.assertFalse(bar1.enter(ticket));
    }

    @Test
    public void testEnter_ticketAmountNull(){
        ITicket ticket = new BaseTicket(false, 0);
        Assert.assertFalse(bar1.enter(ticket));
    }

    @Test
    public void testEnter_ticketAmountNegate(){
        ITicket ticket = new BaseTicket(false, -1);
        Assert.assertFalse(bar1.enter(ticket));
    }

    @Test
    public void testEnter_ticketAllGood(){
        ITicket ticket = new BaseTicket(false, 1000);
        Assert.assertTrue(bar1.enter(ticket));
    }

    // ------------------------------------------------------------------------------- //
    // Test exit(ITicket t)

    @Test
    public void testExit_ticketNotEnoughAmountAdult_bar1(){ //scenar 1
        ITicket ticket = new BaseTicket(false, 45);
        bar1.enter(ticket);
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountAdult_bis(){ //Scenar 1 *
        ITicket ticket = new BaseTicket(false, 500);
        Station s_namboku_4 = new Station("Les Ratz","Namboku Line",4,6.3);
        network_valid.addStation(s_namboku_4);
        prices.put(7.0, 550);
        Barrier bar4 = Barrier.build(network_valid,s_namboku_4.getName(),prices);
        bar1.enter(ticket);
        Assert.assertFalse(bar4.exit(ticket));
    }

    @Test
    public void testExit_ticket_AllGood_Adult(){ //Scenar 1 Bis
        ITicket ticket = new BaseTicket(false, 1000);
        bar1.enter(ticket);
        Assert.assertTrue(bar1.exit(ticket));
    }

    @Test
    public void testExit_ticket_AllGood_Adult_bis(){ //Scenar 1 Bis *
        ITicket ticket = new BaseTicket(false, 1000);
        Station s_namboku_4 = new Station("Les Ratz","Namboku Line",4,6.3);
        network_valid.addStation(s_namboku_4);
        prices.put(7.0, 550);
        Barrier bar4 = Barrier.build(network_valid,s_namboku_4.getName(),prices);
        bar1.enter(ticket);
        Assert.assertTrue(bar4.exit(ticket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountChild_bar1(){ //scenar 2
        ITicket ticket = new BaseTicket(true, 45);
        bar1.enter(ticket);
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountChild_arrondie_dizaine_supp(){ //scenar 2
        Station s_namboku_4 = new Station("Les Ratz","Namboku Line",4,7.0);
        network_valid.addStation(s_namboku_4);
        prices.put(7.0, 550);
        Barrier bar4 = Barrier.build(network_valid,s_namboku_4.getName(),prices);
        ITicket ticket = new BaseTicket(true, 275);
        bar1.enter(ticket);
        Assert.assertFalse(bar4.exit(ticket));
    }

    @Test
    public void testExit_ticket_AllGood_Child(){ //Scenar 2 Bis
        ITicket ticket = new BaseTicket(true, 1000);
        bar1.enter(ticket);
        Assert.assertTrue(bar1.exit(ticket));
    }

    @Test
    public void testExit_ticket_AllGood_Child_arrondie_dizaine_supp(){ //Scenar 2 Bis
        Station s_namboku_4 = new Station("Les Ratz","Namboku Line",4,7.0);
        network_valid.addStation(s_namboku_4);
        prices.put(7.0, 550);
        Barrier bar4 = Barrier.build(network_valid,s_namboku_4.getName(),prices);
        ITicket ticket = new BaseTicket(true, 280);
        bar1.enter(ticket);
        Assert.assertTrue(bar4.exit(ticket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountAdult_butIsChild(){ // scenar 3
        ITicket ticket_adult = new BaseTicket(false, 50);
        bar1.enter(ticket_adult);
        Assert.assertFalse(bar1.exit(ticket_adult));
        ITicket ticket_child = new BaseTicket(true, 50);
        bar1.enter(ticket_child);
        Assert.assertTrue(bar1.exit(ticket_child));
    }

    @Test
    public void testExit_adjustedTicket_1time(){ //scenar 4
        ITicket ticket = new BaseTicket(false, 45);
        bar1.enter(ticket);
        Assert.assertFalse(bar1.exit(ticket));
        ITicket adjusted_ticket = new AdjustedTicket(ticket, 65);
        Assert.assertTrue(bar1.exit(adjusted_ticket));
    }

    @Test
    public void testExit_adjustedTicket_2time(){ //scenar 5
        ITicket ticket = new BaseTicket(false, 45);
        bar1.enter(ticket);
        Assert.assertFalse(bar1.exit(ticket));
        ITicket adjusted_ticket = new AdjustedTicket(ticket, 45);
        Assert.assertFalse(bar1.exit(adjusted_ticket));
        ITicket adjusted_ticket_2 = new AdjustedTicket(adjusted_ticket, 45);
        Assert.assertTrue(bar1.exit(adjusted_ticket_2));
    }

    @Test
    public void testExit_adjustedTicket_2time_bar2(){ //scenar 5 bar2
        ITicket ticket = new BaseTicket(false, 45);
        bar1.enter(ticket);
        Assert.assertFalse(bar2.exit(ticket));
        ITicket adjusted_ticket = new AdjustedTicket(ticket, 145);
        Assert.assertFalse(bar2.exit(adjusted_ticket));
        ITicket adjusted_ticket_2 = new AdjustedTicket(adjusted_ticket, 145);
        Assert.assertTrue(bar2.exit(adjusted_ticket_2));
    }

    @Test
    public void testExit_ticketNotValid(){ //scenar 6
        ITicket ticket = new BaseTicket(false, 1000);
        ticket.invalidate();
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_tryToExit2TimesSameBar(){ //scenar 7
        ITicket ticket = new BaseTicket(false, 1000);
        bar1.enter(ticket);
        Assert.assertTrue(bar1.exit(ticket));
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_tryToExit2TimesNotSameBar(){ //scenar 7
        ITicket ticket = new BaseTicket(false, 1000);
        bar1.enter(ticket);
        Assert.assertTrue(bar1.exit(ticket));
        Assert.assertFalse(bar3.exit(ticket));
    }

    @Test
    public void testExit_ticketNotEntered(){ //scenar 8
        ITicket ticket = new BaseTicket(false, 1000);
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_StationEnteringRemovedFromLine_SameEnterThanExit(){ //Scenario bombe nucléaire sur la station 1
        ITicket ticket = new BaseTicket(false, 1000);
        bar1.enter(ticket);
        s_namboku_1.removeLine("Namboku Line");
        Assert.assertFalse(bar1.exit(ticket));
    }

    @Test
    public void testExit_StationEnteringRemovedFromLine_NotSameEnterThanExit(){ //Scenario bombe nucléaire sur la station 3
        ITicket ticket = new BaseTicket(false, 1000);
        bar1.enter(ticket);
        s_namboku_1.removeLine("Namboku Line");
        Assert.assertFalse(bar3.exit(ticket));
    }

    /*
     * Scenar 1 : Est un adulte, station 1 > station 3 > pas assez d'argent
     * Scenar 2: Est un enfant, station 1 > 3 > pas assez d'argent
     * Scenar 1 bis : Est un adulte s1 > s2 > assez d'argent
     * Scenar 2 bis : Est un enfant s1 > s2 > assez d'argent
     * Scenar 3 : Est un enfant , station 1 > 3 pas assez d'argent pour un adulte mais assez pour enfant
     * Scenar 4: Peu importe, s1 > s3 > pas assez d'argent > MAIS ON AJOUTE DU FRIC > assez d'argent
     * Scenar 5: Pas assez d'argent pour sortir > on ajoute fric > toujours pas assez > on rajoute fric > on peut sortir
     * Scenar 6 : Ticket invalide de base
     * Scenar 7 : On essaye de sortir deux fois avec le meme ticket -> meme barrière
     * Scenar 7 bis : On essaye de sortir deux fois meais pas la meme station
     * Scenar 8 : On essaye de sortir avec un tikcet qui n'est jamais entré quelque part
     */

    // ====================================================================== //
    //Pierre
    @Test
    public void testCreateBarrierNull(){
        Assert.assertNull(Barrier.build(null, t01.getName(), price));
        Assert.assertNull(Barrier.build(n, null, price));
        Assert.assertNull(Barrier.build(n, t01.getName(), null));
    }

    @Test
    public void testCreateBarrierNetworkEmpty(){
        Network n1 = new Network();
        Assert.assertNull(Barrier.build(n1, t01.getName(), price));
    }

    @Test
    public void testCreateBarrierStationEmpty(){
        Assert.assertNull(Barrier.build(n, "Prooooojet", price));
    }

    @Test
    public void testCreateBarrierPriceEmpty(){
        Map<Double, Integer> price2= new HashMap<>();
        Assert.assertNull(Barrier.build(n, t01.getName(), price2));
    }

    @Test
    public void testCreateBarrierPriceDistanceNeg(){
        Map<Double, Integer> price2= new HashMap<>();
        price2.put(-0.1, 1000000000);
        Assert.assertNull(Barrier.build(n, t01.getName(), price2));
    }

    @Test
    public void testCreateBarrierPriceNeg(){
        Map<Double, Integer> price2= new HashMap<>();
        price2.put(0.0, -1);
        Assert.assertNull(Barrier.build(n, t01.getName(), price2));
    }

    @Test
    public void testCreateBarrierPriceFirstDistanceNotZero(){
        Map<Double, Integer> price2= new HashMap<>();
        price2.put(1.0, 10000000);
        Assert.assertNull(Barrier.build(n, t01.getName(), price2));
    }

    @Test
    public void testEnterBadTicket(){
        ITicket t = new BaseTicket(false, 10000000);
        t.invalidate();
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertFalse(b.enter(t));
    }

    @Test
    public void testEnterTwoStationsOneTicket(){
        ITicket t = new BaseTicket(false, 1000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t02.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b2.enter(t));
    }

    @Test
    public void testEnterSameStationOneTicket(){
        ITicket t = new BaseTicket(false, 1000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b.enter(t));
    }

    @Test
    public void testEnterAccountNeg(){
        ITicket t = new BaseTicket(false, -1);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertFalse(b.enter(t));
    }

    @Test
    public void testEnterAccountZero(){
        ITicket t = new BaseTicket(false, 0);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertFalse(b.enter(t));
    }

    @Test
    public void testEnterAccountGrowing(){
        ITicket t = new BaseTicket(false, 0);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertFalse(b.enter(t));
        ITicket ta = new AdjustedTicket(t, 100);
        Assert.assertFalse(b.enter(ta));
    }

    @Test
    public void testExitBadTicket(){
        ITicket t = new BaseTicket(false, 10000000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(b.enter(t));
        t.invalidate();
        Assert.assertFalse(b.exit(t));
    }

    @Test
    public void testExitUsedTicket(){
        ITicket t = new BaseTicket(false, 1000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertTrue(b.exit(t));
        Assert.assertFalse(b.enter(t));
    }

    @Test
    public void testExitNoEntryStation(){
        ITicket t = new BaseTicket(false, 1000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertFalse(b.exit(t));
    }

    @Test
    public void testExitNotEnoughMoney(){
        ITicket t = new BaseTicket(false, 100);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t17.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b2.exit(t));
    }

    @Test
    public void testExitRefuelMoney(){
        ITicket t = new BaseTicket(false, 100);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t17.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b2.exit(t));
        ITicket ta = new AdjustedTicket(t, 1000);
        Assert.assertTrue(b2.exit(ta));
    }

    @Test
    public void testReEnterWithAlreadyExitedRefuelMoney(){
        ITicket t = new BaseTicket(false, 100);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t17.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b2.exit(t));
        ITicket ta = new AdjustedTicket(t, 1000);
        Assert.assertTrue(b2.exit(ta));
        Assert.assertFalse(b2.enter(ta));
    }

    @Test
    public void testExitEnoughMoneyAsChild(){
        ITicket t = new BaseTicket(true, 50);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertTrue(b.exit(t));
    }

    @Test
    public void testExitFailDespiteRefuelMoneyAsChild(){
        ITicket t = new BaseTicket(true, 100);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t05.getName(), price);
        Assert.assertTrue(b.enter(t));
        Assert.assertFalse(b2.exit(t));
        ITicket ta = new AdjustedTicket(t, 87);
        Assert.assertFalse(b2.exit(ta));
        Assert.assertTrue(ta.isValid());
    }

    @Test
    public void testExitRemoveOriginStation(){
        ITicket t = new BaseTicket(false, 10000000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t07.getName(), price);
        Assert.assertTrue(b.enter(t));
        t01.removeLine(tozai);
        Assert.assertFalse(b2.exit(t));
    }

    @Test
    public void testExitRemoveOriginStation2(){
        ITicket t = new BaseTicket(false, 10000000);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(b.enter(t));
        t01.removeLine(tozai);
        Assert.assertFalse(b.exit(t));
    }

    @Test
    public void testEntreeCorrecte(){
        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertTrue(ticketEnfant.isValid());

        Barrier barrierMiyanosawa = Barrier.build(n, t01.getName(), price);
        Assert.assertTrue(barrierMiyanosawa.enter(ticketAdulte));
        Assert.assertTrue(barrierMiyanosawa.enter(ticketEnfant));

        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertTrue(ticketEnfant.isValid());

        Barrier barrierShinSapporo = Barrier.build(n, t15.getName(), price);
        Assert.assertTrue(barrierShinSapporo.exit(ticketAdulte));
        Assert.assertTrue(barrierShinSapporo.exit(ticketEnfant));

        Assert.assertFalse(ticketAdulte.isValid());
        Assert.assertFalse(ticketEnfant.isValid());
    }

    @Test
    public void testExitRefuelMoneyAsChild(){
        ITicket ticketEnfantShort = new BaseTicket(true, 100);
        Barrier b = Barrier.build(n, t01.getName(), price);
        Barrier b2 = Barrier.build(n, t19.getName(), price);
        Assert.assertTrue(b.enter(ticketEnfantShort));
        Assert.assertFalse(b2.exit(ticketEnfantShort));
        ITicket ta = new AdjustedTicket(ticketEnfantShort, 100);
        Assert.assertFalse(b2.exit(ta));
        Assert.assertTrue(ta.isValid());
        ITicket taa = new AdjustedTicket(ta, 1000);
        Assert.assertTrue(b2.exit(taa));
        Assert.assertFalse(taa.isValid());
    }
}

