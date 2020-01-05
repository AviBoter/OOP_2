package gui;

import algorithms.Graph_Algo;
import com.sun.source.tree.SynchronizedTree;
import dataStructure.*;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Graph_GUI implements Serializable{
    final String res = "update";


    private boolean inAction = false;

    static class Active extends TimerTask {
        Graph_GUI gui;
        boolean press = false;
        Point3D temp1 = null;
        Point3D temp2 = null;
        Date date;
        long time;
        boolean update = false;
        public  Active(Graph_GUI graphGui){
            gui = graphGui;
        }
        public synchronized void run(){
            //newLocation(gui);
            if (StdDraw.isMousePressed()&&!press){
                date = new Date();
                time = date.getTime();
                press = true;
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                temp1 = new Point3D(x,y);
                gui.addPoint(temp1);
                gui.update();
            }
            if (StdDraw.isMousePressed()&&press){
                date = new Date();

            }

            if (!StdDraw.isMousePressed()&&press&&date.getTime()-time>300){
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                temp2 = new Point3D(x,y);
                gui.addPoint(temp2);
                gui.update();
                gui.addE(gui._id-1,gui._id,0);
                press = false;
                gui.update();

            }
            else if (!StdDraw.isMousePressed()&&press){
                press = false;

            }
            synchronized (gui.res){
            if (!gui.inAction()&&gui.thisMC()) {
                gui.update();
            }

            }
        }
    }

    private boolean inAction() {
        return inAction;
    }

    public  int _id = 0;
    private int mc = -1;

    private DGraph dGraph;
    private Graph_Algo graphAlgo = new Graph_Algo();


    public Graph_GUI(){
        draw(1200,800,new Range(-100,100),new Range(-100,100));
        Timer timer = new Timer();
        timer.schedule(new Active(this), 1, 1);

    }
    public Graph_GUI(graph graph){
        dGraph = (DGraph) graph;
        draw(1200,800,new Range(NodeData.getMinX(),NodeData.getMaxX()),new Range(NodeData.getMinY(),NodeData.getMaxY()));
        Timer timer = new Timer();
        if (!inAction)
            timer.schedule(new Active(this), 1, 1);

    }

    public void addPoint(Point3D p){
        NodeData temp = new NodeData(p);
        dGraph.addNode(temp);

    }

    public void addE(int src,int dest, double weight){
        dGraph.connect(src,dest,weight);
    }

    public void draw(int width,int height){
        StdDraw.setCanvasSize(width,height,this);
        StdDraw.setXscale(NodeData.getMinX()-10,NodeData.getMaxX()+10);
        StdDraw.setYscale(NodeData.getMinY()-10,NodeData.getMaxY()+10);
        update();
    }
    public void draw(int width, int height, Range x, Range y){
        StdDraw.setCanvasSize(width,height,this);
        update();
    }

    public synchronized void update() {
        try {

            if (inAction) System.out.println("wird");
            while (inAction) {

            }
            StdDraw.setXscale(NodeData.getMinX()-10,NodeData.getMaxX()+10);
            StdDraw.setYscale(NodeData.getMinY()-10,NodeData.getMaxY()+10);
            inAction = true;
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            Iterator<node_data> temp = dGraph.getV().iterator();
            while (temp.hasNext()) {
                node_data n = temp.next();
                StdDraw.setPenColor(Color.blue);
                StdDraw.setPenRadius(0.04);
                StdDraw.point(n.getLocation().x(), n.getLocation().y());
                StdDraw.setPenColor(Color.green);
                StdDraw.setPenRadius(0.02);
                StdDraw.text(n.getLocation().x(), n.getLocation().y(), n.getKey() + "");
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                if (dGraph.getE(n.getKey()) != null) {
                    List<edge_data> myE = new LinkedList<>(dGraph.getE(n.getKey()));
                    for (edge_data edge : myE) {
                        double x0 = n.getLocation().x();
                        double y0 = n.getLocation().y();
                        double y1 = dGraph.getNode(edge.getDest()).getLocation().y();
                        double x1 = dGraph.getNode(edge.getDest()).getLocation().x();
                        StdDraw.setPenRadius(0.003);
                        StdDraw.line(x0, y0, x1, y1);
                        StdDraw.setPenColor(Color.RED);
                        StdDraw.text(0.3 * x0 + 0.7 * x1, 0.3 * y0 + 0.7 * y1, edge.getWeight() + "");
                        StdDraw.setPenRadius(0.03);
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.point(0.1 * x0 + 0.9 * x1, 0.1 * y0 + 0.9 * y1);
                        StdDraw.setPenRadius(0.01);
                        StdDraw.setPenColor(StdDraw.BLACK);
                    }
                }
                _id = n.getKey();

            }
        }catch (Exception e){

        }
            inAction = false;
    }

    public void delete(int key) {
        dGraph.removeNode(key);
    }
    public void delete(int src,int des) {
        dGraph.removeEdge(src,des);
    }

    public static void newLocation(Graph_GUI graph_gui){
        for (int i = 0; i<graph_gui.dGraph.nodeSize();i++){
            double x = graph_gui.dGraph.getNode(i).getLocation().x()+1;
            double y = graph_gui.dGraph.getNode(i).getLocation().y()+1;
            Point3D temp = new Point3D(x,y);
            graph_gui.dGraph.getNode(i).setLocation(temp);
        }
        graph_gui.update();
    }

    public boolean isConected(){
        graphAlgo.init(dGraph);
        return graphAlgo.isConnected();
    }
    public double shortestPathDist(int src, int dest) {
        graphAlgo.init(dGraph);
        return graphAlgo.shortestPathDist(src,dest);
    }
    public List<node_data> shortestPath(int src, int dest) {
        graphAlgo.init(dGraph);
        return graphAlgo.shortestPath(src,dest);
    }
    public void save(String filename){
        graphAlgo.init(dGraph);
        graphAlgo.save(filename);

    }
    public void initGraph(String filename){
        graphAlgo.init(filename);
        dGraph=new DGraph( graphAlgo.myGraph);

    }
    public List<node_data> TSP(List<Integer> targets){
        graphAlgo.init(dGraph);
        return graphAlgo.TSP(targets);
    }
    public boolean thisMC(){
        if (dGraph.getMC()==mc) return false;
        mc = dGraph.getMC();
        return  true;
    }


    public static void main(String[] args){
        graph g = new DGraph();
        node_data n = new NodeData(new Point3D(0,0));
        g.addNode(n);
        for (int i=0; i<20;i++){
            int temp =  (int)(Math.random()*100);
            int temp1 =  (int)(Math.random()*100);
            n =new NodeData(new Point3D(temp,temp1));
            g.addNode(n);
        }
        for (int i =0; i<10;i++){
            int temp =  (int)(Math.random()*20);
            int temp1 =  (int)(Math.random()*20);
            int weght =  (int)(Math.random()*20)+1;
            if (temp!=temp1)
                g.connect(temp,temp1,weght);
        }



//        Graph_GUI test = new Graph_GUI(g);
////        for (int i = 0; i<1000;i++){
////            Point3D tP = new Point3D(10,i);
////            test.addPoint(tP);
////
////        }
////        for (int i=0;i<999;i++){
////            test.addE(i,i+1,i*10+100);
////        }
////        test.addE(999,0,1000);
////
////        for (int i = 0;i<10000;i++){
////            int r = (int)(Math.random()*999);
////            int r2 = (int)(Math.random()*999);
////            if (r!= r2) {
////                test.addE(r, r2, r + i);
////            }
////        }
////        List<Integer> tar = new LinkedList<>();
////        for (int i = 0;i<1000;i++){
////            int r = (int)(Math.random()*3);
////            if (r==2){
////                tar.add(i);
////            }
////        }
////        System.out.println(tar.size());
////        Date date = new Date();
////        double ff = date.getTime();
////        List t =test.TSP(tar);
////        date = new Date();
////        double f = date.getTime();
////        System.out.println(f-ff);
////        System.out.println(t.size());
//        Point3D p1 = new Point3D(-10,40);
//        Point3D p3 = new Point3D(-50,30);
//        Point3D p2 = new Point3D(0,-20);
//        Point3D p4 = new Point3D(40,7);
//
//
//        NodeData n1 = new NodeData(p1);
//        NodeData n2 = new NodeData(p2);
//        NodeData n3 = new NodeData(p3);
//        NodeData n4 = new NodeData(p4);
//
//        NodeData n5 = new NodeData(new Point3D(30,40));
//        NodeData n6 = new NodeData(new Point3D(80,-10));
//        NodeData n7 = new NodeData(new Point3D(70,50));
//        NodeData n8 = new NodeData(new Point3D(90,90));
//
//        NodeData n9 = new NodeData(new Point3D(0,90));
//        NodeData n10 = new NodeData(new Point3D(4,30));
//
//
//        test.addNode(n1);
//        test.addNode(n2);
//        test.addNode(n3);
//        test.addNode(n4);
//        test.addNode(n5);
//        test.addNode(n6);
//        test.addNode(n7);
//        test.addNode(n8);
//        test.addNode(n9);
//        test.addNode(n10);
//
//        test.addE(n1.getKey(), n2.getKey(),1);
//        test.addE(n1.getKey(), n3.getKey(),4);
//        test.addE(n1.getKey(), n4.getKey(),9);
//        test.addE(n2.getKey(), n1.getKey(),1);
//        test.addE(n2.getKey(), n4.getKey(),2);
//        test.addE(n3.getKey(), n2.getKey(),1);
//        test.addE(n3.getKey(), n4.getKey(),3);
//        test.addE(n4.getKey(), n3.getKey(),10);
//
//        test.addE(n5.getKey(), n6.getKey(),1);
//        test.addE(n5.getKey(), n7.getKey(),2);
//        test.addE(n5.getKey(), n8.getKey(),3);
//        test.addE(n6.getKey(), n5.getKey(),1);
//        test.addE(n6.getKey(), n8.getKey(),5);
//        test.addE(n7.getKey(), n6.getKey(),6);
//        test.addE(n7.getKey(), n8.getKey(),7);
//        test.addE(n8.getKey(), n7.getKey(),9);
//
//        test.addE(n1.getKey(),n9.getKey(),10);
//        test.addE(n9.getKey(),n1.getKey(),1);
//        test.addE(n5.getKey(),n9.getKey(),1);
//        test.addE(n9.getKey(),n5.getKey(),1);
//        test.update();
//        List<Integer> twp =new LinkedList<>();
//        twp.add(3);
//        twp.add(2);
//        twp.add(1);
//        twp.add(4);
//        twp.add(6);
//        System.out.println(test.TSP(twp));
//        g.addNode(new NodeData(new Point3D(-10,40)));
//        twp.add(3);
//        twp.add(2);
//        twp.add(1);
//        twp.add(4);
//        twp.add(6);
//        System.out.println(test.TSP(twp));
//        twp.add(3);
//        twp.add(2);
//        twp.add(1);
//        twp.add(4);
//        twp.add(6);
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        System.out.println(test.TSP(twp));
////        g.addNode(new NodeData(new Point3D(-78,40)));
////        g.addNode(new NodeData(new Point3D(-90,40)));
//        test.shortestPath(1,7);
//        test.shortestPathDist(1,7);
//        test.delete(9);
//        test.addPoint(new Point3D(8,9));
//        System.out.println(test.shortestPath(1,7));



    }

    private void addNode(NodeData n1)
    {
        dGraph.addNode(n1);
    }


}
