package shape;

public class Edge3D {
    private Vertex3D v1;
    private Vertex3D v2;
    /**
     * constructor.
     *
     * @param v1 is one vertex
     * @param v2 is second vertex
     */
    public Edge3D(Vertex3D v1, Vertex3D v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    public boolean equals(Edge3D other) {
        if (other.getClass() == getClass()) {
            Edge3D e = (Edge3D) other;
            return ((getV1() == e.getV1()) && (getV2() == e.getV2()));
        }
        return false;
    }
    /**
     * return the exist v1.
     * @return v1
     */
    public Vertex3D getV1() {
        return this.v1;
    }
    /**
     * change the value of v1.
     * @param newV1 of the vertex.
     */
    public void setV1(Vertex3D newV1) {
        this.v1 = newV1;
    }
    /**
     * change the value of v2.
     * @param newV2 of the vertex.
     */
    public void setV2(Vertex3D newV2) {
        this.v2 = newV2;
    }
    /**
     * return the value of second vertex.
     * @return v2
     */
    public Vertex3D getV2() {
        return this.v2;
    }
   // public Polygon createEdge() {
   // 	
   // }
}
