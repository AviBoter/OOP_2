package dataStructure;

import org.junit.jupiter.api.Test;
import utils.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getKey() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,0);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,0);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,0);

       assertNotEquals(node1.getKey(),node2.getKey(),node3.getKey());
    }

    @Test
    void getLocation() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,0);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,0);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,0);
        assertEquals(node1.getLocation(),p1);
        assertEquals(node2.getLocation(),p2);
        assertEquals(node3.getLocation(),p3);
    }

    @Test
    void setLocation() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,0);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,0);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,0);
        node1.setLocation(p2);
        node2.setLocation(p3);
        node3.setLocation(p1);
        assertEquals(node1.getLocation(),p2);
        assertEquals(node2.getLocation(),p3);
        assertEquals(node3.getLocation(),p1);
    }

    @Test
    void getWeight() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,2);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,6);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,4);
        assertEquals(node1.getWeight(),2);
        assertEquals(node2.getWeight(),6);
        assertEquals(node3.getWeight(),4);
    }

    @Test
    void setWeight() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,0);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,0);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,0);

        node1.setWeight(2);
        node2.setWeight(7.5);
        node3.setWeight(8.7);
        assertEquals(node1.getWeight(),2);
        assertEquals(node2.getWeight(),7.5);
        assertEquals(node3.getWeight(),8.7);

    }

    @Test
    void getInfo() {
        Point3D p1= new Point3D(-1,1);
        node_data node1 = new NodeData(p1,0);
        Point3D p2= new Point3D(-2,2);
        node_data node2 = new NodeData(p2,0);
        Point3D p3= new Point3D(-3,3);
        node_data node3 = new NodeData(p3,0);


    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }

    @Test
    void getMaxX() {
    }

    @Test
    void getMinX() {
    }

    @Test
    void getMaxY() {
    }

    @Test
    void getMinY() {
    }
}