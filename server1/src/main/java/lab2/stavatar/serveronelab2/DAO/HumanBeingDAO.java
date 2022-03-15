package lab2.stavatar.serveronelab2.DAO;

import lab2.stavatar.serveronelab2.Model.humanbeing;
import lab2.stavatar.serveronelab2.Model.util.Mood;
import lab2.stavatar.serveronelab2.Model.util.WeaponType;
import lab2.stavatar.serveronelab2.Repository.CarRepository;
import lab2.stavatar.serveronelab2.Repository.CoordinatesRepository;
import lab2.stavatar.serveronelab2.Repository.HumanBeingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HumanBeingDAO
{
    @Autowired
    HumanBeingRepository humanBeingRepository;

    @Autowired
    CarRepository carRepository;
    @Autowired
    CoordinatesRepository coordinatesRepository;

    public humanbeing findById(long id) {
        humanbeing humanBeing= humanBeingRepository.findById(id).get();
        return humanBeing;
    }

    public void save(humanbeing user) {
        humanBeingRepository.save(user);
    }

    public void update(humanbeing user, String nameField, String newValue) {

        switch (nameField){
            case "name":
                if(newValue.equals(""))
                    throw new IllegalArgumentException();
                user.setName(newValue);
                break;
            case "impactSpeed":
                user.setImpactSpeed(Double.parseDouble(newValue));
                break;
            case "weaponType":
                user.setWeaponType(WeaponType.valueOf(newValue));
                break;
            case "mood":
                user.setMood(Mood.valueOf(newValue));
                break;
            case "X":
                user.getCoordinates().setX(Double.parseDouble(newValue));
                break;
            case "Y":
                double y=Double.parseDouble(newValue);
                if (y>369)
                    throw new IllegalArgumentException();
                user.getCoordinates().setY(y);
                break;
            case "car":
                user.getCar().setCool(Boolean.parseBoolean(newValue));
                break;
            case "realHero":
                user.setRealHero(Boolean.parseBoolean(newValue));
                break;
            case "hasToothpick":
                user.setHasToothpick(Boolean.parseBoolean(newValue));
                break;
            default:
                throw new IllegalArgumentException("nameField="+ nameField +"  newValue="+ newValue);
        }
            humanBeingRepository.save(user);
    }

    public void delete(humanbeing user) {


        coordinatesRepository.delete(user.getCoordinates());
        carRepository.delete(user.getCar());
        humanBeingRepository.delete(user);


    }

    public List<humanbeing> findAll() {
        List<humanbeing> users =humanBeingRepository.findAll();
        return users;
    }




}
