package major.project.receiver.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081/")
@RestController
@RequestMapping(path = "/api/v1/")
public class TelemetryController {
    @Autowired
    private TelemetryRepository telemetryRepository;

    @GetMapping(path = "/get-telemetry")
    public List<Telemetry> getAllTelemetry(){
        return telemetryRepository.findAll();
    }

    @PostMapping(path = "/add-telemetry")
    public Telemetry addTelemetry(@RequestBody Telemetry telemetry){
        return telemetryRepository.save(telemetry);
    }
}


