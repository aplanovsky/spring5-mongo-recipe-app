package my.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import my.springframework.commands.IngredientCommand;
import my.springframework.commands.RecipeCommand;
import my.springframework.commands.UnitOfMeasureCommand;
import my.springframework.services.IngredientService;
import my.springframework.services.RecipeService;
import my.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UnitOfMeasureService unitOfMeasureService;

  public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
    this.recipeService = recipeService;
    this.ingredientService = ingredientService;
    this.unitOfMeasureService = unitOfMeasureService;
  }

  @GetMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("Getting ingredient list for recipe id: " + recipeId);

    //use command object to avoid lazy load errors in Thymeleaf
    model.addAttribute("recipe", recipeService.findCommandById(String.valueOf(recipeId)));

    return "recipe/ingredient/list";
  }

  @GetMapping("recipe/{recipeId}/ingredient/new")
  public String newRecipe(@PathVariable String recipeId, Model model){
    //make sure we have a good id value
    RecipeCommand recipeCommand = recipeService.findCommandById(String.valueOf(recipeId));
    //todo raise exeption if null

    //need to return back parent id hidden form property
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(String.valueOf(recipeId));
    model.addAttribute("ingredient", ingredientCommand);
    //init uom
    ingredientCommand.setUom(new UnitOfMeasureCommand());

    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
    return "recipe/ingredient/ingredientform";
  }


  @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
  public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(String.valueOf(recipeId), String.valueOf(id)));
    return "recipe/ingredient/show";
  }

  @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
  public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {
    model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(String.valueOf(recipeId), String.valueOf(id)));

    model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

    return "recipe/ingredient/ingredientform";
  }
  @PostMapping("recipe/{recipeId}/ingredient")
  public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
    IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
    log.debug("saved recipe id: " + savedCommand.getRecipeId());
    log.debug("saved ingredient id: " + savedCommand.getId());

    return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";

  }
  @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
  public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){
    log.debug("deleting ingredient id: " + id);
    ingredientService.deleteById(String.valueOf(recipeId), String.valueOf(id));

    return "redirect:/recipe/" + recipeId + "/ingredients";
  }

}