package create;

public class old {

	public void x() {
		System.out.println(this);
		System.out.println(getClass().getName());
	}

	public old(int a) {
		super();
	}

	public old() {
	}

	public String x2(int a) {
		System.out.println(getClass().getName());
		return Integer.toString(a);
	}

	public void mba() {
		System.err.println(getClass().getName());
	}

	public void a(int a) {
		System.out.println(a);
	}
}
