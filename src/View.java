import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class View extends JFrame {
    JFrame frame;
    JMenuBar menuBar;
    JMenu menuTP, menuFichier;
    JMenuItem miApropos, miQuitter, miNouveau, miOuvrir, miFermer, miEnregistrer, miEnregistrerSous, miExporter;

    public View(){
        
        //Création de la barre du menu
        menuBar = new JMenuBar();

        //Création du menu TP2
        menuTP = new JMenu("TP2");

        miApropos = new JMenuItem("À propos");
        miApropos.addActionListener(e -> miAproposAction());
        miApropos.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));

        miQuitter = new JMenuItem("Quitter");
        miQuitter.addActionListener(e -> miQuitterAction());
        miQuitter.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));


        //Création du menu Fichier
        menuFichier = new JMenu("Fichier");

        miNouveau = new JMenuItem("Nouveau");
        miNouveau.addActionListener(e -> miNouveauAction());
        miNouveau.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));

        miOuvrir = new JMenuItem("Ouvrir");
        miOuvrir.addActionListener(e -> miOuvrirAction());
        miOuvrir.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));

        miFermer = new JMenuItem("Fermer");
        miFermer.addActionListener(e -> miFermerAction());
        miFermer.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK));

        miEnregistrer = new JMenuItem("Enregistrer");
        miEnregistrer.addActionListener(e -> miEnregistrerAction());
        miEnregistrer.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

        miEnregistrerSous = new JMenuItem("Enregistrer sous");
        miEnregistrerSous.addActionListener(e -> miEnregistrerSousAction());
        miEnregistrerSous.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));

        miExporter = new JMenuItem("Exporter");
        miExporter.addActionListener(e -> miExporterAction());
        miExporter.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));



        menuBar.add(menuTP);
        menuBar.add(menuFichier);

        menuTP.add(miApropos);
        menuTP.addSeparator();
        menuTP.add(miQuitter);

        menuFichier.add(miNouveau);
        menuFichier.add(miOuvrir);
        menuFichier.add(miFermer);
        menuFichier.addSeparator();
        menuFichier.add(miEnregistrer);
        menuFichier.add(miEnregistrerSous);
        menuFichier.addSeparator();
        menuFichier.add(miExporter);

        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950,800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        //Ajouter menuBar au frame
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private void miAproposAction() {
    }

    private void miQuitterAction() {
    }

    private void miNouveauAction() {
    }

    private void miOuvrirAction() {
    }

    private void miFermerAction() {
    }

    private void miEnregistrerAction() {
    }

    private void miEnregistrerSousAction() {
    }

    private void miExporterAction() {
    }


    public static void main(String[] args) {
        View myView = new View();

    }
}
