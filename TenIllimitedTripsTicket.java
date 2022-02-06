package fr.ufc.l3info.oprog;

public class TenIllimitedTripsTicket implements ITicket {
    /** is this a child ticket? */
    private boolean child;

    /** nb of trips that u can do with this ticket */
    private int nbTrajetsRestants;

    /** entry station */
    private String entry = "should not be returned";

    /** state of the ticket: 0 - issued, 1 - entry set, 2 - invalid */
    private int state;
    final private int ISSUED = 0;
    final private int ENTERED = 1;
    final private int INVALID = 2;

    protected TenIllimitedTripsTicket(boolean child){
        this.child = child;
        this.nbTrajetsRestants = 10;
        this.state = ISSUED;
    }

    public int getAmount() {return Integer.MAX_VALUE;}

    public boolean isChild() {return child;}

    public boolean entering(String name) {
        if (nbTrajetsRestants > 0 && this.state == INVALID){
            this.state = ISSUED;
        }
        if (this.state == ISSUED && name != null && !name.trim().equals("")) {
            nbTrajetsRestants--;
            entry = name;
            this.state = ENTERED;
            return true;
        }
        if(this.state == ENTERED && name != null && !name.trim().equals("")) {
            nbTrajetsRestants--;
            entry = name;
            return true;
        }
        return false;
    }

    public void invalidate() {
        state = INVALID;
    }

    public String getEntryStation() {
        return (state == ENTERED) ? entry : null;
    }

    public boolean isValid() {
        return state != INVALID;
    }
}
