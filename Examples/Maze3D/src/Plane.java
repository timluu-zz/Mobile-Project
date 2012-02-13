
import javax.microedition.m3g.*;

class Plane {
    // define a square in plane xy

    private final static short POINTS[] = new short[]{
        (short) 1, (short) 1, (short) 0,
        (short) 1, (short) -1, (short) 0,
        (short) -1, (short) 1, (short) 0,
        (short) -1, (short) -1, (short) 0,};
    // define the texture coordinates
    private final static short TEXTCOORDINATES[] = new short[]{
        (short) 1, (short) 1,
        (short) 0, (short) 1,
        (short) 1, (short) 0,
        (short) 0, (short) 0,};
    // triangle strip indices
    private final static int INDICES[] = {
        2, 3, 0,
        1, 0, 3,
        0, 3, 2,
        3, 0, 1
    };
    // strip lengths
    private final static int[] LENGTHS = new int[]{3, 3, 3, 3};
    // these arrays are the same for each plane
    private final static VertexArray POSITIONS_ARRAY, TEXTURE_ARRAY;
    private final static IndexBuffer INDEX_BUFFER;
    private Transform wallTransform = new Transform();
    private float textureRepeat;

    static {
        // initialize the common arrays
        POSITIONS_ARRAY = new VertexArray(POINTS.length / 3, 3, 2);
        POSITIONS_ARRAY.set(0, POINTS.length / 3, POINTS);
        TEXTURE_ARRAY = new VertexArray(TEXTCOORDINATES.length / 2, 2, 2);
        TEXTURE_ARRAY.set(0, TEXTCOORDINATES.length / 2, TEXTCOORDINATES);
        INDEX_BUFFER = new TriangleStripArray(INDICES, LENGTHS);
    }

    // Builds a new plane with a given transform)
    // and the texture repeated n times
    Plane(Transform wallTransform, float textureRepeat) {
        this.wallTransform = wallTransform;
        this.textureRepeat = textureRepeat;
    }
    // Build the mesh

    Mesh createMesh() {
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.setPositions(POSITIONS_ARRAY, 1.0f, null);
        vertexBuffer.setTexCoords(0,
                TEXTURE_ARRAY,
                (float) textureRepeat, null);

        Mesh mesh = new Mesh(vertexBuffer, INDEX_BUFFER, null);
        mesh.setTransform(wallTransform);
        return mesh;
    }
}
