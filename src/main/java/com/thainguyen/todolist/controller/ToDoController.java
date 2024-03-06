package com.thainguyen.todolist.controller;

import com.thainguyen.todolist.model.ToDo;
import com.thainguyen.todolist.service.ToDoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class ToDoController {

    private final ToDoService toDoService;
    private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @ModelAttribute("todo")
    public ToDo toDo() {
        return new ToDo();
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String home(Model model) {
        List<ToDo> toDoList = toDoService.findAllToDoList();
        logger.info("toDoList: " + toDoList);
        model.addAttribute("todos", toDoList.isEmpty() ? Collections.EMPTY_LIST : toDoList);
        return "index";
    }

    @GetMapping("/addtodo")
    public String showAddToDoPage() {
        return "add-todo";
    }

    @PostMapping("/addtodo")
    public String addToDo(@Valid ToDo toDo, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "add-todo";
        }
        toDoService.createToDo(toDo);
        model.addAttribute("todos", toDoService.findAllToDoList());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteToDoById(@PathVariable("id") Long id, Model model) {
        toDoService.deleteToDo(id);
        model.addAttribute("todos", toDoService.findAllToDoList());
        return "redirect:/index";
    }

    @GetMapping("/marked/{id}")
    public String markedDone(@PathVariable("id") Long id, Model model) {
        toDoService.markedDone(id);
        model.addAttribute("todos", toDoService.findAllToDoList());
        return "redirect:/index";
    }

    @GetMapping("/markednotdone/{id}")
    public String markedNotDone(@PathVariable("id") Long id, Model model) {
        toDoService.markedNotDone(id);
        model.addAttribute("todos", toDoService.findAllToDoList());
        return "redirect:/index";
    }

    private static int[][] knapsackMaxValue(int[] times, int[] scores, int time_available, int start, int step) {
        int item_len = times.length;
        int[][] dp = new int[item_len + 1][time_available + 1];
        for (int i = 1; i <= item_len; i++) {
            for (int w = start; w <= time_available; w += step) {
                if (times[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], scores[i - 1] + dp[i - 1][w - times[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        return dp;
    }

    public static int[] item_include(int[][] dp, int[] times, int w) {
        int i = times.length;
        int[] include_todos = new int[times.length];
        int cnt = 0;
        while(i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                include_todos[cnt++] = i - 1;
                w -= times[i - 1];
            }
            i -= 1;
        }
        return Arrays.copyOf(include_todos, cnt);
    }

    // url have parameter
    @GetMapping("/optimization")
    public String optimization(@RequestParam(name = "time", required = false) Integer time, Model model) {
        List<ToDo> toDoList = toDoService.findAllByDone(false);
        if (!toDoList.isEmpty()) {
            int[] scores = toDoList.stream().mapToInt(ToDo::getScore).toArray();
            int[] times = toDoList.stream().mapToInt(ToDo::getEstimate_time).toArray();
            long[] ids = toDoList.stream().mapToLong(ToDo::getId).toArray();

            int[][] dp = knapsackMaxValue(times, scores, time, 1, 1);
            model.addAttribute("opt_value", dp[scores.length][time]);
            int[] result = item_include(dp, times, time);
            toDoList = toDoList.stream().filter(toDo -> {
                for (int i : result) {
                    if (toDo.getId().equals(ids[i])) {
                        return true;
                    }
                }
                return false;
            }).toList();
        }
        model.addAttribute("todos", toDoList.isEmpty() ? Collections.EMPTY_LIST : toDoList);
        return "optimizationpage";
    }


}
