package fr.ufc.l3info.oprog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNetwork {
    Network network;
    Station s, s_namboku_1, s_namboku_2, s_namboku_3, s2, s_v2, s3_valid_n1, s3_valid_n2, s3_valid_n3, s3_valid_n5, s3_valid_n2_km0, s3_not_valid_n1, s3_not_valid_n3_dist, s3_not_valid_n1_doublons;

    //Pierre
    Network n;
    Station s1, s2Pierre;
    String toho, namboku, tozai;


    @Before     // indicates that the method should be executed before each test
    public void setup() {
        network = new Network();
        s = new Station("Sumikawa","Namboku Line",8,5000);
        s_namboku_1 = new Station("Asabu","Namboku Line",1,0);
        s_namboku_2 = new Station("Kita sanjuyo jo","Namboku Line",2,1000);
        s_namboku_3 = new Station("Kita nijuyo yo","Namboku Line",3,2000);
        s2 = new Station("Sumikawa");
        s_v2 = new Station("Sumikawa-2");
        s3_valid_n1 = new Station("Ora ora ora oraaaa", "Jojo's Bizarre adventures", 1, 0);
        s3_valid_n2 = new Station("Muda muda muda mudaaaa", "Jojo's Bizarre adventures", 2, 1000);
        s3_valid_n3 = new Station("NANI", "Jojo's Bizarre adventures", 3, 2000);
        s3_valid_n5 = new Station("Omae wa mou shinderu", "Jojo's Bizarre adventures", 5, 4000);
        s3_valid_n2_km0 = new Station("Muda bis", "Jojo's Bizarre adventures", 2, 0);
        s3_not_valid_n1 = new Station("JOJO tutututututu", "Jojo's Bizarre adventures", 1, 50);
        s3_not_valid_n1_doublons = new Station("Ora bis", "Jojo's Bizarre adventures", 1, 0);
        s3_not_valid_n3_dist = new Station("FROM THE WIND", "Jojo's Bizarre adventures", 3, 500);

        //Pierre
        toho = "Toho Line";
        namboku = "Namboku Line";
        tozai = "Tozai Line";

        n = new Network();
        s1 = new Station("Shibuyatchaka");
        s2Pierre = new Station("Bus Center bae", toho, 10, 3.2);
    }

    // ------------------------------------------------------------------------------- //
    // Tests constructeur Network

    @Test
    public void Network() {
        Assert.assertNotNull(network);
    }

    // ------------------------------------------------------------------------------- //
    // Tests addStation(Station s)
    @Test
    public void addNetworkSucceed() {
        Assert.assertNotNull(s);

        network.addStation(s);
        Assert.assertEquals(s,network.getStationByName("Sumikawa"));
        Assert.assertEquals(s,network.getStationByLineAndNumber("Namboku Line",8));
    }

    @Test
    public void addNetworkStationAlreadyExists() {
        network.addStation(s);
        Assert.assertEquals(s,network.getStationByName("Sumikawa"));
        Assert.assertEquals(s,network.getStationByLineAndNumber("Namboku Line",8));

        network.addStation(s2);
        Assert.assertEquals(s,network.getStationByName("Sumikawa"));
        Assert.assertEquals(s,network.getStationByLineAndNumber("Namboku Line",8));
    }

    @Test
    public void addNetworkStationAlreadyExistsReverse() {
        network.addStation(s2);
        Assert.assertEquals(s2,network.getStationByName("Sumikawa"));

        network.addStation(s);
        Assert.assertEquals(s2,network.getStationByName("Sumikawa"));
    }

    // ------------------------------------------------------------------------------- //
    // Tests getLines()

    @Test
    public void getLines() {
        Assert.assertNotNull(s);
        Assert.assertNotNull(s_v2);

        network.addStation(s);
        network.addStation(s_v2);

        Assert.assertNotNull(network.getLines());
        Assert.assertTrue(network.getLines().contains("Namboku Line"));
    }

    @Test
    public void getLinesNull() {
        Assert.assertNotNull(s2);
        Assert.assertNotNull(s_v2);

        network.addStation(s2);
        network.addStation(s_v2);

        Assert.assertEquals(network.getLines().size(),0);
    }

    // ------------------------------------------------------------------------------- //
    // Tests getStationByName(String name)

    @Test
    public void getStationByNameSucceed() {
        Assert.assertNotNull(s);

        network.addStation(s);
        Assert.assertEquals(s,network.getStationByName("Sumikawa"));

        network.addStation(s_v2);
        Assert.assertEquals(s_v2,network.getStationByName("Sumikawa-2"));
    }

    @Test
    public void getStationByNameFailed() {
        Assert.assertNotNull(s);

        network.addStation(s);
        Assert.assertEquals(s,network.getStationByName("Sumikawa"));
        Assert.assertNotEquals(s,network.getStationByName("Sumikawa-2"));
        Assert.assertNull(network.getStationByName("Sumikawa-2"));
    }

    @Test
    public void getStationByNameFailed2() {
        Assert.assertNull(network.getStationByName("Sumikawa-2"));
        Assert.assertNull(network.getStationByName("Sumikawa"));
    }


    // ------------------------------------------------------------------------------- //
    // Tests getStationByLineAndNumber(String line, int number)

    @Test
    public void getStationByLineAndNumberSucceed() {
        Assert.assertNotNull(s);

        network.addStation(s);
        Assert.assertEquals(s,network.getStationByLineAndNumber("Namboku Line", 8));
        Assert.assertNotNull(network.getStationByLineAndNumber("Namboku Line", 8));
    }

    @Test
    public void getStationLineAndNumberFailed() {
        network.addStation(s2);
        Assert.assertNotEquals(s2,network.getStationByLineAndNumber("Namboku Line", 8));
        Assert.assertNull(network.getStationByLineAndNumber("Namboku Line", 8));

        network.addStation(s_v2);
        Assert.assertNotEquals(s_v2,network.getStationByLineAndNumber("Namboku Line", 8));
        Assert.assertNull(network.getStationByLineAndNumber("Namboku Line", 8));
    }

    @Test
    public void getStationLineAndNumberFailed2() {
        Assert.assertNull(network.getStationByLineAndNumber("Namboku Line", 8));
        Assert.assertNull(network.getStationByLineAndNumber("Kamasutra", 69));
    }

    // ------------------------------------------------------------------------------- //
    // Tests isValid()
    @Test
    public void isValid_no_line_no_station() {
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_no_line_1_station() {
        network.addStation(s2);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_no_more_line_1_station_n1(){
        network.addStation(s3_valid_n1);
        s3_valid_n1.removeLine("Jojo's Bizarre adventures");

        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_no_more_line_1_station_n2(){
        network.addStation(s3_valid_n2);
        s3_valid_n2.removeLine("Jojo's Bizarre adventures");

        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_1_line_1_station_n1_d0() {
        network.addStation(s3_valid_n1);
        Assert.assertTrue(network.isValid());
    }

    @Test
    public void isValid_1_line_1_station_n8_d5000() {
        network.addStation(s);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_first_station_of_line_km0_nb2() {
        network.addStation(s3_valid_n2_km0);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_first_station_of_line_NOT_km0() {
        network.addStation(s3_not_valid_n1);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_numero_doublons(){
        network.addStation(s3_valid_n1);
        network.addStation(s3_not_valid_n1_doublons);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_dist_non_croissante(){
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_not_valid_n3_dist);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_dist_manque_n1(){
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_dist_manque_n2(){
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_dist_manque_n4(){
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);
        network.addStation(s3_valid_n5);
        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_dist_manque_n4_insertion_desordonnee(){
        network.addStation(s3_valid_n5);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);
        network.addStation(s3_valid_n1);
        Assert.assertFalse(network.isValid());
    }


    @Test
    public void isValid_is_isolated_line() {
        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_is_isolated_3lines() {
        Station s1_l3, s2_l3, s3_l3, s4_l3;
        s1_l3 = new Station("station 1", "Pôle Temis", 1, 0);
        s2_l3 = new Station("station 2", "Pôle Temis", 2, 700);
        s3_l3 = new Station("station 3", "Pôle Temis", 3, 1700);
        s4_l3 = new Station("station 4", "Pôle Temis", 4, 1900);

        s_namboku_2.addLine( "Jojo's Bizarre adventures", 2, 1000);

        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);

        network.addStation(s1_l3);
        network.addStation(s2_l3);
        network.addStation(s3_l3);
        network.addStation(s4_l3);

        Assert.assertFalse(network.isValid());
    }

    @Test
    public void isValid_is_not_isolated_line() {
        s_namboku_2.addLine( "Jojo's Bizarre adventures", 2, 1000);

        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.isValid());
    }

    @Test
    public void isValid_is_not_isolated_3lines() {
        Station s2_l3, s3_l3, s4_l3;
        s2_l3 = new Station("station 2", "Pôle Temis", 2, 700);
        s3_l3 = new Station("station 3", "Pôle Temis", 3, 1700);
        s4_l3 = new Station("station 4", "Pôle Temis", 4, 1900);

        s3_valid_n1.addLine("Pôle Temis",1,0);
        s_namboku_2.addLine( "Jojo's Bizarre adventures", 2, 1000);

        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);

        network.addStation(s2_l3);
        network.addStation(s3_l3);
        network.addStation(s4_l3);

        Assert.assertTrue(network.isValid());
    }

    // Test entier du réseau de métro japonais (repris sur Pierre pour effectuer le test entier)
    @Test
    public void isValid_metro_japonais(){
        Station join_station_1 = new Station("Sapporo");
        Station join_station_2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", "Toho Line", 1, 0.0);
        Station h02 = new Station("Shindo higashi", "Toho Line", 2, 0.9);
        Station h03 = new Station( "Motomashi", "Toho Line", 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", "Toho Line", 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", "Toho Line", 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", "Toho Line", 6, 5.4);
        join_station_1.addLine("Toho Line", 7, 6.7);
        join_station_2.addLine("Toho Line", 8, 7.3);
        Station h09 = new Station("Hosui Susukino", "Toho Line", 9, 8.1);
        Station h10 = new Station("Gakuen mae", "Toho Line", 10, 9.5);
        Station h11 = new Station("Toyohira koen", "Toho Line", 11, 10.4);
        Station h12 = new Station("Misono", "Toho Line", 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", "Toho Line", 13, 12.6);
        Station h14 = new Station("Fukuzumi", "Toho Line", 14, 13.6);
        network.addStation(h01);network.addStation(h02);network.addStation(h03);network.addStation(h04);network.addStation(h05);network.addStation(h06);network.addStation(join_station_1);network.addStation(join_station_2);network.addStation(h09);network.addStation(h10);network.addStation(h11);network.addStation(h12);network.addStation(h13);network.addStation(h14);

        Station n01 = new Station("Asabu", "Namboku Line", 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", "Namboku Line", 2, 1.0);
        Station n03 = new Station("Kita nijuyo jo", "Namboku Line", 3, 2.2);
        Station n04 = new Station("Kita juhachi jo", "Namboku Line", 4, 3.1);
        Station n05 = new Station("Kita juni jo", "Namboku Line", 5, 3.9);
        join_station_1.addLine("Namboku Line", 6, 4.9);
        join_station_2.addLine("Namboku Line", 7, 5.5);
        Station n08 = new Station("Susukino", "Namboku Line", 8, 6.1);
        Station n09 = new Station("Nakajima koen", "Namboku Line", 9, 6.8);
        Station n10 = new Station("Horohira bashi", "Namboku Line", 10, 7.8);
        Station n11 = new Station("Nakanoshima", "Namboku Line", 11, 8.3);
        Station n12 = new Station("Hiragishi", "Namboku Line", 12, 9.0);
        Station n13 = new Station("Minami Hiragishi", "Namboku Line", 13, 10.1);
        Station n14 = new Station("Sumikawa", "Namboku Line", 14, 11.3);
        Station n15 = new Station("Jieitai mae", "Namboku Line", 15, 12.6);
        Station n16 = new Station("Makomanai", "Namboku Line", 16, 14.3);
        network.addStation(n01);network.addStation(n02);network.addStation(n03);network.addStation(n04);network.addStation(n05);network.addStation(join_station_1);network.addStation(join_station_2);network.addStation(n08);network.addStation(n09);network.addStation(n10);network.addStation(n11);network.addStation(n12);network.addStation(n13);network.addStation(n14);network.addStation(n15);network.addStation(n16);

        Station t01 = new Station("Miyanosawa", "Tozai Line", 1, 0.0);
        Station t02 = new Station("Hassamu minami", "Tozai Line", 2, 1.5);
        Station t03 = new Station("Kotoni", "Tozai Line", 3, 2.8);
        Station t04 = new Station("Nijuyonken", "Tozai Line", 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", "Tozai Line", 5, 4.9);
        Station t06 = new Station("Maruyama koen", "Tozai Line", 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", "Tozai Line", 7, 6.6);
        Station t08 = new Station("Nishi juitchome", "Tozai Line", 8, 7.5);
        join_station_2.addLine("Tozai Line", 9, 8.5);
        Station t10 = new Station("Bus Center mae", "Tozai Line", 10, 9.3);
        Station t11 = new Station("Kikusui", "Tozai Line", 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", "Tozai Line", 12, 11.6);
        Station t13 = new Station("Shiroishi", "Tozai Line", 13, 12.7);
        Station t14 = new Station("Nango nana chome", "Tozai Line", 14, 14.1);
        Station t15 = new Station("Nango jusan chome", "Tozai Line", 15, 15.2);
        Station t16 = new Station("Nango juhatchome", "Tozai Line", 16, 16.4);
        Station t17 = new Station("Oyachi", "Tozai Line", 17, 17.9);
        Station t18 = new Station("Hibarigaoka", "Tozai Line", 18, 18.9);
        Station t19 = new Station("Shin Sapporo", "Tozai Line", 19, 20.1);
        network.addStation(t01);network.addStation(t02);network.addStation(t03);network.addStation(t04);network.addStation(t05);network.addStation(t06);network.addStation(t07);network.addStation(t08);network.addStation(join_station_2);network.addStation(t10);network.addStation(t11);network.addStation(t12);network.addStation(t13);network.addStation(t14);network.addStation(t15);network.addStation(t16);network.addStation(t17);network.addStation(t18);network.addStation(t19);

        Assert.assertTrue(network.isValid());
    }


    // ------------------------------------------------------------------------------- //
    // Tests distance(String s1 ,String s2)

    @Test
    public void distance_invalid_network() {
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertFalse(network.isValid());
        Assert.assertTrue(network.distance(s3_valid_n1.getName(),s3_valid_n3.getName()) < 0.0);
    }

    @Test
    public void distance_s1_null() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.isValid());
        Assert.assertTrue(network.distance(null,s3_valid_n3.getName()) < 0.0);
    }

    @Test
    public void distance_s2_null() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.distance(s3_valid_n1.getName(),null) < 0.0);
    }

    @Test
    public void distance_station_not_part_of_network_s1() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.distance("Yo tout le monde c'est Squeezie",s3_valid_n3.getName()) < 0.0);
    }

    @Test
    public void distance_station_not_part_of_network_s2() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.distance(s3_valid_n1.getName(),"BONSOIR PARIIIIS") < 0.0);
    }

    @Test
    public void distance_station_not_part_of_network() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertTrue(network.distance("Yo tout le monde c'est Squeezie","BONSOIR PARIIIIS") < 0.0);
    }

    @Test
    public void distance_station_same_line() {
        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n2);
        network.addStation(s3_valid_n3);

        Assert.assertEquals(2000, network.distance(s3_valid_n1.getName(),s3_valid_n3.getName()),0.0);
    }

    @Test
    public void distance_station_not_same_line() {
        Station s_namboku_4 = new Station("Kita juhachi jo", "Namboku Line", 4, 3500);

        s_namboku_2.addLine( "Jojo's Bizarre adventures", 2, 1000);

        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);
        network.addStation(s_namboku_4);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);

        Assert.assertEquals(3500, network.distance(s3_valid_n1.getName(),s_namboku_4.getName()),0.0);
    }

    @Test
    public void distance_station_not_same_line2() {
        Station s_namboku_4 = new Station("Kita juhachi jo", "Namboku Line", 4, 3500);

        s_namboku_1.addLine( "Jojo's Bizarre adventures", 2, 1000);

        network.addStation(s_namboku_1);
        network.addStation(s_namboku_2);
        network.addStation(s_namboku_3);
        network.addStation(s_namboku_4);

        network.addStation(s3_valid_n1);
        network.addStation(s3_valid_n3);

        Assert.assertEquals(4500, network.distance(s3_valid_n1.getName(),s_namboku_4.getName()),0.0);
    }

    //@Test
    /**public void distance_metro_japonais(){
        Station join_station_1 = new Station("Sapporo");
        Station join_station_2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", "Toho Line", 1, 0.0);
        Station h02 = new Station("Shindo higashi", "Toho Line", 2, 0.9);
        Station h03 = new Station( "Motomashi", "Toho Line", 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", "Toho Line", 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", "Toho Line", 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", "Toho Line", 6, 5.4);
        join_station_1.addLine("Toho Line", 7, 6.7);
        join_station_2.addLine("Toho Line", 8, 7.3);
        Station h09 = new Station("Hosui Susukino", "Toho Line", 9, 8.1);
        Station h10 = new Station("Gakuen mae", "Toho Line", 10, 9.5);
        Station h11 = new Station("Toyohira koen", "Toho Line", 11, 10.4);
        Station h12 = new Station("Misono", "Toho Line", 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", "Toho Line", 13, 12.6);
        Station h14 = new Station("Fukuzumi", "Toho Line", 14, 13.6);
        network.addStation(h01);network.addStation(h02);network.addStation(h03);network.addStation(h04);network.addStation(h05);network.addStation(h06);network.addStation(join_station_1);network.addStation(join_station_2);network.addStation(h09);network.addStation(h10);network.addStation(h11);network.addStation(h12);network.addStation(h13);network.addStation(h14);

        Station n01 = new Station("Asabu", "Namboku Line", 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", "Namboku Line", 2, 1.0);
        Station n03 = new Station("Kita nijuyo jo", "Namboku Line", 3, 2.2);
        Station n04 = new Station("Kita juhachi jo", "Namboku Line", 4, 3.1);
        Station n05 = new Station("Kita juni jo", "Namboku Line", 5, 3.9);
        join_station_1.addLine("Namboku Line", 6, 4.9);
        join_station_2.addLine("Namboku Line", 7, 5.5);
        Station n08 = new Station("Susukino", "Namboku Line", 8, 6.1);
        Station n09 = new Station("Nakajima koen", "Namboku Line", 9, 6.8);
        Station n10 = new Station("Horohira bashi", "Namboku Line", 10, 7.8);
        Station n11 = new Station("Nakanoshima", "Namboku Line", 11, 8.3);
        Station n12 = new Station("Hiragishi", "Namboku Line", 12, 9.0);
        Station n13 = new Station("Minami Hiragishi", "Namboku Line", 13, 10.1);
        Station n14 = new Station("Sumikawa", "Namboku Line", 14, 11.3);
        Station n15 = new Station("Jieitai mae", "Namboku Line", 15, 12.6);
        Station n16 = new Station("Makomanai", "Namboku Line", 16, 14.3);
        network.addStation(n01);network.addStation(n02);network.addStation(n03);network.addStation(n04);network.addStation(n05);network.addStation(join_station_1);network.addStation(join_station_2);network.addStation(n08);network.addStation(n09);network.addStation(n10);network.addStation(n11);network.addStation(n12);network.addStation(n13);network.addStation(n14);network.addStation(n15);network.addStation(n16);

        Station t01 = new Station("Miyanosawa", "Tozai Line", 1, 0.0);
        Station t02 = new Station("Hassamu minami", "Tozai Line", 2, 1.5);
        Station t03 = new Station("Kotoni", "Tozai Line", 3, 2.8);
        Station t04 = new Station("Nijuyonken", "Tozai Line", 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", "Tozai Line", 5, 4.9);
        Station t06 = new Station("Maruyama koen", "Tozai Line", 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", "Tozai Line", 7, 6.6);
        Station t08 = new Station("Nishi juitchome", "Tozai Line", 8, 7.5);
        join_station_2.addLine("Tozai Line", 9, 8.5);
        Station t10 = new Station("Bus Center mae", "Tozai Line", 10, 9.3);
        Station t11 = new Station("Kikusui", "Tozai Line", 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", "Tozai Line", 12, 11.6);
        Station t13 = new Station("Shiroishi", "Tozai Line", 13, 12.7);
        Station t14 = new Station("Nango nana chome", "Tozai Line", 14, 14.1);
        Station t15 = new Station("Nango jusan chome", "Tozai Line", 15, 15.2);
        Station t16 = new Station("Nango juhatchome", "Tozai Line", 16, 16.4);
        Station t17 = new Station("Oyachi", "Tozai Line", 17, 17.9);
        Station t18 = new Station("Hibarigaoka", "Tozai Line", 18, 18.9);
        Station t19 = new Station("Shin Sapporo", "Tozai Line", 19, 20.1);
        network.addStation(t01);network.addStation(t02);network.addStation(t03);network.addStation(t04);network.addStation(t05);network.addStation(t06);network.addStation(t07);network.addStation(t08);network.addStation(join_station_2);network.addStation(t10);network.addStation(t11);network.addStation(t12);network.addStation(t13);network.addStation(t14);network.addStation(t15);network.addStation(t16);network.addStation(t17);network.addStation(t18);network.addStation(t19);

        Assert.assertEquals(15.8, network.distance(h01.getName(),t01.getName()),0.0);
    }*/

    // ================================================================================== //
    //Pierre


    @Test
    public void testNetwork(){
        Assert.assertNotNull(n);
    }

    @Test
    public void testAjoutStation(){ //idem testRecupererStationParNom()
        n.addStation(s1);
        Assert.assertEquals(n.getStationByName("Shibuyatchaka"), s1);
    }

    @Test
    public void testAjoutStationComplete(){
        n.addStation(s2Pierre);
        Assert.assertEquals(n.getStationByName("Bus Center bae"), s2Pierre);
    }

    @Test
    public void testAjoutStationDejaExistante(){
        n.addStation(s1);
        int nombre = n.getLines().size();
        n.addStation(s1);
        Assert.assertEquals(nombre,n.getLines().size());
    }

    @Test
    public void testRecupererStationFausseParNom(){
        n.addStation(s1);
        Assert.assertNotEquals(n.getStationByName("Bus Center mae"), s1);
    }

    @Test
    public void testRecupererStationInexistanteParNom(){
        Assert.assertNull(n.getStationByName("Bus Center bae"));
    }

    @Test
    public void testRecupererStationParLigneEtNom(){
        n.addStation(s2Pierre);
        Assert.assertEquals(n.getStationByLineAndNumber(toho, 10), s2Pierre);
    }

    @Test
    public void testRecupererStationIncompleteParLigneEtNom(){
        n.addStation(s1);
        Assert.assertNotEquals(n.getStationByLineAndNumber("euuuuuuuh", 42), s1);
    }

    @Test
    public void testRecupererStationInexistanteParLigneEtNom(){
        Assert.assertNull(n.getStationByLineAndNumber("euuuuuuuh", 42));
    }

    //CAS 1: Network contient au moins 1 ligne
    //CAS 1bis: Ligne contient au moins 1 Station
    //CAS 2: La 1er Station d'une ligne porte le numéro 1 et se trouve au kilomètre 0
    //CAS 3: Il n'y a pas de duplicata sur les numéros de Station et les numéros sont contigues
    //CAS 4: Les kilomètres des Stations est strictement croissant
    //CAS 5: Toutes les Stations sont atteignables depuis n'importe quelle Station

    @Test
    public void testCAS1Faux(){
        n.addStation(s1);
        Assert.assertEquals(n.getLines().size(), 0);
        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS1bisFaux(){
        Station h01 = new Station("Sakaemachi", toho, 42, 1.0);
        n.addStation(h01);
        h01.removeLine(toho);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS2FauxNombre(){
        Station h01 = new Station("Sakaemachi", toho, 12, 0.0);
        n.addStation(h01);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS2FauxDistance(){
        Station n01 = new Station("Asabu", namboku, 1, 0.1);
        n.addStation(n01);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS3FauxDuplicata(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 2, 2.1);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);
        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS3FauxContigue(){
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", toho, 4, 3.5);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);
        n.addStation(h02);
        n.addStation(h03);
        n.addStation(h04);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS4FauxDecroissant(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 2.1);
        Station h03 = new Station( "Motomashi", toho, 3, 0.9);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);
        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS4FauxEgaux(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 0.9);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);
        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS5FauxParallele(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);

        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS5FauxCorrespondance(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 2.3);

        Station t01 = new Station("Miyanosawa", "Tozai Line", 1, 0.0);

        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        n.addStation(t01);

        h02.addLine(namboku, 3, 4.2);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testCAS5FauxCorrespondance2(){
        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);

        Station n01 = new Station("Asabu", namboku, 2, 0.1);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 3, 2.3);

        Station t01 = new Station("Miyanosawa", "Tozai Line", 1, 0.0);

        n.addStation(h01);
        n.addStation(h02);
        n.addStation(h03);

        n.addStation(n01);
        n.addStation(n02);

        n.addStation(t01);

        h01.addLine(namboku, 1, 0.0);
        h02.addLine(namboku, 4, 4.2);

        Assert.assertFalse(n.isValid());
    }

    @Test
    public void testisValidToho(){
        Station c1 = new Station("Sapporo");
        Station c2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", toho, 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", toho, 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", toho, 6, 5.4);
        c1.addLine(toho, 7, 6.7);
        c2.addLine(toho, 8, 7.3);
        Station h09 = new Station("Hosui Susukino", toho, 9, 8.1);
        Station h10 = new Station("Gakuen mae", toho, 10, 9.5);
        Station h11 = new Station("Toyohira koen", toho, 11, 10.4);
        Station h12 = new Station("Misono", toho, 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", toho, 13, 12.6);
        Station h14 = new Station("Fukuzumi", toho, 14, 13.6);
        n.addStation(h01);n.addStation(h02);n.addStation(h03);n.addStation(h04);n.addStation(h05);n.addStation(h06);n.addStation(c1);n.addStation(c2);n.addStation(h09);n.addStation(h10);n.addStation(h11);n.addStation(h12);n.addStation(h13);n.addStation(h14);

        Assert.assertTrue(n.isValid());
    }

    @Test
    public void testisValidHokkaido(){
        Station c1 = new Station("Sapporo");
        Station c2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", toho, 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", toho, 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", toho, 6, 5.4);
        c1.addLine(toho, 7, 6.7);
        c2.addLine(toho, 8, 7.3);
        Station h09 = new Station("Hosui Susukino", toho, 9, 8.1);
        Station h10 = new Station("Gakuen mae", toho, 10, 9.5);
        Station h11 = new Station("Toyohira koen", toho, 11, 10.4);
        Station h12 = new Station("Misono", toho, 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", toho, 13, 12.6);
        Station h14 = new Station("Fukuzumi", toho, 14, 13.6);
        n.addStation(h01);n.addStation(h02);n.addStation(h03);n.addStation(h04);n.addStation(h05);n.addStation(h06);n.addStation(c1);n.addStation(c2);n.addStation(h09);n.addStation(h10);n.addStation(h11);n.addStation(h12);n.addStation(h13);n.addStation(h14);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);
        Station n03 = new Station("Kita nijuyo jo", namboku, 3, 2.2);
        Station n04 = new Station("Kita juhachi jo", namboku, 4, 3.1);
        Station n05 = new Station("Kita juni jo", namboku, 5, 3.9);
        c1.addLine(namboku, 6, 4.9);
        c2.addLine(namboku, 7, 5.5);
        Station n08 = new Station("Susukino", namboku, 8, 6.1);
        Station n09 = new Station("Nakajima koen", namboku, 9, 6.8);
        Station n10 = new Station("Horohira bashi", namboku, 10, 7.8);
        Station n11 = new Station("Nakanoshima", namboku, 11, 8.3);
        Station n12 = new Station("Hiragishi", namboku, 12, 9.0);
        Station n13 = new Station("Minami Hiragishi", namboku, 13, 10.1);
        Station n14 = new Station("Sumikawa", namboku, 14, 11.3);
        Station n15 = new Station("Jieitai mae", namboku, 15, 12.6);
        Station n16 = new Station("Makomanai", namboku, 16, 14.3);
        n.addStation(n01);n.addStation(n02);n.addStation(n03);n.addStation(n04);n.addStation(n05);n.addStation(c1);n.addStation(c2);n.addStation(n08);n.addStation(n09);n.addStation(n10);n.addStation(n11);n.addStation(n12);n.addStation(n13);n.addStation(n14);n.addStation(n15);n.addStation(n16);

        Station t01 = new Station("Miyanosawa", tozai, 1, 0.0);
        Station t02 = new Station("Hassamu minami", tozai, 2, 1.5);
        Station t03 = new Station("Kotoni", tozai, 3, 2.8);
        Station t04 = new Station("Nijuyonken", tozai, 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", tozai, 5, 4.9);
        Station t06 = new Station("Maruyama koen", tozai, 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", tozai, 7, 6.6);
        Station t08 = new Station("Nishi juitchome", tozai, 8, 7.5);
        c2.addLine(tozai, 9, 8.5);
        Station t10 = new Station("Bus Center mae", tozai, 10, 9.3);
        Station t11 = new Station("Kikusui", tozai, 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", tozai, 12, 11.6);
        Station t13 = new Station("Shiroishi", tozai, 13, 12.7);
        Station t14 = new Station("Nango nana chome", tozai, 14, 14.1);
        Station t15 = new Station("Nango jusan chome", tozai, 15, 15.2);
        Station t16 = new Station("Nango juhatchome", tozai, 16, 16.4);
        Station t17 = new Station("Oyachi", tozai, 17, 17.9);
        Station t18 = new Station("Hibarigaoka", tozai, 18, 18.9);
        Station t19 = new Station("Shin Sapporo", tozai, 19, 20.1);
        n.addStation(t01);n.addStation(t02);n.addStation(t03);n.addStation(t04);n.addStation(t05);n.addStation(t06);n.addStation(t07);n.addStation(t08);n.addStation(c2);n.addStation(t10);n.addStation(t11);n.addStation(t12);n.addStation(t13);n.addStation(t14);n.addStation(t15);n.addStation(t16);n.addStation(t17);n.addStation(t18);n.addStation(t19);

        Assert.assertTrue(n.isValid());
    }

    @Test
    public void testDistanceReseauInvalide(){
        Station n01 = new Station("Asabu", namboku, 1, 0.1);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertTrue(n.distance("Asabu", "Kita sanjuyo jo") < 0);
    }

    @Test
    public void testDistanceNull(){
        Station n01 = new Station("Asabu", namboku, 1, 0.0);

        n.addStation(n01);

        Assert.assertTrue(n.distance("Asabu", null) < 0);
    }

    @Test
    public void testDistanceNomInconnu(){
        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertTrue(n.distance("Asab", "Kita sanjuyo jo") < 0);
    }

    @Test
    public void testDistanceZero(){
        Station n01 = new Station("Asabu", namboku, 1, 0.0);

        n.addStation(n01);

        Assert.assertEquals(0.0, n.distance("Asabu", "Asabu"), 0.0);
    }

    @Test
    public void testDistanceSimple(){
        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);

        n.addStation(n01);
        n.addStation(n02);

        Assert.assertEquals(1.0, n.distance("Asabu", "Kita sanjuyo jo"), 0.0);
    }

    @Test
    public void testDistanceComplexe(){
        Station c2 = new Station("Odori");

        Station t01 = new Station("Miyanosawa", tozai, 1, 0.0);
        Station t02 = new Station("Hassamu minami", tozai, 2, 1.5);
        Station t03 = new Station("Kotoni", tozai, 3, 2.8);
        Station t04 = new Station("Nijuyonken", tozai, 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", tozai, 5, 4.9);
        Station t06 = new Station("Maruyama koen", tozai, 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", tozai, 7, 6.6);
        Station t08 = new Station("Nishi juitchome", tozai, 8, 7.5);
        c2.addLine(tozai, 9, 8.5);
        Station t10 = new Station("Bus Center mae", tozai, 10, 9.3);
        Station t11 = new Station("Kikusui", tozai, 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", tozai, 12, 11.6);
        Station t13 = new Station("Shiroishi", tozai, 13, 12.7);
        Station t14 = new Station("Nango nana chome", tozai, 14, 14.1);
        Station t15 = new Station("Nango jusan chome", tozai, 15, 15.2);
        Station t16 = new Station("Nango juhatchome", tozai, 16, 16.4);
        Station t17 = new Station("Oyachi", tozai, 17, 17.9);
        Station t18 = new Station("Hibarigaoka", tozai, 18, 18.9);
        Station t19 = new Station("Shin Sapporo", tozai, 19, 20.1);
        n.addStation(t01);n.addStation(t02);n.addStation(t03);n.addStation(t04);n.addStation(t05);n.addStation(t06);n.addStation(t07);n.addStation(t08);n.addStation(c2);n.addStation(t10);n.addStation(t11);n.addStation(t12);n.addStation(t13);n.addStation(t14);n.addStation(t15);n.addStation(t16);n.addStation(t17);n.addStation(t18);n.addStation(t19);

        Assert.assertEquals(10.8, n.distance("Shin Sapporo", "Bus Center mae"), 0.0);
    }

    @Test
    public void testDistanceTozai(){
        Station c1 = new Station("Sapporo");
        Station c2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", toho, 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", toho, 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", toho, 6, 5.4);
        c1.addLine(toho, 7, 6.7);
        c2.addLine(toho, 8, 7.3);
        Station h09 = new Station("Hosui Susukino", toho, 9, 8.1);
        Station h10 = new Station("Gakuen mae", toho, 10, 9.5);
        Station h11 = new Station("Toyohira koen", toho, 11, 10.4);
        Station h12 = new Station("Misono", toho, 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", toho, 13, 12.6);
        Station h14 = new Station("Fukuzumi", toho, 14, 13.6);
        n.addStation(h01);n.addStation(h02);n.addStation(h03);n.addStation(h04);n.addStation(h05);n.addStation(h06);n.addStation(c1);n.addStation(c2);n.addStation(h09);n.addStation(h10);n.addStation(h11);n.addStation(h12);n.addStation(h13);n.addStation(h14);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);
        Station n03 = new Station("Kita nijuyo jo", namboku, 3, 2.2);
        Station n04 = new Station("Kita juhachi jo", namboku, 4, 3.1);
        Station n05 = new Station("Kita juni jo", namboku, 5, 3.9);
        c1.addLine(namboku, 6, 4.9);
        c2.addLine(namboku, 7, 5.5);
        Station n08 = new Station("Susukino", namboku, 8, 6.1);
        Station n09 = new Station("Nakajima koen", namboku, 9, 6.8);
        Station n10 = new Station("Horohira bashi", namboku, 10, 7.8);
        Station n11 = new Station("Nakanoshima", namboku, 11, 8.3);
        Station n12 = new Station("Hiragishi", namboku, 12, 9.0);
        Station n13 = new Station("Minami Hiragishi", namboku, 13, 10.1);
        Station n14 = new Station("Sumikawa", namboku, 14, 11.3);
        Station n15 = new Station("Jieitai mae", namboku, 15, 12.6);
        Station n16 = new Station("Makomanai", namboku, 16, 14.3);
        n.addStation(n01);n.addStation(n02);n.addStation(n03);n.addStation(n04);n.addStation(n05);n.addStation(c1);n.addStation(c2);n.addStation(n08);n.addStation(n09);n.addStation(n10);n.addStation(n11);n.addStation(n12);n.addStation(n13);n.addStation(n14);n.addStation(n15);n.addStation(n16);

        Station t01 = new Station("Miyanosawa", tozai, 1, 0.0);
        Station t02 = new Station("Hassamu minami", tozai, 2, 1.5);
        Station t03 = new Station("Kotoni", tozai, 3, 2.8);
        Station t04 = new Station("Nijuyonken", tozai, 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", tozai, 5, 4.9);
        Station t06 = new Station("Maruyama koen", tozai, 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", tozai, 7, 6.6);
        Station t08 = new Station("Nishi juitchome", tozai, 8, 7.5);
        c2.addLine(tozai, 9, 8.5);
        Station t10 = new Station("Bus Center mae", tozai, 10, 9.3);
        Station t11 = new Station("Kikusui", tozai, 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", tozai, 12, 11.6);
        Station t13 = new Station("Shiroishi", tozai, 13, 12.7);
        Station t14 = new Station("Nango nana chome", tozai, 14, 14.1);
        Station t15 = new Station("Nango jusan chome", tozai, 15, 15.2);
        Station t16 = new Station("Nango juhatchome", tozai, 16, 16.4);
        Station t17 = new Station("Oyachi", tozai, 17, 17.9);
        Station t18 = new Station("Hibarigaoka", tozai, 18, 18.9);
        Station t19 = new Station("Shin Sapporo", tozai, 19, 20.1);
        n.addStation(t01);n.addStation(t02);n.addStation(t03);n.addStation(t04);n.addStation(t05);n.addStation(t06);n.addStation(t07);n.addStation(t08);n.addStation(c2);n.addStation(t10);n.addStation(t11);n.addStation(t12);n.addStation(t13);n.addStation(t14);n.addStation(t15);n.addStation(t16);n.addStation(t17);n.addStation(t18);n.addStation(t19);

        Assert.assertEquals(20.1, n.distance("Miyanosawa", "Shin Sapporo"), 0.0);
    }

    /*
    @Test
    public void testDistanceHokkaido(){
        Station c1 = new Station("Sapporo");
        Station c2 = new Station("Odori");

        Station h01 = new Station("Sakaemachi", toho, 1, 0.0);
        Station h02 = new Station("Shindo higashi", toho, 2, 0.9);
        Station h03 = new Station( "Motomashi", toho, 3, 2.1);
        Station h04 = new Station("Kanjo dori higashi", toho, 4, 3.5);
        Station h05 = new Station("Higashi kuyakusho mae", toho, 5, 4.5);
        Station h06 = new Station("Kita jusanjo higashi", toho, 6, 5.4);
        c1.addLine(toho, 7, 6.7);
        c2.addLine(toho, 8, 7.3);
        Station h09 = new Station("Hosui Susukino", toho, 9, 8.1);
        Station h10 = new Station("Gakuen mae", toho, 10, 9.5);
        Station h11 = new Station("Toyohira koen", toho, 11, 10.4);
        Station h12 = new Station("Misono", toho, 12, 11.4);
        Station h13 = new Station("Tsukisamu chuo", toho, 13, 12.6);
        Station h14 = new Station("Fukuzumi", toho, 14, 13.6);
        n.addStation(h01);n.addStation(h02);n.addStation(h03);n.addStation(h04);n.addStation(h05);n.addStation(h06);n.addStation(c1);n.addStation(c2);n.addStation(h09);n.addStation(h10);n.addStation(h11);n.addStation(h12);n.addStation(h13);n.addStation(h14);

        Station n01 = new Station("Asabu", namboku, 1, 0.0);
        Station n02 = new Station("Kita sanjuyo jo", namboku, 2, 1.0);
        Station n03 = new Station("Kita nijuyo jo", namboku, 3, 2.2);
        Station n04 = new Station("Kita juhachi jo", namboku, 4, 3.1);
        Station n05 = new Station("Kita juni jo", namboku, 5, 3.9);
        c1.addLine(namboku, 6, 4.9);
        c2.addLine(namboku, 7, 5.5);
        Station n08 = new Station("Susukino", namboku, 8, 6.1);
        Station n09 = new Station("Nakajima koen", namboku, 9, 6.8);
        Station n10 = new Station("Horohira bashi", namboku, 10, 7.8);
        Station n11 = new Station("Nakanoshima", namboku, 11, 8.3);
        Station n12 = new Station("Hiragishi", namboku, 12, 9.0);
        Station n13 = new Station("Minami Hiragishi", namboku, 13, 10.1);
        Station n14 = new Station("Sumikawa", namboku, 14, 11.3);
        Station n15 = new Station("Jieitai mae", namboku, 15, 12.6);
        Station n16 = new Station("Makomanai", namboku, 16, 14.3);
        n.addStation(n01);n.addStation(n02);n.addStation(n03);n.addStation(n04);n.addStation(n05);n.addStation(c1);n.addStation(c2);n.addStation(n08);n.addStation(n09);n.addStation(n10);n.addStation(n11);n.addStation(n12);n.addStation(n13);n.addStation(n14);n.addStation(n15);n.addStation(n16);

        Station t01 = new Station("Miyanosawa", tozai, 1, 0.0);
        Station t02 = new Station("Hassamu minami", tozai, 2, 1.5);
        Station t03 = new Station("Kotoni", tozai, 3, 2.8);
        Station t04 = new Station("Nijuyonken", tozai, 4, 3.7);
        Station t05 = new Station("Nishi nijuhatchome", tozai, 5, 4.9);
        Station t06 = new Station("Maruyama koen", tozai, 6, 5.7);
        Station t07 = new Station("Nishi juhatchome", tozai, 7, 6.6);
        Station t08 = new Station("Nishi juitchome", tozai, 8, 7.5);
        c2.addLine(tozai, 9, 8.5);
        Station t10 = new Station("Bus Center mae", tozai, 10, 9.3);
        Station t11 = new Station("Kikusui", tozai, 11, 10.4);
        Station t12 = new Station("Higashi Sapporo", tozai, 12, 11.6);
        Station t13 = new Station("Shiroishi", tozai, 13, 12.7);
        Station t14 = new Station("Nango nana chome", tozai, 14, 14.1);
        Station t15 = new Station("Nango jusan chome", tozai, 15, 15.2);
        Station t16 = new Station("Nango juhatchome", tozai, 16, 16.4);
        Station t17 = new Station("Oyachi", tozai, 17, 17.9);
        Station t18 = new Station("Hibarigaoka", tozai, 18, 18.9);
        Station t19 = new Station("Shin Sapporo", tozai, 19, 20.1);
        n.addStation(t01);n.addStation(t02);n.addStation(t03);n.addStation(t04);n.addStation(t05);n.addStation(t06);n.addStation(t07);n.addStation(t08);n.addStation(c2);n.addStation(t10);n.addStation(t11);n.addStation(t12);n.addStation(t13);n.addStation(t14);n.addStation(t15);n.addStation(t16);n.addStation(t17);n.addStation(t18);n.addStation(t19);

        Assert.assertEquals(10.8, n.distance("Shin Sapporo", "Makomanai"), 0.0);
    }

    @Test
    public void testDistanceInattendue(){
        Station t01 = new Station("Depart", "Slow Line", 1, 0.0);
        Station t02 = new Station("Intermediaire1", "Slow Line", 2, 1.0);
        Station t03 = new Station("Intermediaire2", "Slow Line", 3, 2.7);
        Station t04 = new Station("Intermediaire3", "Slow Line", 4, 4.0);
        Station t05 = new Station("Arrivee", "Slow Line", 5, 4.7);

        t02.addLine("Fast Line", 1, 0.0);
        t04.addLine("Fast Line", 2, 1.2);

        n.addStation(t01);
        n.addStation(t02);
        n.addStation(t03);
        n.addStation(t04);
        n.addStation(t05);

        Assert.assertEquals(2.9, n.distance("Depart", "Arrivee"), 0.0);

    }
    */
}
