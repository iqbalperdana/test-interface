package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CrmIntegration {

    @Id
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform_name")
    private PlatformName platformName;

    @Column(name = "enabled")
    private boolean enabled = true;
}