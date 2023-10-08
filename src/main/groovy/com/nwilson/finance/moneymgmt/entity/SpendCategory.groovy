package com.nwilson.finance.moneymgmt.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.type.TrueFalseConverter

@Entity
@Table(name="category")
class SpendCategory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="category_id", nullable=false)
    Integer id

    @Column(name="name", nullable=false, length=50)
    String name

    @Column(name="description", nullable=true, length=100)
    String description

    @Column(name="is_expense", nullable=true)
    @Convert(converter= TrueFalseConverter)
    Boolean isExpense
}
