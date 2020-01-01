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

    /**
     * Copy constructor
     * @param n
     */
    public NodeData(node_data n){
        id = n.getKey();
        if (IDnode<n.getKey())
            IDnode = n.getKey()+1;
        nodeLoaction = new Point3D(n.getLocation().x(),n.getLocation().y());
        nodeWeight = n.getWeight();
        nodeInfo = n.getInfo();
        nodeTag = n.getTag();
    }

    /**
     *
     * @return the key of the node
     */
    @Override
    public int getKey() {
        return id;
    }

    /**
     *
     * @return the location of the node
     */
    @Override
    public Point3D getLocation() {
        return nodeLoaction;
    }

    /**
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(Point3D p) {
        nodeLoaction = new Point3D(p);
    }

    /**
     *
     * @return the weight of this node
     */
    @Override
    public double getWeight() {
        return nodeWeight;
    }

    /**
     * Set weight to this node
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        nodeWeight = w;

    }

    /**
     *
     * @return info of the node
     */
    @Override
    public String getInfo() {
        return nodeInfo;
    }

    /**
     * Set info for the node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        nodeInfo = s;
    }

    /**
     *
     * @return tag in int of this node
     */
    @Override
    public int getTag() {
        return nodeTag;
    }

    /**
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        nodeTag = t;
    }

    /**
     *
     * @return return max of location x of all the nodes
     */
    public static double getMaxX() {
        return maxX;
    }
    /**
     *
     * @return return min of location x of all the nodes
     */
    public static double getMinX() {
        return minX;
    }
    /**
     *
     * @return return max of location y of all the nodes
     */

    public static double getMaxY() {
        return maxY;
    }
    /**
     *
     * @return return min of location y of all the nodes
     */
    public static double getMinY() {
        return minY;
    }
}
