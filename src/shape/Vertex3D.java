package shape;

public class Vertex3D {
    private double x;
    private double y;
    private double z;
    /**
     * constructor.
     *
     * @param x is x
     * @param y is y
     */
    public Vertex3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public boolean equals(Vertex3D other) {
        if (other.getClass() == getClass()) {
            Vertex3D v = (Vertex3D) other;
            return ((getX() == v.getX()) && (getY() == v.getY()) && (getZ() == v.getZ()));
        }
        return false;
    }
    /**
     * return the exist x.
     * @return x
     */
    public double getX() {
        return this.x;
    }
    /**
     * change the value of x.
     * @param newX of the point.
     */
    public void setX(double newX) {
        this.x = newX;
    }
    /**
     * change the value of y.
     * @param newY of the point.
     */
    public void setY(double newY) {
        this.y = newY;
    }
    /**
     * return the value of y.
     * @return y
     */
    public void setZ(double newZ) {
        this.y = newZ;
    }
    public double getY() {
        return this.y;
    }
    public double getZ() {
        return this.z;
    }
}
