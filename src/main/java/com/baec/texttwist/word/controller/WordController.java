package com.baec.texttwist.word.controller;

import com.baec.texttwist.word.model.AlphaWord;
import com.baec.texttwist.word.service.WordService;
import com.baec.texttwist.word.service.WordSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WordController
{
    private final WordService wordService;
    private final WordSolutionService wordSolutionService;

    @GetMapping("/word/random")
    public ResponseEntity<AlphaWord> getRandomWord(@RequestParam int length)
    {
        AlphaWord toReturn = wordService.getRandomWord(length);
        return ResponseEntity.ok().body(toReturn);
    }

    @GetMapping("/word/solution/{word}")
    public ResponseEntity<List<String>> getSolutions(@PathVariable("word") String word)
    {
        return ResponseEntity.ok().body(wordSolutionService.getAllSolutions(word));
    }
}
