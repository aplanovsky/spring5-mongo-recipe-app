package my.springframework.services;

import my.springframework.converters.RecipeCommandToRecipe;
import my.springframework.repositories.RecipeRepository;
import my.springframework.commands.RecipeCommand;
import my.springframework.converters.RecipeToRecipeCommand;
import my.springframework.domain.Recipe;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

  public static final String NEW_DESCRIPTION = "New Description";

  @Autowired
  RecipeService recipeService;

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Autowired
  RecipeToRecipeCommand recipeToRecipeCommand;

  @Transactional
  @Test
  public void testSaveOfDescription() throws Exception{
    //given
    Iterable<Recipe> recipes = recipeRepository.findAll();
    Recipe testRecipe = recipes.iterator().next();
    RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

    //when
    testRecipeCommand.setDescription(NEW_DESCRIPTION);
    RecipeCommand saveRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

    //then
    assertEquals(NEW_DESCRIPTION, saveRecipeCommand.getDescription());
    assertEquals(testRecipe.getId(), saveRecipeCommand.getId());
    assertEquals(testRecipe.getCategories().size(), saveRecipeCommand.getCategories().size());
    assertEquals(testRecipe.getIngredients().size(), saveRecipeCommand.getIngredients().size());

  }
}
