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
    private final File commonFile;
    private int check = 0;
    private final List<Object> blacklist;

    /**
     * <b>Create a File with custom methods</b>
     *
     * @param path Which path the file will exists in
     * @param name What name & extension the file will be.
     */
    public GameInsightFile(String path, String name) {
        commonFile = new File(path, name);
        this.blacklist = new ArrayList<>();
        createFile();
    }

    /**
     * <b>Get the File</b>
     *
     * @return The File
     */
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


    /**
     * <b>Writing Objects to The File</b>
     * <p>Converts the element to a String when writing</p>
     *
     * @param element What to write to the file
     * @param unique  if the element is meant to be unique
     */
    public void writeToFile(Object element, boolean unique) {
        if (unique) {
            if (commonFile.length() > 0)
                recreateFile(false);
            try (BufferedReader in = new BufferedReader(new FileReader(commonFile))) {
                for (String line; (line = in.readLine()) != null; )
                    if (line.equals(element))
                        return;
                blacklist.add(element);
                check++;
            } catch (Exception e) {
                throw new RuntimeException("Error reading file: " + commonFile, e);
            }
        } else {
            recreateFile(false);
        }
        try (FileWriter out = new FileWriter(commonFile, true)) {
            out.write(String.valueOf(element));
            System.out.println("wrote: " + element);
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
     * @param line    What line to insert the element
     * @param element What text should be written
     */
    public void writeInline(int line, Object element) {
        if (line < 1) {
            GameInsight.getLOGGER().logp(Level.WARNING, "GameInsightFile.java", "#writeInline(int line, Object element)", "Line can not be less than 1");
            return;
        }
        try {
            List<Object> allLines = new ArrayList<>();

            Scanner scanner = new Scanner(commonFile);
            while (scanner.hasNextLine())
                allLines.add(scanner.nextLine());
            scanner.close();

            if (line > allLines.size())
                for (int i = allLines.size() + 1; i < line; i++)
                    allLines.add("");
            allLines.add(line - 1, element);

            int fileLength = 0;
            List<Integer> position = new ArrayList<>();
            for (Object words : blacklist) {
                for (Object lines : allLines) {
                    if (lines.equals(words))
                        position.add(fileLength);
                    fileLength++;
                }

                if (position.size() > 1) {
                    position.remove(0);

                    for (int positions : position)
                        allLines.remove(positions);
                }

                fileLength = 0;
                position.clear();
            }

            recreateFile(true);

            FileWriter out = new FileWriter(commonFile, true);

            for (Object s : allLines) {
                out.write(String.valueOf(s));
                out.write(System.lineSeparator());
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>Finds Data</b>
     *
     * @param element What to find
     *
     * @return Whether or not the element exists
     */
    public boolean findData(Object element) {
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s.equals(element))
                    return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * <b>Finds Lines from Data</b>
     *
     * @param element What object(s) to find
     *
     * @return All lines where the element exists
     */
    public List<Integer> findLineFromData(Object element) {
        int lineNumber = 0;
        List<Integer> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commonFile)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String s = scanner.nextLine();
                if (s.equals(element))
                    lines.add(lineNumber);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }

    /**
     * <b>Finds Data from Line</b>
     *
     * @param lineNumber What line to return
     *
     * @return The object on the specified line
     */
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

        return null;
    }
    /*------------------------[ End of Constructor ]------------------------*/

    /**
     * <b>Recreate the File</b>
     *
     * @param skipCheck If you wish to force the recreation of the file
     */
    private void recreateFile(boolean skipCheck) {
        if (skipCheck) {
            if (commonFile.delete()) {
                createFile();
            }
        } else {
            if (check == 0)
                if (commonFile.delete()) {
                    createFile();
                    check++;
                }
        }
    }
}
