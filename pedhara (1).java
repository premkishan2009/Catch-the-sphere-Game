

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

import javax.imageio.ImageIO;
//import net.java.games.jogl.*;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;


public class pedhara extends Frame implements GLEventListener,KeyListener {
	List<double[]> subject, clipper, result;
	static GLUT glut = new GLUT();
	GLU glu = new GLU();
	static int HEIGHT = 1600, WIDTH = 1600;
	
	float white[] = {1, 1, 1, 1};
	 float red[] = {1, 0, 0, 1};
	 float origin[] = {0, 0, 0, 1};
	  float green[] = {0, 1, 0, 1};
	  float blue[] = {0, 0, 1, 1};
	  float black[] = {0.2f, 0.2f, 0.2f, 1};
	  float blackish[] = {0.3f, 0.3f, 0.3f, 0.3f};
	  float redish[] = {.3f, 0, 0, 1};
	  float greenish[] = {0, 1, 0, 1};
	  float blueish[] = {0, 0, .3f, 1};
	  float yellish[] = {.7f, .7f, 0.0f, 1};
	  float whitish[] = {0.8f, 0.8f, 0.8f, 1};
	  float position[] = {1, 1, 1, 0};
	static GL gl;
	static GLCanvas canvas;
	double[] x3, y3, z3,n1, n2,n3, x5, y5,z5, dir1, dir2, dir3;
	double a;
	double b;
	double c;
	
	double[] px;
	double[] py;
	double[] px1;
	double[] py1;
	double[] l1;
	double[] l2;
	double x, x4=800, y4=800,z4=200, theta1, theta2, x6, y6, x7, y7,z7,x8,y8,flip=1;
	double y;
	static Animator animator;
	double delta = 0,rtri=2;
	double r = HEIGHT / 4;
	double m = 0;
	double cx = 0;
	double cy = 0;
	double x0, y0, flag = 0;
	String x9, y9;
	int[] l,dis;
	int count=0;
	double[][] clipPoints = {{300, 500}, {1300, 500}, {1300, 1000}, {300, 1000}};
	double cnt=0;
	static byte[] img;
	  static int imgW, imgH, imgType;
	  static final int[] IRIS_TEX = new int[1];
	  static final int[] EARTH_TEX = new int[1];
	  static final int[] STARS_TEX = new int[1];
	

	public pedhara() {

		dir1 = new double[10];
		dir2 = new double[10];
		dir3= new double[10];
		x3 = new double[10];
		y3 = new double[10];
		z3 = new double[10];
		n1 = new double[10];
		n2 = new double[10];
		n3= new double[10];
		x5 = new double[10];
		y5 = new double[10];
		z5=new double [10];
		

		l = new int[10];
		dis = new int[10];
		l1 = new double[5];
		l2 = new double[5];
		px = new double[8];
		py = new double[8];
		px1 = new double[8];
		py1 = new double[8];
		
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		this.add(canvas, BorderLayout.CENTER);
		gl = canvas.getGL();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop();
				System.exit(0);

			}
		});
		
		canvas.addKeyListener(this);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pedhara frame = new pedhara();
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glFrustum(-2000, 2000, -2000, 2000, -2000, 2000);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);      // To operate on Model-View matrix
		gl.glLoadIdentity(); 
		gl.glWindowPos2d(0,300);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Score:"+count);
		gl.glWindowPos2d(0,140);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Click on the Window!");
		gl.glWindowPos2d(0,120);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Try to hit other objects using green sphere.");
		gl.glWindowPos2d(0,100);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Whenever the green sphere touches other objects it becomes red.");
		gl.glWindowPos2d(0,80);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Increase Intensity: Press i and decrease Intensity: Press o");
		gl.glWindowPos2d(0,60);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Use Left/Right Arrow to move light in x-direction");
		gl.glWindowPos2d(0,40);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Use Up/DOwn Arrow to move light in y-direction");
		gl.glWindowPos2d(0,20);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Press z/x to move in z-direction");
		 gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, redish,0);
		    gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, red,0);
		    gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, red,0);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, greenish,0);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, green,0);
		    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, green,0);
		bounce();
		myMaterialColor(blackish, whitish, white, black);
		gl.glRotated(cnt, 0, 1, 0);
		glu.gluLookAt(WIDTH/2, HEIGHT/2, -500, 0, 0, 0, 0, 1,0 );
		
		gl.glPushMatrix();
		gl.glTranslated(x3[0], y3[0], z3[0]);
		if(flag==0)
		 myMaterialColor(redish, redish, red, redish);
		else
			myMaterialColor(blueish, blueish, blue, blueish);
		glut.glutSolidSphere(50, 10, 10);
		gl.glPopMatrix();
		myMaterialColor(blackish, whitish, white, black);
		cnt=cnt+0.2;
		
		gl.glPushMatrix();		
		glut.glutWireCube(900);
		gl.glPopMatrix();
		
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0, 0);gl.glVertex3d(-450,-450 ,450);
		gl.glTexCoord2d(0, 1);gl.glVertex3d(450,-450 ,450);
		gl.glTexCoord2d(1, 1);gl.glVertex3d(450,-450 ,-450);
		gl.glTexCoord2d(1, 0);gl.glVertex3d(-450,-450 ,-450);
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		


		


		
		
		
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(x3[2]	, y3[2], z3[2]);
		gl.glVertex3d(x3[3], y3[3], z3[3]);
		gl.glEnd();
		
		
		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_OBJECT_LINEAR);
		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_OBJECT_LINEAR);
		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_OBJECT_LINEAR);
		gl.glEnable(GL.GL_TEXTURE_GEN_S);
		gl.glEnable(GL.GL_TEXTURE_GEN_T);
		gl.glEnable(GL.GL_TEXTURE_GEN_R);
		gl.glPushMatrix();
		
		gl.glTranslated(x3[1], y3[1], z3[1]);
		gl.glScaled(100,100,100);
		gl.glEnable(GL.GL_TEXTURE_2D);
		glut.glutSolidTetrahedron();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_GEN_S);
		gl.glDisable(GL.GL_TEXTURE_GEN_T);
		gl.glDisable(GL.GL_TEXTURE_GEN_R);
		
		gl.glPushMatrix();
		gl.glTranslated(a, b, c);
		if(distance3d())
		{
			gl.glEnable(GL.GL_LIGHT2);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, origin,0);
			 myMaterialColor(redish, redish, red, redish);
			 count++;
		}
		else
		{
			gl.glEnable(GL.GL_LIGHT1);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, origin,0);
			 myMaterialColor(greenish, greenish, green, greenish);
		}
		  
		glut.glutSolidSphere(100, 10, 10);
		gl.glPopMatrix();
		
		
			
		if(cnt>360)
		{
			cnt=1;
		}
		
		myMaterialColor(blackish, whitish, white, black);
		}

	


	private boolean distance3d() {
		// TODO Auto-generated method stub
		double d;
		for(int i=0;i<4;i++)
		{
			d=Math.sqrt((x3[i]-a)*(x3[i]-a)+(y3[i]-b)*(y3[i]-b)+(z3[i]-c)*(z3[i]-c));
			if(d<=150)
				return true;
		}
		return false;
	}

	private void bounce() {
		// TODO Auto-generated method stub
		x3[4]=(x3[2]+x3[3])/2;
		y3[4]=(y3[2]+y3[3])/2;
		z3[4]=(z3[2]+z3[3])/2;
		
		for(int j=0;j<=1;j++)
		{
			if(distance(4,j)<=100)
			{
				
				
				dir1[2]=dir1[j];
				dir2[2]=dir2[j];
				dir3[2]=dir3[j];
				dir1[3]=0.5*dir1[j];
				dir2[3]=0.4*dir2[j];
				dir3[3]=0.3*dir3[j];
				dir1[j]=-dir1[j];
				dir2[j]=-dir2[j];
				dir3[j]=-dir3[j];
				if(j==0)
					flag=1;
				
			}
		}
		
		for(int i=2;i<3;i++)
		{
			for(int j=0;j<=1;j++)
			{
				if(distance(i,j)<=100)
				{
					double temp4,temp5,temp6;
					temp4=dir1[i];
					temp5=dir2[i];
					temp6=dir3[i];
					dir1[i]=dir1[j];
					dir2[i]=dir2[j];
					dir3[i]=dir3[j];
					dir1[j]=temp4;
					dir2[j]=temp5;
					dir3[j]=temp6;
					if(j==0)
						flag=0;
				}
			}
		}
		
		if(distance(0,1)<=100)
		{
			double temp1,temp2,temp3;
			flag=1;
			temp1=dir1[0];
			temp2=dir2[0];
			temp3=dir3[0];
			dir1[0]=dir1[1];
			dir2[0]=dir2[1];
			dir3[0]=dir3[1];
			dir1[1]=temp1;
			dir2[1]=temp2;
			dir3[1]=temp3;
			
				flag=1;
			
		}
		for(int i=0;i<=3;i++)
		{
			if ((Math.abs(x3[i])>=450) && (Math.abs(y3[i])>=450)&&(Math.abs(z3[i])>=450)) {
				n1[i] = x3[i];
				n2[i] = y3[i];
				n3[i] = z3[i];
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i] = z5[i];
				if(i==0)
					flag=1;
			}else if ((Math.abs(x3[i])>=450) && (Math.abs(y3[i])>=450)){
				n1[i] = x3[i];
				n2[i] = y3[i];
				n3[i] = 0;
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i] = z5[i];
				if(i==0)
					flag=0;
			}else if((Math.abs(y3[i])>=450)&&(Math.abs(z3[i])>=450)){
				n1[i] = 0;
				n2[i] = y3[i];
				n3[i] = z3[i];
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i] = z5[i];
				if(i==0)
					flag=1;
			}else if((Math.abs(x3[i])>=450)&&(Math.abs(z3[i])>=450)){
				n1[i] = x3[i];
				n2[i] = 0;
				n3[i] = z3[i];
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i] = z5[i];
				if(i==0)
					flag=0;
			}
			else if (Math.abs(x3[i]) >=450) {
				n1[i] = x3[i];
				n2[i] = 0;
				n3[i]=0;
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i]= z5[i];
				if(i==0)
					flag=1;
			} else if (Math.abs(y3[i]) >=450) {
				n1[i] = 0;
				n2[i] = y3[i];
				n3[i]=0;
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i]= z5[i];
				if(i==0)
					flag=0;
			}else if (Math.abs(z3[i]) >=450) {
				n1[i] = 0;
				n2[i] = 0;
				n3[i]=z3[i];
				normalize(i);
				calculate(i);
				dir1[i] = x5[i];
				dir2[i] = y5[i];
				dir3[i]= z5[i];
				if(i==0)
					flag=1;
			}
		}
		for(int i=0;i<=3;i++){
		x3[i]=x3[i]+dir1[i];
		y3[i]=y3[i]+dir2[i];
		z3[i]=z3[i]+dir3[i];
		}
	}

	private double distance(int i, int j) {
		// TODO Auto-generated method stub
		double d=0;
		d=Math.sqrt((x3[i]-x3[j])*(x3[i]-x3[j])+(y3[i]-y3[j])*(y3[i]-y3[j])+(z3[i]-z3[j])*(z3[i]-z3[j]));
		return d;
	}

	private void calculate(int i) {
		// TODO Auto-generated method stub
		x5[i] = dir1[i] - 2 * (dir1[i] * n1[i] + dir2[i] * n2[i]+dir3[i]*n3[i]) * n1[i];
		y5[i] = dir2[i] - 2 * (dir2[i] * n2[i] + dir1[i] * n1[i]+dir3[i]*n3[i]) * n2[i];
		z5[i] = dir3[i] - 2 * (dir2[i] * n2[i] + dir1[i] * n1[i]+dir3[i]*n3[i]) * n3[i];
		
	}

	
	
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		

		animator = new Animator(canvas);
		animator.start();
		gl = drawable.getGL();

		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glDrawBuffer(GL.GL_BACK);
		readImage("pedhara.jpg"); // read the image to img[]
	    gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
	    initTexture();
		// gl.glShadeModel(GL.GL_FLAT);
	    // back face culling is discussed in Section 3.4
	    gl.glEnable(GL.GL_CULL_FACE);
	    // removing all back-facing polygons
	    gl.glCullFace(GL.GL_BACK);

	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_NORMALIZE);

	    //gl.glDisable(GL.GL_LIGHT0);
	    gl.glEnable(GL.GL_LIGHT0);
	    //gl.glEnable(GL.GL_LIGHT1);
	  
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position,0);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, blackish,0);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, whitish,0);
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, white,0);

	   

	   
	    
	    myMaterialColor(blackish, whitish, white, black);
		    
		    for(int i=0;i<=3;i++)
		    {
		    	x3[i]=Math.random();
		    	y3[i]=Math.random();
		    	z3[i]=Math.random();
		    	dir1[i]=5*Math.random();
		    	dir2[i]=5*Math.random();
		    	dir3[i]=5*Math.random();
		    }
		    x3[0]=100;
		    x3[1]=-100;
		    y3[2]=100;
		    y3[3]=150;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-2000, 2000, -2000, 2000, -2000, 2000);

		WIDTH = width;

		HEIGHT = height;

	}
	 public void myMaterialColor(
		      float myA[],
		      float myD[],
		      float myS[],
		      float myE[]) {

		    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, myA,0);
		    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, myD,0);
		    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, myS,0);
		    gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, myE,0);
		  }

	

	public void normalize(int i) {
		// TODO Auto-generated method stub
		double d = Math.sqrt(n1[i] * n1[i] + n2[i] * n2[i]+ n3[i]*n3[i]);

		if (d == 0) {
			System.err.println("0 length vector: normalize().");
			return;
		}
		n1[i] /= d;
		n2[i] /= d;
		n3[i] /= d;

	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==38){
			b=b+15;
		}
		if(e.getKeyCode()==40){
			b=b-15;
		}
		if(e.getKeyCode()==37){
			a=a-15;
		}
		if(e.getKeyCode()==39){
			a=a+15;
		}
		if(e.getKeyCode()==90){
			c=c+15;
		}
		if(e.getKeyCode()==88){
			c=c-15;
		}
		if(e.getKeyCode()==73){
			red[0]+=0.2f;
			redish[0]+=0.2f;
			green[0]+=0.2f;
			greenish[1]+=0.2f;
		}
		if(e.getKeyCode()==79){
			red[0]-=0.2f;
			redish[0]-=0.2f;
			green[1]-=0.2f;
			greenish[1]-=0.2f;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void readImage(String fileName) {
	    File f = new File(fileName);
	    BufferedImage bufimg;

	    try {
	      // read the image into BufferredImage structure
	      bufimg = ImageIO.read(f);
	      imgW = bufimg.getWidth();
	      imgH = bufimg.getHeight();
	      imgType = bufimg.getType();
	      
	      //TYPE_BYTE_GRAY  10
	      //TYPE_3BYTE_BGR 	5

	      // retrieve the pixel array in raster's databuffer
	      Raster raster = bufimg.getData();

	      DataBufferByte dataBufByte = (DataBufferByte)raster.
	                                   getDataBuffer();
	      img = dataBufByte.getData();
	      
	      // TYPE_BYTE 0

	    } catch (IOException ex) {
	      System.exit(1);
	    }
	  }
	 void initTexture() {

		    // initialize EARTH texture obj
		    gl.glGenTextures(1, IntBuffer.wrap(EARTH_TEX));
		    gl.glBindTexture(GL.GL_TEXTURE_2D, EARTH_TEX[0]);
		    gl.glTexParameteri(GL.GL_TEXTURE_2D,
		                       GL.GL_TEXTURE_MIN_FILTER,
		                       GL.GL_LINEAR);
		    gl.glTexParameteri(GL.GL_TEXTURE_2D,
		                       GL.GL_TEXTURE_MAG_FILTER,
		                       GL.GL_LINEAR);
		    readImage("pedhara.jpg");
		    gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB8,
		                    imgW, imgH, 0, GL.GL_BGR,
		                    GL.GL_UNSIGNED_BYTE, ByteBuffer.wrap(img));

		    
		  }




}

