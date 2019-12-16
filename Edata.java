package dataStructure;

public class Edata implements edge_data {
	int Src,Dest,tag;
	String info;
	 
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
		return this.getWeight();
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return this.getInfo();
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return this.getTag();
		}

	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub
		this.tag=t;	
	}

}
