package Lab13;

//Lab13.java
//The Paint Brush Program
//Fred Chen
// August 26, 2012
//AP Computer Science
 
import java.applet.Applet;
import java.awt.*;
 
 
public class Lab13 extends Applet
{
    private static final long serialVersionUID = 1L;
     
    Rectangle red, green, blue, black, yellow, eraser;
    Rectangle pen, brush, roller, line, clean;
     
    int numColor, numTool,lineCount, xClick, yClick;
    int currentStartX,currentStartY,currentEndX,currentEndY; //coordinate for drawLine
    boolean currentLineDone;
     
    Image virtualMem;
    Graphics gBuffer;
    int appletWidth, appletHeight;
     
    public void init()
    {
        resize(800, 600);
        appletWidth = getWidth();
        appletHeight = getHeight();
        virtualMem = createImage(appletWidth, appletHeight);
        gBuffer = virtualMem.getGraphics();
        numColor = 1;
        numTool =1;
         
        xClick = 0;
        yClick = 0;
        lineCount=0;
        currentLineDone = false;
         
        //create buttons
        red   = new Rectangle(5,5,40,40);
        green = new Rectangle(5,50,40,40);
        blue  = new Rectangle(5,95,40,40);
        black = new Rectangle(5,140,40,40);
        yellow= new Rectangle(5,185,40,40);
                 
        pen   = new Rectangle(5,300,40,40);
        brush = new Rectangle(5,345,40,40);
        roller= new Rectangle(5,390,40,40);
        line  = new Rectangle(5,435,40,40);
        eraser= new Rectangle(5,480,40,40);
        clean = new Rectangle(5,525,40,40);
    }
     
    /*
     * Set color and tool to draw in the applet.
     * label all the clickable button.
     */
    public void paint(Graphics g)
    {
        // label color on color selections
        gBuffer.setColor(Color.gray);
        gBuffer.fillRect(0,0,50,600);
        gBuffer.setColor(Color.red);
        gBuffer.fillRect(5,5,40,40);
        gBuffer.setColor(Color.green);
        gBuffer.fillRect(5,50,40,40);
        gBuffer.setColor(Color.blue);
        gBuffer.fillRect(5,95,40,40);
        gBuffer.setColor(Color.black);
        gBuffer.fillRect(5,140,40,40);
        gBuffer.setColor(Color.yellow);
        gBuffer.fillRect(5,185,40,40);
        // Color tool selections
        gBuffer.setColor(Color.white);
        gBuffer.fillRect(5,300,40,40);
        gBuffer.fillRect(5,345,40,40);
        gBuffer.fillRect(5,390,40,40);
        gBuffer.fillRect(5,435,40,40);
        gBuffer.fillRect(5,480,40,40);
        gBuffer.fillRect(5,525,40,40);
        // label tool names
        gBuffer.setColor(Color.black);
        gBuffer.drawString("Pen",7,320);
        gBuffer.drawString("Brush",7,365);
        gBuffer.drawString("Roller",7,410);
        gBuffer.drawString("Line",7,455);
        gBuffer.drawString("Eraser",7,500);
        gBuffer.drawString("Clean",7,545);
         
        //set color by click on color palette.
        switch(numColor){
        case 0:
            gBuffer.setColor(Color.white);
            break;
        case 1:
            gBuffer.setColor(Color.red);
            break;
        case 2:
            gBuffer.setColor(Color.green);
            break;
        case 3:
            gBuffer.setColor(Color.blue);
            break;
        case 4:
            gBuffer.setColor(Color.black);
            break;
        case 5:
            gBuffer.setColor(Color.yellow);
            break;
        }
         
        // different tools for drawing.
        switch(numTool){
        //set pen size: small.
        case 1:
            gBuffer.fillRect(xClick, yClick, 2, 2);
            g.drawImage(virtualMem, 0, 0, this);
            break;
        //set pen size: middle.
        case 2:
            gBuffer.fillRect(xClick, yClick, 20, 20);
            g.drawImage(virtualMem, 0, 0, this);
            break;
        //set pen size: big.
        case 3:
            gBuffer.fillRect(xClick, yClick, 50, 50);
            g.drawImage(virtualMem, 0, 0, this);
            break;
        /* draw line ***(works incorrectly)***
           In case 4, I have tried to erase lines to create the "rubber band" effect.
           However, the whole program would work abnormally. 
           Therefore, I have deleted that part.*/  
        case 4:
                g.setColor(Color.black);
                g.drawLine(currentStartX,currentStartY,currentEndX,currentEndY);
                delay(15);
                g.setColor(Color.white);
                g.drawLine(currentStartX,currentStartY,currentEndX,currentEndY);
                if (!currentLineDone)
                g.setColor(Color.black);
                g.drawLine(currentStartX,currentStartY,currentEndX,currentEndY);
                currentLineDone = false;
                break;
        // clean whole screen.***(may need click for a few times to work)***
        case 5:
            gBuffer.setColor(Color.white);
            gBuffer.fillRect(50,0,getWidth()-50,getHeight());
            g.drawImage(virtualMem,0,0,this);
            break;
         
        }
    }
     
    @SuppressWarnings("deprecation")
    public boolean mouseDown(Event e, int x, int y)
    {
        // select color by mouse click on colors.
        if(red.inside(x, y))
            numColor = 1;
        else if(green.inside(x, y))
            numColor = 2;
        else if(blue.inside(x, y))
            numColor = 3;
        else if(black.inside(x, y))
            numColor = 4;
        else if(yellow.inside(x, y))
            numColor = 5;
        else if(eraser.inside(x, y))
            numColor = 0;
        // select tool by mouse click on tools.
        if(pen.inside(x,y))
            numTool = 1;
        else if(brush.inside(x, y))
            numTool = 2;
        else if(roller.inside(x,y))
            numTool = 3;
        else if(line.inside(x, y))
            numTool = 4;
        else if(clean.inside(x, y))
            numTool = 5;
        // give coordinate to draw.
        if(x>50)// restrict area for drawing. 
            // coordinate for pen to start.
                xClick = x;
                yClick = y;
            // coordinate for line to start.
                currentStartX=x;
                currentStartY=y;
      
        return true;
    }
     
    public boolean mouseDrag(Event e, int x, int y)
    {
        if(x>50)
            // coordinate for pen to draw when mouse dragging.
                xClick = x;
                yClick = y;
            // coordinate for line to draw when mouse dragging.
                currentEndX=x;
                currentEndY=y;
             
          repaint();
        return true;
    }
     
         
    public boolean mouseUp(Event e,int x,int y)
    {   
         
        currentEndX=x;// final coordinate for line to stop.
        currentEndY=y;// final coordinate for line to stop.
        currentLineDone = true;
        return true;
    }
     
    // delay when eraseing lines.
    private void delay(long n) {  
        try {  
            Thread.sleep( n );   
        } catch(InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
     
    // prevent flinks when drawing.
    public void update(Graphics g)
    {
          paint(g);
    }
}
 
