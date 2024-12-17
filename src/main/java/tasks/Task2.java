package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    List<Person> persons = new ArrayList<>(persons1.size() + persons2.size());
    persons.addAll(persons1);
    persons.addAll(persons2);
    persons.sort(Comparator.comparing(Person::createdAt));

    return persons.subList(0, Math.min(limit, persons.size()));
  }
}
