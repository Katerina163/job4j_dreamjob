package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryCandidateRepository implements CandidateRepository{
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Смирнов И.И.", "умный",
                LocalDate.of(2021, Month.APRIL, 14)));
        save(new Candidate(1, "Иванов С.Е.", "красивый",
                LocalDate.of(2022, Month.AUGUST, 14)));
        save(new Candidate(1, "Кузнецов О.А.", "общительный",
                LocalDate.of(2023, Month.SEPTEMBER, 14)));
        save(new Candidate(1, "Попов А.П.", "активный",
                LocalDate.of(2024, Month.JANUARY, 14)));
        save(new Candidate(1, "Соколов П.С.", "креативный",
                LocalDate.of(2025, Month.OCTOBER, 14)));
        save(new Candidate(1, "Лебедев В.В.", "замечательный",
                LocalDate.of(2026, Month.JULY, 14)));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return  candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) ->
                new Candidate(candidate.getId(), candidate.getName(),
                        candidate.getDescription(), candidate.getCreationDate()))
                != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
