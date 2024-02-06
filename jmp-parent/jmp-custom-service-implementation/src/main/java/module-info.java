module jmp.custom.service.implementation {
    requires jmp.service.api;
    requires jmp.dto;
    uses com.jmp.service.api.BankService;
    provides com.jmp.service.api.BankService with com.jmp.custom.service.implementation.BankServiceCustomImpl;

}