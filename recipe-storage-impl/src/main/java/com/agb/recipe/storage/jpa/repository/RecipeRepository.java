package com.agb.recipe.storage.jpa.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agb.recipe.storage.jpa.domain.RecipeEntity;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<RecipeEntity, Long>
{
    RecipeEntity findFirstByOrderByAddedDateDesc ();

    Optional<RecipeEntity> findByLink (String link);

    Optional<RecipeEntity> findByMessageId (String messageId);
}
