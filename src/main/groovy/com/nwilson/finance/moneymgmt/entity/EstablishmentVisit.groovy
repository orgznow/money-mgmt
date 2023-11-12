package com.nwilson.finance.moneymgmt.entity

import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat

import java.text.SimpleDateFormat

@Entity
@Table(name="establishment_visit")
class EstablishmentVisit {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy")

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="establishmentVisitGen")
    @TableGenerator(name="establishmentVisitGen", table="establishment_visit_seq", schema="budget")
    @Column(name="establishment_visit_id", nullable=false)
    Integer id

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="visit_date", nullable=false)
    Date visitDate

    @Column(name="description", nullable=true, length=100)
    String description

    @Column(name="visit_total_amount", nullable=false)
    Double visitTotalAmount

    @Column(name="tax_percentage", nullable=false)
    Double taxPercentage

    @Column(name="comments", nullable=true, length=500)
    String comments

    @OneToOne
    @JoinColumn(name="establishment_id", referencedColumnName="establishment_id", nullable=false)
    Establishment establishment

    @OneToOne
    @JoinColumn(name="transaction_type_id", referencedColumnName="transaction_type_id", nullable=false)
    TransactionType transactionType

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    @JoinColumn(name="establishment_visit_id", nullable=true)
    List<JournalEntry> journalEntries

    String getVisitDateString() {
        DATE_FORMATTER.format(this.visitDate)
    }
}
