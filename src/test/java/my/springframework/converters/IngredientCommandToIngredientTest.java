package my.springframework.converters;

import my.springframework.domain.Ingredient;
import my.springframework.domain.Recipe;
import my.springframework.commands.IngredientCommand;
import my.springframework.commands.UnitOfMeasureCommand;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

  public static final Recipe RECIPE = new Recipe();
  public static final BigDecimal AMOUNT = new BigDecimal("1");
  public static final String DESCRIPTION = "Cheeseburger";
  public static final String UOM_ID = "1";
  public static final String ID_VALUE = "2";

  IngredientCommandToIngredient converter;


  @Before
  public void setUp() throws Exception {
    converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

  }

  @Test
  public void testNullConvert() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new IngredientCommand()));
  }

  @Test
  public void convert() throws Exception {
    //given
    IngredientCommand command = new IngredientCommand();
    command.setId(ID_VALUE);
    command.setAmount(AMOUNT);
    command.setDescription(DESCRIPTION);
    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(UOM_ID);
    command.setUom(unitOfMeasureCommand);
    //when
    Ingredient ingredient = converter.convert(command);
    //then
    assertNotNull(ingredient);
    assertNotNull(ingredient.getUom());
    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
    assertEquals(UOM_ID, ingredient.getUom().getId());
  }

  @Test
  public void testConvertNullUOM() throws Exception {

    //given
    IngredientCommand command = new IngredientCommand();
    command.setId(ID_VALUE);
    command.setAmount(AMOUNT);
    command.setDescription(DESCRIPTION);

    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

    //when
    Ingredient ingredient = converter.convert(command);

    //then
    assertNotNull(ingredient);
    assertNull(ingredient.getUom());
    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
  }

}