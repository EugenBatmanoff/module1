package com.jmp.cloud.service.impl;


import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;
import com.jmp.service.api.BankService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BankServiceImpl implements BankService {
    private List<Subscription> subscriptions = new ArrayList<Subscription>();
    private List<User> users = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        subscriptions.add(new Subscription(bankCard.getNumber()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String number) {
        return Optional.ofNullable(subscriptions.stream()
                .filter(subscription -> subscription.getBankCard().equals(number))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found for bank card: " + number)));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptions.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }


public List<User> generateUsers(int n) {
    users = IntStream.range(0, n)
            .mapToObj(i -> {
                User user = new User();
                user.setName("User" + i);
                user.setSurname("Surname" + i);
                user.setBirthday(LocalDate.now().minusYears(17 + i));
                return user;
            })
            .collect(Collectors.toUnmodifiableList());
    return  users;
    }

}
