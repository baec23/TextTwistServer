package com.baec.texttwist.word.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaWord
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer length;
    private String alphaLetters;
}
