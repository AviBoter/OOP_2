package algorithms;

import java.io.*;
import java.util.*;

import dataStructure.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable {
	public graph _G = new DGraph();

	@Override
	public void init(graph g) {
		_G = g;
	}

	@Override
	public void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream objectInputStream = new ObjectInputStream(file);
			graph g = (graph)objectInputStream.readObject();
			init(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file= new FileOutputStream(file_name);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(file);
			objectOutputStream.writeObject(this._G);
			objectOutputStream.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean isConnected() {
		Collection<node_data> temp = _G.getV();
		Iterator<node_data> nodeIter =temp.iterator();
		while (nodeIter.hasNext()){
			nodeIter.next().setTag(-1);
		}
		boolean FLAG = true;
		Queue<node_data> myQue = new LinkedList<>();
		Queue<node_data> finish = new LinkedList<>();
		node_data current;
		nodeIter = _G.getV().iterator();
		if(nodeIter.hasNext()) {
			current = nodeIter.next();
		}else {
			System.out.println("BUG");
			return false;
		}
		boolean second = false;
		int k=0;
		for (;FLAG;k++) {
			while (FLAG) {
				FLAG = false;
				if (_G.getE(current.getKey())==null) return false;
					List<edge_data> list = new LinkedList<>(_G.getE(current.getKey()));
					if (list.isEmpty()) return false;
					for (edge_data i : list) {
						if (_G.getNode(i.getDest()).getTag() !=k) {
							_G.getNode(i.getDest()).setTag(k);
							myQue.add(_G.getNode(i.getDest()));
						}
						else if (!second){
							finish.add(current);
						}
					}
				if (!myQue.isEmpty()) {
					current = myQue.poll();
					FLAG = true;
				}
			}
			if (!finish.isEmpty()) {
				current = finish.poll();
				second = true;
				FLAG = true;
			}
			nodeIter = temp.iterator();
			while (nodeIter.hasNext()){
				node_data tempNode = nodeIter.next();
				if (tempNode.getTag()!=k) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
		Queue<Integer> myQueue = new LinkedList<>();
		int current;
		myQueue.add(src);
		myBoard.put(src,0.0);
		while (!myQueue.isEmpty()) {
			current = myQueue.poll();
			if (current == dest) {
				return myBoard.get(dest);
			}
			ArrayList<edge_data> myEData = new ArrayList<>(_G.getE(current));
			boolean flag = true;
			while (flag) {
				int minIndex = minInArray(myEData);
				if (minIndex != -1) {
					edge_data minE = myEData.remove(minIndex);
					updateBoard(myBoard, minE);
					myQueue.add(minE.getDest());
				}
				else flag =false;
			}
		}
		return myBoard.get(dest);
	}
	private void updateBoard(HashMap<Integer,Double> board,edge_data myedge){
		int dest =  myedge.getDest();
		int src = myedge.getSrc();
		double amount = myedge.getWeight()+board.get(src);
		if (!board.containsKey(dest)){
			board.put(dest,amount);
		}else {
			if (board.get(dest)>amount){
				board.put(dest,amount);
			}
		}
	}
	private int minInArray(List<edge_data> myList){
		if (! myList.isEmpty()) {
			int minWE = 0;
			for (int i = 1; i < myList.size(); i++) {
				if (myList.get(minWE).getWeight()>myList.get(i).getWeight()){
					minWE = i;
				}
			}
			return minWE;
		}
		return -1;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {

		return null;
	}


	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
