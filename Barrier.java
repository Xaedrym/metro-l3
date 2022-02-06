package fr.ufc.l3info.oprog;

import java.util.HashMap;
import java.util.Map;

public class Barrier {
    Network network;
    String station;
    Map<Double,Integer> prices;

    private Barrier(Network net, String station, Map<Double,Integer> prices){
        this.network = net;
        this.station = station;
        this.prices = prices;
    }

    public static Barrier build(Network n, String s, Map<Double,Integer> p){
        if(n == null || s == null || p == null){
            return null;
        }
        if(!n.isValid()){
            return null;
        }
        if(n.getStationByName(s) == null){
            return null;
        }
        if(p.isEmpty()){
            return null;
        }
        if(!p.containsKey(0.0)){
            return null;
        }
        for(Double k : p.keySet()){
            if(k < 0.0){
                return null;
            }
            if(p.get(k) <= 0){
                return null;
            }
        }
        return new Barrier(n,s,p);
    }

    public boolean enter(ITicket t){
        return (t.entering(station) && t.getAmount() > 0);
    }

    public boolean exit(ITicket t){
        if(!t.isValid()){
            return false;
        }
        if(t.getEntryStation() == null){
            return false;
        }
        double dist = network.distance(t.getEntryStation(),station);
        if(dist < 0){
            return false;
        }
        double key = 0.0;
        double delta_dist = dist;
        for(Double k : prices.keySet()){
            if(dist <= k && delta_dist >= k-dist){
                key = k;
                delta_dist = k-dist;
            }
        }
        int price = prices.get(key);
        int ticket_amount = t.getAmount();
        if(t.isChild()){
            price = price / 2;
            while(price%10 != 0){
                price++;
            }
        }
        if(ticket_amount < price){
            return false;
        }
        t.invalidate();
        return true;
    }

}
