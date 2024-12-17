package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Map<Integer, Person> personWithId = new HashMap<>();
    Map<Person, Set<Resume>> personWithResumesMap = new HashMap<>();
    Set<Integer> personIds = new HashSet<>();
    for (Person person : persons) {
      personWithId.put(person.id(), person);
      personIds.add(person.id());
      personWithResumesMap.putIfAbsent(person, new HashSet<>());
    }

    Set<Resume> resumes = personService.findResumes(personIds);

    for (Resume resume : resumes) {
      Integer personId = resume.personId();
      personWithResumesMap.computeIfAbsent(personWithId.get(personId), k -> new HashSet<>())
              .add(resume);
    }

    Set<PersonWithResumes> personWithResumes = new HashSet<>();
    personWithResumesMap.forEach((person, resumeSet) -> {
      PersonWithResumes personWithResume = new PersonWithResumes(person, resumeSet);
      personWithResumes.add(personWithResume);
    });

    return personWithResumes;
  }
}
