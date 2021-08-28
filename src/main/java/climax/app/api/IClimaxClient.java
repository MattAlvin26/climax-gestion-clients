package climax.app.api;


import climax.app.model.ClientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface IClimaxClient {

   /**
    * Returne la liste des clients
    * @return liste clientDto
    */
   List<ClientDto> doFetchClient();

   /**
    * Returne un client
    * @param idClient
    * @return clientDto
    */
   ClientDto doGetClientById(Integer idClient);

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
   List<ClientDto> saveToDatabase(MultipartFile file);

   /**
    *Realise l'enregistrement des données d'un fichier json en base de données
    * @param file
    * @return String
    */
   List<ClientDto> saveJsonToDatabase(MultipartFile file);

   /**
    *Realise l'enregistrement des données d'un fichier xml en base de données
    * @param file
    * @return String
    */
   List<ClientDto> saveXmlToDatabase(MultipartFile file);

   /**
    *Realise le calcul des de la moyenne des salires par profession
    * @param profession
    * @return
    */
   Float CalculMoyenneRevenuParProfession(String profession);
}
