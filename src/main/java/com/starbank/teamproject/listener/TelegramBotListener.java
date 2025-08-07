package com.starbank.teamproject.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.starbank.teamproject.model.RecommendationDTO;
import com.starbank.teamproject.model.User;
import com.starbank.teamproject.repository.TelegramBotRepository;
import com.starbank.teamproject.service.RecommendationsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TelegramBotListener implements UpdatesListener {

    @Autowired
    private TelegramBot bot;

    @Autowired
    private TelegramBotRepository telegramBotRepository;

    @Autowired
    private RecommendationsService recommendationsService;

    @PostConstruct
    public void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                String text = update.message().text();
                Long chatId = update.message().chat().id();
                if ("/start".equals(update.message().text())) {
                    bot.execute(new SendMessage(chatId, """
                            Добрый день.
                            Я бот для выдачи рекоммендаций.
                            Напиши мне "/recommend " и свой username.\
                             Я подберу тебе рекомендацию."""));
                }

                if (update.message().text().startsWith("/recommend")) {
                    String username = update.message().text().substring(11);
                    try {
                        User user = telegramBotRepository.getUser(username);
                        bot.execute(new SendMessage(chatId, "Здравствуйте, " + user.getFirstName() + " " + user.getLastName() + "!\n" +
                                "Новые продукты для вас:\n" + getTextRecommendation(recommendationsService.getClientRecommendations(user.getId()))));
                    } catch (NoSuchElementException | IllegalStateException | SQLException e) {
                        bot.execute(new SendMessage(chatId, e.getMessage()));
                    }


                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private String getTextRecommendation(List<RecommendationDTO> recommendations) {
        StringBuilder text = new StringBuilder();
        for (RecommendationDTO recommendation : recommendations) {
            text.append(recommendation.getText()).append("\n");
        }
        return text.toString();
    }
}
