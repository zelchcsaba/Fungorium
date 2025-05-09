package view;
import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartScreenPanel extends JPanel implements ActionListener{
    private MainWindow parent;
    Controller controller;
    JTextField fPlayerCount;
    JTextField iPlayerCount;
    JTextField maxRounds;
    JButton nextButton;

    public StartScreenPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        JLabel titleLabel = new JLabel("Fungorium");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel fLabel = new JLabel("Number of fungus-players:");
        JLabel iLabel = new JLabel("Number of insect-players:");
        JLabel rLabel = new JLabel("Number of maximum rounds:");

        fPlayerCount = new JTextField(10);
        iPlayerCount = new JTextField(10);
        maxRounds = new JTextField(10);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        this.add(nextButton);
       

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(fLabel)
                        .addComponent(iLabel)
                        .addComponent(rLabel))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(fPlayerCount)
                        .addComponent(iPlayerCount)
                        .addComponent(maxRounds)))
                .addGroup(layout.createSequentialGroup()
                    .addGap(200)
                    .addComponent(nextButton))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fLabel)
                    .addComponent(fPlayerCount))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(iLabel)
                    .addComponent(iPlayerCount))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(rLabel)
                    .addComponent(maxRounds))
                .addGap(20)
                .addComponent(nextButton)
        );

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == nextButton){
             try {
                int fCount = Integer.parseInt(fPlayerCount.getText());
                int iCount = Integer.parseInt(iPlayerCount.getText());
                int maxRound = Integer.parseInt(maxRounds.getText());

                controller.setFungusPlayerCount(fCount);
                controller.setInsectPlayerCount(iCount);
                controller.setMaxRound(maxRound);
                parent.showNameEntryPanel();

                
               
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
    }
        
}
