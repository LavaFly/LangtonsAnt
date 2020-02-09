import java.util.ArrayList;

public class Steuerung {
    public static int dim = 10;                                                                                         //Steuerung hat Spielfeld und Gui, ist aber mehr so ein gemeinsamer Schnittpunkt
    private Spielfeld PlayField;
    private Gui4 MyGui;
    public static ArrayList<Ameise> antNest = new ArrayList<>();                                                        //zwischen den beiden wie eine tatsächliche Steuerung
    public Steuerung(){
        MyGui = new Gui4(this);
    }
    public void createGame(int a){
        PlayField = new Spielfeld(a,this);
    }                                                      //Die Namen der Methoden sagen eigentlich schon was sie machen
    public void createGame(){
        PlayField = new Spielfeld(this);
    }                                                            //Meistens werden die Funktionen von der Gui aufgerufen
    public void addAnt(){                                                                                               //und über die Steuerung an das Spielfeld weitergegeben
        PlayField.createAnt(
                (int)Math.floor(Math.random()*(PlayField.getWidthofFrame()/10))*10,
                (int)Math.floor(Math.random()*(PlayField.getWidthofFrame()/10))*10
        );
    }
    public void removeAnt(){
        PlayField.deleteAnt();
    }
    public void repaintField(){
        PlayField.repaintMe();
    }
    public void start(){
        PlayField.doStuff();
    }
    public void changeTime(int a){
        PlayField.changedelayTime(a);
    }
    public void changeAntText(){
        MyGui.updateAntText();
    }
    public void updateErrorMessage(String a){
        MyGui.errorMessagetxt(a);
    }
}
