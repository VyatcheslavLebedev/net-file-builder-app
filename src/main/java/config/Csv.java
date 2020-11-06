package config;

import com.opencsv.CSVWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class Csv {

    @Bean
    public CSVWriter getCsvWriter(FileWriter fileWriter){
        return new CSVWriter(fileWriter,',',CSVWriter.NO_QUOTE_CHARACTER);
    }

    @Bean
    public FileWriter getFileWriter(String filePath) throws IOException {
        return new FileWriter(filePath);
    }

}
