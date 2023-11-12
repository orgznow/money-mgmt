package com.nwilson.finance.moneymgmt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.TableGenerator

@Entity
@Table(name="unit_type")
class UnitType {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="unitTypeGen")
    @TableGenerator(name="unitTypeGen", table="unit_type_seq", schema="budget")
    @Column(name="unit_type_id", nullable=false)
    Integer id

    @Column(name="name", nullable=false, length=50)
    String name

    @Column(name="description", nullable=true, length=100)
    String description
}
