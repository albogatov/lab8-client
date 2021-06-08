package client.utils;

import commons.app.User;
import commons.network.Request;
import commons.network.Response;
import commons.network.ResponseCode;
import commons.elements.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidAlgorithmParameterException;
import java.util.*;

public class ScriptExecutor {
    private Scanner scanner;
    private Stack<File> paths = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public ScriptExecutor(File script) {
        try {
            scanner = new Scanner(script);
            scannerStack.add(scanner);
            paths.add(script);
        } catch (Exception e) {

        }
    }

    public Request process(ResponseCode lastCode, User user) {
        String input;
        String[] cmd;
        ScriptAction processResult;
        try {
            do {
                try {
                    if (lastCode == ResponseCode.ERROR)
                        throw new InvalidAlgorithmParameterException();
                    while (!scannerStack.isEmpty() && !scanner.hasNextLine()) {
                        scanner.close();
                        scanner = scannerStack.pop();
                        if (!scannerStack.isEmpty()) paths.pop();
                        else return null;
                    }
                    input = scanner.nextLine();
                    if (!input.isEmpty()) {
                        System.out.println(input);
                    }
                    cmd = (input.trim() + " ").split(" ", 2);
                    cmd[1] = cmd[1].trim();
                } catch (NoSuchElementException | IllegalStateException e) {
                    cmd = new String[]{"", ""};
                }
                processResult = defineCmd(cmd[0], cmd[1]);
            } while (cmd[0].isEmpty());
            try {
                if (lastCode == ResponseCode.ERROR || processResult == ScriptAction.ERROR)
                    throw new InvalidAlgorithmParameterException();
                switch (processResult) {
                    case OBJ:
                        Worker worker = readWorker();
                        return new Request(cmd[0], cmd[1], worker, user);
                    case UPD:
                        Worker workerForUpd = readWorker();
                        return new Request(cmd[0], cmd[1], workerForUpd, user);
                    case SCRIPT:
                        File script = new File(cmd[1]);
                        if (!script.exists())
                            throw new FileNotFoundException();
                        if (!paths.isEmpty() && paths.search(script) != -1)
                            throw new InvalidAlgorithmParameterException();
                        scannerStack.push(scanner);
                        paths.push(script);
                        scanner = new Scanner(script);
                        System.out.println("Running " + script.getAbsolutePath());
                        break;
                }
            } catch (FileNotFoundException e) {
                throw new InvalidAlgorithmParameterException();
            } catch (IllegalArgumentException e) {
                throw new InvalidAlgorithmParameterException();
            }
        } catch (InvalidAlgorithmParameterException e) {
            AlertDisplay.showError("ScriptError");
            while (!scannerStack.isEmpty()) {
                scanner.close();
                scanner = scannerStack.pop();
            }
            paths.clear();
            return null;
        }
        return new Request(cmd[0], cmd[1], null, user);
    }

    public ScriptAction defineCmd(String cmdLine, String arg) {
        try {
            switch (cmdLine) {
                case "":
                    return ScriptAction.ERROR;
                case "info":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "add":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.OBJ;
                case "add_if_min":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.OBJ;
                case "update":
                    if (arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.UPD;
                case "remove_by_id":
                    if (arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "remove_greater":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.OBJ;
                case "remove_lower":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.OBJ;
                case "clear":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "count_by_status":
                    if (arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "help":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "print_unique_organization":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "save":
                    if (!arg.isEmpty())
                        throw new IllegalArgumentException();
                    break;
                case "execute_script":
                    if (arg.isEmpty())
                        throw new IllegalArgumentException();
                    return ScriptAction.SCRIPT;
                case "end":
                    if (!arg.isEmpty()) throw new IllegalArgumentException();
                    break;
                default:
                    return ScriptAction.ERROR;
            }
        } catch (IllegalArgumentException e) {
            return ScriptAction.ERROR;
        }
        return ScriptAction.OK;
    }

    public Worker readWorker() throws InvalidAlgorithmParameterException {
        WorkerReader reader = new WorkerReader(scanner);
//        System.out.println(reader.readName() + reader.getCoordinates().toString());
        return new Worker(reader.readName(), reader.getCoordinates(), reader.readSalary(), reader.readEndDate(),
                reader.readPosition(), reader.readStatus(), reader.getOrganization());
    }
}
