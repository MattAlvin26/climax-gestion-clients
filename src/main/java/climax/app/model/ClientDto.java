package climax.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Integer idClient;
    private String nomClient;
    private String prenomClient;
    private String profession;
    private Integer niveauRevenu;

}
