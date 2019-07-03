package geometries;

import primitives.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Plane extends Geometry implements Comparable<Plane>,FlatGeometry{
	protected Vector normal;
	protected Point3D p;
	
	// ***************** Constructors ********************** //
	public Plane() {
		super();
		this.normal = new Vector();
		this.p = new Point3D();
	}
	
	public Plane(Vector vector, Point3D p) {
		super();
		this.normal = vector;
		this.p = p;
	}
	
	public Plane(Vector vector, Point3D p,Material material,Color color) {
		super(color,material);
		this.normal = vector;
		this.p = p;
	}
	
	public Plane(Plane plane) {
		super((Geometry)plane);
		this.normal = new Vector(plane.getNormal());
		this.p = new Point3D(plane.getP());
	}
	// ***************** Getters/Setters ********************** // 
	public Vector getNormal() {
		return normal;
	}
	
	public void setNormal(Vector vector) {
		this.normal = vector;
	}
	public Point3D getP() {
		return p;
	}
	public void setP(Point3D p) {
		this.p = p;
	}
	
	
	public Vector getNormal(Point3D point) throws Exception {
		Vector result=new Vector(normal);
		result.normalize();
		return result;
	}
	
	// ***************** Administration  ******************** //
	
	@Override
	public int compareTo(Plane plane)
	{
		if(normal.compareTo(plane.getNormal())==0 && p.compareTo(plane.getP())==0)
		   return 0;
		return 1;
		
	}
	/*************************************************
		* FUNCTION
		* equals
		* PARAMETERS
		* obj - an object
		* RETURN VALUE
		* bool
		* MEANING
		* checks if 2 objects are equal
		* SEE ALSO
	**************************************************/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plane other = (Plane) obj;
		if (normal == null) {
			if (other.normal != null)
				return false;
		} else if (!normal.equals(other.normal))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}
        
	/*************************************************
		* FUNCTION
		* toString           
		* PARAMETERS
		* RETURN VALUE
		* string
		* MEANING
		* return a string with the data
		* SEE ALSO
	**************************************************/
	@Override
	public String toString() {
		return "vector= "+normal.toString()+ ", p= "+p.toString();
	}

	/*************************************************
		* FUNCTION
		* findIntersections           
		* PARAMETERS
		* r (ray)
		* RETURN VALUE
		* List
		* MEANING
		* finds all intersection between object and ray
		* SEE ALSO
	**************************************************/
	public List<Point3D> findIntersections(Ray r)
	{
		Vector v=new Vector(normal);
		v.scale(-1);//-N
		Point3D p1=new Point3D(r.getStart());
		p1.substract(new Vector(new Point3D(p)));
		Vector v1=new Vector(p1);
		List<Point3D> intersections=new ArrayList<Point3D>();
		double help=(normal.dotProduct(r.getDirection()));
		if(help==0)
				return intersections;
		double tmp=1/help;
		v1.scale(tmp);
		double t=v.dotProduct(v1);
		if(t>0)
		{Point3D result=new Point3D(r.getStart());
		Vector direction=new Vector(r.getDirection());
		direction.scale(t);
		result.add(direction);
		intersections.add(result);
		}
		return(intersections);
	}

}
