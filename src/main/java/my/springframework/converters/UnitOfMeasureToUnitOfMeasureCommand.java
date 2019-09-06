package my.springframework.converters;

import my.springframework.commands.UnitOfMeasureCommand;
import my.springframework.domain.UnitOfMeasure;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure){
    if (unitOfMeasure != null){
      final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
      uomc.setId(unitOfMeasure.getId());
      uomc.setDescription(unitOfMeasure.getDescription());
      return uomc;
    }
    return null;
  }
}
