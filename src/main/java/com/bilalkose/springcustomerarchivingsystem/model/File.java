package com.bilalkose.springcustomerarchivingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntityAudit{
    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
