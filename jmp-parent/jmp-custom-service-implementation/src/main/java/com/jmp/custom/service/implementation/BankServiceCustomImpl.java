package com.jmp.custom.service.implementation;

import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import com.jmp.dto.User;
import com.jmp.service.api.BankService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BankServiceCustomImpl implements BankService {

    @Override
    public void subscribe(BankCard bankCard) {

    }

    @Override
    public Subscription getSubscriptionByBankCardNumber(String number) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of(new User("name1", "surname1", LocalDate.now()), new User("name2", "surname2", LocalDate.now()));
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return null;
    }
}
