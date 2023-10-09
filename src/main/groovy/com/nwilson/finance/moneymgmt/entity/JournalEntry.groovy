package com.nwilson.finance.moneymgmt.entity

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
class JournalEntry {

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy")

    @TableGenerator(name="journalEntryGen", table="journal_entry_seq")

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="journalEntryGen")
    @Column(name="journal_entry_id", nullable=false)
    Integer id

    @Column(name="entry_date", nullable=false)
    Date entryDate

    @Column(name="description", nullable=false, length=100)
    String description

    @Column(name="base_amount", nullable=false)
    Double baseAmount

    @Column(name="discount_amount", nullable=true)
    Double discountAmount

    @Column(name="is_taxable", nullable=false)
    @Convert(converter=TrueFalseConverter)
    boolean isTaxable

    @Column(name="final_amount", nullable=false)
    Double finalAmount

    @Column(name="comments", nullable=true, length=500)
    String comments

    @OneToOne
    @JoinColumn(name="category_id", referencedColumnName="category_id", nullable=true)
    SpendCategory theCategory

    @OneToOne
    @JoinColumn(name="store_id", referencedColumnName="establishment_id", nullable=true)
    Establishment theStore

    @OneToOne
    @JoinColumn(name="transaction_type_id", referencedColumnName="transaction_type_id", nullable=true)
    TransactionType theTxType

    String getEntryDateString() {
        DATE_FORMATTER.format(this.entryDate)
    }
}
