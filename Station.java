package fr.ufc.l3info.oprog;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * Class representing a station.
 */
public class Station {
    private String name;
    private HashMap<String,Integer> number = new HashMap<>();
    private HashMap<String,Double> dist = new HashMap<>();


    /** Builds a station associated to no lines.
     * @param _name the name of the station.
     */
    public Station(String _name) {
        if(_name != null) {
            this.name = _name;
        }
    }

    /**
     * Builds a station, initially associated to a given line with a given number.
     * @param _name the name of the station
     * @param _line the name of the line associated to the station
     * @param _number the number of the station on the considered line
     * @param _dist the distance of the station on the considered line
     */
    public Station(String _name, String _line, int _number, double _dist) {
        if(_name != null){
            this.name = _name;
            if(!Objects.equals(_line, "") && _line != null && _number > 0 && _dist >= 0){
                String str = deleteSpace(_line);
                if(!str.equals("")){
                    this.number.put(_line, _number);
                    this.dist.put(_line, _dist);
                }
            }
        }
    }

    /**
     * Deletes spaces of a string
     * @param str the string to delete spaces
     * @return the string without spaces
     */
    private String deleteSpace(String str){
        return str.replace(" ", "");
    }

    /**
     * Adds a line to the current station, with the appropriate parameters.
     * If the line already exists, the previous information are overwritten.
     * @param _line the name of the line associated to the station
     * @param _number the number of the station on the considered line
     * @param _dist the distance of the station on the considered line
     */
    public void addLine(String _line, int _number, double _dist) {
        if((!Objects.equals(_line, "") && _line != null) && _number > 0 && _dist >= 0) {
            String str = deleteSpace(_line);
            if(str.equals("")) {
                return;
            }
            if(this.number.containsKey(_line)) {
                this.number.remove(_line);
                this.number.put(_line, _number);
                this.dist.remove(_line);
                this.dist.put(_line, _dist);
            } else {
                this.number.put(_line, _number);
                this.dist.put(_line, _dist);
            }
        }
        //Nom assimilé -> exemple une ligne qui se prénome "     "(que des espaces)
        //Solution -> faire une méthode qui enlève tous les espaces
        //et vérifier si c'est finalement une chaine vide ou non
    }


    /**
     * Removes a line from the station.
     * @param _line the line to remove.
     */
    public void removeLine(String _line) {
        if(this.number.containsKey(_line) && this.dist.containsKey(_line)){
            this.number.remove(_line);
            this.dist.remove(_line);
        }
    }


    /**
     * Retrieves the name of the station.
     * @return the name of the station
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the number of the station on a given line.
     * @param l The name of the line
     * @return the # of the station for the given line,
     *         or 0 if the line does not exist at the station.
     */
    public int getNumberForLine(String l) {
        if(this.number.containsKey(l)){
            return this.number.get(l);
        }
        return 0;
    }


    /**
     * Returns the distance of the station on a given line.
     * @param l The name of the line.
     * @return the distance of the station w.r.t. the beginning of the line.
     */
    public double getDistanceForLine(String l) {
        if(this.dist.containsKey(l)){
            return this.dist.get(l);
        }
        return -1;
    }

    /**
     * Computes the set of lines associated to the station.
     * @return a set containing the names of the lines that cross the station.
     */
    public Set<String> getLines() {
        return this.number.keySet();
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Station) {
            return (((Station)o).getName() == this.getName());
        }
        return false;
    }
}

