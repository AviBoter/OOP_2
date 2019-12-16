package dataStructure;

import utils.Point3D;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data{
    private static int _ID=0;
    private int _myID;
    private Point3D _location = null;
    private double _weight;
    private String _info = "";
    private int _tag;
    private HashMap<Integer,Edata> EMap = new HashMap<>();


    public NodeData(){
        _myID = _ID;
        _ID++;

    }
    public NodeData(node_data n){
        _myID = n.getKey();
        _location = n.getLocation();
        _weight = n.getWeight();
        _info = n.getInfo();
        _tag = n.getTag();
    }
    @Override
    public int getKey() {
        return _myID;
    }

    @Override
    public Point3D getLocation() {
        return _location;
    }

    @Override
    public void setLocation(Point3D p) {
        _location = new Point3D(p);
    }

    @Override
    public double getWeight() {
        return _weight;
    }

    @Override
    public void setWeight(double w) {
        _weight = w;

    }

    @Override
    public String getInfo() {
        return _info;
    }

    @Override
    public void setInfo(String s) {
        _info = s;
    }

    @Override
    public int getTag() {
        return _tag;
    }

    @Override
    public void setTag(int t) {
        _tag = t;
    }
    public Edata getEdata(int dest){
        if (EMap.containsKey(dest)) {
            return EMap.get(dest);
        }
        return null;
    }
    public void put(int des, Edata edata){
        EMap.put(des,edata);
    }
    public Collection<edge_data> getE() {
        return (Collection<edge_data>) EMap;
    }
    public Edata removeEdge(int des){
        if (EMap.containsKey(des))
            return EMap.remove(des);
        return null;
    }
}
