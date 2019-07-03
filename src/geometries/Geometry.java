package geometries;

import primitives.Vector;
import primitives.Material;
import primitives.Ray;
import primitives.Point3D;
import java.awt.Color;
import java.util.List;

public abstract class Geometry {
	protected Color emission;
	protected Material material;

	//***************** Constructors ********************** //
	public Geometry() {
		super();
		this.emission = Color.WHITE;
		this.material = new Material(1,1,1);
	}
	public Geometry(Color emission, Material material) {
		super();
		this.emission = emission;
		this.material = material;
	}
	public Geometry(Geometry g) {
		super();
		this.emission = g.getEmission();
		this.material = g.getMaterial();
	}
	//***************** Getters/Setters ********************** // 
	public Color getEmission() {
		return emission;
	}

	public void setEmission(Color emission) {
		this.emission = emission;
	}
	
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public void setKs(double ks){
		material.setKs(ks);
	}
	public void setKd(double kd){
		material.setKd(kd);
	}
	public void setKr(double kr){
		material.setKr(kr);
	}
	public void setKt(double kt){
		material.setKt(kt);
	}
	public void setShininess(double n){
		material.setN(n);
	}

	
	public abstract Vector getNormal(Point3D point) throws Exception;
	public abstract List<Point3D> findIntersections(Ray r) throws Exception;
}
