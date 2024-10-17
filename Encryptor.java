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

        //VARIABLES AND DECLARATION OF CONSTRUCTORS AND PACKAGES
        Scanner scan = new Scanner(System.in);
        String fileName;
        System.out.println("Choose a service:Type 1 For Decryption or Type 2 For Encryption");
        int input = scan.nextInt();

        switch (input) {
            case 1: //Decryption section
                System.out.println("You Have Selected The Option To Decrypt The File");
                System.out.println("Enter the name of the text file you want to Encrypt");                
                fileName = scan.nextLine();
                System.out.println("Enter the name of the text file you want to Encrypt");
                SecretKey secretKey = null;
                try {

                    // Now decrypt the encrypted file
                    FileInputStream encryptedInputFile = new FileInputStream(fileName);
                    FileOutputStream decryptedFile = new FileOutputStream("decryptedfile.txt");

                    decryptFile("AES", secretKey, encryptedInputFile, decryptedFile);
                    System.out.println("File Decrypted Successfully!");
                }
                 catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
                    e.printStackTrace();
                }
                 catch (Exception e) {
                    System.out.println("There was an errors in the Reading a file");
                }
                break;
            
            case 2: //Encryption section
                System.out.println("You Have Selected The Option To Encrypt The File");
                fileName = scan.nextLine();
                try {
                    // Generate a secret key for AES encryption
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(128); // Key size can be 128, 192, or 256 bits
                    secretKey = keyGenerator.generateKey();

                    // Files for input and output
                    FileInputStream inputFile = new FileInputStream(fileName);
                    FileOutputStream encryptedFile = new FileOutputStream("encryptedfile.enc");

                    // Encrypt the file
                    encryptFile("AES", secretKey, inputFile, encryptedFile);
                    System.out.println("File Encrypted Successfully!");
                } 
                catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    System.out.println("There was an errors in the Encrypting  a file");
                }
                break;
        
            default:
                System.out.println("You Did Not Select Any Valid Options");
                break;
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