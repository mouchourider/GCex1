package utils;
import java.lang.Math;


public class Matrixs {
	 public static double[][] CreateScaleMatrix2D(double scaleX, double scaleY) {
	        double[][] scaleMatrix = new double[3][3];
	        scaleMatrix[0][0] = scaleX;
	        scaleMatrix[0][1] = 0;
	        scaleMatrix[0][2] = 0;
	        scaleMatrix[1][0] = 0;
	        scaleMatrix[1][1] = scaleY;
	        scaleMatrix[1][2] = 0;
	        scaleMatrix[2][0] = 0;
	        scaleMatrix[2][1] = 0;
	        scaleMatrix[2][2] = 1;
	        return scaleMatrix;
	 }
	public static double[][] CreateScaleMatrix3D(double scaleX, double scaleY, double scaleZ) {
		double[][] scaleMatrix = new double[4][4];
		scaleMatrix[0][0] = scaleX;
		scaleMatrix[0][1] = 0;
		scaleMatrix[0][2] = 0;
		scaleMatrix[0][3] = 0;

		scaleMatrix[1][0] = 0;
		scaleMatrix[1][1] = scaleY;
		scaleMatrix[1][2] = 0;
		scaleMatrix[1][3] = 0;

		scaleMatrix[2][0] = 0;
		scaleMatrix[2][1] = 0;
		scaleMatrix[2][2] = scaleZ;
		scaleMatrix[2][3] = 0;

		scaleMatrix[3][0] = 0;
		scaleMatrix[3][1] = 0;
		scaleMatrix[3][2] = 0;
		scaleMatrix[3][3] = 1;
		return scaleMatrix;
	}
	 public static double[][] CreateScaleBackMatrix2D(double scaleX, double scaleY) {
	        return CreateScaleMatrix2D(1 / scaleX, 1 / scaleY);
	 }
	 public static double[][] CreateRotateMatrix2D(double rotateAngle) {
	        double[][] rotateMatrix = new double[3][3];
	        rotateMatrix[0][0] = Math.cos(rotateAngle);
	        rotateMatrix[0][1] = Math.sin(rotateAngle);
	        rotateMatrix[0][2] = 0;
	        rotateMatrix[1][0] = -1 * Math.sin(rotateAngle);
	        rotateMatrix[1][1] = Math.cos(rotateAngle);
	        rotateMatrix[1][2] = 0;
	        rotateMatrix[2][0] = 0;
	        rotateMatrix[2][1] = 0;
	        rotateMatrix[2][2] = 1;
	        return rotateMatrix;
	 }
	public static double[][] CreateRotateMatrix3D(double rotateAngle) {
		double[][] rotateMatrix = new double[4][4];
		rotateMatrix[0][0] = Math.cos(rotateAngle);
		rotateMatrix[0][1] = Math.sin(rotateAngle);
		rotateMatrix[0][2] = 0;
		rotateMatrix[0][3] = 0;
		rotateMatrix[1][0] = -1 * Math.sin(rotateAngle);
		rotateMatrix[1][1] = Math.cos(rotateAngle);
		rotateMatrix[1][2] = 0;
		rotateMatrix[1][3] = 0;
		rotateMatrix[2][0] = 0;
		rotateMatrix[2][1] = 0;
		rotateMatrix[2][2] = 1;
		rotateMatrix[2][3] = 0;
		rotateMatrix[3][0] = 0;
		rotateMatrix[3][1] = 0;
		rotateMatrix[3][2] = 0;
		rotateMatrix[3][3] = 1;
		return rotateMatrix;
	}
	 public static double[][] CreateRotateBackMatrix2D(double rotateAngle) {
	        return CreateRotateMatrix2D(-rotateAngle);
	 }
	 public static double[][] CreateTranslateMatrix2D(double translateX, double translateY) {
		double[][] translateMatrix = new double[3][3];
		translateMatrix[0][0] = 1;
		translateMatrix[0][1] = 0;
		translateMatrix[0][2] = translateX;
		translateMatrix[1][0] = 0;
		translateMatrix[1][1] = 1;
		translateMatrix[1][2] = translateY;
		translateMatrix[2][0] = 0;
		translateMatrix[2][1] = 0;
		translateMatrix[2][2] = 1;
		return translateMatrix;
	}
	public static double[][] CreateTranslateMatrix3D(double translateX, double translateY, double translateZ) {
		double[][] translateMatrix = new double[4][4];
		translateMatrix[0][0] = 1;
		translateMatrix[0][1] = 0;
		translateMatrix[0][2] = 0;
		translateMatrix[0][3] = translateX;

		translateMatrix[1][0] = 0;
		translateMatrix[1][1] = 1;
		translateMatrix[1][2] = 0;
		translateMatrix[1][3] = translateY;

		translateMatrix[2][0] = 0;
		translateMatrix[2][1] = 0;
		translateMatrix[2][2] = 1;
		translateMatrix[2][3] = translateZ;

		translateMatrix[3][0] = 0;
		translateMatrix[3][1] = 0;
		translateMatrix[3][2] = 0;
		translateMatrix[3][3] = 1;
		return translateMatrix;
	}
	 public static double[][] CreateTranslateBackMatrix2D(double translateX, double translateY) {
	        return CreateTranslateMatrix2D(-translateX, -translateY);
	 }
	 public static double[][] CreateMatrix2D() {
	        double[][] initMatrix = new double[3][3];
	        initMatrix[0][0] = 1;
	        initMatrix[0][1] = 0;
	        initMatrix[0][2] = 0;
	        initMatrix[1][0] = 0;
	        initMatrix[1][1] = 1;
	        initMatrix[1][2] = 0;
	        initMatrix[2][0] = 0;
	        initMatrix[2][1] = 0;
	        initMatrix[2][2] = 1;
	        return initMatrix;
	 }
	public static double[][] CreateMatrix3D() {
		double[][] initMatrix = new double[4][4];
		initMatrix[0][0] = 1;
		initMatrix[0][1] = 0;
		initMatrix[0][2] = 0;
		initMatrix[0][3] = 0;
		initMatrix[1][0] = 0;
		initMatrix[1][1] = 1;
		initMatrix[1][2] = 0;
		initMatrix[1][3] = 0;
		initMatrix[2][0] = 0;
		initMatrix[2][1] = 0;
		initMatrix[2][2] = 1;
		initMatrix[2][3] = 0;
		initMatrix[3][0] = 0;
		initMatrix[3][1] = 0;
		initMatrix[3][2] = 0;
		initMatrix[3][3] = 1;
		return initMatrix;
	}
	 public static double[][] CreateVertexVector2D(double x, double y) {
	        double[][] vectorVertex = new double[3][1];
	        vectorVertex[0][0] = x;
	        vectorVertex[1][0] = y;
	        vectorVertex[2][0] = 1;
	        return vectorVertex;
	 }
	public static double[][] CreateVertexVector3D(double x, double y, double z, double w) {
		double[][] vectorVertex = new double[4][1];
		vectorVertex[0][0] = x;
		vectorVertex[1][0] = y;
		vectorVertex[2][0] = z;
		vectorVertex[3][0] = w;
		return vectorVertex;
	}
	public static double[][] SubAndDivVectors3D(double[][] vectorA, double[][] vectorB, double w){
		double[][] vectorVertex = new double[4][1];
		double factor = 0;
		int i = 0;
		for(i = 0; i<3; i++){
			vectorVertex[i][0] = vectorA[i][0] - vectorB[i][0];
		}
		factor = AbsValueVector3D(vectorVertex);
		for(i = 0; i<3; i++){
			vectorVertex[i][0] = vectorVertex[i][0]/factor;
		}
		vectorVertex[3][0] = w;
		return vectorVertex;
	}
	public static double[][] SubVectors3D(double[][] vectorA, double[][] vectorB, double w){
		double[][] vectorVertex = new double[4][1];
		double factor = 0;
		int i = 0;
		for(i = 0; i<3; i++){
			vectorVertex[i][0] = vectorA[i][0] - vectorB[i][0];
		}
		vectorVertex[3][0] = w;
		return vectorVertex;
	}
	public static double[][] CrossProdAndDivVectors3D(double[][] vectorA, double[][] vectorB, double w){
		double[][] vectorVertex = new double[4][1];
		double factor = 0;
		int i = 0;
			vectorVertex[0][0] = vectorA[1][0]*vectorB[2][0] - vectorA[2][0]*vectorB[1][0];
		    vectorVertex[1][0] = vectorA[2][0]*vectorB[0][0] - vectorA[0][0]*vectorB[2][0];
			vectorVertex[2][0] = vectorA[0][0]*vectorB[1][0] - vectorA[1][0]*vectorB[0][0];
		factor = AbsValueVector3D(vectorVertex);
		for(i = 0; i<3; i++){
			vectorVertex[i][0] = vectorVertex[i][0]/factor;
		}
		vectorVertex[3][0] = w;
		return vectorVertex;
	}
	public static double[][] CrossProdVectors3D(double[][] vectorA, double[][] vectorB, double w){
		double[][] vectorVertex = new double[4][1];
		double factor = 0;
		int i = 0;
		vectorVertex[0][0] = vectorA[1][0]*vectorB[2][0] - vectorA[2][0]*vectorB[1][0];
		vectorVertex[1][0] = vectorA[2][0]*vectorB[0][0] - vectorA[0][0]*vectorB[2][0];
		vectorVertex[2][0] = vectorA[0][0]*vectorB[1][0] - vectorA[1][0]*vectorB[0][0];
		vectorVertex[3][0] = w;
		return vectorVertex;
	}
	public static double AbsValueVector3D(double[][] vector){
		int i = 0, sum = 0;
		for(i = 0; i<3; i++){
			sum += Math.pow(vector[i][0],2);
		}
		return Math.sqrt(sum);
	}
	public static double[][] BuildMatrixFromVectors(double[][] vec1,double[][] vec2,double[][] vec3,double[][] vec4){
		int size = vec1.length, i = 0, j = 0;
		double[][] matrixRes =  new double[4][4];
		for(i = 0; i < size; i++) {
			matrixRes[i][0] = vec1[i][0];
		}
		for(i = 0; i < size; i++) {
			matrixRes[i][1] = vec2[i][0];
		}
		for(i = 0; i < size; i++) {
			matrixRes[i][2] = vec3[i][0];
		}
		for(i = 0; i < size; i++) {
			matrixRes[i][3] = vec4[i][0];
		}
		return matrixRes;
	}
}
