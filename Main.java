import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public static void LeArquivo(String nome, DoubleLinkedListOfHeaders L)
    {
        L.Imprime("Arquivo:" + nome + "*\n");
        Path path1 = Paths.get(nome);

        //try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
        try (BufferedReader reader = Files.newBufferedReader(path1, StandardCharsets.UTF_8)) {
            String line = null;

            int cont = 0;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '-')
                    L.Remove(line.substring(1, line.length()));
                else L.addIncreasingOrder(line);
                cont++;
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
    }
    public static void main(String[] args) {

        //System.out.println("Mudado novamente 7");

        DoubleLinkedListOfHeaders L = new DoubleLinkedListOfHeaders();

        L.Imprime("\n\n\n\n\n\n\n\n");

        if (args.length == 1)
        {
            LeArquivo(args[0], L);
        }
        else {
            L.Imprime("Linha de Comando Invalida."+args.length+"\n");
            L.Imprime("Digite java Main NomeArquivo.txt\n");
            return;
        }
       L.ImprimeLista();

       L.GeraDOT();
    }
}