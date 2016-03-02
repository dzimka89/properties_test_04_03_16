package com.io_properties_test;



import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;


public class TestOperatingProperties {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("");
        File propertyFile = new File(path.toAbsolutePath() + "/properties.txt");
        Properties properties = System.getProperties();
        File dataFile = new File(path.toAbsolutePath() + "/" + properties.getProperty("user.name") + ".txt");
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }

        String[] propertiesArray = getPropertiesFromFile(propertyFile);

        String[] propertiesVariables = getConfigValues(propertiesArray);

        createFileWithValues(dataFile, propertiesVariables);

        System.out.println(Arrays.toString(propertiesArray));
        System.out.println(Arrays.toString(propertiesVariables));


    }

    private static void createFileWithValues(File dataFile, String[] propertiesVariables) throws IOException {
        FileWriter fileWriter = new FileWriter(dataFile, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String s : propertiesVariables) {
            bufferedWriter.write(s + "\n");
            bufferedWriter.flush();
        }
    }

    private static String[] getConfigValues(String[] propertiesArray) throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = TestOperatingProperties.class.getClassLoader().getResourceAsStream("configFiles/config.properties");
        properties.load(resourceAsStream);

        String[] propertiesVariables = new String[propertiesArray.length];

        int i = 0;
        for (String s : propertiesArray) {
            propertiesVariables[i] = s + " is " + properties.getProperty(s, "not available");
            ++i;
        }
        return propertiesVariables;
    }

    private static String[] getPropertiesFromFile(File propertyFile) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(propertyFile);
        int wordsNumberInFile = 0;
        Scanner scan = new Scanner(propertyFile);

        do {
            scan.nextLine();
            ++wordsNumberInFile;
        } while (scan.hasNext());
        scan.close();

        String[] propertiesArray = new String[wordsNumberInFile];
        Scanner newScan = new Scanner(fileInputStream);
        for (int i = 0; i < wordsNumberInFile; i++) {
            propertiesArray[i] = newScan.nextLine();
        }
        newScan.close();
        return propertiesArray;
    }


}
