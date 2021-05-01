import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.InputEvent;

public class View extends JFrame {
    JFrame frame;
    JMenuBar menuBar;
    JMenu menuTP, menuFichier;
    JMenuItem miApropos, miQuitter, miNouveau, miOuvrir, miFermer, miEnregistrer, miEnregistrerSous, miExporter;

    JTable tableInventaire;
    JTable tableEntretien;
    DefaultTableModel modelInventaire;
    DefaultTableModel modelEntretien;

    JTextField txfFiltrer;
    JButton btnFiltrer;
    JButton btnInventairePlus;
    JButton btnInventaireMoins;
    JButton btnEntretienPlus;
    JButton btnEntretienMoins;
    JButton btnQuitter;


    public View(){

        //Création de la table d'inventaire
        String[] colNomInventaire = {"Nom", "Catégorie", "Prix", "Date d'achat", "Description"};
        modelInventaire = new DefaultTableModel(null,colNomInventaire);
        tableInventaire = new JTable(modelInventaire){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableInventaire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollInventaire = new JScrollPane(tableInventaire);
        scrollInventaire.setPreferredSize(new Dimension(600,300));

        //Création de la table d'entretien
        String[] colNomEntretien = {"Date", "Description"};
        modelEntretien = new DefaultTableModel(null, colNomEntretien);
        tableEntretien = new JTable(modelEntretien){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEntretien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollEntretien = new JScrollPane(tableEntretien);
        scrollEntretien.setPreferredSize(new Dimension(300,300));

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

        //Section filtre
        txfFiltrer = new JTextField();
        txfFiltrer.setPreferredSize(new Dimension(200,20));

        btnFiltrer = new JButton("Filtrer");

        //Section boutton d'ajouts
        btnInventairePlus = new JButton("+");
        btnInventaireMoins = new JButton("-");

        btnEntretienPlus = new JButton("+");
        btnEntretienMoins = new JButton("-");

        //Boutton quitter
        btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> btnQuitterAction());

        //Création du JPanel top
        JPanel pnlNord = new JPanel();
        pnlNord.setLayout(new BorderLayout());
        JPanel pnlLeftNord = new JPanel(new BorderLayout(5,5));
        pnlLeftNord.add(txfFiltrer, BorderLayout.WEST);
        pnlLeftNord.add(btnFiltrer, BorderLayout.EAST);
        pnlNord.add(pnlLeftNord, BorderLayout.WEST);

        //Création JPanel centre
        JPanel pnlCentre = new JPanel();
        pnlCentre.setLayout(new BorderLayout(5,5));

        JPanel pnlCentreGauche = new JPanel(new BorderLayout());
        JPanel pnlCentreGaucheBas = new JPanel(new BorderLayout());
        JPanel pnlCentreGaucheCoinBas = new JPanel(new BorderLayout(5,5));

        pnlCentreGaucheCoinBas.add(btnInventairePlus, BorderLayout.WEST);
        pnlCentreGaucheCoinBas.add(btnInventaireMoins, BorderLayout.EAST);

        pnlCentreGaucheBas.add(pnlCentreGaucheCoinBas, BorderLayout.WEST);

        pnlCentreGauche.add(scrollInventaire, BorderLayout.CENTER);
        pnlCentreGauche.add(pnlCentreGaucheBas, BorderLayout.SOUTH);

        JPanel pnlCentreDroit = new JPanel(new BorderLayout());
        JPanel pnlCentreDroitBas = new JPanel(new BorderLayout());
        JPanel pnlCentreDroitCoinBas = new JPanel(new BorderLayout(5,5));

        pnlCentreDroitCoinBas.add(btnEntretienPlus, BorderLayout.WEST);
        pnlCentreDroitCoinBas.add(btnEntretienMoins, BorderLayout.EAST);

        pnlCentreDroitBas.add(pnlCentreDroitCoinBas, BorderLayout.WEST);

        pnlCentreDroit.add(scrollEntretien, BorderLayout.CENTER);
        pnlCentreDroit.add(pnlCentreDroitBas, BorderLayout.SOUTH);

        pnlCentre.add(pnlCentreGauche, BorderLayout.WEST);
        pnlCentre.add(pnlCentreDroit, BorderLayout.CENTER);


        //Création du panel bas
        JPanel pnlBas = new JPanel(new BorderLayout(5,5));

        JPanel pnlBasDroit = new JPanel(new BorderLayout());
        pnlBasDroit.add(btnQuitter, BorderLayout.EAST);
        pnlBas.add(pnlBasDroit, BorderLayout.SOUTH);

        //Création du frame
        frame = new JFrame("Marc-Antoine Dubois - 1909082");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950,800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(15,10));
        frame.setResizable(false);

        //Ajouter menuBar au frame
        frame.setJMenuBar(menuBar);
        //Ajout du panel nord au frame
        frame.add(pnlNord, BorderLayout.PAGE_START);
        //Ajout du panel centre au frame
        frame.add(pnlCentre, BorderLayout.CENTER);
        //Ajout panel bas frame
        frame.add(pnlBas, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //Affiche un pop up avec l'information du projet
    private void miAproposAction() {
        JOptionPane.showMessageDialog(frame, """
                Travail pratique 2
                Créé par Marc-Antoine Dubois + 1909082
                Session H2021
                Dans le cadre du cours 420-C27""","À propos", JOptionPane.PLAIN_MESSAGE);
    }

    // Affiche un pop up qui demande à l'utilisateur s'il veut quitter le programme
    private void miQuitterAction() {
        int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter?", "Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            frame.dispose();
        }
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

    private void btnQuitterAction() {
        // A CHANGER
        frame.dispose();
    }

    public static void main(String[] args) {
        View myView = new View();

    }
}
