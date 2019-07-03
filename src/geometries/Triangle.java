package geometries;

import primitives.Vector;
import primitives.Material;
import primitives.Ray;
import primitives.Point3D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Triangle extends Geometry implements Comparable<Triangle> ,FlatGeometry{

	protected Point3D p1;
	protected Point3D p2;
	protected Point3D p3;
	// ***************** Constructors ********************** // 
	public Triangle() {//default constructor 
		super();
		this.p1 = new Point3D();
		this.p2 = new Point3D();
		this.p3 = new Point3D();
	}
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {//parameter constructor 
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	public Triangle(Point3D p1, Point3D p2, Point3D p3,Color color, Material material) {//parameter constructor 
		super(color,material);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public Triangle(Triangle triangle) {//copy constructor
		super((Geometry) triangle);
		this.p1 = new Point3D(triangle.getP1());
		this.p2 = new Point3D(triangle.getP2());
		this.p3 = new Point3D(triangle.getP3());
	}
	// ***************** Getters/Setters ********************** // 
	public Point3D getP1() {
		return p1;
	}
	
	public void setP1(Point3D p1) {
		this.p1 = p1;
	}
	public Point3D getP2() {
		return p2;
	}
	public void setP2(Point3D p2) {
		this.p2 = p2;
	}
	public Point3D getP3() {
		return p3;
	}
	public void setP3(Point3D p3) {
		this.p3 = p3;
	}
	
	
	public Vector getNormal(Point3D point) throws Exception {
		Point3D temp1=new Point3D(p1);
		Point3D temp2=new Point3D(p1);
		temp1.substract(new Vector(new Point3D(p2))); 
		Vector v1=new Vector(new Point3D(temp1));
		temp2.substract(new Vector(new Point3D(p3)));
		Vector v2=new Vector(new Point3D(temp2));
		Vector result=v1.crossProduct(v2);
		result.normalize();
		return result;
	}
	// ***************** Administration  ******************** //
	@Override
	public int compareTo(Triangle t)
	{
		if(t.getP1().compareTo(p1)==0&&t.getP2().compareTo(p2)==0&&t.getP3().compareTo(p3)==0)
		   return 0;
		return 1;	
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		if (p3 == null) {
			if (other.p3 != null)
				return false;
		} else if (!p3.equals(other.p3))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "p1= "+p1.toString()+", p2= "+p2.toString()+", p3= "+p3.toString();
	}
	
	public List<Point3D> findIntersections(Ray r) throws Exception //this function return the intersection of the triangle with the ray r 
	{
		Point3D point=new Point3D(p1);
		point.substract(new Vector(new Point3D(p2)));
		Vector v1=new Vector(point);
		Point3D point2=new Point3D(p1);
		point2.substract(new Vector(new Point3D(p3)));
		Vector v2=new Vector(point2);
		Plane plane=new Plane(v1.crossProduct(v2),new Point3D(p1));
		List<Point3D> help=plane.findIntersections(r);
		if(help.isEmpty())
			return help;
		Point3D T1=new Point3D(p1);
		Point3D T2=new Point3D(p2);
		T1.substract(new Vector(r.getStart()));
		Vector V1=new Vector(T1);
		T2.substract(new Vector(r.getStart()));
		Vector V2=new Vector(T2);
		Point3D T3=new Point3D(p3);
		T3.substract(new Vector(r.getStart()));
		Vector V3=new Vector(T3);
		Vector N1=V1.crossProduct(V2);
		N1.normalize();
		Vector N2=V2.crossProduct(V3);
		N2.normalize();
		Vector N3=V3.crossProduct(V1);
		N3.normalize();
		Point3D P=new Point3D(help.get(0));
		P.substract(new Vector(r.getStart()));
		if(N1.dotProduct(new Vector(P))> 0 && N2.dotProduct(new Vector(P))>0 && N3.dotProduct(new Vector(P))>0)
			return help;
		if(N1.dotProduct(new Vector(P))< 0 && N2.dotProduct(new Vector(P))<0 && N3.dotProduct(new Vector(P))<0)
			return help;
		else
			return new ArrayList<Point3D>();
	}
}
