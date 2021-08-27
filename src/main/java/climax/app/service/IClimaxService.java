package climax.app.service;

import climax.app.model.ClientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface IClimaxService {
     List<ClientDto> doGetListeClient();
     ClientDto doGetClient(Integer idClient);
     String getFileFormat(MultipartFile file);
     void doSaveToDatabase(MultipartFile file);
}
