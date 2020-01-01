package dataStructure;

import java.io.Serializable;

public class Edata implements edge_data, Serializable {
	private int Src,Dest,tag;
	double Weight;
	private String info;

	public Edata(edge_data e){
		this.Dest = e.getDest();
		this.Src = e.getSrc();
		this.Weight = e.getWeight();
	}
	public Edata(int src,int dest, double weight){
		if (weight<0) throw new RuntimeException("ERR: weight cant be negetive");
		if (src==dest) throw new RuntimeException("ERR: the destination can't be equals to the source ");
		this.Dest = dest;
		this.Src = src;
		this.Weight = weight;
	}
	@Override
	public int getSrc() {
		return this.Src;
	}
	@Override
	public int getDest() {
		// TODO Auto-generated method stub
		return this.Dest;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return this.Weight;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return tag;
		}

	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub
		this.tag=t;	
	}

}
