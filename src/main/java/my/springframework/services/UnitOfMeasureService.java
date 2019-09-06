package my.springframework.services;

import my.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
  Set<UnitOfMeasureCommand> listAllUoms();
}
