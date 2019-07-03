package Test;

import Scene.Scene;
import elements.*;
import geometries.*;
import java.awt.Color;
import org.junit.Test;
import primitives.*;
import renderer.*;

public class Level3Test {
    @Test
    public void pointLightTest() throws Exception
    {
        Scene scene = new Scene();
        Sphere sphere = new Sphere (800, new Point3D(0.0,0.0,-1000),new Color(0, 0, 100),new Material(1,1,1));
        sphere.setKd(1);
        sphere.setKr(0.1);
        sphere.setKs(1);
        sphere.setKt(0.1);
        sphere.setShininess(20);
        scene.addGeometry(sphere);
        scene.addLight(new PointLight(new Point3D(-200,-200,-100), 0, 0.00001, 0.000005, new Color(255,100,100)));

        ImageWriter imageWriter = new ImageWriter("Point test", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);
        render.renderImage();
        render.writeToimage();
    }
    @Test
    public void SpotLightTest() throws Exception
    {
        Scene scene = new Scene();
        scene.setDistanceViewPlane(200);
        Material m=new Material(1,1,1);
        m.setN(20);
        Sphere sphere = new Sphere(500, new Point3D(new Coordinate(0.0),
                new Coordinate( 0.0),new Coordinate( -1000)),new Color(0, 0, 100),new Material(1,1,1));
        sphere.setMaterial(m);
        scene.addGeometry(sphere);
        
        Triangle triangle = new Triangle(new Point3D(new Coordinate(-125), new Coordinate(-225),new Coordinate( -260)),
                new Point3D(new Coordinate(-225),new Coordinate( -125), new Coordinate(-260)),
                new Point3D(new Coordinate(-225),new Coordinate( -225),new Coordinate( -270)),
                new Color (0, 0, 100),new Material(1,1,1));
        Material m1=new Material(1,1,1);
        m1.setN(4);
        triangle.setMaterial(m);
        scene.addGeometry(triangle);

        scene.addLight(new SpotLight( new Point3D(new Coordinate(-200),new Coordinate( -200),new Coordinate( -150)),0.1, 0.00001, 0.000005, 
                new Vector(new Point3D(new Coordinate(2),new Coordinate( 2),new Coordinate( -3))),new Color(255, 100, 100)));

        ImageWriter imageWriter = new ImageWriter("Spot test", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);
        render.renderImage();
        render.writeToimage();
    }
    @Test
    public void RecursiveL1Test() throws Exception
    {
        Scene scene = new Scene();
        scene.setDistanceViewPlane(200);
        Sphere c= new Sphere (500, new Point3D(0.0, 0.0, -1000),new Color(0, 0, 100),new Material(1,1,20,0,0.5));
        Sphere  c2= new Sphere (250, new Point3D(0.0, 0.0, -1000),new Color(100, 0, 0),new Material(1,1,20,0,0));
        scene.addGeometry(c);
        scene.addGeometry(c2);		

        scene.addLight(new SpotLight(new Point3D(-200, -200, -150), 0.1, 0.00001, 0.000005,
                        new Vector(new Point3D(2, 2, -3)),new Color(255, 100, 100)));
        ImageWriter imageWriter = new ImageWriter("Recursive level 1", 500, 500, 500, 500);

        Render render = new Render(scene,imageWriter);

        render.renderImage();
        render.writeToimage();
    }
    @Test
    public void RecursiveL2Test() throws Exception
    {
        Scene scene = new Scene();
        scene.setDistanceViewPlane(300);
        Sphere c= new Sphere(300, new Point3D(-550, -500, -1000),new Color(0, 0, 100),new Material(1,1,20,0,0.5));
        scene.addGeometry(c);
        Sphere c2= new Sphere(150, new Point3D(-550, -500, -1000),new Color(100, 20, 20),new Material(1,1,20,0,0));
        scene.addGeometry(c2);

        Triangle triangle = new Triangle(new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D( 200,  200, -375),new Color (20, 20, 20),new Material(1,1,20,1,0));	
        scene.addGeometry(triangle);

        Triangle triangle1 = new Triangle(new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -1500),new Color (20, 20, 20),new Material(1,1,20,0.5,0));
        scene.addGeometry(triangle1);



        scene.addLight(new SpotLight( new Point3D(200, 200, -150),0, 0.00001, 0.000005,
                        new Vector(new Point3D(-2, -2, -3)),new Color(255, 100, 100)));

        ImageWriter imageWriter = new ImageWriter("Recursive level 2", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        render.writeToimage();
    }
}
