package primitives;

public class Point3D extends Point2D{
	protected Coordinate z;
	
	// ***************** Constructors ********************** // 
	public Point3D()
	{
		super();
		z=new Coordinate();
	}
	public Point3D(Coordinate a, Coordinate b, Coordinate c) {
		super(a,b);
		this.z = c;
	}
	public Point3D(double a, double b, double c) {
		super(new Coordinate(a),new Coordinate(b));
		this.z = new Coordinate(c);
	}
	public Point3D(Point3D p)
	{
		this.x = new Coordinate(p.getX());
		this.y = new Coordinate( p.getY());
		this.z= new Coordinate(p.getZ());
	}
	
	// ***************** Getters/Setters ********************** // 
	public Coordinate getZ() {
		return z;
	}
	
	public void setZ(Coordinate z) {
		this.z = z;
	}
	
	// ***************** Administration  ******************** // 
	public int compareTo(Point3D p)
	{
		if(this.x.compareTo(p.getX())==0&&this.y.compareTo(p.getY())==0 && this.z.compareTo(p.getZ())==0)
			return 0;
		return 1;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y +","+z+ ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point3D other = (Point3D) obj;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}
	// ***************** Operations ******************** // 
	public double distance(Point3D p)
	{
		Point3D temp=new Point3D(this);
		temp.x.substract(p.getX());
		temp.y.substract(p.getY());
		temp.z.substract(p.getZ());
		return(Math.sqrt(Math.pow(temp.getX().getCoordinate(), 2)+Math.pow(temp.getY().getCoordinate(), 2)+Math.pow(temp.getZ().getCoordinate(), 2))); 
	}
	
	public void add(Vector vector) {
		this.x.add(vector.getHead().getX());
		this.y.add(vector.getHead().getY());
		this.z.add(vector.getHead().getZ());
	}
	
	public void substract(Vector vector) {			
		this.x.substract(vector.getHead().getX());
		this.y.substract(vector.getHead().getY());
		this.z.substract(vector.getHead().getZ());
	}
}
