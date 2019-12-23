package dataStructure;

import utils.Point3D;

import java.io.Serializable;
import java.util.*;

public class NodeData implements node_data, Serializable {
    private static int _ID=0;
    private int _myID;
    private Point3D _location = null;
    private double _weight;
    private String _info = "";
    private int _tag;
    private HashMap<Integer,Edata> EMap = new LinkedHashMap<>();
    private static double maxY=0, maxX=0 , minX=0, minY=0;
    public NodeData(Point3D location,double weight){
        maxX = Math.max(location.x(),maxX)+1;
        maxY = Math.max(location.y(),maxY)+1;
        minX = Math.min(location.x(),minX)-1;
        minY = Math.min(location.y(),minY)-1;
        _myID = _ID;
        _ID++;
        _location = location;
        _weight = weight;

    }
    public NodeData(node_data n){
        _myID = n.getKey();
        if (_ID<n.getKey())
            _ID = n.getKey()+1;
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

    public static double getMaxX() {
        return maxX;
    }

    public static double getMinX() {
        return minX;
    }

    public static double getMaxY() {
        return maxY;
    }

    public static double getMinY() {
        return minY;
    }
}
