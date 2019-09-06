package my.springframework.converters;

import my.springframework.commands.NotesCommand;
import my.springframework.domain.Notes;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

  @Synchronized
  @Nullable
  @Override
  public Notes convert(NotesCommand source){
    if (source == null){
      return null;
    }
    final Notes notes = new Notes();
    notes.setId(source.getId());
    notes.setRecipeNotes(source.getRecipeNotes());
    return notes;
  }
}
