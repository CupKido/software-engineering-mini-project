package elements;


import primitives.*;
import static primitives.Util.*;

public class Camera {
    Point3D Position;
    Vector To;
    Vector Up;
    Vector Right;
    double VPHeight = 0; //View Plane height
    double VPWidth = 0; //View Plane width
    double PDistance = 0; //Plane Distance
    double MoveSize = 1;
    public Camera(Point3D position, Vector to, Vector up) {
        if(position == null || to == null || up == null)
        {
            throw new IllegalArgumentException("ERROR: Cannot use null value for Camera constructor");
        }
        Position = position;
        To = to.normalized();
        Up = up.normalized();
        if(!Util.isZero(To.dotProduct(Up))){
            throw new IllegalArgumentException("ERROR: angle between vectors is not 90 degrees");
        }
        Right = To.crossProduct(Up).normalized();
    }
    public Vector getRight(){
        return Right;
    }

    public void setMovementSize(double MS){
        if(MS <= 0)
        {
            throw new IllegalArgumentException("ERROR: Movement size must be more than 0.");
        }
        MoveSize = MS;
    }

    public void MoveForward(double times){
        if(times == 0)
        {
            return;
        }
        Position = Position.add(To.scale(MoveSize * times));
    }

    public void MoveBackwards(double times){
        MoveForward(times * (-1));
    }

    public void MoveRight(double times){
        if(times == 0)
        {
            return;
        }
        Position = Position.add(Right.scale(MoveSize * times));
    }

    public void MoveLeft(double times){
        MoveRight(times * (-1));
    }

    public void MoveUp(double times){
        if(times == 0)
        {
            return;
        }
        Position = Position.add(Up.scale(MoveSize * times));
    }

    public void MoveDown(double times){
        MoveUp(times * (-1));
    }

    public void LookRight(double degree){

    }

    public void LookLeft(double degree){

    }

    public void LookUp(double degree){

    }

    public void LookDown(double degree){

    }

    public Vector getTo() {
        return To;
    }

    public Vector getUp() {
        return Up;
    }

    public Camera setViewPlaneSize(double width, double height){
        VPHeight = height;
        VPWidth = width;
        return this;
    }

    public Camera setDistance(double distance){
        if(distance <= 0){
            throw new IllegalArgumentException("ERROR: Distance from Camera to View Plane has to be more than 0!");
        }
        PDistance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D Pc = Position.add(To.scale(PDistance));
        double Ry = VPHeight/nY;
        double Rx = VPWidth/nX;

        double Yi = -(i - (Ry - 1)/2) * Ry;
        double Xj = (j - (Rx - 1) / 2) * Rx;
        Point3D Pij = Pc;

        if(!isZero(Yi)) Pij = Pij.add(Up.scale(Yi));
        if(!isZero(Xj)) Pij = Pij.add(Right.scale(Xj));

        try{
            return new Ray(Position, Pij.subtract(Position));
        }catch (Exception e)
        {
            throw e;
        }



    }

    void RTwist(int deg)
    {
        deg = deg % 360;
        if(deg < 0) deg = 360 - deg;
        if(deg % 90 == 0)
        {
            if(deg == 0)
                return;
            if(deg == 90)
            {
                Vector temp = To.scale(-1);
                To = Right;
                Right = temp;
            }
            else if(deg == 180)
            {
                To = To.scale(-1);
                Right = Right.scale(-1);
            }
            else //deg = 270
            {
                Vector temp = Right.scale(-1);
                Right = To;
                To = temp;
            }
            return;
        }
        To = (To.scale(Math.cos(deg)).crossProduct(Right.scale(Math.sin(deg)))).normalized();
        Right = To.crossProduct(Up).normalized();
    }

    void UTwist(int deg)
    {
        deg = -(deg % 360);
        if(deg < 0) deg = 360 - deg;
        if(deg % 90 == 0)
        {
            if(deg == 0)
                return;
            if(deg == 90)
            {
                Vector temp = Up.scale(-1);
                Up = To;
                To = temp;
            }
            else if(deg == 180)
            {
                Up = Up.scale(-1);
                To = To.scale(-1);
            }
            else //deg = 270
            {
                Vector temp = To.scale(-1);
                To = Up;
                Up = temp;
            }
            return;
        }
        Up = (Up.scale(Math.cos(deg)).crossProduct(To.scale(Math.sin(deg)))).normalized();
        To = Up.crossProduct(Right).normalized();
    }

    void Twist(int deg)
    {
        deg = deg % 360;
        if(deg < 0) deg = 360 - deg;
        if(deg % 90 == 0)
        {
            if(deg == 0)
                return;
            if(deg == 90)
            {
                Vector temp = Up.scale(-1);
                Up = Right;
                Right = temp;
            }
            else if(deg == 180)
            {
                Up = Up.scale(-1);
                Right = Right.scale(-1);
            }
            else //deg = 270
            {
                Vector temp = Right.scale(-1);
                Right = Up;
                Up = temp;
            }
            return;
        }
        To = (To.scale(Math.cos(deg)).crossProduct(Right.scale(Math.sin(deg)))).normalized();
        Right = To.crossProduct(Up).normalized();
    }
}
