package primitives;

public class Coordinate implements Comparable<Coordinate> {
	
	protected double coordinate;
	
	// ***************** Constructors ********************** // 
	public Coordinate()
	{
		coordinate = 0;
	}
	public Coordinate(double a)
	{
		this.coordinate = a;
	}
	public Coordinate(Coordinate a)
	{
		this.coordinate=a.getCoordinate();
	}
	
	// ***************** Getters/Setters ********************** // 
	public double getCoordinate() {
		return coordinate;
	}
	public void setcoordinate(double coordinate) {
		this.coordinate = coordinate;
	}
	
	// ***************** Administration  ******************** //
	@Override
	public int compareTo(Coordinate a)
	{
		if(this.coordinate<a.getCoordinate())
	          return -1;
	    else if(a.getCoordinate()<this.coordinate)
	          return 1;
	    return 0;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(coordinate) != Double.doubleToLongBits(other.coordinate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ""+coordinate;
	}
	
	// ***************** Operations ******************** // 
	public void add(Coordinate a)
	{
		coordinate+=a.getCoordinate();
	}
	
	public void substract(Coordinate a)
	{
		coordinate-=a.getCoordinate();
	}
}
