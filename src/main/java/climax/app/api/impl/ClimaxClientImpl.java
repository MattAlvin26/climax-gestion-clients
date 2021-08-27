package climax.app.api.impl;

import climax.app.api.IClimaxClient;
import climax.app.database.entity.ClientEntity;
import climax.app.database.repository.ClientRepository;
import climax.app.model.ClientDto;
import climax.app.model.ClientDtoParse;
import com.github.dozermapper.core.Mapper;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Slf4j
public class ClimaxClientImpl implements IClimaxClient {

    //public static String[] TYPE={"text/csv","text/xml","text/json"};


    private final ClientRepository clientRepository;

    private final Mapper mapper;
    @Override
    public List<ClientDto> doFetchClient() {
        return this.clientRepository.findAll().stream()
                .map(t->mapper.map(t,ClientDto.class)).collect(Collectors.toList());
    }

    @Override
    public ClientDto doGetClientById(Integer idClient) {
        Optional<ClientEntity> client=this.clientRepository.findById(idClient);
        if(client.isPresent()){
        return mapper.map(client.get(),ClientDto.class);
        }
        return null;
    }

    public  String getFileFormat(MultipartFile file){

        return file.getContentType();
    }

    public List<ClientDtoParse> doGetFilesParsed(InputStream is){
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,"UTF-8"))) {
            BeanListProcessor<ClientDtoParse> rowProcessor = new BeanListProcessor<ClientDtoParse>(ClientDtoParse.class);
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            // skip leading whitespaces
            settings.setIgnoreLeadingWhitespaces(true);

            // skip trailing whitespaces
            settings.setIgnoreTrailingWhitespaces(true);
            // skip empty lines
            settings.getFormat().setDelimiter(';');
            settings.setSkipEmptyLines(true);
            settings.setProcessor(rowProcessor);
            CsvParser parser = new CsvParser(settings);
            parser.parse(fileReader);
            return rowProcessor.getBeans();
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CVS file:"+e.getMessage());

        }

    }

    public void saveToDatabase(MultipartFile file){
        try{
            List<ClientEntity> listeClient=new ArrayList<>();
            List<ClientDtoParse> listeClientCsv= doGetFilesParsed(file.getInputStream());
            listeClientCsv.parallelStream().peek(
                    j->{
                        ClientEntity client=mapper.map(j,ClientEntity.class);
                        client.setNomClient(j.getNomClient());
                        client.setPrenomClient(j.getPrenomClient());
                        client.setProfession(j.getProfession());
                        client.setNiveauRevenu(j.getNiveauRevenu());
                        client=this.clientRepository.save(client);
                    }
            ).collect(Collectors.toList());
        }catch (IOException e){
            throw new RuntimeException("fail to store csv data:"+e.getMessage());
        }
    }


}






