package lab2.stavatar.serveronelab2.Model;

import javax.persistence.*;

@Entity
@Table
public class car
{
    public car() {}
    public car(Boolean cool) {
        this.cool = cool;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="cool")
    private Boolean cool; //Поле не может быть null

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }
}