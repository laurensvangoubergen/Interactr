package canvaswindow;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class InteractrCanvasTest {

    private InteractrCanvas interactrCanvas = new InteractrCanvas("Test");
    private File file;
    private BufferedImage bufferedImage;
    private BufferedImage bufferedImage2;

    @Before
    public void setUp() throws InvocationTargetException, InterruptedException {
        java.awt.EventQueue.invokeAndWait(() -> {
            interactrCanvas.show();
        });
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, '0');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_PRESSED, 78, 'n');
    }

    public void screenshot(String filename) throws AWTException, IOException{
        bufferedImage2 = interactrCanvas.captureImage();
        file = new File(filename);
        ImageIO.write(bufferedImage2, "png", file);
    }

    /*@Test
    public void test_interactrCanvas_init() throws AWTException, IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("init.png");
    }*/
    @Test
    public void test_interactrCanvas_addParty() throws IOException, AWTException, InterruptedException {
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,200,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'A');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'d');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'d');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'p');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'r');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'y');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_addParty.png");
    }

    @Test
    public void test_interactrCanvas_makeMessage() throws IOException, AWTException, InterruptedException {
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,200,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'M');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'k');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,400,170,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,400,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'M');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'g');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,241,241,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,440,238,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_makeMessage.png");
    }

    @Test
    public void test_interactrCanvas_message_stack() throws IOException, AWTException, InterruptedException {
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,200,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'M');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'k');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,400,170,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,400,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'M');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'g');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,600,170,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,600,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'S');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'c');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'k');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,241,241,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,440,238,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,442,233,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,643,235,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("interactrCanvas_message_stack.png");
    }

    @Test
    public void test_communication_diagram() throws InterruptedException,IOException, AWTException {
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,200,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'M');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'k');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,400,170,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,400,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'C');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'o');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'m');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'m');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'u');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'n');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'i');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'c');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'i');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'o');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'n');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,600,170,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,600,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'D');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'i');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'g');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'r');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'m');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,241,241,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,440,238,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,442,233,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,643,235,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'s');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'t');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_PRESSED,9,'\t');
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_communication_diagram.png");
    }

    @Test
    public void test_interactrCanvas_changeParty() throws IOException, AWTException, InterruptedException {
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,200,170,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'C');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'h');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'a');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'n');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'g');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'e');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,235,160,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,235,160,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,235,160,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,235,160,2);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,235,160,2);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,235,160,2);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_changeParty.png");
    }

    @Test
    public void test_interactrCanvas_copy() throws IOException, AWTException, InterruptedException{
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,197,175,2);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,':');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'C');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'o');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'p');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_TYPED, 0,'y');
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,351,108,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,872,161,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,628,283,1);
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_PRESSED, 17, '0');
        interactrCanvas.handleKeyEvent(KeyEvent.KEY_PRESSED, 68, 'd');
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_copy.png");
    }

    @Test
    public void test_interactrCanvas_deleteSubwindow() throws IOException, AWTException, InterruptedException{
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,686,113,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,686,113,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_CLICKED,686,113,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_deleteSubwindow.png");
    }

    @Test
    public void test_interactrCanvas_resizeSubwindow() throws IOException, AWTException, InterruptedException{
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,100,100,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,150,150,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,150,700,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,210,637,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,700,636,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,615,555,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,613,149,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,549,227,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,381,228,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,383,264,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,209,420,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,248,421,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,390,555,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,393,518,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_PRESSED,549,406,1);
        interactrCanvas.handleMouseEvent(MouseEvent.MOUSE_RELEASED,506,410,1);
        TimeUnit.SECONDS.sleep(2);
        this.screenshot("test_interactrCanvas_resizeSubwindow.png");
    }
}
