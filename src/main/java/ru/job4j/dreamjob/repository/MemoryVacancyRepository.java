package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Работа 24/7, без выходных",
                LocalDateTime.of(2021, Month.FEBRUARY, 14, 15, 30)));
        save(new Vacancy(1, "Junior Java Developer", "Можно смотреть в окно до двух раз день",
                LocalDateTime.of(2022, Month.APRIL, 24, 14, 25)));
        save(new Vacancy(2, "Junior+ Java Developer", "Один отпускной день в году",
                LocalDateTime.of(2023, Month.JUNE, 9, 16, 13)));
        save(new Vacancy(3, "Middle Java Developer", "Отпускаем поспать дома",
                LocalDateTime.of(2024, Month.SEPTEMBER, 8, 17, 24)));
        save(new Vacancy(4, "Middle+ Java Developer", "Даем перерывы на еду",
                LocalDateTime.of(2025, Month.AUGUST, 12, 18, 15)));
        save(new Vacancy(5, "Senior Java Developer", "Можете иногда спать",
                LocalDateTime.of(2026, Month.APRIL, 1, 14, 6)));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.incrementAndGet());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) ->
                new Vacancy(oldVacancy.getId(), vacancy.getTitle(),
                        vacancy.getDescription(), vacancy.getCreationDate()))
                != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
