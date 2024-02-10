package com.jmp.application;

import com.jmp.bank.api.Bank;
import com.jmp.cloud.bank.impl.BankImpl;
import com.jmp.cloud.service.impl.BankServiceImpl;
import com.jmp.dto.*;
import com.jmp.service.api.BankService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {
        var bank = new BankImpl();
        var service = new BankServiceImpl();

        var users = service.generateUsers(10);
        printUserList(users);

        var bankCards = generateBankCards(users, bank);

        subscribeBankCards(bankCards, service);

        printSubscriptions(service);

        returnSubscriptionByCardNumber(bankCards, service);

        //testing the SubscriptionNotFoundException:
        //service.getSubscriptionByBankCardNumber("dfkjdflkj");


        System.out.println(service.getAverageUsersAge());

        System.out.println("User's birthday: " + users.get(2).getBirthday() +
                ". He is Payable: " + BankService.isPayableUser(users.get(2)));

        getSubscriptionsByCondition(service);


    }

    private static void getSubscriptionsByCondition(BankServiceImpl service) {
        Predicate<Subscription> condition =
                subscription -> subscription.getStartDate().isAfter(LocalDate.now().minusDays(30));

        var filteredSubscriptions = service.getAllSubscriptionsByCondition(condition);
        filteredSubscriptions.stream()
                .map(filteredSubscription -> "filtered Subscription" + filteredSubscription).forEach(System.out::println);
    }

    private static void returnSubscriptionByCardNumber(List<BankCard> bankCards, BankServiceImpl service) {
        System.out.println(System.lineSeparator() + "return subscriptions by the card number: ");
        bankCards.stream().map(bankCard -> service.getSubscriptionByBankCardNumber(bankCard.getNumber())).forEach(System.out::println);
    }

    private static void printSubscriptions(BankServiceImpl service) {
        var list = service.getSubscriptions();
        System.out.println(System.lineSeparator() + "Printing all subscriptions: ");
        list.forEach(System.out::println);
    }

    private static void subscribeBankCards(List<BankCard> bankCards, BankServiceImpl service) {
        bankCards.forEach(service::subscribe);
    }

    private static List<BankCard> generateBankCards(List<User> users, Bank bank) {
        var bankCards = new ArrayList<BankCard>();
        var random = new Random();
        System.out.println(System.lineSeparator() + "generated bank cards: ");
        IntStream.range(0, 15).mapToObj(i -> random.nextBoolean() ? BankCardType.CREDIT : BankCardType.DEBIT)
                .map(type -> bank.createBankCard(users.get(random.nextInt(users.size())), type)).forEach(bankCard -> {
                    System.out.println(bankCard);
                    bankCards.add(bankCard);
                });
        return bankCards;
    }

    private static void printUserList(List<User> users) {
        System.out.println("generated users: ");
        users.stream().map(User::toString).forEach(System.out::println);
    }


}