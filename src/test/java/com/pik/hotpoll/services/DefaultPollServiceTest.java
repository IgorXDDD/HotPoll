package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.interfaces.PollService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class DefaultPollServiceTest {

    @Autowired
    private PollService pollService;
    @Test
    public void pagingTest() throws ConstraintsViolationException {
        User user = User.builder().nickname("igor").id("igor").build();
        Random random = new Random();
        for(int i = 0 ; i < 100; ++i) {
            List<String> tags = new ArrayList<>();
            List<Question> questions = new ArrayList<>();
            List<Answer> answers = new ArrayList<>();

            tags.add("tag" + random.nextInt(100));
            tags.add("tag" + random.nextInt(100));
            tags.add("tag" + random.nextInt(100));
            answers.add(Answer.builder().id("1").text("tak").votes(2).build());
            answers.add(Answer.builder().id("2").text("nie").votes(2).build());
            questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
            questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
            Poll poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).timesFilled(random.nextInt(100000)).build();
            pollService.create(poll);
        }
        Iterable<Poll> polls = pollService.findAll(0, 10, false);
        int prev = Integer.MAX_VALUE;
        for (Poll p : polls){

            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            assertTrue(prev >= p.getTimesFilled());
            prev = p.getTimesFilled();
        }
        polls = pollService.findAll(2, 20, false);
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            assertTrue(prev >= p.getTimesFilled());
        }
        polls = pollService.findAll(0, 10, true);
        LocalDateTime prevDate = LocalDateTime.now();
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());

            assertFalse(prevDate.isBefore(p.getDate()));
            prevDate = p.getDate();
        }
        polls = pollService.findAll(2, 20, true);
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            assertFalse(prevDate.isBefore(p.getDate()));
            prevDate = p.getDate();
        }
    }

    @Test
    public void pagingTagsTest() throws ConstraintsViolationException {
        User user = User.builder().nickname("igor").id("igor").build();
        Random random = new Random();
        for(int i = 0 ; i < 100; ++i) {
            List<String> tags = new ArrayList<>();
            List<Question> questions = new ArrayList<>();
            List<Answer> answers = new ArrayList<>();

            tags.add("tag" + random.nextInt(100));
            tags.add("tag" + random.nextInt(100));
            tags.add("tag" + random.nextInt(100));
            answers.add(Answer.builder().id("1").text("tak").votes(2).build());
            answers.add(Answer.builder().id("2").text("nie").votes(2).build());
            questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
            questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
            Poll poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).timesFilled(random.nextInt(100000)).build();
            pollService.create(poll);
        }
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag45");
        tags.add("tag34");
        Iterable<Poll> polls = pollService.findByTags(tags,0, 10, false);
        int prev = Integer.MAX_VALUE;
        for (Poll p : polls){

            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            System.out.println(p.getTags());
            assertTrue(prev >= p.getTimesFilled());
            assertFalse(Collections.disjoint(tags, p.getTags()));
            prev = p.getTimesFilled();
        }
        polls = pollService.findByTags(tags,2, 20, false);
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getTags());
            System.out.println(p.getDate());
            assertFalse(Collections.disjoint(tags, p.getTags()));
            assertTrue(prev >= p.getTimesFilled());
        }
        polls = pollService.findByTags(tags,0, 10, true);
        LocalDateTime prevDate = LocalDateTime.now();
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getTags());
            System.out.println(p.getDate());
            assertFalse(Collections.disjoint(tags, p.getTags()));
            assertFalse(prevDate.isBefore(p.getDate()));
            prevDate = p.getDate();
        }
        polls = pollService.findByTags(tags,2, 20, true);
        for (Poll p : polls){
            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            System.out.println(p.getTags());
            assertFalse(prevDate.isBefore(p.getDate()));
            assertFalse(Collections.disjoint(tags, p.getTags()));
            prevDate = p.getDate();
        }
    }
}
