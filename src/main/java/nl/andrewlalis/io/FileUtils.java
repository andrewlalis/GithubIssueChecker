package nl.andrewlalis.io;

import nl.andrewlalis.model.Credentials;
import nl.andrewlalis.model.RepositoriesListFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static final String CHOICE_TOKEN = "token";

    private static final String REPOSITORIES_FILE = "repositories.txt";

    /**
     * @return A credentials object that can be used to log into Github, or null if it could not be obtained.
     */
    public static Credentials readCredentials() {
        File f = new File(CREDENTIALS_FILE);

        // Check if the credentials exist already.
        if (f.exists() && f.isFile()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
                String line = fileReader.readLine();
                if (line.startsWith(CHOICE_TOKEN)) {
                    String[] words = line.split(":");
                    String token = words[1].trim();
                    return new Credentials(token);
                } else {
                    String username = line.split(":")[1].trim();
                    String password = fileReader.readLine().split(":")[1].trim();
                    return new Credentials(username, password);
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + f.getAbsolutePath());
                return null;
            } catch (IOException e) {
                System.err.println("Could not read file: " + f.getAbsolutePath());
                return null;
            }
        } else {
            // Credentials do not exist yet, so create them.
            System.out.println("No credentials file named " + CREDENTIALS_FILE + " was found. Will you log in with an OAuth token[" + CHOICE_TOKEN + "], or username and password[username]?");

            try (BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in))) {
                String input = inReader.readLine();

                if (input.equalsIgnoreCase(CHOICE_TOKEN)) {
                    System.out.println("Enter your OAuth token.");
                    String token = inReader.readLine();

                    BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                    writer.write("token:" + token);
                    writer.newLine();
                    writer.close();

                    return new Credentials(token);
                } else {
                    System.out.println("Enter your username.");
                    String username = inReader.readLine();
                    System.out.println("Enter your password.");
                    String password = inReader.readLine();

                    BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                    writer.write("username:" + username);
                    writer.newLine();
                    writer.write("password:" + password);
                    writer.newLine();
                    writer.close();

                    return new Credentials(username, password);
                }
            } catch (IOException e) {
                System.err.println("Could not read input from System.in!");
                return null;
            }
        }
    }

    public static RepositoriesListFile readRepositoryNames() {
        File f = new File(REPOSITORIES_FILE);

        if (f.exists() && f.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

                String line = reader.readLine();
                String organizationName = line.split(":")[1].trim();
                List<String> repositoryNames = new ArrayList<>();
                int lineNumber = 2;
                while ((line = reader.readLine()) != null) {
                    String cleanedName = line.trim();
                    if (!cleanedName.isEmpty()) {
                        repositoryNames.add(cleanedName);
                    } else {
                        System.err.println("Line " + lineNumber + " is empty string.");
                    }
                    lineNumber++;
                }

                String[] namesArray = new String[repositoryNames.size()];
                repositoryNames.toArray(namesArray);

                return new RepositoriesListFile(organizationName, namesArray);
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + f.getAbsolutePath());
                return null;
            } catch (IOException e) {
                System.err.println("Could not read from file: " + f.getAbsolutePath());
                return null;
            }
        } else {
            System.out.println("No repositories file named " + REPOSITORIES_FILE + " found.");
            System.out.println("What is the name of the organization the repositories are in?");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String organizationName = reader.readLine();

                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write("organization:" + organizationName);
                writer.newLine();

                System.out.println("How many repositories do you want to keep track of?");
                int repositoryCount = Integer.parseInt(reader.readLine().trim());

                String[] repositoryNames = new String[repositoryCount];
                for (int i = 0; i < repositoryCount; i++) {
                    System.out.println("Enter the name of repository #" + i + ".");
                    repositoryNames[i] = reader.readLine();

                    writer.write(repositoryNames[i]);
                    writer.newLine();
                }

                writer.close();

                return new RepositoriesListFile(organizationName, repositoryNames);
            } catch (IOException e) {
                System.err.println("Could not read from input.");
                return null;
            }
        }
    }

}
