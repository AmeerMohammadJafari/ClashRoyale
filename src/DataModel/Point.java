package DataModel;

public class Point {
    double x;
    double y;
    double z = 0;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public static double distance(Point point1, Point point2){
        return Math.sqrt(Math.pow(Math.abs(point1.x-point2.x),2) + Math.pow(Math.abs(point1.y-point2.y),2)+ Math.pow(Math.abs(point1.getZ() - point2.getZ()),2));
    }

    public static double angleCalculator(Point point1, Point point2, Point point3){
        double distance12 = distance(point1, point2);
        double distance13 = distance(point1, point3);
        double distance23 = distance(point2, point3);
        return Math.toDegrees(Math.acos((Math.pow(distance12, 2) + Math.pow(distance13, 2) - Math.pow(distance23, 2))/(2*distance12*distance13)));

    }
    public double getZ(){ return z; }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
