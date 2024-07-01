import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visual {

    private static double conversionResult = -1;
    private static double amount;
    private static JLabel resultLabel;

    private static Component createMainPanel() {
        // Main panel where the currency conversion happens
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // From currency label and drop down choice menu
        JLabel fromCur = new JLabel("From Currency", SwingConstants.CENTER);
        JComboBox<String> fromCurChoice = new JComboBox<>(new String[]{"USD", "EUR", "JPY"});
        fromCurChoice.setMaximumSize(new Dimension(100, 25));

        // To currency label and drop down choice menu
        JLabel toCur = new JLabel("To Currency", SwingConstants.CENTER);
        JComboBox<String> toCurChoice = new JComboBox<>(new String[]{"USD", "EUR", "JPY"});
        toCurChoice.setMaximumSize(new Dimension(100, 25));

        // Amount label and input field
        JLabel amountText = new JLabel("Amount", SwingConstants.CENTER);
        JTextField amountInput = new JTextField();
        amountInput.setMaximumSize(new Dimension(100, 25));
        // (maybe limit the amount of chars that can be inputted here)

        // Conversion button and its action listener
        JButton convert = new JButton("Convert");
        convert.setMaximumSize(new Dimension(80, 25));
        convert.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrencyConverter converter = new CurrencyConverter();
                String FromCurrency = (String) fromCurChoice.getSelectedItem();
                String ToCurrency = (String) toCurChoice.getSelectedItem();
                amount = Double.parseDouble(amountInput.getText());
                try {
                    conversionResult = converter.rateConversion(FromCurrency, ToCurrency, amount);
                    resultLabel.setText(String.format("%.2f", conversionResult));
                } catch (Exception ex) {
                    resultLabel.setText("Both currencies are equal / something went wrong");
                }
            }
        });

        // Adding contents to panel and adjusting if needed
        fromCur.setAlignmentX(Component.CENTER_ALIGNMENT);
        toCur.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountText.setAlignmentX(Component.CENTER_ALIGNMENT);
        convert.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(fromCur);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(fromCurChoice);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(toCur);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(toCurChoice);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(amountText);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(amountInput);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(convert);
        return mainPanel;
    }

    private static Component createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
        resultPanel.add(Box.createVerticalStrut(75));
        resultPanel.add(resultLabel);

        return resultPanel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setResizable(false);


        // Base panel which adds the main panel to the center and then the left and right have random stuff
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new GridLayout(1, 3));
        basePanel.add(createMainPanel());
        //basePanel.add(new JLabel("col 2", SwingConstants.CENTER));
        basePanel.add(createResultPanel());

        frame.add(basePanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }


        // Schedule a job for the event-dispatching thread:

        // creating and showing this application’s GUI.
        SwingUtilities.invokeLater(Visual::createAndShowGUI);

    }
}
