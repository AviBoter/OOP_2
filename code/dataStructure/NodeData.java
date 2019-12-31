package dataStructure;

import utils.Point3D;

import java.io.Serializable;
import java.util.*;

public class NodeData implements node_data, Serializable {
    private static int IDnode=0;
    private int id;
    private Point3D nodeLoaction = null;
    private double nodeWeight = 0;
    private String nodeInfo = "";
    private int nodeTag = -1;
    private HashMap<Integer,Edata> EMap = new LinkedHashMap<>();
    private static double maxY=0, maxX=0 , minX=0, minY=0;
    public NodeData(Point3D location){
        maxX = Math.max(location.x()+1,maxX);
        maxY = Math.max(location.y()+1,maxY);
        minX = Math.min(location.x()-1,minX);
        minY = Math.min(location.y()-1,minY);
        id = IDnode;
        IDnode++;
        nodeLoaction = location;

    }
    public NodeData(node_data n){
        id = n.getKey();
        if (IDnode<n.getKey())
            IDnode = n.getKey()+1;
        nodeLoaction = new Point3D(n.getLocation().x(),n.getLocation().y());
        nodeWeight = n.getWeight();
        nodeInfo = n.getInfo();
        nodeTag = n.getTag();
    }
    @Override
    public int getKey() {
        return id;
    }

    @Override
    public Point3D getLocation() {
        return nodeLoaction;
    }

    @Override
    public void setLocation(Point3D p) {
        nodeLoaction = new Point3D(p);
    }

    @Override
    public double getWeight() {
        return nodeWeight;
    }

    @Override
    public void setWeight(double w) {
        nodeWeight = w;

    }

    @Override
    public String getInfo() {
        return nodeInfo;
    }

    @Override
    public void setInfo(String s) {
        nodeInfo = s;
    }

    @Override
    public int getTag() {
        return nodeTag;
    }

    @Override
    public void setTag(int t) {
        nodeTag = t;
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
