package com.library.librarymanagementsystemapi.converters;

import com.library.librarymanagementsystemapi.enums.BorrowingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BorrowingStatusConverter implements AttributeConverter<BorrowingStatus, String> {

    @Override
    public String convertToDatabaseColumn(BorrowingStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();  // Stores "BORROWED", "RETURNED", "OVERDUE"
    }

    @Override
    public BorrowingStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return BorrowingStatus.fromString(dbData);
    }
}