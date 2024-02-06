package com.jmp.service.api;

import com.jmp.dto.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface BankService {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String number);
    List<User> getAllUsers();
    default double getAverageUsersAge() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {return 0.0;}
        LocalDate currentDate = LocalDate.now();
        long totalAge = users.stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), currentDate))
                .sum();
        return (double) totalAge / users.size();
    }
    static boolean isPayableUser(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate eighteenYearsAgo = currentDate.minus(18, ChronoUnit.YEARS);
        return user.getBirthday().isBefore(eighteenYearsAgo);
    }
    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

}