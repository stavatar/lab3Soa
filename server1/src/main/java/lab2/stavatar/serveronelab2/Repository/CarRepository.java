package lab2.stavatar.serveronelab2.Repository;

import lab2.stavatar.serveronelab2.Model.car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<car, Long> {
}
