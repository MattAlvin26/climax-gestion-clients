package climax.app.model;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDtoParse {

    @Parsed(field="nom")
    private String nomClient;
    @Parsed(field="prenom")
    private String prenomClient;
    @Parsed(field="profession")
    private String profession;
    @Parsed(field="salaire")
    private Integer niveauRevenu;

}
