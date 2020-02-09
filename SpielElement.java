import java.awt.Color;
public abstract class SpielElement {
    protected int xPos;
    protected int yPos;
    protected int d;                                                                                                    //Ich brauch keine Höhe oder Breite da alle meine Elemente quadratisch sind
    protected Color c = Color.BLACK;                                                                                    //Standart Farbe ist Schwarz
    public abstract int getDimension();
    public abstract Color getColor();
}
