package tasks;

import common.Area;
import common.Person;

import java.util.*;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, String> personNames = new HashMap<>();
    for (Person person : persons) {
      personNames.put(person.id(), person.firstName());
    }

    Map<Integer, String> areaNames = new HashMap<>();
    for (Area area : areas) {
      areaNames.put(area.getId(), area.getName());
    }

    Set<String> personDescriptions = new HashSet<>();
    for (Integer name : personAreaIds.keySet()) {
      for (Integer region : personAreaIds.get(name)) {
        String nameAndRegion = personNames.get(name) + " - " + areaNames.get(region);
        personDescriptions.add(nameAndRegion);
      }
    }

    return personDescriptions;
  }
}
