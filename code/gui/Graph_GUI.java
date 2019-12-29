package gui;

import algorithms.Graph_Algo;
import dataStructure.*;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import java.util.List;

public class Graph_GUI implements Serializable {




    static class Active extends TimerTask {
        Graph_GUI gui;
        boolean press = false;
        Point3D temp1 = null;
        Point3D temp2 = null;
        Date date;
        long time;
        public Active(Graph_GUI graphGui){
            gui = graphGui;
        }
        public void run(){
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


        }
    }
    public  int _id = 0;

    private DGraph dGraph = new DGraph();
    private Graph_Algo graphAlgo = new Graph_Algo();


    public Graph_GUI(){
        draw(1200,800,new Range(-100,100),new Range(-100,100));
        Timer timer = new Timer();
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
        StdDraw.setXscale(NodeData.getMinX(),NodeData.getMaxX());
        StdDraw.setYscale(NodeData.getMinY(),NodeData.getMaxY());
        update();
    }
    public void draw(int width, int height, Range x, Range y){
        StdDraw.setCanvasSize(width,height,this);
        StdDraw.setXscale(x.get_min(),x.get_max());
        StdDraw.setYscale(y.get_min(),y.get_max());
        update();
    }

    public void update() {
        StdDraw.clear();
        StdDraw.setPenRadius(0.01);
        for (node_data n : dGraph.getV()) {
            StdDraw.setPenColor(Color.blue);
            StdDraw.setPenRadius(0.04);
            StdDraw.point(n.getLocation().x(), n.getLocation().y());
            StdDraw.setPenColor(Color.green);
            StdDraw.setPenRadius(0.02);
            StdDraw.text(n.getLocation().x(), n.getLocation().y(), n.getKey() + "");
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            if (dGraph.getE(n.getKey())!=null) {
                List<edge_data> myE = new LinkedList<>(dGraph.getE(n.getKey()));
                for (edge_data edge : myE) {
                    double x0 = n.getLocation().x();
                    double y0 = n.getLocation().y();
                    double y1 = dGraph.getNode(edge.getDest()).getLocation().y();
                    double x1 = dGraph.getNode(edge.getDest()).getLocation().x();
                    StdDraw.setPenRadius(0.003);

                    StdDraw.line(x0, y0, x1, y1);
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.text(0.3 * x0 + 0.7 * x1, 0.3 * y0 + 0.7 * y1,edge.getWeight()+"");
                    StdDraw.setPenRadius(0.03);
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.point(0.1 * x0 + 0.9 * x1, 0.1 * y0 + 0.9 * y1);
                    StdDraw.setPenRadius(0.01);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
            }
            _id= n.getKey();

        }
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
        dGraph=new DGraph( graphAlgo._G);

    }
    public List<node_data> TSP(List<Integer> targets){
        graphAlgo.init(dGraph);
        return graphAlgo.TSP(targets);
    }

    public static void main(String[] args){
        Graph_GUI test = new Graph_GUI();
//        for (int i = 0; i<1000;i++){
//            Point3D tP = new Point3D(10,i);
//            test.addPoint(tP);
//
//        }
//        for (int i=0;i<999;i++){
//            test.addE(i,i+1,i*10+100);
//        }
//        test.addE(999,0,1000);
//
//        for (int i = 0;i<10000;i++){
//            int r = (int)(Math.random()*999);
//            int r2 = (int)(Math.random()*999);
//            if (r!= r2) {
//                test.addE(r, r2, r + i);
//            }
//        }
//        List<Integer> tar = new LinkedList<>();
//        for (int i = 0;i<1000;i++){
//            int r = (int)(Math.random()*3);
//            if (r==2){
//                tar.add(i);
//            }
//        }
//        System.out.println(tar.size());
//        Date date = new Date();
//        List t =test.TSP(tar);
//        double f = date.getTime();
//        System.out.println(f);
//        System.out.println(t.size());


    }



}
