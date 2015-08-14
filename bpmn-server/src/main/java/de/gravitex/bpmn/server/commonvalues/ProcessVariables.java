package de.gravitex.bpmn.server.commonvalues;

public class ProcessVariables
{
	public class RefNoCollabVars
	{
		public static final String VAR_PROVIDED_PIN = "providedPin";
		
		public static final String VAR_PIN_VALID = "pinValid";
		
		public static final String VAR_PIN_ATTEMPTS = "pinAttempts";

		public static final String VAR_ERROR_CODE = "errorCode";
		
		public static final String VAR_CHOSEN_PUMP_NO = "chosenPumpNo";
		
		public static final String VAR_AMOUNT_TO_DEBIT = "amountToDebit";
		
		public static final String VAR_REFUELING_PROCESS_ID = "refuelingProcessId";
		
		public static final int ERROR_CODE_EXC_ATT = 111;
		
		public static final int ERROR_CODE_EXC_TIME = 222;

		public static final int ERROR_CODE_NONE = 333;
	}
	
	public class ExportRequestVars
	{
		public static final String VAR_REQ_APPROVED = "requestApproved";
	}

	public class CompensationTestVars
	{
		public static final String VAR_DO_JOURNEY = "doJourney";
	}
	
	public class SignalTestVars
	{
		public static final String VAR_FAULTS_EXCEEDED = "faultsExceeded";
	}
	
	public class MasterQuestion
	{

		public static final String COMPUTED_RESULT = "computedResult";
		
	}

	public class SendInvoiceImproved
	{
		public static final String SEND_ERROR_COUNTER = "sendErrorCounter";
	}
}
