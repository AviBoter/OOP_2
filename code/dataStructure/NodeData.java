package dataStructure;

import utils.Point3D;

import java.io.Serializable;
import java.util.*;

public class NodeData implements node_data, Serializable {
    private static int _ID=0;
    private int _myID;
    private Point3D _location = null;
    private double _weight = 0;
    private String _info = "";
    private int _tag = -1;
    private HashMap<Integer,Edata> EMap = new LinkedHashMap<>();
    private static double maxY=0, maxX=0 , minX=0, minY=0;
    public NodeData(Point3D location){
        maxX = Math.max(location.x()+1,maxX);
        maxY = Math.max(location.y()+1,maxY);
        minX = Math.min(location.x()-1,minX);
        minY = Math.min(location.y()-1,minY);
        _myID = _ID;
        _ID++;
        _location = location;

    }
    public NodeData(node_data n){
        _myID = n.getKey();
        if (_ID<n.getKey())
            _ID = n.getKey()+1;
        _location = new Point3D(n.getLocation().x(),n.getLocation().y());
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
