package com.hutech.todolist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hutech.todolist.model.Work;

@Service
public class WorkService {

    private final List<Work> works = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Work> getAllWorks() {
        return new ArrayList<>(works);
    }

    public List<Work> getActiveWorks() {
        return works.stream()
                .filter(work -> !work.isComplete())
                .collect(Collectors.toList());
    }

    public List<Work> getCompletedWorks() {
        return works.stream()
                .filter(Work::isComplete)
                .collect(Collectors.toList());
    }

    public void addWork(String name) {
        works.add(new Work(idCounter.getAndIncrement(), name, false));
    }

    public void toggleComplete(Long id) {
        getWorkById(id).ifPresent(work -> work.setComplete(!work.isComplete()));
    }

    public void deleteWork(Long id) {
        works.removeIf(work -> work.getId().equals(id));
    }

    public void clearCompleted() {
        works.removeIf(Work::isComplete);
    }

    public Optional<Work> getWorkById(Long id) {
        return works.stream().filter(w -> w.getId().equals(id)).findFirst();
    }
}
