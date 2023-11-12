package com.nwilson.finance.moneymgmt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.TableGenerator

@Entity
@Table(name="transaction_type")
class TransactionType {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="transactionTypeGen")
    @TableGenerator(name="transactionTypeGen", table="transaction_type_seq", schema="budget")
    @Column(name="transaction_type_id", nullable=false)
    Integer id

    @Column(name="name", nullable=false, length=50)
    String name

    @Column(name="description", nullable=true, length=100)
    String description
}
