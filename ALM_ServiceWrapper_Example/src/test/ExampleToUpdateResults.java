package test;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.IDefect;
import atu.alm.wrapper.ITestCase;
import atu.alm.wrapper.ITestCaseRun;
import atu.alm.wrapper.ITestSet;
import atu.alm.wrapper.enums.DefectPriority;
import atu.alm.wrapper.enums.DefectSeverity;
import atu.alm.wrapper.enums.DefectStatus;
import atu.alm.wrapper.enums.StatusAs;
import atu.alm.wrapper.exceptions.ALMServiceException;

/**
 * 
 * @author Automation Tester http://automationtestingutilities.blogspot.in/
 */
public class ExampleToUpdateResults {

	/**
	 * @param args
	 * @throws ALMServiceException
	 */
	public static void main(String[] args) throws ALMServiceException {
		createTestCaseRunAndTestCaseExecutionSteps();
		createDefect();
		createAttachmentForTestSet();
		System.out.println("Done!!");
	}

	/*
	 * Example For Creating an Attachment for a TestSet
	 */
	public static void createAttachmentForTestSet() throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper(
				"http://localhost:8081/qcbin");
		wrapper.connect("admin", "admin", "DEFAULT", "SampleProject");
		ITestSet testSet = wrapper.getTestSet(
				"SampleTestSetFolder\\SubTestSetFolder1", "TestSet1", 2);
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				testSet);
		wrapper.close();
	}

	/*
	 * Example For Creating a new Defect and Attaching a File
	 */
	public static void createDefect() throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper(
				"http://localhost:8081/qcbin");
		wrapper.connect("admin", "admin", "DEFAULT", "SampleProject");
		IDefect defect = wrapper.newDefect();
		defect.isReproducible(true);
		defect.setAssignedTo("admin");
		defect.setDetectedBy("admin");
		defect.setDescription("My Defect Description");
		defect.setDetectionDate("12/1/2013");
		defect.setPriority(DefectPriority.HIGH);
		defect.setSeverity(DefectSeverity.MEDIUM);
		defect.setStatus(DefectStatus.OPEN);
		defect.setSummary("My Defect/Bug Summary");
		System.out.println(defect.getDefectID());
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				defect);
		defect.save();
		wrapper.close();
	}

	public static void createTestCaseRunAndTestCaseExecutionSteps()
			throws ALMServiceException {
		ALMServiceWrapper wrapper = new ALMServiceWrapper(
				"http://localhost:8081/qcbin");
		wrapper.connect("admin", "admin", "DEFAULT", "SampleProject");

		// Update Test Case Result and Attach a File
		ITestCase loginTest = wrapper.updateResult(
				"SampleTestSetFolder\\SubTestSetFolder1", "TestSet1", 2,
				"Login", StatusAs.PASSED);
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				loginTest);

		// Update Test Case Result and Attach a File
		ITestCase logoutTest = wrapper.updateResult(
				"SampleTestSetFolder\\SubTestSetFolder1", "TestSet1", 2,
				"Logout", StatusAs.NOT_COMPLETED);
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				logoutTest);

		// Create a new Run, Add the Steps for this Run and Attach a File
		ITestCaseRun loginRun = wrapper.createNewRun(loginTest, "Run 1",
				StatusAs.PASSED);
		wrapper.addStep(loginRun, "Enter username", StatusAs.PASSED,
				"Enters username", "enter", "enter");
		wrapper.addStep(loginRun, "Enter password", StatusAs.PASSED,
				"Enters password", "enter", "enter");
		wrapper.addStep(loginRun, "Click Login", StatusAs.PASSED,
				"Enters username", "", "");
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				loginRun);

		// Create a new Run, Add the Steps for this Run and Attach a File
		ITestCaseRun logoutRun = wrapper.createNewRun(logoutTest, "Run 2",
				StatusAs.PASSED);
		wrapper.addStep(logoutRun, "Enter username", StatusAs.PASSED,
				"Enters username", "enter", "enter");
		wrapper.addStep(logoutRun, "Enter password", StatusAs.PASSED,
				"Enters password", "enter", "enter");
		wrapper.addStep(logoutRun, "Click Login", StatusAs.PASSED,
				"Enters username", "", "");
		wrapper.newAttachment("D:\\Data.xlsx", "My Attachment Description",
				logoutRun);
		wrapper.close();
	}
}
