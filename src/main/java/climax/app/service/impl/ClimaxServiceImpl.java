package climax.app.service.impl;

import climax.app.api.IClimaxClient;
import climax.app.api.impl.ClimaxClientImpl;
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
    @Override
    public List<ClientDto> doGetListeClient() {
        return this.serviceImpl.doFetchClient();
    }

    @Override
    public ClientDto doGetClient(Integer idClient) {
        return this.serviceImpl.doGetClientById(idClient);
    }

    @Override
    public String getFileFormat(MultipartFile file) {
        return this.serviceImpl.getFileFormat(file);
    }

    @Override
    public void doSaveToDatabase(MultipartFile file) {
        this.serviceImpl.saveToDatabase(file);
    }
}
