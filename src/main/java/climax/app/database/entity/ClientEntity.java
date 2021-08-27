package climax.app.database.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="clients")

public class ClientEntity {

    @Id
    @Column(name = "idClient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClient;
    @Column(name = "nom_client")
    private String nomClient;
    @Column(name = "prenom_client")
    private String prenomClient;
    @Column(name = "profession")
    private String profession;
    @Column(name = "niveau_revenu")
    private Integer niveauRevenu;
}