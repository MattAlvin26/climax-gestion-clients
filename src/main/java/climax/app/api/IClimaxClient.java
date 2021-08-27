package climax.app.api;


import climax.app.model.ClientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface IClimaxClient {

   List<ClientDto> doFetchClient();

   ClientDto doGetClientById(Integer idClient);
   String getFileFormat(MultipartFile file);
   void saveToDatabase(MultipartFile file);
}
