package lab2.stavatar.serveronelab2.Repository;

import lab2.stavatar.serveronelab2.Model.humanbeing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanBeingRepository extends JpaRepository<humanbeing, Long> {

}
