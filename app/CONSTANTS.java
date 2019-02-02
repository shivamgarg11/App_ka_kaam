package null;

public class CONSTANTS{
	private int c3;
	private int c1;
	private int c2;
	private double c4;

	public void setC3(int c3){
		this.c3 = c3;
	}

	public int getC3(){
		return c3;
	}

	public void setC1(int c1){
		this.c1 = c1;
	}

	public int getC1(){
		return c1;
	}

	public void setC2(int c2){
		this.c2 = c2;
	}

	public int getC2(){
		return c2;
	}

	public void setC4(double c4){
		this.c4 = c4;
	}

	public double getC4(){
		return c4;
	}

	@Override
 	public String toString(){
		return 
			"CONSTANTS{" + 
			"c3 = '" + c3 + '\'' + 
			",c1 = '" + c1 + '\'' + 
			",c2 = '" + c2 + '\'' + 
			",c4 = '" + c4 + '\'' + 
			"}";
		}
}
