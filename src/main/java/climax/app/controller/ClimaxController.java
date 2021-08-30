package climax.app.controller;

import climax.app.config.ClimaxEndpoint;
import climax.app.model.ClientDto;
import climax.app.service.IClimaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ClimaxController {
    private final IClimaxService climaxService;
    public static String HOME=System.getProperty("user.home");
    public static String TYPECSV="text/csv";
    public static String TYPEXML="application/xml";
    public static String TYPEJSON="application/json";


    /**
     * Endppoint pour la liste des clients
     *
     * @return liste clientDto
     */
    @GetMapping(ClimaxEndpoint.CLIMAX_LISTE_CLIENTS)
    public ResponseEntity<List<ClientDto>> getListClientsRequest()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.climaxService.doGetListeClient());
    }

    /**
     * Endpoint pour retourner un client par son id
     *
     * @return clientDto
     */
    @GetMapping(ClimaxEndpoint.CLIMAX_CLIENT)
    public ResponseEntity<ClientDto> getClientsRequest(
            @RequestParam("idClient") final Integer idClient)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.climaxService.doGetClient(idClient));
    }

    /**
     * Endppoint pour enregistrer les données des differents fichier
     * @param file
     * @return liste clientDto
     */
    @PostMapping(ClimaxEndpoint.CLIMAX_CLIENT)
    public ResponseEntity<List<ClientDto>> doPostClientsRequest(
            @RequestParam("file") final MultipartFile file) throws IOException {
        List<ClientDto> listeClient=new ArrayList<>();
        if(climaxService.getFileFormat(file).equals(TYPECSV)){
            listeClient=climaxService.doSaveToDatabase(file);
        }
        else if(climaxService.getFileFormat(file).equals(TYPEJSON)){
            listeClient=climaxService.doSaveJsonToDatabase(file);
        }
        else if(climaxService.getFileFormat(file).equals(TYPEXML)){
            listeClient=climaxService.doSaveXmlToDatabase(file);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ce type de fichier n'est pas pris en compte");

        }


        return ResponseEntity.status(HttpStatus.OK)
                .body(listeClient);
    }

    @GetMapping(ClimaxEndpoint.CLIMAX_MOYENNE_REVENU)
    public ResponseEntity<Float> getClientsRequest(
            @RequestParam("profession") final String profession)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.climaxService.doGetMoyenneRevenu(profession));
    }
}
