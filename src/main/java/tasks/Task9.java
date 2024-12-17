package tasks;

import common.Person;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // private long count; используется только в одном методе, который ещё и публичный и присваивает в count 0

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) { // улучшили читаемость
      return Collections.emptyList();
    }
    return persons.stream() // чуть улучшили читаемость и не изменяем исходный persons благодаря skip
            .skip(1)
            .map(Person::firstName)
            .filter(Objects::nonNull) // избавились от null значений
            .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // короче и проще, нужные действия произведены в getNames
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    String fullName = ""; // переименовали для читаемости

    // Написал корректную лямбда-функцию, чтобы не повторять код
    BiFunction<String, String, String> concatIfNotNull = (String name, String value) -> {
      String strToReturn = name;
      if (value != null) {
        strToReturn += name.isEmpty() ? value : " " + value;
      }
      return strToReturn;
    };

    fullName = concatIfNotNull.apply(fullName, person.secondName());
    fullName = concatIfNotNull.apply(fullName, person.firstName());
    fullName = concatIfNotNull.apply(fullName, person.middleName());

    return fullName;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Переименовал map для читаемости и использовал putIfAbsent вместо проверки отсутствия ключа
    Map<Integer, String> personNames = new HashMap<>();
    for (Person person : persons) {
      personNames.putIfAbsent(person.id(), convertPersonToString(person));
    }
    return personNames;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Переписали логику, асимптотика - линия
    Set<Person> samePersons = new HashSet<>(persons1);
    samePersons.retainAll(new HashSet<>(persons2));
    return !samePersons.isEmpty();
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // логику можно перенести сразу в return и использовать метод count вместо переменной и её инкремента
    return numbers.filter(number -> number % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // Вероятно, суть в том, что диапазон чисел не очень большой и хэш-функция будет класть числа в контейнеры, индексы
  // которых будут равны самим числам, а equals итерируется в порядке контейнеров
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
