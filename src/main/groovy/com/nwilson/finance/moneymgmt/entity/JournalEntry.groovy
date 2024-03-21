package com.nwilson.finance.moneymgmt.entity

import com.nwilson.finance.moneymgmt.controller.cmd.JournalEntryCmd
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.TableGenerator
import org.hibernate.type.TrueFalseConverter

import java.text.SimpleDateFormat

@Entity
@Table(name="journal_entry")
@TupleConstructor
@ToString(includeNames=true)
class JournalEntry {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy")

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="journalEntryGen")
    @TableGenerator(name="journalEntryGen", table="journal_entry_seq", schema="budget")
    @Column(name="journal_entry_id", nullable=false)
    Integer id

    @Column(name="entry_date", nullable=false)
    Date entryDate

    @Column(name="description", nullable=false, length=100)
    String description

    @Column(name="rate_amount", nullable=false)
    BigDecimal rateAmount

    @Column(name="quantity", nullable=false)
    Double quantity

    @Column(name="base_amount", nullable=false)
    BigDecimal baseAmount

    @Column(name="discount_amount", nullable=true)
    BigDecimal discountAmount

    @Column(name="is_taxable", nullable=false)
    @Convert(converter=TrueFalseConverter)
    Boolean isTaxable

    @Column(name="tax_amount", nullable=false)
    BigDecimal taxAmount

    @Column(name="tip_amount", nullable=false)
    BigDecimal tipAmount

    @Column(name="final_amount", nullable=false)
    BigDecimal finalAmount

    @Column(name="comments", nullable=true, length=500)
    String comments

    @OneToOne
    @JoinColumn(name="establishment_visit_id", referencedColumnName="establishment_visit_id", nullable=false)
    EstablishmentVisit establishmentVisit

    @OneToOne
    @JoinColumn(name="category_id", referencedColumnName="category_id", nullable=false)
    SpendCategory spendCategory

    @OneToOne
    @JoinColumn(name="unit_type_id", referencedColumnName="unit_type_id", nullable=false)
    UnitType unitType

    String getEntryDateString() {
        DATE_FORMATTER.format(this.entryDate)
    }

    JournalEntry toJournalEntry(JournalEntryCmd theEntry, EstablishmentVisit theVisit, SpendCategory theCategory, UnitType theUnitType) {
        this.entryDate = theVisit.visitDate
        this.description = theEntry.description
        this.rateAmount = theEntry.rateAmount
        this.quantity = theEntry.quantity
        this.baseAmount = theEntry.baseAmount
        this.discountAmount = theEntry.discountAmount
        this.isTaxable = theEntry.isTaxable
        this.taxAmount = (this.isTaxable) ? (theEntry.taxAmount ?: 0.0d) : 0.0d
        this.tipAmount = theEntry.tipAmount ?: 0.0d
        this.finalAmount = theEntry.finalAmount
        this.comments = theEntry.comments
        this.establishmentVisit = theVisit
        this.spendCategory = theCategory
        this.unitType = theUnitType
        this
    }
}
