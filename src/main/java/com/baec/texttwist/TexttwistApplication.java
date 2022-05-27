package com.baec.texttwist;

import com.baec.texttwist.word.model.AlphaWord;
import com.baec.texttwist.word.service.WordService;
import com.baec.texttwist.word.service.WordSolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class TexttwistApplication
{
    @Autowired
    ResourceLoader resourceLoader;

    public static void main(String[] args)
    {
        SpringApplication.run(TexttwistApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //
//    @Bean
//    CommandLineRunner run(UserService userService)
//    {
//        return args ->
//        {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_MANAGER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            userService.saveUser(new AppUser(null, "Test User", "testuser1", "testtest", new ArrayList<>()));
//
//            userService.addRoleToUser("testuser1", "ROLE_USER");
//        };
//    }
//
    @Bean
    CommandLineRunner run2(WordSolutionService wordSolutionService, WordService wordService)
    {
        return args ->
        {
            FileInputStream is = null;
            Resource resource = resourceLoader.getResource("classpath:static/words_length_3_to_8.txt");
            File file = resource.getFile();
            try
            {
                is = new FileInputStream(file);
                wordSolutionService.init(is);
                //is = new FileInputStream(file);
                //saveWords(wordService, is);
            }
            catch(IOException e)
            {
                log.error("File read error!");
            }
            finally
            {
                if(is != null)
                    is.close();
            }
        };
    }

    private void saveWords(WordService wordService, FileInputStream is)
    {
        Scanner sc = new Scanner(is);
        while(sc.hasNext())
        {
            String word = sc.next();
            char[] arr = word.toLowerCase().toCharArray();
            Arrays.sort(arr);
            String sortedWord = new String(arr);
            if(sortedWord.length() >= 3 && sortedWord.length() <= 8)
            {
                log.info("Saving AlphaWord : {}", sortedWord);
                wordService.saveWord(new AlphaWord(null, sortedWord.length(), sortedWord));
            }
        }
    }

    private void saveCulledFile(FileInputStream is) throws IOException
    {
        File file = new File("words_length_3_to_8.txt");
        if(!file.exists())
            file.createNewFile();
        Scanner sc = new Scanner(is);
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        while(sc.hasNext())
        {
            String word = sc.next();
            if(word.length() >= 3 && word.length() <= 8)
            {
                printWriter.println(word);
                log.info("saving word : {}", word);
            }
        }
        printWriter.close();
        fileWriter.close();
        log.info("DONE SAVING FILE");
    }
}
