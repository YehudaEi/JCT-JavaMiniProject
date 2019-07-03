package elements;

import java.awt.Color;

import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {

    protected Vector direction;
    // ***************** Constructors ********************** //
    public SpotLight(Point3D position, double kc, double kl, double kq, Vector direction,Color color) {
            super(position, kc, kl, kq,color);
            this.direction = direction;
    }

    public SpotLight(SpotLight l) {
            super(l);
            this.direction =l.getDirection();
    }

    // ***************** Getters/Setters ********************** //
    public Vector getDirection() {
            return direction;
    }

    public void setDirection(Vector direction) {
            this.direction = direction;
    }

    // ***************** Administration  ******************** //

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpotLight other = (SpotLight) obj;
        if (direction == null) {
            if (other.direction != null)
                return false;
        } else if (!direction.equals(other.direction))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpotLight: direction=" + direction;
    }

    @Override
    public Color getIntensity(Point3D point) throws Exception {
        double d=point.distance(position);
        double tmp=((kc)+kl*d+kq*(Math.pow(d, 2)));
        Vector l=new Vector(this.getL(point));
        l.normalize();
        direction.normalize();
        double tmp1=direction.dotProduct(l);
        if(tmp==0)
                throw new Exception();
        if(tmp1<0)
                tmp1=-1*tmp1;
        if(tmp<1)
                tmp=1;
        tmp=tmp1/tmp;
        Color c=scaleColor(color,tmp);
        return c;
    }
    private Color scaleColor(Color color, double n)
    {
        double red=color.getRed()*n;
    	if(red>255)
            red=255;
    	double green=color.getGreen()*n;
    	if(green>255)
            green=255;
    	double blue=color.getBlue()*n;
    	if(blue>255)
            blue=255;
    	return new Color((int)red,(int)green,(int)blue);
    }
}
