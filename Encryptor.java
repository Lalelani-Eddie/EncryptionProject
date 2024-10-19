import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encryptor{
    public static void main(String[] args){

        try{
        //VARIABLES AND DECLARATION OF CONSTRUCTORS AND PACKAGES
        Scanner scan = new Scanner(System.in);
        String fileName;

        System.out.println("Enter the name of the text file you want to Encrypt");           
        fileName = scan.nextLine();
        // Generate a secret key for AES encryption
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Key size can be 128, 192, or 256 bits
        SecretKey secretKey = keyGenerator.generateKey();

        // Files for input and output
        FileInputStream inputFile = new FileInputStream(fileName);
        FileOutputStream encryptedFile = new FileOutputStream("encryptedfile.enc");

        // Encrypt the file
        encryptFile("AES", secretKey, inputFile, encryptedFile);
        System.out.println("File Encrypted Successfully!");

        System.out.println("Enter the name of the text file you want to Decrypt");
        fileName = scan.nextLine();

        // Now decrypt the encrypted file
        FileInputStream encryptedInputFile = new FileInputStream(fileName);
        FileOutputStream decryptedFile = new FileOutputStream("decryptedfile.txt");

        decryptFile("AES", secretKey, encryptedInputFile, decryptedFile);
        System.out.println("File Decrypted Successfully!");
        scan.close();

        }   catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to encrypt a file
    public static void encryptFile(String algorithm, SecretKey key, FileInputStream inputFile, FileOutputStream outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] inputBytes = new byte[inputFile.available()];
        inputFile.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        outputFile.write(outputBytes);

        inputFile.close();
        outputFile.close();
    }
    
    // Method to decrypt a file
    public static void decryptFile(String algorithm, SecretKey key, FileInputStream inputFile, FileOutputStream outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] inputBytes = new byte[inputFile.available()];
        inputFile.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        outputFile.write(outputBytes);

        inputFile.close();
        outputFile.close();
    }
}