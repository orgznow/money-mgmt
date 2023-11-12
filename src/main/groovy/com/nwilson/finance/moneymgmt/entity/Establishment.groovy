package com.nwilson.finance.moneymgmt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.TableGenerator

@Entity
@Table(name="establishment")
class Establishment {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="establishmentGen")
    @TableGenerator(name="establishmentGen", table="establishment_seq", schema="budget")
    @Column(name="establishment_id", nullable=false)
    Integer id

    @Column(name="name", nullable=false, length=50)
    String name

    @Column(name="description", nullable=true, length=100)
    String description

    @Column(name="tax_percentage", nullable=true)
    Double taxPercentage

    @Column(name="zip_code", nullable=true, length=10)
    String zipCode
}
