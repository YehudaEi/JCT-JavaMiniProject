package renderer;

import geometries.FlatGeometry;
import geometries.Geometry;
import primitives.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import elements.Light;
import Scene.*;

public class Render {

	private Scene _scene;
        private boolean Upgrade = true;
	private ImageWriter _imageWriter;
	private static int RECURSION_LEVEL = 3;

	//***************** Constructors ********************** //
	public Render() {
            this._scene = new Scene();
            this._imageWriter = new ImageWriter("image",500,500,500,500);
	}

	public Render(Scene _scene, ImageWriter _imageWriter) {
            super();
            this._scene = _scene;
            this._imageWriter = _imageWriter;
	}

	public Render(Render render) {
            this._scene = new Scene(render.getScene());
            this._imageWriter = new ImageWriter(render.getImageWriter());
	}

	//***************** Getters/Setters ********************** // 
	public Scene getScene() {
            return _scene;
	}

	public void setScene(Scene _scene) {
            this._scene = _scene;
	}

	public ImageWriter getImageWriter() {
            return _imageWriter;
	}
        
        public void writeToimage(){
            _imageWriter.writeToimage();
        }

	public void setImageWriter(ImageWriter _imageWriter) {
            this._imageWriter = _imageWriter;
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
		Render other = (Render) obj;
		if (_imageWriter == null) {
			if (other._imageWriter != null)
				return false;
		} else if (!_imageWriter.equals(other._imageWriter))
			return false;
		if (_scene == null) {
			if (other._scene != null)
				return false;
		} else if (!_scene.equals(other._scene))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Render: "+ "_scene = " + _scene + ", _imageWriter = " + _imageWriter;
	}

    /*************************************************
        * FUNCTION
        * renderImage
        * PARAMETERS
        * RETURN VALUE
        * MEANING
        * runs on all pixels and sends 5 reys thru every
        * pixel and then cakculates the averege between the rays 
        * and writes the color into every pixel he calculates 
        * SEE ALSO
        * calcColor (render)
        * constructRayThroughPixel (camera)
    **************************************************/
	public void renderImage() throws Exception
	{
		double sumRed = 0;
		double sumGreen = 0;
		double sumBlue = 0;
		int height = _imageWriter.getNy();
		int width = _imageWriter.getNx();
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
                if(Upgrade){
                    sumRed = 0;
                    sumGreen = 0;
                    sumBlue = 0;
                    for(int k = 0; k < 5; k++)
                    { 
                        Ray ray = _scene.getCamera().constructRayThroughPixel(
                                _imageWriter.getNx(), _imageWriter.getNy(), j, i,
                                _scene.getDistanceViewPlane(), _imageWriter.getWidth(),
                                _imageWriter.getHeight(),k);
                        Map<Geometry,List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                        if (intersectionPoints.isEmpty())
                            {
                                sumRed += _scene.getBackground().getRed();
                                sumGreen += _scene.getBackground().getGreen();
                                sumBlue += _scene.getBackground().getBlue();
                            }
                        else
                        {
                            Map<Geometry,Point3D> closestPoint = getClosestPoint(intersectionPoints);
                            for(Entry<Geometry, Point3D> entry: closestPoint.entrySet())
                            {
                                Color sumColor = calcColor(entry.getKey(),entry.getValue(),ray);
                                sumRed += sumColor.getRed();
                                sumGreen += sumColor.getGreen();
                                sumBlue += sumColor.getBlue();
                            }
                        }
                    }
                    sumRed /= 5;
                    sumGreen /= 5;
                    sumBlue /= 5;
                    Color sumColor = addColor(new Color((int)sumRed,(int)sumGreen,(int)sumBlue),new Color(0,0,0));
                    _imageWriter.writePixel(j, i, sumColor);
                }
                else{
                    sumRed = 0;
                    sumGreen = 0;
                    sumBlue = 0;
				
                    Ray ray = _scene.getCamera().constructRayThroughPixel(
                                    _imageWriter.getNx(), _imageWriter.getNy(), j, i,
                                    _scene.getDistanceViewPlane(), _imageWriter.getWidth(),
                                    _imageWriter.getHeight(),0);
                    Map<Geometry,List<Point3D>> intersectionPoints = getSceneRayIntersections(ray);
                    if (intersectionPoints.isEmpty())
                    {
                        sumRed += _scene.getBackground().getRed();
                        sumGreen += _scene.getBackground().getGreen();
                        sumBlue += _scene.getBackground().getBlue();
                    }
                    else
                    {
                        Map<Geometry,Point3D> closestPoint = getClosestPoint(intersectionPoints);
                        for(Entry<Geometry, Point3D> entry: closestPoint.entrySet())
                        {
                            Color sumColor = calcColor(entry.getKey(),entry.getValue(),ray);
                            sumRed += sumColor.getRed();
                            sumGreen += sumColor.getGreen();
                            sumBlue += sumColor.getBlue();
                        }
                    }
				
                    Color sumColor = addColor(new Color((int)sumRed,(int)sumGreen,(int)sumBlue),new Color(0,0,0));
                    _imageWriter.writePixel(j, i, sumColor);
                }
			}
		}
	}

    /*************************************************
        * FUNCTION
        * printGrid
        * PARAMETERS
        * interval(int) - determens the size of the grid
        * RETURN VALUE
        * MEANING
        * prins a grid on the imege
        * SEE ALSO
    **************************************************/
	public void printGrid(int interval)
	{
        int height = _imageWriter.getNy();
        int width = _imageWriter.getNx();
        for(int i = 0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                if(i%interval == 0||j%interval == 0||j == 499||i == 499)
                {
                    _imageWriter.writePixel(i,j,255,255,255);
                }
            }
        }
	}

    /*************************************************
        * FUNCTION
        * calcColor
        * PARAMETERS
        * Geometry - the given geometry we want to find its color
        * Point3D - the point on the geometry we are calculating
        * Ray - point of view on the geometry
        * RETURN VALUE
        * Color - the result of the calculation
        * MEANING
        * prins a grid on the imege
        * SEE ALSO
    **************************************************/
	private Color calcColor(Geometry geometry,Point3D p ,Ray inRay) throws Exception
	{
        return calcColor(geometry,p ,inRay,0);
	}
    
    /*************************************************
        * FUNCTION
        * calcColor
        * PARAMETERS
        * Geometry - the given geometry we want to find its color
        * Point3D - the point on the geometry we are calculating
        * Ray - point of view on the geometry
        * level - the level of recursion
        * he
        * RETURN VALUE
        * Color - the result of the calculation
        * MEANING
        * prins a grid on the imege
        * SEE ALSO
    **************************************************/
	private Color calcColor(Geometry geometry,Point3D p ,Ray inRay,int level) throws Exception
	{
        try{
            if (level == RECURSION_LEVEL) 
                    return new Color(0, 0, 0);
            Color ambientLight = _scene.getAmbientLight().getIntensity(p);
            Color emissionLight = geometry.getEmission();
            Iterator<Light> lightsIt = _scene.getLightsIterator();
            Color diffuseLight = new Color(0,0,0);
            Color specularLight = new Color(0,0,0);
            while(lightsIt.hasNext())
            {
                Light light = lightsIt.next();

                if (!occluded(light, p, geometry)){
                    Color temp = calcDiffusiveComp(geometry.getMaterial().getKd(),
                                    geometry.getNormal(p),light.getL(p),light.getIntensity(p));

                    diffuseLight = addColor(diffuseLight,temp);

                    Point3D p0 = new Point3D(p);
                    p0.substract(new Vector(_scene.getCamera().getP0()));
                    temp = calcSpecularComp(geometry.getMaterial().getKs(),
                                    new Vector(p0),geometry.getNormal(p),
                                    light.getL(p),geometry.getMaterial().getN(),light.getIntensity(p));

                    specularLight = addColor(specularLight,temp);
                }
            }

            double Kr = geometry.getMaterial().getKr();
            Color reflectedLight = new Color(0,0,0);
            if(Kr != 0)
            {
                Color reflectedColor = new Color(0,0,0);
                Ray reflectedRay = constructReflectedRay(geometry.getNormal(p), p, inRay);
                Map<Geometry,List<Point3D>> intersectionPointsReflect = getSceneRayIntersections(reflectedRay);

                if (geometry instanceof FlatGeometry)
                        intersectionPointsReflect.remove(geometry);
                Map<Geometry,Point3D> closestPoint = getClosestPoint(intersectionPointsReflect);

                for(Entry<Geometry, Point3D> entry: closestPoint.entrySet())
                {
                    if(entry.getValue() != null)
                            reflectedColor = calcColor(entry.getKey(),entry.getValue(),reflectedRay,level+1);
                    reflectedLight = scaleColor(reflectedColor,Kr);
                }
            }


            double Kt = geometry.getMaterial().getKt();
            Color refractedLight = new Color(0,0,0);
            if(Kt != 0)
            {   
                Color refractedColor = new Color(0,0,0);
                Ray refractedRay = constructRefractedRay(geometry.getNormal(p), p, inRay);
                Map<Geometry,List<Point3D>> intersectionPointsRefract = getSceneRayIntersections(refractedRay);

                if (geometry instanceof FlatGeometry)
                        intersectionPointsRefract.remove(geometry);
                Map<Geometry,Point3D> closestPoint1 = getClosestPoint(intersectionPointsRefract);
                for(Entry<Geometry, Point3D> entry: closestPoint1.entrySet())
                {
                    if(entry.getValue()!= null)
                    {
                        refractedColor = calcColor(entry.getKey(),entry.getValue(),refractedRay,level+1);
                        refractedLight = scaleColor(refractedColor,Kt);
                    }
                }
            }

            Color help1 = addColor(addColor(ambientLight,emissionLight),addColor(specularLight,diffuseLight));
            return addColor(addColor(help1,reflectedLight),refractedLight);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return Color.BLACK;
        }
	}

    /*************************************************
        * FUNCTION
        * constructReflectedRay
        * PARAMETERS
        * normal(Vector) - find what reflection the vector 
        * has from the metirial
        * Point3D - the point on the geometry we are calculating
        * Ray - point of view on the geometry
        * RETURN VALUE
        * return the new reflected ray that include the 
        * original point and a new vector = D-2*(D*N)*N
        * MEANING
        * return a reflected ray from income ray
        * SEE ALSO
    **************************************************/
	private Ray constructReflectedRay(Vector normal, Point3D p, Ray inRay) throws Exception
    {
        Vector N = new Vector(normal);
        N.normalize();
        Vector D = new Vector(inRay.getDirection());
        D.normalize();
        double help1 = -2*(D.dotProduct(N));
        N.scale(help1);
        D.add(N);
        D.normalize();
        return new Ray(new Point3D(p),D);
	}

    /*************************************************
        * FUNCTION
        * constructRefractedRay
        * PARAMETERS
        * normal(Vector) - find what refraction the vector 
        * has from the metirial
        * Point3D - the point on the geometry we are calculating
        * Ray - point of view on the geometry
        * RETURN VALUE
        * return the new refracted ray that include the 
        * original point and a new vector = D-2*(D*N)*N
        * MEANING
        * return a refracted ray from income ray
        * SEE ALSO
    **************************************************/
	private Ray constructRefractedRay(Vector normal, Point3D p, Ray inRay) throws Exception{
		Vector D = new Vector(inRay.getDirection());
		D.normalize();
		Vector epsVec = new Vector(normal);
		epsVec.scale(-2);
		epsVec.normalize();
		Point3D p1 = new Point3D(p);
		p1.add(epsVec);
		return new Ray(p1,D);
	}

    /*************************************************
        * FUNCTION
        * occluded
        * PARAMETERS
        * Light - light that we check if geomtry gets light
        * Point3D - the point on geometry thet we want to check
        * Geometry - the geometry which we are checking
        * RETURN VALUE
        * return true if the geometry occluded 
        * from the light in p-a specific point
        * MEANING
        * true if occluded else false
        * SEE ALSO
    **************************************************/
	private boolean occluded(Light light, Point3D point, Geometry geometry) throws Exception 
	{
        Vector lightDirection = light.getL(point);
        lightDirection.normalize();
        lightDirection.scale(-1);
        Point3D geometryPoint = new Point3D(point);
        Vector epsVector = new Vector(geometry.getNormal(point));
        epsVector.normalize();
        epsVector.scale(0.1);
        geometryPoint.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        Map<Geometry, List<Point3D>> intersectionPoints = getSceneRayIntersections(lightRay);
        
        if (geometry instanceof FlatGeometry)
        {
            intersectionPoints.remove(geometry);
        }
        
        for (Entry<Geometry, List<Point3D>> entry: intersectionPoints.entrySet())
            if (entry.getKey().getMaterial().getKt() == 0&&entry.getKey().getMaterial().getKr() == 0)
                return true;
        return false;
	}

    /*************************************************
        * FUNCTION
        * getClosestPoint
        * PARAMETERS
        * intersectionPoints - map of all the intersection points
        * RETURN VALUE
        * map of lists of closest intersection points
        * MEANING
        * this function returns the closestinersection point 
        * to the camera (P0) in the intersectionPoints list
        * SEE ALSO
    **************************************************/
	private Map<Geometry,Point3D> getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints)
	{
        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.getCamera().getP0();
        Map<Geometry,Point3D> minDistancePoint = new HashMap<Geometry,Point3D>(); 

        for (Entry <Geometry,List<Point3D>> entry: intersectionPoints.entrySet())
        {
            for (Point3D point: entry.getValue())
                if (P0.distance(point) < distance)
                {
                    minDistancePoint.clear();
                    minDistancePoint.put(entry.getKey(),new Point3D(point));
                    distance = P0.distance(point);
                }
        }
        return minDistancePoint; 
	}

    /*************************************************
        * FUNCTION
        * getSceneRayIntersections
        * PARAMETERS
        * Ray - a ray which we send towerds the geometry
        * RETURN VALUE
        * map of lists of intersection points
        * MEANING
        * finds all the intersection point
        * between the given ray and the geometry
        * SEE ALSO
    **************************************************/
	private Map<Geometry,List<Point3D>> getSceneRayIntersections(Ray ray) throws Exception
	{
        Iterator<Geometry> geometries = _scene.getGeometriesIterator();
        Map<Geometry,List<Point3D>> intersectionPoints = new HashMap<Geometry,List<Point3D>>();
        List<Point3D> geometryIntersectionPoints = new ArrayList<Point3D>();
        while (geometries.hasNext())
        {
            Geometry geometry = geometries.next();
            geometryIntersectionPoints = geometry.findIntersections(ray);
            if(!geometryIntersectionPoints.isEmpty())
                intersectionPoints.put(geometry,geometryIntersectionPoints);
        }
        return intersectionPoints; 
	}
        
    /*************************************************
        * FUNCTION
        * calcDiffusiveComp
        * PARAMETERS
        * Kd (Double) - diffus const
        * geometryNormal (Vector) - the geometry normal
        * L (Vector) - the vector of the light
        * Intensity (Color) - the intensity of the color
        * RETURN VALUE
        * Color that we calculated
        * MEANING
        * calcuates diffusive compoent in geometry 
        * SEE ALSO
    **************************************************/
	private Color calcDiffusiveComp(Double Kd,Vector geometryNormal,Vector L,Color Intensity) throws Exception
	{
        Vector normal = new Vector(geometryNormal);
        normal.normalize();
        Vector l = new Vector(L);
        l.normalize();
        double temp = Kd*normal.dotProduct(l);
        if(temp<0)
            temp = -1*temp;
        return scaleColor(Intensity,temp);
	}
        
    /*************************************************
        * FUNCTION
        * calcSpecularComp
        * PARAMETERS
        * Ks (Double) - specular const
        * n (double) - shinines const
        * geometryNormal (Vector) - the geometry normal
        * V (Vector) - what goes back from the 
        * metirial to the camera
        * L (Vector) - the vector of the light
        * Intensity (Color) - the intensity of the color
        * RETURN VALUE
        * Color that we calculated
        * MEANING
        * calcuates specular compoent in geometry 
        * SEE ALSO
    **************************************************/
	private Color calcSpecularComp(Double Ks,Vector V,Vector geoNormal,Vector L,double n,Color Intensity) throws Exception
	{
        Vector N = new Vector(geoNormal);
        N.normalize();
        L.normalize();
        V.normalize();
        Vector R = new Vector(L);
        double help1 = -2*(L.dotProduct(N));
        if(help1<0)
                help1 = 0;
        N.scale(help1);
        R.add(N);
        R.normalize();
        double help = V.dotProduct(R);
        if(help<0)
                help = -1*help;
        double temp1 = Ks*Math.pow(help, n);
        return scaleColor(Intensity,temp1);
	}

    /*************************************************
        * FUNCTION
        * addColor
        * PARAMETERS
        * two Colors to add
        * RETURN VALUE
        * the sum of both colors
        * MEANING
        * adds 2 colors to find final color 
        * and if color > 255 make it 255 (white)
        * SEE ALSO
    **************************************************/
	private Color addColor(Color color1,Color color2)
	{
        int red = color1.getRed()+color2.getRed();
        if(red > 255)
            red = 255;
        int green = color1.getGreen()+color2.getGreen();
        if(green > 255)
            green = 255;
        int blue = color1.getBlue()+color2.getBlue();
        if(blue > 255)
            blue = 255;
        return new Color(red,green,blue);
	}

    /*************************************************
        * FUNCTION
        * scaleColor
        * PARAMETERS
        * Color
        * n - scalar
        * RETURN VALUE
        * returns sum of scalar multipacation between color and n
        * MEANING
        * muliplies color by a scalar (n)
        * and if color > 255 make it 255 (white)
        * SEE ALSO
    **************************************************/
	private Color scaleColor(Color color, double n)
	{
        double red = color.getRed()*n;
        if(red>255)
            red = 255;
        double green = color.getGreen()*n;
        if(green>255)
            green = 255;
        double blue = color.getBlue()*n;
        if(blue>255)
            blue = 255;
        return new Color((int)red,(int)green,(int)blue);
	}
}
