package pt.ipleiria.estg.dei.ei.dae.projetodae.data;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class CSVImporter<Product> {

    public List<Product> importManyFromCSV(String csv, Class<Product> clazz) {
        try (Reader reader = new StringReader(csv)) {
            CsvToBean<Product> csvReader = new CsvToBeanBuilder<Product>(reader)
                    .withType(clazz)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSkipLines(1)
                    .build();

            return csvReader.parse();
        } catch (Exception e) {
            throw new IllegalStateException("CSV improperly formatted: " + e.getMessage());
        }
    }
}
