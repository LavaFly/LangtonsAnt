import java.awt.*;
import java.util.*;
public class Ameise extends SpielElement {
    private int up = 0;
    private int right = 1;
    private int down = 2;
    private int left = 3;
    private int currentview = 2;
    private int colInt = 1;                                                                                             // die Farbe einer Ameise ist immer colors[colInt]
    private boolean debug = false;                                                                                      // Boolean, welches ich eingerichtet hab damit ich mir, wenn ich will verschiedene Informationen ausgeben kann
    public static Color[] colors = {Color.white,Color.BLACK,Color.blue,Color.cyan,Color.green,Color.magenta,Color.orange,Color.red,Color.yellow}; //Alle möglichen Farben
    //public static ArrayList<Ameise> antNest = new ArrayList<>();                                                      // ArrayListe für alle Ameisen
    //Ich hab mich noch nicht ganz entschieden wer die ArrayListe halten sollte, gerade hat sie die Steuerung
    public Ameise(){
        xPos = 200;
        yPos = 200;
        d = Steuerung.dim;                                                                                              //weil alles quadratisch ist und alles die gleiche größe hat, hab ich sie zu einem static Attribut von Steuerung gemacht
        c = Color.BLACK;
        Steuerung.antNest.add(this);
        if(debug) {                                                                                                     // wenn debug = true gibt es mir den Index aus(der aber falsch ist) sowie noch andere Informationen über die Ameise aus
            System.out.println("AntNumber:" + Steuerung.antNest.indexOf(this) + "\n" +
                    "xPos:" + xPos + "\n" +
                    "yPos:" + yPos + "\n" +
                    "Color:" + c + "\n");
        }
        checkColInt();                                                                                                  //Setzt colInt nochmal richtig, da ich immernur die Farbe setzte, für andere Funktionen nutze ich immer den colInt, also muss der auch nochmal gesetzt werden
    }
    public Ameise(int x, int y){                                                                                        // Hier nehme ich die zwei mitgegebenen Werte und setzte sie zu den X und Y Werten der Ameise
        xPos = x;
        yPos = y;
        d = Steuerung.dim;
        c = Color.BLACK;
        Steuerung.antNest.add(this);
        if(debug) {
            System.out.println("AntNumber:" + Steuerung.antNest.indexOf(this) + "\n" +
                    "xPos:" + xPos + "\n" +
                    "yPos:" + yPos + "\n" +
                    "Color:" + c + "\n");
        }
        checkColInt();
    }
    public Ameise(int x,int y,boolean b,boolean r){                                                                     //Der eigentlich einzige genutzte Konstruktor
        xPos = x;
        yPos = y;
        d = Steuerung.dim;
        if(b) {                                                                                                         // bestimmt ob eine Farbe zufällig gewählt werden soll oder es Schwarz werden soll
            colInt = (int) Math.ceil(Math.random() * (Ameise.colors.length-1));                                         //Ich wähle eine zufällige Zahl, multipliziere sie mit der Anzahl aller möglichen Farben-1
            c = Ameise.colors[colInt];                                                                                  //und runde danach auf, dadurch kann das Ergebnis nie 0 werden(also die Farbe weiß), was ziemlich nervig auf einem weißen Hintergrund war
        } else {
            colInt = 1;
            c = Color.BLACK;
        }
        if(r) {                                                                                                         //meistens tue ich beim erstellen das Objekt der Liste hinzufügen, also brauch ich das im Konstruktor nicht
            Steuerung.antNest.add(this);                                                                                          //Ich hab mir aber mal die Option noch offen gelassen
        }
        if(debug) {
            System.out.println("AntNumber:" + Steuerung.antNest.indexOf(this) + "\n" +
                    "xPos:" + xPos + "\n" +
                    "yPos:" + yPos + "\n" +
                    "Color:" + c + "\n");
        }
        checkColInt();
    }
    public Ameise(int x,int y,int j){                                                                                   //Wird eigentlich nie verwendet, ist aber halt noch da
        xPos = x;
        yPos = y;
        d = Steuerung.dim;
        c = Ameise.colors[j%Ameise.colors.length];
        Steuerung.antNest.add(this);
        if(debug) {
            System.out.println("AntNumber:" + Steuerung.antNest.indexOf(this) + "\n" +
                    "xPos:" + xPos + "\n" +
                    "yPos:" + yPos + "\n" +
                    "Color:" + c + "\n");
        }
        checkColInt();
    }
    public Ameise(int x,int y,Color col){                                                                               //Wird auch nie verwendet, hatte ich aber mal gebraucht
        xPos = x;
        yPos = y;
        d = Steuerung.dim;
        c = col;
        Steuerung.antNest.add(this);
        if(debug) {
            System.out.println("AntNumber:" + Steuerung.antNest.indexOf(this) + "\n" +
                    "xPos:" + xPos + "\n" +
                    "yPos:" + yPos + "\n" +
                    "Color:" + c + "\n");
        }
        checkColInt();
    }
    public void moveAnt(){                                                                                              //Bewegt die Ameise in abhänigkeit der Blickrichtung
        if(currentview==up){                                                                                            //Bewegt sie jeweils um länge d
            yPos-=d;
        }else if(currentview==right){
            xPos+=d;
        }else if(currentview==down){
            yPos+=d;
        }else if(currentview==left){
            xPos-=d;
        }
        if(debug) {
            System.out.println("AntNumber:"+Steuerung.antNest.indexOf(this)+"\n"+
                    "xPos:"+xPos+"\n"+
                    "yPos:"+yPos+"\n"+
                    "Color:"+c+"\n");
        }
    }
    public int getView(){                                                                                               //ziemlich selbsterklärend
        return currentview;
    }
    private void setView(int v){
        currentview=v;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public int getDimension(){
        return d;
    }
    public Color getColor(){
        return c;
    }
    public int getColInt(){
        return colInt;
    }
    public void setXPos(int x){
        xPos = x;
    }
    public void setYPos(int y){
        yPos = y;
    }
    private void checkColInt(){                                                                                         //legt colInt fest, sehr wichtig für die nächste Methode
        for(int i = 0;i<colors.length;i++){                                                                             //da ich immer mit der colInt arbeite und nicht mit der eigentlichen Farbe
            if(c == colors[i]){
                colInt = i;
            }
        }
    }
    public void checkField(){
        if (Spielfeld.field[(this.getXPos()) / this.getDimension()][(this.getYPos()) / this.getDimension()] > 0 ) {     //Is ein bisschen unübersichtlich, schaut aber ob das Feld, wo sich die Ameise befindet
            if (this.getView() == 3) {                                                                                  //Weiß ist oder eine andere Farbe
                this.setView(0);                                                                                        //Wenn das Feld Weiß ist wird die Blickrichtung geändert
            } else {                                                                                                    //Sie wird um eins addiert(was einer Drehung von 90Grad entspricht)
                this.setView(this.getView() + 1);                                                                       //Auser wenn sie bereits am Maximum ist, dann wird sie wieder auf 0 gesetzt
            }                                                                                                           //Im Nachhinein hätte ich die Blickrichtung mit % berechnen können dann hätte ich mir die Abfrage sparen können
            Spielfeld.field[(this.getXPos()) / this.getDimension()][(this.getYPos()) / this.getDimension()] = 0;        //Das Feld auf welcher sich die Ameise befindet wird zu 0 gesetzt
        } else {                                                                                                        //Wenn das Feld einen anderen Wert wie 0 hat, also irgendeiner Farbe auser weiß
            if (this.getView() == 0) {                                                                                  //Hier passiert das gleiche wie oben nur andersherum
                this.setView(3);
            } else {
                this.setView(this.getView() - 1);
            }
            Spielfeld.field[(this.getXPos()) / this.getDimension()][(this.getYPos()) / this.getDimension()] = this.getColInt();//Das Feld bekommt die Farbe der Ameise
        }
    }
}
