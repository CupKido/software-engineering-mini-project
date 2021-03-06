package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class Cube extends Geometry{

    Square up, down, left, right, forward, backward;

    Point3D Min;
    Point3D Max;

    public Cube(Point3D center, double width, double depth, double height, Vector up, Vector right)
    {
        if(height <= 0 || width <= 0 || depth <= 0)
            throw new IllegalArgumentException("ERROR: Size must by bigger then zero!");
        if(up.dotProduct(right) != 0)
            throw new IllegalArgumentException("ERROR: Up and right are not vertical");

        up = up.normalized().scale(height/2);
        Vector down = up.scale(-1);
        right = right.normalized().scale(width/2);
        Vector left = right.scale(-1);
        Vector back = up.crossProduct(right).normalized().scale(depth/2);
        Vector front = back.scale(-1);

        build(center.add(up).add(right).add(front), center.add(up).add(right).add(back), center.add(up).add(left).add(front), center.add(up).add(left).add(back), center.add(down).add(right).add(front), center.add(down).add(right).add(back), center.add(down).add(left).add(front), center.add(down).add(left).add(back));
    }


    public Cube(Point3D min, Point3D max){
        up = new Square(
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(),min.getY().getCoord(),max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord())
        );
        down = new Square(
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), min.getY().getCoord(), min.getZ().getCoord()),
                new Point3D(min.getX().getCoord(),min.getY().getCoord(),min.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord())
        );
        forward = new Square(
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(),max.getY().getCoord(),min.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord())
        );
        backward = new Square(
                new Point3D(max.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), min.getY().getCoord(), min.getZ().getCoord()),
                new Point3D(min.getX().getCoord(),min.getY().getCoord(),min.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord())
        );
        left = new Square(
                new Point3D(min.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(min.getX().getCoord(),min.getY().getCoord(),min.getZ().getCoord()),
                new Point3D(min.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord())
        );
        right = new Square(
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
                new Point3D(max.getX().getCoord(),min.getY().getCoord(),min.getZ().getCoord()),
                new Point3D(max.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord())
        );

//        build(max,
//              new Point3D(max.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
//              new Point3D(min.getX().getCoord(), max.getY().getCoord(), max.getZ().getCoord()),
//              new Point3D(min.getX().getCoord(), min.getY().getCoord(), max.getZ().getCoord()),
//
//              new Point3D(max.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord()),
//              new Point3D(max.getX().getCoord(), min.getY().getCoord(), min.getZ().getCoord()),
//              new Point3D(min.getX().getCoord(), max.getY().getCoord(), min.getZ().getCoord()),
//              min
//        );

        Min = min;
        Max = max;

    }
    @Override
    public Vector getNormal(Point3D point) {
        if(up.inoroutNoExtra(point))
            return up.getNormal();
        if(right.inoroutNoExtra(point))
            return right.getNormal();
        if(left.inoroutNoExtra(point))
            return left.getNormal();
        if(down.inoroutNoExtra(point))
            return down.getNormal();
        if(forward.inoroutNoExtra(point))
            return forward.getNormal();
        if(backward.inoroutNoExtra(point))
            return backward.getNormal();
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {



        List<GeoPoint> L = new LinkedList<GeoPoint>();
        List<GeoPoint> temp = new LinkedList<GeoPoint>();
        temp = up.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        temp = down.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        temp = left.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        temp = right.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        temp = forward.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        temp = backward.findGeoIntersections(ray);
        if (temp != null)
        {
            L.addAll(temp);
        }
        for (GeoPoint P:
             L) {
            P.geometry = this;
        }
        if(L.isEmpty()){
            return null;
        }

        return L;
    }

    @Override
    public BoundingBox CreateBox()
    {
        if(Box != null){
            return Box;
        }
        Box = new BoundingBox(Min, Max);
        return Box;
    }

//    public Point3D getMin(){
//
//    }
//
//    public Point3D getMax(){
//
//    }


    void build(Point3D URF, Point3D URB, Point3D ULF, Point3D ULB, Point3D DRF, Point3D DRB, Point3D DLF, Point3D DLB){
        up = new Square(ULB, ULF,  URF, URB);
        down = new Square(DLB, DRB, DRF, DLF);
        left = new Square(ULF, ULB, DLB, DLF);
        right = new Square(URF, URB, DRB, DRF);
        forward = new Square(URF, DRF, DLF, ULF);
        backward = new Square(URB, ULB, DLB, DRB);
    }
}
