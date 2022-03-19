package lab2.stavatar.serveronelab2.Controller;

import lab2.stavatar.serveronelab2.DAO.HumanBeingDAO;
import lab2.stavatar.serveronelab2.Model.humanbeing;
import lab2.stavatar.serveronelab2.NotFoundObjException;
import lab2.stavatar.serveronelab2.Service.HumanBeingService;
import lab2.stavatar.serveronelab2.Utils.RequestParam.Main.GET.Filter;
import lab2.stavatar.serveronelab2.Utils.RequestParam.Main.GET.GetRequest;
import lab2.stavatar.serveronelab2.Utils.Message;
import lab2.stavatar.serveronelab2.Utils.RequestParam.Main.PUT.PutRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class MainServlet {
    @Autowired
    HumanBeingService humanBeingService;
    @Autowired
    HumanBeingDAO humanBeingDAO;
    @Value("${server.port}")
    private String port;

    @GetMapping("/LABSOA1_war/humanBeingsss")
    public ResponseEntity<String> getHuman(){
        humanBeingDAO.findAll();
        return  new ResponseEntity<>(port, HttpStatus.OK);

    }

    @GetMapping("/LABSOA1_war/humanBeings")
    public ResponseEntity<Message> getHumanBeingAll(
            @RequestParam(value ="sortField",required = false) String sortField,
            @RequestParam(value ="isAsc",required = false) Boolean isAsc,
            @RequestParam (value ="sizePage") Integer szPage,
            @RequestParam (value ="numberPage") Integer numPage,
            @RequestParam(value = "filters",required = false) String filters) throws JSONException, NotFoundObjException {

        String sort_nameField = sortField;
        Boolean isAscending = isAsc;
        int sizePage = szPage;
        int numberPage =numPage;
        int startIndex = (numberPage - 1) * sizePage;
        int endIndex = (numberPage - 1) * sizePage + sizePage;
        JSONArray filtersJSON=new JSONArray("[]");

            if (!Objects.equals(filters, "")) {
                filtersJSON = new JSONArray(filters);
            }

        List<humanbeing> humanbeingList = humanBeingDAO.findAll();
        if(humanbeingList ==null|| humanbeingList.size()==0) {
            throw new NotFoundObjException();
        }
        List<humanbeing> filterList = humanbeingList;
        if(filters!=null)
            for (int i = 0; i < filtersJSON.length(); i++) {
                JSONObject current = filtersJSON.getJSONObject(i);
                String nameField = current.getString("nameField");
                String action = current.getString("action");
                String valueFilter = current.getString("value");
                filterList = humanBeingService.filter(nameField, valueFilter, action, filterList);
            }
        if (isAscending!=null && sort_nameField!=null && filterList!=null)
           humanBeingService.sort(isAscending, sort_nameField, filterList);
        List<humanbeing> resultList = filterList;
        if (resultList.size() > 0)
            resultList = resultList.subList(startIndex, Math.min(resultList.size(), endIndex));


        Message message=new Message();
        message.setCode(1);
        message.setData(resultList);

        return  new ResponseEntity<>(message, HttpStatus.OK);


    }
    @GetMapping(value = "/LABSOA1_war/humanBeings/{id}")
    public ResponseEntity<Message> getHumanBeing(@PathVariable(name = "id") int id) throws NotFoundObjException {
        humanbeing humanBeing=humanBeingDAO.findById(id);
        if(humanBeing==null) {
            throw new NotFoundObjException();
        }
        Message message=new Message();
        message.setCode(1);
        message.setData(humanBeing);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/LABSOA1_war/humanBeings")
    public ResponseEntity<Message>  addHuman(@RequestBody humanbeing newHuman)  {
            humanBeingDAO.save(newHuman);
            Message message=new Message();
            message.setCode(1);
            message.setData("Objects added!");
        return  new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/LABSOA1_war/humanBeings")
    public ResponseEntity<Message> updateHuman(@RequestBody PutRequest param) throws NotFoundObjException {
        humanbeing human = humanBeingDAO.findById(param.getId());
        if(human==null)
            throw new NotFoundObjException();
        humanBeingDAO.update(human,param.getFieldName(),param.getNewValue());
        Message message=new Message();
        message.setCode(1);
        message.setData("Objects updated!");

        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/LABSOA1_war/humanBeings/{id}")
    public ResponseEntity<Message>  deleteHuman(@PathVariable(name = "id") int id) throws NotFoundObjException {
        humanbeing humanBeing=humanBeingDAO.findById(id);
        if(humanBeing==null)
            throw new NotFoundObjException();
        humanBeingDAO.delete(humanBeing);
        Message message=new Message();
        message.setCode(1);
        message.setData("Object successfully deleted!");
        return  new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}