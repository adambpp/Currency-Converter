import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Visual {

    private static double conversionResult = -1;
    private static double amount;
    private static JLabel resultLabel1;
    private static JLabel resultLabel2;
    public static JScrollPane resultScrollPane;
    static CurrencyConverter converter = new CurrencyConverter();
    private static final String[] curList = converter.getCurrencies().toArray(new String[0]);
    static {
        Arrays.sort(curList);
    }

    private static Component createMainPanel() {
        // Main panel where the currency conversion happens
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // From currency label and drop down choice menu
        JLabel fromCur = new JLabel("From Currency", SwingConstants.CENTER);
        JComboBox<String> fromCurChoice = new JComboBox<>(curList);
        fromCurChoice.setMaximumSize(new Dimension(175, 25));

        // To currency label and drop down choice menu
        JLabel toCur = new JLabel("To Currency", SwingConstants.CENTER);
        JComboBox<String> toCurChoice = new JComboBox<>(curList);
        toCurChoice.setMaximumSize(new Dimension(175, 25));

        // Amount label and input field
        JLabel amountText = new JLabel("Amount", SwingConstants.CENTER);
        JTextField amountInput = new JTextField();
        amountInput.setMaximumSize(new Dimension(175, 25));
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

                String FromChoice = (String) fromCurChoice.getSelectedItem();
                String FromCurrency = converter.getCurrencyIdByName(FromChoice);
                String ToChoice = (String) toCurChoice.getSelectedItem();
                String ToCurrency = converter.getCurrencyIdByName(ToChoice);
                amount = Double.parseDouble(amountInput.getText());
                try {
                    conversionResult = converter.rateConversion(FromCurrency, ToCurrency, amount);
                    resultLabel1.setText(String.format("%s%.0f %s to %s is", // change the num to only show like 4 numbers and then ... (4000...)
                            converter.getCurrencySymbolByCurrencyId(FromCurrency), amount,
                            converter.getCurrencyIdByName(FromChoice),
                            converter.getCurrencyIdByName(ToChoice)));

                    resultLabel2.setText(String.format("%s%.2f", converter.getCurrencySymbolByCurrencyId(ToCurrency), conversionResult));
                    // check size of resultLabel2
                } catch (IllegalStateException ex) {
                    resultLabel1.setText("Try again: Both currencies are equal");
                    resultLabel2.setText("");
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

        resultLabel1 = new JLabel(" ", SwingConstants.CENTER);
        resultLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel1.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));

        resultLabel2 = new JLabel(" ", SwingConstants.CENTER);
        resultLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel2.setFont(new Font("Monospaced", Font.PLAIN, 30));
        resultScrollPane.add(resultLabel2);


        resultPanel.add(Box.createVerticalStrut(75));
        resultPanel.add(resultLabel1);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(resultLabel2);
        resultPanel.add(resultScrollPane);
        resultScrollPane.setVisible(false);
        // add a JScrollPane and only make it visible if resultLabel2 cannot fully fit on the screen
        // I think any more than 8 characters (not counting the 2 decimals) will cause it to be too long

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
