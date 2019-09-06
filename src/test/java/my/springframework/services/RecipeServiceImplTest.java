package my.springframework.services;

import my.springframework.converters.RecipeCommandToRecipe;
import my.springframework.domain.Recipe;
import my.springframework.exceptions.NotFoundException;
import my.springframework.repositories.RecipeRepository;
import my.springframework.converters.RecipeToRecipeCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

  RecipeService recipeService;

  @Mock
  RecipeRepository recipeRepository;
  @Mock
  RecipeToRecipeCommand recipeToRecipeCommand;
  @Mock
  RecipeCommandToRecipe recipeCommandToRecipe;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
  }


  @Test
  public void getRecipeByIdTest() throws Exception {

    Recipe recipe = new Recipe();
    recipe.setId("1");
    Optional<Recipe> recipeOptional = Optional.of(recipe);


    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById("1");

    assertNotNull("Null recipe returned", recipeReturned);
    verify(recipeRepository, times(1)).findById(anyString());
    verify(recipeRepository, never()).findAll();
  }

  @Test(expected = NotFoundException.class)
  public void getRecipeByIdTestNotFound() throws Exception{
    Optional<Recipe> recipeOptional = Optional.empty();


    when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

    Recipe recipeReturned = recipeService.findById("1");
    //should go boom
  }

  @Test
  public void getRecipeTest() throws Exception {

    Recipe recipe = new Recipe();
    HashSet recipesData = new HashSet();
    recipesData.add(recipe);


    when(recipeService.getRecipes()).thenReturn(recipesData);

    Set<Recipe> recipes = recipeService.getRecipes();

    assertEquals(recipes.size(), 1);
    verify(recipeRepository, times(1)).findAll();
    verify(recipeRepository, never()).findById(anyString());

  }
  @Test
  public void testDeleteById() throws Exception{
    //given
    String idToDelete = String.valueOf("2");
    //when
    recipeService.deleteById(idToDelete);
    //no 'when', since method void return type
    //then
    verify(recipeRepository, times(1)).deleteById(anyString());
  }
}