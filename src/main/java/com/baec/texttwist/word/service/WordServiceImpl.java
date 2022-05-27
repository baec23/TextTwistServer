package com.baec.texttwist.word.service;

import com.baec.texttwist.word.model.AlphaWord;
import com.baec.texttwist.word.model.WordSolution;
import com.baec.texttwist.word.repository.AlphaWordRepository;
import com.baec.texttwist.word.repository.WordSolutionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WordServiceImpl implements WordService
{
    private final AlphaWordRepository alphaWordRepository;
    private final WordSolutionsRepository wordSolutionsRepository;

    @Override
    public AlphaWord getRandomWord(int length)
    {
        AlphaWord alphaWord = alphaWordRepository.getRandomAlphaLetters(length);
        List<WordSolution> solutions = wordSolutionsRepository.findAllByParentAlpha(alphaWord.getAlphaLetters());
        if(solutions == null || solutions.isEmpty())
        {

        }
        return alphaWord;
    }

    @Override
    public AlphaWord saveWord(AlphaWord toSave)
    {
        if(alphaWordRepository.findByAlphaLetters(toSave.getAlphaLetters()) == null)
            return alphaWordRepository.save(toSave);
        return null;
    }
}
