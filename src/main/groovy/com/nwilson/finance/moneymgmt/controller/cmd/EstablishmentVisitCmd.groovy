package com.nwilson.finance.moneymgmt.controller.cmd

import groovy.transform.ToString
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat


@ToString(includeNames=true)
class EstablishmentVisitCmd {

    Integer id

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date visitDate

    @Size(max=100)
    String description

    @NotNull
    BigDecimal visitTotalAmount

    Double taxPercentage

    @Size(max=500)
    String comments

    @NotNull
    Integer establishmentId

    @NotNull
    Integer transactionTypeId

    @Size(min=1)
    @Valid
    List<JournalEntryCmd> journalEntries
}

@ToString(includeNames=true)
class JournalEntryCmd {
    Integer id

    Date entryDate

    @NotNull
    @Size(max=100)
    String description

    @NotNull
    BigDecimal rateAmount

    @NotNull
    Double quantity

    @NotNull
    BigDecimal baseAmount

    BigDecimal discountAmount

    @NotNull
    Boolean isTaxable

    BigDecimal taxAmount

    BigDecimal tipAmount

    @NotNull
    BigDecimal finalAmount

    @Size(max=500)
    String comments

    @NotNull
    Integer spendCategoryId

    @NotNull
    Integer unitTypeId
}
