// ROLLET Tommy TP1B -> Baseticket n° 03

package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Test;

public class TestAdjustedTicket {
    ITicket t_adult = new BaseTicket(false,0);
    ITicket t_child = new BaseTicket(true,10);
    ITicket t_neg = new BaseTicket(false, 10);
    ITicket t_adjusted_adult = new AdjustedTicket(t_adult, 15);
    ITicket t_adjusted_child = new AdjustedTicket(t_child, 7);
    ITicket t_adjusted_neg = new AdjustedTicket(t_neg, -3);

    // ------------------------------------------------------------------------------- //
    // AdjustedTicket(ITicket initial, int amount)
    @Test
    public void adjusted_ticket_t_adult(){
        Assert.assertEquals(15,t_adjusted_adult.getAmount());
    }

    @Test
    // Ici le ticket est censé etre un ticket enfant, mais ca retourne false -> erreur d'implem
    // Je vérifie que en modifiant le montant du ticket, celui ci reste du meme type
    public void adjusted_ticket_t_child(){
        Assert.assertEquals(17,t_adjusted_child.getAmount());
    }

    @Test
    public void adjusted_ticket_t_neg(){
        Assert.assertEquals(10,t_adjusted_neg.getAmount());
    }

    @Test(expected=NullPointerException.class)
    public void adjusted_ticket_initial_null(){
        ITicket t_null = null;
        Assert.assertNull(t_null);
        ITicket t_adjusted_null = new AdjustedTicket(t_null, 15);
        Assert.assertNull(t_adjusted_null);
    }

    // ------------------------------------------------------------------------------- //
    // Tests isChild()
    @Test
    public void isChild_false() {
        Assert.assertFalse(t_adjusted_adult.isChild());
    }

    @Test
    // Ici le ticket est censé etre un ticket enfant, mais ca retourne false -> erreur d'implem
     public void isChild_true_ko() {
        Assert.assertTrue(t_adjusted_child.isChild());
     }

    // ------------------------------------------------------------------------------- //
    // Tests getAmount()
    @Test
    public void getAmount_negative() {
        ITicket t_negative_1 = new AdjustedTicket(t_adult,-1);
        Assert.assertEquals(0,t_negative_1.getAmount());
    }

    // ------------------------------------------------------------------------------- //
    // Tests entering(String name)
    @Test
    public void entering_chaine_vide(){
        Assert.assertFalse(t_adjusted_adult.entering(""));
        Assert.assertFalse(t_adjusted_adult.isValid());
    }

    @Test
    public void entering_chaine_null(){
        Assert.assertFalse(t_adjusted_adult.entering(null));
        Assert.assertFalse(t_adjusted_adult.isValid());
    }

    @Test //Entré et entré son pote
    public void entering_ENTERED(){
        t_adjusted_adult.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_adjusted_adult.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_adjusted_adult.getEntryStation());
        Assert.assertFalse(t_adjusted_adult.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_adjusted_adult.isValid());
    }

    @Test
    public void entering_INVALID(){
        t_adjusted_adult.invalidate();
        Assert.assertFalse(t_adjusted_adult.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_adjusted_adult.isValid());
    }

    // ------------------------------------------------------------------------------- //
    // Tests getEntryStation()
    @Test
    public void getEntryStation_return_null_no_entry(){
        Assert.assertNull(t_adjusted_child.getEntryStation());
    }

    @Test
    public void getEntryStation_return_null_entry_but_invalid(){
        t_adjusted_child.invalidate();
        Assert.assertNull(t_adjusted_child.getEntryStation());
    }

    @Test
    public void getEntryStation_return_null_entry_but_already_entered(){
        t_adjusted_child.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_adjusted_child.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_adjusted_child.getEntryStation());
        t_adjusted_child.entering("Muda muda muda mudaaaa Station");
        Assert.assertNull(t_adjusted_child.getEntryStation());
    }

    // ------------------------------------------------------------------------------- //
    // Tests isValid()
    @Test
    public void isValid_True(){
        Assert.assertTrue(t_adjusted_adult.isValid());
    }

    @Test
    public void isValid_True_adult(){
        Assert.assertTrue(t_adjusted_adult.isValid());
    }

    @Test
    public void isValid_False(){
        t_adjusted_child.invalidate();
        Assert.assertFalse(t_adjusted_child.isValid());
    }

    @Test
    public void isValid_False_adult(){
        t_adjusted_adult.invalidate();;
        Assert.assertFalse(t_adjusted_adult.isValid());
    }

    @Test
    public void isValid_True_ENTERED(){
        t_adjusted_adult.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_adjusted_adult.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_adjusted_adult.getEntryStation());
        Assert.assertTrue(t_adjusted_adult.isValid());
    }

    // =================================================================================== //
    //Test Pierre

    @Test(expected=NullPointerException.class)
    public void AdjustedTicketNoInitial(){
        AdjustedTicket t1 = new AdjustedTicket(null, 100);
        Assert.assertNull(t1);
    }

    @Test
    public void AdjustedTicketPrixNeg(){
        ITicket ticket = new BaseTicket02(true, 40);
        AdjustedTicket t1 = new AdjustedTicket(ticket, -10);
        Assert.assertEquals(40, t1.getAmount());
    }

    @Test
    public void AdjustedTicket(){
        ITicket ticket = new BaseTicket02(true, 40);
        AdjustedTicket t = new AdjustedTicket(ticket, 60);
        Assert.assertEquals(100, t.getAmount());
    }
}
