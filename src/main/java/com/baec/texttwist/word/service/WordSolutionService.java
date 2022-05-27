package com.baec.texttwist.word.service;

import java.io.FileInputStream;
import java.util.List;

public interface WordSolutionService
{
    List<String> getSolutionsByLength(int length, String baseWord);
    List<String> getAllSolutions(String baseWord);
    void init(FileInputStream is);
}
