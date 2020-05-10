//Franck Schwartz 329863237
//Raphael Abenmoha 337689103
package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import utils.Matrixs;
import utils.Mathematics;
import shape.Vertex3D;
import shape.Edge3D;
public class MyCanvas extends Canvas implements MouseListener,  MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private static boolean debugging = true, clipping = true;

	static String dir = System.getProperty("user.dir");
	public static final String filename = dir+"\\src\\"+"example3d.scn"; //name of file to read data from.
	public static final String filenameSettings = dir+"\\src\\"+"example3d.viw"; //name of file to read data from.
	private String locationTranspormation;
	private Point pStart, pEnd;
	private int rotAxis;
	private double centerX , centerY, radiusPStart, radiusPEnd, scaleParameter;
	private double vertexX;
	private double vertexY;
	private double vertexZ;
	private double coorXCamera, coorYCamera, coorZCamera;
	private double coorXLookAt, coorYLookAt, coorZLookAt;
	private double coorXUpCam, coorYUpCam, coorZUpCam;
	private double wl, wr, wb, wt, vw , vh;
	private double sepertation = 83;
	private double[] vectorStart, vectorEnd;
	private double[][] TrM, viewMatrix, CT, TT;
	private double[][] vectorVertex, mMatrix, mTag, matrixTr1 , matrixRo, matrixTr2, matrixTr3, matrixSc1, matrixSc2, pVector, lVector, upVector, wVector, zVector, zVectorN , yVector, xVector;
	public MyCanvas() {
		File settings = new File(filenameSettings);
		Scanner setScan;
		try {
			setScan = new Scanner(settings);
			setScan.next();
			coorXCamera = setScan.nextDouble();
			coorYCamera = setScan.nextDouble();
			coorZCamera = setScan.nextDouble();
			setScan.next();
			pVector = Matrixs.CreateVertexVector3D(coorXCamera, coorYCamera, coorZCamera, 1);
			coorXLookAt = setScan.nextDouble();
			coorYLookAt = setScan.nextDouble();
			coorZLookAt = setScan.nextDouble();
			setScan.next();
			lVector = Matrixs.CreateVertexVector3D(coorXLookAt, coorYLookAt, coorZLookAt, 1);
			coorXUpCam = setScan.nextDouble();
			coorYUpCam = setScan.nextDouble();
			coorZUpCam = setScan.nextDouble();
			setScan.next();
			upVector = Matrixs.CreateVertexVector3D(coorXUpCam, coorYUpCam, coorZUpCam, 0);
			zVector = Matrixs.SubAndDivVectors3D(pVector,lVector, 0);
			zVectorN = Matrixs.SubVectors3D(pVector,lVector, 0);
			xVector = Matrixs.CrossProdAndDivVectors3D(upVector,zVector, 0);
			yVector = Matrixs.CrossProdVectors3D(zVector,xVector, 0);
			wVector = Matrixs.CreateVertexVector3D(0,0,0,1);
			mMatrix = Matrixs.BuildMatrixFromVectors(xVector,yVector,zVector,wVector);
			mTag = Matrixs.BuildMatrixFromVectors(Matrixs.CreateVertexVector3D(1,0,0,-pVector[0][0]),
												  Matrixs.CreateVertexVector3D(0,1,0,-pVector[1][0]),
												  Matrixs.CreateVertexVector3D(0,0,1,-pVector[2][0]),
												  Matrixs.CreateVertexVector3D(0,0,0,1));
			mMatrix = Mathematics.multiplicateMatrix(mMatrix,mTag);
			wl = setScan.nextDouble();
			wr = setScan.nextDouble();
			wb = setScan.nextDouble();
			wt = setScan.nextDouble();
			setScan.next();
			vw = setScan.nextDouble();
			vh = setScan.nextDouble();
			sepertation = vh/3;
			setScan.close();
			rotAxis = 3;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		matrixSc1 = Matrixs.CreateScaleMatrix3D(vw / (wr-wl), vh / (wt-wb),vh / (wt-wb));
		matrixTr2 = Matrixs.CreateTranslateMatrix3D((vw/2)-220, (vh/2)-220, 0);
		matrixTr3 = Matrixs.CreateTranslateMatrix3D(wl+(wr-wl)/2, wb+(wt-wb)/2,0);
		TT = Matrixs.CreateMatrix3D();
		CT = Matrixs.CreateMatrix3D();
		vectorVertex = new double [4][1];
		setSize((int)vw + 40, (int)vh + 40);
		centerX = (vw / 2) + 20;
		centerY = (vh / 2) + 20;
		vectorStart = new double[2];
		vectorEnd = new double[2];
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	public void paint(Graphics g) {
		viewMatrix = Mathematics.multiplicateMatrix(matrixTr2 ,matrixSc1);
		viewMatrix = Mathematics.multiplicateMatrix(viewMatrix, matrixTr3);
		TrM = Mathematics.multiplicateMatrix(CT, TT);
		TrM = Mathematics.multiplicateMatrix(mMatrix, TrM);
		TrM = Mathematics.multiplicateMatrix(TrM, viewMatrix);
		//read from file
		File fileName1 = new File(filename);
		try {
			Scanner scan = new Scanner(fileName1);
			int sizeVertex = scan.nextInt();
			System.out.println(sizeVertex);
			Vertex3D[] vertexs = new Vertex3D[sizeVertex];
			for (int i = 0; i < sizeVertex; i++) {
				vertexX = scan.nextDouble();
				System.out.println(vertexX);
				vertexY = scan.nextDouble();
				System.out.println(vertexY);
				vertexZ = scan.nextDouble();
				System.out.println(vertexZ);
			    vectorVertex = Matrixs.CreateVertexVector3D(vertexX, vertexY, vertexZ, 1);

				vectorVertex = Mathematics.multiplicateMatrix(TrM, vectorVertex);
			    vertexX = vectorVertex[0][0] + 20;
			    vertexY = vectorVertex[1][0] + 20;
				vertexZ = vectorVertex[2][0];
				vertexs[i] = new Vertex3D(vertexX,vertexY,vertexZ);
			}
			int sizeEdge = scan.nextInt();
			System.out.println(sizeEdge);
			g.setColor(Color.BLACK);
			g.drawRect(20, 20, (int)vw-20, (int)vh-20);
			g.setColor(Color.BLACK);
			if (debugging){
				g.drawLine(20, (int)(vw/3), (int)(vw), (int)(vw/3));
				g.drawLine(20, (int)(vw/3)*2, (int)(vw), (int)(vw/3)*2);
				g.drawLine((int)(vw/3),20 , (int)(vw/3), (int)(vw));
				g.drawLine((int)(vw/3)*2, 20, (int)(vw/3)*2, (int)(vw));
			}
			//Polygon p = new Polygon();
			Edge3D[] edges = new Edge3D[sizeEdge];
			for (int i = 0; i < sizeEdge; i++) {
				edges[i] = new Edge3D(vertexs[scan.nextInt()],vertexs[scan.nextInt()]);


				g.setColor(Color.GREEN);
				if(clipping) {
					if(lineIn(edges[i])) {
						edges[i] = clipping(edges[i]);
						g.drawLine((int) edges[i].getV1().getX(), (int) edges[i].getV1().getY(), (int) edges[i].getV2().getX(), (int) edges[i].getV2().getY());
					}
				}
				else {
					g.drawLine((int) edges[i].getV1().getX(), (int) edges[i].getV1().getY(), (int) edges[i].getV2().getX(), (int) edges[i].getV2().getY());
				}
				//p.addPoint((int) edges[i].getV1().getX(), (int) edges[i].getV1().getY());
				//p.addPoint((int) edges[i].getV2().getX(), (int) edges[i].getV2().getY());
				//g.drawPolygon(p);
				//p.reset();
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
		 CT = Matrixs.CreateScaleMatrix3D(scaleParameter, scaleParameter, scaleParameter);
		 CT = Mathematics.multiplicateMatrix(Mathematics.multiplicateMatrix
						 (Matrixs.CreateTranslateMatrix3D(centerX, centerY, zVectorN[2][0]), CT)
				 , Matrixs.CreateTranslateMatrix3D(-centerX, -centerY, -zVectorN[2][0]));
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
		 CT = Matrixs.CreateRotateMatrix3D(Math.toRadians(angleFinish), rotAxis);
		CT = Mathematics.multiplicateMatrix
				(Mathematics.multiplicateMatrix
								(Matrixs.CreateTranslateMatrix3D(centerX, centerY, zVectorN[2][0]), CT)
						, Matrixs.CreateTranslateMatrix3D(-centerX, -centerY, -zVectorN[2][0]));
	}
	public void executeAction(String type) {
		//the matrix separate in this direction:
		// ROTATE(RLU) SCALE(SU) ROTATE(RRU)
		// SCALE(SL) TRANSLATE(T) SCALE (SR)
		// ROTATE(RLD) SCALE(SD) ROTATE(RRD)
		switch(type) {
		 case "T":  CT = Matrixs.CreateTranslateMatrix3D(pEnd.getX() - pStart.getX(),
					pEnd.getY() - pStart.getY(),0);
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
	public Edge3D clipping(Edge3D toClip){
		Vertex3D v1 = toClip.getV1();
		Vertex3D v2 = toClip.getV2();
		Point p1,p2;
		boolean upInt, leftInt, downInt, rightInt;
		Line2D.Double l1 = new Line2D.Double( v1.getX(), v1.getY(), v2.getX(), v2.getY());
		Line2D.Double up = new Line2D.Double( 20, 20, vw-20, 20 );
		Line2D.Double left = new Line2D.Double( 20, 20, 20, vh-20 );
		Line2D.Double down = new Line2D.Double( 20, vh-20, vw-20, vh-20 );
		Line2D.Double right = new Line2D.Double( vw-20, 20, vw-20, vh-20  );
		upInt = l1.intersectsLine(up);
		leftInt = l1.intersectsLine(left);
		downInt = l1.intersectsLine(down);
		rightInt = l1.intersectsLine(right);
		if(!(upInt||leftInt||downInt||rightInt)){
			return toClip;
		}
		if(upInt||(upInt && leftInt)||(upInt && rightInt)||(upInt && downInt)){
			p1 = Mathematics.get_line_intersection(l1,up);
			if(leftInt){
				p2 = Mathematics.get_line_intersection(l1,left);
				if(v1.getY() < 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v1.set(p2.x,p2.y);
					v2.set(p1.x,p1.y);
				}
			}
			else if(rightInt){
				p2 = Mathematics.get_line_intersection(l1,right);
				if(v1.getY() < 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v1.set(p2.x,p2.y);
					v2.set(p1.x,p1.y);
				}
			}
			else if(downInt){
				p2 = Mathematics.get_line_intersection(l1,down);
				if(v1.getY() <= 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v1.set(p2.x,p2.y);
					v2.set(p1.x,p1.y);
				}
			}
			else{
				if(v1.getY() < 20){
					v1.set(p1.x,p1.y);
				}
				else{
					v2.set(p1.x,p1.y);
				}
			}
		}
		else if(rightInt||(rightInt && downInt)|| (rightInt && leftInt)){
			p1 = Mathematics.get_line_intersection(l1,right);
			if(downInt){
				p2 = Mathematics.get_line_intersection(l1,down);
				if(v1.getX() >= vw - 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v1.set(p2.x,p2.y);
					v2.set(p1.x,p1.y);
				}
			}
			else if(leftInt){
				p2 = Mathematics.get_line_intersection(l1,left);
				if(v1.getX() >= vw - 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v1.set(p2.x,p2.y);
					v2.set(p1.x,p1.y);
				}
			}
			else{
				if(v1.getX() >= vw - 20){
					v1.set(p1.x,p1.y);
				}
				else{
					v2.set(p1.x,p1.y);
				}
			}
		}
		else if(leftInt || (leftInt && downInt)){
			p1 = Mathematics.get_line_intersection(l1,left);
			if(downInt){
				p2 = Mathematics.get_line_intersection(l1,down);
				if(v1.getY() <= 20){
					v1.set(p1.x,p1.y);
					v2.set(p2.x,p2.y);
				}
				else{
					v2.set(p1.x,p1.y);
					v1.set(p2.x,p2.y);
				}
			}
			else{
				if(v1.getX() <= 20){
					v1.set(p1.x,p1.y);
				}
				else{
					v2.set(p1.x,p1.y);
				}
			}

		}
		else if(downInt){
			p1 = Mathematics.get_line_intersection(l1,down);
			if(v1.getY() > vh - 20){
				v1.set(p1.x,p1.y);
			}
			else{
				v2.set(p1.x,p1.y);
			}

		}

		toClip.setV1(v1);
		toClip.setV2(v2);
		return toClip;
	}
	public boolean lineIn(Edge3D edge){
		Vertex3D v1 = edge.getV1();
		Vertex3D v2 = edge.getV2();
		if(!(v1.getX() > 20 && v1.getX() < vw - 20 && v1.getY() > 20 && v1.getY() < vh - 20))
			if(!(v2.getX() > 20 && v2.getX() < vw - 20 && v2.getY() > 20 && v2.getY() < vh - 20)){
				return false;
			}
		return true;
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
		CT = Matrixs.CreateMatrix3D();
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

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}
	public void reload(){
		File settings = new File(filenameSettings);
		Scanner setScan;
		try {
			setScan = new Scanner(settings);
			setScan.next();
			coorXCamera = setScan.nextDouble();
			coorYCamera = setScan.nextDouble();
			coorZCamera = setScan.nextDouble();
			setScan.next();
			pVector = Matrixs.CreateVertexVector3D(coorXCamera, coorYCamera, coorZCamera, 1);
			coorXLookAt = setScan.nextDouble();
			coorYLookAt = setScan.nextDouble();
			coorZLookAt = setScan.nextDouble();
			setScan.next();
			lVector = Matrixs.CreateVertexVector3D(coorXLookAt, coorYLookAt, coorZLookAt, 1);
			coorXUpCam = setScan.nextDouble();
			coorYUpCam = setScan.nextDouble();
			coorZUpCam = setScan.nextDouble();
			setScan.next();
			upVector = Matrixs.CreateVertexVector3D(coorXUpCam, coorYUpCam, coorZUpCam, 0);
			zVector = Matrixs.SubAndDivVectors3D(pVector,lVector, 0);
			zVectorN = Matrixs.SubVectors3D(pVector,lVector, 0);
			xVector = Matrixs.CrossProdAndDivVectors3D(upVector,zVector, 0);
			yVector = Matrixs.CrossProdVectors3D(zVector,xVector, 0);
			wVector = Matrixs.CreateVertexVector3D(0,0,0,1);
			mMatrix = Matrixs.BuildMatrixFromVectors(xVector,yVector,zVector,wVector);
			mTag = Matrixs.BuildMatrixFromVectors(Matrixs.CreateVertexVector3D(1,0,0,-pVector[0][0]),
					Matrixs.CreateVertexVector3D(0,1,0,-pVector[1][0]),
					Matrixs.CreateVertexVector3D(0,0,1,-pVector[2][0]),
					Matrixs.CreateVertexVector3D(0,0,0,1));
			mMatrix = Mathematics.multiplicateMatrix(mMatrix,mTag);
			wl = setScan.nextDouble();
			wr = setScan.nextDouble();
			wb = setScan.nextDouble();
			wt = setScan.nextDouble();
			setScan.next();
			vw = setScan.nextDouble();
			vh = setScan.nextDouble();
			sepertation = vh/3;
			setScan.close();
			rotAxis = 3;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		matrixSc1 = Matrixs.CreateScaleMatrix3D(vw / (wr-wl), vh / (wt-wb),vh / (wt-wb));
		matrixTr2 = Matrixs.CreateTranslateMatrix3D((vw/2)-220, (vh/2)-220, 0);
		matrixTr3 = Matrixs.CreateTranslateMatrix3D(wl+(wr-wl)/2, wb+(wt-wb)/2,0);
		TT = Matrixs.CreateMatrix3D();
		CT = Matrixs.CreateMatrix3D();
		vectorVertex = new double [4][1];
		setSize((int)vw + 40, (int)vh + 40);
		centerX = (vw / 2) + 20;
		centerY = (vh / 2) + 20;
		vectorStart = new double[2];
		vectorEnd = new double[2];
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		char entry = keyEvent.getKeyChar();
		if(entry == 'x'){
			rotAxis = 1;
		}
		else if(entry == 'y'){
			rotAxis = 2;
		}
		else if(entry == 'z'){
			rotAxis = 3;
		}
		else if(entry == 'c'){
			if(clipping)
				clipping = false;
			else
				clipping = true;
		}
		else if(entry == 'r'){
			TT = Matrixs.CreateMatrix3D();
		}
		else if(entry == 'l'){
			reload();
		}
		else if(entry == 'q'){
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}