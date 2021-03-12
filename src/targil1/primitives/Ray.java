package targil1.primitives;

public class Ray {


    Point3D p0;
    Vector dir;

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Ray(Point3D point3D, Vector Vec){
        p0 = point3D;
        dir = Vec.normalized();
    }

    @Override
    public String toString() {
        return p0.toString() + " x" + dir.toString();
    }
}