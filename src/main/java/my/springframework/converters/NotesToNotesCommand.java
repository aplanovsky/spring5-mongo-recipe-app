package my.springframework.converters;

import my.springframework.commands.NotesCommand;
import my.springframework.domain.Notes;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

  @Synchronized
  @Nullable
  @Override
  public NotesCommand convert(Notes source){
    if (source == null){
      return null;
    }
    final NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(source.getId());
    notesCommand.setRecipeNotes(source.getRecipeNotes());
    return notesCommand;
  }
}
