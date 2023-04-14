package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Смирнов И.И.", "умный", 1,
                LocalDateTime.of(2021, Month.APRIL, 14, 10, 10), 0));
        save(new Candidate(1, "Иванов С.Е.", "красивый", 2,
                LocalDateTime.of(2022, Month.AUGUST, 14, 10, 5), 0));
        save(new Candidate(1, "Кузнецов О.А.", "общительный", 3,
                LocalDateTime.of(2023, Month.SEPTEMBER, 14, 6, 25), 0));
        save(new Candidate(1, "Попов А.П.", "активный", 1,
                LocalDateTime.of(2024, Month.JANUARY, 14, 14, 35), 0));
        save(new Candidate(1, "Соколов П.С.", "креативный", 3,
                LocalDateTime.of(2025, Month.OCTOBER, 14, 10, 20), 0));
        save(new Candidate(1, "Лебедев В.В.", "замечательный", 1,
                LocalDateTime.of(2026, Month.JULY, 14, 12, 10), 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.incrementAndGet());
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
                        candidate.getDescription(), candidate.getCityId(),
                        candidate.getCreationDate(), candidate.getFileId())) != null;
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
