package com.jmp.custom.service.implementation;

import com.jmp.service.api.BankService;


import java.util.ServiceLoader;
public class ServiceLoaderDemonstration {
    public static void main(String[] args) {
        ServiceLoader<BankService> serviceLoader = ServiceLoader.load(BankService.class);
            for (BankService service : serviceLoader) {
            System.out.println(service.getAllUsers());
        }
    }
}
