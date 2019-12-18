package dataStructure;

import java.util.*;

public class DGraph implements graph{
	HashMap<Integer,NodeData> NMap = new LinkedHashMap<>();
	private int _EdgeZise = 0;
	private int _MC=0;

	public DGraph(){
    }
	public DGraph(graph g){
        List<node_data> list =new LinkedList<>(g.getV());
        for (node_data i : list) {
            NodeData nodeDataTemp = new NodeData(i);
            nodeDataTemp.setE(g.getE(nodeDataTemp.getKey()));
            NMap.put(nodeDataTemp.getKey(), nodeDataTemp);

        }

    }

	@Override
	public node_data getNode(int key) {
		if (NMap.containsKey(key))
			return NMap.get(key);
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (NMap.containsKey(src))
			return NMap.get(src).getEdata(dest);
		return null;
	}

	@Override
	public void addNode(node_data n) {
		if (!NMap.containsKey(n.getKey())) {
			NMap.put(n.getKey(), new NodeData(n));
			_MC++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) {
		if (!(NMap.containsKey(dest)&&NMap.containsKey(src)))
			throw new RuntimeException("not Exist");
		Edata edata = new Edata(src,dest,w);
		NMap.get(src).put(dest,edata);
		_EdgeZise++;
		_MC++;
		
	}

	@Override
	public Collection<node_data> getV() {
		List<node_data> list = new ArrayList<node_data>(NMap.values());
		return list;

	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if (!NMap.containsKey(node_id))
			return null;
		return NMap.get(node_id).getE();
	}

	@Override
	public node_data removeNode(int key) {
		if (NMap.containsKey(key)){
			_MC++;
			for (int i =0; i<NMap.size();i++){
				if (NMap.containsKey(i)){
					Edata temp = NMap.get(i).removeEdge(key);
					if (temp!=null){
						_EdgeZise--;
					}
				}
			}
			return NMap.remove(key);
		}
		else return null;

	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if (!(NMap.containsKey(dest)&&NMap.containsKey(src))){
			return null;
		}
		_MC++;
		Edata temp = NMap.get(src).removeEdge(dest);
		if (temp!=null){
			_EdgeZise--;
		}
		return temp;
	}

	@Override
	public int nodeSize() {
		return NMap.size();
	}

	@Override
	public int edgeSize() {
		return _EdgeZise;
	}

	@Override
	public int getMC() {
		return _MC;
	}

    public boolean isConnected() {
        for (int i=0 ; i<NodeData.getIDMAX();i++){
            if (NMap.containsKey(i)){
                NMap.get(i).set_tagFolow(-1);
            }
        }
	    boolean FLAG = true;
        boolean notfound = true;
        int current = 0;
        for (int i=0 ; i<=NodeData.getIDMAX()&& notfound;i++){
            notfound = !NMap.containsKey(i);
            current = i;
        }
        Queue<Integer> myQue = new LinkedList<>();
            while (FLAG){
                FLAG = false;
	        if (NMap.containsKey(current)){
	               NodeData temp = NMap.get(current);
                    List<edge_data> list =new LinkedList<>(temp.getE());
                    for (edge_data i : list) {
                        if (NMap.get(i.getDest()).get_tagFolow()!=1) {
                            NMap.get(i.getDest()).set_tagFolow(1);
                            myQue.add(i.getDest());
                        }
                    }
	        }
                if (!myQue.isEmpty()) {
                    current = myQue.poll();
                    FLAG = true;
                }
        }
        for (int i=0 ; i<=NodeData.getIDMAX();i++){
            if (NMap.containsKey(i)){
                if(NMap.get(i).get_tagFolow()==-1){
                    System.out.println(i);
                    return false;
                }
            }
        }
        return true;


	}




}
