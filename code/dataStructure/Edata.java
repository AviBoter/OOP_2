package dataStructure;

public class Edata implements edge_data {
	private int Src,Dest,tag;
	double Weight;
	private String info;

	public Edata(int src,int dest, double weight){
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
