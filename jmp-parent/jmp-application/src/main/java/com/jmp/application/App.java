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

public class App {

    public static void main(String[] args) {
        Bank bank = new BankImpl();
        BankServiceImpl service = new BankServiceImpl();

        List<User> users = service.generateUsers(10);
        printUserList(users);

        List <BankCard> bankCards = generateBankCards(users, bank);

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

        List<Subscription> filteredSubscriptions = service.getAllSubscriptionsByCondition(condition);
        for (Subscription filteredSubscription : filteredSubscriptions) {
            System.out.println("filtered Subscription" + filteredSubscription);
        }
    }

    private static void returnSubscriptionByCardNumber(List<BankCard> bankCards, BankServiceImpl service) {
        System.out.println(System.lineSeparator() + "return subscriptions by the card number: ");
        for (var bankCard : bankCards) {
            System.out.println(service.getSubscriptionByBankCardNumber(bankCard.getNumber()));
        }
    }

    private static void printSubscriptions(BankServiceImpl service) {
        List<Subscription> list = service.getSubscriptions();
        System.out.println(System.lineSeparator() + "Printing all subscriptions: ");
        for (Subscription subscription : list) {
            System.out.println(subscription);
        }
    }

    private static void subscribeBankCards(List<BankCard> bankCards, BankServiceImpl service) {
        for (var bankCard : bankCards) {
            service.subscribe(bankCard);
        }
    }

    private static List<BankCard> generateBankCards(List<User> users, Bank bank) {
        List<BankCard> bankCards = new ArrayList<>();
        Random random = new Random();
        System.out.println(System.lineSeparator() + "generated bank cards: ");
        for (var i = 0; i < 15; i++) {
            User user = users.get(random.nextInt(users.size()));
            var type = random.nextBoolean() ? BankCardType.CREDIT : BankCardType.DEBIT;
            var bankCard = bank.createBankCard(user, type);
            System.out.println(bankCard);
            bankCards.add(bankCard);
        }
        return bankCards;
    }
    private static void printUserList(List<User> users) {
        System.out.println("generated users: ");
        for (var user : users) {
            System.out.println(user.toString());
        }
    }


}