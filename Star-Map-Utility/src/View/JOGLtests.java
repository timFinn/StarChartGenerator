/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Dave
 */
public class JOGLtests implements GLEventListener, KeyListener, MouseListener {
    
    //need these to start openGL
    public static GLWindow glWindow;
    public static Animator animator;
    private GLUT glut = new GLUT(); //to use glut commands
    private double sphereSize = 100; //FIXME this defines the size of our sphere   
    
    /**
     * @param args the command line arguments
     */
    
    //public static void main(String[] args) {
    public JOGLtests(){
        //standard implementation template
        Display display = NewtFactory.createDisplay(null,true);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glWindow = GLWindow.create(screen, glCapabilities);
        
        //set beginning parameters for screen
        glWindow.setSize(1000, 1000); //placeholder for future size FIXME
        glWindow.setPosition(100, 100); //placeholder, recommend middle of screen FIXME
        glWindow.setUndecorated(false); //don't need min/max/etc
        glWindow.setTitle("View from (LAT,LONG)"); //Need to fix this to say where you're looking from FIXME
        glWindow.setAlwaysOnTop(true); //won't take over screen FIXME set back to false
        glWindow.setFullscreen(false); //won't fill screen
        glWindow.confinePointer(false); //won't trap mouse cursor
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG); //Don't know if needed
        glWindow.setVisible(true); //allows window to be seen.
        
        JOGLtests PLACEHOLDER = new JOGLtests();
        //listeners so you can resize screen, etc.
        glWindow.addGLEventListener(PLACEHOLDER);
        glWindow.addKeyListener(PLACEHOLDER);
        glWindow.addMouseListener(PLACEHOLDER);
        
        //Begin GL drawing until stopped. //THIS NEEDS TO BE FIXED
        animator = new Animator(glWindow);
        animator.start();
        //animator.stop(); //force exit
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("begin to draw"); //test line REMOVE ME
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glViewport(0, 0, 1000, 1000);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        gl2.glOrtho(-500, 500, -500, 500, 1, 500); //FIXME
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor(1, 1, 1, 1);
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        //GL4 gl4 = drawable.getGL().getGL4();
        //draw stars here FIXME       
        //loop here
       // float xpos = 
        //FIXME THE FOLLOWING BLOCK IS TEST CODE
        this.drawSphere(0, 0.785398, 50, drawable);
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
        
        //gl2.glViewport(0, 0, 1000, 1000); //make sure same as setSize FIXME
        //this.display;
        
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
        //System.out.println("display"); //am I even running display?
        
        GL2 gl2 = drawable.getGL().getGL2(); //gives me ability to use gl commands
        //gl4.glCreateV
        //gl4.glClear(gl4.);
       // gl2.glClearColor(1,1,1,1);
      //  gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
        //draw constellations here FIXME
        //put in a loop, and convert FIXME
  //      double rd = 0; //FIXME delete, temp to test
   //     double ra = 0.785398; //FIXME delete, temp to test
  //      double x, y, z;
        //FIXME do I need to convert RA into degree/radian/etc.
 //      x = this.sphereSize*sin(rd)*cos(ra); //FIXME do we want to store this data?
  //      y = this.sphereSize*sin(rd)*sin(ra);
  //      z = this.sphereSize*cos(rd);
 //       gl2.glPushMatrix(); //keeps origin
        //gl2.glTranslated(x, y, z); //change to set location
      //  this.drawSphere(0, 0.785398, 10, drawable);
     /*   //ROTATE HERE IF there is a name FIXME
        //gl2.glRotated(angle, xline, yline, zline);//FIXME to correct rotation for viewer
        gl2.glTranslated(0, -this.sphereSize-100, 0); //FIX ME PLACEHOLDER CALCULATION FOR DRAWING TEXT ON SCREEN
        gl2.glScaled(5, 5, 0); //FIX ME placeholder calculation
        //FIXME Name alignment code goes here! Push this into a pretty function
        gl2.glRasterPos3d(0, 0, 0); //FIXMETEST, confirms it is in the right spot
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'T');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'E');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'S');
        glut.glutBitmapCharacter(GLUT.BITMAP_9_BY_15, 'T');
        */
  //      gl2.glPopMatrix();
        

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println("reshape");
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        /**
         * Just the glViewport for this sample, normally here you update your
         * projection matrix.
         */
        gl2.glViewport(x, y, width, height);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        //may need to fix this
        //gl2.glOrtho(x, width, y, height, 1, 1000); //FIXME
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //allows for escape, do I need this?
            animator.remove(glWindow);
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
    */
    private void drawSphere(double ra, double rd, double magnitude, GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2(); //allows gl commands
        //convert to x,y,z location
        double x, y, z;
        //FIXME do I need to convert RA into degree/radian/etc.
        x = this.sphereSize*sin(rd)*cos(ra);
        y = this.sphereSize*sin(rd)*sin(ra);
        z = this.sphereSize*cos(rd);
        //draw
        gl2.glColor3f(1, 0, 1); //set color for stellar bodies
        gl2.glPushMatrix(); //keeps origin
        gl2.glTranslated(x, y, z); //change to set location
        glut.glutSolidSphere(magnitude, 16, 16); //FIXME
        System.out.println("sphere should draw!"); //test line REMOVE ME
        gl2.glPopMatrix();
        //assension is time
    }
    
}
