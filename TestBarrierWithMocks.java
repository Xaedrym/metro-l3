package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RunWith(MockitoJUnitRunner.Silent.class)
public class TestBarrierWithMocks {
    String station;
    Map<Double,Integer> prices = new HashMap<>();
    Network mockNetwork;
    Station mockStation;
    Barrier bar;

    //Pierre
    Barrier b;

    @Before     // indicates that the method should be executed before each test
    public void setup() {
        station  = "Ora ora ora ora";
        mockNetwork = Mockito.mock(Network.class);
        mockStation = Mockito.mock(Station.class);
        //bar = Barrier.build(mockNetwork,station,prices);
        prices.put(0.0, 100);
        prices.put(3.0, 250);
        prices.put(5.0, 400);

        //Pierre
        String nom = "Asabu";
        String line = "Toho Line";

        Station s = new Station(nom, line, 1, 0.0);
        Network n = new Network();
        n.addStation(s);
        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(0.0, 100);
        mapPrix.put(3.0, 250);
        mapPrix.put(5.0, 500);
        mapPrix.put(9.0, 700);
        mapPrix.put(15.0, 1200);
        mapPrix.put(20.0, 1500);
        mapPrix.put(30.0, 2200);

        b = Barrier.build(n, nom, mapPrix);
    }

    // ------------------------------------------------------------------------------- //
    // Test build(Network n, String s, Map<Double,Integer> p)

    @Test
    public void testBarrierBuild_NetworkNull(){
        Assert.assertNull(Barrier.build(null,station,prices));
    }

    @Test
    public void testBarrierBuild_StationNull(){
        Network mockNetwork = Mockito.mock(Network.class);
        Assert.assertNull(Barrier.build(mockNetwork,null,prices));
    }

    @Test
    public void testBarrierBuild_PricesNull(){
        Network mockNetwork = Mockito.mock(Network.class);
        Assert.assertNull(Barrier.build(mockNetwork,station,null));
    }

    @Test
    public void testBarrierBuild_NetworkNotValid(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(false);
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_PricesIsEmpty(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        prices = new HashMap<>();
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_StationIsNotInNetwork(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName("Muda muda")).thenReturn(null);
        Assert.assertNull(Barrier.build(mockNetwork,"Muda muda",prices));
    }

    @Test
    public void testBarrierBuild_PricesNotContains0(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        prices.remove(0.0);
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_PricesKeyNegative(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        prices.put(-1.0, 150);
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_PricesValue0(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        prices.put(0.5, 0);
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_PricesValueNegative(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        prices.put(0.5, -175);
        Assert.assertNull(Barrier.build(mockNetwork,station,prices));
    }

    @Test
    public void testBarrierBuild_AllGood(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Station mockStation = Mockito.mock(Station.class);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Assert.assertNotNull(Barrier.build(mockNetwork,station,prices));
    }

    // ------------------------------------------------------------------------------- //
    // Test enter(ITicket t)
    @Test
    public void testEnter_ticketNotValid(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.entering(station)).thenReturn(false);
        Assert.assertFalse(bar.enter(mockTicket));
    }

    @Test
    public void testEnter_ticketAmountNull(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.entering(station)).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(0);
        Assert.assertFalse(bar.enter(mockTicket));
    }

    @Test
    public void testEnter_ticketAllGood(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.entering(station)).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(10);
        Assert.assertTrue(bar.enter(mockTicket));
    }

    // ------------------------------------------------------------------------------- //
    // Test exit(ITicket t)
    @Test
    public void testExit_ticketNotValid(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.isValid()).thenReturn(false);
//        Mockito.when(mockTicket.getAmount()).thenReturn(1000);
        Assert.assertFalse(bar.exit(mockTicket));
    }

    @Test
    public void testExit_ticketNotEnoughAmount_notChild(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.isValid()).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(1);
        Mockito.when(mockTicket.getEntryStation()).thenReturn("Ora Station");
        Mockito.when(mockTicket.isChild()).thenReturn(false);
        Mockito.when(mockNetwork.distance("Ora Station",station)).thenReturn(3.0);
        Assert.assertFalse(bar.exit(mockTicket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountAdult(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.isValid()).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(200);
        Mockito.when(mockTicket.getEntryStation()).thenReturn("Ora Station");
        Mockito.when(mockTicket.isChild()).thenReturn(false);
        Mockito.when(mockNetwork.distance("Ora Station",station)).thenReturn(3.0);
        Assert.assertFalse(bar.exit(mockTicket));
    }

    @Test
    public void testExit_ticketNotEnoughAmountAdult_butIsChild(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.isValid()).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(200);
        Mockito.when(mockTicket.getEntryStation()).thenReturn("Ora Station");
        Mockito.when(mockTicket.isChild()).thenReturn(true);
        Mockito.when(mockNetwork.distance("Ora Station",station)).thenReturn(3.0);
        Assert.assertTrue(bar.exit(mockTicket));
    }

    @Test
    public void testExit_ticket_AllGood_Adult(){
        Mockito.when(mockNetwork.isValid()).thenReturn(true);
        Mockito.when(mockNetwork.getStationByName(station)).thenReturn(mockStation);
        Barrier bar = Barrier.build(mockNetwork,station,prices);
        ITicket mockTicket = Mockito.mock(ITicket.class);
        Mockito.when(mockTicket.isValid()).thenReturn(true);
        Mockito.when(mockTicket.getAmount()).thenReturn(1000);
        Mockito.when(mockTicket.getEntryStation()).thenReturn("Ora Station");
        Mockito.when(mockTicket.isChild()).thenReturn(false);
        Mockito.when(mockNetwork.distance("Ora Station",station)).thenReturn(3.0);
        Assert.assertTrue(bar.exit(mockTicket));
    }

    // ============================================================================= //
    //Pierre

    @Test
    public void testBarrierNull(){
        String nom = "Asabu";

        Network n = Mockito.mock(Network.class);

        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(0.0, 100);
        mapPrix.put(3.0, 250);
        mapPrix.put(5.0, 500);

        Barrier b1 = Barrier.build(null, nom, mapPrix);
        Barrier b2 = Barrier.build(n, null, mapPrix);
        Barrier b3 = Barrier.build(n, nom, null);
        Assert.assertNull(b1);
        Assert.assertNull(b2);
        Assert.assertNull(b3);
    }
    /*
        @Test
        public void testBarrierReseauVide(){
            String nom = "Asabu";

            Set<Station> vide = new HashSet<>();
            Network mockNetwork = Mockito.mock(Network.class);
            Mockito.when(mockNetwork.getLines()).thenReturn(vide);

            Map<Double, Integer> mapPrix = new HashMap<>();
            mapPrix.put(0.0, 100);
            mapPrix.put(3.0, 250);
            mapPrix.put(5.0, 500);

            Barrier b1 = Barrier.build(mockNetwork, nom, mapPrix);
            Assert.assertNull(b1);
        }
    */
    @Test
    public void testBarrierStationHorsReseau(){
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.getStationByName("Abusa")).thenReturn(null);

        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(0.0, 100);
        mapPrix.put(3.0, 250);
        mapPrix.put(5.0, 500);

        Barrier b1 = Barrier.build(mockNetwork, "Abusa", mapPrix);
        Assert.assertNull(b1);
    }

    @Test
    public void testBarrierPrixVides(){
        String nom = "Asabu";
        String line = "Toho Line";
        Station s = new Station(nom, line, 1, 0.0);

        Network n = new Network();
        n.addStation(s);

        Map<Double, Integer> mapPrix = new HashMap<>();

        Barrier b1 = Barrier.build(n, nom, mapPrix);
        Assert.assertNull(b1);
    }

    @Test
    public void testBarrierPrixNegatif(){
        String nom = "Asabu";
        Set<String> s = new HashSet<>();
        s.add("Toho Line");
        Station mockStation = Mockito.mock(Station.class);
        Network mockNetwork = Mockito.mock(Network.class);
        Mockito.when(mockNetwork.getLines()).thenReturn(s);
        Mockito.when(mockNetwork.getStationByName(nom)).thenReturn(mockStation);

        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(0.0, 100);
        mapPrix.put(3.0, -250);
        mapPrix.put(5.0, 500);

        Barrier b1 = Barrier.build(mockNetwork, nom, mapPrix);

        Assert.assertNull(b1);
    }

    @Test
    public void testBarrierDistanceNegatif(){
        String nom = "Asabu";
        String line = "Toho Line";
        Station s = new Station(nom, line, 1, 0.0);

        Network n = new Network();
        n.addStation(s);

        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(0.0, 100);
        mapPrix.put(-3.0, 250);
        mapPrix.put(5.0, 500);

        Barrier b1 = Barrier.build(n, nom, mapPrix);

        Assert.assertNull(b1);
    }

    @Test
    public void testBarrierDistancePremierNonZero(){
        String nom = "Asabu";
        String line = "Toho Line";
        Station s = new Station(nom, line, 1, 0.0);

        Network n = new Network();
        n.addStation(s);

        Map<Double, Integer> mapPrix = new HashMap<>();
        mapPrix.put(1.0, 100);
        mapPrix.put(3.0, 250);
        mapPrix.put(5.0, 500);

        Barrier b1 = Barrier.build(n, nom, mapPrix);

        Assert.assertNull(b1);
    }

    /*
    @Test
    public void testExitWith400YensFare() {
        // instanciation du mock
        ITicket mockTicket = Mockito.mock(ITicket.class);
        // définition du comportement attendu pour l'objet
        mockTicket.entering("Asabu");
        Mockito.when(mockTicket.getAmount()).thenReturn(400);
        Assert.assertEquals(true, b.exit(mockTicket));
    }
     */
}
