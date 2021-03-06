package maindraw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import utils.Matrixs;
import utils.Mathematics;
import shape.Vertex;
import shape.Edge;
public class MyCanvas extends Canvas implements MouseListener,  MouseMotionListener{

	private static final long serialVersionUID = 1L;
	static String dir = System.getProperty("user.dir");
	public static final String filename = dir+"\\src\\"+"example.scn"; //name of file to read data from.
	public static final String filenameSettings = dir+"\\src\\"+"example.viw"; //name of file to read data from.
	private String locationTranspormation;
	private Point pStart, pEnd;
	private double centerX , centerY, radiusPStart, radiusPEnd, scaleParameter;
	private double vertexX,vertexY;
	private double coordinateXCenterWindows, coordinateYCenterWindows;
	private double direction;
	private double ww , wh , vw , vh;
	private double sepertation = 83;
	private double[] vectorStart, vectorEnd;
	private double[][] TrM, viewMatrix, CT, TT;
	private double[][] vectorVertex, matrixTr1 , matrixRo, matrixTr2, matrixTr3, matrixSc1, matrixSc2;
	public MyCanvas() {
		File settings = new File(filenameSettings);
		Scanner setScan;
		try {
			setScan = new Scanner(settings);
			setScan.next();
			coordinateXCenterWindows = setScan.nextDouble();
			//System.out.println(coordinateXCenterWindows);
			coordinateYCenterWindows = setScan.nextDouble();
			//System.out.println(coordinateYCenterWindows);
			setScan.next();
			direction = setScan.nextDouble();
			//System.out.println(direction);
			setScan.next();
			ww = setScan.nextDouble();
			//System.out.println(ww);
			wh = setScan.nextDouble();
			//System.out.println(wh);
			setScan.next();
			vw = setScan.nextDouble();
			//System.out.println(vw);
			vh = setScan.nextDouble();
			//System.out.println(vh);
			sepertation = vh/3;
			setScan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for viewer matrix
		matrixTr1 = Matrixs.CreateTranslateMatrix2D(-coordinateXCenterWindows, -coordinateYCenterWindows);
		matrixRo = Matrixs.CreateRotateMatrix2D(-1 * Math.toRadians(direction));
		matrixSc1 = Matrixs.CreateScaleMatrix2D(vw / ww, vh / wh);
		matrixTr2 = Matrixs.CreateTranslateMatrix2D(20, 20);
	    matrixSc2 = Matrixs.CreateScaleMatrix2D(1, -1);
		matrixTr3 = Matrixs.CreateTranslateMatrix2D(0, vh + 40);
		TT = Matrixs.CreateMatrix2D();
		CT = Matrixs.CreateMatrix2D();
		vectorVertex = new double [3][1];
		setSize((int)vw + 40, (int)vh + 40);
		centerX = (vw / 2) + 20;
		centerY = (vh / 2) + 20;
		vectorStart = new double[2];
		vectorEnd = new double[2];
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void paint(Graphics g) {
		viewMatrix = Mathematics.multiplicateMatrix(matrixTr3, matrixSc2);	
		viewMatrix = Mathematics.multiplicateMatrix(viewMatrix, matrixTr2);
		viewMatrix = Mathematics.multiplicateMatrix(viewMatrix, matrixSc1);
		viewMatrix = Mathematics.multiplicateMatrix(viewMatrix, matrixRo);
		viewMatrix = Mathematics.multiplicateMatrix(viewMatrix, matrixTr1);
		TrM = Mathematics.multiplicateMatrix(CT, TT);
		TrM = Mathematics.multiplicateMatrix(TrM, viewMatrix);
		//read from file
		File fileName1 = new File(filename);
		try {
			Scanner scan = new Scanner(fileName1);
			int sizeVertex = scan.nextInt();
			System.out.println(sizeVertex);
			Vertex[] vertexs = new Vertex[sizeVertex];
			for (int i = 0; i < sizeVertex; i++) { 
				vertexX = scan.nextDouble();
				System.out.println(vertexX);
				vertexY = scan.nextDouble();
				System.out.println(vertexY);
			    vectorVertex = Matrixs.CreateVertexVector2D(vertexX, vertexY);
			    vectorVertex = Mathematics.multiplicateMatrix(TrM, vectorVertex);
			    vertexX = vectorVertex[0][0] + 20;
			    vertexY = vectorVertex[1][0] + 20;
				vertexs[i] = new Vertex(vertexX,vertexY);
			}
			int sizeEdge = scan.nextInt();
			System.out.println(sizeEdge);
			g.setColor(Color.MAGENTA);
			g.drawRect(20, 20, (int)vw, (int)vh);
			Polygon p = new Polygon();
			Edge[] edges = new Edge[sizeEdge];
			for (int i = 0; i < sizeEdge; i++) {
				edges[i] = new Edge(vertexs[scan.nextInt()],vertexs[scan.nextInt()]);
				p.addPoint((int) edges[i].getV1().getX(), (int) edges[i].getV1().getY());
				p.addPoint((int) edges[i].getV2().getX(), (int) edges[i].getV2().getY());	
				g.setColor(Color.BLUE);
				g.drawPolygon(p);
				p.reset();
			}
			scan.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String getTypeTranspormaton(Point point) {
		//the limits of screen is 20 - 270
		String type = "";
		System.out.print(pStart);
		if((pStart.getX() >= 20) && (pStart.getX() <= (int)vh)
				&& (pStart.getY() >= 20) && (pStart.getY() <= (int)vh)) { 
			if(pStart.getX() <= 20 + sepertation) {
				if(pStart.getY() <= 20 + sepertation) {
					type = "RLU";
				} else if(pStart.getY() <= 20 + (sepertation * 2)) {
					type = "SL";
				} else {
					type = "RLD";
				}	
			} else if(pStart.getX() <= 20 + (sepertation * 2)) {
				if(pStart.getY() <= 20 + sepertation) {
					type = "SU";
				} else if(pStart.getY() <= 20 + (sepertation * 2)) {
					type = "T";
				} else {
					type = "SD";
				}
			} else {
				if(pStart.getY() <= 20 + sepertation) {
					type = "RRU";
				} else if(pStart.getY() <= 20 + (sepertation * 2)) {
					type = "SR";
				} else {
					type = "RRD";
				}
			}
		}
		return type;
	}
	public double getAngleFromVectorToXAxis(double[] vector) {
		double angle;
		float RAD2DEG = 180.0f / 3.14159f;
		// atan2 receives first Y second X
		angle = Math.atan2(vector[1], vector[0]) * RAD2DEG;
		if (angle < 0) angle += 360.0f;
		return angle;
	}
	public void executeScale() {
		 radiusPStart = Mathematics.distance(pStart, centerX, centerY);
		 radiusPEnd = Mathematics.distance(pEnd, centerX, centerY);
		 scaleParameter = radiusPEnd / radiusPStart;
		 CT = Matrixs.CreateScaleMatrix2D(scaleParameter, scaleParameter);
		 CT = Mathematics.multiplicateMatrix
				 (Mathematics.multiplicateMatrix
						 (Matrixs.CreateTranslateMatrix2D(centerX, centerY), CT)
						 , Matrixs.CreateTranslateMatrix2D(-centerX, -centerY));
	}
	public void executeRotate() {
		//vector start = (x of start point - x of center point,y of start point - y of center point)
		 vectorStart[0] = pStart.getX() - centerX;
		 vectorStart[1] = pStart.getY() - centerY;
		//vector start = (x of end point - x of center point,y of end point - y of center point)
		 vectorEnd[0] = pEnd.getX() - centerX;
		 vectorEnd[1] = pEnd.getY() - centerY;
		 
		 double angleStart = getAngleFromVectorToXAxis(vectorStart);
		 double angleEnd = getAngleFromVectorToXAxis(vectorEnd);
		 double angleFinish = angleStart - angleEnd;
		 
		 CT = Matrixs.CreateRotateMatrix2D(Math.toRadians(angleFinish));
		 CT = Mathematics.multiplicateMatrix
				 (Mathematics.multiplicateMatrix
						 (Matrixs.CreateTranslateMatrix2D(centerX, centerY), CT)
						 , Matrixs.CreateTranslateMatrix2D(-centerX, -centerY));
	}
	public void executeAction(String type) {
		//the matrix separate in this direction:
		// ROTATE(RLU) SCALE(SU) ROTATE(RRU)
		// SCALE(SL) TRANSLATE(T) SCALE (SR)
		// ROTATE(RLD) SCALE(SD) ROTATE(RRD)
		switch(type) {
		 case "T":  CT = Matrixs.CreateTranslateMatrix2D(pEnd.getX() - pStart.getX(),
					pEnd.getY() - pStart.getY());
         break;
		 case "SD": executeScale();
		 break;
		 case "SU": executeScale();
	     break;
		 case "SL": executeScale();
	     break;
		 case "SR": executeScale();
		 break;
		 case "RLU": executeRotate();
		 break;
		 case "RLD": executeRotate();
		 break;
		 case "RRU": executeRotate();
		 break;
		 case "RRD": executeRotate();
		 break;
		 default: 
         break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		pStart = arg0.getPoint();
		locationTranspormation =  getTypeTranspormaton(pStart);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		pEnd = arg0.getPoint();
		executeAction(locationTranspormation);
		TT = Mathematics.multiplicateMatrix(CT, TT);
		CT = Matrixs.CreateMatrix2D();
		this.repaint();
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		pEnd = arg0.getPoint();
		if((pStart.getX() >= 20) && (pStart.getX() <= (int)vh)
				&& (pStart.getY() >= 20) && (pStart.getY() <= (int)vh))
			executeAction(locationTranspormation);
		this.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
