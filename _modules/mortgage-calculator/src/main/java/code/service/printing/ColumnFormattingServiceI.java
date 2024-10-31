package code.service.printing;

public interface ColumnFormattingServiceI {
    Format getEntryFormat(Format keyFormat, Format valueFormat);
    Format getColumnFormat(Format format);
    Format getColumnFormatInTurns(Format... formats);
    Format getColumnFormatInLine(Format... formats);
}