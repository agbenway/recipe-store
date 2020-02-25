package com.agb.recipe.controller.mock;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.domain.RecipeSearchRequest;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.service.RecipeService;

public class MockRecipeService implements RecipeService
{

    @Override
    public RecipeDto retireveMostRecentRecipe ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RecipeDto createRecipe (RecipeDto recipe) throws RecipeNotFoundException, DuplicateRecipeException
    {
        RecipeDto retval = recipe;
        if (recipe != null && recipe.getLink() != null)
        {
            retval.setRecipeId(11L);
            retval.setTitle("Homemade Garlic Naan - Host The Toast");
            retval.setDescription(
                    "Homemade Garlic Naan. Even if you've never made bread at home before, this soft, chewy, garlicky Indian flatbread is easy to make and well worth the effort.");
            // "link": "https://hostthetoast.com/homemade-garlic-naan/print/12158/",
        }

        return retval;
    }

    @Override
    public RecipeDto retrieveRecipe (Long recipeId) throws RecipeNotFoundException
    {
        RecipeDto retval = null;
        if (recipeId == 15)
        {
            retval = new RecipeDto();
            retval.setRecipeId(15L);
            retval.setLink("https://www.recipetineats.com/a-great-marinade-for-pork-chops/");
            retval.setTitle("A Great Pork Chop Marinade | RecipeTin Eats");
            retval.setDescription(
                    "A great marinade for pork chops - makes them extra juicy, infuses with savoury flavour and a touch of sweet that caramelises beautifully.");
            retval.setMessageDate(LocalDate.of(2020, 01, 05));
            retval.setMessageId("1707283fd8e9b6ba");
        }
        else
        {
            throw new RecipeNotFoundException("Recipe not found");
        }
        return retval;
    }

    @Override
    public Page<RecipeDto> retrieveRecipes (RecipeSearchRequest request)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
