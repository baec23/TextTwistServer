package com.baec.texttwist.word.repository;

import com.baec.texttwist.word.model.AlphaWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlphaWordRepository extends JpaRepository<AlphaWord, Long>
{
    @Query(value = """
            SELECT * FROM alpha_word
            WHERE length = :length
            ORDER BY RAND()
            LIMIT 1""", nativeQuery = true)
    AlphaWord getRandomAlphaLetters(@Param("length") int length);

    AlphaWord findByAlphaLetters(String alphaLetters);
}
