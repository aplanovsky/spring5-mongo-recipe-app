package my.springframework.converters;

import my.springframework.domain.Category;
import my.springframework.commands.CategoryCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

  public static final String ID_VALUE = "1";
  public static final String DESCRIPTION = "description";

  CategoryCommandToCategory converter;

  @Before
  public void setUp() throws Exception {
    converter = new CategoryCommandToCategory();
  }

  @Test
  public void testNullObject() throws Exception{
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception{
    assertNotNull(converter.convert(new CategoryCommand()));
  }



  @Test
  public void convert() throws Exception {
    //given
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID_VALUE);
    categoryCommand.setDescription(DESCRIPTION);

    //when
    Category category = converter.convert(categoryCommand);

    //then
    assertEquals(ID_VALUE, category.getId());
    assertEquals(DESCRIPTION, category.getDescription());
  }
}