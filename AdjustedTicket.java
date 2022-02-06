package fr.ufc.l3info.oprog;

public class AdjustedTicket implements ITicket {
    private ITicket init;
    private int amount;

    protected AdjustedTicket(ITicket initial, int amount){
        if(initial == null){
            throw new NullPointerException("The initial ticket is null");
        }else{
            this.init = initial;
            this.amount = amount > 0 ? initial.getAmount() + amount : initial.getAmount();
        }
    }

    public int getAmount() {return this.amount;}

    public boolean isChild() {return this.init.isChild();}

    public boolean entering(String name) {return this.init.entering(name);}

    public void invalidate() { this.init.invalidate();}

    public String getEntryStation() {return this.init.getEntryStation();}

    public boolean isValid() {return this.init.isValid();}
}
