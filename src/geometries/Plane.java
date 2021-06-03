package geometries;

import primitives.*;
import primitives.Util;
import java.util.LinkedList;
import java.util.List;

public class Plane extends Geometry {


    private Point3D q0;
    private Vector normal;

    //creates the normal vector and inserts one of the points in q0
    public Plane(Point3D p1, Point3D p2, Point3D p3)
    {
        q0 = p1;
        normal = null;


        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        try
        {
            normal = v1.crossProduct(v2);
        }catch(IllegalArgumentException ex)
        {
            throw ex;
        }

        normal.normalize();


    }

    public Plane(Point3D p, Vector v)
    {
        q0 = p;
        normal = v.normalized();
    }

    public Point3D getQ0() {
        return q0;
    }

    //checks if point is in-plane, then returns the normal vector
    public Vector getNormal(Point3D point)
    {

        return normal;

    }

    public boolean inPlane(Point3D point){
        if(point == null)
        {
            return false;
        }
        if (point.equals(q0)){
            return true;
        }
        Vector inPlane = q0.subtract(point);
        if(Util.isZero(inPlane.dotProduct(normal)))
        {
            return true;
        }
        return false;
    }

    //returns normal
    public Vector getNormal()
    {
        return getNormal(null);
    }

    @Override
    public LinkedList<GeoPoint> findGeoIntersections(Ray ray) {
        LinkedList<GeoPoint> L = new LinkedList<GeoPoint>();

        if(Util.isZero(normal.dotProduct(ray.getDir()))){
            return null;
        }
        try{
            double t = q0.subtract(ray.getP0()).dotProduct(normal);
            t = t / normal.dotProduct(ray.getDir());
            if(Util.alignZero(t) <= 0){
                return null;
            }
            L.add(new GeoPoint(this, ray.getPoint(t)));
        }catch (Exception e){
            return null;
        }
        return L;
    }
}
