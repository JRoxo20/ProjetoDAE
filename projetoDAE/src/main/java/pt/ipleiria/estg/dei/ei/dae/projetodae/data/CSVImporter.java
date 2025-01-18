package pt.ipleiria.estg.dei.ei.dae.projetodae.data;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter<T1> {
    public List<T1> importManyFromCSV(String csv, T1 aRecord) {
        try {

            Reader reader = new StringReader(csv);
            CsvToBean<T1> csvReader = new CsvToBeanBuilder(reader)
                    .withType(aRecord.getClass())
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            List<T1> results = csvReader.parse();
            return results;
        } catch (Exception e) {
            throw new IllegalStateException("CSV improperly formatted");
        }
    }

}
