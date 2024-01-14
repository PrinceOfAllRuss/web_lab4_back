package com.webapp.lab4.home;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "results")
public class ResultElement {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "x")
    private String x;
    @Column(name = "y")
    private String y;
    @Column(name = "r")
    private String r;
    @Column(name = "condition")
    protected boolean condition;
    @Column(name = "time")
    protected Date time;
    @Column(name = "time_of_work")
    protected long timeOfWork;

    ResultElement() {}
    
    public ResultElement(String x, String y, String r, boolean condition, long timeOfWork, Date time) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.condition = condition;
        this.timeOfWork = timeOfWork;
        this.time = time;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getTimeOfWork() {
        return timeOfWork;
    }

    public void setTimeOfWork(long timeOfWork) {
        this.timeOfWork = timeOfWork;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.x);
        hash = 53 * hash + Objects.hashCode(this.y);
        hash = 53 * hash + Objects.hashCode(this.r);
        hash = 53 * hash + (this.condition ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.time);
        hash = 53 * hash + (int) (this.timeOfWork ^ (this.timeOfWork >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultElement other = (ResultElement) obj;
        if (this.condition != other.condition) {
            return false;
        }
        if (this.timeOfWork != other.timeOfWork) {
            return false;
        }
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        if (!Objects.equals(this.r, other.r)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.time, other.time);
    }
    
}
