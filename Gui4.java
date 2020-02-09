import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.*;
import javax.swing.JSpinner;
import java.util.concurrent.atomic.AtomicBoolean;
public class Gui4 extends JFrame {
  private final AtomicBoolean running = new AtomicBoolean(false);                                            //Vor dieser Gui gab es schon 4 andere versuche(Gui,Gui1,Gui2...)deshalb der Name
  private JLabel jLabel1 = new JLabel();
  private JTextField jNumberField1 = new JTextField();
  private JLabel jLabel2 = new JLabel();                                                                                //JLabel zum ausgeben von Fehlern
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel8 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JSpinner jSpinner1 = new JSpinner();
  private SpinnerNumberModel jSpinner1Model = new SpinnerNumberModel(1, 1, 10, 1);
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  private JButton jButton4 = new JButton();
  private JButton jButton5 = new JButton();
  private JButton jButton6 = new JButton();
  //public volatile boolean running = false;                                                                            //Noch hab ich den Stoppknopf nicht hinbekommen, das war ein Versuch, hiermit war ein Versuch den ich mir noch offen halte
  private Steuerung Ctrl;

  
  public Gui4(Steuerung xx) {
    // Frame-Initialisierung
    super();
    Ctrl = xx;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);                                                         //hier klappt das auch nicht, wieso erkläre ich ein bisschen weiter unten
    int frameWidth = 300;
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(300 + x, y);                                                                                         //das Fenster wird wieder in die mitte gesetzt, nur noch um 300 nach rechts
    setTitle("XXXsexyInterfaceXXX");                                                                                    //Title in bearbeitung
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);                                                                                                 //ich hab die Gui im javaEditor modelliert, deshalb der befehl
    this.addAllStuff(cp);                                                                                               //hier wird alles hinzugefügt, ich hab das in eine eigene Methode geschrieben

    jButton1.setBounds(104, 196, 73, 33);
    jButton1.setText("Start");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
    public void actionPerformed(ActionEvent evt) {
    jButton1_ActionPerformed(evt);
    }
    });
    cp.add(jButton1);
    jButton2.setBounds(104, 232, 75, 33);
    jButton2.setText("Stopp");
    jButton2.setMargin(new Insets(2, 2, 2, 2));
    jButton2.addActionListener(new ActionListener() { 
    public void actionPerformed(ActionEvent evt) { 
    jButton2_ActionPerformed(evt);
    }
    });
    //cp.add(jButton2);

    jButton3.setBounds(48, 72, 171, 25);
    jButton3.setText("Create PlayField");
    jButton3.setMargin(new Insets(2,2,2,2));
    jButton3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt){jButton3_ActionPerformed(evt); }
    });
    cp.add(jButton3);

      jButton4.setBounds(8, 120, 75, 25);
      jButton4.setText("Add Ant");
      jButton4.setMargin(new Insets(2,2,2,2));
      jButton4.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt){jButton4_ActionPerformed(evt); }
      });
      cp.add(jButton4);

    jButton5.setBounds(200, 120, 75, 25);
    jButton5.setText("Remove");
    jButton5.setMargin(new Insets(2,2,2,2));
    jButton5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt){jButton5_ActionPerformed(evt); }
    });
    cp.add(jButton5);

    jButton6.setBounds(170, 152, 30, 25);
    jButton6.setText("O");
    jButton6.setMargin(new Insets(2,2,2,2));
    jButton6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt){jButton6_ActionPerformed(evt); }
    });
    cp.add(jButton6);
                                                                                                                        //Alle Buttons wurden hinzugefügt, das hatte mir auch der javaEditor geschrieben
     setVisible(true);
  }

  public void jButton1_ActionPerformed(ActionEvent evt) {                                                               //Start Button
    running.set(true);                                                                                                  //und hier ist der Grund wieso der Prozess beim schliesen der Fenster nicht beendet wird(glaub ich zumindest)
      Executors.newSingleThreadExecutor().submit(new Runnable(){                                                        //https://stackoverflow.com/questions/18583413/run-function-on-jbutton-press
        public void run(){                                                                                              //ich lasse das Fenster in einem eigenen Thread laufen, damit ich während Laufzeit auch Änderungen machen kann
            Ctrl.start();                                                                                               //ziemlich praktisch
        }
      });
      if(!running.get()){
      jButton1.setText("Weiter");
    }
    else{
      jButton1.setText("Pause");

    }
  }

  public void jButton2_ActionPerformed(ActionEvent evt) {                                                               //Stopp Button

    Thread.currentThread().interrupt();
                                                                                                                        //Noch klappt der Knopf nich ganz
    jButton1.setText("Start");                                                                                          //Also eigentlich klappt er überhaput nicht
    //ctrl.setPVisible(false);
  }

  public void jButton3_ActionPerformed(ActionEvent evt){                                                                //Apply Button
    try {                                                                                                               //erstellt Spiel mit gewählter Größe
      Ctrl.createGame(Integer.parseInt(jNumberField1.getText()));
    } catch(Exception e){
      Ctrl.createGame();                                                                                                //wenn kein Input angeben, erstellt es trotzdem ein Spiel
      this.errorMessagetxt("kein Input, erstellt Spiel mit 700x700");
    }
  }
  public void jButton4_ActionPerformed(ActionEvent evt){
     try {
       Ctrl.addAnt();                                                                                                   //Sagt der Steuerung, dass eine Ameise hinzugefügt werden soll
       Ctrl.repaintField();                                                                                             //Sagt der Steuerung, dass das Spielfeld neu gezeichnet werden soll
       this.updateAntText();                                                                                            //Aktualisiert das Feld, welches die Anzahl der Ameisen anzeigt
     }catch(Exception e){
       //System.out.println("No Field to add Ant");
       this.errorMessagetxt("No Field to add Ant");
     }
  }
  public void jButton5_ActionPerformed(ActionEvent evt){                                                                //Remove Ant Button
    if(Steuerung.antNest.size()>0) {                                                                                    //Schaut ob überhaput eine Ameise da is
      Ctrl.removeAnt();                                                                                                 //wenn ja, sagt es der Steuerung, dass eine Ameise entfernt werden soll
      this.updateAntText();
    } else{
      System.out.println("No ant to remove");                                                                           //Wenn keine da is, wird der Text angezeigt
      this.errorMessagetxt("No ant to remove");
    }
  }
  public void jButton6_ActionPerformed(ActionEvent evt){
    Ctrl.changeTime((int)jSpinner1.getValue());
  }
  public int getNumAnts(){
    return Steuerung.antNest.size();
  }

  public void updateAntText(){
    jLabel8.setText("Anz.Ants "+Integer.toString(this.getNumAnts()));                                                    //Updated das Textfeld
  }
  public void errorMessagetxt(String err){
    jLabel2.setText(err);
  }
  public void addAllStuff(Container cp){

    jLabel1.setBounds(24, 24, 118, 28);
    jLabel1.setText("Spielfeldgroessee");
    cp.add(jLabel1);
    jLabel2.setBounds(24, 232, 230, 33);
    jLabel2.setText("");
    cp.add(jLabel2);
    jNumberField1.setBounds(168, 24, 83, 28);
    jNumberField1.setText("");
    cp.add(jNumberField1);
    jLabel8.setBounds(100, 120, 86, 20);
    jLabel8.setText("Anz.Ants "+Integer.toString(this.getNumAnts()));
    cp.add(jLabel8);
    jLabel4.setBounds(60, 152, 110, 20);
    jLabel4.setText("Speed");
    cp.add(jLabel4);
    jLabel7.setBounds(38,190,80,20);
    jLabel7.setText("");
    cp.add(jLabel7);


    jSpinner1.setBounds(130, 152, 40, 26);
    jSpinner1.setValue(1);
    jSpinner1.setModel(jSpinner1Model);
    cp.add(jSpinner1);



  }
}
