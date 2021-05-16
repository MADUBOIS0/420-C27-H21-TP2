import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

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

    ArrayList<Inventaire> inventaireData = new ArrayList<>();

    public View(){

        //Création de la table d'inventaire
        String[] colNomInventaire = {"Nom", "Catégorie", "Prix", "Date d'achat", "Description"};
        modelInventaire = new DefaultTableModel(colNomInventaire, 0);

        for(Inventaire objet: inventaireData){
            modelInventaire.addRow(new Object[] {objet.getNom(), objet.getCategorie(), objet.getPrix(), objet.getDateAchat(), objet.getDescription()});
        }
        tableInventaire = new JTable(modelInventaire){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableInventaire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableInventaire.getTableHeader().setReorderingAllowed(false);
        tableInventaire.getTableHeader().setResizingAllowed(false);
        tableInventaire.setAutoCreateRowSorter(true);
        TableRowSorter<DefaultTableModel> sorterInventaire = new TableRowSorter<>(modelInventaire);
        tableInventaire.setRowSorter(sorterInventaire);
        tableInventaire.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                if(e.getClickCount() == 2 && tableInventaire.getSelectedRow() != -1){
                    int row = tableInventaire.rowAtPoint(point);
                    if(getInventairePosition(row) != -1){
                        Inventaire objetModification = inventaireData.get(getInventairePosition(row));
                        ViewModificationInventaire view = new ViewModificationInventaire(objetModification);
                        if(view.estModifier){
                            inventaireData.set(inventaireData.indexOf(objetModification),view.getObjetModification());
                            modelInventaire.setValueAt(view.getObjetModification().getNom(),row,0);
                            modelInventaire.setValueAt(view.getObjetModification().getCategorie(),row,1);
                            modelInventaire.setValueAt(view.getObjetModification().getPrix(),row,2);
                            modelInventaire.setValueAt(view.getObjetModification().getDateAchat(),row,3);
                            modelInventaire.setValueAt(view.getObjetModification().getDescription(),row,4);
                        }
                    }
                }
            }
        });
        tableInventaire.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting() && tableInventaire.getSelectedRow() != -1){
                actualiserTableauEntretien();
            }
        });
        tableInventaire.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) && tableInventaire.getSelectedRow() != -1 ){
                    actualiserTableauEntretien();
                }
            }
        });

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
        tableEntretien.getTableHeader().setReorderingAllowed(false);
        tableEntretien.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollEntretien = new JScrollPane(tableEntretien);
        scrollEntretien.setPreferredSize(new Dimension(300,300));
        tableEntretien.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableEntretien.getColumnModel().getColumn(1).setPreferredWidth(100);


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
        btnFiltrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String filtreTexte = txfFiltrer.getText();
                if(filtreTexte.length() == 0){
                    sorterInventaire.setRowFilter(null);
                }
                else{
                    try{
                        sorterInventaire.setRowFilter(RowFilter.regexFilter(filtreTexte));
                        tableInventaire.getSelectionModel().clearSelection();
                    }catch(PatternSyntaxException pse){
                        JOptionPane.showMessageDialog(null, "Erreur de filtre");
                    }
                }
            }
        });

                //Section boutton d'ajouts
                btnInventairePlus = new JButton("+");
        btnInventairePlus.addActionListener(e -> btnInventairePlusAction());
        btnInventaireMoins = new JButton("-");
        btnInventaireMoins.addActionListener(e -> btnInventaireMoinsAction());

        btnEntretienPlus = new JButton("+");
        btnEntretienPlus.addActionListener(e->btnEntretienPlusAction());
        btnEntretienMoins = new JButton("-");
        btnEntretienMoins.addActionListener(e->btnEntretienMoinsAction());

        //Boutton quitter
        btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> btnQuitterAction());

        //region Interface

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
        //endregion
    }


    private void btnEntretienPlusAction() {
        if(tableInventaire.getSelectedRow() != -1){
            ViewAjoutEntretien viewEntretien = new ViewAjoutEntretien();
            if(viewEntretien.getEntretienEstValide()){
                int positionInventaire = getInventairePosition(tableInventaire.getSelectedRow()); // Position de l'objet sélectionné du tableau inventaire dans inventaireData
                Inventaire objetModification = inventaireData.get(positionInventaire);

                if(!objetModification.getEntretien().containsKey(viewEntretien.getDateEntretien())){
                    objetModification.addEntretien(viewEntretien.getDateEntretien(), viewEntretien.getDescription());
                    inventaireData.set(positionInventaire, objetModification);
                    actualiserTableauEntretien();
                }
            }
        }
    }

    private void actualiserTableauEntretien(){
        modelEntretien.setRowCount(0);
        inventaireData.get(getInventairePosition(tableInventaire.getSelectedRow())).getEntretien().forEach((key, value)->
                modelEntretien.addRow(new Object[] {key, value}
                ));
    }

    private void btnEntretienMoinsAction() {

        if(getInventairePosition(tableInventaire.getSelectedRow()) != -1){
            LocalDate dateEntretien = (LocalDate)modelEntretien.getValueAt(tableEntretien.getSelectedRow(), 0);
            inventaireData.get(getInventairePosition(tableInventaire.getSelectedRow())).getEntretien().remove(dateEntretien);
            modelEntretien.removeRow(tableEntretien.getSelectedRow());

        }
    }

    // Affiche le modal pour ajouter des items dans l'inventaire
    private void btnInventairePlusAction() {
        ViewAjoutInventaire viewInventaire = new ViewAjoutInventaire(); // Création instance du modal
        Inventaire nouvelleObjet = viewInventaire.getNouveauObjet(); //Objet pour inventaire retourné par le modal
        if(nouvelleObjet.getNom() != null){
            inventaireData.add(nouvelleObjet);
            modelInventaire.addRow(new Object[] {nouvelleObjet.getNom(), nouvelleObjet.getCategorie(), nouvelleObjet.getPrix(), nouvelleObjet.getDateAchat(), nouvelleObjet.getDescription()} );
        }
    }

    // Supprime un objet de la l'inventaire
    private void btnInventaireMoinsAction() {

        if(getInventairePosition(tableInventaire.getSelectedRow()) != -1){inventaireData.remove(getInventairePosition(tableInventaire.getSelectedRow()));}
        modelInventaire.removeRow(tableInventaire.getSelectedRow());
    }

    /**
     *
     * @param row Row du tableau où l'on désire obtenir l'objet lié
     * @return position de l'objet dans l'array d'inventaire, retourne -1 si l'objet n'existe pas
     */
    private int getInventairePosition(int row){
        if(row != -1){
            for (Inventaire objetInventaire : inventaireData){
                // Verifier si le nom,prix et date son pareil dans le tableInventaire et l'array inventaire.
                if(
                        objetInventaire.getNom().equals(tableInventaire.getValueAt(row,0)) &&
                                objetInventaire.getPrix() == (Double)tableInventaire.getValueAt(row,2) &&
                                objetInventaire.getDateAchat().equals(tableInventaire.getValueAt(row,3))) {
                    return inventaireData.indexOf(objetInventaire);
                }
            }
        }
        return -1;
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
