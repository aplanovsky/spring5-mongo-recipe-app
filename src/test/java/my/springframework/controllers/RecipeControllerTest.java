package my.springframework.controllers;

import my.springframework.domain.Recipe;
import my.springframework.exceptions.NotFoundException;
import my.springframework.commands.RecipeCommand;
import my.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

  @Mock
  RecipeService recipeService;

  RecipeController controller;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    controller = new RecipeController(recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testGetRecipe() throws Exception {
    Recipe recipe = new Recipe();
    recipe.setId("1");

    when(recipeService.findById(anyString())).thenReturn(recipe);

    mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testGetNewRecipeForm() throws Exception{
    RecipeCommand command = new RecipeCommand();

    mockMvc.perform(get("/recipe/new"))
    .andExpect(status().isOk())
    .andExpect(view().name("recipe/recipeform"))
    .andExpect(model().attributeExists("recipe"));
  }

//  @Test
//  public void testPostNewRecipeForm() throws Exception{
//    RecipeCommand command = new RecipeCommand();
//
//    command.setId(2L);
//
//    when(recipeService.saveRecipeCommand(any())).thenReturn(command);
//
//    mockMvc.perform(post("/recipe")
//    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//    .param("id", "")
//    .param("description", "some string"))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(view().name("redirect:/recipe/2/show"));
//  }

  @Test
  public void testPostNewRecipeFormValidationFinal() throws Exception{
    RecipeCommand command = new RecipeCommand();
    command.setId("2");

    when(recipeService.saveRecipeCommand(any())).thenReturn(command);

    mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", ""))

            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipe/recipeform"));
  }


  @Test
  public void testGetUpdateView() throws Exception{
    RecipeCommand command = new RecipeCommand();
    command.setId("2");

    when(recipeService.findCommandById(anyString())).thenReturn(command);

    mockMvc.perform(get("/recipe/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipeform"))
            .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testGetRecipeNotFound() throws Exception{
    Recipe recipe = new Recipe();
    recipe.setId("2");

    when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

    mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("404error"));
  }

//  @Test
//  public void testGetRecipeNumberFormatException() throws Exception{
//
//    //when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
//
//    mockMvc.perform(get("/recipe/asdf/show"))
//            .andExpect(status().isBadRequest())
//            .andExpect(view().name("400error"));
//  }

  @Test
  public void testDeleteAction() throws Exception{
    mockMvc.perform(get("/recipe/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    verify(recipeService, times(1)).deleteById(anyString());
  }
}