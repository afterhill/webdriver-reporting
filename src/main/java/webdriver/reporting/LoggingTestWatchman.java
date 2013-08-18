package webdriver.reporting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class LoggingTestWatchman extends TestWatcher {
    
    private final TestCaseReportWriter reportWriter;
    
    public LoggingTestWatchman(TestCaseReportWriter reportWriter) {
        this.reportWriter = reportWriter;
    }
    
    @Override
    public void starting(Description desc) {
        reportWriter.clearInfoString();
        reportWriter.createNewTestReportFile(desc.getMethodName(),
                getReportFileName(desc));
        reportWriter.info("Start Test: " + testName(desc));
        reportWriter.getPackageName("Start Test: " + testName(desc)
                + " :Package Name " + packageName(desc));
        reportWriter
                .addTestClassNameToJS(createPackageNamewithTestName(desc));
    }
    
    @Override
    public void finished(Description desc) {
        reportWriter.info("End Test: " + testName(desc));
    }
    
    @Override
    public void failed(Throwable t, Description desc) {
        reportWriter.info("STACKTRACE:" + getStackTrace(t));
        reportWriter.info("Failed: " + testName(desc));
        reportWriter
                .addFailedTestClassNameToJS(createPackageNamewithTestName(desc));
    }
    
    @Override
    public void succeeded(Description desc) {
        reportWriter.info("Test succeeded: " + testName(desc));
        super.succeeded(desc);
    }
    
    private String getStackTrace(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        return writer.toString();
    }
    
    private String testName(Description desc) {
        return desc.getClassName() + "." + desc.getMethodName();
    }
    
    private String packageName(Description desc) {
        return desc.getTestClass().getPackage().getName();
    }
    
    private String extractPackageName(String fullPackageName) {
        int indx = fullPackageName.lastIndexOf(".");
        fullPackageName = fullPackageName.substring(indx + 1,
                fullPackageName.length());
        return fullPackageName.toUpperCase();
    }
    
    private String getReportFileName(Description desc) {
        return extractPackageName(packageName(desc))
                + "/"
                + getLogFileName(desc.getClassName(),
                        "." + desc.getMethodName()) + "_log.html";
    }
    
    private String getLogFileName(String className, String methodName) {
        int indx = methodName.lastIndexOf(".");
        methodName = methodName.substring(indx + 1, methodName.length());
        
        return methodName;
    }
    
    private String createPackageNamewithTestName(Description desc) {
        String methodName = desc.getMethodName();
        String packageName = extractPackageName(packageName(desc));
        return packageName + "." + methodName;
    }
    
}
