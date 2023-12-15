package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Participant {
    @NotBlank(message = "Name is a mandatory field")
    @Size(min = 5, message = "Name must be at least 5 characters")
    @Size(max = 25, message = "Name cannot be more than 25 characters")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth date cannot be today or in the future")
    private Date birthDate;

    @NotBlank(message = "Email is a mandatory field")
    @Email(message = "Invalid email address")
    @Size(max = 50, message = "Email cannot contain more than 50 characters")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid phone number entered")
    private String phoneNum;

    @NotNull(message = "Please specify the number of tickets")
    @Min(value = 1, message = "Minimum 1 ticket")
    @Max(value = 3, message = "Maximum 3 tickets")
    private Integer noOfTickets;

    private String gender;

    // GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(Integer noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    

    // NO ARGS CONSTRUCTOR
    public Participant() {
    }

    // ALL ARGS CONSTRUCTOR
    public Participant(
            @NotBlank(message = "Name is a mandatory field") @Size(min = 5, message = "Name must be at least 5 characters") @Size(max = 25, message = "Name cannot be more than 25 characters") String fullName,
            @Past(message = "Birth date cannot be today or in the future") Date birthDate,
            @NotBlank(message = "Email is a mandatory field") @Email(message = "Invalid email address") @Size(max = 50, message = "Email cannot contain more than 50 characters") String email,
            @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid phone number entered") String phoneNum,
            @Size(min = 1, message = "Minimum 1 ticket") @Size(max = 3, message = "Maximum 3 tickets") Integer noOfTickets,
            String gender) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNum = phoneNum;
        this.noOfTickets = noOfTickets;
        this.gender = gender;
    }
    
}
