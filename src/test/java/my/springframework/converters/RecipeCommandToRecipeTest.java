package my.springframework.converters;

import my.springframework.commands.NotesCommand;
import my.springframework.domain.Difficulty;
import my.springframework.domain.Recipe;
import my.springframework.commands.CategoryCommand;
import my.springframework.commands.IngredientCommand;
import my.springframework.commands.RecipeCommand;
import my.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

  public static final String RECIPE_ID = "1";
  public static final Integer COOK_TIME = Integer.valueOf("5");
  public static final Integer PREP_TIME = Integer.valueOf("7");
  public static final String DESCRIPTION = "My Recipe";
  public static final String DIRECTIONS = "Directions";
  public static final Difficulty DIFFICULTY = Difficulty.EASY;
  public static final Integer SERVINGS = Integer.valueOf("3");
  public static final String SOURCE = "Source";
  public static final String URL = "Some URL";
  public static final String CAT_ID_1 = "1";
  public static final String CAT_ID_2 = "2";
  public static final String INGRED_ID_1 = "3";
  public static final String INGRED_ID_2 = "4";
  public static final String NOTES_ID = "4";
  RecipeCommandToRecipe converter;


  @Before
  public void setUp() throws Exception {
    converter = new RecipeCommandToRecipe(
            new CategoryCommandToCategory(),
            new IngredientCommandToIngredient(
                    new UnitOfMeasureCommandToUnitOfMeasure()),
            new NotesCommandToNotes());
  }

  @Test
  public void testNullObject() throws Exception{
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception{
    assertNotNull(converter.convert(new RecipeCommand()));
  }

  @Test
  public void convert() throws Exception {
    //given
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(RECIPE_ID);
    recipeCommand.setCookTime(COOK_TIME);
    recipeCommand.setPrepTime(PREP_TIME);
    recipeCommand.setDescription(DESCRIPTION);
    recipeCommand.setDifficulty(DIFFICULTY);
    recipeCommand.setDirections(DIRECTIONS);
    recipeCommand.setServings(SERVINGS);
    recipeCommand.setSource(SOURCE);
    recipeCommand.setUrl(URL);

    NotesCommand notes = new NotesCommand();
    notes.setId(NOTES_ID);

    recipeCommand.setNotes(notes);

    CategoryCommand category = new CategoryCommand();
    category.setId(CAT_ID_1);

    CategoryCommand category2 = new CategoryCommand();
    category2.setId(CAT_ID_2);

    recipeCommand.getCategories().add(category);
    recipeCommand.getCategories().add(category2);

    IngredientCommand ingredient = new IngredientCommand();
    ingredient.setId(INGRED_ID_1);

    IngredientCommand ingredient2 = new IngredientCommand();
    ingredient.setId(INGRED_ID_2);

    recipeCommand.getIngredients().add(ingredient);
    recipeCommand.getIngredients().add(ingredient2);

    //when
    Recipe recipe = converter.convert(recipeCommand);

    //then
    assertNotNull(recipe);
    assertEquals(RECIPE_ID, recipe.getId());
    assertEquals(COOK_TIME, recipe.getCookTime());
    assertEquals(PREP_TIME, recipe.getPrepTime());
    assertEquals(DESCRIPTION, recipe.getDescription());
    assertEquals(DIFFICULTY, recipe.getDifficulty());
    assertEquals(DIRECTIONS, recipe.getDirections());
    assertEquals(SERVINGS, recipe.getServings());
    assertEquals(SOURCE, recipe.getSource());
    assertEquals(URL, recipe.getUrl());
    assertEquals(NOTES_ID, recipe.getNotes().getId());
    assertEquals(2, recipe.getCategories().size());
    assertEquals(2, recipe.getIngredients().size());
  }
}