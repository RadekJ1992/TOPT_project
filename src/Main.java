import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) throws IOException {
        
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Witamy w symulatorze sieci o topologii Manhattan");
        System.out.println("Autorzy - Radosław Jarzynka & Krzysztof Owczarek, PW WEiTI 2015");
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
        
        System.out.println("Podaj rozmiar bufora w węźle:");
        String bufferSizeString = bufferRead.readLine();
        Integer bufferSize = 0;
        while (bufferSize == 0) {
            try {
                bufferSize = Integer.valueOf(bufferSizeString);
                if (bufferSize <= 0) {
                    bufferSize = 0;
                    System.out.println("Wprowadź wartość większą od 0");
                    bufferSizeString = bufferRead.readLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę większą od 0");
                bufferSizeString = bufferRead.readLine();
            }
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
        
        Network network = new Network();
        network.generateNetwork(Settings.getNetworkSize());//DO WATKOW???
        //Iterujemy po ilości generowanych pakietów
        for (int generatedPackets = Settings.getGeneratedPacketsFrom(); 
                generatedPackets <= Settings.getGeneratedPacketsTo(); 
                generatedPackets += Settings.getGeneratedPacketsStep()) {
            System.out.println("Testing " + generatedPackets + " packets per one time unit");
            //Robimy 500 "jednostek czasu"
            String[] elements = {"////", "||||", "\\\\\\\\", "||||"};
            for (int i = 0; i< 500; i++) {
                network.generatePackets(generatedPackets);
                network.transferPackets();
                System.out.print(elements[i%4] + "\r");
            }
            System.out.println();
            System.out.println(generatedPackets + " packets per one time unit result:");
            System.out.println("Delivered Packets: " + network.getDeliveredPackets());
            System.out.println("Average Hops: " + network.getAverageHops());
            System.out.println("Delay variance: " + network.getDelayVariance());
            System.out.println("Lost Packets: " + network.getLostPackets());
        }
    }
}
