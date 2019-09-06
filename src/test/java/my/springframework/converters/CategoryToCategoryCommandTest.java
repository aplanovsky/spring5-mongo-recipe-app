package my.springframework.converters;

import my.springframework.domain.Category;
import my.springframework.commands.CategoryCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

  public static final String ID_VALUE = "1";
  public static final String DESCRIPTION = "descript";
  CategoryToCategoryCommand convert;

  @Before
  public void setUp() throws Exception {
    convert = new CategoryToCategoryCommand();
  }


  @Test
  public void convert() {
    //given
    Category category = new Category();
    category.setId(ID_VALUE);
    category.setDescription(DESCRIPTION);

    //when
    CategoryCommand categoryCommand = convert.convert(category);

    //then
    assertEquals(ID_VALUE, categoryCommand.getId());
    assertEquals(DESCRIPTION, categoryCommand.getDescription());
  }
}