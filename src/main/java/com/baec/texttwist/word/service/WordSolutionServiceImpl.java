package com.baec.texttwist.word.service;

import com.baec.texttwist.word.model.WordSolution;
import com.baec.texttwist.word.repository.WordSolutionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WordSolutionServiceImpl implements WordSolutionService
{
    private final WordSolutionsRepository wordSolutionsRepository;
    private Map<Integer, List<String>> lengthWordsMap = new HashMap<>();

    @Override
    public List<String> getSolutionsByLength(int length, String baseWord)
    {
        List<WordSolution> solutions = wordSolutionsRepository.findAllByParentAlphaAndLength(baseWord, length);
        if(solutions == null || solutions.size() == 0)
        {
            calculateAndStoreSolution(baseWord);
        }
        solutions = wordSolutionsRepository.findAllByParentAlphaAndLength(baseWord, length);
        List<String> toReturn = new ArrayList<>();
        for(WordSolution solution : solutions)
        {
            toReturn.add(solution.getWord());
        }
        return toReturn;
    }

    @Override
    public List<String> getAllSolutions(String baseWord)
    {
        List<WordSolution> solutions = wordSolutionsRepository.findAllByParentAlpha(baseWord);
        if(solutions == null || solutions.size() == 0)
        {
            calculateAndStoreSolution(baseWord);
        }
        solutions = wordSolutionsRepository.findAllByParentAlpha(baseWord);
        List<String> toReturn = new ArrayList<>();
        for(WordSolution solution : solutions)
        {
            toReturn.add(solution.getWord());
        }
        return toReturn;
    }

    @Override
    public void init(FileInputStream is)
    {
        log.info("INIT CALLED");
        Scanner sc = new Scanner(is);
        while(sc.hasNext())
        {
            String word = sc.next();
            if(!lengthWordsMap.containsKey(word.length()))
                lengthWordsMap.put(word.length(), new ArrayList<>());
            lengthWordsMap.get(word.length()).add(word);
        }
        log.info("INIT FINISHED");
    }

    private void calculateAndStoreSolution(String alphaWord)
    {
        Map<Character, Integer> parentLetterCountMap = getLetterCountMap(alphaWord);
        for(int i = 3; i <= alphaWord.length(); i++)
        {
            for(String s : lengthWordsMap.get(i))
            {
                Map<Character, Integer> currLetterCountMap = getLetterCountMap(s);
                if(isSolution(parentLetterCountMap, currLetterCountMap))
                {
                    log.info("Saving solution {} for parent word {}", s, alphaWord);
                    wordSolutionsRepository.save(new WordSolution(null, alphaWord, s, s.length()));
                }
            }
        }
    }

    private Map<Character, Integer> getLetterCountMap(String s)
    {
        Map<Character, Integer> toReturn = new HashMap<>();
        for(char a : s.toCharArray())
        {
            if(!toReturn.containsKey(a))
                toReturn.put(a, 0);
            toReturn.put(a, toReturn.get(a) + 1);
        }
        return toReturn;
    }

    private boolean isSolution(Map<Character, Integer> parentMap, Map<Character, Integer> childMap)
    {
        for(Character c : childMap.keySet())
        {
            if(!parentMap.containsKey(c))
                return false;
            if(parentMap.get(c) < childMap.get(c))
                return false;
        }
        return true;
    }
}
