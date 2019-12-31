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
	public graph myGraph = new DGraph();

	public Graph_Algo(graph graph) {
		myGraph = graph;
	}
	public Graph_Algo() {
	}


	@Override
	public void init(graph g) {
		myGraph = g;
	}

	@Override
	public void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream objectInputStream = new ObjectInputStream(file);
			graph g = (graph) objectInputStream.readObject();
			init(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(file);
			objectOutputStream.writeObject(this.myGraph);
			objectOutputStream.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean isConnected() {
		if (myGraph.edgeSize()==0) return true;
		Collection<node_data> temp = myGraph.getV();
		Iterator<node_data> nodeIter = temp.iterator();
		while (nodeIter.hasNext()) {
			nodeIter.next().setTag(-1);
		}
		boolean FLAG = true;
		Queue<node_data> myQue = new LinkedList<>();
		Queue<node_data> finish = new LinkedList<>();
		node_data current;
		nodeIter = myGraph.getV().iterator();
		if (nodeIter.hasNext()) {
			current = nodeIter.next();
		} else {
			System.out.println("BUG");
			return false;
		}
		boolean second = false;
		int k = 0;
		for (; FLAG; k++) {
			while (FLAG) {
				FLAG = false;
				if (myGraph.getE(current.getKey()) == null) return false;
				List<edge_data> list = new LinkedList<>(myGraph.getE(current.getKey()));
				if (list.isEmpty()) return false;
				for (edge_data i : list) {
					if (myGraph.getNode(i.getDest()).getTag() != k) {
						myGraph.getNode(i.getDest()).setTag(k);
						myQue.add(myGraph.getNode(i.getDest()));
					} else if (!second) {
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
			while (nodeIter.hasNext()) {
				node_data tempNode = nodeIter.next();
				if (tempNode.getTag() != k) {
					return false;
				}
			}
		}
		return true;
	}
//	@Override
//	public double shortestPathDist(int src, int dest) {
//		Queue<Edata> PQdist = new LinkedList<Edata>();
//		Queue<Edata> PQnode = new LinkedList<Edata>();
//		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(myGraph.nodeSize());
//		NodeData Runner=(NodeData)myGraph.getNode(src);
//		Edata CurNode = null;
//		Edata CurNode2 = null;
//
//		dist.put(src,(double)0);
//		int i=0,count=0;
//
//		while(i<=myGraph.nodeSize()) {
//			Collection<edge_data> Col=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//			Collection<edge_data> Col2=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//			AddEdgesToPriorityQueue(PQdist,Col);
//			if(i<=myGraph.nodeSize()) {
//				AddEdgesToPriorityQueue(PQnode,Col2);
//			}
//			count=PQnode.size();
//			while(count!=0) {
//				count--;
//				CurNode2=(Edata)PQnode.poll();
//				if(CurNode2!=null) {
//					Runner=(NodeData)myGraph.getNode(CurNode2.getDest());
//					if(!PQnode.isEmpty()) {
//						Collection<edge_data> Col3=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//						AddEdgesToPriorityQueue(PQdist,Col3);
//					}
//				}
//				while(!PQdist.isEmpty()) {
//					CurNode=PQdist.poll();
//					if(!dist.containsKey(CurNode.getDest()) || dist.get(CurNode.getDest()).doubleValue()>dist.get(CurNode.getSrc()).doubleValue()+CurNode.getWeight()) {
//						dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
//					}
//				}
//				if(count!=0) {
//					i++;
//				}
//			}
//			i++;
//		}
//		return dist.get(dest).doubleValue();
//	}
//
//	private void AddEdgesToPriorityQueue(Queue<Edata> Pqueue,Collection<edge_data> Col) {
//		PriorityQueue<edge_data> minHeap = new PriorityQueue<edge_data>(new Comparator<edge_data>() {
//			@Override
//			public int compare(edge_data o1, edge_data o2) {
//				return - Double.compare(o2.getWeight(),o1.getWeight());
//			}
//		});
//		Object[] temp=Col.toArray();
//		int i=0;
//		while(i<temp.length) {
//			minHeap.add((edge_data)temp[i]);
//			i++;
//		}
//		while(minHeap.iterator().hasNext()) {
//			Pqueue.add((Edata)minHeap.poll());
//		}
//	}
//
//	@Override
//	public List<node_data> shortestPath(int src, int dest) {
//		Queue<Edata> PQdist = new LinkedList<Edata>();
//		Queue<Edata> PQnode = new LinkedList<Edata>();
//		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(myGraph.nodeSize());
//		HashMap<Integer,ArrayList<Integer>> Paths=new HashMap<Integer,ArrayList<Integer>>(myGraph.nodeSize());
//		NodeData Runner=(NodeData)myGraph.getNode(src);
//		Edata CurNode = null;
//		Edata CurNode2 = null;
//
//		dist.put(src,(double)0);
//		Paths.put(src, new ArrayList<Integer>());
//		Paths.get(src).add(src);
//		int i=0,count=0;
//
//		while(i<=myGraph.nodeSize()+1) {
//			Collection<edge_data> Col=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//			Collection<edge_data> Col2=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//			AddEdgesToPriorityQueue(PQdist,Col);
//			if(i<=myGraph.nodeSize()) {
//				AddEdgesToPriorityQueue(PQnode,Col2);
//			}
//			count=PQnode.size();
//			while(count!=0) {
//				count--;
//				CurNode2=(Edata)PQnode.poll();
//				if(CurNode2!=null) {
//					Runner=(NodeData)myGraph.getNode(CurNode2.getDest());
//					if(!PQnode.isEmpty()) {
//						Collection<edge_data> Col3=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
//						AddEdgesToPriorityQueue(PQdist,Col3);
//					}
//				}
//				while(!PQdist.isEmpty()) {
//					CurNode=PQdist.poll();
//					if(!dist.containsKey(CurNode.getDest()) || (dist.containsKey(CurNode.getDest()) && dist.get(CurNode.getDest())>dist.get(CurNode.getSrc())+CurNode.getWeight())) {
//						Paths.put(CurNode.getDest(), new ArrayList<Integer>());
//						dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
//						int k=0;
//						while(k<Paths.get(CurNode.getSrc()).size()) {
//							Paths.get(CurNode.getDest()).add(Paths.get(CurNode.getSrc()).get(k));
//							k++;
//						}
//						Paths.get(CurNode.getDest()).add(CurNode.getDest());
//					}
//				}
//				if(count!=0) {
//					i++;
//				}
//			}
//			i++;
//		}
//		List<node_data> Ans=new LinkedList<node_data>();
//		int j=0;
//		while(j<Paths.get(dest).size()) {
//			Ans.add(myGraph.getNode(Paths.get(dest).get(j)));
//			System.out.println(Paths.get(dest).get(j));
//			j++;
//		}
//		return Ans;
//	}

	/**
	 *
	 *
	 * @return
	 */


	@Override
	public double shortestPathDist(int src, int dest) {
		if (myGraph.getNode(src) == null || myGraph.getNode(dest) == null)
			throw new RuntimeException("src or dst dose not exist");
		if (src == dest) return 0;
		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
		Queue<Integer> myQueue = new LinkedList<>();
		int current;
		boolean desH = false;
		myQueue.add(src);
		myBoard.put(src, 0.0);
		while (!myQueue.isEmpty()) {
			current = myQueue.poll();
			if (current == dest) {
				desH = true;
			} else desH = false;
			if (myGraph.getE(current) != null) {

				ArrayList<edge_data> myEData = new ArrayList<>(myGraph.getE(current));
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

	private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge) {
		int dest = myedge.getDest();
		int src = myedge.getSrc();
		double amount = myedge.getWeight() + board.get(src);
		if (!board.containsKey(dest)) {
			board.put(dest, amount);
		} else {
			if (board.get(dest) > amount) {
				board.put(dest, amount);
			} else {
				return false;
			}
		}
		return true;
	}

	private int minInArray(List<edge_data> myList) {
		if (!myList.isEmpty()) {
			int minWE = 0;
			for (int i = 1; i < myList.size(); i++) {
				if (myList.get(minWE).getWeight() > myList.get(i).getWeight()) {
					minWE = i;
				}
			}
			return minWE;
		}
		return -1;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		if (myGraph.getNode(src) == null || myGraph.getNode(dest) == null)
			throw new RuntimeException("src or dst dose not exist");
		if (src == dest) {
			List<node_data> t = new LinkedList<>();
			t.add(myGraph.getNode(src));
			return t;
		}
		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
		HashMap<Integer, LinkedList<node_data>> myBoardList = new LinkedHashMap<>();
		Queue<Integer> myQueue = new LinkedList<>();
		int current;
		boolean desH = false;
		myQueue.add(src);
		myBoard.put(src, 0.0);
		LinkedList<node_data> temp = new LinkedList<>();
		temp.add(myGraph.getNode(src));
		myBoardList.put(src, temp);
		while (!myQueue.isEmpty()) {
			current = myQueue.poll();
			if (current == dest) {
				desH = true;
			} else desH = false;
			if (myGraph.getE(current) != null) {
				ArrayList<edge_data> myEData = new ArrayList<>(myGraph.getE(current));
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

	private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge, HashMap<Integer, LinkedList<node_data>> map) {
		int dest = myedge.getDest();
		int src = myedge.getSrc();
		LinkedList<node_data> list = map.get(src);
		double amount = myedge.getWeight() + board.get(src);
		if (!board.containsKey(dest)) {
			LinkedList<node_data> tempList = new LinkedList<>(list);
			tempList.add(myGraph.getNode(dest));
			board.put(dest, amount);
			map.put(dest, tempList);
		} else {
			if (board.get(dest) > amount) {
				LinkedList<node_data> tempList = new LinkedList<>(list);
				tempList.add(myGraph.getNode(dest));
				board.put(dest, amount);
				map.put(dest, tempList);
			} else return false;
		}
		return true;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if (targets == null || targets.isEmpty()) return null;
		int i = 0;
		List<node_data> ans = new LinkedList<node_data>();

		HashMap<Integer,Boolean> hashMap = new LinkedHashMap<>();
		while (i<targets.size()){
			if(!hashMap.containsKey(targets.get(i))){
				hashMap.put(targets.get(i),true);
				i++;

			}
			else {
				targets.remove(i);
			}
		}
		i=0;
//		while (i < targets.size() - 1) {
//			ans.addAll(shortestPath(targets.get(i), targets.get(i + 1)));
//			i++;
//		}
		int temp = 0;
		int temp2;
		List<node_data> tempN;
		if (!targets.isEmpty()) {
			temp = targets.remove(0);
		}
		while (!targets.isEmpty()){
			temp2 = targets.remove(0);
			tempN = shortestPath(temp,temp2);
			if(tempN == null) return null;
			for (node_data nk: tempN){
				if (targets.contains(nk.getKey())){
					targets.remove((Integer)nk.getKey());
				}
			}
			ans.addAll(tempN);
			temp = temp2;
		}

		i = 0;
		while (i < ans.size() - 1) {
			if (ans.get(i).equals(ans.get(i + 1)))
				ans.remove(i);
			else
				i++;
		}
		//System.out.println( );
		//for(node_data n:ans){
			//System.out.print(" "+(n.getKey()+1));
		//}
		return ans;
	}
	{
//    @Override
//	public List<node_data> TSP(List<Integer> targets){
//		if (targets == null || targets.isEmpty()) return null;
//		List<node_data> myNodeList = new LinkedList<>();
//		for (Integer i:targets){
//			myNodeList.add(myGraph.getNode(i));
//		}
//
//		HashMap<Integer, List<node_data>> myBoardList = new LinkedHashMap<>();
//		HashMap<Integer,HashMap<Integer,List<node_data>>> myBigBoard = new LinkedHashMap<>();
//
//		for (int i=0; i<targets.size();i++){
//			HashMap<Integer,List<node_data>> waysTemp =new LinkedHashMap<>();
//			for (int j=0; j<targets.size();j++){
//				if (i!=j){
//					List<node_data> nlist = shortestPath(targets.get(i),targets.get(j));
//					if (nlist!=null&&nlist.containsAll(myNodeList)) return nlist;
//
//					waysTemp.put(targets.get(j),nlist);
//					myBigBoard.put(targets.get(i),waysTemp);
//				}
//
//			}
//		}
//		for (int i=0; i<targets.size();i++) {
//			HashMap<Integer, List<node_data>> tempI = myBigBoard.get(targets.get(i));
//			for (int j = 0; j < targets.size(); j++) {
//				if (i != j) {
//					List<node_data> tempIlist = tempI.get(targets.get(j));//list i to j short
//					System.out.println();
//						HashMap<Integer, List<node_data>> tempJ = myBigBoard.get(targets.get(j));
//						if (6==targets.get(j) && targets.get(i)==0 ) {
//							System.out.println(tempJ);
//							System.out.println(tempIlist);
//
//						}
//						for (int k = 0; k < targets.size(); k++) {
//							List<node_data> tempJlist = tempJ.get(targets.get(k));//lis j to k
//							List<node_data> iUj = new LinkedList<>();
//							if (tempJlist != null&&tempIlist != null) {
//								iUj.addAll(tempIlist);
//								if (iUj.contains(tempJlist.get(0)))
//									iUj.remove(tempJlist.get(0));
//								iUj.addAll(tempJlist);
//							}
//
//							if (iUj.containsAll(myNodeList)) return iUj;
//						}
//					}
//
//
//				}
//			}
//		int _TEMP=targets.remove(targets.size()-1);
//		List<node_data> finalTry = TSP(targets);
//		List<node_data> result = shortestPath(_TEMP,finalTry.get(0).getKey());
//		if (result==null) return null;
//		result.addAll(finalTry);
//		System.out.println(result.size());
//		return result;
//
//
//	}
}// worse TSP run time ever
	@Override
	public graph copy() {
		return new DGraph(myGraph);//
	}

}
