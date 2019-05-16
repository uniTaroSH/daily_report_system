package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "followList")
@NamedQueries({
    @NamedQuery(
            name = "getMyFollowList",
            query = "SELECT f FROM FollowList AS f WHERE f.employee = :employee ORDER BY f.id DESC"
            )
})


@Entity
public class FollowList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @Column(name = "follow_id", nullable = false)
    private Integer follow_id;


    @Column(name = "follow_code", nullable = false)
    private String follow_code;


    @Column(name = "follow_date", nullable = false)
    private Timestamp follow_date;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Employee getEmployee() {
        return employee;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public String getFollow_code() {
        return follow_code;
    }


    public void setFollow_code(String follow_code) {
        this.follow_code = follow_code;
    }


    public Timestamp getFollow_date() {
        return follow_date;
    }


    public void setFollow_date(Timestamp follow_date) {
        this.follow_date = follow_date;
    }



    public Integer getFollow_id() {
        return follow_id;
    }


    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }


}
