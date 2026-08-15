package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());

        //new Проверить что занятий в Понедельник менее 24 в день(максимально возможное)
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size() < 24);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия:
        // Проверяем что 2 занятия
        List<TrainingSession> thursdayTraining = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdayTraining.size());
        // Проверяем правильный порядок: сначала в 13:00, потом в 20:00
        int i = 0;
        for (TrainingSession session : thursdayTraining) {
            if (i == 0) {
                Assertions.assertEquals(13, session.getTimeOfDay().getHours());
                Assertions.assertEquals(0, session.getTimeOfDay().getMinutes());
                i++;
            } else if (i == 1) {
                Assertions.assertEquals(20, session.getTimeOfDay().getHours());
                Assertions.assertEquals(0, session.getTimeOfDay().getMinutes());
                break;
            }
        }

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Integer oneTraining = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size();
        Assertions.assertEquals(1, oneTraining);
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Integer zeroTraining = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size();
        Assertions.assertEquals(0, zeroTraining);

        List<TrainingSession> trainings = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));
        for (TrainingSession train : trainings) {
            //new Проверить, что в Понедельник указан Тренер
            Assertions.assertNotNull(train.getCoach());
            //new Проверить, что в Понедельник указана Группа
            Assertions.assertNotNull(train.getGroup());
            //new Проверить, что в Понедельник указано Время
            Assertions.assertNotNull(train.getTimeOfDay());
        }

    }

    @Test
    void testGetCountByCoachesOrder() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach3 = new Coach("Петров", "Петр", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);


        TrainingSession fridayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));
        TrainingSession fridayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));
        TrainingSession fridayChildTrainingSession2 = new TrainingSession(groupChild, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(fridayAdultTrainingSession);
        timetable.addNewTrainingSession(fridayChildTrainingSession);
        timetable.addNewTrainingSession(fridayChildTrainingSession2);

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<Map.Entry<Coach, Integer>> coach = timetable.getCountByCoaches();
        //Проверяем порядок отсортированный список
        Assertions.assertEquals(3, coach.get(0).getValue());
        Assertions.assertEquals(2, coach.get(1).getValue());
        Assertions.assertEquals(1, coach.get(2).getValue());

    }
}
