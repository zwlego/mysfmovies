package mysfmovies;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.mysfmovies.Service;
import com.mysfmovies.JsonSource;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ServiceTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}