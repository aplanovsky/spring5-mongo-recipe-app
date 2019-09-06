package my.springframework.services;

import my.springframework.domain.Recipe;
import my.springframework.commands.RecipeCommand;

import java.util.Set;

public interface RecipeService {

  Set<Recipe> getRecipes();

  Recipe findById(String id);
  RecipeCommand findCommandById(String id);
  RecipeCommand saveRecipeCommand(RecipeCommand command);


  void deleteById(String idToDelete);
}
