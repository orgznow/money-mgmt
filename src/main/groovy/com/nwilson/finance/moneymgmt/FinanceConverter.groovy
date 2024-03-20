package com.nwilson.finance.moneymgmt

import com.nwilson.finance.moneymgmt.entity.Establishment
import com.nwilson.finance.moneymgmt.entity.EstablishmentVisit
import com.nwilson.finance.moneymgmt.entity.JournalEntry
import com.nwilson.finance.moneymgmt.entity.SpendCategory
import com.nwilson.finance.moneymgmt.entity.TransactionType
import com.nwilson.finance.moneymgmt.entity.UnitType

class FinanceConverter {
    static Map toJournalEntryMap(JournalEntry entry) {
        [
            id: entry.id,
            entryDate: entry.entryDate,
            description: entry.description,
            rateAmount: entry.rateAmount,
            quantity: entry.quantity,
            baseAmount: entry.baseAmount,
            discountAmount: entry.discountAmount,
            isTaxable: entry.isTaxable,
            taxAmount: entry.taxAmount,
            tipAmount: entry.tipAmount,
            finalAmount: entry.finalAmount,
            comments: entry.comments,
            establishmentVisitId: entry.establishmentVisit.id,
            spendCategory: toSpendCategoryMap(entry.spendCategory),
            unitType: toUnitTypeMap(entry.unitType),
            entryDateString: entry.entryDateString
        ]
    }

    static Map toTransactionType(TransactionType txType) {
        [
            id: txType.id,
            name: txType.name,
            description: txType.description,
            isDefault: txType.isDefault
        ]
    }

    static Map toUnitTypeMap(UnitType unitType) {
        [
            id: unitType.id,
            name: unitType.name,
            description: unitType.description,
            isDefault: unitType.isDefault
        ]
    }

    static Map toSpendCategoryMap(SpendCategory spendCategory) {
        [
            id: spendCategory.id,
            name: spendCategory.name,
            description: spendCategory.description,
            isExpense: spendCategory.isExpense,
            isDefault: spendCategory
        ]
    }

    static Map toEstablishmentMap(Establishment store) {
        [
            id: store.id,
            name: store.name,
            description: store.description,
            taxPercentage: store.taxPercentage,
            zipCode: store.zipCode
        ]
    }

    static Map toEstablishmentVisitMap(EstablishmentVisit visit) {
        [
            id: visit.id,
            visitDate: visit.visitDate,
            description: visit.description,
            visitTotalAmount: visit.visitTotalAmount,
            taxPercentage: visit.taxPercentage,
            comments: visit.comments,
            establishment: toEstablishmentMap(visit.establishment),
            transactionType: toTransactionType(visit.transactionType),
            journalEntries: visit.journalEntries.collect { toJournalEntryMap(it) },
            visitDateString: visit.visitDateString,
            formattedComments: visit.formattedComments
        ]
    }
}
