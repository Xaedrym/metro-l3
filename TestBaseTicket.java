// ROLLET Tommy TP1B -> Baseticket n° 03

package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBaseTicket {
    ITicket t_adult = new BaseTicket(false,0);
    ITicket t_child = new BaseTicket(true,10);

    ITicket ticketAdulte, ticketEnfant;

    // ------------------------------------------------------------------------------- //
    // Tests isChild()
    @Test
    public void isChild_false() {
        Assert.assertFalse(t_adult.isChild());
    }

    @Test
    // Ici le ticket est censé etre un ticket enfant, mais ca retourne false -> erreur d'implem
    public void isChild_true_ko() {
        Assert.assertTrue(t_child.isChild());
    }

    // ------------------------------------------------------------------------------- //
    // Tests getAmount()
    @Test
    public void getAmount_0() {
        Assert.assertEquals(0,t_adult.getAmount());
    }

    @Test
    public void getAmount_10() {
        Assert.assertEquals(10,t_child.getAmount());
    }

    @Test
    public void getAmount_negative1() {
        ITicket t_negative_1 = new BaseTicket03(false,-1);
        Assert.assertEquals(0,t_negative_1.getAmount());
    }

    // ------------------------------------------------------------------------------- //
    // Tests entering(String name)
    @Test
    public void entering_chaine_vide(){
        Assert.assertFalse(t_adult.entering(""));
        Assert.assertFalse(t_child.entering(""));
        Assert.assertFalse(t_child.isValid());
        Assert.assertFalse(t_adult.isValid());
    }

    @Test
    public void entering_chaine_null(){
        Assert.assertFalse(t_adult.entering(null));
        Assert.assertFalse(t_child.entering(null));
        Assert.assertFalse(t_child.isValid());
        Assert.assertFalse(t_adult.isValid());
    }

    @Test //Entré et entré son pote
    public void entering_ENTERED(){
        t_child.entering("Muda muda muda mudaaaa Station");
        t_adult.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_child.getEntryStation());
        Assert.assertNotNull(t_adult.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_child.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_adult.getEntryStation());
        Assert.assertFalse(t_child.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_adult.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_child.isValid());
        Assert.assertFalse(t_adult.isValid());
    }

    @Test
    public void entering_INVALID(){
        t_child.invalidate();
        t_adult.invalidate();
        Assert.assertFalse(t_child.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_adult.entering("Muda muda muda mudaaaa Station"));
        Assert.assertFalse(t_child.isValid());
        Assert.assertFalse(t_adult.isValid());
    }

    // ------------------------------------------------------------------------------- //
    // Tests getEntryStation()
    @Test
    public void getEntryStation_return_null_no_entry(){
        Assert.assertNull(t_child.getEntryStation());
    }

    @Test
    public void getEntryStation_return_null_entry_but_invalid(){
        t_child.invalidate();
        Assert.assertNull(t_child.getEntryStation());
    }

    @Test
    public void getEntryStation_return_null_entry_but_already_entered(){
        t_child.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_child.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_child.getEntryStation());
        t_child.entering("Muda muda muda mudaaaa Station");
        Assert.assertNull(t_child.getEntryStation());
    }

    // ------------------------------------------------------------------------------- //
    // Tests isValid()
    @Test
    public void isValid_True(){
        Assert.assertTrue(t_child.isValid());
    }

    @Test
    public void isValid_True_adult(){
        Assert.assertTrue(t_adult.isValid());
    }

    @Test
    public void isValid_False(){
        t_child.invalidate();
        Assert.assertFalse(t_child.isValid());
    }

    @Test
    public void isValid_False_adult(){
        t_adult.invalidate();;
        Assert.assertFalse(t_adult.isValid());
    }

    @Test
    public void isValid_True_ENTERED(){
        t_adult.entering("Muda muda muda mudaaaa Station");
        Assert.assertNotNull(t_adult.getEntryStation());
        Assert.assertEquals("Muda muda muda mudaaaa Station",t_adult.getEntryStation());
        Assert.assertTrue(t_adult.isValid());
    }

    // ====================================================================================== //
    //Pierre
    @Before
    public void setUp(){
        ticketAdulte = new BaseTicket(false, 10);
        ticketEnfant = new BaseTicket(true, 4);
    }

    @Test
    public void TestIsChildFaux_KO(){   //Valeur de retour pour un ticket adulte erronée
        Assert.assertFalse(ticketAdulte.isChild());
    }

    @Test
    public void TestIsChildVrai(){
        Assert.assertTrue(ticketEnfant.isChild());
    }

    @Test
    public void TestAmountNegatif(){
        ITicket ticketNeg = new BaseTicket(false, -10);
        Assert.assertEquals(ticketNeg.getAmount(), 0);
    }

    @Test
    public void TestGetAmount(){
        Assert.assertEquals(ticketEnfant.getAmount(), 4);
        Assert.assertEquals(ticketAdulte.getAmount(), 10);
    }

    @Test
    public void TestEnteringVrai(){
        Assert.assertTrue(ticketAdulte.entering("Sakaemichi"));
        Assert.assertTrue(ticketEnfant.entering("Sakaemichi"));
    }

    @Test
    public void TestEnteringNull(){
        Assert.assertFalse(ticketAdulte.entering(null));
        Assert.assertFalse(ticketAdulte.isValid());
        Assert.assertFalse(ticketEnfant.entering(null));
        Assert.assertFalse(ticketEnfant.isValid());
    }

    @Test
    public void TestEnteringTwice(){
        Assert.assertTrue(ticketAdulte.entering("Sakaemichi"));
        Assert.assertTrue(ticketEnfant.entering("Sakaemichi"));

        Assert.assertFalse(ticketAdulte.entering("Sakaemichi"));
        Assert.assertFalse(ticketAdulte.isValid());
        Assert.assertFalse(ticketEnfant.entering("Sakaemichi"));
        Assert.assertFalse(ticketEnfant.isValid());
    }

    @Test
    public void TestInvalidate(){
        ticketAdulte.invalidate();
        ticketEnfant.invalidate();

        Assert.assertFalse(ticketAdulte.entering("Sakaemichi"));
        Assert.assertFalse(ticketAdulte.isValid());
        Assert.assertFalse(ticketEnfant.entering("Sakaemichi"));
        Assert.assertFalse(ticketEnfant.isValid());
    }


    @Test
    public void TestGetEntryStation(){
        Assert.assertTrue(ticketAdulte.entering("Sakaemichi"));
        Assert.assertEquals(ticketAdulte.getEntryStation(), "Sakaemichi");
        Assert.assertTrue(ticketEnfant.entering("Sakaemichi"));
        Assert.assertEquals(ticketEnfant.getEntryStation(), "Sakaemichi");
    }

    @Test
    public void TestGetEntryStationNull(){
        Assert.assertFalse(ticketAdulte.entering(null));
        Assert.assertNull(ticketAdulte.getEntryStation());
        Assert.assertFalse(ticketEnfant.entering(null));
        Assert.assertNull(ticketEnfant.getEntryStation());
    }

    @Test
    public void TestGetEntryStationInvalide() {
        ticketAdulte.invalidate();
        ticketEnfant.invalidate();

        Assert.assertFalse(ticketAdulte.entering("Sakaemichi"));
        Assert.assertNull(ticketAdulte.getEntryStation());
        Assert.assertFalse(ticketEnfant.entering("Sakaemichi"));
        Assert.assertNull(ticketEnfant.getEntryStation());
    }

    @Test
    public void TestGetEntryStationTwice(){
        Assert.assertTrue(ticketAdulte.entering("Sakaemichi"));
        Assert.assertTrue(ticketEnfant.entering("Sakaemichi"));

        Assert.assertFalse(ticketAdulte.entering("Sakaemichi"));
        Assert.assertNull(ticketAdulte.getEntryStation());
        Assert.assertFalse(ticketEnfant.entering("Sakaemichi"));
        Assert.assertNull(ticketEnfant.getEntryStation());
    }

    @Test
    public void TestIsValid(){
        Assert.assertTrue(ticketAdulte.isValid());
        Assert.assertTrue(ticketEnfant.isValid());
    }
}
