package com.baec.texttwist.word.repository;

import com.baec.texttwist.word.model.WordSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordSolutionsRepository extends JpaRepository<WordSolution, Long>
{
    @Query("FROM WordSolution WHERE parentAlpha = :alphaLetters ORDER BY word ASC")
    List<WordSolution> findAllByParentAlpha(@Param("alphaLetters") String alphaLetters);

    @Query("FROM WordSolution WHERE parentAlpha = :alphaLetters AND length = :length ORDER BY word ASC")
    List<WordSolution> findAllByParentAlphaAndLength(@Param("alphaLetters")String alphaLetters, @Param("length") int length);
}
