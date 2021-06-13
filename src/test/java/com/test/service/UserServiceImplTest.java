package com.test.service;

import com.test.model.User;
import com.test.repository.UserRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.batch.api.BatchProperty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@RunWith(SpringRunner.class)
@EnableBatchProcessing
public class UserServiceImplTest {

    @Autowired
    UserRepositoryTest userRepositoryTest;


    @Test
    public List<User> unZip(ZipFile zipFile) throws IOException, ParseException {

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        ZipEntry entry = null;
        InputStream stream = null;
        BufferedReader br = null;
        List<User> userList = new ArrayList<>();

        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            stream = zipFile.getInputStream(entry);
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");

                if (userData[0].equals("firstName")) {
                    continue;
                }

                userList.add(new User(userData[0], userData[1], checkTypeOfDate(line)));
            }
        }
        br.close();
        stream.close();

        return userList;
    }

    //Different type of date parse to Date
    @Test
    public long checkTypeOfDate(String line) throws ParseException {
        String[] userData = line.split(",");

        if (userData[2].contains("/")) {
            SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormat.parse(userData[2]);
            return dateObj.getTime();
        } else {
            int firstIndexOfScope = line.indexOf('"');
            int lastIndexOfScope = line.lastIndexOf('"');
            String dateString = line.substring(firstIndexOfScope + 1, lastIndexOfScope);
            DateFormat dateFormat = generateDateFormat(dateString);
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        }
    }

    @Test
    private DateFormat generateDateFormat(String input) {
        Pattern pattern1 = Pattern.compile("[a-zA-Z]+ [0-9]+st, [0-9]{4}", Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile("[a-zA-Z]+ [0-9]+nd, [0-9]{4}", Pattern.CASE_INSENSITIVE);
        Pattern pattern3 = Pattern.compile("[a-zA-Z]+ [0-9]+rd, [0-9]{4}", Pattern.CASE_INSENSITIVE);
        if (pattern1.matcher(input).find()) {
            return new SimpleDateFormat("MMMM d'st', yyyy");
        } else if (pattern2.matcher(input).find()) {
            return new SimpleDateFormat("MMMM d'nd', yyyy");
        } else if (pattern3.matcher(input).find()) {
            return new SimpleDateFormat("MMMM d'rd', yyyy");
        } else {
            return new SimpleDateFormat("MMMM d'th', yyyy");
        }
    }
}
