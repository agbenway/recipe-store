package com.agb.recipe.storage.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agb.recipe.storage.jpa.domain.RecipeEntity;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>
{

}
