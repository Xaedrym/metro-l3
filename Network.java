package fr.ufc.l3info.oprog;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

/**
 * Class representing a network.
 */
public class Network {
    private HashSet<Station> stations = new HashSet<>();
    private HashSet<String> lines = new HashSet<>();

    /**
     * Builds an empty network.
     */
    public Network(){}

    /**
     * Add a station to the current network
     * If the station already exists, it shouldn't add the station
     * @param s the station to add, we consider that it should never be null
     */
    public void addStation(Station s){
        if(!stations.contains(s)){
            stations.add(s);
            for(String line : s.getLines()){
                if(!lines.contains(line)){
                    lines.add(line);
                }
            }
        }
    }

    /**
     * Computes the set of lines associated to the network.
     * @return a set containing the names of the lines that cross the network.
     */
    public Set<String> getLines(){
        Set<String> r = new HashSet<>();
        for (Station s : stations) {
            r.addAll(s.getLines());
        }
        return r;
    }

    /**
     * Find a Station by it name, and return it if it exists.
     * @param name the name of the station that we want
     * @return the object Station with the name in parameter or null if the station doesn't exist.
     */
    public Station getStationByName(String name){
        for(Station station : stations){
            if(station.getName().equals(name)){
                return station;
            }
        }
        return null;
    }

    /**
     * Find a station by it number and it line associated.
     * @param line the line associated to the station
     * @param number the number of the station
     * @return the object station that has le line and the number associated, null if the station doesn't exist.
     */
    public Station getStationByLineAndNumber(String line, int number){
        if(!lines.isEmpty()){
            if(lines.contains(line)){
                for(Station station : stations){
                    if(station.getLines().contains(line) && station.getNumberForLine(line) == number){
                        return station;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Says if the network is valid or not; he is if it contains at least  1 Line, and if all
     *  lines are correctly strutured
     * @return true is the network is valid, false otherwise
     */
    public boolean isValid(){
        // Vérifier si une station n'est pas isolée sa mère la pute
        if(this.lines.isEmpty() || this.stations.isEmpty()){
            return false;
        }
        for(String l : this.lines){
            HashMap<Integer,Double> number_dist = new HashMap<>();
            int nb_station_per_line = 0;
            int nb_station_correspondance = 0;
            for(Station sta : this.stations) {
                // Pour bien vérifier qu'il y a au moins une station par ligne
                if(sta.getLines().isEmpty()){
                    return false;
                }
                if(sta.getLines().contains(l)){
                    // Si la première station d'une ligne n'est pas au km0
                    if(sta.getNumberForLine(l) == 1 && sta.getDistanceForLine(l) != 0){
                        return false;
                    }
                    // Pour gérer les doublons
                    if(number_dist.containsKey(sta.getNumberForLine(l))){
                        return false;
                    }
                    // Pour vérifier qu'il n'y ait aucune ligne isolée
                    if(sta.getLines().size()>1){
                        for(String line_of_sta : sta.getLines()){
                            // Ne devrait pas arrivé mais on vérifie au cas ou
                            if(!lines.contains(line_of_sta)){
                                return false;
                            }
                        }
                        nb_station_correspondance++;
                    }
                    number_dist.put(sta.getNumberForLine(l),sta.getDistanceForLine(l));
                    nb_station_per_line++;
                }
            }
            // Pour bien vérifier qu'il y a au moins une station par ligne
            // Ca ne devrait pas etre possible vu l'implémentation demandé pour Station
            // Mais c'est demandé de vérifier dans le sujet
            if(nb_station_per_line == 0){
                return false;
            }
            // Pour vérifier qu'il n'y ait aucune ligne isolée
            if(this.lines.size() > 1 && nb_station_correspondance < 1){
                return false;
            }

            for(Station sta : this.stations){
                // Pour gérer s'il manque une station
                if(sta.getLines().contains(l)) {
                    if(!number_dist.containsKey(1) || (sta.getNumberForLine(l) > number_dist.size())) {
                        return false;
                    }
                    // Pour gérer si les distances sont croissantes selon les stations
                    if (number_dist.size() > 1 && sta.getNumberForLine(l) > 1) {
                        if(number_dist.containsKey(sta.getNumberForLine(l) - 1)){
                            if (number_dist.get(sta.getNumberForLine(l)) <= number_dist.get(sta.getNumberForLine(l) - 1)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Return the shortest distance between 2 stations that their names are in parameter
     * @param s1 name of the first station
     * @param s2 name of the second station
     * @return return the shortest distance, or a negative number if parameters are null
     *                  does't be part of the current network
     */
    public double distance(String s1, String s2){
        if(!this.isValid()){
            return -1;
        }
        if(s1 == null || s2 == null){
            return -2;
        }

        Station s_1 = getStationByName(s1);
        Station s_2 = getStationByName(s2);
        if(!stations.contains(s_1) || !stations.contains(s_2)){
            return -3;
        }

        Set<String> s_1_lines = s_1.getLines();
        Set<String> s_2_lines = s_2.getLines();

        double dist_min = 0;

        for(String s1_line : s_1_lines){
            for(String s2_line : s_2_lines){
                if(s_2.getLines().equals(s_1.getLines())){
                    dist_min = Math.abs(s_1.getDistanceForLine(s1_line)-s_2.getDistanceForLine(s2_line));
                }
                else{
                    for(Station sta : stations){
                        if(sta.getLines().size() > 1 && sta.getLines().contains(s1_line)){
                            dist_min += Math.abs(s_1.getDistanceForLine(s1_line)-sta.getDistanceForLine(s1_line));
                        }
                        if(sta.getLines().size() > 1 && sta.getLines().contains(s2_line)){
                            dist_min += Math.abs(s_2.getDistanceForLine(s2_line)-sta.getDistanceForLine(s2_line));
                        }
                    }
                }
            }
        }
        return dist_min;
    }
}

