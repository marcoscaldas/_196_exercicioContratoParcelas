package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
		//    atributo               // argumento	
	}	
	public void processContract(Contract contract, int months) {
	double basicQuota = contract.getTotalValue() / months;
	for (int i=1; i <= months; i++) {
		double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
		double fullQuota = updatedQuota + onlinePaymentService.paymentfee(updatedQuota);
		Date dueDate = addMonths(contract.getDate(), i);
		contract.getInstallments().add(new Installment(dueDate, fullQuota));
		
	}
	}
	
	//funcao auxiliar para acrescentar meses a uma data
	private Date addMonths(Date date, int N) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, N);
		return calendar.getTime();
		
	}
}
