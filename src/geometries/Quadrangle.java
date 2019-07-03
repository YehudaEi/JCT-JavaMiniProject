package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Quadrangle extends Geometry implements FlatGeometry {
    Triangle _tri1;
    Triangle _tri2;

    // ***************** Constructors ********************** //
    public Quadrangle() {
        this._tri1 = new Triangle();
        this._tri2 = new Triangle();
    }
    public Quadrangle(Triangle tri1, Triangle tri2) {
        this._tri1 = tri1;
        this._tri2 = tri2;
    }
    public Quadrangle(Point3D P1, Point3D P2, Point3D P3, Point3D P4) {
        _tri1 = new Triangle(P1, P2, P4);
        _tri2 = new Triangle(P2, P3, P4);
    }
    // ***************** Getters/Setters ********************** //
    public Triangle getTri1() {
        return _tri1;
    }
    public void setTri1(Triangle tri1) {
        this._tri1 = tri1;
    }
    public Triangle getTri2() {
        return _tri2;
    }
    public void setTri2(Triangle tri2) {
        this._tri2 = tri2;
    }

    // ***************** Operations ******************** //
    /**
     * FUNCTION
     * getNormal
     * PARAMETERS
     * point – a point on a quadrangle
     * RETURN VALUE
     * the normal vector to the plane that the triangle is on it
     * MEANING
     * This functions computes a vector that is normal to the triangle
     */
    public Vector getNormal(Point3D p) throws Exception{
        return _tri1.getNormal(p);
    }
    /**
     * FUNCTION
     * FindIntersections
     * PARAMETERS
     * Ray – the ray that comes out of camera
     * RETURN VALUE
     * A List that contains all of the points that the Ray
     * intersect with the plane
     * MEANING
     * This functions checks where are the points that
     * the ray intersect with outplane
     * SEE ALSO
     * all of the Geometry class function:
     * List<Point3D> FindIntersections(Ray ray).
     **/
    @Override
    public List<Point3D> findIntersections(Ray ray) throws Exception{
        List<Point3D> tri1 = _tri1.findIntersections(ray);
        List<Point3D> tri2 = _tri2.findIntersections(ray);

        List<Point3D> res = new ArrayList<Point3D>();
        for (Point3D iPoint: tri1){
            res.add(iPoint);
        }
        for (Point3D iPoint: tri2){
            if (!res.contains(iPoint)) { 
                res.add(iPoint); 
            } 
        }
        return res;
    }
}