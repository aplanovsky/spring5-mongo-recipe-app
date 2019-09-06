package my.springframework.services;

import lombok.extern.slf4j.Slf4j;
import my.springframework.domain.Recipe;
import my.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  private final RecipeRepository recipeRepository;

  public ImageServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  @Transactional
  public void saveImageFile(String recipeId, MultipartFile file) {

    try {
      Recipe recipe = recipeRepository.findById(recipeId).get();

      Byte[] byteObject = new Byte[file.getBytes().length];
      int i = 0;
      for (byte b : file.getBytes()){
        byteObject[i++] = b;
      }

      recipe.setImage(byteObject);

      recipeRepository.save(recipe);

    }catch (IOException e){
      //todo handle better
      log.error("Error occurred", e);
      e.printStackTrace();
    }
  }
}
