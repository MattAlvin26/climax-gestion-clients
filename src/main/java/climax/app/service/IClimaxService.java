package climax.app.service;

import climax.app.model.ClientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface IClimaxService {
     /**
      * Returne la liste des clients
      * @return liste clientDto
      */
     List<ClientDto> doGetListeClient();

     /**
      * Returne un client
      * @param idClient
      * @return clientDto
      */
     ClientDto doGetClient(Integer idClient);

     /**
      * Retourne le format d'un fichier
      * @param file
      * @return String
      */
     String getFileFormat(MultipartFile file);

     /**
      *Realise l'enregistrement des données d'un fichier csv en base de données
      * @param file
      * @return String
      */
     List<ClientDto> doSaveToDatabase(MultipartFile file);

     /**
      *Realise l'enregistrement des données d'un fichier json en base de données
      * @param file
      * @return String
      */
     List<ClientDto> doSaveJsonToDatabase(MultipartFile file);

     /**
      *Realise l'enregistrement des données d'un fichier xml en base de données
      * @param file
      * @return String
      */
     List<ClientDto> doSaveXmlToDatabase(MultipartFile file);

     /**
      *Realise le calcul des de la moyenne des salires par profession
      * @param profession
      * @return
      */
     Float doGetMoyenneRevenu(String profession);
}
