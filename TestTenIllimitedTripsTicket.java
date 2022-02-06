package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class TestTenIllimitedTripsTicket {
    Network n;
    String tozai;
    Station c2, t01, t02, t03, t04, t05, t06, t07, t08, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19;
    Map<Double, Integer> price;
    Barrier barrier;
    ITicket ticketAdulte, ticketEnfant;

    @Before
    public void setUp(){
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

        barrier = Barrier.build(n, t01.getName(), price);

        ticketAdulte = new TenIllimitedTripsTicket(false);
        ticketEnfant = new TenIllimitedTripsTicket(true);
    }

    @Test
    public void TestGetAMount(){
        Assert.assertEquals(ticketAdulte.getAmount(), Integer.MAX_VALUE);
    }

    @Test
    public void TestIsChild(){
        Assert.assertFalse(ticketAdulte.isChild());
        Assert.assertTrue(ticketEnfant.isChild());
    }

    @Test
    public void Test10EntreesSorties() {
        for(int i=0; i < 10; i++){
            Assert.assertTrue(barrier.enter(ticketAdulte));
            Assert.assertTrue(barrier.exit(ticketAdulte));
        }
        Assert.assertFalse(ticketAdulte.isValid());
    }

    @Test
    public void Test10EntreesPuis10Sorties() {
        for(int i=0; i < 10; i++){
            Assert.assertTrue(ticketAdulte.isValid());
            Assert.assertTrue(barrier.enter(ticketAdulte));
        }

        Assert.assertTrue(barrier.exit(ticketAdulte));
        for(int i=0; i < 9; i++){
            Assert.assertFalse(barrier.exit(ticketAdulte));
        }
    }

    @Test
    public void Test10EntreesSortiesPlus1Entree(){
        for(int i=0; i < 10; i++){
            Assert.assertTrue(barrier.enter(ticketAdulte));
            Assert.assertTrue(barrier.exit(ticketAdulte));
        }
        Assert.assertFalse(ticketAdulte.isValid());
        Assert.assertFalse(barrier.enter(ticketAdulte));
        Assert.assertFalse(barrier.exit(ticketAdulte));
    }

    @Test
    public void TestInvalidate(){
        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertTrue(barrier.enter(ticketAdulte));
        Assert.assertTrue(barrier.exit(ticketAdulte));
        ticketAdulte.invalidate();
        Assert.assertTrue(barrier.enter(ticketAdulte));
        Assert.assertTrue(ticketAdulte.isValid());
    }

    @Test
    public void TestGetNameStationEntered(){
        Barrier barrierSapporo = Barrier.build(n, t19.getName(), price);

        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertTrue(barrierSapporo.enter(ticketAdulte));
        Assert.assertEquals(ticketAdulte.getEntryStation(), t19.getName());
        Assert.assertTrue(barrierSapporo.exit(ticketAdulte));
        Assert.assertTrue(barrier.enter(ticketAdulte));
        Assert.assertEquals(ticketAdulte.getEntryStation(), t01.getName());
        Assert.assertTrue(barrier.exit(ticketAdulte));
    }

    @Test
    public void TestEnteringEmptyName(){
        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertFalse(ticketAdulte.entering(""));
        Assert.assertNull(ticketAdulte.getEntryStation());
    }

    @Test
    public void TestEnteringNullName(){
        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertFalse(ticketAdulte.entering(null));
        Assert.assertNull(ticketAdulte.getEntryStation());
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
    }
}
