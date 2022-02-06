package fr.ufc.l3info.oprog.parser;

import java.util.*;

/**
 * Vérification du fichier parsé :
 *  - liste de lignes non vide
 *  - nom de ligne vide
 *  - nom de stations non vides
 *  - noms de stations uniques sur une ligne
 *  - distance kilométrique positive
 *  - distance kilométrique strictement croissante à partir de 0
 *  - toutes les lignes sont accessibles
 */
public class ASTCheckerVisitor implements ASTNodeVisitor {

    private HashMap<String, ERROR_KIND> errors;
    private Set<String> noms_lines;

    public ASTCheckerVisitor() {
        errors = new HashMap<>();
        noms_lines = new HashSet<>();
    }

    public Map<String, ERROR_KIND> getErrors() {
        return new HashMap<>(errors);
    }

    @Override
    public Object visit(ASTNode n) {
        return null;
    }

    @Override
    public Object visit(ASTListeLignes n) {
        // Empty Line List
        if (n.getNumChildren() == 0){
            errors.put(n.getLCPrefix() + " La liste de lignes est vide.", ERROR_KIND.EMPTY_LINE_LIST);
        }else{
            //UnreachableLine
            HashMap<String, List<String>> map_network = new HashMap<>();
            for(int i=0; i< n.getNumChildren(); i++){
                String key = (String) n.getChild(i).getChild(0).accept(this);
                List<String> stations = new ArrayList<>();
                for(int j=1; j<n.getChild(i).getNumChildren(); j++){
                    String stationName = (String) n.getChild(i).getChild(j).getChild(0).accept(this);
                    stations.add(stationName);
                }
                map_network.put(key, stations);
            }
            for(Map.Entry<String, List<String>> line1 : map_network.entrySet()){
                int croisements = 0;
                for(Map.Entry<String, List<String>> line2 : map_network.entrySet()){
                    if(!line1.getKey().equals(line2.getKey())){
                        for(String s : line1.getValue()){
                            for(String s2 : line2.getValue()){
                                if(s.equals(s2)){
                                    croisements++;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(croisements < 1 && map_network.size() > 1){
                    errors.put(line1.getKey() + " : la ligne est isolée.", ERROR_KIND.UNREACHABLE_LINE);
                }
            }
            for(ASTNode child : n){
                child.accept(this);
            }
        }
        return null;
    }

    @Override
    public Object visit(ASTLigne n) {
        String name = (String)n.getChild(0).accept(this);
        if(name.trim().isEmpty()){
            errors.put(n.getLCPrefix() + " Le nom de la ligne est vide ou assimilée.", ERROR_KIND.EMPTY_LINE_NAME);
        }else if(noms_lines.contains(name)){
            errors.put(n.getLCPrefix() + " Le nom de la ligne est dupliqué.", ERROR_KIND.DUPLICATE_LINE_NAME);
        }else{
            noms_lines.add(name);
            Set<String> noms_stations = new HashSet<>();
            Set<Double> dists_stations = new HashSet<>();
            for(int i = 1; i < n.getNumChildren(); i++){
                // On vérifie si les noms des stations sont vides/assimilées si elles ne sont pas dupliquées
                String station_name = (String)n.getChild(i).getChild(0).accept(this);
                if(station_name.trim().isEmpty()){
                    errors.put(n.getChild(i).getLCPrefix() + " Le nom de la station est vide ou assimilée.", ERROR_KIND.EMPTY_STATION_NAME);
                }else if(noms_stations.contains(station_name)){
                    errors.put(n.getChild(i).getLCPrefix() + " Le nom de la station est dupliqué dans la ligne.", ERROR_KIND.DUPLICATE_STATION_NAME);
                }else{
                    noms_stations.add(station_name);
                }

                // On vérifie que les distances des stations sur la ligne sont croissante, et que la premiere station est a 0.0
                double station_dist = (double)n.getChild(i).getChild(1).accept(this);
                if(dists_stations.contains(station_dist)){
                    errors.put(n.getChild(i).getLCPrefix() + " La distance n'est pas strictement croissante.", ERROR_KIND.WRONG_NUMBER_VALUE);
                }else{
                    if(i == 1 && station_dist != 0.0){
                        errors.put(n.getChild(i).getLCPrefix() + " La distance de la première station n'est pas a 0.0.", ERROR_KIND.WRONG_NUMBER_VALUE);
                    }
                    dists_stations.add(station_dist);
                }
            }
        }
        for(ASTNode child : n){
            child.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(ASTDeclaration n) { //Declaration	::=	<chaine> : <nombre>
        double dist = (double)n.getChild(1).accept(this);
        if(dist < 0){
           errors.put(n.getLCPrefix() + "La distance kilométrique n'est pas positive", ERROR_KIND.WRONG_NUMBER_VALUE);
        }
        for(ASTNode child : n){
            child.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(ASTChaine n) { // <chaine> (de la déclaration)
        return n.toString().substring(1, n.toString().length()-1);
    }

    @Override
    public Object visit(ASTNombre n) { // <nombre> (de la déclaration)
        return n.getNumberValue();
    }
}

enum ERROR_KIND {
    EMPTY_LINE_LIST,
    EMPTY_LINE_NAME,
    DUPLICATE_LINE_NAME,
    EMPTY_STATION_NAME,
    DUPLICATE_STATION_NAME,
    WRONG_NUMBER_VALUE,
    UNREACHABLE_LINE
}
