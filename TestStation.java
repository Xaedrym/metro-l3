package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test file for the Station class.
 */
public class TestStation {

    //Pierre
    Station s1, s2Pierre;

    Station s, s2, s3, s4, s_name_null, s_line_void, s_line_null, s_line_spaces, s_bad_number, s_bad_dist;

    @Before     // indicates that the method should be executed before each test
    public void setup() {
        // creation of an object to test
        s = new Station("ma Station");
        s2 = new Station("");
        s3 = new Station(null);
        s4 = new Station("Sumikawa","Namboku Line",8,5000);
        s_name_null = new Station(null,"Namboku Line",8,5000);
        s_line_void = new Station("Sumikawa","",8,5000);
        s_line_null = new Station("Sumikawa",null,8,5000);
        s_line_spaces = new Station("Sumikawa","   ",8,5000);
        s_bad_number = new Station("Sumikawa","Namboku Line",0,5000);
        s_bad_dist = new Station("Sumikawa","Namboku Line",8,-1);

        s1 = new Station("ma Station");
        s2Pierre = new Station("ma Station dans une ligne", "Toho", 42, 42.0);
    }

    // ------------------------------------------------------------------------------- //
    // Tests constructeur Station(String _name)
    @Test   // indicates that this method is a test case
    public void testName() {
        // an observation that will obviously succeed
        Assert.assertNotNull(s);
        // an observation that will cause the test to fail:
        Assert.assertEquals("ma Station", s.getName());
    }

    // Tests constructeur Station(String _name)
    @Test
    public void testNameFalse() {
        Assert.assertNotNull(s);
        Assert.assertNotEquals("Pas ma Station", s.getName());
    }

    @Test
    public void testNameVoid() {
        Assert.assertNotNull(s2);
        Assert.assertNotEquals("Pas ma Station",s2.getName());
        Assert.assertEquals("",s2.getName());
    }

    @Test
    public void testNameNull() {
        Assert.assertNotNull(s3);
        Assert.assertNull(s3.getName());
    }

    // ------------------------------------------------------------------------------- //
    // Tests constructeur Station(String _name, String _line, int _number, double _dist)
    @Test
    public void testNameC2() {
        Assert.assertNotNull(s4);
        Assert.assertEquals("Sumikawa", s4.getName());
        Assert.assertNotNull(s4.getLines());
        Assert.assertTrue(s4.getLines().contains("Namboku Line"));
        Assert.assertEquals(8, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(5000, s4.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testNameNullC2() {
        Assert.assertNotNull(s_name_null);
        Assert.assertNull(s_name_null.getName());
        //Assert.assertTrue(s_name_null.getLines().contains(null));
        Assert.assertEquals(0, s_name_null.getNumberForLine(""));
        Assert.assertEquals(0, s_name_null.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s_name_null.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testLineVoid() {
        Assert.assertNotNull(s_line_void);
        Assert.assertEquals("Sumikawa", s_line_void.getName());
        Assert.assertFalse(s_line_void.getLines().contains(""));
        Assert.assertEquals(0, s_line_void.getNumberForLine(""));
        Assert.assertEquals(0, s_line_void.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s_line_void.getDistanceForLine("Namboku Line"),0.0);
    }

    @Test
    public void testLineNull() {
        Assert.assertNotNull(s_line_null);
        Assert.assertEquals("Sumikawa", s_line_null.getName());
        Assert.assertEquals(0, s_line_void.getNumberForLine(null));
        Assert.assertEquals(0, s_line_void.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s_line_void.getDistanceForLine("Namboku Line"),0.0);
    }

    @Test
    public void testLineNameSpaces() {
        Assert.assertNotNull(s_line_spaces);
        Assert.assertEquals("Sumikawa", s_line_spaces.getName());
        Assert.assertEquals(0, s_line_spaces.getNumberForLine("Namboku Line"));
        Assert.assertEquals(0, s_line_spaces.getNumberForLine("   "));
        Assert.assertEquals(-1, s_line_spaces.getDistanceForLine("Namboku Line"),0.0);
        Assert.assertFalse(s_line_spaces.getLines().contains("   "));
    }

    @Test
    public void testBadNumber() {
        Assert.assertNotNull(s_bad_number);
        Assert.assertEquals("Sumikawa", s_bad_number.getName());
        Assert.assertEquals(0, s_bad_number.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s_bad_number.getDistanceForLine("Namboku Line"),0.0);
    }

    @Test
    public void testBadDist() {
        Assert.assertNotNull(s_bad_dist);
        Assert.assertEquals("Sumikawa", s_bad_dist.getName());
        Assert.assertEquals(0, s_bad_dist.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s_bad_dist.getDistanceForLine("Namboku Line"),0.0);
    }

    // ------------------------------------------------------------------------------- //
    // Tests addLine

    @Test
    public void testAdd() {
        s2.addLine("Namboku Line", 8, 5000);
        Assert.assertEquals(8, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(5000, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_LineNameVoid() {
        s2.addLine("", 8, 5000);
        Assert.assertEquals(0, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_LineNameNull() {
        s2.addLine(null, 8, 5000);
        Assert.assertEquals(0, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_LineNameSpaces() {
        s.addLine("   ", 8, 5000);
        Assert.assertFalse(s4.getLines().contains("   "));
        Assert.assertEquals(0, s.getNumberForLine("   "));
        Assert.assertEquals(-1, s.getDistanceForLine( "   "),0.0);
        Assert.assertFalse(s.getLines().contains("   "));
    }

    @Test
    public void testAdd_LineAlreadyExist() {
        s4.addLine("Namboku Line", 30, 6900);
        Assert.assertEquals(30, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(6900, s4.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_BadNumber0() {
        s2.addLine("Namboku Line", 0, 5000);
        Assert.assertEquals(0, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_BadNumberNeg() {
        s2.addLine("Namboku Line", -1, 5000);
        Assert.assertEquals(0, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_Dist0() {
        s2.addLine("Namboku Line", 8, 0);
        Assert.assertEquals(8, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(0, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testAdd_BadDistNeg() {
        s2.addLine("Namboku Line", 8, -1);
        Assert.assertEquals(0, s2.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s2.getDistanceForLine( "Namboku Line"),0.0);
    }

    // ------------------------------------------------------------------------------- //
    // Tests removeLine

    @Test
    public void testRemoveSucceed(){
        Assert.assertNotNull(s4);
        Assert.assertEquals("Sumikawa", s4.getName());
        Assert.assertNotNull(s4.getLines());
        Assert.assertTrue(s4.getLines().contains("Namboku Line"));
        Assert.assertEquals(8, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(5000, s4.getDistanceForLine( "Namboku Line"),0.0);

        s4.removeLine("Namboku Line");
        Assert.assertFalse(s4.getLines().contains("Namboku Line"));
        Assert.assertEquals(0, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(-1, s4.getDistanceForLine( "Namboku Line"),0.0);
    }

    @Test
    public void testRemoveNotSucceed() {
        Assert.assertNotNull(s4);
        Assert.assertEquals("Sumikawa", s4.getName());
        Assert.assertNotNull(s4.getLines());
        Assert.assertTrue(s4.getLines().contains("Namboku Line"));
        Assert.assertEquals(8, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(5000, s4.getDistanceForLine( "Namboku Line"),0.0);

        s4.removeLine("Baka Line");
        Assert.assertFalse(s4.getLines().contains("Baka Line"));
        Assert.assertTrue(s4.getLines().contains("Namboku Line"));
        Assert.assertEquals(8, s4.getNumberForLine("Namboku Line"));
        Assert.assertEquals(5000, s4.getDistanceForLine( "Namboku Line"),0.0);
    }

    // ------------------------------------------------------------------------------- //
    // Tests equals
    @Test
    public void testEqualsSameObject() {
        Assert.assertTrue(s_line_spaces.equals(s4));
    }

    @Test
    public void testEqualsDifferentObject() {
        int yo = 1;
        Assert.assertFalse(s.equals(yo));
    }

    @Test
    public void testEqualsNulls() {
        Assert.assertTrue(s3.equals(s_name_null));
    }

    @Test
    public void testEqualsNotSameName() {
        Assert.assertFalse(s.equals(s4));
    }

    @Test
    public void testEqualExacltyTheSameThing() {
        Assert.assertTrue(s.equals(s));
    }

    // ========================================================================== //
    //Pierre

    @Test
    public void testStation(){
        Assert.assertEquals("ma Station", s1.getName());

    }

    @Test
    public void testStationLigne(){
        Assert.assertEquals("ma Station dans une ligne", s2Pierre.getName());
    }

    @Test
    public void testStationNomLigneVide(){
        String name = "ma Station dans une fausse ligne";
        Station s = new Station(name, "", 42, 42.0);

        Assert.assertFalse(s.getLines().contains(""));
    }

    @Test
    public void testStationNomLigneNull(){
        String name = "ma Station dans une fausse ligne";
        Station s = new Station(name, null, 42, 42.0);

        Assert.assertFalse(s.getLines().contains(null));
        Assert.assertNotEquals(s.getNumberForLine(name), 42);
    }

    @Test
    public void testStationNomLigneAssimile(){
        String name = "ma Station dans une fausse ligne";
        String line = "   ";
        Station s = new Station(name, line, 42, 42.0);

        Assert.assertFalse(s.getLines().contains(line));
    }

    @Test
    public void testStationNumeroLigneZero(){
        String name = "ma Station dans une fausse ligne";
        Station s = new Station(name, "Toho", 0, 42.0);

        Assert.assertFalse(s.getLines().contains("Toho"));
    }

    @Test
    public void testStationNumeroLigneNeg(){
        String name = "ma Station dans une fausse ligne";
        Station s = new Station(name, "Toho", -1, 42.0);

        Assert.assertFalse(s.getLines().contains("Toho"));
    }

    @Test
    public void testStationDistLigneNeg(){
        String name = "ma Station dans une fausse ligne";
        Station s = new Station(name, "Toho", 42, -42);

        Assert.assertFalse(s.getLines().contains("Toho"));
    }

    @Test
    public void testAjoutLigne(){
        s1.addLine("Toho", 12, 4.7);

        Assert.assertTrue(s1.getLines().contains("Toho"));
        Assert.assertEquals(s1.getNumberForLine("Toho"), 12);
        Assert.assertEquals(s1.getDistanceForLine("Toho"), 4.7, 0.0);
    }

    @Test
    public void testAjoutCorrespondance(){
        s1.addLine("Toho", 12, 4.7);
        s1.addLine("Namboku", 7, 7.0);

        Assert.assertTrue(s1.getLines().contains("Toho"));
        Assert.assertTrue(s1.getLines().contains("Namboku"));

        Assert.assertEquals(s1.getNumberForLine("Toho"), 12);
        Assert.assertEquals(s1.getNumberForLine("Namboku"), 7);

        Assert.assertEquals(s1.getDistanceForLine("Toho"), 4.7, 0.0);
        Assert.assertEquals(s1.getDistanceForLine("Namboku"), 7.0, 0.0);
    }

    @Test
    public void testMajLigne(){
        s2Pierre.addLine("Toho", 12, 4.7);

        Assert.assertTrue(s2Pierre.getLines().contains("Toho"));

        Assert.assertNotEquals(s2Pierre.getNumberForLine("Toho"), 42);
        Assert.assertEquals(s2Pierre.getNumberForLine("Toho"), 12);

        Assert.assertNotEquals(s2Pierre.getDistanceForLine("Toho"), 42.0, 0.0);
        Assert.assertEquals(s2Pierre.getDistanceForLine("Toho"), 4.7, 0.0);
    }

    @Test
    public void testAjoutNomLigneVide(){
        String name = "";
        s1.addLine(name, 42, 42.0);

        Assert.assertFalse(s1.getLines().contains(name));
    }

    @Test
    public void testAjoutNomLigneNull(){
        s1.addLine(null, 42, 42.0);

        Assert.assertFalse(s1.getLines().contains(null));
    }

    @Test
    public void testAjoutNumeroLigneNegatif(){
        String name = "Toho";
        int number = -1;
        s1.addLine(name, number, 42.0);

        Assert.assertFalse(s1.getLines().contains(name));

    }

    @Test
    public void testAjoutNumeroLigneZero(){
        String name = "Toho";
        int number = 0;
        s1.addLine(name, number, 42.0);

        Assert.assertFalse(s1.getLines().contains(name));
    }

    @Test
    public void testAjoutDistanceLigneNegatif(){
        String name = "Toho";
        double distance = -2.0;
        s1.addLine(name, 12, distance);

        Assert.assertFalse(s1.getLines().contains(name));
    }

    @Test
    public void testRetraitLigne(){
        String name = "Toho";
        s2Pierre.removeLine(name);

        Assert.assertFalse(s2Pierre.getLines().contains(name));
        Assert.assertNotEquals(s2Pierre.getNumberForLine(name), 42);
        Assert.assertNotEquals(s2Pierre.getDistanceForLine(name), 42.0, 0.0);
    }

    @Test
    public void testRetraitLigneInexistante(){
        Assert.assertTrue(s1.getLines().isEmpty());
        s1.removeLine("ligne 13");

        Assert.assertTrue(s1.getLines().isEmpty());
    }

    @Test
    public void testHashCodeIdentique(){
        Assert.assertEquals(s1.hashCode(), s1.hashCode());
    }

    @Test
    public void testHashCodeSemblable(){
        Station s2bis = new Station("ma Station dans une ligne", "Toho", 42, 42.0);

        Assert.assertEquals(s2bis.hashCode(), s2Pierre.hashCode());
    }

    @Test
    public void testHashCodeDifferent(){
        Station s = new Station("Namboku", "Toho", 7, 7.0);

        Assert.assertNotEquals(s.hashCode(), s2Pierre.hashCode());
    }

    @Test
    public void testComparaisonStationsCorrect(){
        Station s = new Station("Motomachi");
        Station ss = new Station("Motomachi", "Toho", 3, 4.9);

        Assert.assertEquals(s, ss);
    }

    @Test
    public void testComparaisonStationsCorrectReflectif(){
        Assert.assertEquals(s1, s1);
    }

    @Test
    public void testComparaisonStationEtNull(){
        Station ss = new Station("Motomachi");

        Assert.assertNotEquals(ss, null);
        Assert.assertNotEquals(null, ss);
    }

    @Test
    public void testComparaisonStationString(){
        Station s = new Station("Motomachi");
        String str = "Motomachi";

        Assert.assertNotEquals(s, str);
    }
}
