package dataStructure;

import utils.Point3D;

public class NodeData implements node_data{
    private static int _ID=0;
    private int _myID;
    private Point3D _location = null;
    private double _weight;
    private String _info = "";
    private int _tag;

    public NodeData(){
        _myID = _ID;
        _ID++;
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
}
