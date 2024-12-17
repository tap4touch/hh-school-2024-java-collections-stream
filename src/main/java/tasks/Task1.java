package tasks;

import common.Person;
import common.PersonService;

import java.util.*;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds); // Пусть размер составляет n
    List<Person> orderedPersons = new ArrayList<>(personIds.size()); // Пусть размер составляет m
    Map<Integer, Person> personsMap = new HashMap<>();

    // O(n)
    for (Person person: persons) {
      personsMap.put(person.id(), person);
    }

    // O(m)
    for (int i = 0; i < personIds.size(); i++) {
      orderedPersons.add(i, personsMap.get(personIds.get(i)));
    }

    // Итого асимптотика O(n + m), т.е. линия
    return orderedPersons;
  }
}
