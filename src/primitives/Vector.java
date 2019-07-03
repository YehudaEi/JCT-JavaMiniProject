package primitives;

public class Vector implements Comparable<Vector> {
	
        protected Point3D _head;

        // ***************** Constructors ********************** // 
        public Vector()
        {
                _head=new Point3D();
        }
        public Vector(Point3D _head)
        {
                this._head = _head;
        }
        public Vector(Vector v)
        {
                _head=new Point3D(v.getHead());
        }
        public Vector(double x, double y, double z)
        {
                _head=new Point3D(x, y, z);
        }
        public Vector(Point3D p1, Point3D p2){
                p2.substract(new Vector(p1));
                _head = new Point3D(p2);
        }

        // ***************** Getters/Setters ********************** // 
        public Point3D getHead() {
                return _head;
        }
        public void setHead(Point3D _head) {
                this._head = _head;
        }

        // ***************** Administration  ******************** // 
        @Override
        public int compareTo(Vector v)
        {
                return this._head.compareTo(v.getHead());
                
        }
        @Override
        public String toString() {
                return _head.toString();
        }
        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Vector other = (Vector) obj;
                if (_head == null) {
                        if (other._head != null)
                                return false;
                } else if (!_head.equals(other._head))
                        return false;
                return true;
        }
        // ***************** Operations ******************** // 
        /*************************************************
                * FUNCTION
                * add
                * PARAMETERS
                * Vector – vector to add
                * RETURN VALUE
                * void
                * MEANING
                * adds new vector to the givin one without 
                * producing a new vector
                * SEE ALSO
        **************************************************/
        public void add (Vector vector )
        {
                _head.getX().add(vector._head.getX());
                _head.getY().add(vector._head.getY());
                _head.getZ().add(vector._head.getZ());
        }
        /*************************************************
                * FUNCTION
                * subtract
                * PARAMETERS
                * Vector – vector to subtract
                * RETURN VALUE
                * void
                * MEANING
                * subs new vector to the givin one without 
                * producing a new vector
                * SEE ALSO
        **************************************************/
        public void subtract (Vector vector )
        {  
                _head.getX().substract(vector._head.getX());
                _head.getY().substract(vector._head.getY());
                _head.getZ().substract(vector._head.getZ());

        }
        /*************************************************
                * FUNCTION
                * scale
                * PARAMETERS
                * scalingFacor (double) – perform scalar 
                * multiplacation on vector with this double
                * RETURN VALUE
                * void
                * MEANING
                * performs scalar mult on vector
                * SEE ALSO
        **************************************************/
        public void scale(double scalingFacor)
        {
                _head.setX(new Coordinate(scalingFacor*_head.getX().getCoordinate()));
                _head.setY(new Coordinate(scalingFacor*_head.getY().getCoordinate()));
                _head.setZ(new Coordinate(scalingFacor*_head.getZ().getCoordinate()));
        }
        /*************************************************
                * FUNCTION
                * length
                * PARAMETERS
                * RETURN VALUE
                * double = the vector length
                * MEANING
                * return the vector length
                * SEE ALSO
        **************************************************/
        public double length()
        {
                return(Math.sqrt(Math.pow(_head.getX().getCoordinate(), 2)+Math.pow(_head.getY().getCoordinate(), 2)+Math.pow(_head.getZ().getCoordinate(), 2)));
        }
        /*************************************************
                * FUNCTION
                * normalize
                * PARAMETERS
                * RETURN VALUE
                * void
                * MEANING
                * normilizis the vector - make it a vector with a length of 1 
                * SEE ALSO
                * scale function
        **************************************************/
        public void normalize() throws Exception
        {
                double distance = this.length();
                if(distance != 0)
                this.scale(1 / distance);
                else
                throw new Exception("Cannot divide in 0");
        }
        /*************************************************
                * FUNCTION
                * crossProduct
                * PARAMETERS
                * Vector - performs crossprotuct mult 
                * with the vector and the givin vector
                * RETURN VALUE
                * Vector - the result of the crossprotuct
                * MEANING
                * the function perform crossproduct between 
                * 2 vectors
                * SEE ALSO
        **************************************************/
        public Vector crossProduct (Vector vector)
        {
                return (new Vector(
                new Point3D(
                new Coordinate(this._head.getY().getCoordinate()*vector._head.getZ().getCoordinate()-this._head.getZ().getCoordinate()*vector._head.getY().getCoordinate()),
                new Coordinate(this._head.getZ().getCoordinate()*vector._head.getX().getCoordinate()-this._head.getX().getCoordinate()*vector._head.getZ().getCoordinate()),
                new Coordinate(this._head.getX().getCoordinate()*vector._head.getY().getCoordinate()-this._head.getY().getCoordinate()*vector._head.getX().getCoordinate()))));
        }
        /*************************************************
                * FUNCTION
                * dotProduct
                * PARAMETERS
                * scalingFacor (double) – perform scalar 
                * multiplacation on vector with this double
                * RETURN VALUE
                * double - the result of the dotprotuct
                * MEANING
                * performs scalar mult on vector
                * SEE ALSO
        **************************************************/
        public double dotProduct(Vector vector)
        {
                return(_head.getX().getCoordinate()*vector._head.getX().getCoordinate()+
                _head.getY().getCoordinate()*vector._head.getY().getCoordinate()+
                _head.getZ().getCoordinate()*vector._head.getZ().getCoordinate());
        }
}