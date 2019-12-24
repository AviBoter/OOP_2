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
		Queue<Edata> Pqueue = new LinkedList<Edata>();
		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(_G.nodeSize());
		NodeData Runner=(NodeData)_G.getNode(src);
		Edata CurNode = null;

		dist.put(src,(double)0);
		int i=0;

		while(i<_G.nodeSize()-1 || CurNode.getSrc()==dest) {                                         
			AddEdgesToPriorityQueue(Pqueue,Runner);
			CurNode=(Edata)Pqueue.poll();
			Runner=(NodeData)_G.getNode(CurNode.getDest());
			if(!dist.containsKey(CurNode.getDest()) || (dist.containsKey(CurNode.getDest()) && dist.get(CurNode.getDest())>dist.get(CurNode.getSrc())+CurNode.getWeight())) {
				dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
			}
			i++;
		}
		return dist.get(dest).doubleValue();
	}

	private void AddEdgesToPriorityQueue(Queue<Edata> Pqueue,NodeData Runner) {
		Collection<edge_data> Col=_G.getE(Runner.getKey());
		PriorityQueue<edge_data> minHeap = new PriorityQueue<edge_data>(new Comparator<edge_data>() {
			@Override
			public int compare(edge_data o1, edge_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});

		while(Col.iterator().hasNext()) {
			minHeap.add(Col.iterator().next());
			Col.remove(Col.iterator().next());
		}
		while(minHeap.iterator().hasNext()) {
			Pqueue.add((Edata)minHeap.poll());
		}
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		Queue<Edata> Pqueue = new LinkedList<Edata>();
		HashMap<Integer,Double> dist=new HashMap<Integer,Double>(_G.nodeSize());
		HashMap<Integer,ArrayList<Integer>> Paths=new HashMap<Integer,ArrayList<Integer>>(_G.nodeSize());
		NodeData Runner=(NodeData)_G.getNode(src);
		Edata CurNode = null;

		dist.put(src,(double)0);
		Paths.put(src, new ArrayList<Integer>());
		Paths.get(src).add(0);
		int i=0;

		while(i<_G.nodeSize()-1 || CurNode.getSrc()==dest) {                                         
			AddEdgesToPriorityQueue(Pqueue,Runner);
			CurNode=(Edata)Pqueue.poll();
			Runner=(NodeData)_G.getNode(CurNode.getDest());
			if(!dist.containsKey(CurNode.getDest()) || (dist.containsKey(CurNode.getDest()) && dist.get(CurNode.getDest())>dist.get(CurNode.getSrc())+CurNode.getWeight())) {
					Paths.put(CurNode.getDest(), new ArrayList<Integer>());
					dist.put(CurNode.getDest(),dist.get(CurNode.getSrc())+CurNode.getWeight());
					int k=0;
					while(k<Paths.get(CurNode.getSrc()).size()) {
						Paths.get(CurNode.getDest()).add(Paths.get(CurNode.getSrc()).get(k));
						k++;
					}
					Paths.get(CurNode.getDest()).add(CurNode.getDest());
			}
			i++;
		}
		List<node_data> Ans=new LinkedList<node_data>();
		int j=0;
		while(j<Paths.get(dest).size()) {
			Ans.add(_G.getNode(Paths.get(dest).get(j)));
			System.out.println(Paths.get(dest).get(j));
			j++;
		}
		return Ans;
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
