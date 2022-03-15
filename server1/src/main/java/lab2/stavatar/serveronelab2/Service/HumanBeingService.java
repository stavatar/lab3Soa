package lab2.stavatar.serveronelab2.Service;

import lab2.stavatar.serveronelab2.DAO.HumanBeingDAO;
import lab2.stavatar.serveronelab2.Model.humanbeing;
import lab2.stavatar.serveronelab2.Model.util.Mood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class HumanBeingService
{
    @Autowired
    FilterService filterService;
    @Autowired
    HumanBeingDAO humanBeingDAO;


    public void sort(boolean isAscending,String nameField, List<humanbeing> humanbeingList) {
        switch (nameField){
            case "id":
                humanbeingList.sort(Comparator.comparingDouble(humanbeing::getId));
                break;
            case "name":
                humanbeingList.sort(Comparator.comparing(humanbeing::getName));
                break;
            case "impactSpeed":
                humanbeingList.sort(Comparator.comparingDouble(humanbeing::getImpactSpeed));
                break;
            case "X":
                humanbeingList.sort(Comparator.comparingDouble(o -> o.getCoordinates().getX()));
                break;
            case "Y":

                humanbeingList.sort(Comparator.comparingDouble(o -> o.getCoordinates().getY()));
                break;
            case "typeWeapon":
                humanbeingList.sort(Comparator.comparingDouble(o -> o.getWeaponType().ordinal()));
                break;
            case "typeMood":
                humanbeingList.sort(Comparator.comparingInt(o -> o.getMood().ordinal()));
                break;
            case "car":
                humanbeingList.sort((o1, o2) ->Boolean.compare(o1.getCar().getCool(),o2.getCar().getCool()));
                break;
            case "realHero":
                humanbeingList.sort((o1, o2) ->Boolean.compare(o1.isRealHero(),o2.isRealHero()));
                break;
            case "hasToothpick":
                humanbeingList.sort((o1, o2) ->Boolean.compare(o1.isHasToothpick(),o2.isHasToothpick()));
                break;
            case "date":
                humanbeingList.sort(Comparator.comparing(humanbeing::getCreationDate));
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (isAscending)
            Collections.reverse(humanbeingList);

    }

    public List<humanbeing>  filter(String nameField, String valueFilter, String action, List<humanbeing> humanbeingList) {
        List<humanbeing> resultList;
        switch (nameField){
            case "name":
                resultList= humanbeingList.stream().filter(humanBeing -> humanBeing.getName().equals(valueFilter)).collect(Collectors.toList());
                break;
            case "impactSpeed":
                resultList= humanbeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getImpactSpeed()))
                        .collect(Collectors.toList());
                break;
            case "X":
                resultList= humanbeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getCoordinates().getX()))
                        .collect(Collectors.toList());
                break;
            case "Y":
                resultList= humanbeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getCoordinates().getY()))
                        .collect(Collectors.toList());
                break;
            case "weaponType":
                resultList= humanbeingList.stream()
                        .filter(humanBeing -> humanBeing.getWeaponType().ordinal() == Integer.parseInt(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "mood":
                resultList= humanbeingList.stream()
                        .filter(humanBeing -> humanBeing.getMood().ordinal() == Integer.parseInt(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "car":
                resultList= humanbeingList.stream()
                        .filter(humanBeing ->
                                humanBeing.getCar().getCool() == "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "realHero":
                resultList= humanbeingList.stream()
                        .filter(humanBeing -> humanBeing.isRealHero() == "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "hasToothpick":
                resultList= humanbeingList.stream()
                        .filter(humanBeing -> humanBeing.isHasToothpick()== "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "creationDate":
                resultList= humanbeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterDate( valueFilter, action, humanBeing.getCreationDate()))
                        .collect(Collectors.toList());
                break;
            default:
                throw new IllegalArgumentException();
        }
      return resultList;
    }

    public void deleteMood(Mood mood){
        List<humanbeing> humanbeingList = humanBeingDAO.findAll();
        if(humanbeingList.size()==0)
            throw new NoSuchElementException();

        for (humanbeing current: humanbeingList) {
            if (current.getMood()==mood)
                humanBeingDAO.delete(current);
        }
    }
    public int countLessMood(Mood mood){
        int countHuman=0;
        List<humanbeing> humanbeingList = humanBeingDAO.findAll();
        if(humanbeingList.size()==0)
            throw new NoSuchElementException();

        for (humanbeing current: humanbeingList) {
            if (current.getMood().ordinal() < mood.ordinal())
                countHuman++;
        }
        return countHuman;
    }

    public double avrSpeed() {
        double avr;
        double sum=0;
        List<humanbeing> humanbeingList = humanBeingDAO.findAll();
        if(humanbeingList.size()==0)
            throw new NoSuchElementException();

        for (humanbeing current : humanbeingList) {
            sum += current.getImpactSpeed();
        }
        avr = sum / humanbeingList.size();
        avr = Math.round(avr * 1000);
        avr = avr / 1000;
        return avr;
    }

}
