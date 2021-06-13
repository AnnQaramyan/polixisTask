package com.test.service;

import com.test.model.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public static final String ZIP_PATH = "src/main/resources/data.zip";

    @Override
    public void save() throws IOException, ParseException {

        ZipFile zipFile = new ZipFile(ZIP_PATH);
        List<User> userList = unZip(zipFile);
        userRepository.saveAll(userList);

    }

    //The function helps to get a stream from Zip file, and to go through all the files and then all the lines of the files.
    public List<User> unZip(ZipFile zipFile) throws IOException, ParseException {

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        ZipEntry entry;
        InputStream stream = null;
        BufferedReader br = null;
        List<User> userList = new ArrayList<>();

        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            stream = zipFile.getInputStream(entry);
            br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String line;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");

                //This check is intended to prevent columns' names from being sent to the database
                if (userData[0].equals("firstName")) {
                    continue;
                }

                userList.add(new User(userData[0], userData[1], checkTypeOfDate(line)));
            }
        }

        if(br != null) {
            br.close();
        }
        if(stream != null) {
            stream.close();
        }

        return userList;
    }

    //The function takes the date of each line , understands the format and returns milliseconds for that date.
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

    //The function returns the format to the appropriate date
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
