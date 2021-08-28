package climax.app.database.repository;

import climax.app.database.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {
    /**
     * Retourne la liste des clients par profession
     * @param profession
     * @return
     */
    List<ClientEntity> findAllByProfession(String profession);

    /**
     * Retourne la liste de tous les clients
     * @return
     */
    List<ClientEntity> findAll();

    /**
     * Retourne le nombre de clients par profession
     * @param profession
     * @return
     */
    Integer countAllByProfession(String profession);

    /**
     * Retourne la somme des revenus des clients par profession
     * @param profession
     * @return
     */
    @Query(value = "SELECT SUM(niveau_revenu) FROM clients c " +
            " where c.profession=:profession", nativeQuery = true)
    Integer findSommeRevenu(String profession);


}
