package my.springframework.converters;

import my.springframework.commands.IngredientCommand;
import my.springframework.domain.Ingredient;
import org.springframework.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

  private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

  public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
    this.uomConverter = uomConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public IngredientCommand convert(Ingredient ingredient){
    if (ingredient == null){
      return null;
    }
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredient.getId());
    if (ingredient.getRecipe() != null){
      ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
    }
    ingredientCommand.setAmount(ingredient.getAmount());
    ingredientCommand.setDescription(ingredient.getDescription());
    ingredientCommand.setUom(uomConverter.convert(ingredient.getUom()));
    return ingredientCommand;
  }
}
