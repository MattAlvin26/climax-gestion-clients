package climax.app.controller;

import climax.app.config.ClimaxEndpoint;
import climax.app.model.ClientDto;
import climax.app.service.IClimaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ClimaxController {
    private final IClimaxService climaxService;
    public static String TYPECSV="text/csv";
    public static String TYPEXML="text/xml";
    public static String TYPEJSON="text/csv";

    @GetMapping(ClimaxEndpoint.CLIMAX_HOME)
    //@RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

    @GetMapping(ClimaxEndpoint.CLIMAX_LISTE_CLIENTS)
    public ResponseEntity<List<ClientDto>> getListClientsRequest()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.climaxService.doGetListeClient());
    }

    @GetMapping(ClimaxEndpoint.CLIMAX_CLIENT)
    public ResponseEntity<ClientDto> getClientsRequest(
            @RequestParam("idClient") final Integer idClient)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.climaxService.doGetClient(idClient));
    }
    @PostMapping(ClimaxEndpoint.CLIMAX_CLIENT)
    public ResponseEntity<ClientDto> doPostClientsRequest(
            @RequestParam("file") final MultipartFile file)
    {
        if(climaxService.getFileFormat(file).equals(TYPECSV)){
            climaxService.doSaveToDatabase(file);
        }

        return ResponseEntity.ok().build();
    }
}
