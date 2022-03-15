package lab2.stavatar.serveronelab2.Controller;

import lab2.stavatar.serveronelab2.Model.util.Mood;
import lab2.stavatar.serveronelab2.Service.HumanBeingService;
import lab2.stavatar.serveronelab2.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class CountMoodServlet{
    @Autowired
    HumanBeingService humanBeingService;
    @GetMapping("/LABSOA1_war/countHuman")
    public ResponseEntity<Message> get( @RequestParam(value ="mood") String typeMood) {
       Mood mood = Mood.valueOf(typeMood);
        int countHuman = humanBeingService.countLessMood(mood);
        Message message=new Message();
        message.setCode(1);
        message.setData( "Count of objects found =  " + countHuman);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }
}
