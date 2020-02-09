import java.awt.Graphics;
import javax.swing.JPanel;
public class Zeichner extends JPanel {
    private Steuerung Ctrl;
    public Zeichner(Steuerung xx){
        super();
        Ctrl = xx;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            for (int i = 0; i < Spielfeld.field.length; i++) {
                for (int j = 0; j < Spielfeld.field[i].length; j++) {
                        g.setColor(Ameise.colors[Spielfeld.field[i][j]]);
                        g.fillRect(i * Steuerung.dim, j * Steuerung.dim, Steuerung.dim, Steuerung.dim);           //geht zuerst alle Felder durch und zeichnet diese in der Farbe abhängig davon, welchen Wert sie tragen
                }
            }
            try {
                for (int i = 0; i < Steuerung.antNest.size(); i++) {                                                    //Zeichnet danach die Ameisen aus der Arraylist
                    g.setColor(Steuerung.antNest.get(i).getColor());                                                    //Anstatt der Höhe und Breite geb ich die "Dimension" an, da jede Ameise quadratisch ist
                    g.fillRect(Steuerung.antNest.get(i).getXPos(), Steuerung.antNest.get(i).getYPos(), Steuerung.antNest.get(i).getDimension(), Steuerung.antNest.get(i).getDimension());
                }
            }catch (Exception e){
                Ctrl.updateErrorMessage("Add more Ants");                                                            //Wenn keine Ameisen gefunden werden sollte es eigentlich ein Fehler geben
            }                                                                                                           //welchen ich dann ignoriere und mein Fehlerfeld aktualisiere, tut es aber inzwischen nichtmehr
    }
}
