package elements;

import primitives.*;

import java.awt.*;

public interface LightSource {
    public abstract Color getIntensity(Point3D point) throws Exception;
    public abstract Vector getL(Point3D point) throws Exception;
}