package climax.app.service.impl;

import climax.app.api.IClimaxClient;
import climax.app.model.ClientDto;
import climax.app.service.IClimaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class ClimaxServiceImpl implements IClimaxService {
    private final IClimaxClient serviceImpl;

    /**
     * Returne la liste des clients
     * @return liste clientDto
     */
    @Override
    public List<ClientDto> doGetListeClient() {

        return this.serviceImpl.doFetchClient();
    }

    /**
     * Returne un client
     * @param idClient
     * @return clientDto
     */
    @Override
    public ClientDto doGetClient(Integer idClient) {

        return this.serviceImpl.doGetClientById(idClient);
    }

    /**
     * Retourne le format d'un fichier
     * @param file
     * @return String
     */
    @Override
    public String getFileFormat(MultipartFile file) {
        return this.serviceImpl.getFileFormat(file);
    }

    /**
     *Realise l'enregistrement des données d'un fichier csv en base de données
     * @param file
     * @return String
     */
    @Override
    public List<ClientDto> doSaveToDatabase(MultipartFile file) {

        return this.serviceImpl.saveToDatabase(file);
    }

    /**
     *Realise l'enregistrement des données d'un fichier json en base de données
     * @param file
     * @return String
     */
    @Override
    public List<ClientDto> doSaveJsonToDatabase(MultipartFile file) {
       return this.serviceImpl.saveJsonToDatabase(file);
    }

    /**
     *Realise l'enregistrement des données d'un fichier xml en base de données
     * @param file
     * @return String
     */
    @Override
    public List<ClientDto> doSaveXmlToDatabase(MultipartFile file) {
        return this.serviceImpl.saveXmlToDatabase(file);
    }

    /**
     *Realise le calcul des de la moyenne des salires par profession
     * @param profession
     * @return
     */
    @Override
    public Float doGetMoyenneRevenu(String profession) {
        return this.serviceImpl.CalculMoyenneRevenuParProfession(profession);
    }
}
