package com.ujjain.trade.dependencies.db.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "createdOn")
    @CreationTimestamp
    Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseEntity{");
        sb.append("id=").append(id);
        sb.append(", createdOn=").append(createdOn);
        sb.append('}');
        return sb.toString();
    }
}

