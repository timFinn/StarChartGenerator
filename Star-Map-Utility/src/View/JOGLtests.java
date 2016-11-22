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

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dave
 */
public class JOGLtests implements GLEventListener, KeyListener, MouseListener {
   
    //need these to start openGL
    public static GLWindow glWindow;
    public static Animator animator;
    private GLUT glut = new GLUT(); //to use glut commands
    private double sphereSize; // this defines the size of our sphere  
    private double modifier = 1; //FIXME this is so that things look right
    private double lat = 0; //FIXME
    private double longitude=0; //FIXME
    //do I need to add the bracket thing? FIXME
    private ArrayList starArray;
    private ArrayList planetArray;
    private ArrayList messierArray;
    private ArrayList constellation = new ArrayList<>();
  //  Iterator<Star> starIt;
   
    /*TEST CODE HERE FIXME DELETEME
    */
    Star testStar1 = new Star(0,"TEST",0,0.785398,6);
    Star testStar2 = new Star(1,"ANOTHER",0,0,1);
    Star testStar3 = new Star(2,"FINAL",0,-0.785398,3);
   
    //THIS IS THE "eventual" final destination for JOGLtests. Copypaste up here.
    public JOGLtests(ArrayList stars, ArrayList messiers, ArrayList planets, ArrayList constellation,
            double latitude, double longitude) {
        this.lat=latitude;
        this.longitude = longitude;
        //finish ArrayList assignments here
        this.starArray = stars;
        this.messierArray = messiers;
        this.planetArray=planets;
        //this.constellation.addAll(constellation);
       
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
        //if(width>1000) { //any smaller and the universe looks UGLY
            this.sphereSize = screenSize*3/2; //maybe I want width/2 here
       // } else {
       //     this.sphereSize = 1000;
       // }
            this.modifier = sphereSize/screenSize; //FIXME test function
        //FIXME I need to change spheresize and etc. appropriately
        //this.sphereSize;
        
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glWindow = GLWindow.create(screen, glCapabilities);
        //int width;// = screen.getViewport().getWidth();
       //glWindow.setMaximized(true, true);
       // width = glWindow.getBounds().getWidth();
                
        //int height;// = screen.getViewport().getHeight();
       // height = glWindow.getBounds().getHeight();
        
        System.out.println(width+", "+height);
       
        //set beginning parameters for screen
        glWindow.setSize(screenSize, screenSize); //placeholder for future size FIXME
        glWindow.setPosition(200, 20); //placeholder, recommend middle of screen FIXME
        glWindow.setUndecorated(false); //don't need min/max/etc
        glWindow.setTitle("View from ("+this.lat+" ,"+this.longitude+")"); //Need to fix this to say where you're looking from FIXME
        glWindow.setAlwaysOnTop(false); //won't take over screen FIXME set back to false
        glWindow.setFullscreen(false); //won't fill screen
        glWindow.confinePointer(false); //won't trap mouse cursor
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG); //Don't know if needed
        glWindow.setVisible(true); //allows window to be seen.
        screen.removeReference(); //because I wanted to create the monitor size
       
        //listeners so you can resize screen, etc.
        glWindow.addGLEventListener(this);
        glWindow.addKeyListener(this);
        glWindow.addMouseListener(this);
        //TEST BLOCK NORMALLY THIS IS NOT COMMENTED OUT
        //glWindow.display();
        
        //TEST BLOCK FOR THINGS DELETEME FIXME
        animator = new Animator(glWindow);
        animator.start();
    }
   
    public JOGLtests(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //FIXME test data import
       
       
       
        //standard implementation template
        /*Display display = NewtFactory.createDisplay(null,true);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glWindow = GLWindow.create(screen, glCapabilities);
       
        //set beginning parameters for screen
        glWindow.setSize(1000, 1000); //placeholder for future size FIXME
        glWindow.setPosition(2000, 20); //placeholder, recommend middle of screen FIXME
        glWindow.setUndecorated(false); //don't need min/max/etc
        glWindow.setTitle("View from ("+this.lat+" ,"+this.longitude+")"); //Need to fix this to say where you're looking from FIXME
        glWindow.setAlwaysOnTop(false); //won't take over screen FIXME set back to false
        glWindow.setFullscreen(false); //won't fill screen
        glWindow.confinePointer(false); //won't trap mouse cursor
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG); //Don't know if needed
        glWindow.setVisible(true); //allows window to be seen.
       
        JOGLtests PLACEHOLDER = new JOGLtests(1);
        //listeners so you can resize screen, etc.
        glWindow.addGLEventListener(PLACEHOLDER);
        glWindow.addKeyListener(PLACEHOLDER);
        glWindow.addMouseListener(PLACEHOLDER);
        glWindow.display();
        //Begin GL drawing until stopped. //THIS NEEDS TO BE FIXED
       // animator = new Animator(glWindow);
       // animator.start();
        //animator.stop(); //force exit
        */
    }
        
    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("begin to draw"); //test line REMOVE ME
        GL2 gl2 = drawable.getGL().getGL2();
        
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        double temp = this.sphereSize+50; //create viewspace
        gl2.glOrtho(-temp, temp, -temp, temp, 0, temp*2); //FIXME define view of stars
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
     //TEST CODE TO SEE UNIVERSE FIXME
    //    gl2.glTranslated(0,0,-this.sphereSize);
        //FIXME this is where rotations will go to define viewerspace
       
    }
    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.out.println("Get rid of model to save memory!"); //test line REMOVE ME
        //retrieve elements to remove them
        GL2 gl2 = drawable.getGL().getGL2(); //lets me use gl commands
        //Currently I'm done when I dispose so I exit, can remove this from final
        System.exit(0);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    /*listens for reshape or keypresses. Initial drawing satisfies everything
        display would want to do.
    */
    public void display(GLAutoDrawable drawable) {
        System.out.println("display"); //am I even running display?
       
        GL2 gl2 = drawable.getGL().getGL2(); //gives me ability to use gl commands
        gl2.glClearColor(0,0,0,1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl2.glPushMatrix();
        //rotation here?
      //  gl2.glTranslated(0, 0, 300);
        this.drawStars(drawable);
        this.drawMessiers(drawable);
  /*      for (Planet currentPlanet: planetArray) {
            //stuff goes here
        }*/
        //loop here
       // float xpos =
        //FIXME THE FOLLOWING BLOCK IS TEST CODE
        //this.drawSphere(0, 0.785398, 50, drawable);
        /*double rd = 0; //FIXME delete, temp to test
        double ra = 0; //FIXME delete, temp to test
        double x, y, z;
        x = this.sphereSize*sin(rd)*cos(ra); //FIXME do we want to store this data?
        y = this.sphereSize*sin(rd)*sin(ra);
        z = this.sphereSize*cos(rd);
        gl2.glPushMatrix(); //keeps origin
        gl2.glTranslated(x, y, -z); //change to set location
        glut.glutSolidSphere(50, 16, 16); //FIXME
        gl2.glPopMatrix(); // */
        //FIXME REMOVE EVERYTHING ABOVE
        //draw constellations here FIXME
      //  this.drawSphere(0, 0.785398, 10, drawable);
        gl2.glPopMatrix(); //undo rotation here?
        gl2.glRotated(5, 0, 1, 0);
        
    
    gl2.glFlush(); //ensure everything gets drawn?
    
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //FIXME currently does not work as intended. CONFIRMED BROKEN
        //appears to run with everything commented out.
        System.out.println("reshape");
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        /**
         * Just the glViewport for this sample, normally here you update your
         * projection matrix.
         */
  /*      gl2.glViewport(x, y, width, height);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        //gl2.glOrtho(x, width, y, height, 1, 500); //FIXME
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
   */    
       
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //allows for escape, do I need this?
        //    animator.remove(glWindow);
            glWindow.destroy();
        }
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    draws spheres to grid
    currently, ra and rd MUST be in degrees/radians FIXME
    uses already converted magnitude!
    */
    private void drawSphere(double ra, double rd, double magnitude, GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        //convert to x,y,z location
        double x, y, z;
        //FIXME do I need to convert RA into degree/radian/etc.
        x = this.sphereSize*cos(rd)*cos(ra);
        z = this.sphereSize*cos(rd)*sin(ra);
        y = this.sphereSize*sin(rd);
        
     //   System.out.println(x+", "+y+", "+z);

        //draw
        gl2.glTranslated(x, y, -z); //change to set location
        glut.glutSolidSphere(magnitude, 16, 16); //FIXME
   //     System.out.println("sphere should draw!"); //test line REMOVE ME
        //assension is time
    }
    //does rotation based on latitude and longitude
    private void printName(String name, double magnitude, GLAutoDrawable drawable) {
    //    System.out.println("I'm supposed to draw a name here!");
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
       
           //ROTATE HERE IF there is a name FIXME
        //gl2.glRotated(angle, xline, yline, zline);//FIXME to correct rotation for viewer
        //FIXME I need this to scale with sphere size.
        gl2.glTranslated(0, -magnitude-20*this.modifier, 0); //FIX ME PLACEHOLDER CALCULATION FOR DRAWING TEXT ON SCREEN
        gl2.glScaled(0.8*this.modifier, 0.8*this.modifier, 0); //FIX ME placeholder calculation
        //FIXME Name alignment code goes here! Push this into a pretty function
        gl2.glRasterPos3d(0, 0, 0); //FIXMETEST, confirms it is in the right spot
        char[] temp = name.toCharArray();
        int length = name.length();
        for(int i = 0; i < length; i++) {
            glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, temp[i]);
        }
        /*FIXME DELETEME this was a test block
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'T');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'E');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'S');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'T');*/
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //helper function to convert RA to radian degrees.
    private double convertHoursToRadians(double hours) {
        double converted = hours;
        converted = converted*Math.PI/12;
        return converted;
    }
    private void drawStars(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        Iterator<Star> starIt = this.starArray.iterator();
        //    Star sun =starIt.next(); //pulls sun off the list   //FIXME
        while (starIt.hasNext()) {
            Star currentStar = starIt.next();
            // System.out.println("do I have a star?");
            gl2.glPushMatrix(); //keeps origin
            double ra = this.convertHoursToRadians(currentStar.rightAscension); //converts
            double rd = Math.toRadians(currentStar.declination); //FIXME possibly need to convert for radian
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
            //  System.out.println(rd);
            double mag = (-currentStar.magnitude + 7) * this.modifier; //FIXME to make it relative
            String name = currentStar.properName;
            gl2.glColor3d(0.678431, 0.847059, 0.901961); //set color for stellar bodies (LIGHTBLUE)
            if (currentStar.starID == 0) { //if the sun
                gl2.glColor3d(1, 0.843137, 0); //set color for the Sun (GOLD)
            }
            this.drawSphere(ra, rd, mag, drawable);
            //if the star is named, do the name draw part
            if (name.length() > 1) { //If name is defined
                //    System.out.println(name.length());
                //    System.out.println("I have a name, it's "+name);
                gl2.glColor3d(1, 1, 1);
                this.printName(name, mag, drawable);
            }
            gl2.glPopMatrix(); //reverts to origin
        }
    }
    private void drawMessiers(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        
        Iterator<Messier> mesIt = this.messierArray.iterator();
        //    Star sun =starIt.next(); //pulls sun off the list   //FIXME
        while (mesIt.hasNext()) {
            Messier currentMess = mesIt.next();
            gl2.glPushMatrix();
            double ra = this.convertHoursToRadians(currentMess.ra);
            double rd = Math.toRadians(currentMess.dec);
            String name; //PLACEHOLDER FOR NAMES FOR MESSY OBJECTS
            gl2.glColor3d(1,0,1);
            this.drawSphere(ra, rd, 20, drawable);
            
            gl2.glPopMatrix();
        }
    }
}