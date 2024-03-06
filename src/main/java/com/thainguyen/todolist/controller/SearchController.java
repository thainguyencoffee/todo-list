package com.thainguyen.todolist.controller;

import com.thainguyen.todolist.model.ToDo;
import com.thainguyen.todolist.repository.ToDoRepository;
import com.thainguyen.todolist.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class SearchController {

    private final ToDoService toDoService;
    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

    public SearchController(ToDoService toDoRepository) {
        this.toDoService = toDoRepository;
    }

    private int longestCommonSubSequence(String wordA, String wordB) {
        char[] lettersOfA = wordA.toCharArray();
        char[] lettersOfB = wordB.toCharArray();
        int[][] dp = new int[wordA.length() + 1][wordB.length() + 1];
        for (int ia = 1; ia <= wordA.length(); ia++) {
            for (int ib = 1; ib <= wordB.length(); ib++) {
                if (lettersOfA[ia - 1] == lettersOfB[ib - 1]) {
                    dp[ia][ib] = dp[ia - 1][ib - 1] + 1;
                } else {
                    dp[ia][ib] = Math.max(dp[ia][ib - 1], dp[ia - 1][ib]);
                }
            }
        }
        return dp[wordA.length()][wordB.length()];
    }

    private int longestCommonSubSequence2(String wordA, String wordB) {
        char[] lettersOfA = wordA.toCharArray();
        char[] lettersOfB = wordB.toCharArray();
        int[][] dp = new int[wordA.length() + 1][wordB.length() + 1];
        for (int ia = 1; ia <= wordA.length(); ia++) {
            for (int ib = 1; ib <= wordB.length(); ib++) {
                if (lettersOfA[ia - 1] == lettersOfB[ib - 1]) {
                    dp[ia][ib] = dp[ia - 1][ib - 1] + 1;
                } else {
                    dp[ia][ib] = 0;
                }
            }
        }
        return dp[wordA.length()][wordB.length()];
    }

    @GetMapping("/search")
    public String search(@RequestParam("sequence") String input, Model model) {
        logger.info("Search input : {}", input);
        List<ToDo> toDoList = toDoService.findAllToDoList();
        String guessName = null;
        Long guessId = null;
        int mostMatch = 0;

        for (ToDo t : toDoList) {
            int requiredMinLength = (t.getName().length() * 60) / 100;

            // doan dung
            if (input.toLowerCase().equals(t.getName().toLowerCase())) {
                guessName = null;
                guessId = null;
                model.addAttribute("result", t);
                return "search-result";
            } else {
                int numberOfLetterMatching = longestCommonSubSequence(input.toLowerCase(), t.getName().toLowerCase());
                if (numberOfLetterMatching > mostMatch && numberOfLetterMatching >= requiredMinLength) {
                    mostMatch = numberOfLetterMatching;
                    guessName = t.getName();
                    guessId = t.getId();
                }
            }
        }

        model.addAttribute("guessName", guessName);
        model.addAttribute("guessId", guessId);
        return "search-result";
    }

    @GetMapping("/search/{id}")
    public String searchById(@PathVariable("id") Long toDoId, Model model) {
        Optional<ToDo> toDoOptional = toDoService.findToDoById(toDoId);
        model.addAttribute("result", toDoOptional.get());
        return "search-result";
    }
}
