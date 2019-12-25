package algorithms;

import java.io.*;
import java.util.*;

import dataStructure.*;
import gui.Graph_GUI;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable{
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



	/**
	 * xxc
	 *
	 */

	@Override
	public double shortestPathDist(int src, int dest) {
		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
		Queue<Integer> myQueue = new LinkedList<>();
		int current;
		boolean desH = false;
		myQueue.add(src);
		myBoard.put(src,0.0);
		while (!myQueue.isEmpty()) {
			current = myQueue.poll();
			if (current == dest) {
				desH = true;
			} else desH = false;
			if (_G.getE(current) != null) {

				ArrayList<edge_data> myEData = new ArrayList<>(_G.getE(current));
				boolean flag = true;
				while (!desH && flag) {
					int minIndex = minInArray(myEData);
					if (minIndex != -1) {
						edge_data minE = myEData.remove(minIndex);
						if (updateBoard(myBoard, minE))
							myQueue.add(minE.getDest());
					} else flag = false;
				}
			}
		}
		return myBoard.get(dest);
	}
	private boolean updateBoard(HashMap<Integer,Double> board,edge_data myedge){
		int dest =  myedge.getDest();
		int src = myedge.getSrc();
		double amount = myedge.getWeight()+board.get(src);
		if (!board.containsKey(dest)){
			board.put(dest,amount);
		}else {
			if (board.get(dest)>amount){
				board.put(dest,amount);
			}
			else {
				return false;
			}
		}
		return true;
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
		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
		HashMap<Integer, LinkedList<node_data>> myBoardList = new LinkedHashMap<>();
		Queue<Integer> myQueue = new LinkedList<>();
		int current;
		boolean desH = false;
		myQueue.add(src);
		myBoard.put(src,0.0);
		LinkedList<node_data> temp = new LinkedList<>();
		temp.add(_G.getNode(src));
		myBoardList.put(src,temp);
		while (!myQueue.isEmpty()) {
			current = myQueue.poll();
			if (current == dest) {
				desH = true;
			} else desH = false;
			if (_G.getE(current) != null) {
				ArrayList<edge_data> myEData = new ArrayList<>(_G.getE(current));
				boolean flag = true;
				while (!desH && flag) {
					int minIndex = minInArray(myEData);
					if (minIndex != -1) {
						edge_data minE = myEData.remove(minIndex);
						if (updateBoard(myBoard, minE, myBoardList))
							myQueue.add(minE.getDest());
					} else flag = false;
				}
			}
		}
		return myBoardList.get(dest);
	}
	private boolean updateBoard(HashMap<Integer,Double> board,edge_data myedge,HashMap<Integer, LinkedList<node_data>> map){
		int dest =  myedge.getDest();
		int src = myedge.getSrc();
		LinkedList<node_data> list = map.get(src);
		double amount = myedge.getWeight()+board.get(src);
		if (!board.containsKey(dest)){
			LinkedList<node_data> tempList  =new LinkedList<>(list);
			tempList.add(_G.getNode(dest));
			board.put(dest,amount);
			map.put(dest,tempList);
		}else {
			if (board.get(dest)>amount){
				LinkedList<node_data> tempList  =new LinkedList<>(list);
				tempList.add(_G.getNode(dest));
				board.put(dest,amount);
				map.put(dest,tempList);
			}
			else return false;
		}
		return true;
	}


@Override
	public List<node_data> TSP(List<Integer> targets)  {
		if (targets == null || targets.isEmpty()) return null;
		List<node_data> myNodeList = new LinkedList<>();
		for (Integer i:targets){
			myNodeList.add(_G.getNode(i));
		}

		HashMap<Integer, List<node_data>> myBoardList = new LinkedHashMap<>();
		HashMap<Integer,HashMap<Integer,List<node_data>>> myBigBoard = new LinkedHashMap<>();

		for (int i=0; i<targets.size();i++){
			HashMap<Integer,List<node_data>> waysTemp =new LinkedHashMap<>();
			for (int j=0; j<targets.size();j++){
				if (i!=j){
					List<node_data> nlist = shortestPath(targets.get(i),targets.get(j));
					if (nlist!=null&&nlist.containsAll(myNodeList)) return nlist;

					waysTemp.put(targets.get(j),nlist);
					myBigBoard.put(targets.get(i),waysTemp);
				}

			}
		}
		for (int i=0; i<targets.size();i++) {
			HashMap<Integer, List<node_data>> tempI = myBigBoard.get(targets.get(i));
			for (int j = 0; j < targets.size(); j++) {
				if (i != j) {
					List<node_data> tempIlist = tempI.get(targets.get(j));//list i to j short
					System.out.println();
						HashMap<Integer, List<node_data>> tempJ = myBigBoard.get(targets.get(j));
						if (6==targets.get(j) && targets.get(i)==0 ) {
							System.out.println(tempJ);
							System.out.println(tempIlist);

						}
						for (int k = 0; k < targets.size(); k++) {
							List<node_data> tempJlist = tempJ.get(targets.get(k));//lis j to k
							List<node_data> iUj = new LinkedList<>();
							if (tempJlist != null&&tempIlist != null) {
								iUj.addAll(tempIlist);
								iUj.addAll(tempJlist);
							}

							if (iUj.containsAll(myNodeList)) return iUj;
						}
					}


				}
			}
		return null;
	}





	@Override
	public graph copy() {
		return new DGraph(_G);//
	}

}
