package com.tvrtest.tverskoi2.model;

import com.tvrtest.tverskoi2.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {

    private String firstName;
    private String lastName;
    private String birthday;
    private Profession profession;
    private Integer age;
    private String avatarUrl;

    public void setFirstName(String firstName) {
        this.firstName = Utils.firstUpperCaseInWord(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = Utils.firstUpperCaseInWord(lastName);
    }

    public void setBirthday(String birthday) {
        birthday = birthday.replace('-', '.');
        String wrongPattern = "yyyy.MM.dd";
        String correctPattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(wrongPattern);
        simpleDateFormat.setLenient(false);
        try {
            if (simpleDateFormat.parse(birthday) != null) {
                Date date = simpleDateFormat.parse(birthday);
                simpleDateFormat.applyPattern(correctPattern);
                birthday = simpleDateFormat.format(date);
            }
        } catch (Exception exc) {
            try {
                simpleDateFormat.applyPattern(correctPattern);
                simpleDateFormat.parse(birthday);
            } catch (Exception e) {
                birthday = "-";
            }
        }
        this.birthday = birthday;
        calculateAge(birthday);
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    private void calculateAge(String birthday) {
        Integer age;
        String dateFormat = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        try {
            Date currentDate = new Date();
            Date birthdayDate = simpleDateFormat.parse(birthday);
            age = Integer.valueOf(year.format(currentDate)) - Integer.valueOf(year.format(birthdayDate));
            if (Integer.valueOf(month.format(currentDate)) < Integer.valueOf(month.format(birthdayDate))) {
                age = age - 1;
            }
            if (Integer.valueOf(month.format(currentDate)) == Integer.valueOf(month.format(birthdayDate))) {
                SimpleDateFormat day = new SimpleDateFormat("dd");
                if (Integer.valueOf(day.format(currentDate)) < Integer.valueOf(day.format(birthdayDate))) {
                    age = age - 1;
                }
            }
        } catch (Exception exc) {
            age = null;
        }
        this.age = age;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public Profession getProfession() {
        return profession;
    }

    public Integer getAge() {

        return age;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }


}
