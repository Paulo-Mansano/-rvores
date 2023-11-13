import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCreator {
    public static String generateMD5(String input) {
        try {
            // Crie uma instância do MessageDigest com o algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Converta a string de entrada para bytes
            byte[] inputBytes = input.getBytes();

            // Calcule o resumo (hash) dos bytes de entrada
            byte[] hashBytes = md.digest(inputBytes);

            // Converta os bytes do resumo para uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Trate a exceção se o algoritmo MD5 não estiver disponível
            e.printStackTrace();
            return null;
        }
    }
 }
 