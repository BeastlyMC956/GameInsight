package me.beastlymc.gameinsight.file;

import java.io.*;
import java.util.Scanner;

/**
 * <b>A Custom File Creation Class</b>
 *
 * @author BeastlyMC956
 */
public class GameInsightFile {
    File commonFile;
    int element = 0;

    public GameInsightFile(String path, String name) {
        commonFile = new File(path, name);
        createFile();
    }

    public File toFile() {
        return commonFile;
    }

    /**
     * <b>Creates the File</b>
     * <p>Creates a file and checks if the directory & file exists</p>
     */
    public void createFile() {

        File dir = new File(commonFile.getParentFile().getPath());

        if (dir.exists())
            System.out.println(dir.getAbsolutePath() + " already exists");
        else {
            boolean success = dir.mkdir();

            if (success)
                System.out.println(dir.getAbsolutePath() + " successfully created");
            else
                System.out.println(dir.getAbsolutePath() + " not created");
        }
        File file = commonFile;

        if (file.exists())
            System.out.println(file.getName() + " already exists");

        else {
            boolean success = false;
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (success)
                System.out.println(file.getName() + " successfully created");
            else
                System.out.println(file.getName() + " not created");
        }
    }

    public void writeToFile(String index, boolean unique) {
        if (unique) {
            if (commonFile.length() > 0)
                recreateFile();
            try (BufferedReader in = new BufferedReader(new FileReader(commonFile))) {
                for (String line; (line = in.readLine()) != null; )
                    if (line.equals(index))
                        return;
            } catch (Exception e) {
                throw new RuntimeException("Error reading file: " + commonFile, e);
            }
        } else
            recreateFile();
        try (FileWriter out = new FileWriter(commonFile, true)) {
            out.write(index);
            out.write(System.lineSeparator());
        } catch (Exception e) {
            throw new RuntimeException("Error appending to file: " + commonFile, e);
        }
    }

    public boolean findData(Object obj) {
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s.equals(obj))
                    return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int findLineFromData(Object obj) {
        int lineNumber = 0;
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String s = scanner.nextLine();
                if (s.equals(obj))
                    return lineNumber;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lineNumber;
    }

    public Object findDataFromLine(int lineNumber) {
        if(lineNumber <= 0)
            lineNumber = 1;
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if(lineNumber != 1) {
                    lineNumber--;
                    continue;
                }
                    return s;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "N/A";
    }
    /*------------------------[ End of Constructor ]------------------------*/

    private void recreateFile() {
        if (element == 0)
            if (commonFile.delete()) {
                createFile();
                element++;
            }
    }
}
