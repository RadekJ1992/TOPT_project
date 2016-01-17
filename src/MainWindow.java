import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * created on 22:40:27 16 sty 2016 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public class MainWindow implements ActionListener {

    public static final String SHORTEST = "SHORTEST";
    public static final String LONGEST = "LONGEST";
    public static final String RANDOM = "RANDOM";
    public static final String PER_NODE = "PER_NODE";
    public static final String PER_NETWORK = "PER_NETWORK";
    public static final String START = "START";
    private JFrame frmSymulaorSieciTopt;
    private JTextField numberOfPacketsField;
    private JTextField ttlField;
    private JTextField bufferSizeField;
    private JTextField networkSizeField;
    private JTextField timeUnitsField;
    private JTextArea logArea;
    private JButton startButton;
    private Task task;

    public JFrame getFrame() {
        return frmSymulaorSieciTopt;
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSymulaorSieciTopt = new JFrame();
        frmSymulaorSieciTopt.setTitle("Symulator sieci TOPT, Jarzynka, Owczarek, styczeń 2016");
        frmSymulaorSieciTopt.setResizable(false);
        frmSymulaorSieciTopt.setBounds(100, 100, 658, 404);
        frmSymulaorSieciTopt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSymulaorSieciTopt.getContentPane().setLayout(null);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        settingsPanel.setBounds(6, 6, 378, 200);
        frmSymulaorSieciTopt.getContentPane().add(settingsPanel);
        settingsPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ilość testowanych jednostek czasu");
        lblNewLabel.setBounds(6, 6, 219, 16);
        settingsPanel.add(lblNewLabel);

        JLabel lblWymiarSieci = new JLabel("Wymiar sieci (NxN)");
        lblWymiarSieci.setBounds(6, 34, 219, 16);
        settingsPanel.add(lblWymiarSieci);

        JLabel lblRozmiarBuforaW = new JLabel("Rozmiar bufora w węźle");
        lblRozmiarBuforaW.setBounds(6, 62, 219, 16);
        settingsPanel.add(lblRozmiarBuforaW);

        JLabel lblCzasPrzeterminowaniaPakietu = new JLabel("Czas przeterminowania pakietu");
        lblCzasPrzeterminowaniaPakietu.setBounds(6, 90, 219, 16);
        settingsPanel.add(lblCzasPrzeterminowaniaPakietu);

        JLabel lblIlocPakietwGenerowanych = new JLabel("Ilość pakietów generowanych w jednostce czasu");
        lblIlocPakietwGenerowanych.setBounds(6, 117, 308, 16);
        settingsPanel.add(lblIlocPakietwGenerowanych);

        JRadioButton eachNodeRadio = new JRadioButton("W każdym węźle");
        eachNodeRadio.setSelected(true);
        eachNodeRadio.setBounds(207, 145, 141, 23);
        settingsPanel.add(eachNodeRadio);
        eachNodeRadio.setActionCommand(PER_NODE);
        eachNodeRadio.addActionListener(this);

        JRadioButton networkRadio = new JRadioButton("Ogólnie w sieci");
        networkRadio.setBounds(207, 169, 141, 23);
        settingsPanel.add(networkRadio);
        networkRadio.setActionCommand(PER_NETWORK);
        networkRadio.addActionListener(this);

        ButtonGroup packetsRadioGroup = new ButtonGroup();
        packetsRadioGroup.add(eachNodeRadio);
        packetsRadioGroup.add(networkRadio);

        numberOfPacketsField = new JTextField();
        numberOfPacketsField.setText("2");
        numberOfPacketsField.setBounds(326, 111, 46, 28);
        settingsPanel.add(numberOfPacketsField);
        numberOfPacketsField.setColumns(10);
        ((PlainDocument) numberOfPacketsField.getDocument()).setDocumentFilter(new MyIntFilter());
        // Listen for changes in the text
        numberOfPacketsField.getDocument().addDocumentListener(new SettingsDocumentListener(numberOfPacketsField, SETTINGS_TYPE.PACKETS_NUMBER));

        ttlField = new JTextField();
        ttlField.setText("10");
        ttlField.setColumns(10);
        ttlField.setBounds(326, 84, 46, 28);
        settingsPanel.add(ttlField);
        ((PlainDocument) ttlField.getDocument()).setDocumentFilter(new MyIntFilter());
        ttlField.getDocument().addDocumentListener(new SettingsDocumentListener(ttlField, SETTINGS_TYPE.TTL));

        bufferSizeField = new JTextField();
        bufferSizeField.setText("10");
        bufferSizeField.setColumns(10);
        bufferSizeField.setBounds(326, 56, 46, 28);
        settingsPanel.add(bufferSizeField);
        ((PlainDocument) bufferSizeField.getDocument()).setDocumentFilter(new MyIntFilter());
        bufferSizeField.getDocument().addDocumentListener(new SettingsDocumentListener(bufferSizeField, SETTINGS_TYPE.BUFFER_SIZE));

        networkSizeField = new JTextField();
        networkSizeField.setText("7");
        networkSizeField.setColumns(10);
        networkSizeField.setBounds(326, 28, 46, 28);
        settingsPanel.add(networkSizeField);
        ((PlainDocument) networkSizeField.getDocument()).setDocumentFilter(new MyIntFilter());
        networkSizeField.getDocument().addDocumentListener(new SettingsDocumentListener(networkSizeField, SETTINGS_TYPE.NETWORK_SIZE));

        timeUnitsField = new JTextField();
        timeUnitsField.setText("500");
        timeUnitsField.setColumns(10);
        timeUnitsField.setBounds(326, 0, 46, 28);
        settingsPanel.add(timeUnitsField);
        ((PlainDocument) timeUnitsField.getDocument()).setDocumentFilter(new MyIntFilter());
        timeUnitsField.getDocument().addDocumentListener(new SettingsDocumentListener(timeUnitsField, SETTINGS_TYPE.NUMBER_OF_TIME_UNITS));

        JPanel algorithmPanel = new JPanel();
        algorithmPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        algorithmPanel.setBounds(397, 6, 255, 145);
        frmSymulaorSieciTopt.getContentPane().add(algorithmPanel);
        algorithmPanel.setLayout(null);

        JLabel lblWybranyAlgorytmRoutingu = new JLabel("Wybrany algorytm routingu");
        lblWybranyAlgorytmRoutingu.setBounds(6, 6, 197, 16);
        algorithmPanel.add(lblWybranyAlgorytmRoutingu);

        JRadioButton shortestPathRadio = new JRadioButton("Najkrótsza ścieżka (Dijkstra)");
        shortestPathRadio.setSelected(true);
        shortestPathRadio.setBounds(16, 36, 233, 23);
        algorithmPanel.add(shortestPathRadio);
        shortestPathRadio.setActionCommand(SHORTEST);
        shortestPathRadio.addActionListener(this);

        JRadioButton longestPathRadio = new JRadioButton("Najdłuższa ścieżka");
        longestPathRadio.setBounds(16, 71, 233, 23);
        algorithmPanel.add(longestPathRadio);
        longestPathRadio.setActionCommand(LONGEST);
        longestPathRadio.addActionListener(this);

        JRadioButton randomPathRadio = new JRadioButton("Losowa ścieżka");
        randomPathRadio.setBounds(16, 106, 233, 23);
        algorithmPanel.add(randomPathRadio);
        randomPathRadio.setActionCommand(RANDOM);
        randomPathRadio.addActionListener(this);

        ButtonGroup pathRadioGroup = new ButtonGroup();
        pathRadioGroup.add(shortestPathRadio);
        pathRadioGroup.add(longestPathRadio);
        pathRadioGroup.add(randomPathRadio);

        startButton = new JButton("Start");
        startButton.setActionCommand(START);
        startButton.addActionListener(this);
        startButton.setBounds(396, 163, 256, 43);
        frmSymulaorSieciTopt.getContentPane().add(startButton);

        JPanel logPanel = new JPanel();
        logPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        logPanel.setBounds(6, 206, 646, 170);
        frmSymulaorSieciTopt.getContentPane().add(logPanel);
        logPanel.setLayout(null);

        logArea = new JTextArea();
        logArea.setLineWrap(true);
        logArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(6, 6, 634, 158);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        logPanel.add(scrollPane);
    }



    class MyIntFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
        }

        private boolean test(String text) {
            if (text.isEmpty()) {
                return true;
            }
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // warn the user and don't allow the insert
            }

        }
    }
    
    private enum SETTINGS_TYPE {
        NUMBER_OF_TIME_UNITS,
        NETWORK_SIZE,
        BUFFER_SIZE,
        TTL,
        PACKETS_NUMBER;
    }

    private class SettingsDocumentListener implements DocumentListener {

        private SETTINGS_TYPE type;
        private JTextField field;

        @Override
        public void insertUpdate(DocumentEvent e) {
            changeSettings();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changeSettings();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changeSettings();
        }

        private void changeSettings() {
            String valueStr = field.getText();
            try {
                Integer value = 0;
                if (valueStr == null || valueStr.isEmpty()) {
                    value = 0;
                } else {
                    value = Integer.valueOf(valueStr);
                }
                switch (type) {
                    case NUMBER_OF_TIME_UNITS:
                        Settings.setTestedTimeUnits(value);
                        break;
                    case NETWORK_SIZE:
                        Settings.setNetworkSize(value);
                        break;
                    case BUFFER_SIZE:
                        Settings.setBufferSize(value+1);
                        break;
                    case TTL:
                        Settings.setTtl(value);
                        break;
                    case PACKETS_NUMBER:
                        Settings.setGeneratedPackets(value);
                        break;
                }
            } catch (NumberFormatException e) {
            }
        }
        
        public SettingsDocumentListener(JTextField field, SETTINGS_TYPE type) {
            this.field = field;
            this.type = type;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case PER_NETWORK:
            Settings.setPerOneNode(false);
            break;
        case PER_NODE:
            Settings.setPerOneNode(true);
            break;
        case LONGEST:
            Settings.setCurrentAlgorithm(Settings.LONGEST);
            break;
        case RANDOM:
            Settings.setCurrentAlgorithm(Settings.RANDOM);
            break;
        case SHORTEST:
            Settings.setCurrentAlgorithm(Settings.DIJKSTRA);
            break;
        case START:
            startButton.setEnabled(false);
            frmSymulaorSieciTopt.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task = new Task();
            task.execute();
            break;
        }
    }
    
    class Task extends SwingWorker<Void, Void> {
        private Network network;
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            network = new Network();
            logArea.append("Tworzenie sieci o wymiarach " + Settings.getNetworkSize() + " x " + Settings.getNetworkSize() + "\n");
            network.generateNetwork(Settings.getNetworkSize());
            logArea.append("Sieć została utworzona\n");
            
            for (int i = 0; i< Settings.getTestedTimeUnits(); i++) {
                network.generatePackets(Settings.getGeneratedPackets(), Settings.isPerOneNode());
                network.transferPackets(false);
            }
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            startButton.setEnabled(true);
            frmSymulaorSieciTopt.setCursor(null); //turn off the wait cursor
            logArea.append("Dostarczone Pakiety: " + network.getDeliveredPackets() + "\n");
            logArea.append("Średnia ilość przeskoków między węzłami: " + network.getAverageHops() + "\n");
            logArea.append("Średnie opóźnienie pakietu: " + network.getAverageDelay() + "\n");
            logArea.append("Wariancja opóźnienia: " + network.getDelayVariance() + "\n");
            logArea.append("Utracone pakiety: " + network.getLostPackets() + "\n");
        }
    }
}
