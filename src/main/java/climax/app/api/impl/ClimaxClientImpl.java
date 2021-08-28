package climax.app.api.impl;

import climax.app.api.IClimaxClient;
import climax.app.database.entity.ClientEntity;
import climax.app.database.repository.ClientRepository;
import climax.app.model.ClientDto;
import climax.app.model.ClientDtoParse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Slf4j
public class ClimaxClientImpl implements IClimaxClient {

    public static String HOME=System.getProperty("user.home");
    private final ClientRepository clientRepository;

    private final Mapper mapper;

    /**
     * Returne la liste des clients
     * @return liste clientDto
     */
    @Override
    public List<ClientDto> doFetchClient() {
        return this.clientRepository.findAll().stream()
                .map(t->mapper.map(t,ClientDto.class)).collect(Collectors.toList());
    }


    /**
     * Returne un client
     * @param idClient
     * @return clientDto
     */
    @Override
    public ClientDto doGetClientById(Integer idClient) {
        Optional<ClientEntity> client=this.clientRepository.findById(idClient);
        if(client.isPresent()){
        return mapper.map(client.get(),ClientDto.class);
        }
        return null;
    }

    /**
     * Retourne le format d'un fichier
     * @param file
     * @return String
     */

    public  String getFileFormat(MultipartFile file){

        return file.getContentType();
    }
    /**
     *Permer de parser les fichier csv
     * @param is
     * @return liste clientDtoParse
     */
    private List<ClientDtoParse> doGetFilesParsed(InputStream is){
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
    /**
     *Realise l'enregistrement des données d'un fichier xml en base de données
     * @param file
     * @return
     */
    public List<ClientDto> saveXmlToDatabase(MultipartFile file)  {
        List<ClientDto> listeClientDto=new ArrayList<>();
        try {
            InputStream initialStream =file.getInputStream();
            File targetFile = new File("src/main/resources/targetFile.xml");
            FileUtils.copyInputStreamToFile(initialStream, targetFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document document = builder.parse(new File("src/main/resources/dataXml.xml"));
            Document document = builder.parse(targetFile);

            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            NodeList nList = document.getElementsByTagName("employe");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                System.out.println("");    //Just a separator
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Print each employee's detail
                    ClientEntity client = new ClientEntity();
                    Element eElement = (Element) node;
                    client.setNomClient(eElement.getElementsByTagName("nom").item(0).getTextContent());
                    client.setPrenomClient(eElement.getElementsByTagName("prenom").item(0).getTextContent());
                    client.setProfession(eElement.getElementsByTagName("profession").item(0).getTextContent());
                    client.setNiveauRevenu(Integer.parseInt(eElement.getElementsByTagName("salaire").item(0).getTextContent()));
                   client= this.clientRepository.save(client);
                   listeClientDto.add(mapper.map(client,ClientDto.class));
                }
            }


        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return listeClientDto;
    }

    /**
     *Realise le calcul des de la moyenne des salires par profession
     * @param profession
     * @return
     */
    @Override
    public Float CalculMoyenneRevenuParProfession(String profession) {
        Float moyenneRevenu;
        Integer nbEmploye=this.clientRepository.countAllByProfession(profession);
        Integer montantRevenu=this.clientRepository.findSommeRevenu(profession);
        moyenneRevenu= (float)montantRevenu/(float)nbEmploye;
        return moyenneRevenu;
    }

    /**
     *Realise l'enregistrement des données d'un fichier json en base de données
     * @param file
     * @return
     */
    public List<ClientDto> saveJsonToDatabase(MultipartFile file) {
        List<ClientDto> listeClientDto=new ArrayList<>();
        try {
            ObjectMapper mapperObjet = new ObjectMapper();
            TypeReference<List<ClientDtoParse>> mapType = new TypeReference<List<ClientDtoParse>>() {
            };

            InputStream is = file.getInputStream();
            try {
                List<ClientDtoParse> listeClientJson = mapperObjet.readValue(is, mapType);
                listeClientJson.parallelStream().peek(
                        j -> {
                            ClientEntity client = mapper.map(j, ClientEntity.class);
                            client=this.clientRepository.save(client);
                            listeClientDto.add(mapper.map(client,ClientDto.class));
                        }
                ).collect(Collectors.toList());



            } catch (IOException e) {
                throw new RuntimeException("fail to store json data:" + e.getMessage());
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        }
        return listeClientDto;
    }
    /**
     *Realise l'enregistrement des données d'un fichier csv en base de données
     * @param file
     * @return
     */
    public List<ClientDto> saveToDatabase(MultipartFile file){
        try{

            List<ClientEntity> listeClient=new ArrayList<>();
            List<ClientDto> listeClientDto=new ArrayList<>();
            List<ClientDtoParse> listeClientCsv= doGetFilesParsed(file.getInputStream());
            listeClientCsv.parallelStream().peek(
                    j->{
                        ClientEntity client=mapper.map(j,ClientEntity.class);
                        client.setNomClient(j.getNomClient());
                        client.setPrenomClient(j.getPrenomClient());
                        client.setProfession(j.getProfession());
                        client.setNiveauRevenu(j.getNiveauRevenu());
                        client=this.clientRepository.save(client);
                        listeClientDto.add(mapper.map(client,ClientDto.class));
                    }
            ).collect(Collectors.toList());
            return listeClientDto;
        }catch (IOException e){
            throw new RuntimeException("fail to store csv data:"+e.getMessage());
        }
    }


}






