package Test;

import Scene.Scene;
import elements.*;
import geometries.*;
import java.awt.Color;
import org.junit.Test;
import primitives.*;
import renderer.*;

public class FinalTest {
       
        @Test
        public void Test() throws Exception{
            Scene scene = new Scene();
            scene.setDistanceViewPlane(300);
            scene.setBackground(new Color(183,216,249));
            scene.setAmbientLight(new AmbientLight(0.1,Color.black));
            
            Plane p = new Plane(new Vector(0,5,-1), new Point3D(0,-500,0));
            p.setEmission(new Color(50, 150, 0));
            scene.addGeometry(p);
            
            Quadrangle q1= new Quadrangle(new Point3D(-20,-500,-300), new Point3D(20,-500,-300),
                    new Point3D(-20,0,-300), new Point3D(20,0,-300));
            q1.setEmission(Color.GREEN);
            scene.addGeometry(q1);
            
            Sphere s1 = new Sphere(50, new Point3D(0 , 0, -300));
            s1.setKd(0.1);
            s1.setKs(0.1);
            s1.setEmission(new Color(165, 42, 42));
            scene.addGeometry(s1);
            
            Sphere s2 = new Sphere(50, new Point3D(-490 , 490, -600));
            s2.setKd(0.1);
            s2.setKs(0.1);
            s2.setEmission(Color.YELLOW);
            scene.addGeometry(s2);
            
            Quadrangle q2 = new Quadrangle(new Point3D(-50,-50,-300), new Point3D(-50,50,-300), new Point3D(50,50,-300), new Point3D(50,-50,-300));
            Quadrangle q3 = new Quadrangle(new Point3D(0,70.7,-299), new Point3D(-70.7,0,-299), new Point3D(0,-70.70,-299), new Point3D(70.7,0,-299));
            q2.setEmission(Color.YELLOW);
            q3.setEmission(Color.YELLOW);
            scene.addGeometry(q2);
            scene.addGeometry(q3);
            
            
            
           
            scene.addLight(new SpotLight( new Point3D(-300, 200, -150),0, 0.00001, 0.000005,
                            new Vector(new Point3D(1000, -200, 150)),new Color(255, 100, 100)));
            ImageWriter imageWriter = new ImageWriter("test", 500, 500, 500, 500);

            Render render = new Render(scene,imageWriter);

            render.renderImage();
            imageWriter.writeToimage();
        }
}
