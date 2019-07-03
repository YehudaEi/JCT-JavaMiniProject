package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public class AmbientLight extends Light {
	protected double ka;
	// ***************** Constructors ********************** //	
	public AmbientLight() {
		super();
		this.ka = 0.1;
	}
	public AmbientLight(double _ka) {
		super();
		this.ka = _ka;
	}

	public AmbientLight(AmbientLight l1) {
		super(l1);
		this.ka =l1.getKa();
	}
	public AmbientLight(double k,Color c) {
		super(c);
		this.ka =k;
	}

	// ***************** Getters/Setters ********************** //
	public double getKa() {
		return ka;
	}
	public void setKa(double ka) {
		this.ka = ka;
	}
	
	// ***************** Administration  ******************** //
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmbientLight other = (AmbientLight) obj;
		if (Double.doubleToLongBits(ka) != Double.doubleToLongBits(other.ka))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AmbientLight: ka=" + ka;
	}
	
	@Override
	public Color getIntensity(Point3D point) {
		int red=(int)(ka*color.getRed());
		int green=(int)(ka*color.getGreen());
		int blue=(int)(ka*color.getBlue());
		return(new Color(red,green,blue));
	}

	public Vector getL(Point3D point)
	{
		return new Vector();
	}
}
