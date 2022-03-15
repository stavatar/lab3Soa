package lab2.stavatar.serveronelab2.Controller;

import lab2.stavatar.serveronelab2.Service.HumanBeingService;
import lab2.stavatar.serveronelab2.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AverageSpeedSerlvets {
    @Autowired
    HumanBeingService humanBeingService;
    @GetMapping("/LABSOA1_war/averageSpeed")
    public ResponseEntity<Message> getSpeed() {
        double avr= humanBeingService.avrSpeed();
        Message message=new Message();
        message.setCode(1);
        message.setData( "Mean = " + avr);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }
}
