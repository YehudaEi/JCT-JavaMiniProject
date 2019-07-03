package geometries;

import primitives.Vector;
import primitives.Material;
import primitives.Ray;
import primitives.Point3D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Sphere extends Geometry {
 
	protected double radius;
    protected Point3D p;
 
	//***************** Constructors ********************** //
	public Sphere() {
		super();
		this.radius =0;
		this.p = new Point3D();
	}
	public Sphere(double radius, Point3D p) {
		super();
		this.radius = radius;
		this.p = p;

	}
	public Sphere(double radius, Point3D p,Color color,Material material) {
		super(color,material);
		this.radius = radius;
		this.p = p;

	}
	public Sphere(Sphere s) {
		super((Geometry) s);
		this.radius = s.getRadius();
		this.p = new Point3D(s.getP());
	}
	
	//***************** Getters/Setters ********************** // 

	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public Point3D getP() {
		return p;
	}
	public void setP(Point3D p) {
		this.p = p;
	}
	public Vector getNormal(Point3D point) throws Exception {
		Point3D temp1=new Point3D(point);
		temp1.substract(new Vector(new Point3D(p)));
		Vector result= new Vector(temp1);
		result.normalize();
		return result;
	}
		
	// ***************** Administration  ******************** //
	@Override
	public String toString() {
		return "p= "+p.toString()+", radius= "+radius;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sphere other = (Sphere) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}
	public List<Point3D> findIntersections(Ray r)
	{
		Point3D center=new Point3D(p);
		Vector start=new Vector(r.getStart());
		center.substract(start);
		Vector L=new Vector(center);
		double tm=L.dotProduct(r.getDirection());
		
		double d=Math.sqrt(Math.pow(L.length(),2)-Math.pow(tm,2));
		List<Point3D>lst=new ArrayList<Point3D>();
		if(d<=radius)
		{
			double th=Math.sqrt(Math.pow(radius, 2)-Math.pow(d, 2));
			double t1=tm-th;
			double t2=tm+th;
			if(t1>0)
			{
				Vector V=new Vector(r.getDirection());
				V.scale(t1);
				Point3D P1=new Point3D(r.getStart());
				P1.add(V);
				lst.add(P1);
			}	
			if(t2>0)
			{
				Vector V1=new Vector(r.getDirection());
				V1.scale(t2);
				Point3D P2=new Point3D(r.getStart());
				P2.add(V1);
				lst.add(P2);
			
			}
		}
		return lst;		
	}
}
