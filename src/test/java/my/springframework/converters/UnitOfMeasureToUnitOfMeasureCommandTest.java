package my.springframework.converters;

import my.springframework.domain.UnitOfMeasure;
import my.springframework.commands.UnitOfMeasureCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

  public static final String DESCRIPTION = "Description";

  public static final String LONG_VALUE = "1";

  UnitOfMeasureToUnitOfMeasureCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new UnitOfMeasureToUnitOfMeasureCommand();
  }

  @Test
  public void testNullObjectConvert() throws Exception{
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObj() throws Exception{
    assertNotNull(converter.convert(new UnitOfMeasure()));
  }

  @Test
  public void convert() throws Exception {
    //given
    UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(LONG_VALUE);
    uom.setDescription(DESCRIPTION);
    //when
    UnitOfMeasureCommand uoms = converter.convert(uom);
    //then
    assertEquals(LONG_VALUE, uoms.getId());
    assertEquals(DESCRIPTION, uoms.getDescription());
  }
}