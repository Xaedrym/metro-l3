package fr.ufc.l3info.oprog;

public class TicketMachine {

    /* Construction de l’instance */
    private static final TicketMachine INST = new TicketMachine();

    /* Constructeur privé */
    private TicketMachine() { }

    /* Retourne l’instance du singleton. */
    public static TicketMachine getInstance() {
        return INST;
    }

    /**
     *  A method allowing to buy a ticket (Base or TenTrips)
     * @param child Boolean -> True if it's a ticket for a child, false for an adult
     * @param amount (optional) The amount of the ticket, if it's completed and right, it will be a baseticket, else it will be a TenIllimitedTripsTicket
     * @return a BaseTicket(child,amount) or if amount is not completed, TenIllimitedTripsTicket(child), if amount is < 0 then return null
     */
    public ITicket buyTicket(boolean child, int... amount){
        for(int am : amount){
            if(am <= 0){
                return null;
            }
            return new BaseTicket(child,am);
        }
        return new TenIllimitedTripsTicket(child);
    }

    /**
     * A method allowing to adjust a ticket, increase the amount of it
     * @param old The Old ticket
     * @param amount the amount that we want to add to the old ticket
     * @return A new ticket if all is good, else null it's the old ticket was null or the old ticket if the amount it's correct or if old was a TenTrips ticket
     */
    public ITicket adjustFare(ITicket old, int amount){
        if(old == null){
            return null;
        }
        if(amount <= 0 || old instanceof TenIllimitedTripsTicket){
            return old;
        }
        return new AdjustedTicket(old, amount);
    }
}
