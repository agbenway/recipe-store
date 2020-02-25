package com.agb.recipe.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.agb.recipe.controller.domain.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
@ContextConfiguration(classes = { RecipeControllerTestConfiguration.class })
public class RecipeControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRetrieveRecipes () throws Exception
    {
        MvcResult mvcResult = mvc.perform(get("/recipes/{id}", 15)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Recipe recipe = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Recipe.class);

        assertNotNull(recipe);
        assertEquals(Long.valueOf(15), recipe.getRecipeId());
        assertEquals("https://www.recipetineats.com/a-great-marinade-for-pork-chops/", recipe.getLink());
        assertEquals("A Great Pork Chop Marinade | RecipeTin Eats", recipe.getTitle());
        assertEquals(
                "A great marinade for pork chops - makes them extra juicy, infuses with savoury flavour and a touch of sweet that caramelises beautifully.",
                recipe.getDescription());
        assertEquals(LocalDate.of(2020, 1, 5), recipe.getDateAdded());
    }

    @Test
    public void testRetrieveRecipes_Exception () throws Exception
    {
        MvcResult mvcResult = mvc.perform(get("/recipes/{id}", 22)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String error = mvcResult.getResponse().getContentAsString();

        assertNotNull(error);
        assertThat(error, CoreMatchers.containsString("message"));
        assertThat(error, CoreMatchers.containsString("Recipe not found"));
    }

}
