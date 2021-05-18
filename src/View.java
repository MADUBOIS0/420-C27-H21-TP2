/*
  Objectif: Application qui sert à gerer un inventaire, ajouter des entretiens, peut charger des données à partir d'un fichier .dat, créer de nouveaux fichiers et exporter les données en .txt
  Auteur: Marc-Antoine Dubois
  Date: 2021-05-17 Session H2021
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class View extends JFrame {
    JFrame frame; //GUI
    JMenuBar menuBar; //Menu bar
    JMenu menuTP, menuFichier; // Les catégories du menu
    JMenuItem miApropos, miQuitter, miNouveau, miOuvrir, miFermer, miEnregistrer, miEnregistrerSous, miExporter; // Tout les options du menu

    JTable tableInventaire; //Table d'objets inventaire
    JTable tableEntretien; //Table des entretiens effectués aux objets
    DefaultTableModel modelInventaire; // Modèle de la table inventaire
    DefaultTableModel modelEntretien; // Modèle de la table entretien

    JTextField txfFiltrer; //Filtre pour la table inventaire
    JButton btnFiltrer; // Bouton pour appliquer le filtre
    JButton btnInventairePlus; // Bouton pour ouvrir le modal qui sert à ajouter un objet à la table inventaire
    JButton btnInventaireMoins; // Bouton qui permet de supprimer un objet de la table inventaire
    JButton btnEntretienPlus; // Bouton qui permet d'ajouter un entretien à l'objet de la table inventaire sélectionner
    JButton btnEntretienMoins; //Bouton qui permet de supprimer l'entretien sélectionner
    JButton btnQuitter; // Bouton pour quitter le programme

    JFileChooser fc;  // Sert à sélectionner un fichier sur l'ordinateur
    File fichierInventaire; //Contient les informations du fichier sélectionner par fc.
    File fichierExport; //Contient les informations du fichier export

    ArrayList<Inventaire> inventaireData = new ArrayList<>(); // Liste d'objet de type Inventaire, sert à cataloguer tout les objets.

    JScrollPane scrollInventaire; // Sroll pane pour le tableau inventaire
    JScrollPane scrollEntretien; // Sroll pane pour le tableau entretien

    public View(){

        //Création du filechooser
        fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("*.dat", "dat"));
        fc.setAcceptAllFileFilterUsed(false);

        //Création de la table d'inventaire
        String[] colNomInventaire = {"Nom", "Catégorie", "Prix", "Date d'achat", "Description"}; // Colonnes du tableau inventaire
        modelInventaire = new DefaultTableModel(colNomInventaire, 0);

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
        TableRowSorter<DefaultTableModel> sorterInventaire = new TableRowSorter<>(modelInventaire); //Filtre appliquer au tableau inventaire
        tableInventaire.setRowSorter(sorterInventaire);
        // Quand l'utilisateur double click sur une entrée du tableau inventaire, ouvrir le modal de modification
        tableInventaire.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getClickCount() == 2 && tableInventaire.getSelectedRow() != -1){
                    int row = tableInventaire.getSelectedRow();
                    Inventaire objetModification = inventaireData.get(getInventairePosition(row));
                    ViewModificationInventaire view = new ViewModificationInventaire(objetModification);
                    if(view.estModifier){
                        actualiserTableauInventaire();
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

        scrollInventaire = new JScrollPane(tableInventaire);
        scrollInventaire.setPreferredSize(new Dimension(600,300));

        //Création de la table d'entretien
        String[] colNomEntretien = {"Date", "Description"}; // Nom des colonnes du tableau entretien
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

        scrollEntretien = new JScrollPane(tableEntretien);
        scrollEntretien.setPreferredSize(new Dimension(300,300));
        tableEntretien.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableEntretien.getColumnModel().getColumn(1).setPreferredWidth(100);

        //Création du menu TP2
        initialiserMenu();

        //Section filtre
        txfFiltrer = new JTextField();
        txfFiltrer.setPreferredSize(new Dimension(200,20));

        btnFiltrer = new JButton("Filtrer");
        btnFiltrer.addActionListener(e -> {

            String filtreTexte = txfFiltrer.getText();
            if(filtreTexte.length() == 0){
                sorterInventaire.setRowFilter(null);
            }
            else{
                try{
                    sorterInventaire.setRowFilter(RowFilter.regexFilter(filtreTexte));
                    tableInventaire.getSelectionModel().clearSelection();
                }catch(PatternSyntaxException pse){
                    JOptionPane.showMessageDialog(frame, "Erreur de filtre");
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

        //initialiser l'interface
        initialiserInterface();
    }

    /**
     * Nettoie le tableau entretien et le rempli s'il y a lieu avec chacune des entretiens effectués sur l'objet inventaire selectionné
     */
    private void actualiserTableauEntretien(){
        modelEntretien.setRowCount(0);
        inventaireData.get(getInventairePosition(tableInventaire.getSelectedRow())).getEntretien().forEach((key, value)->
                modelEntretien.addRow(new Object[] {key, value}
                ));
    }

    /**
     * Nettoie les tableaux et ajoute chaque objet inventaire d'inventaireData dans le tableau inventaire
     */
    private void actualiserTableauInventaire(){
        modelInventaire.setRowCount(0);
        modelEntretien.setRowCount(0);
        for(Inventaire objet: inventaireData){
            modelInventaire.addRow(new Object[] {objet.getNom(), objet.getCategorie(), objet.getPrix(), objet.getDateAchat(), objet.getDescription()});
        }
    }

    /**
     * Obtient les données du fichier sélectionner et les envoient dans inventaireData
     */
    private void actualiserInventaireData(){
        try{
            // Si la taille du fichierInventaire est égal à 0 nous ne lirons pas les données du fichier
            if(fichierInventaire.length() != 0){
                FileInputStream fis = new FileInputStream(fichierInventaire.getPath()); // Lis les données du fichier à la location préciser
                ObjectInputStream ois = new ObjectInputStream(fis); //Désérialize les données de fis

                //Les données sont envoyés dans inventaireData, si jamais l'utilisateur fourni un mauvais fichier .dat, l'exception ClassCastException sera attraper
                inventaireData = (ArrayList<Inventaire>) ois.readObject();
                ois.close();
                fis.close();
            }
            else{
                modelInventaire.setRowCount(0);
                modelEntretien.setRowCount(0);
                inventaireData.clear();
            }
        }catch(IOException | ClassNotFoundException | ClassCastException e){
            e.printStackTrace();
        }

    }

    /**
     * Sauvegarde inventaireData dans fichierInventaire
     */
    private void sauvegarderInventaireData(){
        try {
            FileOutputStream fos = new FileOutputStream(fichierInventaire.getPath()); // Sert à écrire les données qui lui sont fourni dans le fichier
            ObjectOutputStream oos = new ObjectOutputStream(fos); //Permet l'écriture d'inventaireData dans fichierInventaire
            oos.writeObject(inventaireData);
            oos.close();
            fos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Affiche un pop up demandant à l'utilisateur s'il veut fermer le programme, si un inventaire est actif, demande aussi s'il veut sauvegarder
     */
    private void fermerProgramme(){
        int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter?", "Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)) {

                int resultSave = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder l'inventaire actuel avant de le fermer?", "Sauvegarde",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (resultSave == JOptionPane.YES_OPTION || resultSave == JOptionPane.NO_OPTION) {
                    if (resultSave == JOptionPane.YES_OPTION) {
                        sauvegarderInventaireData();
                    }
                }
            }
            frame.dispose();
        }
    }

    /**
     *
     * @param row Ligne du tableau où l'on désire obtenir l'objet lié
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

    /**
     * Listener pour le bouton Entretien, ouvre le modal pour ajouter un entretien
     */
    private void btnEntretienPlusAction() {
        if(tableInventaire.getSelectedRow() != -1){
            ViewAjoutEntretien viewEntretien = new ViewAjoutEntretien(); //Modal pour ajouter un entretien
            if(viewEntretien.getEntretienEstValide()){
                int positionInventaire = getInventairePosition(tableInventaire.getSelectedRow()); // Position de l'objet sélectionné du tableau inventaire dans inventaireData
                Inventaire objetModification = inventaireData.get(positionInventaire); // L'objet dans inventaireData à la position de la ligne du tableau.

                if(!objetModification.getEntretien().containsKey(viewEntretien.getDateEntretien())){
                    objetModification.addEntretien(viewEntretien.getDateEntretien(), viewEntretien.getDescription());
                    inventaireData.set(positionInventaire, objetModification);
                    actualiserTableauEntretien();
                }
            }
        }
    }

    /**
     * Listener du bouton entretien qui supprime la ligne sélectionner dans le tableau entretien.
     */
    private void btnEntretienMoinsAction() {
        // Verifier si une ligne valide de tableau inventaire et entretien est sélectionner
        if(getInventairePosition(tableInventaire.getSelectedRow()) != -1 && tableEntretien.getSelectedRow() != -1){
            LocalDate dateEntretien = (LocalDate)modelEntretien.getValueAt(tableEntretien.getSelectedRow(), 0); //Date de l'entretien, sert de clé pour LinkedHashMap
            inventaireData.get(getInventairePosition(tableInventaire.getSelectedRow())).removeEntretien(dateEntretien);
            modelEntretien.removeRow(tableEntretien.getSelectedRow());
        }
    }

    /**
     * Listener du bouton qui affiche le modal pour ajouter des items dans l'inventaire
     */
    private void btnInventairePlusAction() {
        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            ViewAjoutInventaire viewInventaire = new ViewAjoutInventaire(); // Création instance du modal ajout à l'inventaire
            Inventaire nouvelleObjet = viewInventaire.getNouveauObjet(); //Objet pour inventaire retourné par le modal
            if(nouvelleObjet.getNom() != null){
                inventaireData.add(nouvelleObjet);
                modelInventaire.addRow(new Object[] {nouvelleObjet.getNom(), nouvelleObjet.getCategorie(), nouvelleObjet.getPrix(), nouvelleObjet.getDateAchat(), nouvelleObjet.getDescription()});
              (tableInventaire.getSelectionModel()).setSelectionInterval(modelInventaire.getRowCount()-1, modelInventaire.getRowCount()-1);
              modelEntretien.setRowCount(0);
            }
        }
    }

    /**
     * Listener du bouton pour supprimer objet de l'inventaire
      */
    private void btnInventaireMoinsAction() {
        if(tableInventaire.getSelectedRow() != -1){
            if(getInventairePosition(tableInventaire.getSelectedRow()) != -1){inventaireData.remove(getInventairePosition(tableInventaire.getSelectedRow()));}
            modelInventaire.removeRow(tableInventaire.getSelectedRow());
        }
    }

    /**
     * Listener du bouton quitter, ferme le programme
     */
    private void btnQuitterAction() {
        fermerProgramme();
    }

    /**
     * Listener menu à propos, affiche un pop up avec l'information du projet
     */
    private void miAproposAction() {
        JOptionPane.showMessageDialog(frame, """
                Travail pratique 2
                Créé par Marc-Antoine Dubois + 1909082
                Session H2021
                Dans le cadre du cours 420-C27""","À propos", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Listener menu quitter, affiche un pop up qui demande à l'utilisateur s'il veut quitter le programme
     */
    private void miQuitterAction() {
        fermerProgramme();
    }

    /**
     * Listener menu nouveau, permet de créer un nouveau fichier pour l'inventaire
     */
    private void miNouveauAction(){
        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder l'inventaire actuel avant de le fermer?", "Sauvegarde",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                sauvegarderInventaireData();
            }
            else if(result == JOptionPane.CANCEL_OPTION){
                return;
            }
        }

        fc.setDialogTitle("Nouveau inventaire");
        int reponse = fc.showSaveDialog(frame);
        if(reponse == JFileChooser.APPROVE_OPTION){

            if(!fc.getSelectedFile().getPath().endsWith("dat")){
                fichierInventaire = new File(fc.getSelectedFile()+".dat");
            }
            else{ fichierInventaire = fc.getSelectedFile(); }

            try{
                fichierInventaire.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
            actualiserInventaireData();
            actualiserTableauInventaire();
            frame.setTitle(fichierInventaire.getName() + " Marc-Antoine Dubois + 1909082");
        }
    }

    /**
     * Listener menu Ouvrir, ouvre un fichier existant.
     */
    private void miOuvrirAction() {

        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder l'inventaire actuel avant de le fermer?", "Sauvegarde",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                sauvegarderInventaireData();
            }
            else if(result == JOptionPane.CANCEL_OPTION){
                return;
            }
        }

        fc.setDialogTitle("Ouverture inventaire");
        int reponse = fc.showOpenDialog(frame);
        if(reponse == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().getName().endsWith("dat")){
            fichierInventaire = fc.getSelectedFile();
            frame.setTitle(fichierInventaire.getName() + " Marc-Antoine Dubois + 1909082");
            actualiserInventaireData();
            actualiserTableauInventaire();
        }
    }

    /**
     * Listener menu Fermer, ferme l'inventaire actif.
     */
    private void miFermerAction() {
        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder l'inventaire actuel avant de le fermer?", "Sauvegarde",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
                if(result == JOptionPane.YES_OPTION){ sauvegarderInventaireData();}
                modelInventaire.setRowCount(0);
                modelEntretien.setRowCount(0);
                inventaireData.clear();
                fichierInventaire = null;
                frame.setTitle("Marc-Antoine Dubois + 1909082");
            }
        }
        else{
            JOptionPane.showMessageDialog(frame, "Aucun inventaire ouvert");
        }
    }

    /**
     * Listener menu Enregistrer, enregistre inventaire si un fichier est sélectionner.
     */
    private void miEnregistrerAction() {
        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            sauvegarderInventaireData();
        }
        else{
            JOptionPane.showMessageDialog(frame, "Aucun inventaire ouvert");
        }
    }

    /**
     * Listener menu Enregistrer sous, enregistre inventaire dans un autre fichier.
     */
    private void miEnregistrerSousAction() {
        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            fc.setDialogTitle("Enregistrer inventaire");
            int reponse = fc.showSaveDialog(frame);

            if(reponse == JFileChooser.APPROVE_OPTION){
                if(!fc.getSelectedFile().getPath().endsWith("dat")){
                    fichierInventaire = new File(fc.getSelectedFile()+".dat");
                }
                else{ fichierInventaire = fc.getSelectedFile();}

                try{
                    fichierInventaire.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
                sauvegarderInventaireData();
                frame.setTitle(fichierInventaire.getName() + " Marc-Antoine Dubois + 1909082");
             }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Aucun inventaire ouvert");
        }
    }

    /**
     * Listener menu exporter, exporte en .txt l'inventaire
     */
    private void miExporterAction() {

        if((fichierInventaire != null && !fichierInventaire.getName().equals("")) || (fichierInventaire != null && fichierInventaire.length() == 0)){
            fc.resetChoosableFileFilters();
            fc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
            fc.setDialogTitle("Exporter inventaire");
            int reponse = fc.showSaveDialog(frame);

            if(reponse == JFileChooser.APPROVE_OPTION){
                if(!fc.getSelectedFile().getPath().endsWith("txt")){
                    fichierExport = new File(fc.getSelectedFile()+".txt");
                }
                else{ fichierExport = fc.getSelectedFile();}

                String text = ""; // Le contenu du fichier à exporter
                for(Inventaire objet : inventaireData){

                    text = text.concat("\n" + objet.getNom() + ", " + objet.getNumSerie() + ", " + objet.getCategorie() + ", " + objet.getPrix() + ", " + objet.getDateAchat() + ", " + objet.getDescription()) + "\n";

                    for (LocalDate key : objet.getEntretien().keySet()){
                        text = text.concat(key + ", " + objet.getEntretien().get(key));
                    }
                    text = text.concat("\n");
                }

                try{
                    fichierExport.createNewFile();
                    FileWriter fw = new FileWriter(fichierExport.getPath());
                    fw.write(text);
                    fw.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            fc.resetChoosableFileFilters();
            fc.setFileFilter(new FileNameExtensionFilter("*.dat", "dat"));
            fichierExport = null;
        }
        else {
            JOptionPane.showMessageDialog(frame, "Aucun inventaire ouvert");
        }
    }

    /**
     * Initialise les éléments du menu
     */
    private void initialiserMenu(){

        menuBar = new JMenuBar();
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
    }

    /**
     * Initialise les éléments de l'interface graphique
     */
    private void initialiserInterface(){
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
        frame = new JFrame("Marc-Antoine Dubois + 1909082");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950,800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(15,10));
        frame.setResizable(false);

        frame.setJMenuBar(menuBar);
        frame.add(pnlNord, BorderLayout.PAGE_START);
        frame.add(pnlCentre, BorderLayout.CENTER);
        frame.add(pnlBas, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        View myView = new View();
    }
}
