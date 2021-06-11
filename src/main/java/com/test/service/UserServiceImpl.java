package com.test.service;

import com.test.model.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void save() throws IOException, ParseException {

        ZipFile zipFile = new ZipFile("src/main/resources/data.zip");

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        ZipEntry entry = null;
        InputStream stream = null;
        BufferedReader br = null;
        List<User> userList = new ArrayList<>();

        while (entries.hasMoreElements()) {

            entry = entries.nextElement();
            stream = zipFile.getInputStream(entry);

            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String currentUser;

            while ((currentUser = br.readLine()) != null) {

                String[] userData = currentUser.split(",");
                if (userData[0].equals("firstName")) {
                    continue;
                }

                if (userData[2].contains("/")) {

                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateObj = curFormater.parse(userData[2]);
                    userList.add(new User(userData[0], userData[1], dateObj));
                } else {
                    String str[] = userData[2].split(",");
                    String strTest[] = userData[2].split(" ");
                    StringBuffer sb = new StringBuffer(strTest[1]);
                    sb.deleteCharAt(sb.length() - 1);
                    String myStr = sb.deleteCharAt(sb.length() - 1).toString();
                    StringBuffer sb1 = new StringBuffer(strTest[0]);
                    String myStr1 = sb1.deleteCharAt(0).toString().concat(" ").concat(myStr).concat(",").concat(" ").concat(userData[3]);
                    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                    Date date = format.parse(myStr1);
                    userList.add(new User(userData[0], userData[1], date));
                }
            }
        }

        br.close();
        stream.close();
        zipFile.close();

        userRepository.saveAll(userList);
    }
}