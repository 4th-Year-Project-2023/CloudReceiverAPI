package major.project.receiver.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.json.ApplicationProperties;
import major.project.receiver.mqtt.MultiThreadedReceiver;
import major.project.receiver.telemetry.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class GetParameter {
    @Autowired
    @Setter
    @Getter
    private MultiThreadedReceiver multiThreadedReceiver;


    @Autowired
    @Setter
    @Getter
    private TelemetryRepository telemetryRepository;

    @Autowired
    @Setter
    @Getter
    private ApplicationProperties applicationProperties;

    @Value("${app.properties}")
    private String paramName;

    @PostConstruct
//    public void extractParams() {
//        AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
//        log.info("SSM client created");
//        GetParameterRequest parameterRequest = new GetParameterRequest();
//        parameterRequest.setName(paramName);
//        GetParameterResult getParameterResult = ssm.getParameter(parameterRequest);
//        log.info(getParameterResult.getParameter().getValue());
//        String paramString = getParameterResult.getParameter().getValue();
//        try {
//            applicationProperties = new ObjectMapper().readValue(paramString, ApplicationProperties.class);
//            log.info(applicationProperties.getMqttChannel()+ "  "+applicationProperties.getMqttServer()+" "+applicationProperties.getShPath());
//            multiThreadedReceiver.setTelemetryRepository(getTelemetryRepository());
//            multiThreadedReceiver.start();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void testRun(){
        multiThreadedReceiver.setTelemetryRepository(getTelemetryRepository());
        multiThreadedReceiver.start();
    }
}