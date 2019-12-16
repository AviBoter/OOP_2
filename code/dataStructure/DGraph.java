package dataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DGraph implements graph{
	HashMap<Integer,NodeData> NMap = new HashMap<>();
	private int _EdgeZise = 0;
	private int _MC=0;

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
		return (Collection<node_data>) NMap;

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

}
