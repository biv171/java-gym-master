package ru.yandex.practicum.gym;

import javax.swing.*;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (timetable.containsKey(dayOfWeek)) {
            timetable.get(dayOfWeek).put(timeOfDay, List.of(trainingSession));
        } else
            timetable.put(dayOfWeek, new TreeMap<>() {
                {
                    put(timeOfDay, List.of(trainingSession));
                }
            }
            );
    }

    public TreeMap<TimeOfDay,List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek);
        } else {
            return new TreeMap<>();
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay,List<TrainingSession>> trainingSession = timetable.get(dayOfWeek);
        //Проверка если есть значение
        if (trainingSession.containsKey(timeOfDay)) {
            return trainingSession.get(timeOfDay);
        } else {
            System.out.println("Тренировок на это время нет!");
            return new ArrayList<>();
        }
    }

    //Метод считает занятий в неделю ведёт каждый из тренеров
    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> coachTrainings = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> entryValues : timetable.values()) {
            for (List<TrainingSession> session : entryValues.values()) {
                for (TrainingSession training : session) {
                    Coach coach = training.getCoach();
                    //Если есть тренер то добавляем занятие
                    if (coachTrainings.containsKey(coach)) {
                        coachTrainings.computeIfPresent(coach, (k, v) -> v + 1);
                    } else {
                        coachTrainings.put(coach, 1);
                    }
                }
            }
        }
        return coachTrainings;
    }

}
