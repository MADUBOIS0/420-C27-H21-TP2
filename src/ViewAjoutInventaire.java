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
    JTextField txfNom;
    JTextField txfNoSerie;
    JTextField txfPrix;
    JTextField txfDescription;
    JComboBox<String> cmbCategorie;
    JDateChooser dateAchat;




    JButton btnAjouter; // Bouton pour ajouter l'item à la table d'inventaire
    JButton btnAnnuler; // Bouton pour annuler l'ajout d'item



    public ViewAjoutInventaire(){

        //Création des labels

        //Création des inputs

        //Création des boutons


        // Création du modal/frame
        dialog = new JDialog((JDialog)null, "Ajout inventaire", true);
        dialog.setSize(new Dimension(500,500));
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Création du panel pour l'inventaire
        JPanel pnlInventaire = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(5,10,5,10);
        //mainConstraints.ipady = 5;


    }

}
