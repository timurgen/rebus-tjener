package student.hin.no;

import java.io.Serializable;

public class ToSend implements Serializable 
{

	private int a;
	private long b;
	private String c;
	
	public ToSend()
	{}
	
	public ToSend(int _a, long _b, String _c)
	{
		a = _a;
		b = _b;
		c = _c;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public long getB() {
		return b;
	}

	public void setB(long b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
}
