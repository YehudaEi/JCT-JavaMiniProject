package primitives;

public class Material {
	private double Kd;
	private double Ks;
	private double n;
	private double Kr;
	private double Kt;
	
	//***************** Constructors ********************** //
	public Material(double kd, double ks, double n) {
		super();
		Kd = kd;
		Ks = ks;
		this.n = n;
	}
	public Material() {
		Kd = 1;
		Ks = 1;
		n = 0.1;
		Kr = 0;
		Kt = 0;
	}
	public Material(double kd, double ks, double n,double kr, double kt) {
		super();
		Kd = kd;
		Ks = ks;
		this.n = n;
		Kr=kr;
		Kt=kt;
	}
	public Material(Material m) {
		super();
		Kd = m.getKd();
		Ks = m.getKs();
		this.n =m.getN();
		Kr=m.getKr();
		Kt=m.getKt();
	}

	//***************** Getters/Setters ********************** // 
	public double getKd() {
		return Kd;
	}
	public void setKd(double kd) {
		Kd = kd;
	}
	public double getKs() {
		return Ks;
	}
	public void setKs(double ks) {
		Ks = ks;
	}
	public double getN() {
		return n;
	}
	public void setN(double n) {
		this.n = n;
	}
	public double getKr() {
		return Kr;
	}
	public void setKr(double kr) {
		Kr = kr;
	}
	public double getKt() {
		return Kt;
	}
	public void setKt(double kt) {
		Kt = kt;
	}
	
	// ***************** Administration  ******************** //
	
	@Override
	public String toString() {
		return "Material: Kd=" + Kd + ", Ks=" + Ks + ", n=" + n+", Kr=" + Kr+", Kt=" + Kt;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (Double.doubleToLongBits(Kd) != Double.doubleToLongBits(other.Kd))
			return false;
		if (Double.doubleToLongBits(Kr) != Double.doubleToLongBits(other.Kr))
			return false;
		if (Double.doubleToLongBits(Ks) != Double.doubleToLongBits(other.Ks))
			return false;
		if (Double.doubleToLongBits(Kt) != Double.doubleToLongBits(other.Kt))
			return false;
		if (Double.doubleToLongBits(n) != Double.doubleToLongBits(other.n))
			return false;
		return true;
	}
	
}
