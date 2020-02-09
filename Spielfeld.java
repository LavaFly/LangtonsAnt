import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.util.concurrent.TimeUnit;
public class Spielfeld extends JFrame implements MouseListener{
    public Zeichner Painter;
    public static int field[][];
    private int width;
    private int height;
    private int delayTime = 500;
    private Steuerung Ctrl;

    public Spielfeld(Steuerung xx){
        super();                                                                                                        //Alles von JFrame wird übernommen
        Painter = new Zeichner(xx);
        Painter.setVisible(true);                                                                                       //Eine Sache, die ich zu oft vergessen hab...
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);                                                     //des klappt nich ganz, wieso erklär ich weiter unten
        this.width = 700;
        this.height = 700;
        setSize(width+16, height+38);//width+16, height+38
        Ctrl = xx;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();                                                      //Fenster wird in die Mitte des Monitors gepackt
        setLocation(((d.width - getSize().width)/2)-150,(d.height - getSize().height)/2);                         //Ist nicht wirklich wichtig, nur bei mehreren Monitoren praktisch
                                                                                                                        //Was bei mir der Fall ist
        setTitle("XXXsexySpielfeldXXX");   //Title noch ändern!!                                                        //Title in bearbeitung
        setResizable(false);

        Container c = getContentPane();                                                                                 //Kontainer wird erstellt und
        c.add(Painter);                                                                                                 //Zeichner hinzugefügt
        this.addMouseListener(this);
        setVisible(true);
        this.field = new int[height][width];                                                                            //field wird intialisiert und jeder Wert mit 0 gefüllt
        for(int i = 0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                this.field[i][j] = 0;
            }
        }
        //this.notCool(70);                                                                                               //damit wurde eine sehr uncoole Eigenschaft des JPanels festgestellt
    }
    public Spielfeld(int a,Steuerung xx){                                                                               //Macht das gleiche wie oben, a wird zur länge und breite gesetzt
        super();
        Painter = new Zeichner(xx);
        Ctrl = xx;                                                                                                      //Jedes Spielfeld ist also quadratisch
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        height = a;
        width = a;
        setSize(width+16, height+38);                                                                     //Jedes Spielfeld sollte quadratisch sein, allerdings wird der obere Rand mitgezählt
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();                                                      //Um zu wissen wieviel man hinzufügen muss, hab ich mir die Funktion notCool geschrieben
        setLocation(((d.width - getSize().width)/2)-150,(d.height - getSize().height)/2);
        setTitle("XXXsexySpielfeldXXX");
        setResizable(false);
        Painter.setVisible(true);
        Container c = this.getContentPane();
        c.add(Painter);
        setVisible(true);
        this.addMouseListener(this);
        this.field = new int[a][a];
        for(int i = 0;i<a;i++){
            for(int j = 0;j<a;j++){
                this.field[i][j]=0;
            }
        }
        //notCool(a);
    }
    private void notCool(int a){                                                                                        //Damit hab ich erst gemerkt, dass das Spielfeld nicht wirklich
        for(int i = 0;i<a/10;i++){                                                                                      //die größe hat, die es haben sollte
            this.createAnt(i*10,i*10);
        }
    }
    public void doStuff(){
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(getDelayTime());
            } catch(Exception e){
                System.out.println(e);
            }
            loop();                                                                                                     //nach jedem sleep wird die Loop funktion aufgerufen
        }
    }
    public void createAnt(int x, int y){
        Steuerung.antNest.add(new Ameise(x,y,true,false));                                                        //So wird eigentlich jede Ameise erstellt
    }
    public void deleteAnt(){
        Steuerung.antNest.remove(Steuerung.antNest.size()-1);                                                     //Ich tue sie nicht wirklich löschen,ich entferne sie nur aus dem Array
        Painter.repaint();                                                                                              //Der GarbageCollector dürfte sie dann aber löschen
    }
    public void loop(){
        for(int i = 0;i<Steuerung.antNest.size();i++) {                                                                 //Ich gehe jede Ameise durch
            Steuerung.antNest.get(i).checkField();                                                                      //Ich teste das Feld unter ihr und drehe sie in Abhängigkeit davon
            Steuerung.antNest.get(i).moveAnt();                                                                         //Ich bewege sie nach vorne
            checkBorder(Steuerung.antNest.get(i));                                                                      //Wenn sie sich aus dem Feld raus bewegt hat, bring ich sie auf die andere Seite des Feldes
            Painter.repaint();
        }
    }
    public void checkBorder(Ameise a){                                                                                  //schützt vor einem Array Index out of Bounds
        if(a.getXPos()>this.getWidthofFrame()){                                                                         //Indem es die Ameise beim verlassen des Bildschirms
            a.setXPos(0);                                                                                               //auf die andere Seite bringt
        } else if(a.getXPos()<0){
            a.setXPos(this.getWidthofFrame()-1);
        } else if(a.getYPos()>this.getHeightofFrame()){
            a.setYPos(0);
        } else if(a.getYPos()<0){
            a.setYPos(this.getHeightofFrame()-1);
        }
    }
    public int getHeightofFrame(){
        return height;
    }
    public int getWidthofFrame(){
        return width;
    }
    public void repaintMe(){                                                                                            //repainted alles nochmal, wird von anderen Objekten genutzt
        Painter.repaint();
    }
    public void mouseEntered(MouseEvent e){
    }                                                                            //muss hinzugefügt werden, weil ich den MouseListener implementiere
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){                                                                             //erstellt bei klicken eine Ameise an dem Punkt
        createAnt(((e.getX()-7)/10)*10,((e.getY()-30)/10)*10);
        Ctrl.changeAntText();                                                                                           //Ich teile und multipliziere den Wert 10, um die Zahl abzurunden(14=>10)
        repaintMe();
                                                                                                                        //-7 und -30 muss ich hinzufügen, weil der Fensterrand dazugefügt wird
    }                                                                                                                   //Wieso genau diese Werte kann ich mir selbst nicht ganz erklären, aber nach viel testen bin ich auf die Werte gekommen
                                                                                                                        //Und jetzt klappt es auch
    public void changedelayTime(int a){
        delayTime = 110-(a*10);
    }
    private int getDelayTime(){
        return delayTime;
    }



/**
    public int getWidth(){ return width; }                                                                                                                   diese beiden Methoden dürfen nicht auskommentiert werden
    public int getHeight(){ return height; }
**/

}
