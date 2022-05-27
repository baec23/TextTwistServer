package com.baec.texttwist.word.service;

import com.baec.texttwist.word.model.AlphaWord;

public interface WordService
{
    AlphaWord getRandomWord(int length);
    AlphaWord saveWord(AlphaWord alphaWord);
}
