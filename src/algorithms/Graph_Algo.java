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
	public boolean Ok = true;

	/**
	 * init
	 * @param graph - the graph for the algorithms.
	 */
	public Graph_Algo(graph graph) {
		myGraph = graph;
	}
	public Graph_Algo() {
	}

	/**
	 * init
	 * @param g - the graph for the algorithms.
	 */
	@Override
	public void init(graph g) {
		myGraph = g;
	}

	/**
	 * init from file
	 * @param file_name - include path to the file
	 */
	@Override
	public  void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream objectInputStream = new ObjectInputStream(file);
			graph g = (graph) objectInputStream.readObject();
			init(g);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * save to file
	 * @param file_name - the file name that will be save in
	 */
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

	/**
	 * Returns true if and only if (iff) there is a valid path from EVREY node to each
	 * other node. NOTE: assume directional graph - a valid path (a-->b) does NOT imply a valid path (b-->a).
	 * @return true if the graph is connected and not if not
	 */
	@Override
	public boolean isConnected() {
		if (myGraph.edgeSize() == 0) return true;
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
	@Override
public double shortestPathDist(int src, int dest) {
		Ok = false;
		resetTag();
		if(myGraph.getNode(src)==null || myGraph.getNode(dest)==null)
			return Integer.MIN_VALUE;

		Queue<Edata> PQdist = new LinkedList<Edata>();
		Queue<Edata> PQnode = new LinkedList<Edata>();
		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(myGraph.nodeSize());
		NodeData Runner=(NodeData)myGraph.getNode(src);
		Edata CurNode= null;

		dist.put(src,(double)0);
		int i=0;

		while(i<=myGraph.nodeSize() || !PQnode.isEmpty()) {
			Collection<edge_data> Col=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
			Collection<edge_data> Col2=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
			AddEdgesToPriorityQueue(PQdist,Col);
			AddNodesToPriorityQueue(PQnode,Col2);
			GetNewDist(PQdist,dist);
			CurNode=(Edata)PQnode.poll();
			if(CurNode!=null)
				Runner=(NodeData)myGraph.getNode(CurNode.getDest());
			i++;
		}
		Ok = true;
		return dist.get(dest).doubleValue();
	}

	private void GetNewDist(Queue<Edata> PQdist,HashMap<Integer,Double> dist) {
		while(!PQdist.isEmpty()) {
			Edata CurNode=PQdist.poll();
			if(!dist.containsKey(CurNode.getDest()) || dist.get(CurNode.getDest()).doubleValue()>dist.get(CurNode.getSrc()).doubleValue()+CurNode.getWeight()) {
				dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
			}
		}
	}
	private void GetNewDist(Queue<Edata> PQdist,HashMap<Integer,Double> dist,HashMap<Integer,ArrayList<Integer>> Paths) {
		while(!PQdist.isEmpty()) {
			Edata CurNode=PQdist.poll();
			if(!dist.containsKey(CurNode.getDest()) || dist.get(CurNode.getDest()).doubleValue()>dist.get(CurNode.getSrc()).doubleValue()+CurNode.getWeight()) {
				Paths.put(CurNode.getDest(), new ArrayList<Integer>());
				dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
				int k=0;
				while(k<Paths.get(CurNode.getSrc()).size()) {
					Paths.get(CurNode.getDest()).add(Paths.get(CurNode.getSrc()).get(k));
					k++;
				}
				Paths.get(CurNode.getDest()).add(CurNode.getDest());
			}
		}
	}

	private void AddEdgesToPriorityQueue(Queue<Edata> Pqueue,Collection<edge_data> Col) {
		PriorityQueue<edge_data> minHeap = new PriorityQueue<edge_data>(new Comparator<edge_data>() {
			@Override
			public int compare(edge_data o1, edge_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});
		Object[] temp=Col.toArray();
		int i=0;
		while(i<temp.length) {
			minHeap.add((edge_data)temp[i]);
			i++;
		}
		while(minHeap.iterator().hasNext()) {
			Pqueue.add((Edata)minHeap.poll());
		}
	}

	private void AddNodesToPriorityQueue(Queue<Edata> Pqueue,Collection<edge_data> Col) {
		PriorityQueue<edge_data> minHeap = new PriorityQueue<edge_data>(new Comparator<edge_data>() {
			@Override
			public int compare(edge_data o1, edge_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});
		int i=0;
		Object[] temp=Col.toArray();
		while(i<temp.length) {
			Edata cur=(Edata)temp[i];
			if(myGraph.getNode(cur.getDest()).getTag()!=2) {
				minHeap.add((edge_data)temp[i]);
			}
			i++;
		}
		i=0;
		while(i<minHeap.size()) {
			if(!Pqueue.contains((Edata)minHeap.peek()) && !minHeap.isEmpty())
				Pqueue.add((Edata)minHeap.poll());
			if(Pqueue.contains((Edata)minHeap.peek()))
				minHeap.poll();
		}
		i=0;
		while(i<Col.size()) {
			Edata cur=(Edata)temp[i];
			this.myGraph.getNode(cur.getDest()).setTag(2);
			i++;
		}
		Col=null;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		resetTag();
		if(myGraph.getNode(src)==null || myGraph.getNode(dest)==null)
			return null;
		Queue<Edata> PQdist = new LinkedList<Edata>();
		Queue<Edata> PQnode = new LinkedList<Edata>();
		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(myGraph.nodeSize());
		HashMap<Integer,ArrayList<Integer>> Paths=new HashMap<Integer,ArrayList<Integer>>(myGraph.nodeSize());
		Edata CurNode = null;
		NodeData Runner=(NodeData)myGraph.getNode(src);

		dist.put(src,(double)0);
		Paths.put(src, new ArrayList<Integer>());
		Paths.get(src).add(src);
		int i=0;

		while(i<=myGraph.nodeSize() || !PQnode.isEmpty()) {
			Collection<edge_data> Col=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
			Collection<edge_data> Col2=new ArrayList<edge_data>(myGraph.getE(Runner.getKey()));
			AddEdgesToPriorityQueue(PQdist,Col);
			AddNodesToPriorityQueue(PQnode,Col2);
			GetNewDist(PQdist,dist,Paths);
			CurNode=(Edata)PQnode.poll();
			if(CurNode!=null)
				Runner=(NodeData)myGraph.getNode(CurNode.getDest());
			i++;
		}
		List<node_data> Ans=new LinkedList<node_data>();
		int j=0;
		if (Paths.get(dest) != null) {
			while (j < Paths.get(dest).size()) {
				Ans.add(myGraph.getNode(Paths.get(dest).get(j)));
				//System.out.println(Paths.get(dest).get(j));
				j++;
			}
		}
		else return null;
		return Ans;
	}

	/**
	 * returns the length of the shortest path between src to dest
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return Distance of the src to dest in double
	 */

//	@Override
//	public double shortestPathDist(int src, int dest) {
//		if (myGraph.getNode(src) == null || myGraph.getNode(dest) == null)
//			throw new RuntimeException("src or dst dose not exist");
//		if (src == dest) return 0;
//		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
//		Queue<Integer> myQueue = new LinkedList<>();
//		int current;
//		boolean desH = false;
//		myQueue.add(src);
//		myBoard.put(src, 0.0);
//		while (!myQueue.isEmpty()) {
//			current = myQueue.poll();
//			if (current == dest) {
//				desH = true;
//			} else desH = false;
//			if (myGraph.getE(current) != null) {
//
//				ArrayList<edge_data> myEData = new ArrayList<>(myGraph.getE(current));
//				boolean flag = true;
//				while (!desH && flag) {
//					int minIndex = minInArray(myEData);
//					if (minIndex != -1) {
//						edge_data minE = myEData.remove(minIndex);
//						if (updateBoard(myBoard, minE))
//							myQueue.add(minE.getDest());
//					} else flag = false;
//				}
//			}
//		}
//		return myBoard.get(dest);
//	}
//
//	private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge) {
//		int dest = myedge.getDest();
//		int src = myedge.getSrc();
//		double amount = myedge.getWeight() + board.get(src);
//		if (!board.containsKey(dest)) {
//			board.put(dest, amount);
//		} else {
//			if (board.get(dest) > amount) {
//				board.put(dest, amount);
//			} else {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private int minInArray(List<edge_data> myList) {
//		if (!myList.isEmpty()) {
//			int minWE = 0;
//			for (int i = 1; i < myList.size(); i++) {
//				if (myList.get(minWE).getWeight() > myList.get(i).getWeight()) {
//					minWE = i;
//				}
//			}
//			return minWE;
//		}
//		return -1;
//	}
///**
// * returns the the shortest path between src to dest - as an ordered List of nodes:
// * src--> n1-->n2-->...dest
// * see: https://en.wikipedia.org/wiki/Shortest_path_problem
// * @param src - start node
// * @param dest - end (target) node
// * @return the path of the way in list of node_data
// */
//	@Override
//	public List<node_data> shortestPath(int src, int dest) {
//		if (myGraph.getNode(src) == null || myGraph.getNode(dest) == null)
//			throw new RuntimeException("src or dst dose not exist");
//		if (src == dest) {
//			List<node_data> t = new LinkedList<>();
//			t.add(myGraph.getNode(src));
//			return t;
//		}
//		HashMap<Integer, Double> myBoard = new LinkedHashMap<>();
//		HashMap<Integer, LinkedList<node_data>> myBoardList = new LinkedHashMap<>();
//		Queue<Integer> myQueue = new LinkedList<>();
//		int current;
//		boolean desH = false;
//		myQueue.add(src);
//		myBoard.put(src, 0.0);
//		LinkedList<node_data> temp = new LinkedList<>();
//		temp.add(myGraph.getNode(src));
//		myBoardList.put(src, temp);
//		while (!myQueue.isEmpty()) {
//			current = myQueue.poll();
//			if (current == dest) {
//				desH = true;
//			} else desH = false;
//			if (myGraph.getE(current) != null) {
//				ArrayList<edge_data> myEData = new ArrayList<>(myGraph.getE(current));
//				boolean flag = true;
//				while (!desH && flag) {
//					int minIndex = minInArray(myEData);
//					if (minIndex != -1) {
//						edge_data minE = myEData.remove(minIndex);
//						if (updateBoard(myBoard, minE, myBoardList))
//							myQueue.add(minE.getDest());
//					} else flag = false;
//				}
//			}
//		}
//		return myBoardList.get(dest);
//	}
//
//	private boolean updateBoard(HashMap<Integer, Double> board, edge_data myedge, HashMap<Integer, LinkedList<node_data>> map) {
//		int dest = myedge.getDest();
//		int src = myedge.getSrc();
//		LinkedList<node_data> list = map.get(src);
//		double amount = myedge.getWeight() + board.get(src);
//		if (!board.containsKey(dest)) {
//			LinkedList<node_data> tempList = new LinkedList<>(list);
//			tempList.add(myGraph.getNode(dest));
//			board.put(dest, amount);
//			map.put(dest, tempList);
//		} else {
//			if (board.get(dest) > amount) {
//				LinkedList<node_data> tempList = new LinkedList<>(list);
//				tempList.add(myGraph.getNode(dest));
//				board.put(dest, amount);
//				map.put(dest, tempList);
//			} else return false;
//		}
//		return true;
//	}
	/**
	 * computes a relatively short path which visit each node in the targets List.
	 * Note: this is NOT the classical traveling salesman problem,
	 * as you can visit a node more than once, and there is no need to return to source node -
	 * just a simple path going over all nodes in the list.
	 * @param targets
	 * @return the path of the way in list of node_data
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		resetTag();
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
	/**
	 * Compute a deep copy of this graph.
	 * @return
	 */
	@Override
	public graph copy() {
		return new DGraph(myGraph);//
	}
	private void resetTag(){
		int i=0;
		while(i<myGraph.nodeSize()) {
			myGraph.getNode(i).setTag(0);
			i++;
		}
	}

}
