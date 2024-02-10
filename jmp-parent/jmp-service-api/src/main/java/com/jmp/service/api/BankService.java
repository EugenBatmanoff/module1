package com.jmp.service.api;

import com.jmp.dto.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public interface BankService {
    static boolean isPayableUser(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate eighteenYearsAgo = currentDate.minus(18, ChronoUnit.YEARS);
        return user.getBirthday().isBefore(eighteenYearsAgo);
    }

    void subscribe(BankCard bankCard);

    Subscription getSubscriptionByBankCardNumber(String number);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        List<User> users = getAllUsers();
        if (users.isEmpty()) {
            return 0.0;
        }
        LocalDate currentDate = LocalDate.now();
        return users.stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), currentDate))
                .average().orElse(0.0);
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

}