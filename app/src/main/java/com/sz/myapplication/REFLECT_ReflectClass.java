package com.sz.myapplication;

/**
 * author：created by renlei on 2021/12/7
 * eMail :renlei@yitong.com.cn
 */
public class REFLECT_ReflectClass {
    public String testName;
    public String testId;
    private int testSize;


    public REFLECT_ReflectClass() {
    }

    private REFLECT_ReflectClass(String testName, String testId, int testSize) {
        this.testName = testName;
        this.testId = testId;
        this.testSize = testSize;
    }


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getTestSize() {
        return testSize;
    }

    public void setTestSize(int testSize) {
        this.testSize = testSize;
    }

    @Override
    public String toString() {
        return "FANSHE_Test{" +
                "testName='" + testName + '\'' +
                ", testId='" + testId + '\'' +
                ", testSize=" + testSize +
                '}';
    }
    private String tst(int testSize) {
        return "FANSHE_Test_tst"+testSize;
    }
}
