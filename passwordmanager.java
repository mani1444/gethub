import java.io.*;
import java.util.*;

public class PasswordManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int passwordLength = 0;
        boolean includeUppercase = false;
        boolean includeLowercase = false;
        boolean includeNumbers = false;
        boolean includeSpecialChars = false;

        System.out.println("Welcome to Password Manager!");

        // Prompt the user for password specifications
        while (passwordLength == 0 || !includeUppercase && !includeLowercase && !includeNumbers && !includeSpecialChars) {
            System.out.print("Enter password length (at least 6 characters): ");
            passwordLength = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

            System.out.print("Include uppercase letters? (y/n): ");
            includeUppercase = scanner.nextLine().equalsIgnoreCase("y");

            System.out.print("Include lowercase letters? (y/n): ");
            includeLowercase = scanner.nextLine().equalsIgnoreCase("y");

            System.out.print("Include numbers? (y/n): ");
            includeNumbers = scanner.nextLine().equalsIgnoreCase("y");

            System.out.print("Include special characters? (y/n): ");
            includeSpecialChars = scanner.nextLine().equalsIgnoreCase("y");

            if (passwordLength < 6 || (!includeUppercase && !includeLowercase && !includeNumbers && !includeSpecialChars)) {
                System.out.println("Invalid input. Please try again.\n");
            }
        }

        // Generate and display the password
        String password = generatePassword(passwordLength, includeUppercase, includeLowercase, includeNumbers, includeSpecialChars);
        System.out.println("Generated Password: " + password);

        // Save the password to a file
        System.out.print("Do you want to save this password to a file? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            savePasswordToFile(password);
        }

        // Check password strength
        System.out.print("Do you want to check the strength of a password? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Enter the password to check its strength: ");
            String passwordToCheck = scanner.nextLine();
            checkPasswordStrength(passwordToCheck);
        }

        scanner.close();
    }

    private static String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                                           boolean includeNumbers, boolean includeSpecialChars) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        StringBuilder validChars = new StringBuilder();
        if (includeUppercase) validChars.append(uppercaseLetters);
        if (includeLowercase) validChars.append(lowercaseLetters);
        if (includeNumbers) validChars.append(numbers);
        if (includeSpecialChars) validChars.append(specialChars);

        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(validChars.length());
            password.append(validChars.charAt(randomIndex));
        }

        return password.toString();
    }

    private static void savePasswordToFile(String password) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the file name to save the password: ");
            String fileName = scanner.nextLine();

            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(password);
            fileWriter.close();

            System.out.println("Password saved to " + fileName + " successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the password to the file.");
        }
    }

    private static void checkPasswordStrength(String password) {
        int score = 0;
        int length = password.length();

        if (length >= 8) score += 2;
        else if (length >= 6) score += 1;

        if (password.matches(".*[A-Z].*")) score += 2;
        if (password.matches(".*[a-z].*")) score += 2;
        if (password.matches(".*\\d.*")) score += 2;
        if (password.matches(".*[^A-Za-z0-9].*")) score += 2;

        System.out.println("Password Strength: " + score + "/10");
    }
}
