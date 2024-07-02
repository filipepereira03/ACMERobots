package dados;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CSVReader {
    public List<String[]> fileReader(String nomeArquivoIn) {
        List<String[]> dadosLidos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivoIn))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                dadosLidos.add(values);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não Encontrado");;
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo:" + e.getMessage());
        }

        return dadosLidos;
    }
}
