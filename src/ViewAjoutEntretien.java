/*
Objectif: Modal qui sert à ajouter un un entretien
  Auteur: Marc-Antoine Dubois
  Date: 2021-05-17 Session H2021
 */

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class ViewAjoutEntretien extends JDialog {
    JDialog dialog; //Frame du modal

    JLabel lblDate; // Label de la date
    JLabel lblDescription; // Label de la description
    JDateChooser dateEntretien; // Date Chooser pour la date d'entretien
    JTextArea txaDescription; // TextArea pour la description
    JScrollPane scrollPaneDescription; // Scroll pane qui permet de scroll dans la description

    JButton btnAjouter; // Bouton qui sert à ajouter un entretien
    JButton btnAnnuler; // Bouton qui sert à annuler l'ajout

    boolean estValide = false; // Si l'entretien contient une description et date valide, sera vrai

    public ViewAjoutEntretien(){

        //Création labels
        lblDate = new JLabel("*Date:");
        lblDescription = new JLabel("*Description:");

        //Création des boutons
        btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e-> btnAjouterAction());
        btnAnnuler= new JButton("Annuler");
        btnAnnuler.addActionListener(e-> btnAnnulerAction());

        //Création date
        dateEntretien = new JDateChooser();
        dateEntretien.setDateFormatString("dd MMM yyyy");

        //Création textarea
        txaDescription = new JTextArea();
        scrollPaneDescription = new JScrollPane(txaDescription);

        //Création de l'interface
        initialiserInterface();
    }

    /**
     * Listener pour le bouton ajouter, ajoute l'entretien
     */
    private void btnAjouterAction() {
        if(dateEntretien.getDate() != null && !txaDescription.getText().equals("")){
            estValide = true;
            dialog.dispose();
        }
        else{
            JOptionPane.showMessageDialog(dialog," Erreur de donnée");
        }
    }

    /**
     * Listener pour le bouton annuler, annule l'ajout et ferme le modal
     */
    private void btnAnnulerAction() {
        dialog.dispose();
    }

    /**
     * Retourne le status de l'entretien
     * @return Si l'entretien ne contient pas une date et une description, retournera faux
     */
    public boolean getEntretienEstValide(){
        return estValide;
    }

    /**
     * Retourne la date de l'entretien
     * @return date d'entretien en LocalDate
     */
    public LocalDate getDateEntretien(){
        return dateEntretien.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Retourne la description
     * @return description en String
     */
    public String getDescription(){
        return txaDescription.getText();
    }

    /**
     * Initialise l'interface
     */
    private void initialiserInterface(){
        JPanel pnlEntretien = new JPanel(new GridBagLayout());
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(5,10,0,10);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        pnlEntretien.add(lblDate, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 0;
        pnlEntretien.add(dateEntretien, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        pnlEntretien.add(lblDescription, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridwidth = 3;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 1;
        mainConstraints.ipady = 200;
        pnlEntretien.add(scrollPaneDescription, mainConstraints);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlButtons.add(btnAjouter);
        pnlButtons.add(btnAnnuler);

        JPanel pnlCentre = new JPanel(new BorderLayout());
        pnlCentre.add(pnlEntretien, BorderLayout.CENTER);
        pnlCentre.add(pnlButtons, BorderLayout.SOUTH);

        // Création du modal/frame
        dialog = new JDialog((JDialog)null, "Ajout entretien", true);
        dialog.setSize(new Dimension(500,350));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dialog.add(pnlCentre);
        dialog.setVisible(true);
    }
}
