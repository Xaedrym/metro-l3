package fr.ufc.l3info.oprog.parser;

import fr.ufc.l3info.oprog.Network;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 *  Quelques tests pour le package parser.
 */
public class NetworkParserTest {

    /** Chemin vers les fichiers de test */
    final String path = "./target/classes/data/";

    /** Instance singleton du parser de stations */
    final NetworkParser parser = NetworkParser.getInstance();

    @Test
    public void testTokenizer() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroOK.txt"));
        assertEquals(53, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
        assertEquals(1, tokens.get(0).getLigne());
        assertEquals(1, tokens.get(0).getColonne());
        assertEquals(16, tokens.get(tokens.size()-1).getLigne());
        assertEquals(15, tokens.get(tokens.size()-1).getColonne());
    }


    @Test
    public void testParserOK() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        assertTrue(n instanceof ASTListeLignes);
        assertEquals(3, n.getNumChildren());

        assertEquals(6, n.getChild(0).getNumChildren());
        assertEquals(4, n.getChild(1).getNumChildren());
        assertEquals(4, n.getChild(2).getNumChildren());

        for (ASTNode n1 : n) {
            assertTrue(n1 instanceof ASTLigne);
            for (ASTNode nn1 : n1) {
                if (!(nn1 instanceof ASTChaine)) {
                    assertTrue(nn1 instanceof ASTDeclaration);
                    assertTrue(nn1.getChild(0) instanceof ASTChaine);
                    assertTrue(nn1.getChild(1) instanceof ASTNombre);
                }
            }
        }
    }

    @Test
    public void testNetworkBuilder() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File(path + "metroOK.txt"));
        NetworkBuilder builder = new NetworkBuilder();
        n.accept(builder);
        Network net = builder.getNetwork();
        assertEquals(3, net.getLines().size());
        assertNotNull(net.getStationByName("s3"));
        assertTrue(net.getStationByName("s3").getLines().contains("line0"));
        assertTrue(net.getStationByName("s3").getLines().contains("line1"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO1() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO1.txt"));
        assertEquals(53, tokens.size());
        String[] expected = {
                "ligne", "line0", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
        assertEquals(1, tokens.get(0).getLigne());
        assertEquals(1, tokens.get(0).getColonne());
        assertEquals(16, tokens.get(tokens.size()-1).getLigne());
        assertEquals(15, tokens.get(tokens.size()-1).getColonne());
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO1_KO() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO1.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO2() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO2.txt"));
        assertEquals(53, tokens.size());
        String[] expected = {
                "ligne", "\"\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test
    public void testParserOK_metroKO2_KO() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO2.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroVoid() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroVoid.txt"));
        assertEquals(0, tokens.size());
        String[] expected ={};
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test
    public void testParserOK_metroVoid() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroVoid.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO3() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO3.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO3() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO3.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO4() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO4.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"","{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO4() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO4.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO5() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO5.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"","{",
                "\"s0\"", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO5() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO5.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO6() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO6.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"","{",
                "\"s0\"", ":", "0.0", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO6() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO6.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO7() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO7.txt"));
        assertEquals(54, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{", ",",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO7() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO7.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO8() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO8.txt"));
        assertEquals(54, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{", "\'",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO8() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO8.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO9() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO9.txt"));
        assertEquals(54, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{", "}",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO9() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO9.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO10() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO10.txt"));
        assertEquals(54, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7", ",",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO10() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO10.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO11() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO11.txt"));
        assertEquals(53, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{",
                "\"s0\"", ",", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO11() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO11.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO12() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO12.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "\"line0\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO12() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO12.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_metroKO13() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "metroKO13.txt"));
        assertEquals(52, tokens.size());
        String[] expected = {
                "ligne", "\"line0\"", "{",
                ":", "0.0", ",", "\"s1\"", ":", "1", ",", "\"s2\"", ":", "4", ",", "\"s3\"", ":", "6", ",", "\"s4\"", ":", "7",
                "}",
                "ligne", "\"line1\"", "{",
                "\"s0\"", ":", "0.0", ",", "\"s3\"", ":", "3.0", ",", "\"s5\"", ":", "5.0",
                "}",
                "ligne", "\"line2\"", "{",
                "\"s1\"", ":", "0.0", ",", "\"s5\"", ":", "2", ",", "\"s4\"", ":", "5.0", "}"
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test(expected = NetworkParserException.class)
    public void testParserOK_metroKO13() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO13.txt"));
    }

    @Test
    public void testParserOK_metroKO14() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "metroKO14.txt"));
    }

    // ----------------------------------------------------------------------------- //

    @Test
    public void testTokenizer_sapporo() throws NetworkParserException, IOException {
        List<Token> tokens = NetworkFileTokenizer.tokenize(new File(path + "sapporo.txt"));
        assertEquals(205, tokens.size());
        String[] expected = {
                "ligne", "\"Namboku\"", "{",
                "\"Asabu\"",":", "0", ",",
                "\"Kita-Sanjūyo-Jō\"", ":", "1.0", ",",
                "\"Kita-Nijūyo-Jō\"", ":", "2.2", ",",
                "\"Kita-Jūhachi-Jō\"", ":", "3.1", ",",
                "\"Kita-Jūni-Jō\"", ":", "3.9", ",",
                "\"Sapporo\"", ":", "4.9", ",",
                "\"Ōdōri\"", ":", "5.5", ",",
                "\"Susukino\"", ":", "6.1", ",",
                "\"Nakajima-Kōen\"", ":", "6.8", ",",
                "\"Horohira-Bashi\"", ":", "7.8", ",",
                "\"Nakanoshima\"", ":", "8.3", ",",
                "\"Hiragishi\"", ":", "9.0", ",",
                "\"Minami-Hiragishi\"", ":", "10.1", ",",
                "\"Sumikawa\"", ":", "11.3", ",",
                "\"Jieitai-Mae\"", ":", "12.6", ",",
                "\"Makomanai\"", ":", "14.3",
                "}",
                "ligne", "\"Tōzai\"", "{",
                "\"Miyanosawa\"",":", "0", ",",
                "\"Hassamu-Minami\"", ":", "1.5", ",",
                "\"Kotoni\"", ":", "2.8", ",",
                "\"Nijūyon-Ken\"", ":", "3.7", ",",
                "\"Nishi-Nijūhatchōme\"", ":", "4.9", ",",
                "\"Maruyama-Kōen\"", ":", "5.7", ",",
                "\"Nishi-Jūhatchōme\"", ":", "6.6", ",",
                "\"Nishi-Jūitchōme\"", ":", "7.5", ",",
                "\"Ōdōri\"", ":", "8.5", ",",
                "\"Bus Center-Mae\"", ":", "9.3", ",",
                "\"Kikusui\"", ":", "10.4", ",",
                "\"Higashi-Sapporo\"", ":", "11.6", ",",
                "\"Shiroishi\"", ":", "12.7", ",",
                "\"Nangō-Nanachōme\"", ":", "14.1", ",",
                "\"Nangō-Jūsanchōme\"", ":", "15.2", ",",
                "\"Nangō-Jūhatchōme\"", ":", "16.4", ",",
                "\"Ōyachi\"", ":", "17.9", ",",
                "\"Hibarigaoka\"", ":", "18.9", ",",
                "\"Shin-Sapporo\"", ":", "20.1",
                "}",
                "ligne", "\"Tōhō\"", "{",
                "\"Sakaemachi\"",":", "0", ",",
                "\"Shindō-Higashi\"", ":", "0.9", ",",
                "\"Motomachi\"", ":", "2.1", ",",
                "\"Kanjō-Dōri-Higashi\"", ":", "3.5", ",",
                "\"Higashi-Kuyakusho-Mae\"", ":", "4.5", ",",
                "\"Kita-Jūsan-Jō-Higashi\"", ":", "5.4", ",",
                "\"Sapporo\"", ":", "6.7", ",",
                "\"Ōdōri\"", ":", "7.3", ",",
                "\"Hōsui-Susukino\"", ":", "8.1", ",",
                "\"Gakuen-Mae\"", ":", "9.5", ",",
                "\"Toyohira-Kōen\"", ":", "10.4", ",",
                "\"Misono\"", ":", "11.4", ",",
                "\"Tsukisamu-Chūō\"", ":", "12.6", ",",
                "\"Fukuzumi\"", ":", "13.6",
                "}",
        };
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], tokens.get(i).getValeur());
        }
    }

    @Test
    public void testParserOK_sapporo() throws NetworkParserException, IOException {
        ASTNode n = parser.parse(new File(path + "sapporo.txt"));
    }
}