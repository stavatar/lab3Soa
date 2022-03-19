package lab2.stavatar.serveronelab2.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;


@Service
public class FilterService {

    public Boolean filterNumber(String valueFilter,String action, double current){
        double value = Double.parseDouble(valueFilter);
        switch (action){
            case "=":
                return current == value;
            case ">":
                return current > value;
            case ">=":
                return current >= value;
            case "<":
                return current < value;
            case "<=":
                return current <= value;
            case "!=":
                return current != value;
            default:
                return true;
        }
    }
    public Boolean filterDate(String valueFilter,String action, ZonedDateTime current){

        ZonedDateTime value = ZonedDateTime.parse(valueFilter)
                                           .withZoneSameInstant(current.getZone())
                                           .withHour(0)
                                           .withMinute(0)
                                           .withSecond(0)
                                           .withNano(0);


     //   log.println("valueFilter"+value.toString());
      //  log.println("current"+current.toString());

     //   log.println("valueFilter.LOCAL"+value.toLocalDate().toString());
      //  log.println("current.LOCAL"+current.toLocalDate().toString());


        //log.println("!!!!");
        switch (action){
            case "=":
                //  log.println(current +" = " + value + " IS " +  current.isEqual(value)) ;
                return current.isEqual(value);
            case ">":
             //   log.println(current +" > " + value  + " IS " +  current.isAfter(value));
                return current.isAfter(value);
            case ">=":
               // log.println(current +" >= " + value  + " IS " +  (current.isAfter(value)||current.isEqual(value)));
                return current.isAfter(value)||current.isEqual(value);
            case "<":
            //    log.println(current +" < " + value  + " IS " + current.isBefore(value));
                return current.isBefore(value);
            case "<=":
           //     log.println(current +" <= " + value  + " IS " +  (current.isBefore(value)||current.isEqual(value)));
                return current.isBefore(value)||current.isEqual(value);
            case "!=":
           //     log.println(current +" != " + value  + " IS " +  current.isEqual(value));
                return !current.isEqual(value);
            default:
                return true;
        }
    }


}
