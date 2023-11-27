package com.ll.sb20231114.domain.base.attr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Attr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
