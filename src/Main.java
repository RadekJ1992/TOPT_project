import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Main{
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void mainOld(String[] args) throws IOException {
        
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Witamy w symulatorze sieci o topologii Manhattan");
        System.out.println("Autorzy - Radosław Jarzynka & Krzysztof Owczarek, PW WEiTI 2015");
        while (true) {
            System.out.println("Wybierz tryb działania aplikacji:");
            System.out.println("(1) Sprawdzanie sieci w zależności od ilości pakietów generowanych w jednostce czasu");
            System.out.println("(2) Sprawdzanie sieci w zależności od głębokości bufora w węźle");
            System.out.println("(3) Sprawdzanie sieci w zależności od czasu przeterminowania pakietu");
            System.out.println("(4) Wyjście z aplikacji");
            String chosenMode = bufferRead.readLine();
            if ("1".equals(chosenMode)) {
                Settings.setCurrentMode(1);
                runPacketsMode(bufferRead);
            } else if ("2".equals(chosenMode)) {
                Settings.setCurrentMode(2);
                runBufferMode(bufferRead);
            } else if ("3".equals(chosenMode)) {
                Settings.setCurrentMode(3);
                runTtlMode(bufferRead);
            } else if ("4".equals(chosenMode)) {
                System.exit(0);
            }
        }
    }
    
    public static void runPacketsMode(BufferedReader bufferRead) throws IOException {
        System.out.println("Podaj wymiar testowanej sieci (N):");
        System.out.println("Uwaga, symulacja sieci o N większych od kilkunastu trwa bardzo długo");
        String networkSizeString = bufferRead.readLine();
        Integer networkSize = 0;
        while (networkSize == 0) {
            try {
                networkSize = Integer.valueOf(networkSizeString);
                if (networkSize <= 0) {
                    networkSize = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    networkSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                networkSizeString = bufferRead.readLine();
            }
        }
        Settings.setNetworkSize(networkSize);
        System.out.println("Wymiar testowanej sieci: " + networkSize + " x " + networkSize);

        System.out.println("Podaj ilość testowanych jednostek czasu (np. 500) :");
        String timeUnitsString = bufferRead.readLine();
        Integer timeUnits = 0;
        while (timeUnits == 0) {
            try {
                timeUnits = Integer.valueOf(timeUnitsString);
                if (timeUnits <= 0) {
                    timeUnits = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    timeUnitsString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                timeUnitsString = bufferRead.readLine();
            }
        }
        Settings.setTestedTimeUnits(timeUnits);
        System.out.println("Ilość testowanych jednostek czasu: " + timeUnits);
        
        System.out.println("Podaj rozmiar bufora w węźle:");
        String bufferSizeString = bufferRead.readLine();
        Integer bufferSize = -1;
        while (bufferSize == -1) {
            try {
                bufferSize = Integer.valueOf(bufferSizeString);
                if (bufferSize <= -1) {
                    bufferSize = -1;
                    System.out.println("Wprowadź wartość większą od 0");
                    bufferSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                bufferSizeString = bufferRead.readLine();
            }
        }
        if (bufferSize == 0) {
            bufferSize = 1;
        }
        Settings.setBufferSize(bufferSize);
        System.out.println("Rozmiar bufora: " + bufferSize);
        
        System.out.println("Podaj czas życia pakietu (Time To Live):");
        String ttlString = bufferRead.readLine();
        Integer ttl = 0;
        while (ttl == 0) {
            try {
                ttl = Integer.valueOf(ttlString);
                if (ttl <= 0) {
                    ttl = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    ttlString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                ttlString = bufferRead.readLine();
            }
        }
        Settings.setTtl(ttl);
        System.out.println("Time To Live Pakietu: " + ttl);
        
        System.out.println("Podaj minimalną ilość generowanych pakietów na jednostkę czasu:");
        String minString = bufferRead.readLine();
        Integer min = 0;
        while (min == 0) {
            try {
                min = Integer.valueOf(minString);
                if (min <= 0) {
                    min = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    minString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                minString = bufferRead.readLine();
            }
        }
        Settings.setGeneratedPacketsFrom(min);
        System.out.println("Minimalna ilość generowanych pakietów: " + min);
        
        System.out.println("Podaj maksymalną ilość generowanych pakietów na jednostkę czasu:");
        String maxString = bufferRead.readLine();
        Integer max = 0;
        while (max == 0) {
            try {
                max = Integer.valueOf(maxString);
                if (max <= 0 || max < Settings.getGeneratedPacketsFrom()) {
                    max = 0;
                    System.out.println("Wprowadź wartość większą od " + Settings.getGeneratedPacketsFrom());
                    maxString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od " + Settings.getGeneratedPacketsFrom());
                maxString = bufferRead.readLine();
            }
        }
        Settings.setGeneratedPacketsTo(max);
        System.out.println("Maksymalna ilość generowanych pakietów: " + max);
        
        System.out.println("Podaj krok, z jakim ma się zmieniać ilość generowanych pakietów na jednostkę czasu:");
        String stepString = bufferRead.readLine();
        Integer step = 0;
        while (step == 0) {
            try {
                step = Integer.valueOf(stepString);
                if (step <= 0) {
                    step = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    stepString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                stepString = bufferRead.readLine();
            }
        }
        Settings.setGeneratedPacketsStep(step);
        System.out.println("Krok, z jakim zmienia się ilość generowanych pakietów to: " + step);
        
        System.out.println("Wybierz algorytm routingu:");
        System.out.println("(1) Najkrótsza ścieżka (Dijkstra)");
        System.out.println("(2) Najdłuższa ścieżka");
        System.out.println("(3) Losowa ścieżka");
        
        String chosenAlgorithm = bufferRead.readLine();
        
        boolean algorithmChosen = false;
        while (!algorithmChosen) {            
            switch (chosenAlgorithm) {
            case "1":
                Settings.setCurrentAlgorithm(Settings.DIJKSTRA);
                algorithmChosen = true;
                break;
            case "2":
                Settings.setCurrentAlgorithm(Settings.LONGEST);
                algorithmChosen = true;
                break;
            case "3":
                Settings.setCurrentAlgorithm(Settings.RANDOM);
                algorithmChosen = true;
                break;
            default:
                System.out.println("Wybierz algorytm (wpisz 1, 2 lub 3)");
                chosenAlgorithm = bufferRead.readLine();
                break;
            }
        }
        
        Network network = new Network();
        network.generateNetwork(Settings.getNetworkSize());
        
        final XYSeries deliveredPacketsSeries = new XYSeries("Pakiety");
        final XYSeries averageHopsSeries = new XYSeries("hopy");
        final XYSeries delaySeries = new XYSeries("opóźnienie");
        final XYSeries delayVarianceSeries = new XYSeries("wariancja opóźnienia");
        final XYSeries lostPacketsSeries = new XYSeries("utracone");
        //Iterujemy po ilości generowanych pakietów
        for (int generatedPackets = Settings.getGeneratedPacketsFrom(); 
                generatedPackets <= Settings.getGeneratedPacketsTo(); 
                generatedPackets += Settings.getGeneratedPacketsStep()) {
            System.out.println("Symuluję generowanie " + generatedPackets + " pakietów w jednostce czasu");
            for (int i = 0; i< Settings.getTestedTimeUnits(); i++) {
                network.generatePackets(generatedPackets, true);
                network.transferPackets(false);
            }
            System.out.println();
            System.out.println("Sprawdzanie generowania " + generatedPackets + " pakietów w jednostce czasu:");
            System.out.println("Dostarczone Pakiety: " + network.getDeliveredPackets());
            deliveredPacketsSeries.add(generatedPackets, network.getDeliveredPackets());
            System.out.println("Średnia ilość przeskoków między węzłami: " + network.getAverageHops());
            averageHopsSeries.add(generatedPackets, network.getAverageHops());
            System.out.println("Średnie opóźnienie pakietu: " + network.getAverageDelay());
            delaySeries.add(generatedPackets, network.getAverageDelay());
            System.out.println("Wariancja opóźnienia: " + network.getDelayVariance());
            delayVarianceSeries.add(generatedPackets, network.getDelayVariance());
            System.out.println("Utracone pakiety: " + network.getLostPackets());
            lostPacketsSeries.add(generatedPackets, network.getLostPackets());
            }
        
        final XYSeriesCollection deliveredDataset = new XYSeriesCollection();
        deliveredDataset.addSeries(deliveredPacketsSeries);
        final SummaryChart deliveredChart = new SummaryChart("Ilość dostarczonych pakietów", 
                deliveredDataset,
                "Dostarczone pakiety",
                "Ilość pakietów generowanych w jednej jednostce czasu",
                "Dostarczone pakiety");
        deliveredChart.pack();
        RefineryUtilities.centerFrameOnScreen(deliveredChart);
        deliveredChart.setVisible(true);
        
        final XYSeriesCollection averageHopsDataset = new XYSeriesCollection();
        averageHopsDataset.addSeries(averageHopsSeries);
        final SummaryChart hopsChart = new SummaryChart("Średnia ilość przeskoków między węzłami", 
                averageHopsDataset,
                "Ilość przeskoków",
                "Ilość pakietów generowanych w jednej jednostce czasu",
                "Ilość przeskoków");
        hopsChart.pack();
        RefineryUtilities.centerFrameOnScreen(hopsChart);
        hopsChart.setVisible(true);
        
        final XYSeriesCollection averageDelayDataset = new XYSeriesCollection();
        averageDelayDataset.addSeries(delaySeries);
        final SummaryChart delayChart = new SummaryChart("Średnie opóźnienie pakietu", 
                averageDelayDataset,
                "Opóźnienie",
                "Ilość pakietów generowanych w jednej jednostce czasu",
                "Opóźnienie");
        delayChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayChart);
        delayChart.setVisible(true);
        
        final XYSeriesCollection averageDelayVarianceDataset = new XYSeriesCollection();
        averageDelayVarianceDataset.addSeries(delayVarianceSeries);
        final SummaryChart delayVarianceChart = new SummaryChart("Wariancja opóźnienia pakietów", 
                averageDelayVarianceDataset,
                "Wariancja opóźnienia",
                "Ilość pakietów generowanych w jednej jednostce czasu",
                "Wariancja opóźnienia");
        delayVarianceChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayVarianceChart);
        delayVarianceChart.setVisible(true);
        
        final XYSeriesCollection lostPacketsDataset = new XYSeriesCollection();
        lostPacketsDataset.addSeries(lostPacketsSeries);
        final SummaryChart lostChart = new SummaryChart("Utracone pakiety", 
                lostPacketsDataset,
                "Ilość utraconych pakietów",
                "Ilość pakietów generowanych w jednej jednostce czasu",
                "Utracone pakiety");
        lostChart.pack();
        RefineryUtilities.centerFrameOnScreen(lostChart);
        lostChart.setVisible(true);
    }
    
    public static void runBufferMode(BufferedReader bufferRead) throws IOException {
        System.out.println("Podaj wymiar testowanej sieci (N):");
        System.out.println("Uwaga, symulacja sieci o N większych od kilkunastu trwa bardzo długo");
        String networkSizeString = bufferRead.readLine();
        Integer networkSize = 0;
        while (networkSize == 0) {
            try {
                networkSize = Integer.valueOf(networkSizeString);
                if (networkSize <= 0) {
                    networkSize = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    networkSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                networkSizeString = bufferRead.readLine();
            }
        }
        Settings.setNetworkSize(networkSize);
        System.out.println("Wymiar testowanej sieci: " + networkSize + " x " + networkSize);

        System.out.println("Podaj ilość testowanych jednostek czasu (np. 500) :");
        String timeUnitsString = bufferRead.readLine();
        Integer timeUnits = 0;
        while (timeUnits == 0) {
            try {
                timeUnits = Integer.valueOf(timeUnitsString);
                if (timeUnits <= 0) {
                    timeUnits = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    timeUnitsString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                timeUnitsString = bufferRead.readLine();
            }
        }
        Settings.setTestedTimeUnits(timeUnits);
        System.out.println("Ilość testowanych jednostek czasu: " + timeUnits);
        
        System.out.println("Podaj ilość pakietów generowanych w jednostce czasu:");
        String generatedPacketsString = bufferRead.readLine();
        Integer generatedPackets = 0;
        while (generatedPackets == 0) {
            try {
                generatedPackets = Integer.valueOf(generatedPacketsString);
                if (generatedPackets <= 0) {
                    generatedPackets = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    generatedPacketsString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                generatedPacketsString = bufferRead.readLine();
            }
        }
        Settings.setGeneratedPackets(generatedPackets);
        System.out.println("Ilość pakietów generowanych w jednostce czasu: " + generatedPackets);
        
        System.out.println("Podaj czas życia pakietu (Time To Live):");
        String ttlString = bufferRead.readLine();
        Integer ttl = 0;
        while (ttl == 0) {
            try {
                ttl = Integer.valueOf(ttlString);
                if (ttl <= 0) {
                    ttl = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    ttlString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                ttlString = bufferRead.readLine();
            }
        }
        Settings.setTtl(ttl);
        System.out.println("Time To Live Pakietu: " + ttl);
        
        System.out.println("Podaj minimalną wielkość bufora:");
        String minString = bufferRead.readLine();
        Integer min = 0;
        while (min == 0) {
            try {
                min = Integer.valueOf(minString);
                if (min <= 0) {
                    min = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    minString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                minString = bufferRead.readLine();
            }
        }
        if (min == 0) {
            min = 1;
        }
        Settings.setBufferSizeFrom(min);
        System.out.println("Minimalna wielkość bufora: " + min);
        
        System.out.println("Podaj maksymalną wielkość bufora:");
        String maxString = bufferRead.readLine();
        Integer max = -1;
        while (max == -1) {
            try {
                max = Integer.valueOf(maxString);
                if (max <= -1 || max < Settings.getBufferSizeFrom()) {
                    max = -1;
                    System.out.println("Wprowadź wartość większą od " + Settings.getBufferSizeFrom());
                    maxString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od " + Settings.getBufferSizeFrom());
                maxString = bufferRead.readLine();
            }
        }
        Settings.setBufferSizeTo(max);
        System.out.println("Maksymalna wielkość bufora: " + max);
        
        System.out.println("Podaj krok, z jakim ma się zmieniać wielkość bufora:");
        String stepString = bufferRead.readLine();
        Integer step = 0;
        while (step == 0) {
            try {
                step = Integer.valueOf(stepString);
                if (step <= 0) {
                    step = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    stepString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                stepString = bufferRead.readLine();
            }
        }
        Settings.setBufferSizeStep(step);
        System.out.println("Krok, z jakim zmienia się wielkość bufora to: " + step);
        
        System.out.println("Wybierz algorytm routingu:");
        System.out.println("(1) Najkrótsza ścieżka (Dijkstra)");
        System.out.println("(2) Najdłuższa ścieżka");
        System.out.println("(3) Losowa ścieżka");
        
        String chosenAlgorithm = bufferRead.readLine();
        
        boolean algorithmChosen = false;
        while (!algorithmChosen) {            
            switch (chosenAlgorithm) {
            case "1":
                Settings.setCurrentAlgorithm(Settings.DIJKSTRA);
                algorithmChosen = true;
                break;
            case "2":
                Settings.setCurrentAlgorithm(Settings.LONGEST);
                algorithmChosen = true;
                break;
            case "3":
                Settings.setCurrentAlgorithm(Settings.RANDOM);
                algorithmChosen = true;
                break;
            default:
                System.out.println("Wybierz algorytm (wpisz 1, 2 lub 3)");
                chosenAlgorithm = bufferRead.readLine();
                break;
            }
        }

        final XYSeries deliveredPacketsSeries = new XYSeries("Pakiety");
        final XYSeries averageHopsSeries = new XYSeries("hopy");
        final XYSeries delaySeries = new XYSeries("opóźnienie");
        final XYSeries delayVarianceSeries = new XYSeries("wariancja opóźnienia");
        final XYSeries lostPacketsSeries = new XYSeries("utracone");
        
        for (int bufferSize = Settings.getBufferSizeFrom(); 
                bufferSize <= Settings.getBufferSizeTo();
                bufferSize += Settings.getBufferSizeStep()) {
            System.out.println();
            System.out.println("Sprawdzanie bufora o rozmiarze " + bufferSize + ":");
            Settings.setBufferSize(bufferSize);
            Network network = new Network();
            network.generateNetwork(Settings.getNetworkSize());
            for (int i = 0; i< Settings.getTestedTimeUnits(); i++) {
                network.generatePackets(generatedPackets, true);
                network.transferPackets(false);
            }
            System.out.println("Dostarczone Pakiety: " + network.getDeliveredPackets());
            deliveredPacketsSeries.add(bufferSize, network.getDeliveredPackets());
            System.out.println("Średnia ilość przeskoków między węzłami: " + network.getAverageHops());
            averageHopsSeries.add(bufferSize, network.getAverageHops());
            System.out.println("Średnie opóźnienie pakietu: " + network.getAverageDelay());
            delaySeries.add(bufferSize, network.getAverageDelay());
            System.out.println("Wariancja opóźnienia: " + network.getDelayVariance());
            delayVarianceSeries.add(bufferSize, network.getDelayVariance());
            System.out.println("Utracone pakiety: " + network.getLostPackets());
            lostPacketsSeries.add(bufferSize, network.getLostPackets());
        }
        final XYSeriesCollection deliveredDataset = new XYSeriesCollection();
        deliveredDataset.addSeries(deliveredPacketsSeries);
        final SummaryChart deliveredChart = new SummaryChart("Ilość dostarczonych pakietów", 
                deliveredDataset,
                "Dostarczone pakiety",
                "Wielkość bufora",
                "Dostarczone pakiety");
        deliveredChart.pack();
        RefineryUtilities.centerFrameOnScreen(deliveredChart);
        deliveredChart.setVisible(true);
        
        final XYSeriesCollection averageHopsDataset = new XYSeriesCollection();
        averageHopsDataset.addSeries(averageHopsSeries);
        final SummaryChart hopsChart = new SummaryChart("Średnia ilość przeskoków między węzłami", 
                averageHopsDataset,
                "Ilość przeskoków",
                "Wielkość bufora",
                "Ilość przeskoków");
        hopsChart.pack();
        RefineryUtilities.centerFrameOnScreen(hopsChart);
        hopsChart.setVisible(true);
        
        final XYSeriesCollection averageDelayDataset = new XYSeriesCollection();
        averageDelayDataset.addSeries(delaySeries);
        final SummaryChart delayChart = new SummaryChart("Średnie opóźnienie pakietu", 
                averageDelayDataset,
                "Opóźnienie",
                "Wielkość bufora",
                "Opóźnienie");
        delayChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayChart);
        delayChart.setVisible(true);
        
        final XYSeriesCollection averageDelayVarianceDataset = new XYSeriesCollection();
        averageDelayVarianceDataset.addSeries(delayVarianceSeries);
        final SummaryChart delayVarianceChart = new SummaryChart("Wariancja opóźnienia pakietów", 
                averageDelayVarianceDataset,
                "Wariancja opóźnienia",
                "Wielkość bufora",
                "Wariancja opóźnienia");
        delayVarianceChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayVarianceChart);
        delayVarianceChart.setVisible(true);
        
        final XYSeriesCollection lostPacketsDataset = new XYSeriesCollection();
        lostPacketsDataset.addSeries(lostPacketsSeries);
        final SummaryChart lostChart = new SummaryChart("Utracone pakiety", 
                lostPacketsDataset,
                "Ilość utraconych pakietów",
                "Wielkość bufora",
                "Utracone pakiety");
        lostChart.pack();
        RefineryUtilities.centerFrameOnScreen(lostChart);
        lostChart.setVisible(true);
    }
    

    public static void runTtlMode(BufferedReader bufferRead) throws IOException {
        System.out.println("Podaj wymiar testowanej sieci (N):");
        System.out.println("Uwaga, symulacja sieci o N większych od kilkunastu trwa bardzo długo");
        String networkSizeString = bufferRead.readLine();
        Integer networkSize = 0;
        while (networkSize == 0) {
            try {
                networkSize = Integer.valueOf(networkSizeString);
                if (networkSize <= 0) {
                    networkSize = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    networkSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                networkSizeString = bufferRead.readLine();
            }
        }
        Settings.setNetworkSize(networkSize);
        System.out.println("Wymiar testowanej sieci: " + networkSize + " x " + networkSize);

        System.out.println("Podaj ilość testowanych jednostek czasu (np. 500) :");
        String timeUnitsString = bufferRead.readLine();
        Integer timeUnits = 0;
        while (timeUnits == 0) {
            try {
                timeUnits = Integer.valueOf(timeUnitsString);
                if (timeUnits <= 0) {
                    timeUnits = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    timeUnitsString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                timeUnitsString = bufferRead.readLine();
            }
        }
        Settings.setTestedTimeUnits(timeUnits);
        System.out.println("Ilość testowanych jednostek czasu: " + timeUnits);
        
        System.out.println("Podaj ilość pakietów generowanych w jednostce czasu:");
        String generatedPacketsString = bufferRead.readLine();
        Integer generatedPackets = 0;
        while (generatedPackets == 0) {
            try {
                generatedPackets = Integer.valueOf(generatedPacketsString);
                if (generatedPackets <= 0) {
                    generatedPackets = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    generatedPacketsString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                generatedPacketsString = bufferRead.readLine();
            }
        }
        Settings.setGeneratedPackets(generatedPackets);
        System.out.println("Ilość pakietów generowanych w jednostce czasu: " + generatedPackets);
        
        System.out.println("Podaj rozmiar bufora w węźle:");
        String bufferSizeString = bufferRead.readLine();
        Integer bufferSize = -1;
        while (bufferSize == -1) {
            try {
                bufferSize = Integer.valueOf(bufferSizeString);
                if (bufferSize < 0) {
                    bufferSize = -1;
                    System.out.println("Wprowadź wartość większą od 0");
                    bufferSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                bufferSizeString = bufferRead.readLine();
            }
        }
        if (bufferSize == 0) {
            bufferSize = 1;
        }
        Settings.setBufferSize(bufferSize);
        System.out.println("Rozmiar bufora: " + bufferSize);

        System.out.println("Podaj minimalny czas przeterminowania pakietu:");
        String minString = bufferRead.readLine();
        Integer min = 0;
        while (min == 0) {
            try {
                min = Integer.valueOf(minString);
                if (min <= 0) {
                    min = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    minString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                minString = bufferRead.readLine();
            }
        }
        Settings.setTtlFrom(min);
        System.out.println("Minimalny czas przeterminowania pakietu: " + min);
        
        System.out.println("Podaj maksymalny czas przeterminowania pakiet:");
        String maxString = bufferRead.readLine();
        Integer max = 0;
        while (max == 0) {
            try {
                max = Integer.valueOf(maxString);
                if (max <= 0 || max < Settings.getTtlFrom()) {
                    max = 0;
                    System.out.println("Wprowadź wartość większą od " + Settings.getTtlFrom());
                    maxString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od " + Settings.getTtlFrom());
                maxString = bufferRead.readLine();
            }
        }
        Settings.setTtlTo(max);
        System.out.println("Maksymalny czas przeterminowania pakiet: " + max);
        
        System.out.println("Podaj krok, z jakim ma się zmieniać czas przeterminowania pakietu:");
        String stepString = bufferRead.readLine();
        Integer step = 0;
        while (step == 0) {
            try {
                step = Integer.valueOf(stepString);
                if (step <= 0) {
                    step = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    stepString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                stepString = bufferRead.readLine();
            }
        }
        Settings.setTtlStep(step);
        System.out.println("Krok, z jakim zmienia się czas przeterminowania pakietu to: " + step);
        
        System.out.println("Wybierz algorytm routingu:");
        System.out.println("(1) Najkrótsza ścieżka (Dijkstra)");
        System.out.println("(2) Najdłuższa ścieżka");
        System.out.println("(3) Losowa ścieżka");
        
        String chosenAlgorithm = bufferRead.readLine();
        
        boolean algorithmChosen = false;
        while (!algorithmChosen) {            
            switch (chosenAlgorithm) {
            case "1":
                Settings.setCurrentAlgorithm(Settings.DIJKSTRA);
                algorithmChosen = true;
                break;
            case "2":
                Settings.setCurrentAlgorithm(Settings.LONGEST);
                algorithmChosen = true;
                break;
            case "3":
                Settings.setCurrentAlgorithm(Settings.RANDOM);
                algorithmChosen = true;
                break;
            default:
                System.out.println("Wybierz algorytm (wpisz 1, 2 lub 3)");
                chosenAlgorithm = bufferRead.readLine();
                break;
            }
        }

        final XYSeries deliveredPacketsSeries = new XYSeries("Pakiety");
        final XYSeries averageHopsSeries = new XYSeries("hopy");
        final XYSeries delaySeries = new XYSeries("opóźnienie");
        final XYSeries delayVarianceSeries = new XYSeries("wariancja opóźnienia");
        final XYSeries lostPacketsSeries = new XYSeries("utracone");
        
        for (int ttl = Settings.getTtlFrom(); 
                ttl <= Settings.getTtlTo();
                ttl += Settings.getTtlStep()) {
            System.out.println();
            System.out.println("Sprawdzanie czasu przeterminowania pakietu wynoszączego:" + ttl + ":");
            Settings.setTtl(ttl);
            Network network = new Network();
            network.generateNetwork(Settings.getNetworkSize());
            for (int i = 0; i< Settings.getTestedTimeUnits(); i++) {
                network.generatePackets(generatedPackets, true);
                network.transferPackets(false);
            }
            System.out.println("Dostarczone Pakiety: " + network.getDeliveredPackets());
            deliveredPacketsSeries.add(ttl, network.getDeliveredPackets());
            System.out.println("Średnia ilość przeskoków między węzłami: " + network.getAverageHops());
            averageHopsSeries.add(ttl, network.getAverageHops());
            System.out.println("Średnie opóźnienie pakietu: " + network.getAverageDelay());
            delaySeries.add(ttl, network.getAverageDelay());
            System.out.println("Wariancja opóźnienia: " + network.getDelayVariance());
            delayVarianceSeries.add(ttl, network.getDelayVariance());
            System.out.println("Utracone pakiety: " + network.getLostPackets());
            lostPacketsSeries.add(ttl, network.getLostPackets());
        }
        final XYSeriesCollection deliveredDataset = new XYSeriesCollection();
        deliveredDataset.addSeries(deliveredPacketsSeries);
        final SummaryChart deliveredChart = new SummaryChart("Ilość dostarczonych pakietów", 
                deliveredDataset,
                "Dostarczone pakiety",
                "Czas przeterminowania pakietu",
                "Dostarczone pakiety");
        deliveredChart.pack();
        RefineryUtilities.centerFrameOnScreen(deliveredChart);
        deliveredChart.setVisible(true);
        
        final XYSeriesCollection averageHopsDataset = new XYSeriesCollection();
        averageHopsDataset.addSeries(averageHopsSeries);
        final SummaryChart hopsChart = new SummaryChart("Średnia ilość przeskoków między węzłami", 
                averageHopsDataset,
                "Ilość przeskoków",
                "Czas przeterminowania pakietu",
                "Ilość przeskoków");
        hopsChart.pack();
        RefineryUtilities.centerFrameOnScreen(hopsChart);
        hopsChart.setVisible(true);
        
        final XYSeriesCollection averageDelayDataset = new XYSeriesCollection();
        averageDelayDataset.addSeries(delaySeries);
        final SummaryChart delayChart = new SummaryChart("Średnie opóźnienie pakietu", 
                averageDelayDataset,
                "Opóźnienie",
                "Czas przeterminowania pakietu",
                "Opóźnienie");
        delayChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayChart);
        delayChart.setVisible(true);
        
        final XYSeriesCollection averageDelayVarianceDataset = new XYSeriesCollection();
        averageDelayVarianceDataset.addSeries(delayVarianceSeries);
        final SummaryChart delayVarianceChart = new SummaryChart("Wariancja opóźnienia pakietów", 
                averageDelayVarianceDataset,
                "Wariancja opóźnienia",
                "Czas przeterminowania pakietu",
                "Wariancja opóźnienia");
        delayVarianceChart.pack();
        RefineryUtilities.centerFrameOnScreen(delayVarianceChart);
        delayVarianceChart.setVisible(true);
        
        final XYSeriesCollection lostPacketsDataset = new XYSeriesCollection();
        lostPacketsDataset.addSeries(lostPacketsSeries);
        final SummaryChart lostChart = new SummaryChart("Utracone pakiety", 
                lostPacketsDataset,
                "Ilość utraconych pakietów",
                "Czas przeterminowania pakietu",
                "Utracone pakiety");
        lostChart.pack();
        RefineryUtilities.centerFrameOnScreen(lostChart);
        lostChart.setVisible(true);
    }
    
}
