package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Test;

public class TestTicketMachine {
    TicketMachine distrib = TicketMachine.getInstance();

    @Test
    public void create_TenTripsTicket_child() {
        ITicket ticket = distrib.buyTicket(true);
        Assert.assertTrue(ticket.isChild());
        Assert.assertTrue(ticket instanceof TenIllimitedTripsTicket);
    }

    @Test
    public void create_TenTripsTicket_adult() {
        ITicket ticket = distrib.buyTicket(false);
        Assert.assertFalse(ticket.isChild());
        Assert.assertTrue(ticket instanceof TenIllimitedTripsTicket);
    }

    @Test
    public void create_BaseTicket_child() {
        ITicket ticket = distrib.buyTicket(true,1000);
        Assert.assertTrue(ticket.isChild());
        Assert.assertTrue(ticket instanceof BaseTicket);
        Assert.assertEquals(1000, ticket.getAmount());
    }

    @Test
    public void create_BaseTicket_adult() {
        ITicket ticket = distrib.buyTicket(false,1000);
        Assert.assertFalse(ticket.isChild());
        Assert.assertTrue(ticket instanceof BaseTicket);
        Assert.assertEquals(1000, ticket.getAmount());
    }

    @Test
    public void create_BaseTicket_child_null() {
        ITicket ticket = distrib.buyTicket(true,0);
        Assert.assertNull(ticket);
    }

    @Test
    public void create_BaseTicket_adult_null() {
        ITicket ticket = distrib.buyTicket(false,-1);
        Assert.assertNull(ticket);
    }

    @Test
    public void create_BaseTicket_More_Amount_ValuesPositiv() {
        ITicket ticket = distrib.buyTicket(false,1000,5000);
        Assert.assertFalse(ticket.isChild());
        Assert.assertTrue(ticket instanceof BaseTicket);
        Assert.assertEquals(1000, ticket.getAmount());
    }

    @Test
    public void create_BaseTicket_More_Amount_ValuesPositivAndNegativ() {
        ITicket ticket = distrib.buyTicket(false,1000,5000,-50,-3000);
        Assert.assertFalse(ticket.isChild());
        Assert.assertTrue(ticket instanceof BaseTicket);
        Assert.assertEquals(1000, ticket.getAmount());
    }

    @Test
    public void create_BaseTicket_More_Amount_NegativFirst() {
        ITicket ticket = distrib.buyTicket(false,-1000,5000,50,3000);
        Assert.assertNull(ticket);
    }

    @Test
    public void create_BaseTicket_More_Amount_0First() {
        ITicket ticket = distrib.buyTicket(false,0,5000,50,3000);
        Assert.assertNull(ticket);
    }

    // ------------------------------------- AdjustFare

    @Test
    public void adjustFare_null_Amount1000() {
        ITicket adjusted = distrib.adjustFare(null, 1000);
        Assert.assertNull(adjusted);
    }

    @Test
    public void adjustFare_null_Amount0() {
        ITicket adjusted = distrib.adjustFare(null, 0);
        Assert.assertNull(adjusted);
    }

    @Test
    public void adjustFare_null_AmountMinus1000() {
        ITicket adjusted = distrib.adjustFare(null, -1000);
        Assert.assertNull(adjusted);
    }

    @Test
    public void adjustFare_TenTrips_Amount1000_child() {
        ITicket tenTrips = new TenIllimitedTripsTicket(true);
        ITicket adjusted = distrib.adjustFare(tenTrips, 1000);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertTrue(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_TenTrips_Amount0_child() {
        ITicket tenTrips = new TenIllimitedTripsTicket(true);
        ITicket adjusted = distrib.adjustFare(tenTrips, 0);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertTrue(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_TenTrips_AmountMinus1000_child() {
        ITicket tenTrips = new TenIllimitedTripsTicket(true);
        ITicket adjusted = distrib.adjustFare(tenTrips, -1000);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertTrue(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_TenTrips_Amount1000_adult() {
        ITicket tenTrips = new TenIllimitedTripsTicket(false);
        ITicket adjusted = distrib.adjustFare(tenTrips, 1000);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertFalse(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_TenTrips_Amount0_adult() {
        ITicket tenTrips = new TenIllimitedTripsTicket(false);
        ITicket adjusted = distrib.adjustFare(tenTrips, 0);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertFalse(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_TenTrips_AmountMinus1000_adult() {
        ITicket tenTrips = new TenIllimitedTripsTicket(false);
        ITicket adjusted = distrib.adjustFare(tenTrips, -1000);
        Assert.assertTrue(adjusted instanceof TenIllimitedTripsTicket);
        Assert.assertFalse(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(tenTrips));
    }

    @Test
    public void adjustFare_BaseTicket_Amount1000_child() {
        ITicket baseT = new BaseTicket(true, 1000);
        ITicket adjusted = distrib.adjustFare(baseT, 1000);
        Assert.assertTrue(adjusted instanceof AdjustedTicket);
        Assert.assertEquals(2000,adjusted.getAmount());
        Assert.assertTrue(adjusted.isChild());

        Assert.assertFalse(adjusted.equals(baseT));
    }

    @Test
    public void adjustFare_BaseTicket_Amount0_child() {
        ITicket baseT = new BaseTicket(true,1000);
        ITicket adjusted = distrib.adjustFare(baseT, 0);
        Assert.assertTrue(adjusted instanceof BaseTicket);
        Assert.assertEquals(1000,adjusted.getAmount());
        Assert.assertTrue(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(baseT));
    }

    @Test
    public void adjustFare_BaseTicket_AmountMinus1000_child() {
        ITicket baseT = new BaseTicket(true,1000);
        ITicket adjusted = distrib.adjustFare(baseT, -1000);
        Assert.assertTrue(adjusted instanceof BaseTicket);
        Assert.assertEquals(1000,adjusted.getAmount());
        Assert.assertTrue(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(baseT));
    }

    @Test
    public void adjustFare_BaseTicket_Amount1000_adult() {
        ITicket baseT = new BaseTicket(false,1000);
        ITicket adjusted = distrib.adjustFare(baseT, 1000);
        Assert.assertTrue(adjusted instanceof AdjustedTicket);
        Assert.assertEquals(2000,adjusted.getAmount());
        Assert.assertFalse(adjusted.isChild());

        Assert.assertFalse(adjusted.equals(baseT));
    }

    @Test
    public void adjustFare_BaseTicket_Amount0_adult() {
        ITicket baseT = new BaseTicket(false,1000);
        ITicket adjusted = distrib.adjustFare(baseT, 0);
        Assert.assertTrue(adjusted instanceof BaseTicket);
        Assert.assertEquals(1000,adjusted.getAmount());
        Assert.assertFalse(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(baseT));
    }

    @Test
    public void adjustFare_BaseTicket_AmountMinus1000_adult() {
        ITicket baseT = new BaseTicket(false, 1000);
        ITicket adjusted = distrib.adjustFare(baseT, -1000);
        Assert.assertTrue(adjusted instanceof BaseTicket);
        Assert.assertEquals(1000,adjusted.getAmount());
        Assert.assertFalse(adjusted.isChild());

        Assert.assertTrue(adjusted.equals(baseT));
    }

}
