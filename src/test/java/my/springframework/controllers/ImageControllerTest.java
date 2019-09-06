package my.springframework.controllers;

import my.springframework.commands.RecipeCommand;
import my.springframework.services.ImageService;
import my.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

  @Mock
  ImageService imageService;

  @Mock
  RecipeService recipeService;

  ImageController controller;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    controller = new ImageController(imageService, recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void getImageForm() throws Exception {
    //given
    RecipeCommand command = new RecipeCommand();
    command.setId("1");

    when(recipeService.findCommandById(anyString())).thenReturn(command);

    //when
    mockMvc.perform(get("/recipe/1/image"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"));

    verify(recipeService, times(1)).findCommandById(anyString());
  }

  @Test
  public void handleImagePost() throws Exception {
    MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
            "Spring Framework My".getBytes());

    mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/recipe/1/show"));

    verify(imageService, times(1)).saveImageFile(anyString(), any());

  }

  @Test
  public void renderImageFromDB() throws Exception{
    //given
    RecipeCommand command = new RecipeCommand();
    command.setId("1");

    String s = "fake image text";
    Byte[] byteBoxes = new Byte[s.getBytes().length];

    int i = 0;

    for (byte primByte : s.getBytes()){
      byteBoxes[i++] = primByte;
    }

    command.setImage(byteBoxes);

    when(recipeService.findCommandById(anyString())).thenReturn(command);

    //when
    MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
            .andExpect(status().isOk())
            .andReturn().getResponse();

    byte[] responseBytes = response.getContentAsByteArray();
    assertEquals(s.getBytes().length, responseBytes.length);
  }
}