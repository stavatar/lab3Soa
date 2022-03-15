package lab2.stavatar.serveronelab2.Controller;

import lab2.stavatar.serveronelab2.Model.util.Mood;
import lab2.stavatar.serveronelab2.Service.HumanBeingService;
import lab2.stavatar.serveronelab2.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeleteMoodServlets  {
    @Autowired
    HumanBeingService humanBeingService;
    @PostMapping("/LABSOA1_war/deleteAllForMood")
    public ResponseEntity<Message> delete( @RequestParam(value ="mood") String mood)  {
        Mood mood1 = Mood.valueOf(mood);
        humanBeingService.deleteMood(mood1);

        Message message=new Message();
        message.setCode(1);
        message.setData("All objects have been deleted!");

        return  new ResponseEntity<>(message, HttpStatus.OK);
    }
}
