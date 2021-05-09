import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

public class ViewAjoutInventaire extends JDialog {

    JDialog dialog; //Frame du modal

    JLabel lblNom; //Label du nom
    JLabel lblnumSerie; //Label du numéro de serie
    JLabel lblCategorie; // Label de la catégorie à sélectionner
    JLabel lblPrix; // Label du prix
    JLabel lblDateAchat; // Label de la date d'achat
    JLabel lblDescription; // Label de la description
    JTextField txfNom; // Input pour le nom de l'objet
    JTextField txfNoSerie; // Input pour le numéro de serie
    JTextField txfPrix; // Input pour le prix de l'objet
    JTextArea txaDescription; // Input pour la description de l'objet
    JComboBox<String> cmbCategorie; // Input pour la catégorie de l'objet
    JDateChooser dateAchat; // Input pour la date d'achat

    JButton btnAjouter; // Bouton pour ajouter l'item à la table d'inventaire
    JButton btnAnnuler; // Bouton pour annuler l'ajout d'item

    public ViewAjoutInventaire(){

        //Création des labels
        lblNom = new JLabel("*Nom:");
        lblnumSerie= new JLabel("No série:");
        lblCategorie = new JLabel("Catégorie:");
        lblPrix = new JLabel("*Prix:");
        lblDateAchat = new JLabel("*Date achat:");
        lblDescription = new JLabel("Description:");

        //Création des textfields
        txfNom = new JTextField();
        txfNoSerie = new JTextField();
        txfPrix = new JTextField();
        txaDescription = new JTextArea();

        //Création du combobox
        cmbCategorie = new JComboBox<>(new String[]{"Caméra", "Accessoires", "Jeux", "Véhicule", "Divers"});

        //Création du date picker
        dateAchat = new JDateChooser();

        //Création des boutons
        btnAjouter = new JButton("Ajouter");
        btnAnnuler = new JButton("Annuler");

        // Création scrollPane pour textArea
        JScrollPane scrollPaneDescription = new JScrollPane(txaDescription);

        // Création du modal/frame
        dialog = new JDialog((JDialog)null, "Ajout inventaire", true);
        dialog.setSize(new Dimension(600,310));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Création du panel pour l'inventaire
        GridBagLayout gbl = new GridBagLayout();
        JPanel pnlInventaire = new JPanel(gbl);
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(5,10,0,10);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        pnlInventaire.add(lblNom, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 0;
        pnlInventaire.add(txfNom, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        pnlInventaire.add(lblnumSerie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 1;
        pnlInventaire.add(txfNoSerie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        pnlInventaire.add(lblCategorie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 2;
        pnlInventaire.add(cmbCategorie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 3;
        pnlInventaire.add(lblDateAchat, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 3;
        pnlInventaire.add(dateAchat, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 4;
        pnlInventaire.add(lblDescription, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridwidth = 3;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 4;
        mainConstraints.ipady = 100;
        pnlInventaire.add(scrollPaneDescription, mainConstraints);

        //Création Panel buttons
        JPanel pnlButton = new JPanel(new FlowLayout());
        pnlButton.add(btnAjouter);
        pnlButton.add(btnAnnuler);

        //Création du panel principal
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
        pnlMain.add(pnlInventaire);
        pnlMain.add(pnlButton);

        dialog.add(pnlMain);
        dialog.setVisible(true);
    }

}
