package serialization.Convertors;

public interface IGenericConverter
{
    <T> T GetConvertedValue(String valueToConvert);
}
