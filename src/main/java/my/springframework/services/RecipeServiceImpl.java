package my.springframework.services;

import lombok.extern.slf4j.Slf4j;
import my.springframework.commands.RecipeCommand;
import my.springframework.converters.RecipeCommandToRecipe;
import my.springframework.domain.Recipe;
import my.springframework.exceptions.NotFoundException;
import my.springframework.repositories.RecipeRepository;
import my.springframework.converters.RecipeToRecipeCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
      this.recipeRepository = recipeRepository;
      this.recipeCommandToRecipe = recipeCommandToRecipe;
      this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes () {
      log.debug("I'm in the service");

      Set<Recipe> recipeSet = new HashSet<>();
      recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
      return recipeSet;
    }

    @Override
    public Recipe findById (String id){

      Optional<Recipe> recipeOptional = recipeRepository.findById(id);

      if (!recipeOptional.isPresent()) {
        //throw new RuntimeException("Recipe Not Found!");
        throw new NotFoundException("Recipe Not Found. For ID value: " + id.toString());
      }

      return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
    return recipeToRecipeCommand.convert(findById(id));
  }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command){
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return  recipeToRecipeCommand.convert(savedRecipe);
  }

    @Override
    public void deleteById(String idToDelete) {
    recipeRepository.deleteById(idToDelete);
  }
}
