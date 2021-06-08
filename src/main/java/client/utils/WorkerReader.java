package client.utils;

import commons.elements.*;

import javax.swing.text.DateFormatter;
import java.security.InvalidAlgorithmParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class WorkerReader {
    private Scanner scanner;

    public WorkerReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readName() throws InvalidAlgorithmParameterException {
        String name;
        try {
            name = scanner.nextLine().trim();
            System.out.println(name);
            if (name.equals(""))
                throw new InvalidAlgorithmParameterException();
            else return name;
        } catch (NoSuchElementException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Integer readX() throws InvalidAlgorithmParameterException {
        Integer x;
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            x = Integer.parseInt(input);
            if (x < 0 || x > 627)
                throw new InvalidAlgorithmParameterException();
            else return x;
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Long readY() throws InvalidAlgorithmParameterException {
        Long y;
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            y = Long.parseLong(input);
            if (y < 0 || y > 990)
                throw new InvalidAlgorithmParameterException();
            else return y;
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Coordinates getCoordinates() throws InvalidAlgorithmParameterException {
        Integer x = readX();
        Long y = readY();
        return new Coordinates(x, y);
    }

    public Integer readSalary() throws InvalidAlgorithmParameterException {
        String input;
        Integer salary;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            salary = Integer.parseInt(input);
            if (salary < 0)
                throw new InvalidAlgorithmParameterException();
            return salary;
        } catch (NoSuchElementException e) {
            throw new InvalidAlgorithmParameterException();
        } catch (NumberFormatException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public LocalDate readEndDate() throws InvalidAlgorithmParameterException {
        String input;
        LocalDate endDate;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            if (input.equals(""))
                return null;
            endDate = LocalDate.parse(input);
            return endDate;
        } catch (NoSuchElementException | DateTimeParseException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Position readPosition() throws InvalidAlgorithmParameterException {
        String input;
        Position position;
        try {
            input = scanner.nextLine().trim().toUpperCase();
            System.out.println(input);
            if (input.equals(""))
                return null;
            position = Position.valueOf(input);
            return position;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Status readStatus() throws InvalidAlgorithmParameterException {
        String input;
        Status status;
        try {
            input = scanner.nextLine().trim().toUpperCase();
            System.out.println(input);
            if (input.equals(""))
                return null;
            status = Status.valueOf(input);
            return status;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public String readOrgName() throws InvalidAlgorithmParameterException {
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            return input;
        } catch (NoSuchElementException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public OrganizationType readOrgType() throws InvalidAlgorithmParameterException {
        String input;
        OrganizationType orgType;
        try {
            input = scanner.nextLine().trim().toUpperCase();
            System.out.println(input);
            if (input.equals(""))
                return null;
            orgType = OrganizationType.valueOf(input);
            return orgType;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Long readAnnualTurnover() throws InvalidAlgorithmParameterException {
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            if (input.equals(""))
                return null;
            else return Long.parseLong(input);
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public String readStreet() throws InvalidAlgorithmParameterException {
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            if (input.equals(""))
                throw new InvalidAlgorithmParameterException();
            else return input;
        } catch (NoSuchElementException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public String readCode() throws InvalidAlgorithmParameterException {
        String input;
        try {
            input = scanner.nextLine().trim();
            System.out.println(input);
            if (input.equals(""))
                throw new InvalidAlgorithmParameterException();
            else return input;
        } catch (NoSuchElementException e) {
            throw new InvalidAlgorithmParameterException();
        }
    }

    public Organization getOrganization() throws InvalidAlgorithmParameterException {
        String name = readOrgName();
        Long annualTurnover = readAnnualTurnover();
        OrganizationType orgType = readOrgType();
        String street = readStreet();
        String postalCode = readCode();
        return new Organization(annualTurnover, orgType, new Address(street, postalCode), name);
    }
}
