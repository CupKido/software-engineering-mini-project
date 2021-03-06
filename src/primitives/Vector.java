package primitives;

import java.util.Objects;

public class Vector {
    private Point3D head;

    public Vector(double X, double Y, double Z) {
        Point3D point3D = new Point3D(X, Y, Z);
        if (point3D.equals(new Point3D(0, 0, 0))) {
            throw new IllegalArgumentException("Cannot create 0 Vector");
        }
        head = point3D;
    }

    public Vector(Coordinate X, Coordinate Y, Coordinate Z) {
        Point3D point3D = new Point3D(X, Y, Z);
        if (point3D.equals(new Point3D(0, 0, 0))) {
            throw new IllegalArgumentException("Cannot create 0 Vector");
        }
        head = point3D;
    }

    public Vector(Point3D point3D) {
        if (point3D.equals(new Point3D(0, 0, 0))) {
            throw new IllegalArgumentException("Cannot create 0 Vector");
        }
        head = point3D;
    }

    public Point3D getHead() {
        return head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return Objects.equals(head, vector.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    public String toString() {
        return head.toString();
    }

    public Vector add(Vector Vec) {
        return new Vector(this.head.add(Vec));
    }

    /**
     * subtracts a vector from this vector.
     * @param Vec
     * @return
     */
    public Vector subtract(Vector Vec) {
        return head.subtract(Vec.head);
    }

    /**
     * return current Vector * given double
     * @param ScaleBy
     * @return
     */
    public Vector scale(double ScaleBy) {
        if (ScaleBy == 0) {
            throw new IllegalArgumentException("Cannot Scale by 0");
        }
        return new Vector(head.x.coord * ScaleBy, head.y.coord * ScaleBy, head.z.coord * ScaleBy);
    }

    /**
     * classic vectors dot product
     * @param Vec
     * @return
     */
    public double dotProduct(Vector Vec) {
        return head.x.coord * Vec.head.x.coord +
                head.y.coord * Vec.head.y.coord +
                head.z.coord * Vec.head.z.coord;
    }

    /**
     * classic vectors cross product
     * @param Vec
     * @return
     */
    public Vector crossProduct(Vector Vec) {
        try {
            return new Vector(
                    (head.y.coord * Vec.head.z.coord) - (head.z.coord * Vec.head.y.coord),
                    (head.z.coord * Vec.head.x.coord) - (head.x.coord * Vec.head.z.coord),
                    (head.x.coord * Vec.head.y.coord) - (head.y.coord * Vec.head.x.coord)
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("ERROR: Invalid Plane - Vectors are dependable");
        }


    }

    public double lengthSquared() {
        return
                head.x.coord * head.x.coord +
                        head.y.coord * head.y.coord +
                        head.z.coord * head.z.coord;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {

        double length = this.length();
        if (length == 0) {
            throw new IllegalArgumentException("Vector Normalize Vector 0");
        }
        head = this.scale(1 / length).head;
        return this;
    }

    public Vector normalized() {
        return (new Vector(head)).normalize();
    }

    public Vector getByDegree(double degree) {
        degree = degree % 360;
        return null;
    }

    public double getDotEquals0X(double y, double z) {
        if (0 == head.x.coord) {
            throw new IllegalArgumentException("X coord in vector is zero");
        }
        return (0 - head.y.coord * y - head.z.coord * z) / head.x.coord;
    }

    public double getDotEquals0Y(double x, double z) {
        if (0 == head.y.coord) {
            throw new IllegalArgumentException("Y coord in vector is zero");
        }
        return (0 - head.x.coord * x - head.z.coord * z) / head.y.coord;
    }

    public double getDotEquals0Z(double x, double y) {
        if (0 == head.z.coord) {
            throw new IllegalArgumentException("Z coord in vector is zero");
        }
        return (0 - head.x.coord * x - head.y.coord * y) / head.z.coord;
    }
}