package elements;

import primitives.Vector;
import primitives.Point3D;
import java.awt.Color;

public abstract class Light {
	protected Color color;
	
	// ***************** Constructors ********************** //
	public Light() {
		super();
		this.color = new Color(255,255,255);
	}
	public Light(Color color) {
		super();
		this.color = color;
	}
	public Light(Light light) {
		super();
		this.color =new Color(light.getColor().getRed(),light.getColor().getGreen(),
				light.getColor().getBlue());
	}

	// ***************** Getters/Setters ********************** //
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	// ***************** Administration  ******************** //
	public abstract Color getIntensity(Point3D point) throws Exception;
	
	public abstract Vector getL(Point3D point) throws Exception;
}
