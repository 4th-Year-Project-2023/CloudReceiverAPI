package major.project.receiver.telemetry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

}

