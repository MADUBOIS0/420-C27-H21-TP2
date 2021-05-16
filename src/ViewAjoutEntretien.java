import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class ViewAjoutEntretien extends JDialog {
    JDialog dialog; //Frame du modal

    JLabel lblDate;
    JLabel lblDescription;
    JDateChooser dateEntretien;
    JTextArea txaDescription;

    JButton btnAjouter;
    JButton btnAnnuler;

    boolean estValide = false;

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
        JScrollPane scrollPaneDescription = new JScrollPane(txaDescription);


        //region interface
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
        //endregion

    }

    private void btnAjouterAction() {
        if(dateEntretien.getDate() != null && !txaDescription.getText().equals("")){
            estValide = true;
            dialog.dispose();
        }
        else{
            JOptionPane.showMessageDialog(dialog," Erreur de donnée");
        }
    }

    private void btnAnnulerAction() {
        dialog.dispose();
    }

    public boolean getEntretienEstValide(){
        return estValide;
    }

    public LocalDate getDateEntretien(){
        return dateEntretien.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getDescription(){
        return txaDescription.getText();
    }
}
