/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Model.CelestialObject;
import Model.Messier;
import Model.Planet;
import Model.Star;
import Model.Constellation;
import Model.Moon;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.Display;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.GLReadBufferUtil;
import com.jogamp.opengl.util.awt.AWTGLReadBufferUtil;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dave
 */
public class DrawVerse implements GLEventListener, KeyListener, MouseListener {
   
    //need these to start openGL
    public static GLWindow glWindow;
    //public static Animator animator;
    private GLUT glut = new GLUT(); //to use glut commands
    private double sphereSize; // this defines the size of our sphere  
    private double modifier = 1; //modifier to scale graphics correctly
    private double lat; 
    private double longitude; 
    private ArrayList starArray;
    private ArrayList planetArray;
    private ArrayList messierArray;
    private ArrayList constellation= new ArrayList<>();
    private Moon moon;
  
    //I may need these to play with glOrtho in reshape call.
    private double xleft, xright;
    private double ydown, yup;
    private double znear, zfar;
   
    private boolean drawcnst, drawstr, drawmss, drawplnt, rotate, drawmoon;
    private int sphereSlice;
   
    public DrawVerse(ArrayList stars, ArrayList messiers, ArrayList planets, ArrayList constellation, Moon moon,
            double latitude, double longitude) {
       this.constellation =constellation;
        this.moon = moon;
        this.sphereSlice = 8; //initial slice calculation
        this.drawcnst = true; //draw no constellations
        this.drawstr = true; //draw stars
        this.drawmss = true; //draw messiers
        this.drawplnt = true; //draw planets
        this.rotate = false; //initialize test var
        this.drawmoon = true;//draw moon
        this.lat=latitude;
        this.longitude = longitude;
        //finish ArrayList assignments here
        this.starArray = stars;
        this.messierArray = messiers;
        this.planetArray=planets;
       
        Display display = NewtFactory.createDisplay(null,true);
        Screen screen = NewtFactory.createScreen(display, 0);
        screen.addReference(); //allows me to determine monitor size and adjust
        int height = screen.getPrimaryMonitor().getViewport().getHeight();
        int width = screen.getPrimaryMonitor().getViewport().getWidth();
        int screenSize;
        height= height*3/4;
        width = width*3/4;
        //SQUARES ASPECT
        if(width>height) { //if normal monitor
            screenSize = height;
        } else { //if not normal monitor
            screenSize = width;
        }
            this.sphereSize = screenSize*3/2; //make depth something that doesn't look bad on screen, maybe I want width/2 here

            this.modifier = sphereSize/screenSize; //attempt to make the drawing pretty
        //modify above to change spheresize and etc. appropriately
        
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glWindow = GLWindow.create(screen, glCapabilities);

        
        System.out.println(width+", "+height);
       
        //set beginning parameters for screen
        glWindow.setSize(screenSize, screenSize); //placeholder for future size
        glWindow.setPosition(200, 20); //placeholder, recommend middle of screen
        glWindow.setUndecorated(false); //don't need min/max/etc
        glWindow.setTitle("View from ("+this.lat+" ,"+this.longitude+")"); //sets window to say where you're looking from
        glWindow.setAlwaysOnTop(false); //won't take over screen
        glWindow.setFullscreen(false); //won't fill screen
        glWindow.confinePointer(false); //won't trap mouse cursor
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG); //Don't know if needed
        glWindow.setVisible(true); //allows window to be seen.
        screen.removeReference(); //because I wanted to create the monitor size
       
        //listeners so you can resize screen, etc.
        glWindow.addGLEventListener(this);
        glWindow.addKeyListener(this);
        glWindow.addMouseListener(this);
        //comment below out if you want to run animator.
        glWindow.display();
        
        //animator script;
       // animator = new Animator(glWindow);
       // animator.start();
    }
   
    public DrawVerse(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("begin to draw"); //test line REMOVE ME
        GL2 gl2 = drawable.getGL().getGL2();
        
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        double temp = this.sphereSize+50; //create viewspace
        this.xleft=-temp;
        this.xright=temp;
        this.ydown=-temp;
        this.yup=temp;
        this.znear=0;
        this.zfar=temp*2;
        gl2.glOrtho(xleft, xright, ydown, yup, znear, zfar); //define view of stars
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
        //FIXME this is where rotations will go to define viewerspace
        //THESE DO NOT WORK CORRECT CURRENTLY
        gl2.glRotated(this.lat,1,0,0);
        gl2.glRotated(this.longitude,0,1,0);       
        //gl2.glTranslated(0,0,-this.sphereSize);
        gl2.glRotated(90-63,1,0,0);        
    }
    @Override
    public void dispose(GLAutoDrawable drawable) {
       // System.out.println("Get rid of model to save memory!"); //test line REMOVE ME
        //retrieve elements to remove them
        GL2 gl2 = drawable.getGL().getGL2(); //lets me use gl commands
        //Currently I'm done when I dispose so I exit, can remove this from final
        System.exit(0);
    }
    @Override
    /*listens for reshape or keypresses. Initial drawing satisfies everything
        display would want to do.
    */
    public void display(GLAutoDrawable drawable) {
       // System.out.println("display"); //am I even running display?
       
        GL2 gl2 = drawable.getGL().getGL2(); //gives me ability to use gl commands
        gl2.glClearColor(0,0,0,1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        if(this.rotate) {  //to spin globe
            gl2.glRotated(15, 0, 1, 0);
            this.rotate = false;
        }
        gl2.glPushMatrix(); //preserve current manipulations
        //rotation here?
      //  gl2.glTranslated(0, 0, 300);
        if(this.drawstr) { //if drawing stars
            gl2.glPushMatrix();
            //ROTATE TO SIDEREAL TIME
        this.drawStars(drawable);
        gl2.glPopMatrix();
        }
        if(this.drawcnst) { //if drawing constellations
            this.drawConstellations(drawable);
        }
        if(this.drawmss) { //if drawing messier
        this.drawMessiers(drawable);
        }
        if(this.drawplnt) { //if drawing planets
            this.drawPlanets(drawable);
        }
        if(this.drawmoon) { //if drawing the moon
            this.drawMoon(drawable);
        }
        gl2.glPopMatrix(); //undo rotation here?
        
    
    gl2.glFlush(); //ensure everything gets drawn?
    
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //FIXME currently does not work as intended. CONFIRMED BROKEN
        //reverted to default function from glWindow which seems to work.
        //appears to run with everything commented out.
        System.out.println("reshape");
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        /**
         * Viewport appears to resolve itself in glwindows.
         */
        
       /*
        int view = width;
        if(width>height) { //if you scroll right, expand down
            this.reshape(drawable, x, y, view, view);
        } else if (height>width) {
            view = height;  //if you scroll down, expand right
            this.reshape(drawable, x, y, view, view);
        } else { //if expansion has occurred
            gl2.glViewport(x, y, view, view); //fix viewport, probably don't need this
        */
            //then redefine sphere area.
    /*    gl2.glViewport(x, y, width, height);
        this.xright=this.xleft+width;
        this.ydown=this.yup-height;
            gl2.glMatrixMode(GL2.GL_PROJECTION);
            gl2.glLoadIdentity();
            //may need to fix this
            //double temp = this.sphereSize + 50; //create viewspace
            gl2.glOrtho(xleft, xright, ydown, yup, znear, zfar); //FIXME define view of stars
            gl2.glMatrixMode(GL2.GL_MODELVIEW);
       */     
       /* gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        //gl2.glOrtho(x, width, y, height, 1, 500); //FIXME
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
       */
       
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

//allows for escape, do I need this?
            //    animator.remove(glWindow);
            glWindow.destroy();

        } else if (e.getKeyCode() == KeyEvent.VK_C) { //draw or not constellations
            this.drawcnst = !drawcnst;
        } else if (e.getKeyCode() == KeyEvent.VK_M) { //draw or not Messier
            this.drawmss = !drawmss;
        } else if (e.getKeyCode() == KeyEvent.VK_S) { //draw or not stars
            this.drawstr = !drawstr;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            this.drawplnt = !drawplnt;
        } else if (e.getKeyCode() == KeyEvent.VK_T) { //image process for stars faster
            if (this.sphereSlice <= 2) { //if already as small as can be, do nothing
                this.sphereSlice = 2;
            } else {
                this.sphereSlice = sphereSlice / 2; //divide by 2
            }
        } else if (e.getKeyCode() == KeyEvent.VK_Y) { //image process for stars slower
            if (this.sphereSlice >= 32) { //this is absurdly high, do nothing
                this.sphereSlice = 32;
            } else {
                this.sphereSlice = sphereSlice * 2; //mulitply by 2
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) { //rotate
            this.rotate = true;
        } else if (e.getKeyCode() == KeyEvent.VK_Z) { //printscreen
            System.out.println("Put the call to the function to create the jpg here, human"); //FIX ME DELETE ME HEY RIGHT HERE, just by pressing z you can test.
            
            AWTGLReadBufferUtil buff = new AWTGLReadBufferUtil(glWindow.getGLProfile(), true);
            //BufferedImage image = buff.readPixelsToBufferedImage(glWindow.getGL().getGL()., true);
            //buff.readPixels(, false);
            File dest = new File("chart.jpg");
            //buff.write(dest);
        } else if (e.getKeyCode() == KeyEvent.VK_N) { //draw moon
            this.drawmoon = !drawmoon;
        }
        glWindow.display(); //redraw?
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseEntered(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseExited(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mousePressed(MouseEvent arg0) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseReleased(MouseEvent arg0) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseMoved(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseDragged(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseWheelMoved(MouseEvent e) {
       // System.out.println("mousewheel!"+e.getClickCount());
        
        GL2 gl = glWindow.getGL().getGL2(); //this may not work
        //glWindow.display();
        //
        /* String message;
       int notches = e.getWheelRotation();
       if (notches < 0) {
           message = "Mouse wheel moved UP "
                        + -notches + " notch(es)" + newline;
       } else {
           message = "Mouse wheel moved DOWN "
                        + notches + " notch(es)" + newline;
       }
       if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
           message += "    Scroll type: WHEEL_UNIT_SCROLL" + newline;
           message += "    Scroll amount: " + e.getScrollAmount()
                   + " unit increments per notch" + newline;
           message += "    Units to scroll: " + e.getUnitsToScroll()
                   + " unit increments" + newline;
           message += "    Vertical unit increment: "
               + scrollPane.getVerticalScrollBar().getUnitIncrement(1)
               + " pixels" + newline;
       } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
           message += "    Scroll type: WHEEL_BLOCK_SCROLL" + newline;
           message += "    Vertical block increment: "
               + scrollPane.getVerticalScrollBar().getBlockIncrement(1)
               + " pixels" + newline;
       }
       //saySomething(message, e);
        */
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    draws spheres to grid
    currently, ra and rd MUST be in radians
    uses already converted magnitude!
    */
    private void drawSphere(double ra, double rd, double magnitude, GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        //convert to x,y,z location
        double x, y, z;
        x = this.sphereSize*cos(rd)*cos(ra);
        z = this.sphereSize*cos(rd)*sin(ra);
        y = this.sphereSize*sin(rd);
        
     //   System.out.println(x+", "+y+", "+z);

        //draw
        gl2.glTranslated(x, y, -z); //change to set location
        glut.glutSolidSphere(magnitude, this.sphereSlice, this.sphereSlice);
    }
    
    //does rotation based on latitude and longitude
    private void printName(String name, double magnitude, GLAutoDrawable drawable) {
    //    System.out.println("I'm supposed to draw a name here!");
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
       
           //potential ROTATE HERE to get text to face user
        //gl2.glRotated(angle, xline, yline, zline);//This needs to be changed to correct rotation for viewer
        gl2.glTranslated(0, -magnitude-20*this.modifier, 0); //place beneath star
        gl2.glScaled(0.8*this.modifier, 0.8*this.modifier, 0); //increase size to make legible.
        //gl2.glTranslated(0, -magnitude-20*this.modifier, 0); //place beneath star
        gl2.glRasterPos3d(0, 0, 0); //confirms it is in the right spot
        char[] temp = name.toCharArray();
        int length = name.length();
        for(int i = 0; i < length; i++) {
            glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, temp[i]);
        }
    }
    
    //helper function to convert RA in decimal hours to radians.
    private double convertHoursToRadians(double hours) {
        double converted = hours;
        converted = converted*Math.PI/12;
        return converted;
    }
    private void drawStars(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        Iterator<Star> starIt = this.starArray.iterator();
        while (starIt.hasNext()) {
            Star currentStar = starIt.next();
            gl2.glPushMatrix(); //keeps origin
            double ra = this.convertHoursToRadians(currentStar.rightAscension); //converts
            double rd = Math.toRadians(currentStar.declination); //
            /*      
             hopefully I don't need this code, but leaving in for now.
             if((ra>=Math.PI/2)&&(ra<=3*Math.PI/2)) { //if on opposite side of earth convert rd
             if(rd>0) {
             rd+= Math.PI; //add pi to put it on the other side of the planet?
             } else {
             rd-= Math.PI; //subtract pi
             }
             }
             */
            double mag = (-currentStar.magnitude + 7) * this.modifier; //currently scale's a star based on its magnitude, could change this to logarithmic
            String name = currentStar.properName;
            gl2.glColor3d(0.678431, 0.847059, 0.901961); //set color for stellar bodies (LIGHTBLUE)
            if (currentStar.starID == 0) { //if the sun
                gl2.glColor3d(1, 0.843137, 0); //set color for the Sun (GOLD)
            }
            this.drawSphere(ra, rd, mag, drawable);
            //if the star is named, do the name draw part
            if (name.length() > 1) {
                gl2.glColor3d(1, 1, 1);
                this.printName(name, mag, drawable);
            }
            gl2.glPopMatrix(); //reverts to origin
        }
    }
    private void drawMessiers(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        int PLACEHOLDER = 5; //placeholder value to make messier objects pop
        Iterator<Messier> mesIt = this.messierArray.iterator();
        while (mesIt.hasNext()) {
            Messier currentMess = mesIt.next();
            gl2.glPushMatrix();
            double ra = Math.toRadians(currentMess.ra);
            double rd = Math.toRadians(currentMess.dec);
            String name = "M"+currentMess.nosInCat; //NAMES FOR MESSY OBJECTS
            gl2.glColor3d(1,0,1);
            this.drawSphere(ra, rd, PLACEHOLDER, drawable);
            this.printName(name, PLACEHOLDER, drawable);
            gl2.glPopMatrix();
        }
    }
    private void drawPlanets(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        int PLACEHOLDER =10; //placeholder value to make planet objects pop
        Iterator<Planet> planIt = this.planetArray.iterator();
        while (planIt.hasNext()) {
            Planet currentPlan = planIt.next();
            gl2.glPushMatrix();
            double ra = currentPlan.getRightAsc();
            double rd = currentPlan.getDeclination();
            String name = currentPlan.getPlanetName(); //NAME FOR planet OBJECTS
            gl2.glColor3d(1,1,1);
            this.drawSphere(ra, rd, PLACEHOLDER, drawable);
            this.printName(name, PLACEHOLDER, drawable);
            gl2.glPopMatrix();
        }
    }
    private void drawMoon(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        System.out.println("I should be drawing moon now");
        gl2.glPushMatrix();
        int PLACEHOLDER = 15; //to make moon pop
        double ra = this.moon.getRA();
        double rd = this.moon.getDec();
        String name = this.moon.getPhase();
        gl2.glColor3d(1,1,1);
        this.drawSphere(ra, rd, PLACEHOLDER, drawable);
        this.printName(name, PLACEHOLDER, drawable);
        gl2.glPopMatrix();
    }
    
    private void drawConstellations(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        Iterator<Constellation> groupIt = this.constellation.iterator();
        while(groupIt.hasNext()) {
            Constellation currentConstellation = groupIt.next();
            boolean nameflag = true;
        Iterator<CelestialObject> conIt = currentConstellation.constellationMembers.iterator();
        gl2.glColor3d(0, 0.74902, 1); //constellations are deepskyblue
        gl2.glLineWidth((float)this.modifier);
        while (conIt.hasNext()) {
            CelestialObject startOfLine = conIt.next();
                CelestialObject endOfLine = conIt.next();
                //finds x,y,z position for start
                double ra1 = this.convertHoursToRadians(startOfLine.ra);
                double rd1 = Math.toRadians(startOfLine.dec);
                double x1, y1, z1;
                //
                x1 = this.sphereSize * cos(rd1) * cos(ra1);
                z1 = this.sphereSize * cos(rd1) * sin(ra1);
                y1 = this.sphereSize * sin(rd1);
if(nameflag) { //print name of constellation under first star of constellation
    String name = currentConstellation.constellationName;
    gl2.glPushMatrix(); //preserve state
    gl2.glTranslated(x1, y1-20, z1);  //PLACEHOLDER TO SHIFT IT DOWN A LITTLE
    this.printName(name, 0, drawable);
    gl2.glPopMatrix();
    nameflag = false;
    
}
                //finds x,y,z position for end
                double ra2 = this.convertHoursToRadians(endOfLine.ra);
                double rd2 = Math.toRadians(endOfLine.dec);
                double x2, y2, z2;
                //
                x2 = this.sphereSize * cos(rd2) * cos(ra2);
                z2 = this.sphereSize * cos(rd2) * sin(ra2);
                y2 = this.sphereSize * sin(rd2);
                //draw line
                gl2.glBegin(GL2.GL_LINES);
                {
                    System.out.println("I'm drawing lines");
                    gl2.glVertex3d(x1, y1, z1);
                    gl2.glVertex3d(x2, y2, z2);
                }
                gl2.glEnd();
           // }
        }        
        }
    }
}
