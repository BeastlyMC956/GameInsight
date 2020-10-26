package me.beastlymc.gameinsight.file;

import me.beastlymc.gameinsight.GameInsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

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

    public void writeToFile(Object element, boolean unique) {
        if (unique) {
            if (commonFile.length() > 0)
                recreateFile(false);
            try (BufferedReader in = new BufferedReader(new FileReader(commonFile))) {
                for (String line; (line = in.readLine()) != null; )
                    if (line.equals(element))
                        return;
            } catch (Exception e) {
                throw new RuntimeException("Error reading file: " + commonFile, e);
            }
        } else
            recreateFile(false);
        try (FileWriter out = new FileWriter(commonFile, true)) {
            out.write(String.valueOf(element));
            out.write(System.lineSeparator());
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException("Error appending to file: " + commonFile, e);
        }
    }


    /**
     * <b>Write Text to a Line</b>
     * <p>Writes a specific String in to a specific line</p>
     *
     * @param line What line to insert the element
     * @param element What text should be written
     */
    public void writeInline(int line, Object element) {
        if(line < 1) {
            GameInsight.getLOGGER().logp(Level.WARNING,"GameInsightFile.java","#writeInline(int line, Object element)","Line can not be less than 1");
            return;
        }
        try {
            List<Object> allLines = new ArrayList<>();

            Scanner scanner = new Scanner(commonFile);
            while(scanner.hasNextLine())
                allLines.add(scanner.nextLine());
            scanner.close();

            if(line > allLines.size())
                for (int i = allLines.size()+1; i < line; i++)
                    allLines.add("");
            allLines.add(line - 1, element);

            recreateFile(true);

            FileWriter out = new FileWriter(commonFile, true);
            for (Object s : allLines) {
                out.write(String.valueOf(s));
                out.write(System.lineSeparator());
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        if (lineNumber <= 0)
            lineNumber = 1;
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (lineNumber != 1) {
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

    private void recreateFile(boolean skipCheck) {
        if (skipCheck)
            if (commonFile.delete()) {
                System.out.println("recreated before");
                createFile();
                return;
            }
        if (element == 0)
            if (commonFile.delete()) {
                System.out.println("recreated after");
                createFile();
                element++;
            }
    }
}
