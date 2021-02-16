package com.orderoperator.apps.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "`order`")
@Document(indexName = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field
    private int id;
    @Field
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Field
    private int customerId;
}
