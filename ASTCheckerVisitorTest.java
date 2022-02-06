package fr.ufc.l3info.oprog.parser;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import java.util.Map;

public class ASTCheckerVisitorTest {

    final NetworkParser parser = NetworkParser.getInstance();
    /** Chemin vers les fichiers de test */
    final String path = "./target/classes/data/";

    public int getNbErrors(Map<String, ERROR_KIND> errors, ERROR_KIND error){
        int nb_error = 0;
        for(Map.Entry<String, ERROR_KIND> elem : errors.entrySet()){
            if(elem.getValue() == error){
                nb_error++;
            }
        }
        return nb_error;
    }

    @Test
    public void checkKO_1erreur_vide() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metroVoid.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1,checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_LINE_LIST));
    }

    @Test
    public void checkKO_2erreur_declaration() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_declaration.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(2, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_STATION_NAME));
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_2erreur_declaration_trim() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_declaration_trim.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(2, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_STATION_NAME));
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_1erreur_FirstStationNotZero() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_firstStation_NotZero.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_2erreur_FirstStationNegatif() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_firstStation_Negatif.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(2, checker.getErrors().size());
        Assert.assertEquals(2, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_1erreur_duplicateDist() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_duplicate_dist.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_4erreur_FirstStationNegatifAndDuplicate() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_firstStation_Negatifbis.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(4, checker.getErrors().size());
        Assert.assertEquals(4, getNbErrors(checker.getErrors(), ERROR_KIND.WRONG_NUMBER_VALUE));
    }

    @Test
    public void checkKO_1erreur_DuplicateStationName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_duplicateStationName.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.DUPLICATE_STATION_NAME));
    }

    @Test
    public void checkKO_1erreur_DuplicateLineName() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_duplicateLineName.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.DUPLICATE_LINE_NAME));
    }

    @Test
    public void checkKO_1erreur_LineNameVoid() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_LineVoid.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_LINE_NAME));
    }

    @Test
    public void checkKO_1erreur_LineNameAssimilee() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_LineAssimilee.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_LINE_NAME));
    }

    @Test
    public void checkKO_1erreur_LineNameVoidAndAssimilee() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_LineVoidAndAssimilee.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(2, checker.getErrors().size());
        Assert.assertEquals(2, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_LINE_NAME));

    }

    @Test
    public void checkKO_1erreur_StationNameVoidAndAssimilee() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_stationVoidAndAssimilee.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(2, checker.getErrors().size());
        Assert.assertEquals(2, getNbErrors(checker.getErrors(), ERROR_KIND.EMPTY_STATION_NAME));
    }

    @Test
    public void checkKO_1erreur_LigneIsolee() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metro_test_ligneIsolated.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(1, checker.getErrors().size());
        Assert.assertEquals(1, getNbErrors(checker.getErrors(), ERROR_KIND.UNREACHABLE_LINE));
    }

    @Test
    public void checkOK() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "metroOK.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(0, checker.getErrors().size());
    }

    @Test
    public void checkOK_Sapporo() throws IOException, NetworkParserException {
        ASTNode n = parser.parse(new File((path + "sapporo.txt")));
        ASTCheckerVisitor checker = new ASTCheckerVisitor();
        n.accept(checker);
        Assert.assertEquals(0, checker.getErrors().size());
    }

}
