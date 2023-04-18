package major.project.receiver.telemetry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "telemetry")
@Getter
@Setter
@NoArgsConstructor
public class Telemetry {
    @Id
    @SequenceGenerator(
            name = "telemetry_sequence",
            sequenceName = "telemetry_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "telemetry_sequence1"
    )
    private long id;
    @JsonProperty("currDate")
    @Column(name = "currDate")
    private String currDate;
    @JsonProperty("humidity")
    @Column(name = "humidity")
    private String humidity;
    @JsonProperty("temperature")
    @Column(name = "temperature")
    private String temperature;

    public Telemetry(String currDate, String humidity, String temperature) {
        this.currDate = currDate;
        this.humidity = humidity;
        this.temperature = temperature;
    }
}




