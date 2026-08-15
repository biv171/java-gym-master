package ru.yandex.practicum.gym;

import javax.swing.*;
import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> dayTraining = timetable.get(dayOfWeek);
        TreeMap<TimeOfDay, List<TrainingSession>> newDayOfweek = new TreeMap<>();

        if (timetable.containsKey(dayOfWeek)) {
            if (dayTraining.containsKey(timeOfDay)) {
                dayTraining.get(timeOfDay).add(trainingSession);
            } else {
                dayTraining.put(timeOfDay, List.of(trainingSession));
            }
        } else {
            newDayOfweek.put(timeOfDay, new ArrayList<>(List.of(trainingSession)));
            timetable.put(dayOfWeek, newDayOfweek);
        }

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayShedule = timetable.get(dayOfWeek);
        List<TrainingSession> resultSheduleList = new ArrayList<>();
        //Если дня нет возврат пустого списка
        if (dayShedule == null) {
            return new ArrayList<>();
        }
        //формируем список тренировок
        for (List<TrainingSession> sessions : dayShedule.values()) {
            resultSheduleList.addAll(sessions);
        }

        return resultSheduleList;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay,List<TrainingSession>> dayShedule = timetable.get(dayOfWeek);

        //Если дня нет возврат пустого списка
        if (dayShedule == null || dayShedule.get(timeOfDay) == null) {
            return new ArrayList<>();
        }

        return dayShedule.get(timeOfDay);
    }

    //Метод считает занятий в неделю ведёт каждый из тренеров
    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
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

        List<Map.Entry<Coach, Integer>> entryList = new ArrayList<>(coachTrainings.entrySet());
        entryList.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());

        return entryList;
    }

}
