package com.library.librarymanagementsystemapi.converters;

import com.library.librarymanagementsystemapi.enums.BookGenre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookGenreConverter implements AttributeConverter<BookGenre, String> {

    @Override
    public String convertToDatabaseColumn(BookGenre genre) {
        return genre == null ? null : genre.getDisplayName();
    }

    @Override
    public BookGenre convertToEntityAttribute(String dbData) {
        return dbData == null ? null : BookGenre.fromString(dbData);
    }
}
