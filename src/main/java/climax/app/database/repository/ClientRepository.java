package climax.app.database.repository;

import climax.app.database.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {
    List<ClientEntity> findAllByProfession(String profession);
    List<ClientEntity> findAll();


}
