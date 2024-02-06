package com.jmp.cloud.bank.impl;

import com.jmp.bank.api.Bank;
import com.jmp.dto.*;

import java.util.Random;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    /*   @Override
      public BankCard createBankCard(User user, BankCardType type) {
           var randomInt = new Random().nextInt(1000) + 1;
           switch (type) {
               case DEBIT -> {
                   return new DebitBankCard("DEBIT" + randomInt, user);
               }
               case CREDIT -> {
                   return new CreditBankCard("CREDIT" + randomInt, user);
               }
               default -> throw new IllegalArgumentException("Invalid bank card type");
           }
       }*/
    @Override
    public BankCard createBankCard(User user, BankCardType type) {
        var randomInt = new Random().nextInt(1000) + 1;
        BiFunction<String, User, BankCard> cardConstructor;
        switch (type) {
            case DEBIT:
                cardConstructor = DebitBankCard::new;
                break;
            case CREDIT:
                cardConstructor = CreditBankCard::new;
                break;
            default:
                throw new IllegalArgumentException("Invalid bank card type");
        }
        return cardConstructor.apply(type + String.valueOf(randomInt), user);
    }
}