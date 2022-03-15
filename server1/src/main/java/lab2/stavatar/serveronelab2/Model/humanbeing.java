package lab2.stavatar.serveronelab2.Model;

import lab2.stavatar.serveronelab2.Model.util.Mood;
import lab2.stavatar.serveronelab2.Model.util.WeaponType;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table
public class humanbeing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL,optional = true)
    @JoinColumn (name="coordinates_id")
    private lab2.stavatar.serveronelab2.Model.coordinates coordinates;
    @Column(name = "creationdate")
    private ZonedDateTime  creationDate;
    @Column(name = "realhero")
    private boolean realHero;
    @Column(name = "hastoothpick")
    private boolean hasToothpick;
    @Column(name = "impactspeed")
    private double impactSpeed;


    @Column(name = "weapontype")
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    @Column(name = "mood")
    @Enumerated(EnumType.STRING)
    private Mood mood;
    @ManyToOne(cascade = CascadeType.ALL,optional = true)
    @JoinColumn (name="car_id")
    private lab2.stavatar.serveronelab2.Model.car car;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public lab2.stavatar.serveronelab2.Model.coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(lab2.stavatar.serveronelab2.Model.coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public double getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public lab2.stavatar.serveronelab2.Model.car getCar() {
        return car;
    }

    public void setCar(lab2.stavatar.serveronelab2.Model.car car) {
        this.car = car;
    }


}


